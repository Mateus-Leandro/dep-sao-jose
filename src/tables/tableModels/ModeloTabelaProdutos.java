package tables.tableModels;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import entities.produto.Produto_cadastro;
import entities.produto.Setor;

public class ModeloTabelaProdutos extends AbstractTableModel {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private String colunas[] = { "Cod", "Nome", "Fator", "Setor", "Pr.Custo", "Mg%", "Pr.Sug.", "Pr.Venda", "Mg% P.",
			"Cod. Barras", "Bloq.", "Dt. Cad." };
	private ArrayList<Produto_cadastro> produtos;
	private final int COLUNA_CODIGO = 0;
	private final int COLUNA_NOME = 1;
	private final int COLUNA_FATOR = 2;
	private final int COLUNA_SETOR = 3;
	private final int COLUNA_PRECO_CUSTO = 4;
	private final int COLUNA_MARGEM = 5;
	private final int COLUNA_PRECO_SUGERIDO = 6;
	private final int COLUNA_PRECO_VENDA = 7;
	private final int COLUNA_MARGEM_PRATICADA = 8;
	private final int COLUNA_CODIGO_BARRAS = 9;
	private final int COLUNA_BLOQUEADO_VENDA = 10;
	private final int COLUNA_DATA_CADASTRO = 11;

	public ModeloTabelaProdutos(ArrayList<Produto_cadastro> produtos) {
		this.produtos = produtos;
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
		return produtos.size();
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
		case COLUNA_CODIGO_BARRAS:
			return String.class;
		case COLUNA_SETOR:
			return Setor.class;
		case COLUNA_FATOR:
			return String.class;
		case COLUNA_PRECO_CUSTO:
			return BigDecimal.class;
		case COLUNA_PRECO_VENDA:
			return BigDecimal.class;
		case COLUNA_MARGEM:
			return BigDecimal.class;
		case COLUNA_PRECO_SUGERIDO:
			return BigDecimal.class;
		case COLUNA_MARGEM_PRATICADA:
			return BigDecimal.class;
		case COLUNA_BLOQUEADO_VENDA:
			return Boolean.class;
		case COLUNA_DATA_CADASTRO:
			return String.class;
		default:
			return String.class;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columIndex) {
		Produto_cadastro produto = this.produtos.get(rowIndex);

		NumberFormat nf = new DecimalFormat("R$ ,##0.00");
		NumberFormat nf2 = new DecimalFormat("0.00");

		switch (columIndex) {
		case COLUNA_CODIGO:
			return produto.getIdProduto();
		case COLUNA_NOME:
			return produto.getDescricao();
		case COLUNA_CODIGO_BARRAS:
			return produto.getCodigo_barra();
		case COLUNA_SETOR:
			return produto.getSetor();
		case COLUNA_FATOR:
			return produto.getUnidadeVenda();
		case COLUNA_PRECO_CUSTO:
			return nf.format(produto.getPrCusto());
		case COLUNA_PRECO_VENDA:
			return nf.format(produto.getPrecoVenda());
		case COLUNA_MARGEM:
			return nf2.format(produto.getMargem());
		case COLUNA_PRECO_SUGERIDO:
			return nf.format(produto.getPrSugerido());
		case COLUNA_MARGEM_PRATICADA:
			return nf2.format(produto.getMargemPraticada());
		case COLUNA_BLOQUEADO_VENDA:
			return produto.isBloqueadoVenda();
		case COLUNA_DATA_CADASTRO:
			return sdf.format(produto.getDataCadastro());
		}
		return null;
	}

	public void addProduto(Produto_cadastro p) {
		this.produtos.add(p);
		this.fireTableDataChanged();
	}

	public void removeProduto(Integer codigo_produto) {
		this.produtos.removeIf(produto -> produto.getIdProduto().equals(codigo_produto));
		this.fireTableDataChanged();
	}

	public void recarregarTabela(JTable tabela, ArrayList<Produto_cadastro> produtos) {
		ModeloTabelaProdutos modelo = new ModeloTabelaProdutos(produtos);
		tabela.setModel(modelo);
	}

}
