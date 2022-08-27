package view.panels.pedido;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

import entities.produto.Produto_cadastro;
import entities.produto.Produto_pedido;
import icons.Icones;
import tables.tableModels.ModeloTabelaProdutos_Pedido;
import tools.JTextFieldLimit;
import tools.Jlist_tools;
import tools.Jtext_tools;
import view.formatFields.FormataNumeral;

public class Panel_pedido extends JPanel {
	private JSeparator separador_titulo;
	private JLabel lblPedidosDeCompra;
	private JLabel lblFornecedor;
	private JSeparator separador_fornecedor;
	private JLabel lblCodigoFornecedor;
	private JLabel lblNomeFornecedor;
	private JFormattedTextField fTxtNomeFornecedor;
	private JLabel lblDocumento;
	private JTextField fTxtDocumento;
	private JLabel lblNomeFantasia;
	private JFormattedTextField fTxtNomeFantasia;
	private JLabel lblProdutos;
	private JSeparator separador_produto;
	private JLabel lblCodigoProduto;
	private JLabel lblNomeProduto;
	private JFormattedTextField fTxtNomeProduto;
	private JLabel lblQuantidadePedida;
	private JLabel lblBarras;
	private JFormattedTextField fTxtCodigoFornecedor;
	private JFormattedTextField fTxtCodigoProduto;
	private JFormattedTextField fTxtQuantidadePedida;
	private JButton btnIncluir;
	private Icones icones = new Icones();
	private JButton btnEditar;
	private JButton btnExcluir;
	private JLabel lblFator;
	private JComboBox<String> cbxFatorVenda;
	private JPanel panelNumeroOrcamento;
	private JTextField txtNumeroPedido;
	private JLabel lblNumeroPedido;
	private JLabel lblProdutosInclusos;
	private JScrollPane scrollPaneTabelaProdutosInclusos;
	private JSeparator separador_produtos_inclusos;
	private JButton btnNovo;
	private JButton btnPesquisar;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JLabel lblEsc;
	private JLabel lblCancelar;
	private JLabel lblF1;
	private JLabel lblNovo;
	private JLabel lblF3;
	private JLabel lblEditar;
	private JLabel lblF5;
	private JLabel lblPesquisarPedidos;
	private JLabel lblF7;
	private JLabel lblAlterarFornecedor;
	private JLabel lblF12;
	private JLabel lblExcluir;
	private JPanel panelTotalItem;
	private JFormattedTextField fTxtTotalPedido;
	private JLabel lblTotalItem;
	private JLabel lblNumeroProdutos;
	private JTextField txtNumeroProdutos;
	private JLabel lblPrecoUnitario;
	private JFormattedTextField fTxtPrecoUnit;
	private JLabel lblTotal;
	private JFormattedTextField fTxtTotalItem;
	private JLabel lblQuantidadeEntregue;
	private JFormattedTextField fTxtQuantidadeEntregue;
	private JLabel lblCustoAtual;
	private JFormattedTextField fTxtCustoAtual;
	private JPanel panelNumeroOrcamento_1;
	private JTextField txtNumeroCotacao;
	private JLabel lblCotaoN;
	private JFormattedTextField fTxtCodigoBarras;
	private NumberFormat nf = new DecimalFormat(",##0.00");
	private JScrollPane scrollPaneListaProdutos;
	private JList<Produto_cadastro> ltProdutos;
	private DefaultListModel<Produto_cadastro> list_model_produtos = new DefaultListModel<Produto_cadastro>();
	private ArrayList<Produto_cadastro> lista_produtos = new ArrayList<Produto_cadastro>();
	private ArrayList<Produto_pedido> produtos_inclusos = new ArrayList<Produto_pedido>();
	private Jlist_tools jlist_tools = new Jlist_tools();
	private Jtext_tools text_tools = new Jtext_tools();
	private JTable tabelaProdutos;
	private ModeloTabelaProdutos_Pedido modelo_tabela = new ModeloTabelaProdutos_Pedido(produtos_inclusos);

