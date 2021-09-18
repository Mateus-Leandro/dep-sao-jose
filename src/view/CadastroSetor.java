package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import dao.SetorDAO;
import entidades.Setor;
import tableModels.ModeloTabelaSetores;

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

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroSetor frame = new CadastroSetor();
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
	public CadastroSetor() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
			fTxtNomeSetor.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent clickNomeSetor) {
					if(fTxtNomeSetor.getText().trim().isEmpty()) {
						fTxtNomeSetor.setCaretPosition(0);
					}
				}
			});
			fTxtNomeSetor.setEnabled(false);
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
				btnEditar.setVisible(false);
				btnExcluir.setVisible(false);
				btnNovo.setVisible(false);
				btnSalvar.setVisible(true);
				btnCancelar.setVisible(true);
				fTxtNomeSetor.setEnabled(true);
				lblNomeSetor.setEnabled(true);
				fTxtCodigoSetor.setText(null);
				fTxtNomeSetor.setText(null);
			}
		});
		btnNovo.setBounds(10, 43, 89, 23);
		btnNovo.setIcon(icone_mais);
		panel.add(btnNovo);

		btnEditar = new JButton("Editar");
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEditar) {
				if (tabelaSetores.getSelectedRow() != -1 && btnEditar.isEnabled()) {
					fTxtNomeSetor.setEnabled(true);
					btnSalvar.setVisible(true);
					btnCancelar.setVisible(true);
					btnNovo.setVisible(false);
					btnExcluir.setVisible(false);
					btnEditar.setVisible(false);
				}
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
				if (tabelaSetores.getSelectedRow() != -1 && btnExcluir.isEnabled()) {
					boolean flag;
					SetorDAO setor_dao = new SetorDAO();
					Integer codigo_setor = (Integer) tabelaSetores.getValueAt(tabelaSetores.getSelectedRow(), 0);
					int opcao = JOptionPane.showConfirmDialog(null,
							"Deseja excluir o setor abaixo?\n" + "Cod = " + codigo_setor + "\n" + "Nome = "
									+ (tabelaSetores.getValueAt(tabelaSetores.getSelectedRow(), 1)),
							"Exclusão de setor", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

					flag = opcao == JOptionPane.YES_OPTION;

					if (flag) {
						if(setor_dao.excluirSetor(codigo_setor)) {
							JOptionPane.showMessageDialog(null, "Setor excluído!", "Exclusão de setor",
									JOptionPane.ERROR_MESSAGE);
							modelo.removeSetor(tabelaSetores.getSelectedRow());
						}else {
							JOptionPane.showMessageDialog(null, "Verifique se existem produtos que usam este setor.","Erro ao excluir setor!",JOptionPane.PLAIN_MESSAGE);
						}
					}
				}
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

				if (fTxtNomeSetor.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(btnSalvar, "Necessário informar nome do setor.", "Setor sem nome!",
							JOptionPane.WARNING_MESSAGE);
				} else {
					SetorDAO setor_dao = new SetorDAO();
					Setor setor = new Setor();

					setor = novoSetor(setor);
					setor.setCodSetor(setor_dao.inserirSetor(setor));   
					modelo.addSetor(setor);
					JOptionPane.showMessageDialog(null, "Setor adiconado!\n" + "código: " + setor.getCodSetor() + " "
							+ "\nNome: " + setor.getNome());
					btnSalvar.setVisible(false);
					btnCancelar.setVisible(false);
					btnEditar.setVisible(true);
					btnExcluir.setVisible(true);
					btnNovo.setVisible(true);
					fTxtNomeSetor.setText(null);
				}
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
				btnEditar.setEnabled(false);
				btnExcluir.setEnabled(false);
				btnSalvar.setVisible(false);
				btnCancelar.setVisible(false);
				btnNovo.setVisible(true);
				btnEditar.setVisible(true);
				btnExcluir.setVisible(true);
				fTxtNomeSetor.setEnabled(false);
				lblNomeSetor.setEnabled(false);
				fTxtCodigoSetor.setText(null);
				fTxtNomeSetor.setText(null);
			}
		});
		btnCancelar.setBounds(297, 54, 114, 23);
		btnCancelar.setVisible(false);
		panel.add(btnCancelar);
		btnCancelar.setIcon(icone_cancelar);

		try {
			MaskFormatter mascara_codigo_setor = new MaskFormatter("####");
			fTxtCodigoSetor = new JFormattedTextField(mascara_codigo_setor);
			fTxtCodigoSetor.setEnabled(false);
			fTxtCodigoSetor.setFocusLostBehavior(JFormattedTextField.PERSIST);
			fTxtCodigoSetor.setBounds(56, 13, 50, 18);
			panel.add(fTxtCodigoSetor);
		} catch (ParseException e) {
			e.getMessage();
		}

		setores = alimenta_setores(setores);
		tabelaSetores = new JTable(modelo);
		tabelaSetores.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickTabelaSetores) {
				btnExcluir.setEnabled(true);
				btnEditar.setEnabled(true);
				if(btnNovo.isVisible()) {
					fTxtCodigoSetor.setText(tabelaSetores.getValueAt(tabelaSetores.getSelectedRow(), 0).toString());
					fTxtNomeSetor.setText(tabelaSetores.getValueAt(tabelaSetores.getSelectedRow(), 1).toString());
				}
				
			}
		});
		ConfiguralarguracolunaTabela(tabelaSetores);
		tabelaSetores.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabelaSetores.getTableHeader().setReorderingAllowed(false);
		tabelaSetores.setBounds(10, 142, 414, 108);

		scrollPaneSetores = new JScrollPane(tabelaSetores);
		scrollPaneSetores.setBounds(10, 141, 414, 109);
		contentPane.add(scrollPaneSetores);

	}

	// ---------Funções------------
	public ArrayList<Setor> alimenta_setores(ArrayList<Setor> lista_setor) {
		SetorDAO setor_dao = new SetorDAO();
		lista_setor = setor_dao.listarSetores(lista_setor);
		return lista_setor;
	}

	public Setor novoSetor(Setor novo_setor) {
		novo_setor.setCodSetor(null);
		novo_setor.setNome(fTxtNomeSetor.getText().trim());
		return novo_setor;
	}

	// Configurando largura das colunas
	public void ConfiguralarguracolunaTabela(JTable tabelaSetores) {
		tabelaSetores.getColumnModel().getColumn(0).setPreferredWidth(50);
		tabelaSetores.getColumnModel().getColumn(1).setPreferredWidth(361);
		tabelaSetores.setAutoCreateRowSorter(true);
	}

	// ------Icones--------
	Icon icone_salvar = new ImageIcon(getClass().getResource("/icones/salvar.png"));
	Icon icone_cancelar = new ImageIcon(getClass().getResource("/icones/cancelar.png"));
	Icon icone_mais = new ImageIcon(getClass().getResource("/icones/mais.png"));
	Icon icone_editar = new ImageIcon(getClass().getResource("/icones/editar.png"));
	Icon icone_excluir = new ImageIcon(getClass().getResource("/icones/excluir.png"));
	private JFormattedTextField fTxtCodigoSetor;
}
