package tables.tableRenders;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Render_tabela_produtos extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		Color cor = Color.BLACK;

		Boolean bloqueado = (Boolean) table.getValueAt(row, 10);

		if (bloqueado) {
			cor = Color.RED;
		} else {
			cor = Color.BLACK;
		}

		label.setForeground(cor);
		return label;

	}
}
