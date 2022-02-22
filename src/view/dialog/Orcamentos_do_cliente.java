package view.dialog;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
import entities.orcamentos.Orcamento;
import entities.orcamentos.Produto_Orcamento;
import icons.Icones;
import pdf.Gera_pdf;
import tables.tableModels.ModeloTabelaOrcamentos;
import tables.tableRenders.Render_tabela_orcamentos;
import tables.tableSorters.SorterMonetario;
import view.panels.Panel_orcamento;
import javax.swing.JTextField;

public class Orcamentos_do_cliente extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblOrcamentos_do_realizados;
	private JSeparator separador_orcamentos;
	private JScrollPane scrollPaneOrcamentos;
	private JLabel lblPesquisarPorOrcamento;
	private JComboBox<String> cbxTipoPesquisaOrcamento;
	private JFormattedTextField fTxtPesquisaOrcamento = new JFormattedTextField();
	private DefaultListModel<Cliente> list_model_clientes = new DefaultListModel<Cliente>();
	private ArrayList<Cliente> lista_clientes = new ArrayList<Cliente>();
	private ArrayList<Orcamento> orcamentos_cliente = new ArrayList<Orcamento>();
	private ModeloTabelaOrcamentos modelo_tabela_orcamentos = new ModeloTabelaOrcamentos(orcamentos_cliente);
	private JTable tabelaOrcamentos = new JTable(modelo_tabela_orcamentos);
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
	private Render_tabela_orcamentos render = new Render_tabela_orcamentos();
	private JLabel lgPagos;
	private JLabel lgPendentes;
	private JLabel lblVencidos;
	private JLabel lgParcDif;
	private JTextField txtPendentes = new JTextField();
	private JTextField txtPagos = new JTextField();
	private JTextField txtVencidos = new JTextField();
	private JTextField txtParcDif = new JTextField();
	private JButton btnReload;

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
		alimentar_lista_orcamento();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent fechamentoDaJanela) {
				panel_orcamento.limpar_campos();
			}
		});
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 814, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setModal(true);

		ltClientes = new JList<Cliente>();
		ltClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickListaClientes) {

				scrollPaneLtClientes.setVisible(false);
				cliente_selecionado = ltClientes.getSelectedValue();
				fTxtPesquisaCliente.setText(cliente_selecionado.getNome());

				alimentar_lista_orcamento();
			}
		});
		ltClientes.setBounds(197, 104, 514, 70);

		scrollPaneLtClientes = new JScrollPane(ltClientes);
		scrollPaneLtClientes.setBounds(198, 96, 587, 55);
		scrollPaneLtClientes.setVisible(false);
		contentPane.setLayout(null);

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
		txtParcDif.setToolTipText("Or\u00E7amentos com o total das parcelas diferente do total do or\u00E7amento.");

		txtParcDif.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtParcDif.setEditable(false);
		txtParcDif.setColumns(10);
		txtParcDif.setBounds(696, 321, 89, 20);
		contentPane.add(txtParcDif);
		txtVencidos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtVencidos.setToolTipText("Or\u00E7amentos com 1 ou mais parcelas n\u00E3o pagas e vencidas.");

		txtVencidos.setEditable(false);
		txtVencidos.setColumns(10);
		txtVencidos.setBounds(457, 321, 95, 20);
		contentPane.add(txtVencidos);
		txtPendentes.setToolTipText("Or\u00E7amentos com 1 ou mais parcelas n\u00E3o pagas.");

		txtPendentes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPendentes.setEditable(false);
		txtPendentes.setColumns(10);
		txtPendentes.setBounds(267, 321, 104, 20);
		contentPane.add(txtPendentes);
		txtPagos.setToolTipText("Or\u00E7amentos com todas as parcelas pagas.");

		txtPagos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPagos.setEditable(false);
		txtPagos.setColumns(10);
		txtPagos.setBounds(59, 322, 100, 20);
		contentPane.add(txtPagos);

		scrollPaneOrcamentos = new JScrollPane(tabelaOrcamentos);
		scrollPaneOrcamentos.setBounds(12, 205, 773, 111);
		contentPane.add(scrollPaneOrcamentos);
		contentPane.add(scrollPaneLtClientes);

		lblOrcamentos_do_realizados = new JLabel("Or\u00E7amentos Realizados");
		lblOrcamentos_do_realizados.setBounds(267, 10, 287, 29);
		lblOrcamentos_do_realizados.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrcamentos_do_realizados.setFont(new Font("Tahoma", Font.BOLD, 24));
		contentPane.add(lblOrcamentos_do_realizados);

		separador_orcamentos = new JSeparator();
		separador_orcamentos.setBounds(10, 50, 775, 9);
		contentPane.add(separador_orcamentos);

		lblPesquisarPorOrcamento = new JLabel("Pesquisar por");
		lblPesquisarPorOrcamento.setBounds(10, 128, 89, 20);
		lblPesquisarPorOrcamento.setToolTipText("");
		lblPesquisarPorOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblPesquisarPorOrcamento);

		cbxTipoPesquisaOrcamento = new JComboBox<String>();
		cbxTipoPesquisaOrcamento.setBounds(97, 127, 97, 22);
		cbxTipoPesquisaOrcamento.setModel(new DefaultComboBoxModel<String>(new String[] { "N\u00FAmero" }));
		cbxTipoPesquisaOrcamento.setSelectedIndex(0);
		cbxTipoPesquisaOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(cbxTipoPesquisaOrcamento);

		fTxtPesquisaOrcamento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaPesquisaOrcamento) {
				alimentar_lista_orcamento();
			}
		});
		fTxtPesquisaOrcamento.setBounds(202, 129, 395, 20);
		fTxtPesquisaOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtPesquisaOrcamento.setFocusLostBehavior(JFormattedTextField.PERSIST);
		contentPane.add(fTxtPesquisaOrcamento);

		lblCliente = new JLabel("Cliente");
		lblCliente.setBounds(10, 60, 48, 20);
		lblCliente.setToolTipText("");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(lblCliente);

		separador_cliente = new JSeparator();
		separador_cliente.setBounds(62, 70, 723, 9);
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
		fTxtPesquisaCliente.setBounds(198, 78, 587, 20);
		fTxtPesquisaCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaCliente) {
				if (fTxtPesquisaCliente.getText().trim().isEmpty()) {

					cliente_selecionado = null;
					scrollPaneLtClientes.setVisible(false);

					alimentar_lista_orcamento();
				} else {
					alimenta_lt_clientes(cbxTipoPesquisaCliente.getSelectedItem().toString(),
							fTxtPesquisaCliente.getText().trim(), lista_clientes);
				}

			}
		});
		fTxtPesquisaCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtPesquisaCliente.setFocusLostBehavior(JFormattedTextField.PERSIST);
		contentPane.add(fTxtPesquisaCliente);

		separador_orcamento = new JSeparator();
		separador_orcamento.setBounds(91, 116, 694, 9);
		contentPane.add(separador_orcamento);

		lblOrcamento = new JLabel("Or\u00E7amento");
		lblOrcamento.setBounds(10, 106, 82, 20);
		lblOrcamento.setToolTipText("");
		lblOrcamento.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(lblOrcamento);

		checkBoxFaturado.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent clickSoFaturado) {
				alimentar_lista_orcamento();
			}
		});
		checkBoxFaturado.setBounds(603, 128, 144, 20);
		checkBoxFaturado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(checkBoxFaturado);

		separador_orcamentos1 = new JSeparator();
		separador_orcamentos1.setBounds(12, 193, 307, 9);
		contentPane.add(separador_orcamentos1);

		lblOrcamentosDoCliente = new JLabel("Or\u00E7amentos do cliente");
		lblOrcamentosDoCliente.setToolTipText("");
		lblOrcamentosDoCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblOrcamentosDoCliente.setBounds(328, 182, 160, 20);
		contentPane.add(lblOrcamentosDoCliente);

		separador_orcamentos2 = new JSeparator();
		separador_orcamentos2.setBounds(489, 193, 296, 9);
		contentPane.add(separador_orcamentos2);

		fTxtObservacao = new JFormattedTextField();
		fTxtObservacao.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtObservacao.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtObservacao.setEditable(false);
		fTxtObservacao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtObservacao.setBounds(10, 380, 775, 24);
		contentPane.add(fTxtObservacao);

		lblObservacoes = new JLabel("Observa\u00E7\u00E3o");
		lblObservacoes.setToolTipText("");
		lblObservacoes.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblObservacoes.setBounds(12, 353, 89, 20);
		contentPane.add(lblObservacoes);

		separador_observacoes = new JSeparator();
		separador_observacoes.setBounds(97, 364, 333, 9);
		contentPane.add(separador_observacoes);

		btnExcluirOrcamento = new JButton("Excluir");
		btnExcluirOrcamento.setEnabled(false);
		btnExcluirOrcamento.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickExcluirOrcamento) {

				if (btnExcluirOrcamento.isEnabled()) {

					int opcao = JOptionPane.showConfirmDialog(btnExcluirOrcamento,
							"Deseja realmente excluir o orçamento abaixo?" + "\n\nOrçamento Nº:  "
									+ orcamento_selecionado.getId_orcamento() + "\nCliente: "
									+ orcamento_selecionado.getCliente().getNome() + "\nValor: "
									+ nf.format(orcamento_selecionado.getValor_total())
									+ "\n\nCASO EXCLUÍDO NÃO SERÁ POSSÍVEL RECUPERÁ-LO E TODOS OS DADOS DO ORÇAMENTO SERÃO REMOVIDOS PERMANENTIMENTE!",
							"Exclusão de orçamento.", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

					Boolean flag = opcao == JOptionPane.YES_OPTION;

					if (flag) {
						OrcamentoDAO orcamento_dao = new OrcamentoDAO();
						if (orcamento_dao.excluir_orcamento(orcamento_selecionado.getId_orcamento())) {
							JOptionPane.showMessageDialog(lblOrcamentosDoCliente,
									"Orçamento " + orcamento_selecionado.getId_orcamento() + " " + "excluído.",
									"Exclusão de orçamento", JOptionPane.ERROR_MESSAGE);
							orcamentos_cliente.remove(orcamento_selecionado);
							orcamento_selecionado = null;
							modelo_tabela_orcamentos.fireTableDataChanged();
							mostra_totais_por_status(orcamentos_cliente);
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
					configuracoes_do_sistema = conf_dao.busca_configuracoes(); // Buscando novamente as ocnfigurações do
																				// sistema.
					FaturamentoDAO faturamento_dao = new FaturamentoDAO();
					orcamento_selecionado.setParcelas(faturamento_dao.lista_parcelas(orcamento_selecionado));
					Boolean flag;

					// Valida a configuração de alteração de orçamentos que possuem parcelas
					// lançadas
					if (orcamento_selecionado.getParcelas().size() != 0) {
						if (configuracoes_do_sistema.getAltera_orc().equals("SIM")) {
							flag = true;
						} else if (configuracoes_do_sistema.getAltera_orc().equals("PERGUNTAR")) {
							int opcao = JOptionPane.showConfirmDialog(lblOrcamentosDoCliente,
									"O orçamento selecionado já possui parcelas lançadas, deseja alterá-lo?",
									"Orçamento com parcelas salvas.", JOptionPane.YES_OPTION,
									JOptionPane.WARNING_MESSAGE);
							flag = opcao == JOptionPane.YES_OPTION;
						} else {
							flag = false;
							JOptionPane.showMessageDialog(lblOrcamentosDoCliente,
									"As configurações atuais não permitem alterar orçamentos que já possuem parcelas lançadas,\ncaso desejar alterar o orçamento selecionado, altere primeiro as configurações do sistema na aba 'Configurações' e libere esse tipo de alteração.",
									"Orçamento com parcelas salvas.", JOptionPane.WARNING_MESSAGE);
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
		btnEditarObservacao.setBounds(440, 357, 164, 20);
		btnEditarObservacao.setIcon(icones.getIcone_editar());
		contentPane.add(btnEditarObservacao);

		btnExcluirObservacao = new JButton("Excluir Observa\u00E7\u00E3o");
		btnExcluirObservacao.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickExcluirObservacao) {
				if (btnExcluirObservacao.isEnabled()) {

					int opcao = JOptionPane.showConfirmDialog(lblOrcamentosDoCliente,
							"Deseja remover a observação do orçamento Nº " + orcamento_selecionado.getId_orcamento()
									+ " ?",
							"Exclusão de observação.", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

					Boolean flag = opcao == JOptionPane.YES_OPTION;

					if (flag) {
						OrcamentoDAO orcamento_dao = new OrcamentoDAO();
						if (orcamento_dao.deleta_observacao(orcamento_selecionado)) {
							JOptionPane.showMessageDialog(
									lblOrcamentosDoCliente, "A observação do orçamento N° "
											+ orcamento_selecionado.getId_orcamento() + " " + "foi removida.",
									"Exclusão de observação.", JOptionPane.ERROR_MESSAGE);
							orcamento_selecionado.setObservacao(null);
							tabelaOrcamentos.clearSelection();
						}
					}
				}
			}
		});
		btnExcluirObservacao.setEnabled(false);
		btnExcluirObservacao.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnExcluirObservacao.setBounds(614, 357, 172, 20);
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
								"Observação salva no orçamento Nº " + orcamento_selecionado.getId_orcamento() + ".",
								"Observação do orçamento.", JOptionPane.NO_OPTION);
						tabelaOrcamentos.clearSelection();
						fTxtObservacao.setEditable(false);
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Impossível salvar a observação! "
									+ "\nCaso desejar removê-la, utilize o botão excluir observação.",
							"Observação vazia!", JOptionPane.WARNING_MESSAGE);
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
		btnSalvarObservacao.setBounds(469, 350, 164, 20);
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
		btnCancelarObservacao.setBounds(643, 350, 135, 20);
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
					faturamento.setLocationRelativeTo(btnFaturar);
					faturamento.abrir_faturamento(faturamento);
				}
			}
		});
		btnFaturar.setBounds(635, 160, 150, 29);
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
		btnImprimir.setBounds(503, 160, 122, 29);
		btnImprimir.setIcon(icones.getIcone_impressora());
		contentPane.add(btnImprimir);

		lgPagos = new JLabel("pagos");
		lgPagos.setForeground(new Color(0, 128, 0));
		lgPagos.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lgPagos.setBounds(13, 324, 46, 20);
		contentPane.add(lgPagos);

		lgPendentes = new JLabel("pendentes");
		lgPendentes.setForeground(new Color(0, 0, 255));
		lgPendentes.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lgPendentes.setBounds(190, 323, 78, 20);
		contentPane.add(lgPendentes);

		lblVencidos = new JLabel("vencidos");

		lblVencidos.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblVencidos.setForeground(Color.RED);
		lblVencidos.setBounds(393, 323, 64, 20);
		contentPane.add(lblVencidos);

		lgParcDif = new JLabel("Parc. <> Tot.Orc.");
		lgParcDif.setForeground(new Color(128, 0, 128));
		lgParcDif.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lgParcDif.setBounds(575, 323, 122, 20);
		contentPane.add(lgParcDif);
		
		btnReload = new JButton();
		btnReload.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickReload) {
				fTxtPesquisaCliente.setText(null);
				cliente_selecionado = null;
				fTxtPesquisaOrcamento.setText(null);
				alimentar_lista_orcamento();
			}
		});
		btnReload.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReload.setBounds(753, 128, 32, 20);
		btnReload.setIcon(icones.getIcone_reload());
		contentPane.add(btnReload);

	}

	// Configurando largura das colunas da tabela de orçamentos
	public void ConfiguraLarguraColunaTabelaOrcamento(JTable tabela) {
		tabela.getColumnModel().getColumn(0).setPreferredWidth(42); // Número do orçamento.
		tabela.getColumnModel().getColumn(1).setPreferredWidth(150); // Nome do cliente
		tabela.getColumnModel().getColumn(2).setPreferredWidth(60); // Quantidade de itens.
		tabela.getColumnModel().getColumn(3).setPreferredWidth(90); // Total mercadorias.
		tabela.getColumnModel().getColumn(4).setPreferredWidth(90); // Desconto final.
		tabela.getColumnModel().getColumn(5).setPreferredWidth(90); // Desconto Produto.
		tabela.getColumnModel().getColumn(6).setPreferredWidth(90); // Frete.
		tabela.getColumnModel().getColumn(7).setPreferredWidth(90); // Valor total.
		tabela.getColumnModel().getColumn(8).setPreferredWidth(68); // Status.
		tabela.getColumnModel().getColumn(9).setPreferredWidth(55); // Quantidade de parcelas
		tabela.getColumnModel().getColumn(10).setPreferredWidth(80); // Data inclusão.

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelo_tabela_orcamentos);
		tabelaOrcamentos.setRowSorter(sorter);

		sorter.setComparator(3, spv);
		sorter.setComparator(4, spv);
		sorter.setComparator(5, spv);
		sorter.setComparator(6, spv);
		sorter.setComparator(7, spv);

		tabela.setDefaultRenderer(Object.class, render);
		tabela.getColumnModel().getColumn(0).setCellRenderer(render);
		tabela.getColumnModel().getColumn(1).setCellRenderer(render);
		tabela.getColumnModel().getColumn(2).setCellRenderer(render);
		tabela.getColumnModel().getColumn(3).setCellRenderer(render);
		tabela.getColumnModel().getColumn(4).setCellRenderer(render);
		tabela.getColumnModel().getColumn(5).setCellRenderer(render);
		tabela.getColumnModel().getColumn(6).setCellRenderer(render);
		tabela.getColumnModel().getColumn(7).setCellRenderer(render);
		tabela.getColumnModel().getColumn(8).setCellRenderer(render);
		tabela.getColumnModel().getColumn(9).setCellRenderer(render);

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

	public void alimentar_lista_orcamento() {
		
		orcamentos_cliente.clear();

		// Verifica se foi passado número do orçamento.
		String numero_orcamento;
		if (fTxtPesquisaOrcamento.getText().trim().isEmpty()) {
			numero_orcamento = null;
		} else {
			numero_orcamento = fTxtPesquisaOrcamento.getText().trim();
		}

		OrcamentoDAO orcamento_dao = new OrcamentoDAO();
		orcamentos_cliente = orcamento_dao.listar_orcamentos_do_cliente(orcamentos_cliente, cliente_selecionado, numero_orcamento, 150,
				checkBoxFaturado.isSelected());

		modelo_tabela_orcamentos.fireTableDataChanged();

		mostra_totais_por_status(orcamentos_cliente);
	}


	public void mostra_totais_por_status(ArrayList<Orcamento> orcamentos) {
		Integer pagos = 0;
		Integer pendentes = 0;
		Integer vencidos = 0;
		Integer parc_dif = 0;

		if (orcamentos.size() > 0) {
			for (int n = 0; n < tabelaOrcamentos.getRowCount(); n++) {
				switch (tabelaOrcamentos.getValueAt(n, 8).toString()) {
				case "PAGO":
					pagos ++;
					break;
				case "PENDENTE":
					pendentes ++;
					break;
				case "VENCIDO":
					vencidos ++;
					break;
				case "PC.<>TOT.":
					parc_dif++;
				}

			}
		}
			txtPagos.setText(pagos.toString());
			txtPendentes.setText(pendentes.toString());
			txtVencidos.setText(vencidos.toString());
			txtParcDif.setText(parc_dif.toString());
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
