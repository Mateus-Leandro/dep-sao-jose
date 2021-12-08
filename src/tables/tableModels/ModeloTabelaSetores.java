package tables.tableModels;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import entities.produto.Setor;

public class ModeloTabelaSetores extends AbstractTableModel {

	private String colunas[] = { "Cod", "Nome"};
	private ArrayList<Setor> setores;
	private final int COLUNA_CODIGO = 0;
	private final int COLUNA_NOME = 1;

	
	
	public ModeloTabelaSetores(ArrayList<Setor> setores) {
		this.setores = setores;
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
		return setores.size();
	}


	public String getColumnName(int indice) {
		return colunas[indice];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case COLUNA_CODIGO:
			return Integer.class;
		case COLUNA_NOME:
			return String.class;
		default:
			return String.class;
		}
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columIndex) {
		Setor Setor = this.setores.get(rowIndex);

		switch (columIndex) {
		case COLUNA_CODIGO:
			return Setor.getCodSetor();
		case COLUNA_NOME:
			return Setor.getNome();
		}
		return null;
	}
	
	public void addSetor(Setor s) {
		this.setores.add(s);
		this.fireTableDataChanged();
	}
	
	public void removeSetor(int row) {
		this.setores.remove(row);
		this.fireTableRowsDeleted(row, row);
	}
	
	public void recarregarTabela(JTable tabela, ArrayList<Setor> setores) {
		ModeloTabelaSetores modelo = new ModeloTabelaSetores(setores);
		tabela.setModel(modelo);
	}

}
