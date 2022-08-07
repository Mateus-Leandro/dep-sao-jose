package view.panels.pessoa.fornecedor;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import icons.Icones;
import javax.swing.JScrollPane;
import java.awt.Component;
import java.awt.Color;

public class Panel_pedido extends JPanel {
	private JSeparator separador_titulo;
	private JLabel lblPedidosDeCompra;
	private JLabel lblFornecedor;
	private JSeparator separador_fornecedor;
	private JLabel lblCodigoFornecedor;
	private JLabel lblNomeFornecedor;
	private JFormattedTextField fTxtNomeFornecedor;
	private JLabel lblDocumento;
	private JTextField txtDocumento;
	private JLabel lblNomeFantasia;
	private JFormattedTextField fTxtNomeFantasia;
	private JLabel lblProdutos;
	private JSeparator separador_produto;
	private JLabel lblCodigoProduto;
	private JLabel lblNomeProduto;
	private JFormattedTextField fTxtNomeProduto;
	private JLabel lblQuantidade;
	private JLabel lblBarras;
	private JTextField txtBarras;
	private JFormattedTextField fTxtCodigoFornecedor;
	private JFormattedTextField fTxtCodigoProduto;
	private JFormattedTextField fTxtQuantidade;
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
	private JSeparator separador_produto_1;
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
	private JFormattedTextField fTxtTotalItem;
	private JLabel lblTotalItem;
	private JLabel lblNumeroProdutos;
	private JTextField textField;

