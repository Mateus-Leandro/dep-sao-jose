package tables.tableModels;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import entities.pessoa.Cliente;
import entities.pessoa.Fornecedor;

public class ModeloTabelaFornecedores extends AbstractTableModel {

	/**
	 * 
	 */
	
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static final long serialVersionUID = 1L;
	private String colunas[] = { "Cod", "Bloq. Ped.", "Bloq. Cot.", "Razão", "Nome Fant.", "Celular", "Endereco",
			"Numero", "Referencia", "Cidade", "Bairro", "Cep", "Documento", "I.E.", "Email", "Telefone", "Dt. Cad." };
	private ArrayList<Fornecedor> fornecedores;
	private final int COLUNA_CODIGO = 0;
	private final int COLUNA_BLOQUEADO_PEDIDO = 1;
	private final int COLUNA_BLOQUEADO_COTACAO = 2;
	private final int COLUNA_NOME = 3;
	private final int COLUNA_NOME_FANTASIA = 4;
	private final int COLUNA_CELULAR = 5;
	private final int COLUNA_ENDERECO = 6;
	private final int COLUNA_NUMERO = 7;
	private final int COLUNA_REFERENCIA = 8;
	private final int COLUNA_CIDADE = 9;
	private final int COLUNA_BAIRRO = 10;
	private final int COLUNA_CEP = 11;
	private final int COLUNA_DOCUMENTO = 12;
	private final int COLUNA_INSCRICAO_ESTADUAL = 13;
	private final int COLUNA_EMAIL = 14;
	private final int COLUNA_TELEFONE = 15;
	private final int COLUNA_DATA_CADASTRO = 16;

	public ModeloTabelaFornecedores(ArrayList<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
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
		if (fornecedores != null)
			return fornecedores.size();
		else {
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
		case COLUNA_BLOQUEADO_PEDIDO:
			return Boolean.class;
		case COLUNA_BLOQUEADO_COTACAO:
			return Boolean.class;
		case COLUNA_NOME:
			return String.class;
		case COLUNA_NOME_FANTASIA:
			return String.class;
		case COLUNA_CELULAR:
			return String.class;
		case COLUNA_ENDERECO:
			return String.class;
		case COLUNA_NUMERO:
			return String.class;
		case COLUNA_REFERENCIA:
			return String.class;
		case COLUNA_CIDADE:
			return String.class;
		case COLUNA_BAIRRO:
			return String.class;
		case COLUNA_CEP:
			return String.class;
		case COLUNA_DOCUMENTO:
			return String.class;
		case COLUNA_INSCRICAO_ESTADUAL:
			return String.class;
		case COLUNA_EMAIL:
			return String.class;
		case COLUNA_TELEFONE:
			return String.class;
		case COLUNA_DATA_CADASTRO:
			return String.class;
		default:
			return String.class;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columIndex) {
		Fornecedor fornecedor = this.fornecedores.get(rowIndex);
		switch (columIndex) {
		case COLUNA_CODIGO:
			return fornecedor.getId();
		case COLUNA_BLOQUEADO_PEDIDO:
			return fornecedor.getBloqueado();
		case COLUNA_BLOQUEADO_COTACAO:
			return fornecedor.getBloqueadoCotacao();
		case COLUNA_NOME:
			return fornecedor.getNome();
		case COLUNA_NOME_FANTASIA:
			return fornecedor.getApelido();
		case COLUNA_CELULAR:
			return fornecedor.getCelular();
		case COLUNA_ENDERECO:
			return fornecedor.getEndereco();
		case COLUNA_NUMERO:
			return fornecedor.getNumero();
		case COLUNA_REFERENCIA:
			return fornecedor.getReferencia();
		case COLUNA_CIDADE:
			return fornecedor.getCidade();
		case COLUNA_BAIRRO:
			return fornecedor.getBairro();
		case COLUNA_CEP:
			return fornecedor.getCep();
		case COLUNA_DOCUMENTO:
			return fornecedor.getCpf_cnpj();
		case COLUNA_INSCRICAO_ESTADUAL:
			return fornecedor.getInscricao_estadual();
		case COLUNA_EMAIL:
			return fornecedor.getEmail();
		case COLUNA_TELEFONE:
			return fornecedor.getTelefone();
		case COLUNA_DATA_CADASTRO:
			return sdf.format(fornecedor.getDataCadastro());
		}
		return null;
	}

	public void addFornecedor(Fornecedor fornecedor) {
		this.fornecedores.add(fornecedor);
		this.fireTableDataChanged();
	}

	public void removeFornecedor(Integer codigo_fornecedor) {
		this.fornecedores.removeIf(fornecedor -> fornecedor.getId().equals(codigo_fornecedor));
		this.fireTableDataChanged();
	}

	public void recarregarTabela(JTable tabela, ArrayList<Cliente> clientes) {
		ModeloTabelaFornecedores modelo = new ModeloTabelaFornecedores(fornecedores);
		tabela.setModel(modelo);
	}

}
