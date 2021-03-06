package tables.tableModels;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import entities.produto.Produto_orcamento;

public class ModeloTabelaProdutos_Orcamento extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NumberFormat nf = new DecimalFormat("R$ ,##0.00");
	private NumberFormat nf2 = new DecimalFormat(",##0.00");

	private String colunas[] = { "Cod.", "Nome", "Cod. Barras", "Unid.", "Quant.", "Pr.Unit.", "Desconto",
			"Total-Desc." };
	private ArrayList<Produto_orcamento> produtos_orcamento;
	private final int COLUNA_CODIGO = 0;
	private final int COLUNA_NOME = 1;
	private final int COLUNA_BARRAS = 2;
	private final int COLUNA_UNID = 3;
	private final int COLUNA_QUANTIDADE = 4;
	private final int COLUNA_PRECO_UNIT = 5;
	private final int COLUNA_DESC = 6;
	private final int COLUNA_TOTAL = 7;

	public ModeloTabelaProdutos_Orcamento(ArrayList<Produto_orcamento> produtos_orcamento) {
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

		if (rowIndex != -1) {
			Produto_orcamento produto_orcamento = this.produtos_orcamento.get(rowIndex);

			nf.setRoundingMode(RoundingMode.DOWN);
			nf2.setRoundingMode(RoundingMode.DOWN);

			Boolean fracionado = false;

			if (produto_orcamento.getUnidadeVenda().equals("MT") || produto_orcamento.getUnidadeVenda().equals("KG")
					|| produto_orcamento.getUnidadeVenda().equals("L") || produto_orcamento.getUnidadeVenda().contentEquals("PAR")) {
				fracionado = true;
			} else {
				fracionado = false;
			}

			switch (columIndex) {
			case COLUNA_CODIGO:
				return produto_orcamento.getIdProduto();
			case COLUNA_NOME:
				return produto_orcamento.getDescricao();
			case COLUNA_BARRAS:
				return produto_orcamento.getCodigo_barra();
			case COLUNA_UNID:
				return produto_orcamento.getUnidadeVenda();
			case COLUNA_QUANTIDADE:
				if (fracionado) {
					return nf2.format(produto_orcamento.getQuantidade());
				} else {
					return nf2.format(produto_orcamento.getQuantidade()).substring(0,
							nf2.format(produto_orcamento.getQuantidade()).length() - 3);
				}

			case COLUNA_PRECO_UNIT:
				return nf.format(produto_orcamento.getPreco_unitario().doubleValue());
			case COLUNA_DESC:
				return nf.format(produto_orcamento.getValor_desconto());
			case COLUNA_TOTAL:
				Double total_produto = produto_orcamento.getValor_total() - produto_orcamento.getValor_desconto();
				total_produto = Math.round(total_produto * 10000) / 10000d;
				return nf.format(total_produto);
			}
			return null;
		}else {
			return null;
		}

	}

	public void addProduto(Produto_orcamento p) {
		this.produtos_orcamento.add(p);
		this.fireTableDataChanged();
	}

	public void removeProduto(Produto_orcamento produto) {
		this.produtos_orcamento.remove(produto);
		this.fireTableDataChanged();
	}

	public void recarregarTabela(JTable tabela, ArrayList<Produto_orcamento> produtos_orcamento) {
		ModeloTabelaProdutos_Orcamento modelo = new ModeloTabelaProdutos_Orcamento(produtos_orcamento);
		tabela.setModel(modelo);
	}

}
