package view.dialog;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import dao.ClienteDAO;
import dao.ConfiguracaoDAO;
import dao.FaturamentoDAO;
import dao.OrcamentoDAO;
import entities.cliente.Cliente;
import entities.configuracoes.Configuracoes;
import entities.financeiro.Forma_pagamento;
import entities.orcamentos.Orcamento;
import entities.orcamentos.Produto_Orcamento;
import icons.Icones;
import pdf.Gera_pdf;
import tables.tableModels.ModeloTabelaOrcamentos;
import tables.tableSorters.SorterMonetario;
import view.panels.Panel_orcamento;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Orcamentos_do_cliente extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblOrcamentos_do_realizados;
	private JSeparator separador_orcamentos;
	private JScrollPane scrollPaneOrcamentos;
	private JTable tabelaOrcamentos;
	private JLabel lblPesquisarPorOrcamento;
	private JComboBox<String> cbxTipoPesquisaOrcamento;
	private JFormattedTextField fTxtPesquisaOrcamento = new JFormattedTextField();
	private DefaultListModel<Cliente> list_model_clientes = new DefaultListModel<Cliente>();
	private ArrayList<Cliente> lista_clientes = new ArrayList<Cliente>();
	private ArrayList<Orcamento> orcamentos_cliente = new ArrayList<Orcamento>();
	private ModeloTabelaOrcamentos modelo_tabela_orcamentos = new ModeloTabelaOrcamentos(orcamentos_cliente);
	private JLabel lblCliente;
	private JSeparator separador_cliente;
	private JComboBox<String> cbxTipoPesquisaCliente;
	private JLabel lblPesquisarPorCliente;
	private JFormattedTextField fTxtPesquisaCliente;
	private JSeparator separador_orcamento;
	private JLabel lblOrcamento;
	private JCheckBox checkBoxFaturado = new JCheckBox("Somente faturados");
	private JList<Cliente> ltClientes;
	private JScrollPane scrollPaneLtClientes;
	private Cliente cliente_selecionado;
	private ArrayList<Produto_Orcamento> produtos_do_orcamento = new ArrayList<Produto_Orcamento>();
	private JSeparator separador_orcamentos1;
	private JLabel lblOrcamentosDoCliente;
	private JSeparator separador_orcamentos2;
	private JFormattedTextField fTxtObservacao;
	private JLabel lblObservacoes;
	private JSeparator separador_observacoes;
	private ListSelectionModel lsm;
	private SorterMonetario spv = new SorterMonetario();
	private Orcamento orcamento_selecionado = new Orcamento();
	private JButton btnExcluirOrcamento;
	private Icones icones = new Icones();
	private JButton btnEditarOrcamento;
	private NumberFormat nf = new DecimalFormat("R$ ,##0.00");
	private JButton btnEditarObservacao;
	private JButton btnExcluirObservacao;
	private JButton btnSalvarObservacao = new JButton("Salvar observa\u00E7\u00E3o");
	private JButton btnCancelarObservacao = new JButton("Cancelar");
	private JButton btnFaturar;
	private JButton btnImprimir;
	private ConfiguracaoDAO conf_dao = new ConfiguracaoDAO();
	private Configuracoes configuracoes_do_sistema = conf_dao.busca_configuracoes();
	private String numero_orcamento;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Orcamentos_do_cliente frame = new Orcamentos_do_cliente(null);
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
	public Orcamentos_do_cliente(Panel_orcamento panel_orcamento) {
		alimentar_lista_orcamento(orcamentos_cliente, null);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent fechamentoDaJanela) {
				panel_orcamento.limpar_campos();
			}
		});
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 766, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		// setModal(true);

		ltClientes = new JList<Cliente>();
		ltClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickListaClientes) {

				scrollPaneLtClientes.setVisible(false);
				cliente_selecionado = ltClientes.getSelectedValue();
				fTxtPesquisaCliente.setText(cliente_selecionado.getNome());

				alimentar_lista_orcamento(orcamentos_cliente, cliente_selecionado);
			}
		});
		ltClientes.setBounds(197, 104, 514, 70);

		scrollPaneLtClientes = new JScrollPane(ltClientes);
		scrollPaneLtClientes.setBounds(198, 96, 514, 58);
		scrollPaneLtClientes.setVisible(false);
		contentPane.setLayout(null);
		contentPane.add(scrollPaneLtClientes);

		lblOrcamentos_do_realizados = new JLabel("Or\u00E7amentos Realizados");
		lblOrcamentos_do_realizados.setBounds(228, 11, 287, 29);
		lblOrcamentos_do_realizados.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrcamentos_do_realizados.setFont(new Font("Tahoma", Font.BOLD, 24));
		contentPane.add(lblOrcamentos_do_realizados);

		separador_orcamentos = new JSeparator();
		separador_orcamentos.setBounds(10, 50, 727, 9);
		contentPane.add(separador_orcamentos);

		tabelaOrcamentos = new JTable(modelo_tabela_orcamentos);
		tabelaOrcamentos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tabelaOrcamentos.setBounds(10, 87, 331, 185);
		tabelaOrcamentos.getTableHeader().setReorderingAllowed(false);
		tabelaOrcamentos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tabelaOrcamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ConfiguraLarguraColunaTabelaOrcamento(tabelaOrcamentos);
		tabelaOrcamentos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent selecaoLinhaTabela) {

				lsm = (ListSelectionModel) selecaoLinhaTabela.getSource();
				if (!lsm.isSelectionEmpty()) {

					int linha_selecionada = tabelaOrcamentos.getSelectedRow();
					Integer id_orcamento_selecionado = (Integer) tabelaOrcamentos.getValueAt(linha_selecionada, 0);
					orcamento_selecionado = busca_orcamento_selecionado(id_orcamento_selecionado);
					panel_orcamento.lista_produtos_do_orcamento_selecionado(orcamento_selecionado);
					panel_orcamento.setNumeroOrcamento(orcamento_selecionado);

					fTxtObservacao.setText(orcamento_selecionado.getObservacao());

					btnExcluirOrcamento.setEnabled(true);
					btnEditarOrcamento.setEnabled(true);
					btnFaturar.setEnabled(true);
					btnImprimir.setEnabled(true);

					btnEditarObservacao.setEnabled(true);
					if (orcamento_selecionado.getObservacao() != null) {
						btnExcluirObservacao.setEnabled(true);
					} else {
						btnExcluirObservacao.setEnabled(false);
					}

				} else {
					btnExcluirOrcamento.setEnabled(false);
					btnEditarOrcamento.setEnabled(false);
					btnEditarObservacao.setEnabled(false);
					btnExcluirObservacao.setEnabled(false);
					btnFaturar.setEnabled(false);
					fTxtObservacao.setText(null);
					btnImprimir.setEnabled(false);
				}
			}
		});

		scrollPaneOrcamentos = new JScrollPane(tabelaOrcamentos);
		scrollPaneOrcamentos.setBounds(12, 205, 725, 111);
		contentPane.add(scrollPaneOrcamentos);

		lblPesquisarPorOrcamento = new JLabel("Pesquisar por");
		lblPesquisarPorOrcamento.setBounds(10, 128, 89, 20);
		lblPesquisarPorOrcamento.setToolTipText("");
		lblPesquisarPorOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblPesquisarPorOrcamento);

		cbxTipoPesquisaOrcamento = new JComboBox<String>();
		cbxTipoPesquisaOrcamento.setBounds(97, 127, 97, 22);
		cbxTipoPesquisaOrcamento.setModel(new DefaultComboBoxModel(new String[] { "N\u00FAmero" }));
		cbxTipoPesquisaOrcamento.setSelectedIndex(0);
		cbxTipoPesquisaOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(cbxTipoPesquisaOrcamento);

		fTxtPesquisaOrcamento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaPesquisaOrcamento) {
				alimentar_lista_orcamento(orcamentos_cliente, cliente_selecionado);
			}
		});
		fTxtPesquisaOrcamento.setBounds(202, 129, 386, 20);
		fTxtPesquisaOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtPesquisaOrcamento.setFocusLostBehavior(JFormattedTextField.PERSIST);
		contentPane.add(fTxtPesquisaOrcamento);

		lblCliente = new JLabel("Cliente");
		lblCliente.setBounds(10, 60, 48, 20);
		lblCliente.setToolTipText("");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(lblCliente);

		separador_cliente = new JSeparator();
		separador_cliente.setBounds(62, 70, 675, 9);
		contentPane.add(separador_cliente);

		cbxTipoPesquisaCliente = new JComboBox<String>();
		cbxTipoPesquisaCliente.setBounds(98, 80, 89, 22);
		cbxTipoPesquisaCliente
				.setModel(new DefaultComboBoxModel<String>(new String[] { "Nome", "Apelido", "C\u00F3digo" }));
		cbxTipoPesquisaCliente.setSelectedIndex(0);
		cbxTipoPesquisaCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(cbxTipoPesquisaCliente);

		lblPesquisarPorCliente = new JLabel("Pesquisar por");
		lblPesquisarPorCliente.setBounds(9, 82, 89, 20);
		lblPesquisarPorCliente.setToolTipText("");
		lblPesquisarPorCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblPesquisarPorCliente);

		fTxtPesquisaCliente = new JFormattedTextField();
		fTxtPesquisaCliente.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent perdaFocoNomeCliente) {
				scrollPaneLtClientes.setVisible(false);
			}
		});
		fTxtPesquisaCliente.setBounds(198, 78, 514, 20);
		fTxtPesquisaCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaCliente) {
				if (fTxtPesquisaCliente.getText().trim().isEmpty()) {

					cliente_selecionado = null;
					scrollPaneLtClientes.setVisible(false);

					alimentar_lista_orcamento(orcamentos_cliente, cliente_selecionado);
				} else {
					alimenta_lt_clientes(cbxTipoPesquisaCliente.getSelectedItem().toString(),
							fTxtPesquisaCliente.getText().trim(), lista_clientes);
				}

			}
		});
		fTxtPesquisaCliente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtPesquisaCliente.setFocusLostBehavior(JFormattedTextField.PERSIST);
		contentPane.add(fTxtPesquisaCliente);

		separador_orcamento = new JSeparator();
		separador_orcamento.setBounds(91, 116, 646, 9);
		contentPane.add(separador_orcamento);

		lblOrcamento = new JLabel("Or\u00E7amento");
		lblOrcamento.setBounds(10, 106, 82, 20);
		lblOrcamento.setToolTipText("");
		lblOrcamento.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(lblOrcamento);

		checkBoxFaturado.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent clickSoFaturado) {
				alimentar_lista_orcamento(orcamentos_cliente, cliente_selecionado);
			}
		});
		checkBoxFaturado.setBounds(594, 127, 143, 23);
		checkBoxFaturado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(checkBoxFaturado);

		separador_orcamentos1 = new JSeparator();
		separador_orcamentos1.setBounds(12, 193, 265, 9);
		contentPane.add(separador_orcamentos1);

		lblOrcamentosDoCliente = new JLabel("Or\u00E7amentos do cliente");
		lblOrcamentosDoCliente.setToolTipText("");
		lblOrcamentosDoCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblOrcamentosDoCliente.setBounds(281, 182, 160, 20);
		contentPane.add(lblOrcamentosDoCliente);

		separador_orcamentos2 = new JSeparator();
		separador_orcamentos2.setBounds(440, 193, 297, 9);
		contentPane.add(separador_orcamentos2);

		fTxtObservacao = new JFormattedTextField();
		fTxtObservacao.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtObservacao.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtObservacao.setEditable(false);
		fTxtObservacao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtObservacao.setBounds(9, 368, 727, 29);
		contentPane.add(fTxtObservacao);

		lblObservacoes = new JLabel("Observa\u00E7\u00E3o");
		lblObservacoes.setToolTipText("");
		lblObservacoes.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblObservacoes.setBounds(11, 341, 89, 20);
		contentPane.add(lblObservacoes);

		separador_observacoes = new JSeparator();
		separador_observacoes.setBounds(96, 352, 287, 9);
		contentPane.add(separador_observacoes);

		btnExcluirOrcamento = new JButton("Excluir");
		btnExcluirOrcamento.setEnabled(false);
		btnExcluirOrcamento.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickExcluirOrcamento) {

				if (btnExcluirOrcamento.isEnabled()) {

					int opcao = JOptionPane.showConfirmDialog(btnExcluirOrcamento,
							"Deseja realmente excluir o or�amento abaixo?" + "\n\nOr�amento N�:  "
									+ orcamento_selecionado.getId_orcamento() + "\nCliente: "
									+ orcamento_selecionado.getCliente().getNome() + "\nValor: "
									+ nf.format(orcamento_selecionado.getValor_total())
									+ "\n\nCASO EXCLU�DO N�O SER� POSS�VEL RECUPER�-LO E TODOS OS DADOS DO OR�AMENTO SER�O REMOVIDOS PERMANENTIMENTE!",
							"Exclus�o de or�amento.", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

					Boolean flag = opcao == JOptionPane.YES_OPTION;

					if (flag) {
						OrcamentoDAO orcamento_dao = new OrcamentoDAO();
						if (orcamento_dao.excluir_orcamento(orcamento_selecionado.getId_orcamento())) {
							JOptionPane.showMessageDialog(lblOrcamentosDoCliente,
									"Or�amento " + orcamento_selecionado.getId_orcamento() + " " + "exclu�do.",
									"Exclus�o de or�amento", JOptionPane.ERROR_MESSAGE);
							orcamentos_cliente.remove(orcamento_selecionado);
							orcamento_selecionado = null;
							modelo_tabela_orcamentos.fireTableDataChanged();
							panel_orcamento.limpar_campos();
							tabelaOrcamentos.clearSelection();
						}
					}

				}

			}
		});
		btnExcluirOrcamento.setIcon(icones.getIcone_excluir());
		btnExcluirOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExcluirOrcamento.setBounds(119, 159, 97, 29);
		contentPane.add(btnExcluirOrcamento);

		btnEditarOrcamento = new JButton("Editar");
		btnEditarOrcamento.setEnabled(false);
		btnEditarOrcamento.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEditar) {
				if (btnEditarOrcamento.isEnabled()) {
					configuracoes_do_sistema = conf_dao.busca_configuracoes(); // Buscando novamente as ocnfigura��es do
																				// sistema.
					FaturamentoDAO faturamento_dao = new FaturamentoDAO();
					orcamento_selecionado.setParcelas(faturamento_dao.lista_parcelas(orcamento_selecionado));
					Boolean flag;

					// Valida a configura��o de altera��o de or�amentos que possuem parcelas
					// lan�adas
					if (orcamento_selecionado.getParcelas().size() != 0) {
						if (configuracoes_do_sistema.getAltera_orc().equals("SIM")) {
							flag = true;
						} else if (configuracoes_do_sistema.getAltera_orc().equals("PERGUNTAR")) {
							int opcao = JOptionPane.showConfirmDialog(lblOrcamentosDoCliente,
									"O or�amento selecionado j� possui parcelas lan�adas, deseja alter�-lo?",
									"Or�amento com parcelas salvas.", JOptionPane.YES_OPTION,
									JOptionPane.WARNING_MESSAGE);
							flag = opcao == JOptionPane.YES_OPTION;
						} else {
							flag = false;
							JOptionPane.showMessageDialog(lblOrcamentosDoCliente,
									"As configura��es atuais n�o permitem alterar or�amentos que j� possuem parcelas lan�adas,\ncaso desejar alterar o or�amento selecionado, altere primeiro as configura��es do sistema na aba 'Configura��es' e libere esse tipo de altera��o.",
									"Or�amento com parcelas salvas.", JOptionPane.WARNING_MESSAGE);
						}
					} else {
						flag = true;
					}
					if (flag) {
						panel_orcamento.editar_orcamento(orcamento_selecionado);
						dispose();
					}
				}
			}
		});
		btnEditarOrcamento.setIcon(icones.getIcone_editar());
		btnEditarOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEditarOrcamento.setBounds(10, 159, 97, 29);

		contentPane.add(btnEditarOrcamento);

		btnEditarObservacao = new JButton("Editar observa\u00E7\u00E3o");
		btnEditarObservacao.setEnabled(false);
		btnEditarObservacao.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEditarObservacao) {
				if (btnEditarObservacao.isEnabled()) {
					btnEditarObservacao.setVisible(false);
					btnExcluirObservacao.setVisible(false);
					btnSalvarObservacao.setVisible(true);
					btnCancelarObservacao.setVisible(true);
					fTxtObservacao.setEditable(true);
					fTxtObservacao.requestFocus();
				}
			}
		});
		btnEditarObservacao.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnEditarObservacao.setBounds(391, 344, 164, 20);
		btnEditarObservacao.setIcon(icones.getIcone_editar());
		contentPane.add(btnEditarObservacao);

		btnExcluirObservacao = new JButton("Excluir Observa\u00E7\u00E3o");
		btnExcluirObservacao.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickExcluirObservacao) {
				if (btnExcluirObservacao.isEnabled()) {

					int opcao = JOptionPane.showConfirmDialog(lblOrcamentosDoCliente,
							"Deseja remover a observa��o do or�amento N� " + orcamento_selecionado.getId_orcamento()
									+ " ?",
							"Exclus�o de observa��o.", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

					Boolean flag = opcao == JOptionPane.YES_OPTION;

					if (flag) {
						OrcamentoDAO orcamento_dao = new OrcamentoDAO();
						if (orcamento_dao.deleta_observacao(orcamento_selecionado)) {
							JOptionPane.showMessageDialog(
									lblOrcamentosDoCliente, "A observa��o do or�amento N� "
											+ orcamento_selecionado.getId_orcamento() + " " + "foi removida.",
									"Exclus�o de observa��o.", JOptionPane.ERROR_MESSAGE);
							orcamento_selecionado.setObservacao(null);
							tabelaOrcamentos.clearSelection();
						}
					}
				}
			}
		});
		btnExcluirObservacao.setEnabled(false);
		btnExcluirObservacao.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnExcluirObservacao.setBounds(565, 344, 172, 20);
		btnExcluirObservacao.setIcon(icones.getIcone_excluir());
		contentPane.add(btnExcluirObservacao);
		btnSalvarObservacao.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickSalvarObservacao) {
				if (!fTxtObservacao.getText().trim().isEmpty()) {
					orcamento_selecionado.setObservacao(fTxtObservacao.getText().trim());

					for (Orcamento orcamento : orcamentos_cliente) {
						if (orcamento.getId_orcamento() == orcamento_selecionado.getId_orcamento()) {
							orcamento.setObservacao(orcamento_selecionado.getObservacao());
							break;
						}
					}
					OrcamentoDAO orcamento_dao = new OrcamentoDAO();
					if (orcamento_dao.salva_observacao(orcamento_selecionado)) {
						JOptionPane.showMessageDialog(lblOrcamentosDoCliente,
								"Observa��o salva no or�amento N� " + orcamento_selecionado.getId_orcamento() + ".",
								"Observa��o do or�amento.", JOptionPane.NO_OPTION);
						tabelaOrcamentos.clearSelection();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Imposs�vel salvar a observa��o! "
									+ "\nCaso desejar remov�-la, utilize o bot�o excluir observa��o.",
							"Observa��o vazia!", JOptionPane.WARNING_MESSAGE);
					fTxtObservacao.setText(orcamento_selecionado.getObservacao());
					fTxtObservacao.setEditable(false);
				}
				btnSalvarObservacao.setVisible(false);
				btnCancelarObservacao.setVisible(false);

				btnEditarObservacao.setVisible(true);
				btnExcluirObservacao.setVisible(true);
			}
		});

		btnSalvarObservacao.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSalvarObservacao.setBounds(420, 337, 164, 20);
		btnSalvarObservacao.setIcon(icones.getIcone_salvar());
		btnSalvarObservacao.setVisible(false);
		contentPane.add(btnSalvarObservacao);
		btnCancelarObservacao.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancelarObservacao) {
				btnEditarObservacao.setVisible(true);
				btnExcluirObservacao.setVisible(true);
				btnSalvarObservacao.setVisible(false);
				btnCancelarObservacao.setVisible(false);
				fTxtObservacao.setEditable(false);
			}
		});

		btnCancelarObservacao.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCancelarObservacao.setBounds(594, 337, 135, 20);
		btnCancelarObservacao.setIcon(icones.getIcone_cancelar());
		btnCancelarObservacao.setVisible(false);
		contentPane.add(btnCancelarObservacao);

		btnFaturar = new JButton("Faturamento");
		btnFaturar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnFaturar.setEnabled(false);
		btnFaturar.setIcon(icones.getIcone_dinheiro());
		btnFaturar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickFaturar) {
				if (btnFaturar.isEnabled()) {
					Faturamento faturamento = new Faturamento(getOrcamentosDoCliente(), orcamento_selecionado);
					faturamento.abrir_faturamento(faturamento);
				}
			}
		});
		btnFaturar.setBounds(594, 157, 143, 29);
		contentPane.add(btnFaturar);

		btnImprimir = new JButton("Imprimir");
		btnImprimir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickImprimirOrcamento) {
				if (btnImprimir.isEnabled()) {
					Gera_pdf gera_pdf = new Gera_pdf();
					gera_pdf.monta_pdf_orcamento(orcamento_selecionado);
				}
			}
		});
		btnImprimir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnImprimir.setEnabled(false);
		btnImprimir.setBounds(461, 157, 122, 29);
		btnImprimir.setIcon(icones.getIcone_impressora());
		contentPane.add(btnImprimir);

	}

	// Configurando largura das colunas da tabela de or�amentos
	public void ConfiguraLarguraColunaTabelaOrcamento(JTable tabela) {
		tabela.getColumnModel().getColumn(0).setPreferredWidth(50); // N�mero do or�amento.
		tabela.getColumnModel().getColumn(1).setPreferredWidth(150); // Nome do cliente
		tabela.getColumnModel().getColumn(2).setPreferredWidth(60); // Quantidade de itens.
		tabela.getColumnModel().getColumn(3).setPreferredWidth(90); // Total mercadorias.
		tabela.getColumnModel().getColumn(4).setPreferredWidth(90); // Desconto.
		tabela.getColumnModel().getColumn(5).setPreferredWidth(90); // Frete.
		tabela.getColumnModel().getColumn(6).setPreferredWidth(90); // Valor total.
		tabela.getColumnModel().getColumn(7).setPreferredWidth(60); // Faturado.
		tabela.getColumnModel().getColumn(8).setPreferredWidth(80); // Data inclus�o.

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelo_tabela_orcamentos);
		tabelaOrcamentos.setRowSorter(sorter);

		sorter.setComparator(3, spv);
		sorter.setComparator(4, spv);
		sorter.setComparator(5, spv);
		sorter.setComparator(6, spv);
	}

	public void alimenta_lt_clientes(String forma_pesquisa, String texto, ArrayList<Cliente> lista_clientes) {

		String tipo_busca = forma_pesquisa;

		String texto_buscado;
		if (texto == null) {
			texto_buscado = "%";
		} else {
			texto_buscado = texto;
		}

		ClienteDAO cliente_dao = new ClienteDAO();
		cliente_dao.alimenta_lt_clientes(tipo_busca, texto_buscado, list_model_clientes, lista_clientes);

		if (!list_model_clientes.isEmpty()) {
			scrollPaneLtClientes.setVisible(true);
		} else {
			scrollPaneLtClientes.setVisible(false);
		}

		ltClientes.setModel(list_model_clientes);
	}

	public ArrayList<Orcamento> alimentar_lista_orcamento(ArrayList<Orcamento> orcamentos, Cliente cliente) {
		orcamentos.clear();

		// Verifica se foi passado n�mero do or�amento.
		String numero_orcamento;
		if (fTxtPesquisaOrcamento.getText().trim().isEmpty()) {
			numero_orcamento = null;
		} else {
			numero_orcamento = fTxtPesquisaOrcamento.getText().trim();
		}

		OrcamentoDAO orcamento_dao = new OrcamentoDAO();
		orcamentos = orcamento_dao.listar_orcamentos_do_cliente(orcamentos_cliente, cliente, numero_orcamento, 150,
				checkBoxFaturado.isSelected());

		modelo_tabela_orcamentos.fireTableDataChanged();
		return orcamentos;
	}

	public Orcamento busca_orcamento_selecionado(Integer numero_do_orcamento) {

		produtos_do_orcamento.clear();

		for (Orcamento orcamento : orcamentos_cliente) {
			if (orcamento.getId_orcamento() == numero_do_orcamento) {
				return orcamento;
			}
		}

		return null;
	}

	public Orcamentos_do_cliente getOrcamentosDoCliente() {
		return this;
	}
}
