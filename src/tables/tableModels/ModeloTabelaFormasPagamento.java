package tables.tableModels;

import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import entities.financeiro.Forma_pagamento;

public class ModeloTabelaFormasPagamento extends AbstractTableModel {

	private String colunas[] = { "Cod", "Nome" };
	private ArrayList<Forma_pagamento> formas;
	private final int COLUNA_CODIGO = 0;
	private final int COLUNA_NOME = 1;

	public ModeloTabelaFormasPagamento(ArrayList<Forma_pagamento> formas) {
		this.formas = formas;
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
		if(formas != null) {
			return formas.size();
		}else {
			return 0;
		}
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
		Forma_pagamento forma = this.formas.get(rowIndex);

		switch (columIndex) {
		case COLUNA_CODIGO:
			return forma.getCodigo();
		case COLUNA_NOME:
			return forma.getDescricao();
		}
		return null;
	}

	public void addForma(Forma_pagamento form) {
		this.formas.add(form);
		this.fireTableDataChanged();
	}

	public void removeForma(int row) {
		this.formas.remove(row);
		this.fireTableRowsDeleted(row, row);
	}

	public void recarregarTabela(JTable tabela, ArrayList<Forma_pagamento> formas) {
		ModeloTabelaFormasPagamento modelo = new ModeloTabelaFormasPagamento(formas);
		tabela.setModel(modelo);
	}

}
