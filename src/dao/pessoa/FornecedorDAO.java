package dao.pessoa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import db.DB;
import entities.pessoa.Fornecedor;
import entities.pessoa.Pessoa;

public class FornecedorDAO {

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	public FornecedorDAO() {
	}

	public Fornecedor inserirFornecedor(Pessoa pessoa) {
		conn = DB.getConnection();

		Fornecedor fornecedor = (Fornecedor) pessoa;
		try {
			conn.setAutoCommit(false);
			fornecedor.setId(null);
			ps = conn.prepareStatement(
					"INSERT INTO fornecedores (razao, nomeFantasia, documento, inscricaoEstadual, "
							+ "cep, cidade, endereco, referencia, numero, bairro, email, celular, telefone, "
							+ "bloqueadoPedido, bloqueadoCotacao, numeroDePedidos, dataCadastro) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
					PreparedStatement.RETURN_GENERATED_KEYS);

			ps.setString(1, fornecedor.getNome());
			ps.setString(2, fornecedor.getApelido());
			ps.setString(3, fornecedor.getCpf_cnpj());
			ps.setString(4, fornecedor.getInscricao_estadual());
			ps.setString(5, fornecedor.getCep());
			ps.setString(6, fornecedor.getCidade());
			ps.setString(7, fornecedor.getEndereco());
			ps.setString(8, fornecedor.getReferencia());
			ps.setString(9, fornecedor.getNumero());
			ps.setString(10, fornecedor.getBairro());
			ps.setString(11, fornecedor.getEmail());
			ps.setString(12, fornecedor.getCelular());
			ps.setString(13, fornecedor.getTelefone());
			ps.setBoolean(14, fornecedor.getBloqueado());
			ps.setBoolean(15, fornecedor.getBloqueadoCotacao());
			ps.setInt(16, 0);
			ps.setDate(17, new java.sql.Date(System.currentTimeMillis()));

			ps.execute();
			conn.commit();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				fornecedor.setId(rs.getInt(1));
			}

			return fornecedor;
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
			DB.closeConnection(conn);
		}
		return null;
	}

	public Boolean alterarFornecedor(Pessoa pessoa) {
		conn = DB.getConnection();

		Fornecedor fornecedor = (Fornecedor) pessoa;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("UPDATE fornecedores SET razao = ?, nomeFantasia = ?, documento = ?, "
					+ "inscricaoEstadual = ?, " + "cep = ?, cidade = ?, endereco = ?, referencia = ?, "
					+ "numero = ?, bairro = ?, email = ?, " + "celular = ?, telefone = ?, bloqueadoPedido = ?, "
					+ "bloqueadoCotacao = ? WHERE id = ?");

			ps.setString(1, fornecedor.getNome());
			ps.setString(2, fornecedor.getApelido());
			ps.setString(3, fornecedor.getCpf_cnpj());
			ps.setString(4, fornecedor.getInscricao_estadual());
			ps.setString(5, fornecedor.getCep());
			ps.setString(6, fornecedor.getCidade());
			ps.setString(7, fornecedor.getEndereco());
			ps.setString(8, fornecedor.getReferencia());
			ps.setString(9, fornecedor.getNumero());
			ps.setString(10, fornecedor.getBairro());
			ps.setString(11, fornecedor.getEmail());
			ps.setString(12, fornecedor.getCelular());
			ps.setString(13, fornecedor.getTelefone());
			ps.setBoolean(14, fornecedor.getBloqueado());
			ps.setBoolean(15, fornecedor.getBloqueadoCotacao());
			ps.setInt(16, fornecedor.getId());

			ps.execute();
			conn.commit();

			return true;

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
			DB.closeConnection(conn);
		}
	}
	
	public boolean excluirFornecedor(Integer codigo) {
		conn = DB.getConnection();

		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("DELETE FROM fornecedores WHERE id = ?");
			ps.setInt(1, codigo);

			ps.execute();
			conn.commit();

			return true;
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			return false;
		} finally {
			DB.closeStatement(ps);
			DB.closeConnection(conn);
		}
	}


	public ArrayList<Fornecedor> listarFornecedores(ArrayList<Fornecedor> fornecedores, String tipo_busca,
			String valor_buscado, Integer limite) {
		conn = DB.getConnection();

		try {
			if (tipo_busca == null) {
				if (limite == null) {
					ps = conn.prepareStatement("SELECT * FROM fornecedores");
				} else {
					ps = conn.prepareStatement("SELECT * FROM fornecedores LIMIT " + limite);
				}
			} else {
				switch (tipo_busca.toUpperCase()) {
				case "NOME":
					if (limite == null) {
						ps = conn.prepareStatement("SELECT * FROM fornecedores WHERE razao LIKE ?");
					} else {
						ps = conn.prepareStatement("SELECT * FROM fornecedores WHERE razao LIKE ? LIMIT " + limite);
					}
					break;
				case "NOME FANT.":
					if (limite == null) {
						ps = conn.prepareStatement("SELECT * FROM fornecedores WHERE nomeFantasia LIKE ?");
					} else {
						ps = conn.prepareStatement(
								"SELECT * FROM fornecedores WHERE nomeFantasia LIKE ? LIMIT " + limite);
					}
					break;
				case "COD.":
					if (limite == null) {
						ps = conn.prepareStatement("SELECT * FROM fornecedores WHERE id LIKE ?");
					} else {
						ps = conn.prepareStatement("SELECT * FROM fornecedores WHERE id LIKE ? LIMIT " + limite);
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
				Fornecedor fornecedor = new Fornecedor();
				fornecedor.setId(rs.getInt("id"));
				fornecedor.setNome(rs.getString("razao"));
				fornecedor.setApelido(rs.getString("nomeFantasia"));
				fornecedor.setCpf_cnpj(rs.getString("documento"));
				fornecedor.setInscricao_estadual(rs.getString("inscricaoEstadual"));
				fornecedor.setCep(rs.getString("cep"));
				fornecedor.setCidade(rs.getString("cidade"));
				fornecedor.setEndereco(rs.getString("endereco"));
				fornecedor.setReferencia(rs.getString("referencia"));
				fornecedor.setNumero(rs.getString("numero"));
				fornecedor.setBairro(rs.getString("bairro"));
				fornecedor.setEmail(rs.getString("email"));
				fornecedor.setCelular(rs.getString("celular"));
				fornecedor.setTelefone(rs.getString("telefone"));
				fornecedor.setBloqueado(rs.getBoolean("bloqueadoPedido"));
				fornecedor.setBloqueado_cotacao(rs.getBoolean("bloqueadoCotacao"));
				fornecedor.setNumero_de_pedidos(rs.getInt("numeroDePedidos"));
				fornecedor.setUltimo_pedido(rs.getInt("ultimoPedido"));
				fornecedor.setDataCadastro(rs.getDate("dataCadastro"));
				fornecedores.add(fornecedor);
			}
			return fornecedores;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
			DB.closeConnection(conn);
		}

	}
	
	public String validarDocumento(String documento, String id) {
		conn = DB.getConnection();
		try {
			
			String query = "SELECT id, razao FROM fornecedores ";
			
			if (id != null) {
				ps = conn
						.prepareStatement(query + "WHERE documento = ? and id = ?");
				ps.setString(1, documento);
				ps.setString(2, id);
				rs = ps.executeQuery();
				if (rs.next()) {
					return null;
				}
			} else {
				ps = conn.prepareStatement(query + "WHERE documento = ?");
				ps.setString(1, documento);
				rs = ps.executeQuery();
			}

			if (rs.next()) {
				return "Código: " + rs.getString("id") + "\nNome: " + rs.getString("razao");
			} else {
				ps = conn.prepareStatement(query + "WHERE documento = ?");
				ps.setString(1, documento);
				rs = ps.executeQuery();

				if (rs.next()) {
					return "Código: " + rs.getString("id") + "\nNome: " + rs.getString("razao");
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
}