	/**
	 * Create the panel.
	 */
	public Panel_pedido() {
		setLayout(null);

		ltProdutos = new JList<Produto_cadastro>();
		ltProdutos.setBounds(238, 251, 390, 117);
		scrollPaneListaProdutos = new JScrollPane(ltProdutos);
		scrollPaneListaProdutos.setBounds(238, 249, 390, 117);
		scrollPaneListaProdutos.setVisible(false);
		add(scrollPaneListaProdutos);

		separador_titulo = new JSeparator();
		separador_titulo.setBounds(10, 50, 999, 9);
		add(separador_titulo);

		lblPedidosDeCompra = new JLabel("Pedido de Compra");
		lblPedidosDeCompra.setBounds(393, 11, 244, 29);
		lblPedidosDeCompra.setHorizontalAlignment(SwingConstants.CENTER);
		lblPedidosDeCompra.setFont(new Font("Tahoma", Font.BOLD, 24));
		add(lblPedidosDeCompra);

		lblFornecedor = new JLabel("Fornecedor");
		lblFornecedor.setBounds(10, 120, 103, 29);
		lblFornecedor.setHorizontalAlignment(SwingConstants.CENTER);
		lblFornecedor.setFont(new Font("Tahoma", Font.BOLD, 18));
		add(lblFornecedor);

		separador_fornecedor = new JSeparator();
		separador_fornecedor.setBounds(119, 137, 890, 9);
		add(separador_fornecedor);

		lblCodigoFornecedor = new JLabel("Código");
		lblCodigoFornecedor.setBounds(11, 155, 46, 20);
		lblCodigoFornecedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblCodigoFornecedor);

