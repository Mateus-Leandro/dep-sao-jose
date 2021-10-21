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
	private String colunas[] = { "Cod", "Nome", "Celular", "Cidade", "Endereco", "Numero", "Dt. Cad." };
	private ArrayList<Cliente> clientes;
	private final int COLUNA_CODIGO = 0;
	private final int COLUNA_NOME = 1;
	private final int COLUNA_CELULAR = 2;
	private final int COLUNA_CIDADE = 3;
	private final int COLUNA_ENDERECO = 4;
	private final int COLUNA_NUMERO = 5;
	private final int COLUNA_DATA_CADASTRO = 6;

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
		case COLUNA_NOME:
			return String.class;
		case COLUNA_CELULAR:
			return String.class;
		case COLUNA_CIDADE:
			return String.class;
		case COLUNA_ENDERECO:
			return String.class;
		case COLUNA_NUMERO:
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
		case COLUNA_NOME:
			return cliente.getNome();
		case COLUNA_CELULAR:
			return cliente.getCelular();
		case COLUNA_CIDADE:
			return cliente.getCidade();
		case COLUNA_ENDERECO:
			return cliente.getEndereco();
		case COLUNA_NUMERO:
			return cliente.getNumero();
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
