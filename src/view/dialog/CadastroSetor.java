package view.dialog;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;

import dao.produto.SetorDAO;
import entities.produto.Setor;
import tables.tableModels.ModeloTabelaSetores;
import tools.Jtext_tools;
import view.panels.produto.Panel_produtos;

import java.awt.Color;
import javax.swing.ListSelectionModel;

public class CadastroSetor extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblCadatroDeProduto;
	private JPanel panel;
	private JLabel lblNomeSetor;
	private JFormattedTextField fTxtNomeSetor = new JFormattedTextField();
	private JLabel lblCodigoSetor;
	private JTable tabelaSetores;
	private JScrollPane scrollPaneSetores;
	private JButton btnNovo;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnSalvar;
	private JButton btnCancelar;
	ArrayList<Setor> setores = new ArrayList<Setor>();
	ModeloTabelaSetores modelo = new ModeloTabelaSetores(setores);
	private Panel_produtos panel_produtos;
	private Jtext_tools text_tools = new Jtext_tools();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroSetor frame = new CadastroSetor(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CadastroSetor(Panel_produtos panel_produtos) {
		this.panel_produtos = panel_produtos;
		tecla_pressionada();
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 451, 358);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setModal(true);

		lblCadatroDeProduto = new JLabel("Cadastro de Setores");
		lblCadatroDeProduto.setBounds(10, 11, 414, 32);
		lblCadatroDeProduto.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadatroDeProduto.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(lblCadatroDeProduto);

		panel = new JPanel();
		panel.setBounds(10, 41, 414, 77);
		panel.setBorder(UIManager.getBorder("PopupMenu.border"));
		contentPane.add(panel);
		panel.setLayout(null);

		lblNomeSetor = new JLabel("Nome");
		lblNomeSetor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeSetor.setBounds(138, 12, 36, 19);
		lblNomeSetor.setEnabled(false);
		panel.add(lblNomeSetor);

		try {
			MaskFormatter mascara_nome_setor = new MaskFormatter("******************************");
			fTxtNomeSetor = new JFormattedTextField(mascara_nome_setor);
			fTxtNomeSetor.setEditable(false);
			fTxtNomeSetor.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent clickNomeSetor) {
					text_tools.move_cursor_inicio(fTxtNomeSetor);
				}
			});
			fTxtNomeSetor.setBounds(179, 13, 225, 20);
			panel.add(fTxtNomeSetor);
			fTxtNomeSetor.setColumns(10);
		} catch (ParseException e1) {
			e1.getMessage();
		}

		lblCodigoSetor = new JLabel("C\u00F3digo");
		lblCodigoSetor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigoSetor.setBounds(10, 12, 50, 19);
		lblCodigoSetor.setEnabled(false);
		panel.add(lblCodigoSetor);

		btnNovo = new JButton("Novo");
		btnNovo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNovo) {
				novo_setor();
			}
		});
		btnNovo.setBounds(10, 43, 89, 23);
		btnNovo.setIcon(icone_mais);
		panel.add(btnNovo);

		btnEditar = new JButton("Editar");
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEditar) {
				editar_setor();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setBounds(165, 42, 89, 23);
		btnEditar.setIcon(icone_editar);
		panel.add(btnEditar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickExcluir) {
				exclui_setor();

			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setBounds(309, 43, 95, 23);
		btnExcluir.setIcon(icone_excluir);
		panel.add(btnExcluir);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickSalvar) {
				salva_setor();
			}
		});
		btnSalvar.setBounds(192, 54, 95, 23);
		btnSalvar.setVisible(false);
		panel.add(btnSalvar);
		btnSalvar.setIcon(icone_salvar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent clickCancelar) {
				cancela_setor();
				tabelaSetores.clearSelection();
			}
		});
		btnCancelar.setBounds(297, 54, 114, 23);
		btnCancelar.setVisible(false);
		panel.add(btnCancelar);
		btnCancelar.setIcon(icone_cancelar);

		try {
			MaskFormatter mascara_codigo_setor = new MaskFormatter("####");
			fTxtCodigoSetor = new JFormattedTextField(mascara_codigo_setor);
			fTxtCodigoSetor.setEditable(false);
			fTxtCodigoSetor.setFocusLostBehavior(JFormattedTextField.PERSIST);
			fTxtCodigoSetor.setBounds(56, 13, 50, 18);
			panel.add(fTxtCodigoSetor);
		} catch (ParseException e) {
			e.getMessage();
		}

		setores = alimenta_setores(setores);
		tabelaSetores = new JTable(modelo);
		tabelaSetores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tabelaSetores.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent selecaoLinhaTabela) {

				ListSelectionModel lsm = (ListSelectionModel) selecaoLinhaTabela.getSource();

				if (!lsm.isSelectionEmpty()) {
					btnExcluir.setEnabled(true);
					btnEditar.setEnabled(true);

					if (btnNovo.isVisible()) {
						fTxtCodigoSetor.setText(tabelaSetores.getValueAt(tabelaSetores.getSelectedRow(), 0).toString());
						fTxtNomeSetor.setText(tabelaSetores.getValueAt(tabelaSetores.getSelectedRow(), 1).toString());
					}

				} else {
					btnExcluir.setEnabled(true);
					btnEditar.setEnabled(true);
				}

			}
		});

		ConfiguralarguracolunaTabela(tabelaSetores);
		tabelaSetores.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabelaSetores.getTableHeader().setReorderingAllowed(false);
		tabelaSetores.setBounds(10, 142, 414, 108);

		scrollPaneSetores = new JScrollPane(tabelaSetores);
		scrollPaneSetores.setBounds(10, 141, 414, 165);
		contentPane.add(scrollPaneSetores);

		lblEsc = new JLabel("Esc:");
		lblEsc.setFont(new Font("Arial", Font.BOLD, 12));
		lblEsc.setBounds(10, 304, 30, 14);
		contentPane.add(lblEsc);

		lblCancelar = new JLabel("Cancelar");
		lblCancelar.setForeground(Color.GRAY);
		lblCancelar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCancelar.setBounds(41, 304, 53, 14);
		contentPane.add(lblCancelar);

		lblEnter = new JLabel("Enter:");
		lblEnter.setFont(new Font("Arial", Font.BOLD, 12));
		lblEnter.setBounds(111, 304, 36, 14);
		contentPane.add(lblEnter);

		lblSalvar = new JLabel("Salvar");
		lblSalvar.setForeground(new Color(0, 128, 0));
		lblSalvar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSalvar.setBounds(150, 304, 36, 14);
		contentPane.add(lblSalvar);

		lblF1 = new JLabel("F1:");
		lblF1.setFont(new Font("Arial", Font.BOLD, 12));
		lblF1.setBounds(202, 304, 23, 14);
		contentPane.add(lblF1);

		lblNovo = new JLabel("Novo");
		lblNovo.setForeground(Color.BLUE);
		lblNovo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNovo.setBounds(225, 304, 36, 14);
		contentPane.add(lblNovo);

		lblF3 = new JLabel("F3:");
		lblF3.setFont(new Font("Arial", Font.BOLD, 12));
		lblF3.setBounds(271, 304, 23, 14);
		contentPane.add(lblF3);

		lblEditar = new JLabel("Editar");
		lblEditar.setForeground(new Color(139, 69, 19));
		lblEditar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEditar.setBounds(294, 304, 36, 14);
		contentPane.add(lblEditar);

		lblF12 = new JLabel("F12:");
		lblF12.setFont(new Font("Arial", Font.BOLD, 12));
		lblF12.setBounds(348, 304, 23, 14);
		contentPane.add(lblF12);

		lblExcluir = new JLabel("Excluir");
		lblExcluir.setForeground(Color.RED);
		lblExcluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblExcluir.setBounds(378, 304, 46, 14);
		contentPane.add(lblExcluir);

	}

	// ---------Funções------------
	public ArrayList<Setor> alimenta_setores(ArrayList<Setor> lista_setor) {
		SetorDAO setor_dao = new SetorDAO();
		lista_setor = setor_dao.listarSetores(lista_setor);
		return lista_setor;
	}

	public Setor novoSetor(Setor novo_setor) {
		if (!fTxtCodigoSetor.getText().trim().isEmpty()) {
			novo_setor.setCodSetor(Integer.parseInt(fTxtCodigoSetor.getText().trim()));
		} else {
			novo_setor.setCodSetor(null);
		}
		novo_setor.setNome(fTxtNomeSetor.getText().trim());
		return novo_setor;
	}

	// Configurando largura das colunas
	public void ConfiguralarguracolunaTabela(JTable tabelaSetores) {
		tabelaSetores.getColumnModel().getColumn(0).setPreferredWidth(50);
		tabelaSetores.getColumnModel().getColumn(1).setPreferredWidth(361);
		tabelaSetores.setAutoCreateRowSorter(true);
	}

	// Recarrega a tabela de setores.
	public void recarregarTabela() {
		setores.clear();
		setores = alimenta_setores(setores);
		modelo = new ModeloTabelaSetores(setores);
		tabelaSetores.setModel(modelo);
		ConfiguralarguracolunaTabela(tabelaSetores);
		modelo.fireTableDataChanged();
	}

	// Ação novo setor
	public void novo_setor() {
		btnNovo.doClick();
		btnEditar.setVisible(false);
		btnExcluir.setVisible(false);
		btnNovo.setVisible(false);
		btnSalvar.setVisible(true);
		btnCancelar.setVisible(true);
		fTxtNomeSetor.setEditable(true);
		lblNomeSetor.setEnabled(true);
		fTxtCodigoSetor.setText(null);
		fTxtNomeSetor.setText(null);
		fTxtNomeSetor.requestFocusInWindow();
		tabelaSetores.clearSelection();
	}

	// Ação botão cancelar
	public void cancela_setor() {
		btnCancelar.doClick();
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnSalvar.setVisible(false);
		btnCancelar.setVisible(false);
		btnNovo.setVisible(true);
		btnEditar.setVisible(true);
		btnExcluir.setVisible(true);
		fTxtNomeSetor.setEditable(false);
		lblNomeSetor.setEnabled(false);
		fTxtCodigoSetor.setText(null);
		fTxtNomeSetor.setText(null);
		fTxtNomeSetor.setEditable(false);
		btnNovo.requestFocus();
	}

	// Ação de salva_setor
	public void salva_setor() {

		btnSalvar.doClick();

		SetorDAO setor_dao = new SetorDAO();
		Setor setor = new Setor();

		if (fTxtNomeSetor.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(btnSalvar, "Necessário informar nome do setor.", "Setor sem nome!",
					JOptionPane.WARNING_MESSAGE);
		} else {

			setor = novoSetor(setor);

			if (fTxtCodigoSetor.getText().trim().isEmpty()) {
				setor = setor_dao.inserirSetor(setor);

				if (setor != null) {
					modelo.addSetor(setor);
					JOptionPane.showMessageDialog(null, "Setor adiconado!\n" + "código: " + setor.getCodSetor() + " "
							+ "\nNome: " + setor.getNome());
				}

			} else {
				setor_dao.alterarSetor(setor);
				JOptionPane.showMessageDialog(null, "Setor alterado!", "Alteração de setores.", JOptionPane.NO_OPTION);
				recarregarTabela();
			}
			btnSalvar.setVisible(false);
			btnCancelar.setVisible(false);
			btnEditar.setVisible(true);
			btnExcluir.setVisible(true);
			btnNovo.setVisible(true);
			fTxtNomeSetor.setText(null);
			fTxtCodigoSetor.setText(null);
			fTxtNomeSetor.setEditable(false);
			panel_produtos.alimenta_setores();
		}
	}

	// Editar setor
	public void editar_setor() {
		if (tabelaSetores.getSelectedRow() != -1 && btnEditar.isEnabled()) {
			fTxtNomeSetor.setEditable(true);
			btnSalvar.setVisible(true);
			btnCancelar.setVisible(true);
			btnNovo.setVisible(false);
			btnExcluir.setVisible(false);
			btnEditar.setVisible(false);
		}
	}

	// Excluir setor
	public void exclui_setor() {
		if (tabelaSetores.getSelectedRow() != -1 && btnExcluir.isEnabled()) {

			btnExcluir.doClick();

			boolean flag;
			SetorDAO setor_dao = new SetorDAO();
			Integer codigo_setor = (Integer) tabelaSetores.getValueAt(tabelaSetores.getSelectedRow(), 0);
			int opcao = JOptionPane.showConfirmDialog(null,
					"Deseja excluir o setor abaixo?\n" + "Cod = " + codigo_setor + "\n" + "Nome = "
							+ (tabelaSetores.getValueAt(tabelaSetores.getSelectedRow(), 1)),
					"Exclusão de setor", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

			flag = opcao == JOptionPane.YES_OPTION;

			if (flag) {
				if (setor_dao.excluirSetor(codigo_setor)) {
					JOptionPane.showMessageDialog(null, "Setor excluído!", "Exclusão de setor",
							JOptionPane.ERROR_MESSAGE);
					modelo.removeSetor(tabelaSetores.getSelectedRow());
					panel_produtos.alimenta_setores();
				}
			}
		}
	}

	// Teclas de atalho
	public void tecla_pressionada() {
		InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "novo");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelar");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "salvar");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "excluir");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "editar");

		this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);

		this.getRootPane().getActionMap().put("cancelar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_cancelar) {
				if (btnNovo.isVisible()) {
					dispose();
				} else {
					cancela_setor();
				}
			}
		});

		this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);

		this.getRootPane().getActionMap().put("salvar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_salvar) {
				if (!btnNovo.isVisible()) {
					salva_setor();
				}

			}
		});

		this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);

		this.getRootPane().getActionMap().put("excluir", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_excluir) {
				exclui_setor();

			}
		});

		this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);

		this.getRootPane().getActionMap().put("novo", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_novo) {
				novo_setor();

			}
		});

		this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);

		this.getRootPane().getActionMap().put("editar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_editar) {
				editar_setor();

			}
		});

	}

	// ------icons--------
	Icon icone_salvar = new ImageIcon(getClass().getResource("/icons/salvar.png"));
	Icon icone_cancelar = new ImageIcon(getClass().getResource("/icons/cancelar.png"));
	Icon icone_mais = new ImageIcon(getClass().getResource("/icons/mais.png"));
	Icon icone_editar = new ImageIcon(getClass().getResource("/icons/editar.png"));
	Icon icone_excluir = new ImageIcon(getClass().getResource("/icons/excluir.png"));
	private JFormattedTextField fTxtCodigoSetor;
	private JLabel lblEsc;
	private JLabel lblCancelar;
	private JLabel lblEnter;
	private JLabel lblSalvar;
	private JLabel lblF1;
	private JLabel lblNovo;
	private JLabel lblF3;
	private JLabel lblEditar;
	private JLabel lblF12;
	private JLabel lblExcluir;
}
