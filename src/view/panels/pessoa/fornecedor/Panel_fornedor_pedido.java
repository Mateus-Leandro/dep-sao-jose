package view.panels.pessoa.fornecedor;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import view.panels.pedido.Panel_pedido;

import java.awt.Font;

public class Panel_fornedor_pedido extends JPanel {
	private JTabbedPane tabbedPane;
	private Panel_Fornecedor panel_fornecedor = new Panel_Fornecedor();
	private Panel_pedido panel_pedido = new Panel_pedido();

	/**
	 * Create the panel.
	 */
	public Panel_fornedor_pedido() {
		setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabbedPane.addTab("Cadastro", panel_fornecedor);
		tabbedPane.addTab("Pedidos de Compra", panel_pedido);
		tabbedPane.setBounds(2, 0, 1023, 645);
		add(tabbedPane);

	}
}
