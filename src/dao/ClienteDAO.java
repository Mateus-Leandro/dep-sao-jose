package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import db.DB;
import entities.cliente.Cliente;

public class ClienteDAO {

	private Connection conn;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public ClienteDAO() {
	}

	public Cliente inserirCliente(Cliente cliente) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn.setAutoCommit(false);
			cliente.setIdCliente(null);
			ps = conn.prepareStatement(
					"INSERT INTO clientes " + "(nome, apelido, documento, inscricaoEstadual, cep, cidade, endereco, "
							+ "referencia, numero, bairro, email, celular, telefone, bloqueado, dataCadastro)"
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getApelido());
			ps.setString(3, cliente.getCpf_cnpj());
			ps.setString(4, cliente.getInscricao_estadual());
			ps.setString(5, cliente.getCep());
			ps.setString(6, cliente.getCidade());
			ps.setString(7, cliente.getEndereco());
			ps.setString(8, cliente.getReferencia());
			ps.setString(9, cliente.getNumero());
			ps.setString(10, cliente.getBairro());
			ps.setString(11, cliente.getEmail());
			ps.setString(12, cliente.getCelular());
			ps.setString(13, cliente.getTelefone());
			ps.setBoolean(14, cliente.getBloqueado());
			ps.setDate(15, new java.sql.Date(System.currentTimeMillis()));