		lblNomeFornecedor = new JLabel("Nome");
		lblNomeFornecedor.setBounds(168, 157, 40, 20);
		lblNomeFornecedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblNomeFornecedor);

		fTxtNomeFornecedor = new JFormattedTextField();
		fTxtNomeFornecedor.setBounds(207, 155, 273, 20);
		JTextFieldLimit limitNome_fornecedor = new JTextFieldLimit(32, "texto");
		fTxtNomeFornecedor.setDocument(limitNome_fornecedor);
		fTxtNomeFornecedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtNomeFornecedor.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeFornecedor.setEditable(false);
		add(fTxtNomeFornecedor);

		lblDocumento = new JLabel("Documento");
		lblDocumento.setBounds(799, 157, 78, 17);
		lblDocumento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblDocumento);

		fTxtDocumento = new JFormattedTextField();
		fTxtDocumento.setBounds(875, 155, 134, 20);
		fTxtDocumento.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtDocumento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtDocumento.setEditable(false);
		add(fTxtDocumento);

		lblNomeFantasia = new JLabel("Nome Fant.");
		lblNomeFantasia.setBounds(506, 155, 78, 20);
		lblNomeFantasia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblNomeFantasia);

		fTxtNomeFantasia = new JFormattedTextField();
		fTxtNomeFantasia.setBounds(582, 155, 202, 20);
		JTextFieldLimit limitFantasia_fornecedor = new JTextFieldLimit(23, "texto");
		fTxtNomeFantasia.setDocument(limitFantasia_fornecedor);
		fTxtNomeFantasia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtNomeFantasia.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeFantasia.setEditable(false);
		add(fTxtNomeFantasia);

		lblProdutos = new JLabel("Produto");
		lblProdutos.setBounds(10, 190, 72, 29);
		lblProdutos.setHorizontalAlignment(SwingConstants.CENTER);
		lblProdutos.setFont(new Font("Tahoma", Font.BOLD, 18));
		add(lblProdutos);

		separador_produto = new JSeparator();
		separador_produto.setBounds(85, 207, 924, 9);
		add(separador_produto);

		lblCodigoProduto = new JLabel("Código");
		lblCodigoProduto.setBounds(10, 233, 48, 20);
		lblCodigoProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblCodigoProduto);

		lblNomeProduto = new JLabel("Nome");
		lblNomeProduto.setBounds(194, 233, 40, 20);
		lblNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblNomeProduto);

		fTxtNomeProduto = new JFormattedTextField();
		fTxtNomeProduto.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNomeProduto) {
				text_tools.move_cursor_inicio(fTxtNomeProduto);
			}
		});
		fTxtNomeProduto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaNomeProduto) {
				jlist_tools.alimentar_lista_produtos("NOME", fTxtNomeProduto.getText().trim(), lista_produtos,
						list_model_produtos, scrollPaneListaProdutos, ltProdutos, fTxtNomeProduto, fTxtCodigoProduto);
			}
		});
		fTxtNomeProduto.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent perdaFocoNomeProduto) {
				scrollPaneListaProdutos.setVisible(false);
			}

			@Override
			public void focusGained(FocusEvent ganhoFocoNomeProduto) {
				// Testa se est� editando o produto ou incluindo.
				if (btnEditar.isVisible() && fTxtNomeProduto.isEditable()) {
					jlist_tools.alimentar_lista_produtos("NOME", "%", lista_produtos, list_model_produtos,
							scrollPaneListaProdutos, ltProdutos, fTxtNomeProduto, fTxtCodigoProduto);
				}
			}
		});
		fTxtNomeProduto.setBounds(238, 230, 390, 20);
		JTextFieldLimit limitNome_produto = new JTextFieldLimit(40, "texto");
		fTxtNomeProduto.setDocument(limitNome_produto);
		fTxtNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtNomeProduto.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeProduto.setEditable(false);
		add(fTxtNomeProduto);

		lblQuantidadePedida = new JLabel("Quantidade Pedida");
		lblQuantidadePedida.setBounds(194, 265, 117, 20);
		lblQuantidadePedida.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblQuantidadePedida);

		lblBarras = new JLabel("Código de barras");
		lblBarras.setBounds(780, 233, 112, 20);
		lblBarras.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblBarras);

		fTxtCodigoFornecedor = new JFormattedTextField();
		fTxtCodigoFornecedor.setBounds(58, 155, 85, 20);
		JTextFieldLimit limitDocument_fornecedor = new JTextFieldLimit(6, "inteiro");
		fTxtCodigoFornecedor.setDocument(limitDocument_fornecedor);
		fTxtCodigoFornecedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCodigoFornecedor.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCodigoFornecedor.setEditable(false);
		add(fTxtCodigoFornecedor);

		fTxtCodigoProduto = new JFormattedTextField();
		fTxtCodigoProduto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaCodigoProduto) {
				jlist_tools.alimentar_lista_produtos("CóDIGO", fTxtCodigoProduto.getText().trim(), lista_produtos,
						list_model_produtos, scrollPaneListaProdutos, ltProdutos, fTxtNomeProduto, fTxtCodigoProduto);
			}
		});
		fTxtCodigoProduto.setBounds(58, 230, 85, 20);
		fTxtCodigoProduto.setHorizontalAlignment(SwingConstants.RIGHT);
		JTextFieldLimit limitCodigo_produto = new JTextFieldLimit(6, "inteiro");
		fTxtCodigoProduto.setDocument(limitCodigo_produto);
		fTxtCodigoProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCodigoProduto.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCodigoProduto.setEditable(false);
		add(fTxtCodigoProduto);

		fTxtQuantidadePedida = new JFormattedTextField();
		fTxtQuantidadePedida.setBounds(315, 265, 94, 20);
		fTxtQuantidadePedida.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaQuantidadePedida) {
				calcula_total_item();
			}
		});
		fTxtQuantidadePedida.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtQuantidadePedida.setDocument(new FormataNumeral(6, 0));
		fTxtQuantidadePedida.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtQuantidadePedida.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtQuantidadePedida.setEditable(false);
		add(fTxtQuantidadePedida);

		btnIncluir = new JButton("Incluir");
		btnIncluir.setBounds(696, 299, 93, 29);
		btnIncluir.setEnabled(false);
		btnIncluir.setIcon(icones.getIcone_mais());
		btnIncluir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(btnIncluir);

		btnEditar = new JButton("Editar");
		btnEditar.setBounds(799, 299, 93, 29);
		btnEditar.setEnabled(false);
		btnEditar.setIcon(icones.getIcone_editar());
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(btnEditar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(902, 298, 107, 29);
		btnExcluir.setEnabled(false);
		btnExcluir.setIcon(icones.getIcone_excluir());
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(btnExcluir);

		lblFator = new JLabel("Fator");
		lblFator.setBounds(10, 266, 40, 19);
		lblFator.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblFator);

		cbxFatorVenda = new JComboBox<String>();
		cbxFatorVenda.setEnabled(false);
		cbxFatorVenda.setBounds(58, 264, 85, 22);
		cbxFatorVenda.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (cbxFatorVenda.getSelectedItem().toString().equals("MT")
						|| cbxFatorVenda.getSelectedItem().toString().equals("KG")
						|| cbxFatorVenda.getSelectedItem().toString().equals("L")
						|| cbxFatorVenda.getSelectedItem().toString().equals("PAR")) {
					fTxtQuantidadePedida.setText(null);
					fTxtQuantidadePedida.setDocument(new FormataNumeral(9, 2));
				} else {
					fTxtQuantidadePedida.setText(null);
					fTxtQuantidadePedida.setDocument(new FormataNumeral(6, 0));
				}
			}
		});
		cbxFatorVenda
				.setModel(new DefaultComboBoxModel(new String[] { "UN", "PAR", "MT", "KG", "L", "CX", "FD", "PCT" }));
		cbxFatorVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(cbxFatorVenda);

		panelNumeroOrcamento = new JPanel();
		panelNumeroOrcamento.setBounds(811, 57, 198, 29);
		panelNumeroOrcamento.setLayout(null);
		panelNumeroOrcamento.setBorder(UIManager.getBorder("DesktopIcon.border"));
		add(panelNumeroOrcamento);

		txtNumeroPedido = new JTextField();
		txtNumeroPedido.setToolTipText("Gerado Automaticamente");
		txtNumeroPedido.setHorizontalAlignment(SwingConstants.RIGHT);
		txtNumeroPedido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNumeroPedido.setEditable(false);
		txtNumeroPedido.setColumns(10);
		txtNumeroPedido.setBounds(91, 4, 100, 20);
		panelNumeroOrcamento.add(txtNumeroPedido);

		lblNumeroPedido = new JLabel("Pedido Nº");
		lblNumeroPedido.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNumeroPedido.setBounds(10, 5, 70, 19);
		panelNumeroOrcamento.add(lblNumeroPedido);

		lblProdutosInclusos = new JLabel("Produtos Inclusos");
		lblProdutosInclusos.setBounds(11, 363, 163, 29);
		lblProdutosInclusos.setHorizontalAlignment(SwingConstants.CENTER);
		lblProdutosInclusos.setFont(new Font("Tahoma", Font.BOLD, 18));
		add(lblProdutosInclusos);

		tabelaProdutos = new JTable(modelo_tabela);
		tabelaProdutos.setBounds(10, 403, 999, 142);
		tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaProdutos.setAutoResizeMode(tabelaProdutos.AUTO_RESIZE_OFF);
		tabelaProdutos.getTableHeader().setReorderingAllowed(false);

		scrollPaneTabelaProdutosInclusos = new JScrollPane(tabelaProdutos);
		scrollPaneTabelaProdutosInclusos.setBounds(10, 403, 999, 142);
		add(scrollPaneTabelaProdutosInclusos);
		
		ConfiguraLarguraColunaTabela(tabelaProdutos);

		separador_produtos_inclusos = new JSeparator();
		separador_produtos_inclusos.setBounds(181, 379, 828, 9);
		add(separador_produtos_inclusos);

		btnNovo = new JButton("Novo");
		btnNovo.setBounds(10, 70, 93, 35);
		btnNovo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNovoPedido) {
				novo_pedido();
			}
		});
		btnNovo.setIcon(icones.getIcone_mais());
		btnNovo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(btnNovo);

		btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setBounds(119, 70, 116, 35);
		btnPesquisar.setIcon(icones.getIcone_pesquisar());
		btnPesquisar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(btnPesquisar);

		btnSalvar = new JButton("Salvar Pedido");
		btnSalvar.setBounds(743, 556, 144, 35);
		btnSalvar.setIcon(icones.getIcone_salvar());
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSalvar.setVisible(false);
		add(btnSalvar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(894, 556, 115, 35);
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancelarPedido) {
				cancela_pedido();
			}
		});
		btnCancelar.setIcon(icones.getIcone_cancelar());
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.setVisible(false);
		add(btnCancelar);

		lblEsc = new JLabel("Esc:");
		lblEsc.setBounds(10, 597, 30, 14);
		lblEsc.setFont(new Font("Arial", Font.BOLD, 12));
		add(lblEsc);

		lblCancelar = new JLabel("Cancelar");
		lblCancelar.setBounds(36, 597, 53, 14);
		lblCancelar.setForeground(Color.GRAY);
		lblCancelar.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblCancelar);

		lblF1 = new JLabel("F1:");
		lblF1.setBounds(182, 597, 21, 14);
		lblF1.setFont(new Font("Arial", Font.BOLD, 12));
		add(lblF1);

		lblNovo = new JLabel("Novo");
		lblNovo.setBounds(199, 597, 35, 14);
		lblNovo.setForeground(Color.BLUE);
		lblNovo.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblNovo);

		lblF3 = new JLabel("F3:");
		lblF3.setBounds(325, 597, 21, 14);
		lblF3.setFont(new Font("Arial", Font.BOLD, 12));
		add(lblF3);

		lblEditar = new JLabel("Editar");
		lblEditar.setBounds(343, 597, 35, 14);
		lblEditar.setForeground(new Color(139, 69, 19));
		lblEditar.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblEditar);

		lblF5 = new JLabel("F5:");
		lblF5.setBounds(489, 597, 21, 14);
		lblF5.setFont(new Font("Arial", Font.BOLD, 12));
		add(lblF5);

		lblPesquisarPedidos = new JLabel("Pesquisar Pedidos");
		lblPesquisarPedidos.setBounds(506, 597, 108, 14);
		lblPesquisarPedidos.setForeground(new Color(0, 128, 0));
		lblPesquisarPedidos.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblPesquisarPedidos);

		lblF7 = new JLabel("F7:");
		lblF7.setBounds(696, 597, 21, 14);
		lblF7.setFont(new Font("Arial", Font.BOLD, 12));
		add(lblF7);

		lblAlterarFornecedor = new JLabel("Alterar Fornecedor");
		lblAlterarFornecedor.setBounds(715, 597, 119, 14);
		lblAlterarFornecedor.setForeground(new Color(255, 140, 0));
		lblAlterarFornecedor.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblAlterarFornecedor);

		lblF12 = new JLabel("F12:");
		lblF12.setBounds(942, 597, 26, 14);
		lblF12.setFont(new Font("Arial", Font.BOLD, 12));
		add(lblF12);

		lblExcluir = new JLabel("Excluir");
		lblExcluir.setBounds(967, 597, 42, 14);
		lblExcluir.setForeground(Color.RED);
		lblExcluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblExcluir);

		panelTotalItem = new JPanel();
		panelTotalItem.setBounds(537, 339, 472, 29);
		panelTotalItem.setLayout(null);
		panelTotalItem.setBorder(UIManager.getBorder("DesktopIcon.border"));
		add(panelTotalItem);

		fTxtTotalPedido = new JFormattedTextField();
		fTxtTotalPedido.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtTotalPedido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtTotalPedido.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtTotalPedido.setEditable(false);
		fTxtTotalPedido.setColumns(10);
		fTxtTotalPedido.setBounds(144, 4, 105, 20);
		panelTotalItem.add(fTxtTotalPedido);

		lblTotalItem = new JLabel("Total do Pedido R$");
		lblTotalItem.setForeground(new Color(0, 128, 0));
		lblTotalItem.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTotalItem.setBounds(10, 5, 136, 19);
		panelTotalItem.add(lblTotalItem);

		txtNumeroProdutos = new JTextField();
		txtNumeroProdutos.setHorizontalAlignment(SwingConstants.RIGHT);
		txtNumeroProdutos.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNumeroProdutos.setEditable(false);
		txtNumeroProdutos.setColumns(10);
		txtNumeroProdutos.setBounds(365, 4, 100, 20);
		panelTotalItem.add(txtNumeroProdutos);

		lblNumeroProdutos = new JLabel("Nº produtos");
		lblNumeroProdutos.setBounds(273, 4, 87, 20);
		panelTotalItem.add(lblNumeroProdutos);
		lblNumeroProdutos.setFont(new Font("Tahoma", Font.BOLD, 14));

		lblPrecoUnitario = new JLabel("Preço Unit.");
		lblPrecoUnitario.setBounds(462, 266, 72, 20);
		lblPrecoUnitario.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblPrecoUnitario);

		fTxtPrecoUnit = new JFormattedTextField();
		fTxtPrecoUnit.setBounds(537, 266, 91, 20);
		fTxtPrecoUnit.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaPrecoUnit) {
				calcula_total_item();
			}
		});
		fTxtPrecoUnit.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtPrecoUnit.setDocument(new FormataNumeral(8, 2));
		fTxtPrecoUnit.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtPrecoUnit.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtPrecoUnit.setEditable(false);
		add(fTxtPrecoUnit);

		lblTotal = new JLabel("Total item");
		lblTotal.setBounds(849, 265, 66, 20);
		lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblTotal);

		fTxtTotalItem = new JFormattedTextField();
		fTxtTotalItem.setBounds(912, 265, 97, 20);
		fTxtTotalItem.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtTotalItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtTotalItem.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtTotalItem.setEditable(false);
		add(fTxtTotalItem);

		lblQuantidadeEntregue = new JLabel("Quantidade Entreg.");
		lblQuantidadeEntregue.setBounds(194, 296, 125, 20);
		lblQuantidadeEntregue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblQuantidadeEntregue);

		fTxtQuantidadeEntregue = new JFormattedTextField();
		fTxtQuantidadeEntregue.setBounds(315, 296, 94, 20);
		fTxtQuantidadeEntregue.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtQuantidadeEntregue.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtQuantidadeEntregue.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtQuantidadeEntregue.setEditable(false);
		add(fTxtQuantidadeEntregue);

		lblCustoAtual = new JLabel("Custo Atual");
		lblCustoAtual.setBounds(462, 296, 72, 20);
		lblCustoAtual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblCustoAtual);

		fTxtCustoAtual = new JFormattedTextField();
		fTxtCustoAtual.setBounds(537, 296, 91, 20);
		fTxtCustoAtual.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtCustoAtual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCustoAtual.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCustoAtual.setEditable(false);
		add(fTxtCustoAtual);

		panelNumeroOrcamento_1 = new JPanel();
		panelNumeroOrcamento_1.setBounds(802, 97, 207, 29);
		panelNumeroOrcamento_1.setLayout(null);
		panelNumeroOrcamento_1.setBorder(UIManager.getBorder("DesktopIcon.border"));
		add(panelNumeroOrcamento_1);

		txtNumeroCotacao = new JTextField();
		txtNumeroCotacao.setHorizontalAlignment(SwingConstants.RIGHT);
		txtNumeroCotacao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNumeroCotacao.setEditable(false);
		txtNumeroCotacao.setColumns(10);
		txtNumeroCotacao.setBounds(100, 4, 100, 20);
		panelNumeroOrcamento_1.add(txtNumeroCotacao);

		lblCotaoN = new JLabel("Cotação Nº");
		lblCotaoN.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCotaoN.setBounds(10, 5, 80, 19);
		panelNumeroOrcamento_1.add(lblCotaoN);

		fTxtCodigoBarras = new JFormattedTextField();
		fTxtCodigoBarras.setBounds(892, 230, 117, 20);
		JTextFieldLimit limitCodigo_Barras = new JTextFieldLimit(14, "inteiro");
		fTxtCodigoBarras.setDocument(limitCodigo_Barras);
		fTxtCodigoBarras.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCodigoBarras.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCodigoBarras.setEditable(false);
		add(fTxtCodigoBarras);

	}

	public void novo_pedido() {
		if (btnNovo.isEnabled()) {
			ativar_campos();
			limpar_campos();
		}
	}

	public void cancela_pedido() {
		if (btnCancelar.isVisible()) {
			desativar_campos();
			limpar_campos();
		}
	}

	public void ativar_campos() {
		btnNovo.setEnabled(false);
		btnPesquisar.setEnabled(false);

		fTxtCodigoFornecedor.setEditable(true);
		fTxtNomeFornecedor.setEditable(true);
		fTxtNomeFantasia.setEditable(true);
		fTxtCodigoProduto.setEditable(true);
		fTxtNomeProduto.setEditable(true);
		fTxtCodigoBarras.setEditable(true);
		cbxFatorVenda.setEnabled(true);
		fTxtQuantidadePedida.setEditable(true);
		fTxtPrecoUnit.setEditable(true);

		btnSalvar.setVisible(true);
		btnCancelar.setVisible(true);
	}

	public void desativar_campos() {
		btnNovo.setEnabled(true);
		btnPesquisar.setEnabled(true);

		fTxtCodigoFornecedor.setEditable(false);
		fTxtNomeFornecedor.setEditable(false);
		fTxtNomeFantasia.setEditable(false);
		fTxtCodigoProduto.setEditable(false);
		fTxtNomeProduto.setEditable(false);
		fTxtCodigoBarras.setEditable(false);
		cbxFatorVenda.setEnabled(false);
		fTxtQuantidadePedida.setEditable(false);
		fTxtPrecoUnit.setEditable(false);

		btnSalvar.setVisible(false);
		btnCancelar.setVisible(false);
		scrollPaneListaProdutos.setVisible(false);
	}

	public void limpar_campos() {
		fTxtCodigoFornecedor.setText(null);
		fTxtDocumento.setText(null);
		fTxtNomeFornecedor.setText(null);
		fTxtNomeFantasia.setText(null);
		fTxtCodigoProduto.setText(null);
		fTxtNomeProduto.setText(null);
		fTxtCodigoBarras.setText(null);
		cbxFatorVenda.getModel().setSelectedItem("UN");
		fTxtQuantidadePedida.setText(null);
		fTxtPrecoUnit.setText(null);
	}

	public void calcula_total_item() {
		Double preco_unit = 0.00;
		Double quantidade = 0.00;
		Double total_produto = 0.00;

		try {

			if (!fTxtPrecoUnit.getText().isEmpty()) {
				preco_unit = nf.parse(fTxtPrecoUnit.getText()).doubleValue();
			}
			if (!fTxtQuantidadePedida.getText().isEmpty()) {
				quantidade = nf.parse(fTxtQuantidadePedida.getText()).doubleValue();
			}

			total_produto = (preco_unit * quantidade);
			total_produto = Math.round(total_produto * 10000) / 10000d;

			fTxtTotalItem.setText(nf.format(total_produto));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void ConfiguraLarguraColunaTabela(JTable tabela_produtos_inclusos) {
		tabela_produtos_inclusos.getColumnModel().getColumn(0).setPreferredWidth(60); // C�digo
		tabela_produtos_inclusos.getColumnModel().getColumn(1).setPreferredWidth(400); // Nome
		tabela_produtos_inclusos.getColumnModel().getColumn(2).setPreferredWidth(53); // Unid
		tabela_produtos_inclusos.getColumnModel().getColumn(3).setPreferredWidth(150); // Quantidade Pedida
		tabela_produtos_inclusos.getColumnModel().getColumn(4).setPreferredWidth(150); // Quantidade Entregue
		tabela_produtos_inclusos.getColumnModel().getColumn(5).setPreferredWidth(100); // Custo Unit.
		tabela_produtos_inclusos.getColumnModel().getColumn(6).setPreferredWidth(100); // Custo Total

//		// Alinhamento das colunas.
//		esquerda.setHorizontalAlignment(SwingConstants.LEFT);
//		tabela_produtos_inclusos.getColumnModel().getColumn(4).setCellRenderer(esquerda); // Quantidade
//		tabela_produtos_inclusos.getColumnModel().getColumn(5).setCellRenderer(esquerda); // Preco Unit
//		tabela_produtos_inclusos.getColumnModel().getColumn(6).setCellRenderer(esquerda); // Desconto
//		tabela_produtos_inclusos.getColumnModel().getColumn(7).setCellRenderer(esquerda); // Total

		tabela_produtos_inclusos.getTableHeader().setResizingAllowed(false);
	}
}
