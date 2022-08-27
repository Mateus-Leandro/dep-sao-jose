package view.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dao.financeiro.FormaPagamentoDAO;
import entities.financeiro.Forma_pagamento;
import icons.Icones;
import tables.tableModels.ModeloTabelaFormasPagamento;
import java.awt.Color;

public class CadastroFormaPagamento extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblCadastroDeFormas;
	private JPanel panel;
	private JLabel lblCodigoSetor;
	private JFormattedTextField fTxtCodigoForma;
	private JLabel lblNomeFormaPagamento;
	private JFormattedTextField fTxtNomeForma;
	private JButton btnNovaForma;
	private Icones icones = new Icones();
	private JButton btnEditarForma;
	private JButton btnExcluirForma;
	private JButton btnCancelarForma;
	private JButton btnSalvarForma;
	private JScrollPane scrollPaneFormasPagamento;
	private JTable tabelaFormasPagamento;
	private ModeloTabelaFormasPagamento modelo_tabela;
	private ListSelectionModel lsm;
	private ArrayList<Forma_pagamento> formas;
	private Forma_pagamento forma_selecionada = new Forma_pagamento();
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
	private Faturamento tel_fat;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CadastroFormaPagamento dialog = new CadastroFormaPagamento(null, null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CadastroFormaPagamento(Faturamento tela_faturamento, ArrayList<Forma_pagamento> formas_pagamento) {
		tel_fat = tela_faturamento;
		
		setTitle("Cadastro de formas de pagamento");
		tecla_pressionada();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent fechaTelaFormas) {
				if (!tela_faturamento.getTelaFaturamento().isVisible()) {
					tela_faturamento.dispose();
					dispose();
				} else {
					dispose();
				}
			}
		});
		modelo_tabela = new ModeloTabelaFormasPagamento(formas_pagamento);
		formas = formas_pagamento;
		setBounds(100, 100, 450, 313);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setModal(true);
		setLocationRelativeTo(tela_faturamento);

		contentPanel.setLayout(null);

		lblCadastroDeFormas = new JLabel("Cadastro de Formas de Pagamento");
		lblCadastroDeFormas.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastroDeFormas.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCadastroDeFormas.setBounds(10, 7, 414, 32);
		contentPanel.add(lblCadastroDeFormas);

		panel = new JPanel();
		panel.setBorder(UIManager.getBorder("ComboBox.border"));
		panel.setBounds(10, 41, 414, 76);
		contentPanel.add(panel);
		panel.setLayout(null);

		btnCancelarForma = new JButton("Cancelar");
		btnCancelarForma.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancelarForma) {
				cancela_nova_forma();
			}
		});
		btnCancelarForma.setBounds(146, 53, 116, 23);
		btnCancelarForma.setIcon(icones.getIcone_cancelar());
		btnCancelarForma.setVisible(false);
		panel.add(btnCancelarForma);

		btnSalvarForma = new JButton("Salvar");
		btnSalvarForma.setEnabled(false);
		btnSalvarForma.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickSalvarForma) {
				if (salvar_forma(tela_faturamento)) {
					tela_faturamento.alimenta_formas_pagamento();
					if (!tela_faturamento.isVisible()) {
						tela_faturamento.setVisible(true);
					}
				}
			}
		});
		btnSalvarForma.setBounds(283, 53, 123, 23);
		btnSalvarForma.setIcon(icones.getIcone_salvar());
		btnSalvarForma.setVisible(false);
		panel.add(btnSalvarForma);

		lblCodigoSetor = new JLabel("C\u00F3digo");
		lblCodigoSetor.setBounds(12, 9, 43, 17);
		lblCodigoSetor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblCodigoSetor);

		fTxtCodigoForma = new JFormattedTextField((AbstractFormatter) null);
		fTxtCodigoForma.setEditable(false);
		fTxtCodigoForma.setBounds(60, 6, 50, 20);
		fTxtCodigoForma.setFocusLostBehavior(JFormattedTextField.PERSIST);
		panel.add(fTxtCodigoForma);

		lblNomeFormaPagamento = new JLabel("Nome");
		lblNomeFormaPagamento.setBounds(170, 9, 36, 17);
		lblNomeFormaPagamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblNomeFormaPagamento);

		fTxtNomeForma = new JFormattedTextField((AbstractFormatter) null);
		fTxtNomeForma.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent digitaNomeFormaPagamento) {
				btnSalvarForma.setEnabled(valida_nome_forma());

				if(digitaNomeFormaPagamento.getKeyCode()== digitaNomeFormaPagamento.VK_ENTER) {
					salvar_forma(tela_faturamento);
				}
			}
		});
		fTxtNomeForma.setEditable(false);
		fTxtNomeForma.setBounds(211, 7, 195, 20);
		fTxtNomeForma.setColumns(10);
		panel.add(fTxtNomeForma);

		btnNovaForma = new JButton("Novo");
		btnNovaForma.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNovaForma) {
				nova_forma();
			}
		});
		btnNovaForma.setBounds(12, 42, 89, 23);
		btnNovaForma.setIcon(icones.getIcone_mais());
		panel.add(btnNovaForma);

		btnEditarForma = new JButton("Editar");
		btnEditarForma.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEditarForma) {
				editar_forma();
			}
		});
		btnEditarForma.setEnabled(false);
		btnEditarForma.setBounds(162, 42, 89, 23);
		btnEditarForma.setIcon(icones.getIcone_editar());
		panel.add(btnEditarForma);

		btnExcluirForma = new JButton("Excluir");
		btnExcluirForma.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickExcluirForma) {
				if (excluir_forma(forma_selecionada)) {
					tela_faturamento.alimenta_formas_pagamento();
				}
			}
		});
		btnExcluirForma.setEnabled(false);
		btnExcluirForma.setBounds(304, 42, 102, 23);
		btnExcluirForma.setIcon(icones.getIcone_excluir());
		panel.add(btnExcluirForma);

		tabelaFormasPagamento = new JTable(modelo_tabela);
		tabelaFormasPagamento.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaFormasPagamento.setBounds(10, 126, 414, 125);
		tabelaFormasPagamento.getTableHeader().setReorderingAllowed(false);
		tabelaFormasPagamento.getTableHeader().setResizingAllowed(false);
		tabelaFormasPagamento.setAutoResizeMode(tabelaFormasPagamento.AUTO_RESIZE_OFF);
		ConfiguraLarguraColunaTabela();
		tabelaFormasPagamento.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent selecaoLinhaTabela) {

				lsm = (ListSelectionModel) selecaoLinhaTabela.getSource();
				if (!lsm.isSelectionEmpty()) {
					int linha_selecionda = tabelaFormasPagamento.getSelectedRow();
					Integer cod = (Integer) tabelaFormasPagamento.getValueAt(linha_selecionda, 0);
					String nome = (String) tabelaFormasPagamento.getValueAt(linha_selecionda, 1);

					forma_selecionada = new Forma_pagamento();
					forma_selecionada.setCodigo(cod);
					forma_selecionada.setDescricao(nome);

					btnEditarForma.setEnabled(true);
					btnExcluirForma.setEnabled(true);
				} else {
					btnEditarForma.setEnabled(false);
					btnExcluirForma.setEnabled(false);
					forma_selecionada.setCodigo(null);
					forma_selecionada.setDescricao(null);
				}
			}
		});

		scrollPaneFormasPagamento = new JScrollPane(tabelaFormasPagamento);
		scrollPaneFormasPagamento.setBounds(10, 128, 414, 123);
		contentPanel.add(scrollPaneFormasPagamento);

		lblEsc = new JLabel("Esc:");
		lblEsc.setFont(new Font("Arial", Font.BOLD, 12));
		lblEsc.setBounds(10, 256, 30, 14);
		contentPanel.add(lblEsc);

		lblCancelar = new JLabel("Cancelar");
		lblCancelar.setForeground(Color.GRAY);
		lblCancelar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCancelar.setBounds(41, 256, 53, 14);
		contentPanel.add(lblCancelar);

		lblEnter = new JLabel("Enter:");
		lblEnter.setFont(new Font("Arial", Font.BOLD, 12));
		lblEnter.setBounds(111, 256, 36, 14);
		contentPanel.add(lblEnter);

		lblSalvar = new JLabel("Salvar");
		lblSalvar.setForeground(new Color(0, 128, 0));
		lblSalvar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSalvar.setBounds(150, 256, 36, 14);
		contentPanel.add(lblSalvar);

		lblF1 = new JLabel("F1:");
		lblF1.setFont(new Font("Arial", Font.BOLD, 12));
		lblF1.setBounds(202, 256, 23, 14);
		contentPanel.add(lblF1);

		lblNovo = new JLabel("Novo");
		lblNovo.setForeground(Color.BLUE);
		lblNovo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNovo.setBounds(225, 256, 36, 14);
		contentPanel.add(lblNovo);

		lblF3 = new JLabel("F3:");
		lblF3.setFont(new Font("Arial", Font.BOLD, 12));
		lblF3.setBounds(271, 256, 23, 14);
		contentPanel.add(lblF3);

		lblEditar = new JLabel("Editar");
		lblEditar.setForeground(new Color(139, 69, 19));
		lblEditar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEditar.setBounds(294, 256, 36, 14);
		contentPanel.add(lblEditar);

		lblF12 = new JLabel("F12:");
		lblF12.setFont(new Font("Arial", Font.BOLD, 12));
		lblF12.setBounds(348, 256, 23, 14);
		contentPanel.add(lblF12);

		lblExcluir = new JLabel("Excluir");
		lblExcluir.setForeground(Color.RED);
		lblExcluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblExcluir.setBounds(378, 256, 46, 14);
		contentPanel.add(lblExcluir);
	}

	// ------ Funções ------
	public void ConfiguraLarguraColunaTabela() {
		tabelaFormasPagamento.getColumnModel().getColumn(0).setPreferredWidth(60); // Código forma de pagamento
		tabelaFormasPagamento.getColumnModel().getColumn(1).setPreferredWidth(351); // Nome
	}

	public void nova_forma() {
		btnNovaForma.setVisible(false);
		btnCancelarForma.setVisible(true);
		btnSalvarForma.setVisible(true);
		btnEditarForma.setVisible(false);
		btnExcluirForma.setVisible(false);
		fTxtNomeForma.setEditable(true);
		fTxtNomeForma.requestFocus();
		tabelaFormasPagamento.clearSelection();
	}

	public void cancela_nova_forma() {
		btnNovaForma.setVisible(true);
		btnCancelarForma.setVisible(false);
		btnEditarForma.setVisible(true);
		btnExcluirForma.setVisible(true);
		btnSalvarForma.setVisible(false);
		fTxtNomeForma.setEditable(false);
		tabelaFormasPagamento.clearSelection();
		btnNovaForma.requestFocus();
		limpar_campos();
	}

	public void editar_forma() {
		if (forma_selecionada.getCodigo() != null && btnNovaForma.isVisible()) {
			btnNovaForma.setVisible(false);
			btnExcluirForma.setVisible(false);
			btnEditarForma.setVisible(false);
			btnCancelarForma.setVisible(true);
			btnSalvarForma.setVisible(true);
			fTxtCodigoForma.setText(forma_selecionada.getCodigo().toString());
			fTxtNomeForma.setText(forma_selecionada.getDescricao());
			fTxtNomeForma.setEditable(true);

			fTxtNomeForma.requestFocus();
		}
	}

	public Boolean salvar_forma(Faturamento tela_faturamento) {
		if (btnSalvarForma.isEnabled()) {
			if (fTxtCodigoForma.getText().trim().isEmpty()) {
				FormaPagamentoDAO forma_pagamento_dao = new FormaPagamentoDAO();
				Forma_pagamento forma = new Forma_pagamento(null, fTxtNomeForma.getText().trim());
				forma = forma_pagamento_dao.salvar_forma(forma);

				if (forma.getCodigo() != null) {
					JOptionPane.showMessageDialog(btnCancelarForma,
							"Inclusa nova forma de pagamento.\n" + forma.getCodigo() + " - " + forma.getDescricao(),
							"Inclusão de forma de pagamento.", JOptionPane.NO_OPTION);
					formas.add(forma);
					modelo_tabela.fireTableDataChanged();
					cancela_nova_forma();
					return true;
				} else {
					return false;
				}
			} else {
				return salvar_edicao(tela_faturamento);
			}
		}else {
			return false;
		}
	}

	public Boolean salvar_edicao(Faturamento tela_faturamento) {
		forma_selecionada.setDescricao(fTxtNomeForma.getText().trim());
		FormaPagamentoDAO forma_pagamento_dao = new FormaPagamentoDAO();
		Forma_pagamento forma = forma_selecionada;

		if (forma_pagamento_dao.alterar_forma(forma)) {
			for (Forma_pagamento form : formas) {
				if (form.equals(forma)) {
					formas.set(formas.indexOf(form), forma);
				}
			}
			modelo_tabela.fireTableDataChanged();
			tela_faturamento.alimenta_formas_pagamento();
			cancela_nova_forma();
			return true;
		} else {
			return false;
		}
	}

	public Boolean excluir_forma(Forma_pagamento forma_excluida) {

		if (btnExcluirForma.isEnabled()) {
			FormaPagamentoDAO forma_pagamento_dao = new FormaPagamentoDAO();

			int opcao = JOptionPane.showConfirmDialog(btnEditarForma,
					"Deseja excluir a forma de pagamento abaixo?\nCódigo: " + forma_selecionada.getCodigo() + "\nNome: "
							+ forma_selecionada.getDescricao(),
					"Exclusão de forma de pagamento.", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

			Boolean flag = opcao == JOptionPane.YES_OPTION;
			if (flag) {
				if (forma_pagamento_dao.excluir_forma_pagamento(forma_excluida)) {
					formas.remove(forma_excluida);
					JOptionPane.showMessageDialog(btnEditarForma, "Forma de pagamento excluída.",
							"Exclusão de forma de pagamento", JOptionPane.ERROR_MESSAGE);
					modelo_tabela.fireTableDataChanged();
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void limpar_campos() {
		fTxtNomeForma.setText(null);
		fTxtCodigoForma.setText(null);
	}

	public Boolean valida_nome_forma() {
		if (fTxtNomeForma.getText().trim().isEmpty()) {
			return false;
		} else {
			for (Forma_pagamento form : formas) {
				if (form.getDescricao().equalsIgnoreCase(fTxtNomeForma.getText().trim())) {
					return false;
				}
			}
		}
		return true;
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
				if (btnNovaForma.isVisible()) {
					dispose();
				} else {
					cancela_nova_forma();
				}
			}
		});

		this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);

		this.getRootPane().getActionMap().put("salvar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_salvar) {
				if (!btnNovaForma.isVisible()) {
					salvar_forma(tel_fat);
				}

			}
		});

		this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);

		this.getRootPane().getActionMap().put("excluir", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_excluir) {
				excluir_forma(forma_selecionada);
			}
		});

		this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);

		this.getRootPane().getActionMap().put("novo", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_novo) {
				nova_forma();
			}
		});

		this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);

		this.getRootPane().getActionMap().put("editar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_editar) {
				editar_forma();
			}
		});

	}
}