			ps.execute();
			conn.commit();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				cliente.setIdCliente(rs.getInt(1));
			}

			return cliente;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean alterar_cliente(Cliente cliente) {
		conn = DB.getConnection();
		PreparedStatement ps = null;

		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(
					"UPDATE clientes SET nome = ?, apelido = ?, documento = ?, inscricaoEstadual = ?, cep = ?, cidade = ?, endereco = ?, "
							+ "referencia = ?, numero = ?, bairro = ?, email = ?, celular = ?, telefone = ?, bloqueado = ? WHERE idCliente = ?");

			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getApelido());
			ps.setString(3, cliente.getCpf_cnpj());
			ps.setString(4, cliente.getInscricao_estadual());
			ps.setString(5, cliente.getCep());
			ps.setString(6, cliente.getCidade());
			ps.setString(7, cliente.getEndereco());
			ps.setString(8, cliente.getReferencia());
			ps.setString(9, cliente.getNumero());
			ps.setString(10, cliente.getBairro());
			ps.setString(11, cliente.getEmail());
			ps.setString(12, cliente.getCelular());
			ps.setString(13, cliente.getTelefone());
			ps.setBoolean(14, cliente.getBloqueado());
			ps.setInt(15, cliente.getIdCliente());

			ps.execute();
			conn.commit();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public boolean excluirCliente(Integer codigo) {
		conn = DB.getConnection();

		PreparedStatement ps = null;

		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("DELETE FROM clientes WHERE idCliente = ?");
			ps.setInt(1, codigo);

			ps.execute();
			conn.commit();

			return true;
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			return false;
		}

		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<Cliente> listarClientes(ArrayList<Cliente> clientes) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM clientes");
			rs = ps.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setIdCliente(rs.getInt("idCliente"));
				cliente.setBloqueado(rs.getBoolean("bloqueado"));
				cliente.setNome(rs.getString("nome"));
				cliente.setApelido(rs.getString("apelido"));
				cliente.setCpf_cnpj(rs.getString("documento"));
				cliente.setInscricao_estadual(rs.getString("inscricaoEstadual"));
				cliente.setCep(rs.getString("cep"));
				cliente.setCidade(rs.getString("cidade"));
				cliente.setEndereco(rs.getString("endereco"));
				cliente.setReferencia(rs.getString("referencia"));
				cliente.setNumero(rs.getString("numero"));
				cliente.setBairro(rs.getString("bairro"));
				cliente.setEmail(rs.getString("email"));
				cliente.setCelular(rs.getString("celular"));
				cliente.setTelefone(rs.getString("telefone"));
				cliente.setDataCadastro(rs.getDate("dataCadastro"));
				clientes.add(cliente);
			}
			return clientes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public ArrayList<Cliente> listarClientes_nome(ArrayList<Cliente> clientes, String nome) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM clientes WHERE nome LIKE ?");
			ps.setString(1, nome);
			rs = ps.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setIdCliente(rs.getInt("idCliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setApelido(rs.getString("apelido"));
				cliente.setCpf_cnpj(rs.getString("documento"));
				cliente.setInscricao_estadual(rs.getString("inscricaoEstadual"));
				cliente.setCep(rs.getString("cep"));
				cliente.setCidade(rs.getString("cidade"));
				cliente.setEndereco(rs.getString("endereco"));
				cliente.setReferencia(rs.getString("referencia"));
				cliente.setNumero(rs.getString("numero"));
				cliente.setBairro(rs.getString("bairro"));
				cliente.setEmail(rs.getString("email"));
				cliente.setCelular(rs.getString("celular"));
				cliente.setTelefone(rs.getString("telefone"));
				cliente.setDataCadastro(rs.getDate("dataCadastro"));
				cliente.setBloqueado(rs.getBoolean("bloqueado"));
				clientes.add(cliente);
			}
			return clientes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public ArrayList<Cliente> listarClientes_codigo(ArrayList<Cliente> clientes, String codigo) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM clientes WHERE idCliente LIKE ?");
			ps.setString(1, codigo);
			rs = ps.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setIdCliente(rs.getInt("idCliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setApelido(rs.getString("apelido"));
				cliente.setCpf_cnpj(rs.getString("documento"));
				cliente.setInscricao_estadual(rs.getString("inscricaoEstadual"));
				cliente.setCep(rs.getString("cep"));
				cliente.setCidade(rs.getString("cidade"));
				cliente.setEndereco(rs.getString("endereco"));
				cliente.setReferencia(rs.getString("referencia"));
				cliente.setNumero(rs.getString("numero"));
				cliente.setBairro(rs.getString("bairro"));
				cliente.setEmail(rs.getString("email"));
				cliente.setCelular(rs.getString("celular"));
				cliente.setTelefone(rs.getString("telefone"));
				cliente.setDataCadastro(rs.getDate("dataCadastro"));
				cliente.setBloqueado(rs.getBoolean("bloqueado"));
				clientes.add(cliente);
			}
			return clientes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public ArrayList<Cliente> listarClientes_apelido(ArrayList<Cliente> clientes, String apelido) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM clientes WHERE apelido LIKE ?");
			ps.setString(1, apelido);
			rs = ps.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setIdCliente(rs.getInt("idCliente"));
				cliente.setNome(rs.getString("nome"));
				cliente.setApelido(rs.getString("apelido"));
				cliente.setCpf_cnpj(rs.getString("documento"));
				cliente.setInscricao_estadual(rs.getString("inscricaoEstadual"));
				cliente.setCep(rs.getString("cep"));
				cliente.setCidade(rs.getString("cidade"));
				cliente.setEndereco(rs.getString("endereco"));
				cliente.setReferencia(rs.getString("referencia"));
				cliente.setNumero(rs.getString("numero"));
				cliente.setBairro(rs.getString("bairro"));
				cliente.setEmail(rs.getString("email"));
				cliente.setCelular(rs.getString("celular"));
				cliente.setTelefone(rs.getString("telefone"));
				cliente.setDataCadastro(rs.getDate("dataCadastro"));
				cliente.setBloqueado(rs.getBoolean("bloqueado"));
				clientes.add(cliente);
			}
			return clientes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void alimenta_lt_clientes(String tipo_busca, String texto_buscado, DefaultListModel<Cliente> list_model,
			ArrayList<Cliente> lista_clientes) {

		list_model.clear();
		lista_clientes.clear();

		
		if(texto_buscado != null) {
			if (tipo_busca.toUpperCase().equals("NOME")) {
				lista_clientes = listarClientes_nome(lista_clientes, texto_buscado + "%");
			} else if (tipo_busca.toUpperCase().equals("APELIDO")) {
				lista_clientes = listarClientes_apelido(lista_clientes, texto_buscado + "%");
			} else {
				lista_clientes = listarClientes_codigo(lista_clientes, texto_buscado + "%");
			}

			for (Cliente cliente : lista_clientes) {
				list_model.addElement(cliente);
			}
		}
	}

	public String validarDocumento(String documento, String id) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT idCliente, nome FROM clientes WHERE documento = ? and idCliente = ?");
			ps.setString(1, documento);
			ps.setString(2, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				return null;
			} else {
				ps = conn.prepareStatement("SELECT nome FROM clientes WHERE documento = ?");
				ps.setString(1, documento);
				rs = ps.executeQuery();

				if (rs.next()) {
					return rs.getString("nome");
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
