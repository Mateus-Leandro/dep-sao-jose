package tables.tableRenders;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Render_tabela_orcamentos extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		Color cor = Color.BLACK;

		String status = (String) table.getValueAt(row, 7);

		if (status.equals("N.FAT.")) {
			cor = Color.BLACK;
		} else if (status.equals("PENDENTE")) {
			cor = new Color(0, 0, 255);
		} else if (status.equals("PAGO")) {
			cor = new Color(0, 128, 0);
		} else if (status.equals("VENCIDO")) {
			cor = Color.RED;
		}else {
			cor = new Color(128, 0, 128);
		}

		label.setForeground(cor);
		return label;

	}
}
