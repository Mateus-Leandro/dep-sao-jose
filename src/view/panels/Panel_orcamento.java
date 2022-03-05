package view.panels;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

import dao.ClienteDAO;
import dao.ConfiguracaoDAO;
import dao.OrcamentoDAO;
import dao.ProdutoDAO;
import dao.Resumo_financeiroDAO;
import entities.cliente.Cliente;
import entities.configuracoes.Configuracoes;
import entities.financeiro.Parcela;
import entities.financeiro.Resumo_financeiro;
import entities.orcamentos.Orcamento;
import entities.orcamentos.Produto_Orcamento;
import entities.produto.Produto;
import icons.Icones;
import pdf.Gera_pdf;
import tables.tableModels.ModeloTabelaProdutos_Orcamento;
import tools.Jtext_tools;
import view.dialog.Faturamento;
import view.dialog.Orcamentos_do_cliente;
import view.formatFields.FormataNumeral;

public class Panel_orcamento extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabbedPane;
	private JPanel cliente;
	private JPanel produtos;
	private JButton btnNovo;
	private JLabel lblCliente;
	private JSeparator separadorInformacoesDoCliente;
	private JLabel lblNomeCliente;
	private JFormattedTextField fTxtNomeCliente;
	private JLabel lblApelido;
	private JLabel lblDocumento;
	private JTextField txtDocumento;
	private JLabel lblIe;
	private JTextField txtIe;
	private JLabel lblOrcamento;
	private JSeparator separador_Orc_Vend;
	private Icones icones = new Icones();
	private JLabel lblCep;
	private JFormattedTextField fTxtCep;
	private JLabel lblCidade;
	private JFormattedTextField fTxtCidade;
	private JLabel lblEndereco;
	private JFormattedTextField fTxtEndereco;
	private JLabel lblReferencia;
	private JFormattedTextField fTxtReferencia;
	private JLabel lblNumero;
	private JFormattedTextField fTxtNumero;
	private JLabel lblBairro;
	private JFormattedTextField fTxtBairro;
	private JLabel lblCelular;
	private JFormattedTextField fTxtCelular;
	private JLabel lblTelFixo;
	private JFormattedTextField fTxtTelFixo;
	private JLabel lblEmai;
	private JFormattedTextField fTxtEmail;
	private JLabel lblResumoFinanceiro;
	private JSeparator separadorResumoFinanceiro;
	private JLabel lblValorComprado;
	private JFormattedTextField fTxtTotalVendido;
	private JLabel lblValorEmAberto;
	private JFormattedTextField fTxtValorEmAberto;
	private JLabel lblPrimeiraCompra;
	private JFormattedTextField fTxtPrimeiraCompra;
	private JLabel lblUltimaCompra;
	private JFormattedTextField fTxtUltimaCompra;
	private JLabel lblValorDaMaior;
	private JFormattedTextField fTxtMaiorCompra;
	private JList<Cliente> ltClientes;
	private DefaultListModel<Cliente> list_model = new DefaultListModel<Cliente>();
	private ArrayList<Cliente> lista_clientes = new ArrayList<Cliente>();
	private JButton btnLimpaCliente;
	private JLabel lblNomeProduto;
	private JFormattedTextField fTxtNomeProduto;
	private JLabel lblInclusãoDeProdutos;
	private JSeparator separadorInformacoesDoCliente_1;
	private JLabel lblCodigoProduto;
	private JLabel lblFatorVenda;
	private JComboBox<String> cbxFatorVenda;
	private JFormattedTextField fTxtQuantidade;
	private JLabel lblQuantidade;
	private JLabel lblPrecoUnitario;
	private JFormattedTextField fTxtPrecoUnitario;
	private JLabel lblDesconto;
	private JLabel lblPorcentagemDesconto;
	private JLabel lblValorDesconto;
	private JFormattedTextField fTxtValorDesconto;
	private JPanel panelDesconto;
	private JButton btnIncluir;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JLabel lblProdutosInclusos;
	private JScrollPane scrollPaneTabelaProdutosInclusos;
	private JLabel lblTotais;
	private JLabel lblTotalMercadoriasLiquido;
	private JFormattedTextField fTxtTotalMercadoriasLiquido;
	private JLabel lblTotalMercadoriasBruto;
	private JFormattedTextField fTxtTotalMercadoriasBruto;
	private JLabel lblFrete;
	private JFormattedTextField fTxtFrete;
	private JFormattedTextField fTxtValorDescFinal;
	private JLabel lblDescontoGeral;
	private JPanel panelValorTotal;
	private JFormattedTextField fTxtTotalOrcamento;
	private JLabel lblValorTotalOrcamento;
	private JLabel lblQuantidadeProdutos;
	private JFormattedTextField fTxtQuantidadeTotal;
	private JList<Produto> ltProdutos;
	private DefaultListModel<Produto> list_model_produtos = new DefaultListModel<Produto>();
	private ArrayList<Produto> lista_produtos = new ArrayList<Produto>();
	private JFormattedTextField fTxtCodigoProduto;
	private JScrollPane scrollPaneListaProdutos;
	private JScrollPane scrollPaneListaClientes;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JFormattedTextField fTxtPorcentagemDesconto;
	private JButton btnLimpaDadosProduto;
	private JTable tabelaProdutosInclusos;
	private ListSelectionModel lsm;
	private ArrayList<Produto_Orcamento> lista_produtos_inclusos = new ArrayList<Produto_Orcamento>();
	private ModeloTabelaProdutos_Orcamento modelo_tabela = new ModeloTabelaProdutos_Orcamento(lista_produtos_inclusos);
	private JPanel panelTotalItem;
	private JFormattedTextField fTxtTotalItemComDesconto;
	private JLabel lblTotalComDesconto;
	private JLabel lblCodBarra;
	private JFormattedTextField fTxtCodigoBarra;
	private Produto produto_selecionado;
	private Jtext_tools text_tools = new Jtext_tools();
	private JButton btnSalvar_editado;
	private JButton btnCancelar_editado;
	private Double total_mercadorias_bruto = 0.00;
	private Double total_mercadorias_liquido = 0.00;
	private Double desconto_final = 0.00;
	private Double valor_frete = 0.00;
	private NumberFormat nf = new DecimalFormat(",##0.00");
	private NumberFormat nf2 = new DecimalFormat("0.00");
	private NumberFormat nf3 = new DecimalFormat("R$ ,##0.00");
	private JFormattedTextField fTxtApelido;
	private Cliente cliente_selecionado = null;
	private Integer quantidade_de_produtos = 0;
	private Double total_orcamento = 0.00;
	private JButton btnPesquisaOrcamento;
	private JPanel panelNumeroOrcamento = new JPanel();
	private JFormattedTextField fTxtNumeroOrcamento;
	private JLabel lblNumeroOrcamento;
	private Double valor_original = 0.00;
	private Date data_faturamento = null;
	private Date data_inclusao_orcamento = null;
	private ArrayList<Parcela> parcelas = new ArrayList<Parcela>();
	private ConfiguracaoDAO conf_dao = new ConfiguracaoDAO();
	private Configuracoes configuracoes_do_sistema = conf_dao.busca_configuracoes();
	private Produto_Orcamento produto_incluso = new Produto_Orcamento();
	private JLabel lblObg_produto;
	private JLabel lblObg_produto_1;
	private JLabel lblObg_quantidade;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private JLabel lblTextoObservacao;
	private JPanel panelDescontoFinal;
	private JLabel lblPorcentagemDesconto_1;
	private JFormattedTextField fTxtPorcentDescFinal;
	private JLabel lblValorDescontoFinal;
	private Boolean editando_produto = false;
	private Checkbox checkboxLeitorBarras;
	private JPanel panelTotalItem_1;
	private JLabel lblTotalItem;
	private JFormattedTextField fTxtTotalItem;
	private DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
	private JLabel lblTotalDescontoProduto;
	private JSeparator separadorInformacoesDoCliente_2;
	private JFormattedTextField fTxtTotalDescontoProduto;
	private JButton btnImprimir;
	private Gera_pdf gera_pdf = new Gera_pdf();
	private Boolean cliente_vazio = true;

	/**
	 * Create the panel.
	 */
	public Panel_orcamento() {

		// Truncar valores, e não arredondar.
		nf.setRoundingMode(RoundingMode.DOWN);
		nf.setRoundingMode(RoundingMode.DOWN);
		tecla_pressionada();
		setLayout(null);

		btnImprimir = new JButton("Imprimir");
		btnImprimir.setEnabled(false);
		btnImprimir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickImprimeOrcamento) {
				if (btnImprimir.isEnabled()) {
					Orcamento orcamento = new Orcamento();
					orcamento = montar_orcamento(orcamento);
					gera_pdf.monta_pdf_orcamento(orcamento);
				}
			}
		});
		btnImprimir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnImprimir.setBounds(471, 70, 123, 29);
		btnImprimir.setIcon(icones.getIcone_impressora());
		btnImprimir.setVisible(false);
		add(btnImprimir);

		panelNumeroOrcamento.setLayout(null);
		panelNumeroOrcamento.setBorder(UIManager.getBorder("DesktopIcon.border"));
		panelNumeroOrcamento.setBounds(490, 10, 227, 29);
		add(panelNumeroOrcamento);

		fTxtNumeroOrcamento = new JFormattedTextField();
		fTxtNumeroOrcamento.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtNumeroOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtNumeroOrcamento.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNumeroOrcamento.setEditable(false);
		fTxtNumeroOrcamento.setColumns(10);
		fTxtNumeroOrcamento.setBounds(116, 4, 105, 20);
		panelNumeroOrcamento.add(fTxtNumeroOrcamento);

		lblNumeroOrcamento = new JLabel("Or\u00E7amento N\u00BA");
		lblNumeroOrcamento.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNumeroOrcamento.setBounds(10, 4, 102, 19);
		panelNumeroOrcamento.add(lblNumeroOrcamento);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setEnabled(false);
		tabbedPane.setBounds(3, 110, 720, 541);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(tabbedPane);

		produtos = new JPanel();
		tabbedPane.addTab("Produtos", produtos);
		produtos.setLayout(null);

		btnCancelar_editado = new JButton("Cancelar");
		btnCancelar_editado.setEnabled(false);
		btnCancelar_editado.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancelarEdicao) {
				cancelar_edicao();
			}
		});
		btnCancelar_editado.setIcon(icones.getIcone_cancelar());
		btnCancelar_editado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar_editado.setBounds(585, 196, 120, 29);
		btnCancelar_editado.setVisible(false);

		ltProdutos = new JList<Produto>();
		ltProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ltProdutos.setVisibleRowCount(10);
		ltProdutos.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickListaProduto) {
				produto_selecionado = ltProdutos.getSelectedValue();

				if (!produto_bloqueado()) {
					if (produto_ja_incluso(produto_selecionado.getIdProduto(), lista_produtos_inclusos)) {
						limpar_dados_produto();
						fTxtNomeProduto.requestFocus();
					} else {
						scrollPaneListaProdutos.setVisible(false);
						exibe_dados_produto_selecionado();
						fTxtQuantidade.requestFocus();
					}
				}
			}
		});

		lblTotalItem = new JLabel("Total R$");
		lblTotalItem.setForeground(Color.BLUE);
		lblTotalItem.setBounds(529, 128, 62, 19);
		produtos.add(lblTotalItem);
		lblTotalItem.setFont(new Font("Tahoma", Font.BOLD, 14));

		ltProdutos.setBounds(438, 77, 267, 79);
		scrollPaneListaProdutos = new JScrollPane(ltProdutos);
		scrollPaneListaProdutos.setBounds(199, 74, 307, 111);
		produtos.add(scrollPaneListaProdutos);
		scrollPaneListaProdutos.setVisible(false);
		produtos.add(btnCancelar_editado);

		btnSalvar_editado = new JButton("Salvar");
		btnSalvar_editado.setEnabled(false);
		btnSalvar_editado.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickSalvarEdicao) {
				salvar_edicao();
			}
		});
		btnSalvar_editado.setIcon(icones.getIcone_salvar());
		btnSalvar_editado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSalvar_editado.setBounds(455, 196, 120, 29);
		produtos.add(btnSalvar_editado);
		btnSalvar_editado.setVisible(false);

		panelTotalItem = new JPanel();
		panelTotalItem.setLayout(null);
		panelTotalItem.setBorder(UIManager.getBorder("DesktopIcon.border"));
		panelTotalItem.setBounds(417, 156, 288, 29);
		produtos.add(panelTotalItem);

		fTxtTotalItemComDesconto = new JFormattedTextField();
		fTxtTotalItemComDesconto.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtTotalItemComDesconto.setEditable(false);
		fTxtTotalItemComDesconto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtTotalItemComDesconto.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtTotalItemComDesconto.setColumns(10);
		fTxtTotalItemComDesconto.setBounds(173, 4, 105, 20);
		panelTotalItem.add(fTxtTotalItemComDesconto);

		lblTotalComDesconto = new JLabel("Total com desconto R$");
		lblTotalComDesconto.setForeground(new Color(0, 128, 0));
		lblTotalComDesconto.setBounds(10, 5, 159, 19);
		panelTotalItem.add(lblTotalComDesconto);
		lblTotalComDesconto.setFont(new Font("Tahoma", Font.BOLD, 14));

		panelValorTotal = new JPanel();
		panelValorTotal.setLayout(null);
		panelValorTotal.setBorder(UIManager.getBorder("DesktopIcon.border"));
		panelValorTotal.setBounds(464, 460, 241, 39);
		produtos.add(panelValorTotal);

		fTxtTotalOrcamento = new JFormattedTextField();
		fTxtTotalOrcamento.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtTotalOrcamento.setEditable(false);
		fTxtTotalOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtTotalOrcamento.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtTotalOrcamento.setColumns(10);
		fTxtTotalOrcamento.setBounds(92, 11, 139, 20);
		panelValorTotal.add(fTxtTotalOrcamento);

		lblValorTotalOrcamento = new JLabel("Valor total");
		lblValorTotalOrcamento.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblValorTotalOrcamento.setBounds(7, 11, 82, 19);
		panelValorTotal.add(lblValorTotalOrcamento);

		panelDesconto = new JPanel();
		panelDesconto.setBorder(UIManager.getBorder("DesktopIcon.border"));
		panelDesconto.setBounds(10, 156, 284, 29);
		produtos.add(panelDesconto);
		panelDesconto.setLayout(null);

		fTxtPorcentagemDesconto = new JFormattedTextField();
		fTxtPorcentagemDesconto.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent ganhoFocoPorcentagemDesconto) {
				text_tools.move_cursor_inicio(fTxtPorcentagemDesconto);
			}
		});
		fTxtPorcentagemDesconto.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtPorcentagemDesconto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaPorcentDesconto) {
				if (digitaPorcentDesconto.getKeyCode() == digitaPorcentDesconto.VK_ENTER) {
					fTxtValorDesconto.requestFocus();
				} else {
					calcula_valor_desconto(digitaPorcentDesconto);
					calcula_total_item();
				}
			}
		});
		fTxtPorcentagemDesconto.setEnabled(false);
		fTxtPorcentagemDesconto.setDocument(new FormataNumeral(5, 2));
		fTxtPorcentagemDesconto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtPorcentagemDesconto.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtPorcentagemDesconto.setBounds(95, 5, 58, 20);
		panelDesconto.add(fTxtPorcentagemDesconto);

		fTxtValorDesconto = new JFormattedTextField();
		fTxtValorDesconto.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent ganhoFocoValorDesconto) {
				text_tools.move_cursor_inicio(fTxtValorDesconto);
			}
		});
		fTxtValorDesconto.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtValorDesconto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaDesconto) {
				if (digitaDesconto.getKeyCode() == digitaDesconto.VK_ENTER) {
					if (btnIncluir.isVisible()) {
						incluir_produto();
					} else {
						salvar_edicao();
					}
				} else {
					calcula_total_item();
					calcula_porcentagem_desconto();
				}
			}
		});
		fTxtValorDesconto.setEnabled(false);
		fTxtValorDesconto.setDocument(new FormataNumeral(8, 2));
		fTxtValorDesconto.setBounds(193, 5, 85, 20);
		panelDesconto.add(fTxtValorDesconto);
		fTxtValorDesconto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtValorDesconto.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtValorDesconto.setColumns(10);

		lblValorDesconto = new JLabel("R$");
		lblValorDesconto.setForeground(new Color(255, 69, 0));
		lblValorDesconto.setBounds(173, 6, 20, 19);
		panelDesconto.add(lblValorDesconto);
		lblValorDesconto.setFont(new Font("Tahoma", Font.PLAIN, 14));

		lblDesconto = new JLabel("Desconto");
		lblDesconto.setForeground(new Color(255, 69, 0));
		lblDesconto.setBounds(7, 5, 64, 19);
		panelDesconto.add(lblDesconto);
		lblDesconto.setFont(new Font("Tahoma", Font.PLAIN, 14));

		lblPorcentagemDesconto = new JLabel("%");
		lblPorcentagemDesconto.setForeground(new Color(255, 69, 0));
		lblPorcentagemDesconto.setBounds(76, 6, 18, 19);
		panelDesconto.add(lblPorcentagemDesconto);
		lblPorcentagemDesconto.setFont(new Font("Tahoma", Font.PLAIN, 14));

		lblNomeProduto = new JLabel("Prod.");
		lblNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeProduto.setBounds(163, 56, 37, 19);
		produtos.add(lblNomeProduto);

		MaskFormatter mascara_nome_produto = null;
		try {
			mascara_nome_produto = new MaskFormatter("***********************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtNomeProduto = new JFormattedTextField(mascara_nome_produto);
		fTxtNomeProduto.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtNomeProduto.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNomeProduto) {
				text_tools.move_cursor_inicio(fTxtNomeProduto);
			}
		});
		fTxtNomeProduto.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent perdaFocoNomeProduto) {
				scrollPaneListaProdutos.setVisible(false);
			}

			@Override
			public void focusGained(FocusEvent ganhoFocoNomeProduto) {
				// Testa se está editando o produto ou incluindo.
				if (btnEditar.isVisible() && fTxtNomeProduto.isEditable()) {
					alimentar_lista_produtos("NOME", "%");
				}
			}
		});
		fTxtNomeProduto.setEnabled(false);
		fTxtNomeProduto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaNomeProduto) {
				if (digitaNomeProduto.getKeyCode() != digitaNomeProduto.VK_ENTER) {
					alimentar_lista_produtos("NOME", fTxtNomeProduto.getText().trim());
				} else {
					if (!seleciona_produto()) {
						lista_produtos.clear();
						fTxtNomeProduto.requestFocus();
					}
				}
			}
		});
		fTxtNomeProduto.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtNomeProduto.setColumns(10);
		fTxtNomeProduto.setBounds(199, 55, 307, 20);
		produtos.add(fTxtNomeProduto);

		lblInclusãoDeProdutos = new JLabel("Inclus\u00E3o de produtos");
		lblInclusãoDeProdutos.setHorizontalAlignment(SwingConstants.CENTER);
		lblInclusãoDeProdutos.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblInclusãoDeProdutos.setBounds(234, 11, 213, 29);
		produtos.add(lblInclusãoDeProdutos);

		separadorInformacoesDoCliente_1 = new JSeparator();
		separadorInformacoesDoCliente_1.setBounds(10, 41, 695, 9);
		produtos.add(separadorInformacoesDoCliente_1);

		lblCodigoProduto = new JLabel("C\u00F3d.");
		lblCodigoProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigoProduto.setBounds(11, 56, 30, 19);
		produtos.add(lblCodigoProduto);

		lblFatorVenda = new JLabel("Fator de venda");
		lblFatorVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFatorVenda.setBounds(10, 98, 97, 19);
		produtos.add(lblFatorVenda);

		cbxFatorVenda = new JComboBox<>();
		cbxFatorVenda.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent alteraFator) {
				if (cbxFatorVenda.getSelectedItem().toString().equals("MT")
						|| cbxFatorVenda.getSelectedItem().toString().equals("KG")
						|| cbxFatorVenda.getSelectedItem().toString().equals("L")
						|| cbxFatorVenda.getSelectedItem().toString().equals("PAR")) {
					fTxtQuantidade.setText(null);
					fTxtQuantidade.setDocument(new FormataNumeral(9, 2));
				} else {
					fTxtQuantidade.setText(null);
					fTxtQuantidade.setDocument(new FormataNumeral(6, 0));
				}
			}
		});
		cbxFatorVenda.setEnabled(false);
		cbxFatorVenda
				.setModel(new DefaultComboBoxModel(new String[] { "UN", "PAR", "MT", "KG", "L", "CX", "FD", "PCT" }));
		cbxFatorVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxFatorVenda.setBounds(107, 98, 57, 22);
		produtos.add(cbxFatorVenda);

		lblQuantidade = new JLabel("Quantidade");
		lblQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuantidade.setBounds(306, 98, 70, 19);
		produtos.add(lblQuantidade);

		lblPrecoUnitario = new JLabel("Pre\u00E7o unit\u00E1rio");
		lblPrecoUnitario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecoUnitario.setBounds(519, 98, 86, 19);
		produtos.add(lblPrecoUnitario);

		fTxtPrecoUnitario = new JFormattedTextField();
		fTxtPrecoUnitario.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtPrecoUnitario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaPrecoUnitario) {
				if (digitaPrecoUnitario.getKeyCode() == digitaPrecoUnitario.VK_ENTER) {
					fTxtPorcentagemDesconto.requestFocus();
				} else {
					calcula_total_item();
					calcula_valor_desconto(digitaPrecoUnitario);
					calcula_total_item();
				}
			}
		});
		fTxtPrecoUnitario.setEnabled(false);
		fTxtPrecoUnitario.setDocument(new FormataNumeral(8, 2));
		fTxtPrecoUnitario.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtPrecoUnitario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtPrecoUnitario.setColumns(10);
		fTxtPrecoUnitario.setBounds(607, 94, 97, 20);
		produtos.add(fTxtPrecoUnitario);

		btnIncluir = new JButton("Incluir");
		btnIncluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickIncluirProduto) {
				if (btnIncluir.isEnabled()) {
					incluir_produto();
				}
			}
		});
		btnIncluir.setEnabled(false);
		btnIncluir.setIcon(icones.getIcone_mais());
		btnIncluir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnIncluir.setBounds(375, 191, 104, 29);
		produtos.add(btnIncluir);

		btnEditar = new JButton("Editar");
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEditarProduto) {
				if (btnEditar.isEnabled() && !lsm.isSelectionEmpty()) {
					produto_selecionado = null;
					editar_produto();
				}
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setIcon(icones.getIcone_editar());
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEditar.setBounds(489, 191, 104, 29);
		produtos.add(btnEditar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickExcluir) {

				if (btnExcluir.isEnabled() && !lsm.isSelectionEmpty()) {

					int linha_selecionada = tabelaProdutosInclusos.getSelectedRow();
					if (excluir_produto(tabelaProdutosInclusos.getValueAt(linha_selecionada, 0).toString())) {
						JOptionPane.showMessageDialog(lblQuantidade, "Produto removido corretamente do orçamento.",
								"Produto removido.", JOptionPane.NO_OPTION);
						quantidade_de_produtos = modelo_tabela.getRowCount();
						fTxtQuantidadeTotal.setText(Integer.toString(quantidade_de_produtos));
						calcula_total_mercadorias();

						// Desativa os campos de frete, valor de desconto final, porcentagem de
						// desconto final e botão imprimir, caso não exista produtos no orçamento.
						if (quantidade_de_produtos < 1) {
							fTxtFrete.setEditable(false);
							fTxtValorDescFinal.setEditable(false);
							fTxtPorcentDescFinal.setEditable(false);
							btnImprimir.setEnabled(false);
						}
					}
				}
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setIcon(icones.getIcone_excluir());
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExcluir.setBounds(601, 191, 104, 29);
		produtos.add(btnExcluir);

		lblProdutosInclusos = new JLabel("Produtos Inclusos");
		lblProdutosInclusos.setHorizontalAlignment(SwingConstants.CENTER);
		lblProdutosInclusos.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblProdutosInclusos.setBounds(10, 203, 163, 29);
		produtos.add(lblProdutosInclusos);

		tabelaProdutosInclusos = new JTable(modelo_tabela);
		tabelaProdutosInclusos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaProdutosInclusos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tabelaProdutosInclusos.setBounds(10, 236, 695, 152);
		ConfiguraLarguraColunaTabela(tabelaProdutosInclusos);
		tabelaProdutosInclusos.setAutoResizeMode(tabelaProdutosInclusos.AUTO_RESIZE_OFF);
		tabelaProdutosInclusos.getTableHeader().setReorderingAllowed(false);
		tabelaProdutosInclusos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent selecaoLinhaTabela) {

				lsm = (ListSelectionModel) selecaoLinhaTabela.getSource();
				if (!lsm.isSelectionEmpty()) {
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
				}
			}
		});

		scrollPaneTabelaProdutosInclusos = new JScrollPane(tabelaProdutosInclusos);
		scrollPaneTabelaProdutosInclusos.setBounds(10, 236, 695, 152);
		produtos.add(scrollPaneTabelaProdutosInclusos);

		lblTotais = new JLabel("Totais");
		lblTotais.setHorizontalAlignment(SwingConstants.CENTER);
		lblTotais.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblTotais.setBounds(10, 393, 57, 27);
		produtos.add(lblTotais);

		lblTotalMercadoriasLiquido = new JLabel("Tot. merc.(L\u00EDquido)");
		lblTotalMercadoriasLiquido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotalMercadoriasLiquido.setForeground(new Color(0, 128, 0));
		lblTotalMercadoriasLiquido.setBounds(10, 481, 127, 19);
		produtos.add(lblTotalMercadoriasLiquido);

		fTxtTotalMercadoriasLiquido = new JFormattedTextField();
		fTxtTotalMercadoriasLiquido.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtTotalMercadoriasLiquido.setEditable(false);
		fTxtTotalMercadoriasLiquido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtTotalMercadoriasLiquido.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtTotalMercadoriasLiquido.setColumns(10);
		fTxtTotalMercadoriasLiquido.setBounds(129, 480, 105, 20);
		produtos.add(fTxtTotalMercadoriasLiquido);

		lblTotalMercadoriasBruto = new JLabel("Tot. merc. (Bruto)");
		lblTotalMercadoriasBruto.setForeground(Color.BLUE);
		lblTotalMercadoriasBruto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotalMercadoriasBruto.setBounds(10, 451, 120, 19);
		produtos.add(lblTotalMercadoriasBruto);

		fTxtTotalMercadoriasBruto = new JFormattedTextField();
		fTxtTotalMercadoriasBruto.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtTotalMercadoriasBruto.setEditable(false);
		fTxtTotalMercadoriasBruto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtTotalMercadoriasBruto.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtTotalMercadoriasBruto.setColumns(10);
		fTxtTotalMercadoriasBruto.setBounds(125, 450, 109, 20);
		produtos.add(fTxtTotalMercadoriasBruto);

		lblFrete = new JLabel("Frete");
		lblFrete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFrete.setBounds(328, 480, 37, 19);
		produtos.add(lblFrete);

		fTxtFrete = new JFormattedTextField();
		fTxtFrete.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtFrete.setEditable(false);
		fTxtFrete.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaValorFrete) {
				try {
					valor_frete = nf.parse(fTxtFrete.getText()).doubleValue();

				} catch (ParseException e) {
					e.printStackTrace();
				}
				calcula_valor_desconto_final(digitaValorFrete);
				calcula_total_orcamento();
			}
		});
		fTxtFrete.setDocument(new FormataNumeral(8, 2));
		fTxtFrete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtFrete.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtFrete.setColumns(10);
		fTxtFrete.setBounds(365, 477, 89, 20);
		produtos.add(fTxtFrete);

		lblDescontoGeral = new JLabel("Desc. final");
		lblDescontoGeral.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDescontoGeral.setBounds(417, 417, 68, 19);
		produtos.add(lblDescontoGeral);

		lblQuantidadeProdutos = new JLabel("Quantidade de produtos");
		lblQuantidadeProdutos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuantidadeProdutos.setBounds(10, 424, 154, 19);
		produtos.add(lblQuantidadeProdutos);

		fTxtQuantidadeTotal = new JFormattedTextField();
		fTxtQuantidadeTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtQuantidadeTotal.setEditable(false);
		fTxtQuantidadeTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtQuantidadeTotal.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtQuantidadeTotal.setColumns(10);
		fTxtQuantidadeTotal.setBounds(161, 421, 96, 20);
		produtos.add(fTxtQuantidadeTotal);

		MaskFormatter mascara_codigo_produto = null;

		try {
			mascara_codigo_produto = new MaskFormatter("######");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtCodigoProduto = new JFormattedTextField(mascara_codigo_produto);
		fTxtCodigoProduto.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCodigoProduto) {
				text_tools.move_cursor_inicio(fTxtCodigoProduto);
			}
		});
		fTxtCodigoProduto.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtCodigoProduto.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent perdaFocoNomeProduto) {
				scrollPaneListaProdutos.setVisible(false);
			}
		});
		fTxtCodigoProduto.setEnabled(false);
		fTxtCodigoProduto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaCodigoProduto) {

				if (digitaCodigoProduto.getKeyCode() != digitaCodigoProduto.VK_F2) {
					if (digitaCodigoProduto.getKeyCode() != digitaCodigoProduto.VK_ENTER) {
						alimentar_lista_produtos("CÓDIGO", fTxtCodigoProduto.getText().trim());
					} else {
						if (!fTxtCodigoProduto.getText().trim().isEmpty()) {
							if (!seleciona_produto()) {
								lista_produtos.clear();
							}
						}
					}
				}
			}
		});
		fTxtCodigoProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCodigoProduto.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCodigoProduto.setColumns(10);
		fTxtCodigoProduto.setBounds(43, 55, 67, 20);
		produtos.add(fTxtCodigoProduto);

		btnLimpaDadosProduto = new JButton();
		btnLimpaDadosProduto.setToolTipText("Limpar item escolhido.");
		btnLimpaDadosProduto.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickLimpaDadosProduto) {
				if (btnLimpaDadosProduto.isEnabled()) {
					limpar_dados_produto();
					if (checkboxLeitorBarras.getState()) {
						fTxtCodigoBarra.setEditable(true);
						fTxtCodigoBarra.requestFocus();
					} else {
						fTxtCodigoProduto.requestFocus();
					}

					fTxtValorEmAberto.setBorder(new LineBorder(Color.lightGray));
					fTxtQuantidade.setBorder(new LineBorder(Color.lightGray));
					fTxtPrecoUnitario.setBorder(new LineBorder(Color.lightGray));
				}
			}
		});
		btnLimpaDadosProduto.setEnabled(false);
		btnLimpaDadosProduto.setIcon(icones.getIcone_limpar());
		btnLimpaDadosProduto.setBounds(115, 56, 27, 19);
		produtos.add(btnLimpaDadosProduto);

		lblCodBarra = new JLabel("Cod.Barra");
		lblCodBarra.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodBarra.setBounds(519, 55, 63, 19);
		produtos.add(lblCodBarra);

		MaskFormatter mascara_barras = null;
		try {
			mascara_barras = new MaskFormatter("##############");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtCodigoBarra = new JFormattedTextField(mascara_barras);
		fTxtCodigoBarra.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCodigoBarras) {
				text_tools.move_cursor_inicio(fTxtCodigoBarra);
			}
		});
		fTxtCodigoBarra.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent perdaFocoCodigoBarras) {
				scrollPaneListaProdutos.setVisible(false);
			}

			@Override
			public void focusGained(FocusEvent ganhoFocoCodigoBarras) {
				if (!fTxtNomeProduto.isEditable() && fTxtCodigoBarra.isEditable()
						&& !fTxtCodigoBarra.getText().trim().isEmpty()) {
					alimentar_lista_produtos("BARRAS", fTxtCodigoBarra.getText().trim());
				}
			}
		});
		fTxtCodigoBarra.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaCodigoBarras) {
				if (fTxtCodigoBarra.getText().trim().isEmpty()) {
					lista_produtos.clear();
					scrollPaneListaProdutos.setVisible(false);
				} else {
					if (digitaCodigoBarras.getKeyCode() == digitaCodigoBarras.VK_ENTER) {
						if (!seleciona_produto()) {
							lista_produtos.clear();
							fTxtCodigoBarra.requestFocus();
						}
					} else {
						if (digitaCodigoBarras.getKeyCode() != digitaCodigoBarras.VK_F2
								&& teclou_digito(digitaCodigoBarras.getKeyChar())) {
							alimentar_lista_produtos("BARRAS", fTxtCodigoBarra.getText().trim());
						}
					}
				}
			}
		});
		fTxtCodigoBarra.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtCodigoBarra.setEditable(false);
		fTxtCodigoBarra.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCodigoBarra.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCodigoBarra.setEnabled(false);
		fTxtCodigoBarra.setColumns(10);
		fTxtCodigoBarra.setBounds(585, 54, 120, 20);
		produtos.add(fTxtCodigoBarra);

		lblObg_produto = new JLabel("*");
		lblObg_produto.setForeground(Color.RED);
		lblObg_produto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObg_produto.setBounds(507, 67, 20, 15);
		produtos.add(lblObg_produto);

		lblObg_produto_1 = new JLabel("*");
		lblObg_produto_1.setForeground(Color.RED);
		lblObg_produto_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObg_produto_1.setBounds(704, 106, 20, 15);
		produtos.add(lblObg_produto_1);

		lblObg_quantidade = new JLabel("*");
		lblObg_quantidade.setForeground(Color.RED);
		lblObg_quantidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObg_quantidade.setBounds(465, 108, 20, 15);
		produtos.add(lblObg_quantidade);

		fTxtQuantidade = new JFormattedTextField();
		fTxtQuantidade.setHorizontalAlignment(SwingConstants.CENTER);
		fTxtQuantidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaQuantidade) {
				calcula_total_item();
				calcula_valor_desconto(digitaQuantidade);
				calcula_total_item();

				if (digitaQuantidade.getKeyCode() == digitaQuantidade.VK_ENTER) {

					if (!fTxtQuantidade.getText().trim().isEmpty()) {
						if (Double.parseDouble(fTxtQuantidade.getText().replaceAll("\\.", "").replace(",", ".")) > 0) {
							fTxtPrecoUnitario.requestFocus();
						}
					}
				}
			}
		});
		fTxtQuantidade.setEnabled(false);
		fTxtQuantidade.setDocument(new FormataNumeral(6, 0));
		fTxtQuantidade.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtQuantidade.setColumns(10);
		fTxtQuantidade.setBounds(379, 95, 86, 20);
		produtos.add(fTxtQuantidade);

		panelDescontoFinal = new JPanel();
		panelDescontoFinal.setLayout(null);
		panelDescontoFinal.setBorder(UIManager.getBorder("DesktopIcon.border"));
		panelDescontoFinal.setBounds(410, 412, 295, 31);
		produtos.add(panelDescontoFinal);

		lblPorcentagemDesconto_1 = new JLabel("%");
		lblPorcentagemDesconto_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPorcentagemDesconto_1.setBounds(76, 6, 18, 19);
		panelDescontoFinal.add(lblPorcentagemDesconto_1);

		fTxtValorDescFinal = new JFormattedTextField();
		fTxtValorDescFinal.setBounds(194, 5, 91, 20);
		panelDescontoFinal.add(fTxtValorDescFinal);
		fTxtValorDescFinal.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtValorDescFinal.setEditable(false);
		fTxtValorDescFinal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaDescontoFinal) {
				calcula_porcent_desc_final();
				calcula_total_orcamento();
			}
		});
		fTxtValorDescFinal.setDocument(new FormataNumeral(8, 2));
		fTxtValorDescFinal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtValorDescFinal.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtValorDescFinal.setColumns(10);

		fTxtPorcentDescFinal = new JFormattedTextField();
		fTxtPorcentDescFinal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaPorcentDescFinal) {

				if (digitaPorcentDescFinal.getKeyCode() == digitaPorcentDescFinal.VK_ENTER) {
					fTxtValorDescFinal.requestFocus();
				} else {
					calcula_valor_desconto_final(digitaPorcentDescFinal);
					calcula_total_orcamento();
				}
			}
		});
		fTxtPorcentDescFinal.setDocument(new FormataNumeral(5, 2));
		fTxtPorcentDescFinal.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtPorcentDescFinal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtPorcentDescFinal.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtPorcentDescFinal.setEditable(false);
		fTxtPorcentDescFinal.setColumns(10);
		fTxtPorcentDescFinal.setBounds(93, 5, 51, 20);
		panelDescontoFinal.add(fTxtPorcentDescFinal);

		lblValorDescontoFinal = new JLabel("R$");
		lblValorDescontoFinal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValorDescontoFinal.setBounds(169, 6, 20, 19);
		panelDescontoFinal.add(lblValorDescontoFinal);

		checkboxLeitorBarras = new Checkbox("Leitor Cod. Barras (F2)");
		checkboxLeitorBarras.setForeground(new Color(139, 0, 0));
		checkboxLeitorBarras.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent clickLeitor) {
				lista_produtos.clear();
				limpar_dados_produto();
				if (clickLeitor.getStateChange() == clickLeitor.SELECTED) {
					ativa_leitor();
				} else {
					desativa_leitor();
				}
				scrollPaneListaProdutos.setVisible(false);
			}
		});
		checkboxLeitorBarras.setFont(new Font("Dialog", Font.PLAIN, 14));
		checkboxLeitorBarras.setBounds(10, 11, 163, 22);
		checkboxLeitorBarras.setVisible(false);
		produtos.add(checkboxLeitorBarras);

		panelTotalItem_1 = new JPanel();
		panelTotalItem_1.setLayout(null);
		panelTotalItem_1.setBorder(UIManager.getBorder("DesktopIcon.border"));
		panelTotalItem_1.setBounds(523, 122, 182, 29);
		produtos.add(panelTotalItem_1);

		fTxtTotalItem = new JFormattedTextField();
		fTxtTotalItem.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtTotalItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtTotalItem.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtTotalItem.setEditable(false);
		fTxtTotalItem.setColumns(10);
		fTxtTotalItem.setBounds(67, 5, 105, 20);
		panelTotalItem_1.add(fTxtTotalItem);

		lblTotalDescontoProduto = new JLabel("Tot. Desc. Merc.");
		lblTotalDescontoProduto.setForeground(new Color(255, 69, 0));
		lblTotalDescontoProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTotalDescontoProduto.setBounds(244, 452, 103, 19);
		produtos.add(lblTotalDescontoProduto);

		separadorInformacoesDoCliente_2 = new JSeparator();
		separadorInformacoesDoCliente_2.setBounds(67, 407, 639, 9);
		produtos.add(separadorInformacoesDoCliente_2);

		fTxtTotalDescontoProduto = new JFormattedTextField();
		fTxtTotalDescontoProduto.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtTotalDescontoProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtTotalDescontoProduto.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtTotalDescontoProduto.setEditable(false);
		fTxtTotalDescontoProduto.setColumns(10);
		fTxtTotalDescontoProduto.setBounds(347, 449, 107, 20);
		produtos.add(fTxtTotalDescontoProduto);

		btnNovo = new JButton("Novo");
		btnNovo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNovoOrcamento) {
				novo_orcamento();
			}
		});
		btnNovo.setBounds(10, 70, 89, 29);
		btnNovo.setIcon(icones.getIcone_mais());
		add(btnNovo);
		btnNovo.setFont(new Font("Tahoma", Font.PLAIN, 14));

		lblOrcamento = new JLabel("Cadastro de or\u00E7amentos");
		lblOrcamento.setBounds(180, 10, 300, 29);
		lblOrcamento.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrcamento.setFont(new Font("Tahoma", Font.BOLD, 24));
		add(lblOrcamento);

		separador_Orc_Vend = new JSeparator();
		separador_Orc_Vend.setBounds(10, 50, 707, 9);
		add(separador_Orc_Vend);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickSalvarOrcamento) {
				salvar_orcamento();
			}
		});
		btnSalvar.setIcon(icones.getIcone_salvar());
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSalvar.setBounds(366, 70, 95, 29);
		btnSalvar.setVisible(false);
		add(btnSalvar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancelar) {
				cancelar_orcamento();
			}
		});
		btnCancelar.setIcon(icones.getIcone_cancelar());
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.setBounds(603, 70, 114, 29);
		btnCancelar.setVisible(false);
		add(btnCancelar);

		cliente = new JPanel();
		tabbedPane.addTab("Cliente", cliente);
		cliente.setLayout(null);

		ltClientes = new JList<Cliente>();
		ltClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickListaClientes) {
				Boolean flag = true;
				cliente_selecionado = ltClientes.getSelectedValue();
				if (cliente_selecionado.getBloqueado()) {
					int opcao = JOptionPane.showConfirmDialog(fTxtCidade,
							"ATENÇÃO!\nO cliente selecionado está bloqueado.\nDeseja utilizá-lo no orçamento mesmo assim?\n",
							"Cliente bloqueado.", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);
					flag = opcao == JOptionPane.YES_OPTION;
				}

				if (flag) {
					exibir_dados_cliente();
				} else {
					cliente_selecionado = null;
					fTxtNomeCliente.setText(null);
					fTxtNomeCliente.requestFocus();
				}
				scrollPaneListaClientes.setVisible(false);
			}
		});
		ltClientes.setBounds(48, 79, 267, 66);

		scrollPaneListaClientes = new JScrollPane(ltClientes);
		scrollPaneListaClientes.setBounds(48, 79, 267, 66);
		scrollPaneListaClientes.setVisible(false);
		cliente.add(scrollPaneListaClientes);

		lblCliente = new JLabel("Informa\u00E7\u00F5es do Cliente");
		lblCliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCliente.setBounds(236, 11, 225, 29);
		cliente.add(lblCliente);

		separadorInformacoesDoCliente = new JSeparator();
		separadorInformacoesDoCliente.setBounds(10, 41, 715, 9);
		cliente.add(separadorInformacoesDoCliente);

		lblNomeCliente = new JLabel("Nome");
		lblNomeCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeCliente.setBounds(10, 61, 48, 19);
		cliente.add(lblNomeCliente);

		MaskFormatter mascara_nome = null;
		try {
			mascara_nome = new MaskFormatter("*********************************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtNomeCliente = new JFormattedTextField(mascara_nome);
		fTxtNomeCliente.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent perdaFocoNomeCliente) {
				scrollPaneListaClientes.setVisible(false);
			}
		});
		fTxtNomeCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNomeCliente) {
				text_tools.move_cursor_inicio(fTxtNomeCliente);
			}
		});
		fTxtNomeCliente.setEnabled(false);
		fTxtNomeCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaNomeCliente) {
				if (fTxtNomeCliente.getText().trim().isEmpty()) {
					alimentar_lista_clientes("NOME", null);
				} else {
					alimentar_lista_clientes("NOME", fTxtNomeCliente.getText().trim());
				}

			}
		});
		fTxtNomeCliente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtNomeCliente.setColumns(10);
		fTxtNomeCliente.setBounds(48, 61, 267, 20);
		cliente.add(fTxtNomeCliente);

		lblApelido = new JLabel("Apelido");
		lblApelido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblApelido.setBounds(417, 61, 48, 19);
		cliente.add(lblApelido);

		lblDocumento = new JLabel("Documento");
		lblDocumento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDocumento.setBounds(10, 93, 78, 19);
		cliente.add(lblDocumento);

		txtDocumento = new JTextField();
		txtDocumento.setEnabled(false);
		txtDocumento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtDocumento.setEditable(false);
		txtDocumento.setColumns(10);
		txtDocumento.setBounds(88, 91, 133, 20);
		cliente.add(txtDocumento);

		lblIe = new JLabel("I.E.");
		lblIe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIe.setBounds(544, 93, 27, 19);
		cliente.add(lblIe);

		txtIe = new JTextField();
		txtIe.setEnabled(false);
		txtIe.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtIe.setEditable(false);
		txtIe.setColumns(10);
		txtIe.setBounds(570, 91, 133, 20);
		cliente.add(txtIe);

		lblCep = new JLabel("Cep");
		lblCep.setToolTipText("");
		lblCep.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCep.setBounds(10, 125, 28, 20);
		cliente.add(lblCep);

		MaskFormatter mascara_cep = null;

		try {
			mascara_cep = new MaskFormatter("#####-###");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtCep = new JFormattedTextField(mascara_cep);
		fTxtCep.setEditable(false);
		fTxtCep.setEnabled(false);
		fTxtCep.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtCep.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCep.setBounds(40, 127, 78, 20);
		cliente.add(fTxtCep);

		lblCidade = new JLabel("Cidade");
		lblCidade.setToolTipText("");
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCidade.setBounds(205, 129, 44, 20);
		cliente.add(lblCidade);

		MaskFormatter mascara_cidade = null;
		try {
			mascara_cidade = new MaskFormatter("******************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtCidade = new JFormattedTextField(mascara_cidade);
		fTxtCidade.setEditable(false);
		fTxtCidade.setEnabled(false);
		fTxtCidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtCidade.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCidade.setBounds(256, 127, 239, 20);
		cliente.add(fTxtCidade);

		lblEndereco = new JLabel("Endereco");
		lblEndereco.setToolTipText("");
		lblEndereco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEndereco.setBounds(10, 165, 65, 20);
		cliente.add(lblEndereco);

		MaskFormatter mascara_endereco = null;

		try {
			mascara_endereco = new MaskFormatter("*************************************************");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		fTxtEndereco = new JFormattedTextField(mascara_endereco);
		fTxtEndereco.setEditable(false);
		fTxtEndereco.setEnabled(false);
		fTxtEndereco.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtEndereco.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtEndereco.setBounds(73, 163, 348, 20);
		cliente.add(fTxtEndereco);

		lblReferencia = new JLabel("Referencia");
		lblReferencia.setToolTipText("");
		lblReferencia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReferencia.setBounds(9, 198, 65, 20);
		cliente.add(lblReferencia);

		MaskFormatter mascara_referencia = null;

		try {
			mascara_referencia = new MaskFormatter("*************************************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtReferencia = new JFormattedTextField(mascara_referencia);
		fTxtReferencia.setEditable(false);
		fTxtReferencia.setEnabled(false);
		fTxtReferencia.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtReferencia.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtReferencia.setBounds(79, 196, 342, 20);
		cliente.add(fTxtReferencia);

		lblNumero = new JLabel("N\u00B0");
		lblNumero.setToolTipText("");
		lblNumero.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumero.setBounds(463, 165, 16, 20);
		cliente.add(lblNumero);

		MaskFormatter mascara_numero = null;
		try {
			mascara_numero = new MaskFormatter("********");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtNumero = new JFormattedTextField(mascara_numero);
		fTxtNumero.setEditable(false);
		fTxtNumero.setEnabled(false);
		fTxtNumero.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtNumero.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNumero.setBounds(481, 164, 90, 20);
		cliente.add(fTxtNumero);

		lblBairro = new JLabel("Bairro");
		lblBairro.setToolTipText("");
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBairro.setBounds(437, 198, 40, 20);
		cliente.add(lblBairro);

		MaskFormatter mascara_bairro = null;
		try {
			mascara_bairro = new MaskFormatter("******************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtBairro = new JFormattedTextField(mascara_bairro);
		fTxtBairro.setEditable(false);
		fTxtBairro.setEnabled(false);
		fTxtBairro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtBairro.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtBairro.setBounds(482, 194, 220, 20);
		cliente.add(fTxtBairro);

		lblCelular = new JLabel("Celular");
		lblCelular.setToolTipText("");
		lblCelular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCelular.setBounds(9, 231, 46, 20);
		cliente.add(lblCelular);

		MaskFormatter mascara_celular = null;
		try {
			mascara_celular = new MaskFormatter("(##)#####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtCelular = new JFormattedTextField(mascara_celular);
		fTxtCelular.setEditable(false);
		fTxtCelular.setEnabled(false);
		fTxtCelular.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtCelular.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCelular.setBounds(55, 231, 104, 20);
		cliente.add(fTxtCelular);

		lblTelFixo = new JLabel("Tel. Fixo");
		lblTelFixo.setToolTipText("");
		lblTelFixo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTelFixo.setBounds(187, 232, 50, 20);
		cliente.add(lblTelFixo);

		MaskFormatter mascara_telefone = null;
		try {
			mascara_telefone = new MaskFormatter("(##)####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtTelFixo = new JFormattedTextField(mascara_telefone);
		fTxtTelFixo.setEditable(false);
		fTxtTelFixo.setEnabled(false);
		fTxtTelFixo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtTelFixo.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtTelFixo.setBounds(244, 231, 104, 20);
		cliente.add(fTxtTelFixo);

		lblEmai = new JLabel("Email");
		lblEmai.setToolTipText("");
		lblEmai.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmai.setBounds(365, 233, 40, 20);
		cliente.add(lblEmai);

		MaskFormatter mascara_email = null;
		try {
			mascara_email = new MaskFormatter("********************************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtEmail = new JFormattedTextField(mascara_email);
		fTxtEmail.setEditable(false);
		fTxtEmail.setEnabled(false);
		fTxtEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtEmail.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtEmail.setBounds(405, 232, 297, 20);
		cliente.add(fTxtEmail);

		lblResumoFinanceiro = new JLabel("Resumo Financeiro");
		lblResumoFinanceiro.setHorizontalAlignment(SwingConstants.CENTER);
		lblResumoFinanceiro.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblResumoFinanceiro.setBounds(236, 268, 225, 29);
		cliente.add(lblResumoFinanceiro);

		separadorResumoFinanceiro = new JSeparator();
		separadorResumoFinanceiro.setBounds(10, 302, 715, 9);
		cliente.add(separadorResumoFinanceiro);

		lblValorComprado = new JLabel("Total j\u00E1 vendido");
		lblValorComprado.setToolTipText("");
		lblValorComprado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValorComprado.setBounds(10, 364, 104, 20);
		cliente.add(lblValorComprado);

		fTxtTotalVendido = new JFormattedTextField();
		fTxtTotalVendido.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtTotalVendido.setEditable(false);
		fTxtTotalVendido.setToolTipText("N\u00E3o considera or\u00E7amentos");
		fTxtTotalVendido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtTotalVendido.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtTotalVendido.setEnabled(false);
		fTxtTotalVendido.setBounds(115, 364, 100, 20);
		cliente.add(fTxtTotalVendido);

		lblValorEmAberto = new JLabel("Valor em aberto");
		lblValorEmAberto.setToolTipText("");
		lblValorEmAberto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValorEmAberto.setBounds(10, 330, 104, 20);
		cliente.add(lblValorEmAberto);

		fTxtValorEmAberto = new JFormattedTextField();
		fTxtValorEmAberto.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtValorEmAberto.setEditable(false);
		fTxtValorEmAberto.setToolTipText("N\u00E3o considera or\u00E7amentos");
		fTxtValorEmAberto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtValorEmAberto.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtValorEmAberto.setEnabled(false);
		fTxtValorEmAberto.setBounds(116, 328, 99, 20);
		cliente.add(fTxtValorEmAberto);

		lblPrimeiraCompra = new JLabel("Data da primeira compra");
		lblPrimeiraCompra.setToolTipText("");
		lblPrimeiraCompra.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrimeiraCompra.setBounds(438, 331, 151, 20);
		cliente.add(lblPrimeiraCompra);

		fTxtPrimeiraCompra = new JFormattedTextField();
		fTxtPrimeiraCompra.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtPrimeiraCompra.setEditable(false);
		fTxtPrimeiraCompra.setToolTipText("N\u00E3o considera or\u00E7amentos");
		fTxtPrimeiraCompra.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtPrimeiraCompra.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtPrimeiraCompra.setEnabled(false);
		fTxtPrimeiraCompra.setBounds(599, 329, 104, 20);
		cliente.add(fTxtPrimeiraCompra);

		lblUltimaCompra = new JLabel("Data da \u00FAltima compra");
		lblUltimaCompra.setToolTipText("");
		lblUltimaCompra.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUltimaCompra.setBounds(448, 364, 145, 20);
		cliente.add(lblUltimaCompra);

		fTxtUltimaCompra = new JFormattedTextField();
		fTxtUltimaCompra.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtUltimaCompra.setEditable(false);
		fTxtUltimaCompra.setToolTipText("N\u00E3o considera or\u00E7amentos");
		fTxtUltimaCompra.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtUltimaCompra.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtUltimaCompra.setEnabled(false);
		fTxtUltimaCompra.setBounds(599, 362, 104, 20);
		cliente.add(fTxtUltimaCompra);

		lblValorDaMaior = new JLabel("Valor da maior compra");
		lblValorDaMaior.setToolTipText("");
		lblValorDaMaior.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblValorDaMaior.setBounds(10, 397, 141, 20);
		cliente.add(lblValorDaMaior);

		fTxtMaiorCompra = new JFormattedTextField();
		fTxtMaiorCompra.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtMaiorCompra.setEditable(false);
		fTxtMaiorCompra.setToolTipText("N\u00E3o considera or\u00E7amentos");
		fTxtMaiorCompra.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtMaiorCompra.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtMaiorCompra.setEnabled(false);
		fTxtMaiorCompra.setBounds(155, 395, 100, 20);
		cliente.add(fTxtMaiorCompra);

		btnLimpaCliente = new JButton();
		btnLimpaCliente.setEnabled(false);
		btnLimpaCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickLimpaDadosCliente) {
				limpar_dados_cliente();
				cliente_selecionado = null;
			}
		});
		btnLimpaCliente.setIcon(icones.getIcone_limpar());
		btnLimpaCliente.setBounds(325, 61, 27, 19);
		cliente.add(btnLimpaCliente);

		fTxtApelido = new JFormattedTextField();
		fTxtApelido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaApelidoCliente) {
				if (fTxtApelido.getText().trim().isEmpty()) {
					alimentar_lista_clientes("APELIDO", null);
				} else {
					alimentar_lista_clientes("APELIDO", fTxtApelido.getText().trim());
				}
			}
		});
		fTxtApelido.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtApelido.setEnabled(false);
		fTxtApelido.setColumns(10);
		fTxtApelido.setBounds(463, 61, 240, 20);
		cliente.add(fTxtApelido);

		lblTextoObservacao = new JLabel(
				"* Os valores acima consideram somente or\u00E7amentos confirmados. (1 ou mais parcelas lan\u00E7adas).");
		lblTextoObservacao.setForeground(Color.BLUE);
		lblTextoObservacao.setToolTipText("");
		lblTextoObservacao.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTextoObservacao.setBounds(10, 458, 634, 20);
		cliente.add(lblTextoObservacao);

		btnPesquisaOrcamento = new JButton("Pesquisar");
		btnPesquisaOrcamento.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickPesquisarOrcamento) {
				Orcamentos_do_cliente orcamentos_do_cliente = new Orcamentos_do_cliente(getPanelOrcamento());
				orcamentos_do_cliente.setLocationRelativeTo(btnNovo);
				orcamentos_do_cliente.setVisible(true);

			}
		});
		btnPesquisaOrcamento.setIcon(icones.getIcone_pesquisar());
		btnPesquisaOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPesquisaOrcamento.setBounds(120, 70, 120, 29);
		add(btnPesquisaOrcamento);
		if (configuracoes_do_sistema.getSo_orcamento()) {
			btnPesquisaOrcamento.setVisible(false);
		}

	}

	// Funções ------------------------------

	// retorna o panel de orçamento.
	public Panel_orcamento getPanelOrcamento() {
		return this;
	}

	public void incluir_produto() {
		if (produto_selecionado != null || editando_produto) {

			produto_incluso = new Produto_Orcamento();

			if (novo_produto(produto_incluso, false)) {
				modelo_tabela.addProduto(produto_incluso);
				limpar_dados_produto();
				produto_selecionado = null;
				calcula_total_mercadorias();
				quantidade_de_produtos = modelo_tabela.getRowCount();
				fTxtQuantidadeTotal.setText(Integer.toString(quantidade_de_produtos));

				// Testa se está utilizando leitor de código de barras
				if (checkboxLeitorBarras.getState()) {
					fTxtCodigoBarra.setEditable(true);
					fTxtCodigoBarra.requestFocus();
				} else {
					fTxtCodigoProduto.requestFocus();
				}

				// Ativa os campos de frete, desconto,porcentagem de desconto e botão imprimir,
				// caso não
				// estejam ativos.
				if (!fTxtFrete.isEditable()) {
					fTxtFrete.setEditable(true);
					fTxtValorDescFinal.setEditable(true);
					fTxtPorcentDescFinal.setEditable(true);
					btnImprimir.setEnabled(true);
				}

				produto_selecionado = null;
				lista_produtos.clear();
			}
		} else {
			JOptionPane.showMessageDialog(lblQuantidade, "Necessário selecionar um produto.",
					"Nenhum produto selecionado", JOptionPane.WARNING_MESSAGE);
			fTxtNomeProduto.requestFocus();
		}

	}

	public void exibir_dados_cliente() {
		fTxtApelido.setText(cliente_selecionado.getApelido());
		fTxtNomeCliente.setText(cliente_selecionado.getNome());
		txtDocumento.setText(cliente_selecionado.getCpf_cnpj());
		fTxtEndereco.setText(cliente_selecionado.getEndereco());
		fTxtReferencia.setText(cliente_selecionado.getReferencia());
		fTxtCidade.setText(cliente_selecionado.getCidade());
		fTxtBairro.setText(cliente_selecionado.getBairro());
		fTxtCep.setText(cliente_selecionado.getCep());
		fTxtNumero.setText(cliente_selecionado.getNumero());
		fTxtEmail.setText(cliente_selecionado.getEmail());
		fTxtCelular.setText(cliente_selecionado.getCelular());
		fTxtTelFixo.setText(cliente_selecionado.getTelefone());

		if (cliente_selecionado.getCpf_cnpj() != null) {

			if (cliente_selecionado.getCpf_cnpj().length() > 14) {
				lblIe.setVisible(true);
				txtIe.setText(cliente_selecionado.getInscricao_estadual());
				txtIe.setVisible(true);
			} else {
				lblIe.setVisible(false);
				txtIe.setText(null);
				txtIe.setVisible(false);
			}
		} else {
			lblIe.setVisible(false);
			txtIe.setText(null);
			txtIe.setVisible(false);

		}

		exibir_resumo_financeiro();
	}

	public void exibir_resumo_financeiro() {
		Resumo_financeiroDAO resumo_dao = new Resumo_financeiroDAO();
		Resumo_financeiro resumo_financeiro = new Resumo_financeiro();
		resumo_financeiro = resumo_dao.carregar_resumo_financeiro(cliente_selecionado, resumo_financeiro);

		if (resumo_financeiro.getPrimeira_compra() != null) {
			fTxtPrimeiraCompra.setText(sdf.format(resumo_financeiro.getPrimeira_compra()));
		}
		if (resumo_financeiro.getUltima_compra() != null) {
			fTxtUltimaCompra.setText(sdf.format(resumo_financeiro.getUltima_compra()));
		}
		if (resumo_financeiro.getValor_aberto() != null) {
			fTxtValorEmAberto.setText(nf3.format(resumo_financeiro.getValor_aberto()));
		}

		if (resumo_financeiro.getMaior_compra() != null) {
			fTxtMaiorCompra.setText(nf3.format(resumo_financeiro.getMaior_compra()));
		}

		if (resumo_financeiro.getTotal_comprado() != null) {
			fTxtTotalVendido.setText(nf3.format(resumo_financeiro.getTotal_comprado()));
		}

	}

	public void salvar_orcamento() {

		if (quantidade_de_produtos > 0) {
			int opcao = JOptionPane.showConfirmDialog(lblQuantidade, "Deseja confirmar o orçamento?\n",
					"Confirmar orçamento.", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);

			Boolean flag = opcao == JOptionPane.YES_OPTION;
			if (flag) {
				if (cliente_vazio()) {
					configuracoes_do_sistema = conf_dao.busca_configuracoes(); // Buscando configurações.
					opcao = JOptionPane.showConfirmDialog(lblQuantidade, "Nenhum cliente foi informado!\n"
							+ "Caso confirmar o orçamento será gravado utilizando o cliente configurado como consumidor final:"
							+ "\n\nConsumidor final" + "\nCódigo: "
							+ configuracoes_do_sistema.getConsumidor_final().getIdCliente().toString() + "\nNome: "
							+ configuracoes_do_sistema.getConsumidor_final().getNome()
							+ "\n\nDeseja confirmar o orçamento?", "Confirmar orçamento.", JOptionPane.YES_OPTION,
							JOptionPane.WARNING_MESSAGE);
					flag = opcao == JOptionPane.YES_OPTION;
				}
			}

			if (flag) {
				Orcamento orcamento = new Orcamento();
				orcamento = montar_orcamento(orcamento);

				orcamento.setCliente(cliente_selecionado);
				OrcamentoDAO orcamento_dao = new OrcamentoDAO();

				if (fTxtNumeroOrcamento.getText().trim().isEmpty()) {
					orcamento_dao.salvar_novo_orcamento(orcamento);
				} else {
					orcamento_dao.salvar_orcamento_editado(orcamento);
				}
				if (orcamento.getId_orcamento() != null) {
					fTxtNumeroOrcamento.setText(orcamento.getId_orcamento().toString());
					JOptionPane.showMessageDialog(lblQuantidade,
							"Orçamento Nº " + orcamento.getId_orcamento() + " salvo corretamente.",
							"Confirmar orçamento.", JOptionPane.NO_OPTION);

					// Testa se o orçamento foi editado e se seu valor original foi alterado.
					flag = false;
					Boolean valor_alterado = orcamento.getValor_total().compareTo(valor_original) != 0;
					if (valor_alterado) {
						if (orcamento.getParcelas() != null) {
							if (orcamento.getParcelas().size() > 0) {
								flag = true;
							}
						} else {
							flag = false;
						}
					}
					if (!flag) {
						opcao = JOptionPane.showConfirmDialog(lblQuantidade,
								"Deseja prosseguir para a manutenção das parcelas?", "Manutenção das parcelas.",
								JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
						flag = opcao == JOptionPane.YES_OPTION;
					}

					if (flag) {
						Faturamento faturamento = new Faturamento(null, orcamento);
						faturamento.abrir_faturamento(faturamento);
					}

					// Testa se após salvar será gerado o pdf do orçamento.
					flag = false;
					configuracoes_do_sistema = new ConfiguracaoDAO().busca_configuracoes();
					switch (configuracoes_do_sistema.getGera_PDF()) {
					case "SIM":
						flag = true;
						break;
					case "PERGUNTAR":
						opcao = JOptionPane.showConfirmDialog(lblQuantidade, "Deseja emitir o orçamento salvo?",
								"Impressao do orçamento.", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
						flag = opcao == JOptionPane.YES_OPTION;
						break;
					}

					if (flag) {
						gera_pdf.monta_pdf_orcamento(orcamento);
					}

				}

				limpar_campos();
				desativar_campos();
			}

		} else {
			JOptionPane.showMessageDialog(lblQuantidade,
					"Necessário informar pelo menos 1 item para criar o orçamento.", "Orçamento sem itens.",
					JOptionPane.WARNING_MESSAGE);
			fTxtNomeProduto.requestFocus();
		}
	}

	public void cancelar_orcamento() {

		Boolean flag = true;
		if (quantidade_de_produtos > 0) {
			int opcao = JOptionPane.showConfirmDialog(lblQuantidade,
					"ATENÇÃO!\nDeseja sair do orçamento atual?\nTODAS AS ALTERAÇÕES REALIZADAS NÃO SERÃO SALVAS.",
					"Sair do orçamento.", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);
			flag = opcao == JOptionPane.YES_OPTION;
		}
		if (flag) {
			desativar_campos();
			limpar_campos();
		}
	}

	public boolean novo_produto(Produto_Orcamento novo_produto, Boolean editando_item) {

		nf.setRoundingMode(RoundingMode.DOWN);
		nf2.setRoundingMode(RoundingMode.DOWN);

		Double quantidade = 0.00;
		Double total_produto = 0.00;
		Double valor_desconto = 0.00;
		Double preco_unitario = 0.00;

		try {
			if (!fTxtQuantidade.getText().trim().isEmpty()) {
				quantidade = nf.parse(fTxtQuantidade.getText()).doubleValue();
			}

			if (!fTxtTotalItem.getText().trim().isEmpty()) {
				total_produto = nf.parse(fTxtTotalItem.getText()).doubleValue();
				total_produto = Double.valueOf(nf2.format(total_produto).replaceAll(",", "\\."));
			}

			if (!fTxtValorDesconto.getText().trim().isEmpty()) {
				valor_desconto = nf.parse(fTxtValorDesconto.getText()).doubleValue();
				valor_desconto = Double.valueOf(nf2.format(valor_desconto).replaceAll(",", "\\."));
			}
			if (!fTxtPrecoUnitario.getText().trim().isEmpty()) {
				preco_unitario = nf.parse(fTxtPrecoUnitario.getText()).doubleValue();
				preco_unitario = Double.valueOf(nf2.format(preco_unitario).replaceAll(",", "\\."));
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		if (quantidade > 0 && total_produto > 0 && valor_desconto < total_produto) {

			String codigo = null;
			String nome_produto = null;
			String codigo_barras = null;
			String fator = null;

			fator = cbxFatorVenda.getSelectedItem().toString();

			if (editando_item) {
				codigo = fTxtCodigoProduto.getText().trim();
				nome_produto = fTxtNomeProduto.getText().trim();

				if (!fTxtCodigoBarra.getText().trim().isEmpty()) {
					codigo_barras = fTxtCodigoBarra.getText().trim();
				}
			} else {
				codigo = produto_selecionado.getIdProduto().toString();
				nome_produto = produto_selecionado.getDescricao();
				if (produto_selecionado.getCodigo_barra() != null) {
					codigo_barras = produto_selecionado.getCodigo_barra();
				}
			}
			novo_produto.setCodigo(Integer.parseInt(codigo));
			novo_produto.setNome(nome_produto);
			novo_produto.setCodigo_barras(codigo_barras);
			novo_produto.setFator_venda(fator);
			novo_produto.setQuantidade(quantidade);
			novo_produto.setPreco_unitario(preco_unitario);
			novo_produto.setValor_desconto(valor_desconto);
			novo_produto.setValor_total(total_produto);
			return true;
		} else {
			if (quantidade <= 0.00) {
				JOptionPane.showMessageDialog(lblQuantidade, "Necessário informar quantidade.", "Quantidade zerada.",
						JOptionPane.WARNING_MESSAGE);
				fTxtQuantidade.setBorder(new LineBorder(Color.RED));
				fTxtQuantidade.requestFocus();
			}

			if (preco_unitario <= 0.00) {
				JOptionPane.showMessageDialog(lblPrecoUnitario, "Necessário informar preço unitário.",
						"Preço unitário zerado!", JOptionPane.WARNING_MESSAGE);
				fTxtPrecoUnitario.setBorder(new LineBorder(Color.RED));
				fTxtPrecoUnitario.requestFocus();
			}

			if (valor_desconto >= preco_unitario && preco_unitario != 0.00) {
				JOptionPane.showMessageDialog(lblValorDesconto, "Valor de desconto maior ou igual ao total do produto.",
						"Valor de desconto inválido!", JOptionPane.WARNING_MESSAGE);
				fTxtValorEmAberto.setBorder(new LineBorder(Color.RED));
				fTxtPorcentagemDesconto.requestFocus();
			}

			return false;
		}
	}

	public void alimentar_lista_clientes(String forma_pesquisa, String texto) {

		String tipo_busca = forma_pesquisa;
		String texto_buscado = texto;

		ClienteDAO cliente_dao = new ClienteDAO();
		cliente_dao.alimenta_lt_clientes(tipo_busca, texto_buscado, list_model, lista_clientes);

		if (!list_model.isEmpty()) {
			scrollPaneListaClientes.setVisible(true);
		} else {
			scrollPaneListaClientes.setVisible(false);
		}

		ltClientes.setModel(list_model);
	}

	public void alimentar_lista_produtos(String tipo_busca, String texto_buscado) {

		list_model_produtos.clear();
		lista_produtos.clear();
		ProdutoDAO produto_dao = new ProdutoDAO();

		switch (tipo_busca) {
		case "NOME":
			lista_produtos = produto_dao.listarProdutosNome(lista_produtos, fTxtNomeProduto.getText().trim() + "%", 50);
			break;
		case "CÓDIGO":
			lista_produtos = produto_dao.listarProdutosCodigo(lista_produtos, fTxtCodigoProduto.getText().trim(), 50);
			break;
		case "BARRAS":
			lista_produtos = produto_dao.listarProdutosBarras(lista_produtos, texto_buscado + "%", 50);
			break;
		default:
			break;
		}

		for (Produto produto : lista_produtos) {
			list_model_produtos.addElement(produto);
		}

		ltProdutos.setModel(list_model_produtos);

		if (!list_model_produtos.isEmpty()) {
			scrollPaneListaProdutos.setVisible(true);
		} else {
			scrollPaneListaProdutos.setVisible(false);
		}
	}

	public void limpar_campos() {
		limpar_dados_cliente();
		limpar_dados_produto();
		limpar_totais_orcamento();
		ConfiguraLarguraColunaTabela(tabelaProdutosInclusos);
		desativa_leitor();
		fTxtNumeroOrcamento.setText(null);
		lista_produtos_inclusos.clear();
		checkboxLeitorBarras.setState(false);
		modelo_tabela.fireTableDataChanged();
	}

	public void limpar_dados_produto() {
		fTxtCodigoProduto.setText(null);
		fTxtNomeProduto.setText(null);
		fTxtCodigoBarra.setText(null);
		cbxFatorVenda.getModel().setSelectedItem("UN");
		fTxtPrecoUnitario.setText(null);
		fTxtPorcentagemDesconto.setText(null);
		fTxtValorDesconto.setText(null);
		fTxtTotalItemComDesconto.setText(null);
		fTxtTotalItem.setText(null);
		produto_selecionado = null;
		btnIncluir.setEnabled(false);
		fTxtQuantidade.setText(null);

		if (checkboxLeitorBarras.getState()) {
			fTxtCodigoProduto.setEditable(false);
			fTxtNomeProduto.setEditable(false);
		} else {
			fTxtCodigoProduto.setEditable(true);
			fTxtNomeProduto.setEditable(true);
		}

		fTxtPorcentagemDesconto.setBorder(new LineBorder(Color.lightGray));
		fTxtQuantidade.setBorder(new LineBorder(Color.lightGray));
		fTxtPrecoUnitario.setBorder(new LineBorder(Color.lightGray));
	}

	public void limpar_totais_orcamento() {
		fTxtQuantidadeTotal.setText(null);
		fTxtTotalMercadoriasBruto.setText(null);
		fTxtTotalMercadoriasLiquido.setText(null);
		fTxtTotalDescontoProduto.setText(null);
		fTxtFrete.setText(null);
		fTxtValorDescFinal.setText(null);
		fTxtPorcentDescFinal.setText(null);
		fTxtTotalOrcamento.setText(null);

		fTxtFrete.setEditable(false);
		fTxtPorcentDescFinal.setEditable(false);
		fTxtValorDescFinal.setEditable(false);

		quantidade_de_produtos = 0;
		total_mercadorias_bruto = 0.00;
		total_mercadorias_liquido = 0.00;
		valor_frete = 0.00;
		desconto_final = 0.00;
		data_inclusao_orcamento = null;
		parcelas = null;
	}

	public void limpar_dados_cliente() {
		scrollPaneListaClientes.setVisible(false);
		fTxtNomeCliente.setText(null);
		fTxtApelido.setText(null);
		fTxtNomeCliente.setText(null);
		txtDocumento.setText(null);
		txtIe.setText(null);
		fTxtEndereco.setText(null);
		fTxtNumero.setText(null);
		fTxtReferencia.setText(null);
		fTxtCidade.setText(null);
		fTxtCep.setText(null);
		fTxtBairro.setText(null);
		fTxtCelular.setText(null);
		fTxtTelFixo.setText(null);
		fTxtEmail.setText(null);

		fTxtValorEmAberto.setText(null);
		fTxtTotalVendido.setText(null);
		fTxtMaiorCompra.setText(null);
		fTxtPrimeiraCompra.setText(null);
		fTxtUltimaCompra.setText(null);
		fTxtNomeCliente.requestFocus();
	}

	public boolean produto_ja_incluso(Integer codigo, ArrayList<Produto_Orcamento> produtos_inclusos) {
		for (Produto_Orcamento produto_orcamento : produtos_inclusos) {
			if (produto_orcamento.getCodigo() == codigo) {
				JOptionPane.showMessageDialog(lblQuantidade, "O produto selecionado já está presente no orçamento.",
						"Produto já incluso anteriormente.", JOptionPane.WARNING_MESSAGE);
				produto_selecionado = null;
				return true;
			}
		}
		return false;
	}

	public boolean produto_bloqueado() {
		if (produto_selecionado.getBloqueadoVenda()) {
			lista_produtos.clear();
			produto_selecionado = null;
			fTxtCodigoProduto.setText(null);
			fTxtNomeProduto.setText(null);
			fTxtCodigoBarra.setText(null);
			JOptionPane.showMessageDialog(lblQuantidade,
					"O produto selecionado está bloqueado.\nAltere o cadastro e retire o bloqueio caso desejar inclui-lo no orçamento.",
					"Produto Bloqueado", JOptionPane.WARNING_MESSAGE);
			return true;
		} else {
			return false;
		}
	}

	public void calcula_valor_desconto_final(KeyEvent digitado) {

		if (digitado == null || teclou_digito(digitado.getKeyChar())
				|| digitado.getKeyCode() == digitado.VK_BACK_SPACE) {
			Double porcent_desc_final = 0.00;
			Double desc_final = 0.00;

			try {
				if (!fTxtPorcentDescFinal.getText().trim().isEmpty()) {
					porcent_desc_final = nf.parse(fTxtPorcentDescFinal.getText()).doubleValue();
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

			desc_final = (total_mercadorias_liquido + valor_frete) * (porcent_desc_final / 100);

			fTxtValorDescFinal.setText(nf.format(desc_final));
		}
	}

	public void calcula_porcent_desc_final() {
		Double porcent_desc_final = 0.00;
		Double valor_desconto = 0.00;
		try {
			if (!fTxtValorDescFinal.getText().isEmpty()) {
				valor_desconto = nf.parse(fTxtValorDescFinal.getText()).doubleValue();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		porcent_desc_final = (valor_desconto * 100) / (total_mercadorias_liquido + valor_frete);
		porcent_desc_final = Math.round(porcent_desc_final * 100) / 100d;

		if (porcent_desc_final > 99.99) {
			fTxtPorcentDescFinal.setText(nf.format(0.00));
			fTxtValorDescFinal.setText(nf.format(0.00));
			JOptionPane.showMessageDialog(lblQuantidade,
					"O valor de desconto informado é igual ou maior ao total do orçamento.",
					"Valor de desconto inválido.", JOptionPane.WARNING_MESSAGE);
		} else {
			fTxtPorcentDescFinal.setText(nf.format(porcent_desc_final));
		}

	}

	public void calcula_porcentagem_desconto() {

		if (produto_selecionado != null || editando_produto) {

			Double valor_produto = 0.00;
			Double valor_desconto = 0.00;
			Double porcentagem_desconto = 0.00;

			try {
				if (!fTxtPrecoUnitario.getText().isEmpty()) {
					valor_produto = nf.parse(fTxtTotalItem.getText()).doubleValue();
				}
				if (!fTxtValorDesconto.getText().isEmpty()) {
					valor_desconto = nf.parse(fTxtValorDesconto.getText()).doubleValue();
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}

			porcentagem_desconto = (valor_desconto * 100.00) / valor_produto;
			porcentagem_desconto = Math.round(porcentagem_desconto * 100) / 100d;

			if (porcentagem_desconto > 99.99) {
				fTxtPorcentagemDesconto.setText(nf.format(99.99));
				fTxtPorcentagemDesconto.setBorder(new LineBorder(Color.red));
			} else {
				fTxtPorcentagemDesconto.setText(nf.format(porcentagem_desconto));
				fTxtPorcentagemDesconto.setBorder(new LineBorder(Color.lightGray));
			}
		}
	}

	public void calcula_valor_desconto(KeyEvent digitado) {

		if (produto_selecionado != null || editando_produto) {

			if (teclou_digito(digitado.getKeyChar()) || digitado.getKeyCode() == digitado.VK_BACK_SPACE) {

				Double total_produto = 0.00;
				Double valor_desconto = 0.00;
				Double porcentagem_desconto = 0.00;

				try {
					if (!fTxtPrecoUnitario.getText().isEmpty()) {
						total_produto = nf.parse(fTxtTotalItem.getText()).doubleValue();
					}
					if (!fTxtPorcentagemDesconto.getText().isEmpty()) {
						porcentagem_desconto = nf.parse(fTxtPorcentagemDesconto.getText()).doubleValue();
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}

				valor_desconto = total_produto * (porcentagem_desconto / 100.00);
				valor_desconto = Math.round(valor_desconto * 10000) / 10000d;
				fTxtValorDesconto.setText(nf.format(valor_desconto));
			}
		}
	}

	public void calcula_total_item() {
		if (produto_selecionado != null || editando_produto) {

			nf.setRoundingMode(RoundingMode.DOWN);

			Double preco_unitario = 0.00;
			Double quantidade = 0.00;
			Double valor_desconto = 0.00;

			try {
				if (!fTxtPrecoUnitario.getText().isEmpty()) {
					preco_unitario = nf.parse(fTxtPrecoUnitario.getText()).doubleValue();
				}

				if (!fTxtQuantidade.getText().isEmpty()) {
					quantidade = nf.parse(fTxtQuantidade.getText()).doubleValue();
				}

				if (!fTxtValorDesconto.getText().isEmpty()) {
					valor_desconto = nf.parse(fTxtValorDesconto.getText()).doubleValue();
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}
			Double total_produto = (preco_unitario * quantidade);
			Double total_produto_com_desconto = (total_produto - valor_desconto);
			
			total_produto =  Math.round(total_produto * 10000) / 10000d;
			total_produto_com_desconto = Math.round(total_produto_com_desconto * 10000) / 10000d;
			
			fTxtTotalItem.setText(nf.format(total_produto));
			fTxtTotalItemComDesconto.setText(nf.format(total_produto_com_desconto));
		}
	}

	public void calcula_total_mercadorias() {

		total_mercadorias_bruto = 0.00;
		total_mercadorias_liquido = 0.00;
		Double desconto = 0.00;

		for (Produto_Orcamento produto : lista_produtos_inclusos) {
			total_mercadorias_bruto += produto.getValor_total();
			desconto += produto.getValor_desconto();
		}

		total_mercadorias_bruto = Math.round(total_mercadorias_bruto * 10000) / 10000d;
		desconto = Math.round(desconto * 10000) / 10000d;
		total_mercadorias_liquido = total_mercadorias_bruto - desconto;

		// Sem considerar desconto dos itens, frete e desconto final do orçamento.
		fTxtTotalMercadoriasBruto.setText(nf3.format(total_mercadorias_bruto));

		// Considerando desconto dos itens
		fTxtTotalMercadoriasLiquido.setText(nf3.format(total_mercadorias_liquido));

		fTxtTotalDescontoProduto.setText(nf3.format(desconto));

		calcula_valor_desconto_final(null);
		calcula_porcent_desc_final();
		calcula_total_orcamento();
	}

	public void calcula_total_orcamento() {

		nf3.setRoundingMode(RoundingMode.DOWN);
		total_orcamento = 0.00;

		if (!fTxtValorDescFinal.getText().isEmpty()) {
			try {
				desconto_final = nf.parse(fTxtValorDescFinal.getText()).doubleValue();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		total_orcamento = total_mercadorias_liquido - desconto_final + valor_frete;
		fTxtTotalOrcamento.setText(nf3.format(total_orcamento));
	}

	public void ativar_campos() {
		// Campos clientes
		fTxtNomeCliente.setEnabled(true);
		fTxtApelido.setEnabled(true);
		txtDocumento.setEnabled(true);
		txtIe.setEnabled(true);
		fTxtCep.setEnabled(true);
		fTxtCidade.setEnabled(true);
		fTxtEndereco.setEnabled(true);
		fTxtNumero.setEnabled(true);
		fTxtReferencia.setEnabled(true);
		fTxtBairro.setEnabled(true);
		fTxtCelular.setEnabled(true);
		fTxtTelFixo.setEnabled(true);
		fTxtEmail.setEnabled(true);
		fTxtValorEmAberto.setEnabled(true);
		fTxtTotalVendido.setEnabled(true);
		fTxtMaiorCompra.setEnabled(true);
		fTxtPrimeiraCompra.setEnabled(true);
		fTxtUltimaCompra.setEnabled(true);

		// Campos produto
		fTxtCodigoProduto.setEnabled(true);
		fTxtNomeProduto.setEnabled(true);
		fTxtCodigoBarra.setEnabled(true);
		cbxFatorVenda.setEnabled(true);
		fTxtQuantidade.setEnabled(true);
		fTxtPrecoUnitario.setEnabled(true);
		fTxtPorcentagemDesconto.setEnabled(true);
		fTxtValorDesconto.setEnabled(true);
		fTxtQuantidadeTotal.setEnabled(true);
		fTxtTotalMercadoriasLiquido.setEnabled(true);
		fTxtTotalMercadoriasBruto.setEnabled(true);
		fTxtTotalItemComDesconto.setEnabled(true);
		fTxtTotalOrcamento.setEnabled(true);
		checkboxLeitorBarras.setVisible(true);

		// Campos gerais
		tabbedPane.setEnabled(true);
		btnCancelar.setVisible(true);
		configuracoes_do_sistema = conf_dao.busca_configuracoes();
		if (!configuracoes_do_sistema.getSo_orcamento()) {
			btnSalvar.setVisible(true);
		}
		btnLimpaCliente.setEnabled(true);
		btnLimpaDadosProduto.setEnabled(true);
		btnImprimir.setVisible(true);
	}

	public void desativar_campos() {
		// Campos clientes
		fTxtNomeCliente.setEnabled(false);
		fTxtApelido.setEnabled(false);
		txtDocumento.setEnabled(false);
		txtIe.setEnabled(false);
		fTxtCep.setEnabled(false);
		fTxtCidade.setEnabled(false);
		fTxtEndereco.setEnabled(false);
		fTxtNumero.setEnabled(false);
		fTxtReferencia.setEnabled(false);
		fTxtBairro.setEnabled(false);
		fTxtCelular.setEnabled(false);
		fTxtTelFixo.setEnabled(false);
		fTxtEmail.setEnabled(false);
		fTxtValorEmAberto.setEnabled(false);
		fTxtTotalVendido.setEnabled(false);
		fTxtMaiorCompra.setEnabled(false);
		fTxtPrimeiraCompra.setEnabled(false);
		fTxtUltimaCompra.setEnabled(false);
		cliente_selecionado = null;

		// Campos produto
		fTxtCodigoProduto.setEnabled(false);
		fTxtNomeProduto.setEnabled(false);
		fTxtCodigoBarra.setEnabled(false);
		cbxFatorVenda.setEnabled(false);
		fTxtQuantidade.setEnabled(false);
		fTxtPrecoUnitario.setEnabled(false);
		fTxtTotalItemComDesconto.setEnabled(false);
		fTxtPorcentagemDesconto.setEnabled(false);
		fTxtValorDesconto.setEnabled(false);
		fTxtFrete.setEditable(false);
		fTxtValorDescFinal.setEditable(false);
		fTxtPorcentDescFinal.setEditable(false);
		btnIncluir.setEnabled(false);
		produto_selecionado = null;

		// Campos gerais
		tabbedPane.setEnabled(false);
		btnCancelar.setVisible(false);
		btnSalvar.setVisible(false);
		btnLimpaDadosProduto.setEnabled(false);
		configuracoes_do_sistema = conf_dao.busca_configuracoes();
		if (!configuracoes_do_sistema.getSo_orcamento()) {
			btnPesquisaOrcamento.setVisible(true);
		}
		btnNovo.setVisible(true);
		tabbedPane.setSelectedIndex(0);
		checkboxLeitorBarras.setVisible(false);
		btnImprimir.setVisible(false);
		btnImprimir.setEnabled(false);
	}

	public void editar_produto() {
		editando_produto = true;

		checkboxLeitorBarras.setEnabled(false);

		// Utiliza leitor, bloquear o campo durante edição.
		if (checkboxLeitorBarras.getState()) {
			fTxtCodigoBarra.setEditable(false);
		}

		Double quantidade = Double.parseDouble(tabelaProdutosInclusos
				.getValueAt(tabelaProdutosInclusos.getSelectedRow(), 4).toString().replace(".", "").replace(",", "."));

		Double preco_unit = 0.00;
		Double desconto = 0.00;
		Double total = 0.00;

		try {
			preco_unit = Double
					.parseDouble(tabelaProdutosInclusos.getValueAt(tabelaProdutosInclusos.getSelectedRow(), 5)
							.toString().replace(".", "").replace(",", ".").replace("R$ ", ""));

			desconto = Double.parseDouble(tabelaProdutosInclusos.getValueAt(tabelaProdutosInclusos.getSelectedRow(), 6)
					.toString().replace(".", "").replace(",", ".").replace("R$ ", ""));

			total = Double.parseDouble(tabelaProdutosInclusos.getValueAt(tabelaProdutosInclusos.getSelectedRow(), 7)
					.toString().replace(".", "").replace(",", ".").replace("R$ ", ""));
		} catch (Exception e) {
			e.printStackTrace();
		}

		fTxtCodigoProduto
				.setText(tabelaProdutosInclusos.getValueAt(tabelaProdutosInclusos.getSelectedRow(), 0).toString());
		fTxtNomeProduto.setText((String) tabelaProdutosInclusos.getValueAt(tabelaProdutosInclusos.getSelectedRow(), 1));

		fTxtCodigoBarra.setText((String) tabelaProdutosInclusos.getValueAt(tabelaProdutosInclusos.getSelectedRow(), 2));

		cbxFatorVenda.getModel().setSelectedItem(
				(String) tabelaProdutosInclusos.getValueAt(tabelaProdutosInclusos.getSelectedRow(), 3));
		fTxtQuantidade
				.setText(tabelaProdutosInclusos.getValueAt(tabelaProdutosInclusos.getSelectedRow(), 4).toString());
		fTxtPrecoUnitario.setText(nf.format(preco_unit));
		fTxtTotalItem.setText(nf.format(total + desconto));
		fTxtTotalItemComDesconto.setText(nf.format(total));
		fTxtValorDesconto.setText(nf.format(desconto));
		calcula_porcentagem_desconto();
		
		fTxtNomeProduto.setEditable(false);
		fTxtCodigoProduto.setEditable(false);

		btnSalvar.setVisible(false);
		btnIncluir.setVisible(false);
		btnCancelar.setVisible(false);
		btnEditar.setVisible(false);
		btnExcluir.setVisible(false);
		btnLimpaDadosProduto.setEnabled(false);

		btnSalvar_editado.setVisible(true);
		btnCancelar_editado.setVisible(true);
		btnSalvar_editado.setEnabled(true);
		btnCancelar_editado.setEnabled(true);
		fTxtQuantidade.requestFocus();
	}

	public void salvar_edicao() {
		Produto_Orcamento produto = new Produto_Orcamento();

		if (novo_produto(produto, true)) {
			for (Produto_Orcamento produto_editado : lista_produtos_inclusos) {
				if (produto_editado.getCodigo().equals(Integer.parseInt(fTxtCodigoProduto.getText().trim()))) {
					lista_produtos_inclusos.set(lista_produtos_inclusos.indexOf(produto_editado), produto);
				}
			}
			modelo_tabela.fireTableDataChanged();
			cancelar_edicao();
			JOptionPane.showMessageDialog(lblQuantidade, "Produto alterado com sucesso!", " Alteração de produto.",
					JOptionPane.NO_OPTION);
			calcula_total_mercadorias();
			btnLimpaDadosProduto.setEnabled(true);

			checkboxLeitorBarras.setEnabled(true);

			// Se utiliza leitor, libera o campos após edição.
			if (checkboxLeitorBarras.getState()) {
				fTxtCodigoBarra.setEditable(true);
				fTxtCodigoBarra.requestFocus();
			} else {
				fTxtCodigoProduto.setEditable(true);
				fTxtNomeProduto.setEditable(true);
				fTxtCodigoProduto.requestFocus();
			}

			editando_produto = false;
		}
	}

	public void cancelar_edicao() {
		tabelaProdutosInclusos.clearSelection();
		btnCancelar_editado.setVisible(false);
		btnSalvar_editado.setVisible(false);
		btnSalvar_editado.setEnabled(false);
		btnCancelar_editado.setEnabled(false);
		btnSalvar.setVisible(true);
		btnCancelar.setVisible(true);
		btnEditar.setVisible(true);
		btnEditar.setEnabled(false);
		btnExcluir.setVisible(true);
		btnExcluir.setEnabled(false);
		btnIncluir.setVisible(true);
		btnLimpaDadosProduto.setEnabled(true);
		limpar_dados_produto();

		checkboxLeitorBarras.setEnabled(true);

		// se usa leitor, libera campo de código de barras
		if (checkboxLeitorBarras.getState()) {
			fTxtCodigoBarra.setEditable(true);
			fTxtCodigoBarra.requestFocus();
		} else {
			fTxtNomeProduto.setEditable(true);
			fTxtCodigoProduto.setEditable(true);
		}
		editando_produto = false;
	}

	public Boolean excluir_produto(String codigo) {
		int opcao = JOptionPane.showConfirmDialog(lblQuantidade,
				"Deseja remover o seguinte produto do orçamento?\n" + "Cod = "
						+ tabelaProdutosInclusos.getValueAt(tabelaProdutosInclusos.getSelectedRow(), 0) + "\n"
						+ "Nome = " + tabelaProdutosInclusos.getValueAt(tabelaProdutosInclusos.getSelectedRow(), 1),
				"Remoção de produtos", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

		Boolean flag = opcao == JOptionPane.YES_OPTION;

		if (flag) {
			for (Produto_Orcamento produto_excluido : lista_produtos_inclusos) {
				if (produto_excluido.getCodigo().equals(Integer.parseInt(codigo))) {
					modelo_tabela.removeProduto(lista_produtos_inclusos.indexOf(produto_excluido));
					return true;
				}
			}
		}
		return false;
	}

	public void novo_orcamento() {
		ativar_campos();
		btnNovo.setVisible(false);
		btnPesquisaOrcamento.setVisible(false);
		fTxtCodigoProduto.requestFocus();
	}

	public void editar_orcamento(Orcamento orcamento_informado) {
		cliente_selecionado = orcamento_informado.getCliente();
		total_mercadorias_bruto = orcamento_informado.getTotal_mercadorias_bruto();
		total_mercadorias_liquido = orcamento_informado.getTotal_mercadorias_liquido();
		valor_frete = orcamento_informado.getFrete();
		desconto_final = orcamento_informado.getDesconto_final();
		total_orcamento = orcamento_informado.getValor_total();
		valor_original = total_orcamento;
		data_inclusao_orcamento = orcamento_informado.getData_inclusao();

		if (orcamento_informado.getData_faturamento() != null) {
			data_faturamento = orcamento_informado.getData_faturamento();
		}

		if (orcamento_informado.getParcelas().size() > 0) {
			parcelas = orcamento_informado.getParcelas();
		}

		// Ativando campos de frete, porcentagem de desconto final e valor de desconto
		// final.
		fTxtFrete.setEditable(true);
		fTxtPorcentDescFinal.setEditable(true);
		fTxtValorDescFinal.setEditable(true);

		novo_orcamento();
		exibir_dados_cliente();
	}

	public Orcamento montar_orcamento(Orcamento orcamento) {
		nf2.setRoundingMode(RoundingMode.DOWN);

		Integer id_orcamento;
		if (fTxtNumeroOrcamento.getText().trim().isEmpty()) {
			id_orcamento = null;
			data_inclusao_orcamento = new Date();
		} else {
			id_orcamento = Integer.parseInt(fTxtNumeroOrcamento.getText().trim());
		}

		Boolean faturado = false;
		Integer numero_de_parcelas = 0;

		if (cliente_vazio()) {
			configuracoes_do_sistema = conf_dao.busca_configuracoes();
			cliente_selecionado = configuracoes_do_sistema.getConsumidor_final();
		}

		orcamento = new Orcamento(id_orcamento, cliente_selecionado, quantidade_de_produtos,
				Double.valueOf(nf2.format(total_mercadorias_bruto).replaceAll(",", "\\.")),
				Double.valueOf(nf2.format(total_mercadorias_liquido).replaceAll(",", "\\.")), valor_frete,
				desconto_final, Double.valueOf(nf2.format(total_orcamento).replaceAll(",", "\\.")), faturado,
				numero_de_parcelas, null, data_inclusao_orcamento, data_faturamento, lista_produtos_inclusos, parcelas);

		return orcamento;
	}

	public void setNumeroOrcamento(Orcamento orcamento) {
		fTxtNumeroOrcamento.setText(orcamento.getId_orcamento().toString());
	}

	public Integer getNumeroOrcamento() {
		return Integer.parseInt(fTxtNumeroOrcamento.getText().trim());
	}

	public void ConfiguraLarguraColunaTabela(JTable tabela_produtos_inclusos) {
		tabela_produtos_inclusos.getColumnModel().getColumn(0).setPreferredWidth(55); // Código
		tabela_produtos_inclusos.getColumnModel().getColumn(1).setPreferredWidth(165); // Nome
		tabela_produtos_inclusos.getColumnModel().getColumn(2).setPreferredWidth(95); // barras
		tabela_produtos_inclusos.getColumnModel().getColumn(3).setPreferredWidth(53); // Unid
		tabela_produtos_inclusos.getColumnModel().getColumn(4).setPreferredWidth(68); // Quantidade
		tabela_produtos_inclusos.getColumnModel().getColumn(5).setPreferredWidth(80); // Preço Unit.
		tabela_produtos_inclusos.getColumnModel().getColumn(6).setPreferredWidth(80); // Desconto
		tabela_produtos_inclusos.getColumnModel().getColumn(7).setPreferredWidth(96); // Total produto

		// Alinhamento das colunas.
		esquerda.setHorizontalAlignment(SwingConstants.LEFT);
		tabela_produtos_inclusos.getColumnModel().getColumn(4).setCellRenderer(esquerda); // Quantidade
		tabela_produtos_inclusos.getColumnModel().getColumn(5).setCellRenderer(esquerda); // Preco Unit
		tabela_produtos_inclusos.getColumnModel().getColumn(6).setCellRenderer(esquerda); // Desconto
		tabela_produtos_inclusos.getColumnModel().getColumn(7).setCellRenderer(esquerda); // Total
	}

	public void lista_produtos_do_orcamento_selecionado(Orcamento orcamento_selecionado) {
		Double porcent_desc_final = 0.00;

		if (orcamento_selecionado.getDesconto_final() != null) {
			porcent_desc_final = (orcamento_selecionado.getDesconto_final() * 100)
					/ (orcamento_selecionado.getTotal_mercadorias_liquido() + orcamento_selecionado.getFrete());
			porcent_desc_final = Math.round(porcent_desc_final * 10000) / 10000d;
		}

		lista_produtos_inclusos.clear();
		lista_produtos_inclusos.addAll(orcamento_selecionado.getProdutos_do_orcamento());
		modelo_tabela.fireTableDataChanged();

		fTxtQuantidadeTotal.setText(orcamento_selecionado.getQuantidade_produtos().toString());
		fTxtTotalMercadoriasBruto.setText(nf3.format(orcamento_selecionado.getTotal_mercadorias_bruto()));
		fTxtTotalMercadoriasLiquido.setText(nf3.format(orcamento_selecionado.getTotal_mercadorias_liquido()));
		fTxtFrete.setText(nf.format(orcamento_selecionado.getFrete()));
		fTxtValorDescFinal.setText(nf.format(orcamento_selecionado.getDesconto_final()));
		fTxtPorcentDescFinal.setText(nf.format(porcent_desc_final));
		fTxtTotalOrcamento.setText(nf3.format(orcamento_selecionado.getValor_total()));
		fTxtTotalDescontoProduto.setText(nf3.format(orcamento_selecionado.getTotal_mercadorias_bruto()
				- orcamento_selecionado.getTotal_mercadorias_liquido()));

		quantidade_de_produtos = orcamento_selecionado.getQuantidade_produtos();
	}

	public Boolean seleciona_produto() {
		if (lista_produtos.size() == 1) {
			produto_selecionado = lista_produtos.get(0);
			if (!produto_bloqueado()) {
				if (produto_ja_incluso(produto_selecionado.getIdProduto(), lista_produtos_inclusos)) {
					limpar_dados_produto();
					return false;
				} else {
					exibe_dados_produto_selecionado();
					fTxtCodigoBarra.setEditable(false);
					fTxtCodigoProduto.setEditable(false);
					fTxtNomeProduto.setEditable(false);
					fTxtQuantidade.requestFocus();
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void ativa_leitor() {
		if (produto_selecionado == null) {
			checkboxLeitorBarras.setState(true);
			limpar_dados_produto();
			fTxtCodigoProduto.setEditable(false);
			fTxtNomeProduto.setEditable(false);
			fTxtCodigoBarra.setEditable(true);
			fTxtCodigoBarra.requestFocus();
		}
	}

	public void desativa_leitor() {
		if (produto_selecionado == null) {
			limpar_dados_produto();
			checkboxLeitorBarras.setState(false);
			fTxtCodigoProduto.setEditable(true);
			fTxtNomeProduto.setEditable(true);
			fTxtCodigoBarra.setEditable(false);
			fTxtCodigoProduto.requestFocus();
		}
	}

	public void exibe_dados_produto_selecionado() {
		fTxtCodigoProduto.setText(produto_selecionado.getIdProduto().toString());
		fTxtNomeProduto.setText(produto_selecionado.getDescricao());
		fTxtCodigoBarra.setText(produto_selecionado.getCodigo_barra());
		cbxFatorVenda.getModel().setSelectedItem(produto_selecionado.getUnidadeVenda());
		fTxtPrecoUnitario.setText(nf.format(produto_selecionado.getPrecoVenda()));

		btnIncluir.setEnabled(true);
		fTxtNomeProduto.setEditable(false);
		fTxtCodigoProduto.setEditable(false);
		fTxtCodigoBarra.setEditable(false);
	}

	public Boolean cliente_vazio() {
		cliente_vazio = cliente_selecionado == null;
		return cliente_vazio;
	}

	public Boolean teclou_digito(char tecla) {
		if (Character.isDigit(tecla)) {
			return true;
		} else {
			return false;
		}
	}

	public void so_orcamento(Boolean so_orcamento) {
		Boolean orcamento_em_andamento = !btnNovo.isVisible();
		if (!so_orcamento) {
			if (orcamento_em_andamento) {
				btnSalvar.setVisible(true);
			} else {
				btnPesquisaOrcamento.setVisible(true);
			}
			conf_dao.so_orcamentos(false);
		} else {
			if (orcamento_em_andamento) {
				btnSalvar.setVisible(false);
			} else {
				btnPesquisaOrcamento.setVisible(false);
			}
			conf_dao.so_orcamentos(true);
		}
	}

	// Teclas de atalho
	public void tecla_pressionada() {
		InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "leitor_barras");

		setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);

		getActionMap().put("leitor_barras", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_leitor_barras) {
				if (!btnNovo.isVisible()) {
					if (checkboxLeitorBarras.getState()) {
						desativa_leitor();
					} else {
						ativa_leitor();
					}
				}
			}
		});
	}
}
