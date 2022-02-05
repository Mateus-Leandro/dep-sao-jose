package view.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;

import com.toedter.calendar.JDateChooser;

import dao.ConfiguracaoDAO;
import dao.FaturamentoDAO;
import dao.FormaPagamentoDAO;
import entities.configuracoes.Configuracoes;
import entities.financeiro.Forma_pagamento;
import entities.financeiro.Parcela;
import entities.orcamentos.Orcamento;
import icons.Icones;
import tables.tableModels.ModeloTabelaParcelas;
import view.formatFields.FormataNumeral;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Faturamento extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel lblOrcamentos_do_cliente;
	private JSeparator separador_faturamento;
	private JLabel lblNumeroOrcamento;
	private JTextField txtNumeroOrcamento = new JTextField();
	private JLabel lblDataInclusao;
	private JTextField txtDataCriacao = new JTextField();
	private JLabel lblCliente;
	private JTextField txtCliente = new JTextField();
	private JLabel lblInformaesBsicas;
	private JSeparator separador_cliente;
	private JLabel lblPagamento;
	private JSeparator separador_pagamento;
	private JLabel lblDataFaturamento;
	private JTextField txtDataFaturamento;
	private JScrollPane scrollPaneParcelas;
	private JPanel panelTotalOrcamento;
	private JLabel lblTotalOrcamento;
	private JFormattedTextField fTxtValorParcela = new JFormattedTextField();
	private JLabel lblValorParcela;
	private JLabel lblDataVencimento;
	private JDateChooser jdcDataVencimento;
	private JButton btnInserirParcela;
	private Icones icones = new Icones();
	private JLabel lblFormaPagamento;
	private JComboBox<Forma_pagamento> cbxFormaPagamento = new JComboBox<Forma_pagamento>();
	private JLabel lblTotalParcelas;
	private JLabel lblValorEmAberto;
	private JLabel lblValorPago;
	private JTextField txtTotalOrcamento = new JTextField();
	private JButton btnEditarParcela;
	private JLabel lblNParc;
	private JFormattedTextField fTxtNumeroParcela = new JFormattedTextField();
	private JLabel lblDtPagt;
	private JDateChooser jdcDataPagamento;
	private JSeparator separador_totais;
	private JLabel lblTotais;
	private JButton btnMaisFormaPagamento;
	private JTextField txtValorAberto = new JTextField();
	private JTextField txtValorPago = new JTextField();
	private JButton btnConfirmarFaturamento = new JButton("Confirmar");
	private JTable tabelaParcelas;
	private NumberFormat nf = new DecimalFormat(",##0.00");
	private NumberFormat nf2 = new DecimalFormat("R$ ,##0.00");
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private JButton btnParcelarIgualmente = new JButton("Parcelar igualmente");
	private JFormattedTextField fTxtQuantidadeDeParcelas;
	private JLabel lblQuantidadeParcelas;
	private JButton btnParcelar;
	private JButton btnCancelarDivisao;
	private JButton btnExcluirParcela;
	private ModeloTabelaParcelas modelo_tabela;
	private ListSelectionModel lsm;
	private Orcamento orcamento;
	private ArrayList<Forma_pagamento> formas_pagamento = new ArrayList<Forma_pagamento>();
	private JButton btnLimpaDataVencimento;
	private Double total_parcelas;
	private Parcela parcela_selecionada;
	private JButton btnConfirmarEdicao;
	private JButton btnCancelarEdicao;
	private JButton btnLimpaDataPagamento;
	private JTextField txtTotalParcelas = new JTextField();
	private Double valor_em_aberto;
	private Double valor_pago;
	private Double valor_digitado;
	private JButton btnCancelarFaturamento = new JButton("Cancelar");
	private Configuracoes configuracoes_do_sistema = new ConfiguracaoDAO().busca_configuracoes();
	private JLabel lblObg_valorParcela;
	private JLabel lblObg_formaPagamento;
	private JLabel lblObg_dataVencimento;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Faturamento dialog = new Faturamento(null, null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Faturamento(Orcamentos_do_cliente orcamentos_do_cliente, Orcamento orcamento_passado) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent clickFecharTela) {
				aviso_fecha_tela();
			}
		});
		this.orcamento = orcamento_passado;
		mostra_dados_orcamento(orcamento);
		modelo_tabela = new ModeloTabelaParcelas(orcamento.getParcelas());
		setBounds(100, 100, 680, 575);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setModal(true);
		setResizable(false);
		contentPanel.setLayout(null);

		tabelaParcelas = new JTable(modelo_tabela);
		tabelaParcelas.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		tabelaParcelas.setBounds(10, 210, 317, 116);
		tabelaParcelas.getTableHeader().setReorderingAllowed(false);
		tabelaParcelas.getTableHeader().setResizingAllowed(false);
		tabelaParcelas.setAutoResizeMode(tabelaParcelas.AUTO_RESIZE_OFF);
		ConfiguraLarguraColunaTabela(tabelaParcelas);
		tabelaParcelas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaParcelas.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent selecaoLinhaTabela) {

				lsm = (ListSelectionModel) selecaoLinhaTabela.getSource();
				if (!lsm.isSelectionEmpty()) {
					if (btnParcelarIgualmente.isEnabled()) {
						btnEditarParcela.setEnabled(true);
						btnExcluirParcela.setEnabled(true);
					}
				} else {
					btnEditarParcela.setEnabled(false);
					btnExcluirParcela.setEnabled(false);
				}
			}
		});

		MaskFormatter mascara_data = null;
		try {
			mascara_data = new MaskFormatter("##/##/##");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		btnInserirParcela = new JButton("");
		btnInserirParcela.setEnabled(false);
		btnInserirParcela.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickAdicionarParcela) {

				if (btnInserirParcela.isEnabled()) {
					Parcela nova_parcela = null;
					nova_parcela = monta_parcela();
					if (nova_parcela != null) {
						orcamento.getParcelas().add(nova_parcela);
						modelo_tabela.fireTableDataChanged();
						calcula_totais();
						mostra_totais();
						limpar_campos();
					}
				}
			}
		});

		btnCancelarEdicao = new JButton();
		btnCancelarEdicao.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancelarEdicao) {
				limpar_campos();
			}
		});
		btnCancelarEdicao.setToolTipText("Cancelar edi\u00E7\u00E3o");
		btnCancelarEdicao.setText("Cancelar");
		btnCancelarEdicao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelarEdicao.setEnabled(false);
		btnCancelarEdicao.setBounds(397, 269, 112, 23);
		btnCancelarEdicao.setIcon(icones.getIcone_cancelar());
		btnCancelarEdicao.setVisible(false);

		btnConfirmarFaturamento.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickConfirmarFaturamento) {
				if (btnConfirmarFaturamento.isEnabled()) {
					FaturamentoDAO faturamento_dao = new FaturamentoDAO();

					Boolean flag;
					if (orcamento.getValor_total().compareTo(total_parcelas) != 0
							&& configuracoes_do_sistema.getSalva_parc_dif().equals("PERGUNTAR")) {
						int opcao = JOptionPane.showConfirmDialog(jdcDataVencimento,
								"O total das parcelas está diferente do total do orçamento.\nDeseja salvar as parcelas?",
								"Total das parcelas", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

						flag = opcao == JOptionPane.YES_OPTION;
					} else {
						flag = true;
					}

					if (flag) {
						if (faturamento_dao.salvar_parcelas(orcamento)) {
							JOptionPane.showMessageDialog(jdcDataVencimento, "Parcelas salvas corretamente.",
									"Parcelas do orçamento.", JOptionPane.NO_OPTION);

							if(orcamentos_do_cliente != null) {
								orcamentos_do_cliente.alimentar_lista_orcamento();
							}
							dispose();
						}
					}

				}
			}
		});
		btnConfirmarFaturamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnConfirmarFaturamento.setBounds(526, 498, 128, 26);
		btnConfirmarFaturamento.setIcon(icones.getIcone_ok());
		contentPanel.add(btnConfirmarFaturamento);

		fTxtNumeroParcela.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtNumeroParcela.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtNumeroParcela.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNumeroParcela.setEditable(false);
		fTxtNumeroParcela.setColumns(10);
		fTxtNumeroParcela.setBounds(67, 188, 36, 20);
		contentPanel.add(fTxtNumeroParcela);
		contentPanel.add(btnCancelarEdicao);

		btnConfirmarEdicao = new JButton();
		btnConfirmarEdicao.setEnabled(false);
		btnConfirmarEdicao.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickSalvaParcelaEditada) {
				if (btnConfirmarEdicao.isEnabled()) {
					salva_parcela_editada(monta_parcela());
					mostra_totais();
				}
			}
		});
		btnConfirmarEdicao.setText("Confirmar");
		btnConfirmarEdicao.setToolTipText("Salvar edi\u00E7\u00E3o");
		btnConfirmarEdicao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnConfirmarEdicao.setBounds(521, 269, 133, 23);
		btnConfirmarEdicao.setIcon(icones.getIcone_ok());
		btnConfirmarEdicao.setVisible(false);
		contentPanel.add(btnConfirmarEdicao);
		txtTotalParcelas.setToolTipText("Total de parcelas pagas e n\u00E3o pagas.");

		txtTotalParcelas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtTotalParcelas.setEditable(false);
		txtTotalParcelas.setColumns(10);
		txtTotalParcelas.setBounds(122, 445, 101, 20);
		contentPanel.add(txtTotalParcelas);

		btnInserirParcela.setToolTipText("Incluir parcela");
		btnInserirParcela.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnInserirParcela.setBounds(433, 221, 67, 23);
		btnInserirParcela.setIcon(icones.getIcone_mais());
		contentPanel.add(btnInserirParcela);

		jdcDataVencimento = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		jdcDataVencimento.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent clickAlteraDataVencimento) {

				if (valida_parcela()) {
					btnInserirParcela.setEnabled(true);
					btnConfirmarEdicao.setEnabled(true);
				} else {
					btnInserirParcela.setEnabled(false);
					btnConfirmarEdicao.setEnabled(false);
				}
			}
		});

		((JTextField) jdcDataVencimento.getDateEditor()).setEditable(false);
		jdcDataVencimento.setMinSelectableDate(new Date());
		jdcDataVencimento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jdcDataVencimento.setBounds(272, 230, 110, 20);
		contentPanel.add(jdcDataVencimento);

		jdcDataPagamento = new JDateChooser("dd/MM/yyyy", "##/##/#####", '_');
		jdcDataPagamento.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent clickAlteraDataPagamento) {

				// Verificando se é uma nova parcela ou a edição de uma parcela existente.
				if (fTxtNumeroParcela.getText().trim().isEmpty()) {

					jdcDataVencimento.setMinSelectableDate(jdcDataPagamento.getDate());

					if (jdcDataVencimento.getDate() != null && jdcDataPagamento.getDate() != null) {
						// Testando se a data de vencimento é menor que a data de pagamento.
						try {
							if (sdf.parse(((JTextField) jdcDataVencimento.getDateEditor()).getText())
									.before(sdf.parse(((JTextField) jdcDataPagamento.getDateEditor()).getText()))) {
								jdcDataVencimento.setDate(null);
							}
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		((JTextField) jdcDataPagamento.getDateEditor()).setEditable(false);
		jdcDataPagamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		jdcDataPagamento.setBounds(67, 230, 110, 20);
		contentPanel.add(jdcDataPagamento);

		scrollPaneParcelas = new JScrollPane(tabelaParcelas);
		scrollPaneParcelas.setBounds(10, 298, 643, 116);
		contentPanel.add(scrollPaneParcelas);

		lblOrcamentos_do_cliente = new JLabel("Faturamento do or\u00E7amento");
		lblOrcamentos_do_cliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrcamentos_do_cliente.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblOrcamentos_do_cliente.setBounds(173, 11, 327, 29);
		contentPanel.add(lblOrcamentos_do_cliente);

		separador_faturamento = new JSeparator();
		separador_faturamento.setBounds(10, 50, 643, 9);
		contentPanel.add(separador_faturamento);

		lblNumeroOrcamento = new JLabel("N\u00BA do or\u00E7amento");
		lblNumeroOrcamento.setToolTipText("");
		lblNumeroOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumeroOrcamento.setBounds(10, 95, 110, 20);
		contentPanel.add(lblNumeroOrcamento);
		txtNumeroOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 14));

		txtNumeroOrcamento.setEditable(false);
		txtNumeroOrcamento.setBounds(122, 95, 86, 20);
		contentPanel.add(txtNumeroOrcamento);
		txtNumeroOrcamento.setColumns(10);

		lblDataInclusao = new JLabel("Data de cria\u00E7\u00E3o");
		lblDataInclusao.setToolTipText("");
		lblDataInclusao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDataInclusao.setBounds(446, 95, 101, 20);
		contentPanel.add(lblDataInclusao);
		txtDataCriacao.setFont(new Font("Tahoma", Font.PLAIN, 14));

		txtDataCriacao.setEditable(false);
		txtDataCriacao.setColumns(10);
		txtDataCriacao.setBounds(545, 95, 108, 20);
		contentPanel.add(txtDataCriacao);

		lblCliente = new JLabel("Cliente");
		lblCliente.setToolTipText("");
		lblCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCliente.setBounds(10, 126, 45, 20);
		contentPanel.add(lblCliente);
		txtCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));

		txtCliente.setEditable(false);
		txtCliente.setColumns(10);
		txtCliente.setBounds(54, 126, 273, 20);
		contentPanel.add(txtCliente);

		lblInformaesBsicas = new JLabel("Informa\u00E7\u00F5es b\u00E1sicas");
		lblInformaesBsicas.setToolTipText("");
		lblInformaesBsicas.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblInformaesBsicas.setBounds(10, 64, 144, 20);
		contentPanel.add(lblInformaesBsicas);

		separador_cliente = new JSeparator();
		separador_cliente.setBounds(158, 74, 495, 9);
		contentPanel.add(separador_cliente);

		lblPagamento = new JLabel("Pagamento");
		lblPagamento.setToolTipText("");
		lblPagamento.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPagamento.setBounds(10, 157, 86, 20);
		contentPanel.add(lblPagamento);

		separador_pagamento = new JSeparator();
		separador_pagamento.setBounds(93, 167, 560, 9);
		contentPanel.add(separador_pagamento);

		lblDataFaturamento = new JLabel("Data de faturamento");
		lblDataFaturamento.setToolTipText("");
		lblDataFaturamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDataFaturamento.setBounds(409, 126, 133, 20);
		contentPanel.add(lblDataFaturamento);

		txtDataFaturamento = new JTextField();
		txtDataFaturamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDataFaturamento.setEditable(false);
		txtDataFaturamento.setColumns(10);
		txtDataFaturamento.setBounds(545, 126, 108, 20);
		contentPanel.add(txtDataFaturamento);

		panelTotalOrcamento = new JPanel();
		panelTotalOrcamento.setLayout(null);
		panelTotalOrcamento.setBorder(UIManager.getBorder("DesktopIcon.border"));
		panelTotalOrcamento.setBounds(10, 495, 253, 29);
		contentPanel.add(panelTotalOrcamento);

		lblTotalOrcamento = new JLabel("Total or\u00E7amento");
		lblTotalOrcamento.setForeground(Color.BLACK);
		lblTotalOrcamento.setBackground(Color.BLACK);
		lblTotalOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotalOrcamento.setBounds(5, 6, 128, 19);
		panelTotalOrcamento.add(lblTotalOrcamento);
		txtTotalOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 14));

		txtTotalOrcamento.setEditable(false);
		txtTotalOrcamento.setColumns(10);
		txtTotalOrcamento.setBounds(119, 5, 128, 20);
		panelTotalOrcamento.add(txtTotalOrcamento);

		fTxtValorParcela.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaValorParcela) {
				if (valida_parcela()) {
					btnInserirParcela.setEnabled(true);
					btnConfirmarEdicao.setEnabled(true);
				} else {
					btnInserirParcela.setEnabled(false);
					btnConfirmarEdicao.setEnabled(false);
				}
			}
		});
		fTxtValorParcela.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtValorParcela.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtValorParcela.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtValorParcela.setColumns(10);
		fTxtValorParcela.setBounds(154, 188, 109, 20);
		fTxtValorParcela.setDocument(new FormataNumeral(10, 2));
		contentPanel.add(fTxtValorParcela);

		lblValorParcela = new JLabel("Valor");
		lblValorParcela.setToolTipText("");
		lblValorParcela.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValorParcela.setBounds(122, 190, 36, 20);
		contentPanel.add(lblValorParcela);

		lblDataVencimento = new JLabel("Dt. Venc.");
		lblDataVencimento.setToolTipText("");
		lblDataVencimento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDataVencimento.setBounds(215, 230, 58, 20);
		contentPanel.add(lblDataVencimento);

		lblFormaPagamento = new JLabel("Forma de pagamento");
		lblFormaPagamento.setToolTipText("");
		lblFormaPagamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFormaPagamento.setBounds(284, 190, 133, 20);
		contentPanel.add(lblFormaPagamento);

		cbxFormaPagamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxFormaPagamento.setBounds(419, 188, 195, 22);
		contentPanel.add(cbxFormaPagamento);
		alimenta_formas_pagamento();

		lblTotalParcelas = new JLabel("Total das parcelas");
		lblTotalParcelas.setForeground(new Color(0, 0, 0));
		lblTotalParcelas.setToolTipText("");
		lblTotalParcelas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotalParcelas.setBounds(10, 445, 112, 20);
		contentPanel.add(lblTotalParcelas);

		lblValorEmAberto = new JLabel("Valor em aberto");
		lblValorEmAberto.setForeground(new Color(255, 0, 0));
		lblValorEmAberto.setToolTipText("");
		lblValorEmAberto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValorEmAberto.setBounds(455, 445, 101, 20);
		contentPanel.add(lblValorEmAberto);

		lblValorPago = new JLabel("Valor pago");
		lblValorPago.setForeground(new Color(0, 128, 0));
		lblValorPago.setToolTipText("");
		lblValorPago.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValorPago.setBounds(254, 445, 67, 20);
		contentPanel.add(lblValorPago);

		btnEditarParcela = new JButton();
		btnEditarParcela.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEditarParcela) {
				if (btnEditarParcela.isEnabled()) {
					busca_parcela_selecionada();
					editar_parcela(parcela_selecionada);
				}
			}
		});
		btnEditarParcela.setEnabled(false);
		btnEditarParcela.setToolTipText("Editar parcela");
		btnEditarParcela.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEditarParcela.setBounds(510, 221, 67, 23);
		btnEditarParcela.setIcon(icones.getIcone_editar());
		contentPanel.add(btnEditarParcela);

		lblNParc = new JLabel("N\u00BA Parc.");
		lblNParc.setToolTipText("");
		lblNParc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNParc.setBounds(10, 190, 54, 20);
		contentPanel.add(lblNParc);

		lblDtPagt = new JLabel("Dt. pagt.");
		lblDtPagt.setToolTipText("");
		lblDtPagt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDtPagt.setBounds(10, 230, 58, 20);
		contentPanel.add(lblDtPagt);

		separador_totais = new JSeparator();
		separador_totais.setBounds(57, 425, 596, 9);
		contentPanel.add(separador_totais);

		lblTotais = new JLabel("Totais");
		lblTotais.setToolTipText("");
		lblTotais.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTotais.setBounds(10, 413, 45, 20);
		contentPanel.add(lblTotais);

		btnMaisFormaPagamento = new JButton();
		btnMaisFormaPagamento.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickMaisForma) {
				CadastroFormaPagamento cadastro_forma = new CadastroFormaPagamento(getTelaFaturamento(),
						formas_pagamento);
				cadastro_forma.setVisible(true);
			}
		});
		btnMaisFormaPagamento.setBounds(627, 188, 26, 22);
		btnMaisFormaPagamento.setIcon(icones.getIcone_mais());
		contentPanel.add(btnMaisFormaPagamento);

		btnExcluirParcela = new JButton();
		btnExcluirParcela.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickExcluirParcela) {
				if (btnExcluirParcela.isEnabled()) {
					busca_parcela_selecionada();

					String pagamento = null;
					if (parcela_selecionada.getData_pagamento() == null) {
						pagamento = "Parcela não paga.";
					} else {
						pagamento = sdf.format(parcela_selecionada.getData_pagamento());
					}
					lblTotalParcelas.setFont(new Font("Tahoma", Font.PLAIN, 14));

					int opcao = JOptionPane.showConfirmDialog(jdcDataVencimento,
							"Deseja excluir a parcela selecionada?" + "\nValor:  "
									+ nf2.format(parcela_selecionada.getValor_parcela()) + "\nData de pagamento:  "
									+ pagamento + "\nData de Vencimento: "
									+ sdf.format(parcela_selecionada.getData_vencimento()) + "\n\nATENÇÃO!"
									+ "\nAo excluir a parcela não será possível recuperá-la.",
							"Exclusão de parcelas.", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

					Boolean flag = opcao == JOptionPane.YES_OPTION;

					if (flag) {
						excluir_parcela(parcela_selecionada);
						calcula_totais();
						mostra_totais();
						fTxtValorParcela.setText(null);
						jdcDataPagamento.setDate(null);
						jdcDataVencimento.setDate(null);
						JOptionPane.showMessageDialog(jdcDataVencimento, "Parcela removida do orçamento.",
								"Exclusão de parcelas", JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		});
		btnExcluirParcela.setEnabled(false);
		btnExcluirParcela.setToolTipText("Excluir parcela");
		btnExcluirParcela.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExcluirParcela.setBounds(586, 221, 67, 23);
		btnExcluirParcela.setIcon(icones.getIcone_excluir());
		contentPanel.add(btnExcluirParcela);

		txtValorAberto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtValorAberto.setEditable(false);
		txtValorAberto.setColumns(10);
		txtValorAberto.setBounds(553, 445, 101, 20);
		contentPanel.add(txtValorAberto);

		txtValorPago.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtValorPago.setEditable(false);
		txtValorPago.setColumns(10);
		txtValorPago.setBounds(322, 445, 112, 20);
		contentPanel.add(txtValorPago);

		btnParcelarIgualmente.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickDividirParcelas) {
				parcelar();
			}
		});
		btnParcelarIgualmente
				.setToolTipText("Divide as parcelas em valores iguais de acordo com o total do or\u00E7amento.");
		btnParcelarIgualmente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnParcelarIgualmente.setBounds(10, 264, 179, 23);
		btnParcelarIgualmente.setIcon(icones.getIcone_parcelas());
		contentPanel.add(btnParcelarIgualmente);

		fTxtQuantidadeDeParcelas = new JFormattedTextField();
		fTxtQuantidadeDeParcelas.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaQuantidadeParcelas) {
				valida_parcelamento();
			}
		});
		fTxtQuantidadeDeParcelas.setDocument(new FormataNumeral(2, 0));
		fTxtQuantidadeDeParcelas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtQuantidadeDeParcelas.setBounds(345, 267, 26, 20);
		fTxtQuantidadeDeParcelas.setVisible(false);
		contentPanel.add(fTxtQuantidadeDeParcelas);

		lblQuantidadeParcelas = new JLabel("Quantidade de parcelas");
		lblQuantidadeParcelas.setToolTipText("");
		lblQuantidadeParcelas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuantidadeParcelas.setBounds(199, 267, 144, 20);
		lblQuantidadeParcelas.setVisible(false);
		contentPanel.add(lblQuantidadeParcelas);

		btnParcelar = new JButton("Parcelar");
		btnParcelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickParcelar) {
				if (btnParcelar.isEnabled()) {
					parcelar_igualmente(Integer.parseInt(fTxtQuantidadeDeParcelas.getText().trim()),
							orcamento.getValor_total() - total_parcelas);
					calcula_totais();
					mostra_totais();
					cancelar_parcelar_igualmente();
				}
			}
		});
		btnParcelar.setEnabled(false);
		btnParcelar.setToolTipText("");
		btnParcelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnParcelar.setBounds(545, 264, 108, 23);
		btnParcelar.setVisible(false);
		btnParcelar.setIcon(icones.getIcone_ok());
		contentPanel.add(btnParcelar);

		btnCancelarDivisao = new JButton("Cancelar");
		btnCancelarDivisao.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancelarParcelamentoIgual) {
				cancelar_parcelar_igualmente();
			}
		});
		btnCancelarDivisao.setToolTipText("");
		btnCancelarDivisao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelarDivisao.setBounds(419, 264, 112, 23);
		btnCancelarDivisao.setIcon(icones.getIcone_cancelar());
		btnCancelarDivisao.setVisible(false);
		contentPanel.add(btnCancelarDivisao);

		btnLimpaDataVencimento = new JButton();
		btnLimpaDataVencimento.setToolTipText("Limpar datas");
		btnLimpaDataVencimento.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickLimpaDataVencimento) {
				if (btnLimpaDataVencimento.isEnabled()) {
					jdcDataVencimento.setDate(null);
				}
			}
		});
		btnLimpaDataVencimento.setBounds(392, 231, 26, 20);
		btnLimpaDataVencimento.setIcon(icones.getIcone_limpar());
		contentPanel.add(btnLimpaDataVencimento);

		btnLimpaDataPagamento = new JButton();
		btnLimpaDataPagamento.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickLimpaDataPagamento) {
				if (btnLimpaDataPagamento.isEnabled()) {
					jdcDataPagamento.setDate(null);
				}
			}
		});
		btnLimpaDataPagamento.setToolTipText("Limpar datas");
		btnLimpaDataPagamento.setBounds(181, 230, 26, 20);
		btnLimpaDataPagamento.setIcon(icones.getIcone_limpar());
		contentPanel.add(btnLimpaDataPagamento);

		btnCancelarFaturamento.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancelarFaturamento) {
				if (btnCancelarFaturamento.isEnabled()) {
					aviso_fecha_tela();
				}
			}
		});
		btnCancelarFaturamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelarFaturamento.setBounds(388, 498, 128, 26);
		btnCancelarFaturamento.setIcon(icones.getIcone_cancelar());
		contentPanel.add(btnCancelarFaturamento);

		lblObg_valorParcela = new JLabel("*");
		lblObg_valorParcela.setForeground(Color.RED);
		lblObg_valorParcela.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObg_valorParcela.setBounds(263, 199, 20, 15);
		contentPanel.add(lblObg_valorParcela);

		lblObg_formaPagamento = new JLabel("*");
		lblObg_formaPagamento.setForeground(Color.RED);
		lblObg_formaPagamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObg_formaPagamento.setBounds(614, 202, 20, 15);
		contentPanel.add(lblObg_formaPagamento);

		lblObg_dataVencimento = new JLabel("*");
		lblObg_dataVencimento.setForeground(Color.RED);
		lblObg_dataVencimento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObg_dataVencimento.setBounds(382, 242, 20, 15);
		contentPanel.add(lblObg_dataVencimento);

	}

	// ------- Funções ------

	public void ConfiguraLarguraColunaTabela(JTable tabela_produtos_inclusos) {
		tabelaParcelas.getColumnModel().getColumn(0).setPreferredWidth(80); // Número da parcela
		tabelaParcelas.getColumnModel().getColumn(1).setPreferredWidth(120); // Valor
		tabelaParcelas.getColumnModel().getColumn(2).setPreferredWidth(200); // Forma de pagamento
		tabelaParcelas.getColumnModel().getColumn(3).setPreferredWidth(120); // Data pagamento
		tabelaParcelas.getColumnModel().getColumn(4).setPreferredWidth(120); // Data Vencimento
	}

	public void mostra_dados_orcamento(Orcamento orcamento) {
		txtCliente.setText(orcamento.getCliente().getNome());
		txtDataCriacao.setText(sdf.format(orcamento.getData_inclusao()));
		txtNumeroOrcamento.setText(orcamento.getId_orcamento().toString());
		txtTotalOrcamento.setText(nf2.format(orcamento.getValor_total()));

		FaturamentoDAO faturamento_dao = new FaturamentoDAO();
		orcamento.setParcelas(faturamento_dao.lista_parcelas(orcamento));
		calcula_totais();
		mostra_totais();
	}

	public ArrayList<Forma_pagamento> alimenta_formas_pagamento() {

		formas_pagamento.clear();
		cbxFormaPagamento.removeAllItems();
		FormaPagamentoDAO forma_pagamento_dao = new FormaPagamentoDAO();
		formas_pagamento = forma_pagamento_dao.listar_formas_pagamento(formas_pagamento);

		for (Forma_pagamento form : formas_pagamento) {
			Forma_pagamento forma = new Forma_pagamento(form.getCodigo(), form.getDescricao());
			cbxFormaPagamento.addItem(forma);
		}

		return formas_pagamento;
	}

	public void parcelar() {
		if (btnParcelarIgualmente.isEnabled()) {
			fTxtQuantidadeDeParcelas.setVisible(true);
			lblQuantidadeParcelas.setVisible(true);
			btnCancelarDivisao.setVisible(true);
			btnParcelar.setVisible(true);
			btnParcelarIgualmente.setEnabled(false);
			btnInserirParcela.setEnabled(false);
			btnEditarParcela.setEnabled(false);
			btnExcluirParcela.setEnabled(false);
			fTxtValorParcela.setEnabled(false);
			jdcDataVencimento.setEnabled(false);
			jdcDataPagamento.setEnabled(false);
			jdcDataVencimento.setDate(null);
			jdcDataPagamento.setDate(null);
			fTxtValorParcela.setText(null);
			btnLimpaDataPagamento.setEnabled(false);
			btnLimpaDataVencimento.setEnabled(false);

			tabelaParcelas.clearSelection();
			parcela_selecionada = null;

			fTxtQuantidadeDeParcelas.requestFocus();
		}

	}

	public void cancelar_parcelar_igualmente() {
		fTxtQuantidadeDeParcelas.setVisible(false);
		fTxtValorParcela.setEnabled(true);
		lblQuantidadeParcelas.setVisible(false);
		jdcDataPagamento.setEnabled(true);
		jdcDataVencimento.setEnabled(true);
		btnParcelarIgualmente.setEnabled(true);
		btnCancelarDivisao.setVisible(false);
		btnParcelar.setVisible(false);
		btnParcelar.setEnabled(false);
		btnLimpaDataPagamento.setEnabled(true);
		btnLimpaDataVencimento.setEnabled(true);
		tabelaParcelas.clearSelection();
		fTxtQuantidadeDeParcelas.setText(null);
		limpar_campos();
	}

	public Faturamento getTelaFaturamento() {
		return this;
	}

	public Parcela monta_parcela() {
		Parcela parcela = new Parcela();
		Date data_pagamento;

		if (jdcDataPagamento.getDate() == null) {
			data_pagamento = null;
		} else {
			try {
				data_pagamento = sdf.parse(((JTextField) jdcDataPagamento.getDateEditor()).getText());
			} catch (ParseException e) {
				data_pagamento = null;
				e.printStackTrace();
			}
		}

		try {
			parcela.setValor_parcela(nf.parse(fTxtValorParcela.getText().trim()).doubleValue());
			parcela.setData_pagamento(data_pagamento);
			parcela.setData_vencimento(sdf.parse(((JTextField) jdcDataVencimento.getDateEditor()).getText()));
			parcela.setForma_pagamento((Forma_pagamento) cbxFormaPagamento.getModel().getSelectedItem());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return parcela;
	}

	public void editar_parcela(Parcela parcela) {
		btnParcelarIgualmente.setVisible(false);
		btnInserirParcela.setVisible(false);
		btnExcluirParcela.setVisible(false);
		btnEditarParcela.setVisible(false);

		btnConfirmarEdicao.setVisible(true);
		btnConfirmarEdicao.setEnabled(true);

		btnCancelarEdicao.setVisible(true);
		btnCancelarEdicao.setEnabled(true);

		fTxtNumeroParcela.setText(tabelaParcelas.getValueAt(tabelaParcelas.getSelectedRow(), 0).toString());
		fTxtValorParcela.setText(nf.format(parcela.getValor_parcela()));
		cbxFormaPagamento.getModel().setSelectedItem(parcela.getForma_pagamento());

		jdcDataPagamento.setDate(parcela.getData_pagamento());
		jdcDataVencimento.setMinSelectableDate(null);
		jdcDataVencimento.setDate(parcela.getData_vencimento());
	}

	public Boolean excluir_parcela(Parcela parcela) {
		for (Parcela parc : orcamento.getParcelas()) {
			if (parc.equals(parcela)) {
				orcamento.getParcelas().remove(parc);
				modelo_tabela.fireTableDataChanged();
				parcela_selecionada = null;
				return true;
			}
		}
		return false;
	}

	public void limpar_campos() {
		fTxtNumeroParcela.setText(null);
		fTxtValorParcela.setText(null);
		jdcDataPagamento.setDate(null);
		jdcDataVencimento.setDate(null);
		btnParcelarIgualmente.setVisible(true);
		btnInserirParcela.setVisible(true);
		btnExcluirParcela.setVisible(true);
		btnEditarParcela.setVisible(true);

		btnConfirmarEdicao.setVisible(false);
		btnConfirmarEdicao.setEnabled(false);

		btnCancelarEdicao.setVisible(false);
		btnCancelarEdicao.setEnabled(false);

		tabelaParcelas.clearSelection();
		parcela_selecionada = null;
	}

	public void salva_parcela_editada(Parcela parcela) {
		for (Parcela parc : orcamento.getParcelas()) {
			if (parc.equals(parcela_selecionada)) {
				orcamento.getParcelas().set(orcamento.getParcelas().indexOf(parc), parcela);
				calcula_totais();
				txtTotalParcelas.setText(nf.format(total_parcelas));
				modelo_tabela.fireTableDataChanged();
				limpar_campos();
			}
		}
	}

	public Boolean valida_parcela() {
		try {
			if (cbxFormaPagamento.getSelectedIndex() == -1 || fTxtValorParcela.getText().trim().isEmpty()
					|| nf.parse(fTxtValorParcela.getText().trim().replace("R$ ", "")).doubleValue() < 0.01
					|| nf.parse(fTxtValorParcela.getText().trim().replace("R$ ", "")).doubleValue() > orcamento
							.getValor_total()
					|| jdcDataVencimento.getDate() == null || !valida_data_vencimento()) {
				return false;
			} else {
				calcula_totais();
				if (parcela_selecionada != null) {
					if (orcamento.getValor_total() < total_parcelas + valor_digitado
							- parcela_selecionada.getValor_parcela()) {
						return false;
					}
				} else if (orcamento.getValor_total() < total_parcelas + valor_digitado) {
					return false;
				}
			}

			return true;

		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void busca_parcela_selecionada() {
		parcela_selecionada = new Parcela();

		int linha_selecionda = tabelaParcelas.getSelectedRow();
		String valor_parcela = tabelaParcelas.getValueAt(linha_selecionda, 1).toString().replace(".", "")
				.replace(",", ".").replace("R$", "");

		Double valor_parcela_form = Double.parseDouble(valor_parcela);

		parcela_selecionada.setId_orcamento(orcamento.getId_orcamento());
		parcela_selecionada.setValor_parcela(valor_parcela_form);
		parcela_selecionada.setForma_pagamento((Forma_pagamento) tabelaParcelas.getValueAt(linha_selecionda, 2));
		parcela_selecionada.setData_pagamento((Date) tabelaParcelas.getValueAt(linha_selecionda, 3));
		parcela_selecionada.setData_vencimento((Date) tabelaParcelas.getValueAt(linha_selecionda, 4));

	}

	public void calcula_totais() {
		total_parcelas = 0.00;
		valor_em_aberto = 0.00;
		valor_pago = 0.00;
		valor_digitado = 0.00;

		if (!orcamento.getParcelas().isEmpty()) {
			for (Parcela parc : orcamento.getParcelas()) {
				total_parcelas += parc.getValor_parcela();

				// Calcula o total pagoverificando se a parcela possui data de pagamento.
				if (parc.getData_pagamento() != null) {
					valor_pago += parc.getValor_parcela();
				}
			}
		}

		valor_em_aberto = orcamento.getValor_total() - valor_pago;

		// Verifica se o campo de valor da parcela está vazio.
		if (!fTxtValorParcela.getText().trim().isEmpty()) {
			try {
				valor_digitado = nf.parse(fTxtValorParcela.getText().trim()).doubleValue();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		if (orcamento.getValor_total().compareTo(total_parcelas) == 0) {
			txtTotalParcelas.setForeground(Color.BLACK);
			btnConfirmarFaturamento.setEnabled(true);
		} else {
			txtTotalParcelas.setForeground(new Color(255, 69, 0));

			if (configuracoes_do_sistema.getSalva_parc_dif().equals("NÃO")) {
				btnConfirmarFaturamento.setEnabled(false);
			} else {
				btnConfirmarFaturamento.setEnabled(true);
			}
		}

	}

	public void mostra_totais() {
		txtTotalParcelas.setText(nf2.format(total_parcelas));
		txtValorAberto.setText(nf2.format(valor_em_aberto));
		txtValorPago.setText(nf2.format(valor_pago));
	}

	public Date pega_ultimo_vencimento() {
		Date maior_data_vencimento = null;

		if (orcamento.getParcelas().isEmpty()) {
			maior_data_vencimento = new Date();
		} else {
			for (Parcela parc : orcamento.getParcelas()) {
				if (maior_data_vencimento == null) {
					maior_data_vencimento = parc.getData_vencimento();
				}
				if (parc.getData_vencimento().after(maior_data_vencimento)) {
					maior_data_vencimento = parc.getData_vencimento();
				}
			}
		}

		// Testa se a data de vencimento da ultima parcela é menor que a data atual.
		if (maior_data_vencimento.after(new Date())) {
			return maior_data_vencimento;
		} else {
			return new Date();
		}
	}

	public void parcelar_igualmente(int quantidade_de_parcelas, Double valor_a_parcelar) {
		nf.setRoundingMode(RoundingMode.DOWN);

		Double valor_parcela = Double.parseDouble(
				nf.format(valor_a_parcelar / quantidade_de_parcelas).replaceAll("\\.", "").replace(",", "."));

		Calendar vencimento = Calendar.getInstance();
		vencimento.setTime(pega_ultimo_vencimento());

		for (int n = 0; n < quantidade_de_parcelas; n++) {
			vencimento.add(Calendar.MONTH, 1);
			Parcela nova_parcela = new Parcela(orcamento.getId_orcamento(), valor_parcela,
					(Forma_pagamento) cbxFormaPagamento.getSelectedItem(), null, vencimento.getTime());

			if (n + 1 == quantidade_de_parcelas) {
				if (valor_a_parcelar.compareTo(nova_parcela.getValor_parcela() * quantidade_de_parcelas) != 0) {

					Double total = valor_parcela * quantidade_de_parcelas;

					nf.setRoundingMode(RoundingMode.HALF_UP);
					Double centavos_faltantes = valor_a_parcelar - total;

					centavos_faltantes = Double
							.parseDouble(nf.format(centavos_faltantes).replaceAll("\\.", "").replace(",", "."));

					nova_parcela.setValor_parcela(Double.parseDouble(nf.format(valor_parcela + centavos_faltantes).replaceAll("\\.", "").replace(",", ".")));
				}
			}
			orcamento.getParcelas().add(nova_parcela);
		}
		modelo_tabela.fireTableDataChanged();
	}

	// Valida se a data de vencimento ja foi utilizada por alguma parcela.
	public Boolean valida_data_vencimento() {
		Date vencimento_informado = null;

		if (jdcDataVencimento.getDate() == null) {
			return false;
		} else {
			try {
				vencimento_informado = sdf.parse(((JTextField) jdcDataVencimento.getDateEditor()).getText());
			} catch (ParseException e) {
				e.printStackTrace();
			}

			if (orcamento.getParcelas().isEmpty()) {
				return true;
			} else {
				for (Parcela parc : orcamento.getParcelas()) {
					if (vencimento_informado.equals(parc.getData_vencimento())) {
						if (!parc.equals(parcela_selecionada)) {
							return false;
						}
					}
				}
			}
			return true;
		}
	}

	public void valida_parcelamento() {
		if (total_parcelas < orcamento.getValor_total()) {
			if (fTxtQuantidadeDeParcelas.getText().trim().isEmpty()
					|| Integer.parseInt(fTxtQuantidadeDeParcelas.getText().trim()) == 0
					|| cbxFormaPagamento.getSelectedIndex() == -1) {
				btnParcelar.setEnabled(false);
			} else {
				btnParcelar.setEnabled(true);
			}
		} else {
			btnParcelar.setEnabled(false);
		}
	}

	
	public void aviso_fecha_tela() {
		Boolean flag = false;
		int opcao = JOptionPane.showConfirmDialog(jdcDataVencimento,
				"Deseja realmente cancelar as alterações realizadas?" + "\n\nATENÇÃO!"
						+ "\nTODAS AS ALTERAÇÕES REALIZADAS NO PARCELAMENTO SERÃO PERDIDAS.",
				"Cancelar alterações.", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);
		flag = opcao == JOptionPane.YES_OPTION;
		if (flag) {
			dispose();
		}else {
			setDefaultCloseOperation(0);
		}
	}
	
	public void abrir_faturamento(Faturamento tela_faturamento) {
		ArrayList<Forma_pagamento> formas_de_pagamento = alimenta_formas_pagamento();

		// Testa se existe formas de pagamento cadastradas.
		if (formas_de_pagamento.size() == 0) {
			JOptionPane.showMessageDialog(null,
					"Nenhuma forma de pagamento cadastrada.\nNecessário cadastrar ao menos 1 forma de pagamento para utilizar a manutenção das parcelas.",
					"Formas de pagamento.", JOptionPane.WARNING_MESSAGE);
			tela_faturamento.setVisible(false);

			CadastroFormaPagamento cadastro_formas = new CadastroFormaPagamento(tela_faturamento, formas_de_pagamento);
			cadastro_formas.setVisible(true);
		} else {
			tela_faturamento.setVisible(true);
		}
	}
}
