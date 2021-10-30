package tables.tableModels;

import java.sql.Date;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import entities.Cliente;

public class ModeloTabelaClientes extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String colunas[] = { "Cod", "Bloq.", "Nome", "Apelido", "Celular", "Endereco", "Numero", "Referencia", "Cidade",
			"Bairro", "Cep", "Documento", "I.E.", "Email", "Telefone", "Dt. Cad." };
	private ArrayList<Cliente> clientes;
	private final int COLUNA_CODIGO = 0;
	private final int COLUNA_BLOQUEADO = 1;
	private final int COLUNA_NOME = 2;
	private final int COLUNA_APELIDO = 3;
	private final int COLUNA_CELULAR = 4;
	private final int COLUNA_ENDERECO = 5;
	private final int COLUNA_NUMERO = 6;
	private final int COLUNA_REFERENCIA = 7;
	private final int COLUNA_CIDADE = 8;
	private final int COLUNA_BAIRRO = 9;
	private final int COLUNA_CEP = 10;
	private final int COLUNA_DOCUMENTO = 11;
	private final int COLUNA_INSCRICAO = 12;
	private final int COLUNA_EMAIL = 13;
	private final int COLUNA_TELEFONE = 14;
	private final int COLUNA_DATA_CADASTRO = 15;

	public ModeloTabelaClientes(ArrayList<Cliente> clientes) {
		this.clientes = clientes;
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
		if (clientes != null)
			return clientes.size();
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
		case COLUNA_BLOQUEADO:
			return Boolean.class;
		case COLUNA_NOME:
			return String.class;
		case COLUNA_APELIDO:
			return String.class;
		case COLUNA_DOCUMENTO:
			return String.class;
		case COLUNA_INSCRICAO:
			return String.class;
		case COLUNA_CEP:
			return String.class;
		case COLUNA_CIDADE:
			return String.class;
		case COLUNA_ENDERECO:
			return String.class;
		case COLUNA_REFERENCIA:
			return String.class;
		case COLUNA_NUMERO:
			return String.class;
		case COLUNA_BAIRRO:
			return String.class;
		case COLUNA_EMAIL:
			return String.class;
		case COLUNA_CELULAR:
			return String.class;
		case COLUNA_TELEFONE:
			return String.class;
		case COLUNA_DATA_CADASTRO:
			return Date.class;
		default:
			return String.class;
		}
	}

	@Override
	public Object getValueAt(int rowIndex, int columIndex) {

		Cliente cliente = this.clientes.get(rowIndex);
		switch (columIndex) {
		case COLUNA_CODIGO:
			return cliente.getIdCliente();
		case COLUNA_BLOQUEADO:
			return cliente.getBloqueado();
		case COLUNA_NOME:
			return cliente.getNome();
		case COLUNA_APELIDO:
			return cliente.getApelido();
		case COLUNA_DOCUMENTO:
			return cliente.getCpf_cnpj();
		case COLUNA_INSCRICAO:
			return cliente.getInscricao_estadual();
		case COLUNA_CEP:
			return cliente.getCep();
		case COLUNA_CIDADE:
			return cliente.getCidade();
		case COLUNA_ENDERECO:
			return cliente.getEndereco();
		case COLUNA_REFERENCIA:
			return cliente.getReferencia();
		case COLUNA_NUMERO:
			return cliente.getNumero();
		case COLUNA_BAIRRO:
			return cliente.getBairro();
		case COLUNA_EMAIL:
			return cliente.getEmail();
		case COLUNA_CELULAR:
			return cliente.getCelular();
		case COLUNA_TELEFONE:
			return cliente.getTelefone();
		case COLUNA_DATA_CADASTRO:
			return cliente.getDataCadastro();
		}
		
		return null;
	}

	public void addCliente(Cliente c) {
		this.clientes.add(c);
		this.fireTableDataChanged();
	}

	public void removeProduto(Integer codigo_cliente) {
		this.clientes.removeIf(cliente -> cliente.getIdCliente().equals(codigo_cliente));
		this.fireTableDataChanged();
	}

	public void recarregarTabela(JTable tabela, ArrayList<Cliente> clientes) {
		ModeloTabelaClientes modelo = new ModeloTabelaClientes(clientes);
		tabela.setModel(modelo);
	}

}
