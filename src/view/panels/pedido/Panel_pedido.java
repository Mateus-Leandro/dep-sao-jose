package view.panels.pedido;

import java.awt.Font;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Panel_pedido extends JPanel {
	private JSeparator separador_titulo;
	private JLabel lblPedidosDeCompra;
	private JLabel lblFornecedor;
	private JSeparator separador_fornecedor;
	private JLabel lblCodigoFornecedor;
	private JTextField txtCodigoFornecedor;
	private JLabel lblNomeFornecedor;
	private JFormattedTextField fTxtNomeFornecedor;
	private JLabel lblDocumento;
	private JTextField txtDocumento;
	private JLabel lblNomeFantasia;
	private JFormattedTextField fTxtNomeFantasia;
	private JLabel lblProdutos;
	private JSeparator separador_produto;
	private JLabel lblCodigoProduto;
	private JTextField txtCodigoProduto;
	private JLabel lblNomeProduto;
	private JFormattedTextField fTxtNomeProduto;

	/**
	 * Create the panel.
	 */
	public Panel_pedido() {
		setLayout(null);
		
		separador_titulo = new JSeparator();
		separador_titulo.setBounds(10, 50, 698, 9);
		add(separador_titulo);
		
		lblPedidosDeCompra = new JLabel("Pedido de Compra");
		lblPedidosDeCompra.setHorizontalAlignment(SwingConstants.CENTER);
		lblPedidosDeCompra.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblPedidosDeCompra.setBounds(247, 11, 244, 29);
		add(lblPedidosDeCompra);
		
		lblFornecedor = new JLabel("Fornecedor");
		lblFornecedor.setHorizontalAlignment(SwingConstants.CENTER);
		lblFornecedor.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblFornecedor.setBounds(10, 70, 103, 29);
		add(lblFornecedor);
		
		separador_fornecedor = new JSeparator();
		separador_fornecedor.setBounds(119, 87, 589, 9);
		add(separador_fornecedor);
		
		lblCodigoFornecedor = new JLabel("C\u00F3digo");
		lblCodigoFornecedor.setToolTipText("Gerado Autom\u00E1ticamente");
		lblCodigoFornecedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigoFornecedor.setBounds(11, 105, 46, 20);
		add(lblCodigoFornecedor);
		
		txtCodigoFornecedor = new JTextField();
		txtCodigoFornecedor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtCodigoFornecedor.setEditable(false);
		txtCodigoFornecedor.setColumns(10);
		txtCodigoFornecedor.setBounds(58, 103, 65, 20);
		add(txtCodigoFornecedor);
		
		lblNomeFornecedor = new JLabel("Nome");
		lblNomeFornecedor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeFornecedor.setBounds(10, 135, 40, 20);
		add(lblNomeFornecedor);
		
		fTxtNomeFornecedor = new JFormattedTextField();
		fTxtNomeFornecedor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtNomeFornecedor.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeFornecedor.setEditable(false);
		fTxtNomeFornecedor.setBounds(52, 133, 317, 20);
		add(fTxtNomeFornecedor);
		
		lblDocumento = new JLabel("Documento");
		lblDocumento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDocumento.setBounds(163, 106, 78, 17);
		add(lblDocumento);
		
		txtDocumento = new JFormattedTextField();
		txtDocumento.setHorizontalAlignment(SwingConstants.LEFT);
		txtDocumento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtDocumento.setEditable(false);
		txtDocumento.setBounds(244, 103, 125, 20);
		add(txtDocumento);
		
		lblNomeFantasia = new JLabel("Nome Fant.");
		lblNomeFantasia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeFantasia.setBounds(379, 135, 78, 20);
		add(lblNomeFantasia);
		
		fTxtNomeFantasia = new JFormattedTextField();
		fTxtNomeFantasia.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtNomeFantasia.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeFantasia.setEditable(false);
		fTxtNomeFantasia.setBounds(456, 133, 252, 20);
		add(fTxtNomeFantasia);
		
		lblProdutos = new JLabel("Produto");
		lblProdutos.setHorizontalAlignment(SwingConstants.CENTER);
		lblProdutos.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblProdutos.setBounds(10, 166, 72, 29);
		add(lblProdutos);
		
		separador_produto = new JSeparator();
		separador_produto.setBounds(85, 183, 623, 9);
		add(separador_produto);
		
		lblCodigoProduto = new JLabel("C\u00F3digo");
		lblCodigoProduto.setToolTipText("Gerado Autom\u00E1ticamente");
		lblCodigoProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigoProduto.setBounds(12, 205, 46, 20);
		add(lblCodigoProduto);
		
		txtCodigoProduto = new JTextField();
		txtCodigoProduto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtCodigoProduto.setEditable(false);
		txtCodigoProduto.setColumns(10);
		txtCodigoProduto.setBounds(59, 203, 65, 20);
		add(txtCodigoProduto);
		
		lblNomeProduto = new JLabel("Nome");
		lblNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeProduto.setBounds(163, 205, 40, 20);
		add(lblNomeProduto);
		
		fTxtNomeProduto = new JFormattedTextField();
		fTxtNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtNomeProduto.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeProduto.setEditable(false);
		fTxtNomeProduto.setBounds(205, 203, 317, 20);
		add(fTxtNomeProduto);

	}
}
