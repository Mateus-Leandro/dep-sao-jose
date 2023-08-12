package tables.tableModels;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import entities.produto.Produto_pedido;

public class ModeloTabelaProdutos_Pedido extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NumberFormat nf = new DecimalFormat("R$ ,##0.00");
	private NumberFormat nf2 = new DecimalFormat(",##0.00");

	private String colunas[] = { "Cod.", "Nome", "Unid.", "Qtd. Pedida", "Custo Unit.",
			"Custo Total" };
	private ArrayList<Produto_pedido> produtos_pedido;
	private final int COLUNA_CODIGO = 0;
	private final int COLUNA_NOME = 1;
	private final int COLUNA_UNID = 2;
	private final int COLUNA_QUANTIDADE_PEDIDA = 3;
	private final int COLUNA_CUSTO_UNIT = 4;
	private final int COLUNA_CUSTO_TOTAL = 5;

	public ModeloTabelaProdutos_Pedido(ArrayList<Produto_pedido> produtos_pedido) {
		this.produtos_pedido = produtos_pedido;
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
		return produtos_pedido.size();
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
		case COLUNA_UNID:
			return String.class;
		case COLUNA_QUANTIDADE_PEDIDA:
			return BigDecimal.class;
		case COLUNA_CUSTO_UNIT:
			return BigDecimal.class;
		case COLUNA_CUSTO_TOTAL:
			return BigDecimal.class;
		default:
			return String.class;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columIndex) {

		if (rowIndex != -1) {
			Produto_pedido produto_pedido = this.produtos_pedido.get(rowIndex);

			nf.setRoundingMode(RoundingMode.DOWN);
			nf2.setRoundingMode(RoundingMode.DOWN);

			Boolean fracionado = false;

			if (produto_pedido.getUnidadeVenda().equals("MT") || produto_pedido.getUnidadeVenda().equals("KG")
					|| produto_pedido.getUnidadeVenda().equals("L")
					|| produto_pedido.getUnidadeVenda().contentEquals("PAR")) {
				fracionado = true;
			} else {
				fracionado = false;
			}

			switch (columIndex) {
			case COLUNA_CODIGO:
				return produto_pedido.getIdProduto();
			case COLUNA_NOME:
				return produto_pedido.getDescricao();
			case COLUNA_UNID:
				return produto_pedido.getUnidadeVenda();
			case COLUNA_QUANTIDADE_PEDIDA:
				if (fracionado) {
					return nf2.format(produto_pedido.getQuantidade_pedida());
				} else {
					return nf2.format(produto_pedido.getQuantidade_pedida()).substring(0,
							nf2.format(produto_pedido.getQuantidade_pedida()).length() - 3);
				}
			case COLUNA_CUSTO_UNIT:
				return nf.format(produto_pedido.getPreco_fornecedor().doubleValue());
			case COLUNA_CUSTO_TOTAL:
				Double total_produto = produto_pedido.getPreco_fornecedor();
				total_produto = Math.round(total_produto * 10000) / 10000d;
				return nf.format(total_produto);
			}
			return null;
		} else {
			return null;
		}

	}

	public void addProduto(Produto_pedido p) {
		this.produtos_pedido.add(p);
		this.fireTableDataChanged();
	}

	public void removeProduto(Produto_pedido produto) {
		this.produtos_pedido.remove(produto);
		this.fireTableDataChanged();
	}

	public void recarregarTabela(JTable tabela, ArrayList<Produto_pedido> produtos_orcamento) {
		ModeloTabelaProdutos_Pedido modelo = new ModeloTabelaProdutos_Pedido(produtos_orcamento);
		tabela.setModel(modelo);
	}

}
