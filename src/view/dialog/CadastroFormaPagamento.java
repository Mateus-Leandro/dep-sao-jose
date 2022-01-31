package view.dialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import dao.FormaPagamentoDAO;
import entities.financeiro.Forma_pagamento;
import icons.Icones;
import tables.tableModels.ModeloTabelaFormasPagamento;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent fechaTelaFormas) {
				if(!tela_faturamento.getTelaFaturamento().isVisible()) {
					tela_faturamento.dispose();
					dispose();
				}else {
					dispose();
				}
			}
		});
		modelo_tabela = new ModeloTabelaFormasPagamento(formas_pagamento);
		formas = formas_pagamento;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
//		setModal(true);
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
				if (btnSalvarForma.isEnabled()) {
					if (fTxtCodigoForma.getText().trim().isEmpty()) {
						if (salvar_forma()) {
							tela_faturamento.alimenta_formas_pagamento();
							if(!tela_faturamento.isVisible()) {
								tela_faturamento.setVisible(true);
							}
						}
					} else {
						if (editar_forma()) {
							tela_faturamento.alimenta_formas_pagamento();
						}
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
				btnNovaForma.setVisible(false);
				btnExcluirForma.setVisible(false);
				btnEditarForma.setVisible(false);
				btnCancelarForma.setVisible(true);
				btnSalvarForma.setVisible(true);
				fTxtCodigoForma.setText(forma_selecionada.getCodigo().toString());
				fTxtNomeForma.setText(forma_selecionada.getDescricao());
				fTxtNomeForma.setEditable(true);
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

				if (btnExcluirForma.isEnabled()) {
					int opcao = JOptionPane.showConfirmDialog(btnEditarForma,
							"Deseja excluir a forma de pagamento abaixo?\nCódigo: " + forma_selecionada.getCodigo()
									+ "\nNome: " + forma_selecionada.getDescricao(),
							"Exclusão de forma de pagamento.", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

					Boolean flag = opcao == JOptionPane.YES_OPTION;

					if (flag) {
						if (excluir_forma(forma_selecionada)) {
							tela_faturamento.alimenta_formas_pagamento();
						}
					}
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

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
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
		limpar_campos();
	}

	public Boolean salvar_forma() {
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

	}

	public Boolean editar_forma() {
		FormaPagamentoDAO forma_pagamento_dao = new FormaPagamentoDAO();
		Forma_pagamento forma = new Forma_pagamento(Integer.parseInt(fTxtCodigoForma.getText().trim()),
				fTxtNomeForma.getText().trim());

		if (forma_pagamento_dao.alterar_forma(forma)) {
			for(Forma_pagamento form : formas) {
				if(form.equals(forma)) {
					formas.set(formas.indexOf(form), forma);
				}
			}
			modelo_tabela.fireTableDataChanged();
			cancela_nova_forma();
			return true;
		} else {
			return false;
		}
	}

	public Boolean excluir_forma(Forma_pagamento forma_excluida) {
		FormaPagamentoDAO forma_pagamento_dao = new FormaPagamentoDAO();
		if (forma_pagamento_dao.excluir_forma_pagamento(forma_excluida)) {

			formas.remove(forma_excluida);
			JOptionPane.showMessageDialog(btnEditarForma, "Forma de pagamento excluída.",
					"Exclusão de forma de pagamento", JOptionPane.ERROR_MESSAGE);
			modelo_tabela.fireTableDataChanged();
			return true;
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
}