	/**
	 * Create the panel.
	 */
	public Panel_pedido() {
		setLayout(null);

		separador_titulo = new JSeparator();
		separador_titulo.setBounds(10, 50, 999, 9);
		add(separador_titulo);

		lblPedidosDeCompra = new JLabel("Pedido de Compra");
		lblPedidosDeCompra.setHorizontalAlignment(SwingConstants.CENTER);
		lblPedidosDeCompra.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblPedidosDeCompra.setBounds(393, 11, 244, 29);
		add(lblPedidosDeCompra);

		lblFornecedor = new JLabel("Fornecedor");
		lblFornecedor.setHorizontalAlignment(SwingConstants.CENTER);
		lblFornecedor.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblFornecedor.setBounds(10, 120, 103, 29);
		add(lblFornecedor);

		separador_fornecedor = new JSeparator();
		separador_fornecedor.setBounds(119, 137, 890, 9);
		add(separador_fornecedor);

		lblCodigoFornecedor = new JLabel("C\u00F3digo");
		lblCodigoFornecedor.setToolTipText("Gerado Autom\u00E1ticamente");
		lblCodigoFornecedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigoFornecedor.setBounds(11, 155, 46, 20);
		add(lblCodigoFornecedor);

		lblNomeFornecedor = new JLabel("Nome");
		lblNomeFornecedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeFornecedor.setBounds(675, 155, 40, 20);
		add(lblNomeFornecedor);

		fTxtNomeFornecedor = new JFormattedTextField();
		fTxtNomeFornecedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtNomeFornecedor.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeFornecedor.setEditable(false);
		fTxtNomeFornecedor.setBounds(716, 155, 293, 20);
		add(fTxtNomeFornecedor);

		lblDocumento = new JLabel("Documento");
		lblDocumento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDocumento.setBounds(181, 157, 78, 17);
		add(lblDocumento);

		txtDocumento = new JFormattedTextField();
		txtDocumento.setHorizontalAlignment(SwingConstants.LEFT);
		txtDocumento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDocumento.setEditable(false);
		txtDocumento.setBounds(257, 155, 134, 20);
		add(txtDocumento);

		lblNomeFantasia = new JLabel("Nome Fant.");
		lblNomeFantasia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeFantasia.setBounds(678, 185, 78, 20);
		add(lblNomeFantasia);

		fTxtNomeFantasia = new JFormattedTextField();
		fTxtNomeFantasia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtNomeFantasia.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeFantasia.setEditable(false);
		fTxtNomeFantasia.setBounds(757, 185, 252, 20);
		add(fTxtNomeFantasia);

		lblProdutos = new JLabel("Produto");
		lblProdutos.setHorizontalAlignment(SwingConstants.CENTER);
		lblProdutos.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblProdutos.setBounds(10, 199, 72, 29);
		add(lblProdutos);

		separador_produto = new JSeparator();
		separador_produto.setBounds(85, 216, 924, 9);
		add(separador_produto);

		lblCodigoProduto = new JLabel("C\u00F3digo");
		lblCodigoProduto.setToolTipText("Gerado Autom\u00E1ticamente");
		lblCodigoProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigoProduto.setBounds(10, 259, 48, 20);
		add(lblCodigoProduto);

		lblNomeProduto = new JLabel("Nome");
		lblNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeProduto.setBounds(194, 259, 40, 20);
		add(lblNomeProduto);

		fTxtNomeProduto = new JFormattedTextField();
		fTxtNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtNomeProduto.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeProduto.setEditable(false);
		fTxtNomeProduto.setBounds(238, 256, 390, 20);
		add(fTxtNomeProduto);

		lblQuantidade = new JLabel("Quantidade");
		lblQuantidade.setToolTipText("Gerado Autom\u00E1ticamente");
		lblQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQuantidade.setBounds(163, 300, 72, 20);
		add(lblQuantidade);

		lblBarras = new JLabel("C\u00F3digo de barras");
		lblBarras.setToolTipText("Gerado Autom\u00E1ticamente");
		lblBarras.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBarras.setBounds(774, 259, 112, 20);
		add(lblBarras);

		txtBarras = new JTextField();
		txtBarras.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtBarras.setEditable(false);
		txtBarras.setColumns(10);
		txtBarras.setBounds(887, 256, 122, 20);
		add(txtBarras);

		fTxtCodigoFornecedor = new JFormattedTextField();
		fTxtCodigoFornecedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCodigoFornecedor.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCodigoFornecedor.setEditable(false);
		fTxtCodigoFornecedor.setBounds(58, 155, 85, 20);
		add(fTxtCodigoFornecedor);

		fTxtCodigoProduto = new JFormattedTextField();
		fTxtCodigoProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCodigoProduto.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCodigoProduto.setEditable(false);
		fTxtCodigoProduto.setBounds(58, 256, 85, 20);
		add(fTxtCodigoProduto);

		fTxtQuantidade = new JFormattedTextField();
		fTxtQuantidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtQuantidade.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtQuantidade.setEditable(false);
		fTxtQuantidade.setBounds(238, 300, 115, 20);
		add(fTxtQuantidade);

		btnIncluir = new JButton("Incluir");
		btnIncluir.setIcon(icones.getIcone_mais());
		btnIncluir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnIncluir.setBounds(696, 301, 93, 29);
		add(btnIncluir);

		btnEditar = new JButton("Editar");
		btnEditar.setIcon(icones.getIcone_editar());
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEditar.setBounds(799, 301, 93, 29);
		add(btnEditar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setIcon(icones.getIcone_excluir());
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExcluir.setBounds(902, 300, 107, 29);
		add(btnExcluir);

		lblFator = new JLabel("Fator");
		lblFator.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFator.setBounds(10, 300, 40, 19);
		add(lblFator);

		cbxFatorVenda = new JComboBox<String>();
		cbxFatorVenda
				.setModel(new DefaultComboBoxModel(new String[] { "UN", "PAR", "MT", "KG", "L", "CX", "FD", "PCT" }));
		cbxFatorVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxFatorVenda.setEnabled(false);
		cbxFatorVenda.setBounds(47, 298, 78, 22);
		add(cbxFatorVenda);

		panelNumeroOrcamento = new JPanel();
		panelNumeroOrcamento.setLayout(null);
		panelNumeroOrcamento.setBorder(UIManager.getBorder("DesktopIcon.border"));
		panelNumeroOrcamento.setBounds(811, 70, 198, 29);
		add(panelNumeroOrcamento);

		txtNumeroPedido = new JTextField();
		txtNumeroPedido.setToolTipText("Gerado Automaticamente");
		txtNumeroPedido.setHorizontalAlignment(SwingConstants.RIGHT);
		txtNumeroPedido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNumeroPedido.setEditable(false);
		txtNumeroPedido.setColumns(10);
		txtNumeroPedido.setBounds(88, 4, 100, 20);
		panelNumeroOrcamento.add(txtNumeroPedido);

		lblNumeroPedido = new JLabel("Pedido N\u00BA");
		lblNumeroPedido.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNumeroPedido.setBounds(10, 5, 70, 19);
		panelNumeroOrcamento.add(lblNumeroPedido);
		
		lblProdutosInclusos = new JLabel("Produtos Inclusos");
		lblProdutosInclusos.setHorizontalAlignment(SwingConstants.CENTER);
		lblProdutosInclusos.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblProdutosInclusos.setBounds(11, 363, 163, 29);
		add(lblProdutosInclusos);
		
		scrollPaneTabelaProdutosInclusos = new JScrollPane((Component) null);
		scrollPaneTabelaProdutosInclusos.setBounds(10, 403, 999, 142);
		add(scrollPaneTabelaProdutosInclusos);
		
		separador_produto_1 = new JSeparator();
		separador_produto_1.setBounds(181, 379, 828, 9);
		add(separador_produto_1);
		
		btnNovo = new JButton("Novo");
		btnNovo.setIcon(icones.getIcone_mais());
		btnNovo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNovo.setBounds(10, 70, 93, 35);
		add(btnNovo);
		
		btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.setIcon(icones.getIcone_pesquisar());
		btnPesquisar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPesquisar.setBounds(119, 70, 116, 35);
		add(btnPesquisar);
		
		btnSalvar = new JButton("Salvar Pedido");
		btnSalvar.setIcon(icones.getIcone_salvar());
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSalvar.setBounds(743, 556, 144, 35);
		add(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(icones.getIcone_cancelar());
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.setBounds(894, 556, 115, 35);
		add(btnCancelar);
		
		lblEsc = new JLabel("Esc:");
		lblEsc.setFont(new Font("Arial", Font.BOLD, 12));
		lblEsc.setBounds(10, 597, 30, 14);
		add(lblEsc);
		
		lblCancelar = new JLabel("Cancelar");
		lblCancelar.setForeground(Color.GRAY);
		lblCancelar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCancelar.setBounds(36, 597, 53, 14);
		add(lblCancelar);
		
		lblF1 = new JLabel("F1:");
		lblF1.setFont(new Font("Arial", Font.BOLD, 12));
		lblF1.setBounds(182, 597, 21, 14);
		add(lblF1);
		
		lblNovo = new JLabel("Novo");
		lblNovo.setForeground(Color.BLUE);
		lblNovo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNovo.setBounds(199, 597, 35, 14);
		add(lblNovo);
		
		lblF3 = new JLabel("F3:");
		lblF3.setFont(new Font("Arial", Font.BOLD, 12));
		lblF3.setBounds(325, 597, 21, 14);
		add(lblF3);
		
		lblEditar = new JLabel("Editar");
		lblEditar.setForeground(new Color(139, 69, 19));
		lblEditar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEditar.setBounds(343, 597, 35, 14);
		add(lblEditar);
		
		lblF5 = new JLabel("F5:");
		lblF5.setFont(new Font("Arial", Font.BOLD, 12));
		lblF5.setBounds(489, 597, 21, 14);
		add(lblF5);
		
		lblPesquisarPedidos = new JLabel("Pesquisar Pedidos");
		lblPesquisarPedidos.setForeground(new Color(0, 128, 0));
		lblPesquisarPedidos.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPesquisarPedidos.setBounds(506, 597, 108, 14);
		add(lblPesquisarPedidos);
		
		lblF7 = new JLabel("F7:");
		lblF7.setFont(new Font("Arial", Font.BOLD, 12));
		lblF7.setBounds(696, 597, 21, 14);
		add(lblF7);
		
		lblAlterarFornecedor = new JLabel("Alterar Fornecedor");
		lblAlterarFornecedor.setForeground(new Color(255, 140, 0));
		lblAlterarFornecedor.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAlterarFornecedor.setBounds(715, 597, 119, 14);
		add(lblAlterarFornecedor);
		
		lblF12 = new JLabel("F12:");
		lblF12.setFont(new Font("Arial", Font.BOLD, 12));
		lblF12.setBounds(942, 597, 26, 14);
		add(lblF12);
		
		lblExcluir = new JLabel("Excluir");
		lblExcluir.setForeground(Color.RED);
		lblExcluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblExcluir.setBounds(967, 597, 42, 14);
		add(lblExcluir);
		
		panelTotalItem = new JPanel();
		panelTotalItem.setLayout(null);
		panelTotalItem.setBorder(UIManager.getBorder("DesktopIcon.border"));
		panelTotalItem.setBounds(589, 339, 420, 29);
		add(panelTotalItem);
		
		fTxtTotalItem = new JFormattedTextField();
		fTxtTotalItem.setHorizontalAlignment(SwingConstants.RIGHT);
		fTxtTotalItem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtTotalItem.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtTotalItem.setEditable(false);
		fTxtTotalItem.setColumns(10);
		fTxtTotalItem.setBounds(67, 5, 105, 20);
		panelTotalItem.add(fTxtTotalItem);
		
		lblTotalItem = new JLabel("Total R$");
		lblTotalItem.setForeground(new Color(0, 128, 0));
		lblTotalItem.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTotalItem.setBounds(6, 5, 62, 19);
		panelTotalItem.add(lblTotalItem);
		
		textField = new JTextField();
		textField.setToolTipText("Gerado Automaticamente");
		textField.setHorizontalAlignment(SwingConstants.RIGHT);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setEditable(false);
		textField.setColumns(10);
		textField.setBounds(310, 5, 100, 20);
		panelTotalItem.add(textField);
		
		lblNumeroProdutos = new JLabel("N\u00BA de produtos");
		lblNumeroProdutos.setBounds(193, 5, 115, 20);
		panelTotalItem.add(lblNumeroProdutos);
		lblNumeroProdutos.setToolTipText("Gerado Autom\u00E1ticamente");
		lblNumeroProdutos.setFont(new Font("Tahoma", Font.BOLD, 14));

	}
}
