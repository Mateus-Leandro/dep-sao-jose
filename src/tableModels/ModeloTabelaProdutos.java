package tableModels;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import entities.Produto;
import entities.Setor;

public class ModeloTabelaProdutos extends AbstractTableModel {

	private String colunas[] = { "Cod", "Nome", "Cod.Barras", "Setor", "Fator", "Preço", "Bloqueado", "Dt. Cad." };
	private ArrayList<Produto> produtos;
	private final int COLUNA_CODIGO = 0;
	private final int COLUNA_NOME = 1;
	private final int COLUNA_CODIGO_BARRAS = 2;
	private final int COLUNA_SETOR = 3;
	private final int COLUNA_FATOR_VENDA = 4;
	private final int COLUNA_PRECO_VENDA = 5;
	private final int COLUNA_BLOQUEADO_VENDA = 6;
	private final int COLUNA_DATA_CADASTRO = 7;

	public ModeloTabelaProdutos(ArrayList<Produto> produtos) {
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
		case COLUNA_FATOR_VENDA:
			return String.class;
		case COLUNA_PRECO_VENDA:
			return String.class;
		case COLUNA_BLOQUEADO_VENDA:
			return Boolean.class;
		case COLUNA_DATA_CADASTRO:
			return java.util.Date.class;
		default:
			return String.class;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columIndex) {
		Produto produto = this.produtos.get(rowIndex);

		NumberFormat nf = new DecimalFormat("R$ 0.00");

		switch (columIndex) {
		case COLUNA_CODIGO:
			return produto.getIdProduto();
		case COLUNA_NOME:
			return produto.getDescricao();
		case COLUNA_CODIGO_BARRAS:
			return produto.getCodigo_barra();
		case COLUNA_SETOR:
			return produto.getSetor();
		case COLUNA_FATOR_VENDA:
			return produto.getUnidadeVenda();
		case COLUNA_PRECO_VENDA:
			return nf.format(produto.getPreco());
		case COLUNA_BLOQUEADO_VENDA:
			return produto.getBloqueadoVenda();
		case COLUNA_DATA_CADASTRO:
			return produto.getDataCadastro();
		}
		return null;
	}

	public void addProduto(Produto p) {
		this.produtos.add(p);
		this.fireTableDataChanged();
	}

	public void removeProduto(Integer codigo_produto) {
		this.produtos.removeIf(produto -> produto.getIdProduto().equals(codigo_produto));
		this.fireTableDataChanged();
	}

	public void recarregarTabela(JTable tabela, ArrayList<Produto> produtos) {
		ModeloTabelaProdutos modelo = new ModeloTabelaProdutos(produtos);
		tabela.setModel(modelo);
	}

}
