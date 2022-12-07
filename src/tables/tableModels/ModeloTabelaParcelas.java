package tables.tableModels;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import entities.financeiro.Forma_pagamento;
import entities.financeiro.Parcela;

public class ModeloTabelaParcelas extends AbstractTableModel {

	private NumberFormat nf = new DecimalFormat("R$ ,##0.00");

	private String colunas[] = { "Nº Parc.", "Valor", "Forma pagt.", "Dt. Pagt.", "Dt. Venc." };
	private ArrayList<Parcela> parcelas;
	private final int COLUNA_NUMERO_PARCELA = 0;
	private final int COLUNA_VALOR_PARCELA = 1;
	private final int COLUNA_FORMA_PAGAMENTO = 2;
	private final int COLUNA_DATA_PAGAMENTO = 3;
	private final int COLUNA_DATA_VENCIMENTO = 4;

	public ModeloTabelaParcelas(ArrayList<Parcela> parcelas) {
		this.parcelas = parcelas;
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
		if (parcelas != null) {
			return parcelas.size();
		} else {
			return 0;
		}
	}

	public String getColumnName(int indice) {
		return colunas[indice];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
			case COLUNA_NUMERO_PARCELA:
				return Integer.class;
			case COLUNA_VALOR_PARCELA:
				return BigDecimal.class;
			case COLUNA_FORMA_PAGAMENTO:
				return Forma_pagamento.class;
			case COLUNA_DATA_PAGAMENTO:
				return Date.class;
			case COLUNA_DATA_VENCIMENTO:
				return Date.class;
			default:
				return String.class;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columIndex) {
		nf.setRoundingMode(RoundingMode.DOWN);

		Parcela parcela = this.parcelas.get(rowIndex);
		switch (columIndex) {
			case COLUNA_NUMERO_PARCELA:
				return rowIndex + 1;
			case COLUNA_VALOR_PARCELA:
				return nf.format(parcela.getValor_parcela().doubleValue());
			case COLUNA_FORMA_PAGAMENTO:
				return parcela.getForma_pagamento();
			case COLUNA_DATA_PAGAMENTO:
				return parcela.getData_pagamento();
			case COLUNA_DATA_VENCIMENTO:
				return parcela.getData_vencimento();
		}
		return null;
	}

	public void addOrcamento(Parcela nova_parcela) {
		this.parcelas.add(nova_parcela);
		this.fireTableDataChanged();
	}

	public void removeOrcamento(Parcela parcela_removida) {
		for (Parcela parcela : parcelas) {
			if (parcela.equals(parcela_removida)) {
				this.parcelas.remove(parcela);
				this.fireTableDataChanged();
			}
		}
	}

	public void recarregarTabela(JTable tabela, ArrayList<Parcela> parcelas) {
		ModeloTabelaParcelas modelo = new ModeloTabelaParcelas(parcelas);
		tabela.setModel(modelo);
	}

}
