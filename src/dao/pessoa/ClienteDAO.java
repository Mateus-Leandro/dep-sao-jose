package dao.pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

import db.DB;
import entities.pessoa.Cliente;
import entities.pessoa.Pessoa;

public class ClienteDAO {

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public ClienteDAO() {
	}

	public Pessoa inserirCliente(Pessoa cliente) {
		conn = DB.getConnection();

		try {
			conn.setAutoCommit(false);
			cliente.setId(null);
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
				cliente.setId(rs.getInt(1));
			}

			return cliente;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
			DB.closeConnection(conn);
		}
	}

	public boolean alterar_cliente(Pessoa cliente) {
		conn = DB.getConnection();

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
			ps.setInt(15, cliente.getId());

			ps.execute();
			conn.commit();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			DB.closeStatement(ps);
			DB.closeConnection(conn);
		}

	}

	public boolean excluirCliente(Integer codigo) {
		conn = DB.getConnection();

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
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DB.closeStatement(ps);
			DB.closeConnection(conn);
		}
	}

	public ArrayList<Cliente> listarClientes(ArrayList<Cliente> clientes, String tipo_busca, String valor_buscado,
			Integer limite) {
		conn = DB.getConnection();

		try {
			if (tipo_busca == null) {
				if (limite == null) {
					ps = conn.prepareStatement("SELECT * FROM clientes");
				} else {
					ps = conn.prepareStatement("SELECT * FROM clientes LIMIT " + limite);
				}
			} else {
				switch (tipo_busca.toUpperCase()) {
				case "NOME":
					if (limite == null) {
						ps = conn.prepareStatement("SELECT * FROM clientes WHERE nome LIKE ?");
					} else {
						ps = conn.prepareStatement("SELECT * FROM clientes WHERE nome LIKE ? LIMIT " + limite);
					}
					break;
				case "APELIDO":
					if (limite == null) {
						ps = conn.prepareStatement("SELECT * FROM clientes WHERE apelido LIKE ?");
					} else {
						ps = conn.prepareStatement("SELECT * FROM clientes WHERE apelido LIKE ? LIMIT " + limite);
					}
					break;
				case "CÓDIGO":
					if (limite == null) {
						ps = conn.prepareStatement("SELECT * FROM clientes WHERE idCliente LIKE ?");
					} else {
						ps = conn.prepareStatement("SELECT * FROM clientes WHERE idCliente LIKE ? LIMIT " + limite);
					}
					break;
				}

				if (valor_buscado != null) {
					ps.setString(1, valor_buscado);
				} else {
					ps.setString(1, "%");
				}
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("idCliente"));
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
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
			DB.closeConnection(conn);
		}

	}

	public void alimenta_lt_clientes(String tipo_busca, String texto_buscado, DefaultListModel<Cliente> list_model,
			ArrayList<Cliente> lista_clientes) {

		list_model.clear();
		lista_clientes.clear();

		lista_clientes = listarClientes(lista_clientes, tipo_busca, texto_buscado + "%", 50);

		for (Cliente cliente : lista_clientes) {
			list_model.addElement(cliente);
		}
	}

	public String validarDocumento(String documento, String id) {
		conn = DB.getConnection();
		try {
			if (id != null) {
				ps = conn
						.prepareStatement("SELECT idCliente, nome FROM clientes WHERE documento = ? and idCliente = ?");
				ps.setString(1, documento);
				ps.setString(2, id);
				rs = ps.executeQuery();
				if (rs.next()) {
					return null;
				}
			} else {
				ps = conn.prepareStatement("SELECT idCliente, nome FROM clientes WHERE documento = ?");
				ps.setString(1, documento);
				rs = ps.executeQuery();
			}

			if (rs.next()) {
				return "Código: " + rs.getString("idCliente") + "\nNome: " + rs.getString("nome");
			} else {
				ps = conn.prepareStatement("SELECT idCliente, nome FROM clientes WHERE documento = ?");
				ps.setString(1, documento);
				rs = ps.executeQuery();

				if (rs.next()) {
					return "Código: " + rs.getString("idCliente") + "\nNome: " + rs.getString("nome");
				} else {
					return null;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
			DB.closeConnection(conn);
		}
	}

	public Boolean cliente_com_orcamento(String id_cliente) {
		conn = DB.getConnection();
		try {
			ps = conn.prepareStatement("SELECT idCliente FROM orcamento WHERE idCliente = ?");
			ps.setString(1, id_cliente);
			rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return true;
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
			DB.closeConnection(conn);
		}
	}

}
