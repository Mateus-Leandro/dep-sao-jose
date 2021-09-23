package tableModels;

import java.sql.Date;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import entities.Barras_Produto;
import entities.Produto;
import entities.Setor;

public class ModeloTabelaBarras extends AbstractTableModel {

	private String colunas[] = {"Barras Vinculado", "Dt. Vinculacao." };
	private ArrayList<Barras_Produto> barras_produto;
	private final int COLUNA_BARRAS = 0;
	private final int COLUNA_DT_VINCULACAO = 1;
	
	
	
	public ModeloTabelaBarras(ArrayList<Barras_Produto> barras_produto) {
		this.barras_produto = barras_produto;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public int getRowCount() {
		return barras_produto.size();
	}

	public String getColumnName(int indice) {
		return colunas[indice];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case COLUNA_BARRAS:
			return Integer.class;
		case COLUNA_DT_VINCULACAO:
			return Date.class;
		default:
			return String.class;
		}
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columIndex) {
		Barras_Produto barras = this.barras_produto.get(rowIndex);

		switch (columIndex) {
		case COLUNA_BARRAS:
			return barras.getBarras();
		case COLUNA_DT_VINCULACAO:
			return barras.getDt_vinculacao();
		}
		return null;
	}
	
	public void addBarras(Barras_Produto bp) {
		this.barras_produto.add(bp);
		this.fireTableDataChanged();
	}
	
	public void removeProduto(int row) {
		this.barras_produto.remove(row);
		this.fireTableRowsDeleted(row, row);
	}
	
	public void recarregarTabela(JTable tabela, ArrayList<Barras_Produto> barras_produto) {
		ModeloTabelaBarras modelo = new ModeloTabelaBarras(barras_produto);
		tabela.setModel(modelo);
	}

}
