package tables.tableModels;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import entities.orcamentos.Orcamento;

public class ModeloTabelaOrcamentos extends AbstractTableModel {

	private NumberFormat nf = new DecimalFormat("R$ ,##0.00");
	private NumberFormat nf2 = new DecimalFormat("0.00");

	private String colunas[] = { "Nº Orc.", "Cliente", "Qtd. Itens", "Total Merc", "Desconto", "Frete", "Valor Total",
			"Status","Qtd.Par.", "Dt. Inclusão" };
	private ArrayList<Orcamento> orcamentos;
	private final int COLUNA_NUMERO_ORCAMENTO = 0;
	private final int COLUNA_CLIENTE = 1;
	private final int COLUNA_QUANTIDADE_ITENS = 2;
	private final int COLUNA_TOTAL_MERCADORIAS = 3;
	private final int COLUNA_VALOR_DESCONTO = 4;
	private final int COLUNA_VALOR_FRETE = 5;
	private final int COLUNA_TOTAL_ORCAMENTO = 6;
	private final int COLUNA_STATUS = 7;
	private final int COLUNA_QTD_PARCELAS = 8;
	private final int COLUNA_DATA_INCLUSAO = 9;

	public ModeloTabelaOrcamentos(ArrayList<Orcamento> orcamentos) {
		this.orcamentos = orcamentos;
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
		return orcamentos.size();
	}

	public String getColumnName(int indice) {
		return colunas[indice];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case COLUNA_NUMERO_ORCAMENTO:
			return Integer.class;
		case COLUNA_CLIENTE:
			return String.class;
		case COLUNA_QUANTIDADE_ITENS:
			return Integer.class;
		case COLUNA_TOTAL_MERCADORIAS:
			return BigDecimal.class;
		case COLUNA_VALOR_DESCONTO:
			return BigDecimal.class;
		case COLUNA_VALOR_FRETE:
			return BigDecimal.class;
		case COLUNA_TOTAL_ORCAMENTO:
			return BigDecimal.class;
		case COLUNA_STATUS:
			return String.class;
		case COLUNA_QTD_PARCELAS:
			return Integer.class;
		case COLUNA_DATA_INCLUSAO:
			return java.util.Date.class;
		default:
			return String.class;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columIndex) {
		Orcamento orcamento = this.orcamentos.get(rowIndex);

		switch (columIndex) {
		case COLUNA_NUMERO_ORCAMENTO:
			return orcamento.getId_orcamento();
		case COLUNA_CLIENTE:
			return orcamento.getCliente().getNome();
		case COLUNA_QUANTIDADE_ITENS:
			return orcamento.getQuantidade_produtos();
		case COLUNA_TOTAL_MERCADORIAS:
			return nf.format(orcamento.getTotal_mercadorias_liquido());
		case COLUNA_VALOR_DESCONTO:
			return nf.format(orcamento.getDesconto_final());
		case COLUNA_VALOR_FRETE:
			return nf.format(orcamento.getFrete());
		case COLUNA_TOTAL_ORCAMENTO:
			return nf.format(orcamento.getValor_total());
		case COLUNA_STATUS:
			return orcamento.getStatusPagamento(orcamento);
		case COLUNA_QTD_PARCELAS:
			return orcamento.getNumero_de_parcelas();
		case COLUNA_DATA_INCLUSAO:
			return orcamento.getData_inclusao();
		}
		return null;
	}

	public void addOrcamento(Orcamento orc) {
		this.orcamentos.add(orc);
		this.fireTableDataChanged();
	}

	public void removeOrcamento(int row) {
		this.orcamentos.remove(row);
		this.fireTableRowsDeleted(row, row);
	}

	public void recarregarTabela(JTable tabela, ArrayList<Orcamento> orcamentos) {
		ModeloTabelaOrcamentos modelo = new ModeloTabelaOrcamentos(orcamentos);
		tabela.setModel(modelo);
	}

}
