package tables.tableModels;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import entities.orcamentos.Produto_Orcamento;

public class ModeloTabelaProdutos_Orcamento extends AbstractTableModel {

	private String colunas[] = { "It. N�", "Cod.", "Nome", "Cod. Barras", "Unid.", "Quantidade", "Pr.Unit.", "Desconto",
			"Total" };
	private ArrayList<Produto_Orcamento> produtos_orcamento;
	private final int COLUNA_NUMERO_ITEM = 0;
	private final int COLUNA_CODIGO = 1;
	private final int COLUNA_NOME = 2;
	private final int COLUNA_BARRAS = 3;
	private final int COLUNA_UNID = 4;
	private final int COLUNA_QUANTIDADE = 5;
	private final int COLUNA_PRECO_UNIT = 6;
	private final int COLUNA_DESC = 7;
	private final int COLUNA_TOTAL = 8;

	public ModeloTabelaProdutos_Orcamento(ArrayList<Produto_Orcamento> produtos_orcamento) {
		this.produtos_orcamento = produtos_orcamento;
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
		return produtos_orcamento.size();
	}

	public String getColumnName(int indice) {
		return colunas[indice];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		switch (columnIndex) {
		case COLUNA_NUMERO_ITEM:
			return Integer.class;
		case COLUNA_CODIGO:
			return Integer.class;
		case COLUNA_NOME:
			return String.class;
		case COLUNA_BARRAS:
			return String.class;
		case COLUNA_UNID:
			return String.class;
		case COLUNA_QUANTIDADE:
			return BigDecimal.class;
		case COLUNA_PRECO_UNIT:
			return BigDecimal.class;
		case COLUNA_DESC:
			return BigDecimal.class;
		case COLUNA_TOTAL:
			return BigDecimal.class;
		default:
			return String.class;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columIndex) {
		Produto_Orcamento produto_orcamento = this.produtos_orcamento.get(rowIndex);

		NumberFormat nf = new DecimalFormat("R$ 0.00");
		NumberFormat nf2 = new DecimalFormat("0.00");

		Boolean fracionado = false;

		if (produto_orcamento.getFator_venda().equals("MT") || produto_orcamento.getFator_venda().equals("KG")
				|| produto_orcamento.getFator_venda().equals("L")) {
			fracionado = true;
		} else {
			fracionado = false;
		}

		switch (columIndex) {
		case COLUNA_NUMERO_ITEM:
			return rowIndex + 1;
		case COLUNA_CODIGO:
			return produto_orcamento.getCodigo();
		case COLUNA_NOME:
			return produto_orcamento.getNome();
		case COLUNA_BARRAS:
			return produto_orcamento.getCodigo_barras();
		case COLUNA_UNID:
			return produto_orcamento.getFator_venda();
		case COLUNA_QUANTIDADE:
			if (fracionado) {
				return nf2.format(produto_orcamento.getQuantidade());
			} else {
				return nf2.format(produto_orcamento.getQuantidade()).substring(0,
						nf2.format(produto_orcamento.getQuantidade()).length() - 3);
			}

		case COLUNA_PRECO_UNIT:
			return nf.format(produto_orcamento.getPreco_unitario());
		case COLUNA_DESC:
			return nf.format(produto_orcamento.getValor_desconto());
		case COLUNA_TOTAL:
			return nf.format(produto_orcamento.getValor_total());
		}
		return null;
	}

	public void addProduto(Produto_Orcamento p) {
		this.produtos_orcamento.add(p);
		this.fireTableDataChanged();
	}

	public void removeProduto(int index_produto) {
			this.produtos_orcamento.remove(index_produto);
			this.fireTableDataChanged();
	}

	public void recarregarTabela(JTable tabela, ArrayList<Produto_Orcamento> produtos_orcamento) {
		ModeloTabelaProdutos_Orcamento modelo = new ModeloTabelaProdutos_Orcamento(produtos_orcamento);
		tabela.setModel(modelo);
	}

}
