package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import db.DB;
import entidades.Produto;

public class ProdutoDAO {

	private Connection conn;

	public ProdutoDAO() {
	}

	public ProdutoDAO(Connection conn) {
		this.conn = conn;
	}

//------------Inserir-----------------
	public Produto inserirProduto(Produto produto) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e1) {
			e1.getMessage();
		}

		try {
			produto.setIdProduto(null);
			ps = conn.prepareStatement(
					"INSERT INTO produto (descricao, codigoBarras, codSetor, "
							+ "unidadeVenda, preco, bloqueadoVenda,dataCadastro) VALUES (?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, produto.getDescricao().trim());
			ps.setString(2, produto.getCodigo_barra());
			ps.setInt(3, produto.getSetor().getCodSetor());
			ps.setString(4, produto.getUnidadeVenda());
			ps.setDouble(5, produto.getPreco());
			ps.setBoolean(6, produto.getBloqueadoVenda());
			ps.setDate(7, new java.sql.Date(System.currentTimeMillis()));
			ps.execute();
			conn.commit();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				produto.setIdProduto(rs.getInt(1));
			}

			JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso! " + "\nCódigo: "
					+ produto.getIdProduto() + "\nProduto: " + produto.getDescricao());

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao inserir novo produto!" + e.getMessage());
			try {
				conn.rollback();
			} catch (SQLException erroRollBack) {
				erroRollBack.printStackTrace();
			} finally {
				DB.closeResultSet(rs);
				DB.closeStatement(ps);
				DB.closeConnection(conn);
			}
			return null;
		}
		return produto;
	}

	// -----------Alterar---------------
	public boolean alterarProduto(Produto produto) {
		conn = DB.getConnection();
		PreparedStatement ps = null;

		try {
			conn.setAutoCommit(false);

			ps = conn.prepareStatement("UPDATE produto SET descricao = ?, "
					+ "codigoBarras = ?, preco = ?, codSetor = ?, unidadeVenda = ?, bloqueadoVenda = ? "
					+ "WHERE idProduto = ?");
			ps.setString(1, produto.getDescricao());
			ps.setString(2, produto.getCodigo_barra());
			ps.setDouble(3, produto.getPreco());
			ps.setInt(4, produto.getSetor().getCodSetor());
			ps.setString(5, produto.getUnidadeVenda());
			ps.setBoolean(6, produto.getBloqueadoVenda());
			ps.setInt(7, produto.getIdProduto());
			ps.executeUpdate();
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				return false;
			} catch (SQLException e1) {
				e1.getMessage();
			}
		}
		JOptionPane.showMessageDialog(null, "Produto alterado!");
		return true;
	}

	// --------Deletar--------------
	public boolean deletarProduto(int id) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("DELETE FROM produto WHERE idProduto = ?");
			ps.setInt(1, id);
			ps.execute();
			conn.commit();
			return true;
		} catch (Exception e) {
			e.getMessage();
			return false;
		}
	}

//---------Listar---------------
	public ResultSet listarTodosProdutos() {
		conn = DB.getConnection();
		PreparedStatement ps = null;

		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT idProduto, descricao, codigoBarras, produto.codSetor ,"
					+ "setor.nome, unidadeVenda, preco, bloqueadoVenda, dataCadastro "
					+ "from produto inner join setor on produto.codSetor = setor.codSetor ORDER BY descricao");
			rs = ps.executeQuery();

		} catch (Exception e) {
			e.getMessage();
		}
		return rs;
	}

	// -----Listar produto por nome-----
	public ResultSet listarProdutosNome(String nome) {
		conn = DB.getConnection();
		PreparedStatement ps = null;

		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT idProduto, descricao, codigoBarras, produto.codSetor ,"
					+ "setor.nome, unidadeVenda, preco, bloqueadoVenda, dataCadastro "
					+ "from produto inner join setor on produto.codSetor = setor.codSetor WHERE produto.descricao LIKE ? ORDER BY descricao");
			ps.setString(1, nome);
			rs = ps.executeQuery();

		} catch (Exception e) {
			e.getMessage();
		}
		return rs;
	}

	// -----Listar produto por código interno----
	public ResultSet listarProdutosCodigo(String codInterno) {
		conn = DB.getConnection();
		PreparedStatement ps = null;

		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT idProduto, descricao, codigoBarras, produto.codSetor ,"
					+ "setor.nome, unidadeVenda, preco, bloqueadoVenda, dataCadastro "
					+ "from produto inner join setor on produto.codSetor = setor.codSetor WHERE produto.idProduto LIKE ? ORDER BY descricao");
			ps.setString(1, codInterno);
			rs = ps.executeQuery();

		} catch (Exception e) {
			e.getMessage();
		}
		return rs;
	}

	// -----Listar produto por código de barras----
	public ResultSet listarProdutosBarras(String barras) {
		conn = DB.getConnection();
		PreparedStatement ps = null;

		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT idProduto, descricao, codigoBarras, produto.codSetor ,"
					+ "setor.nome, unidadeVenda, preco, bloqueadoVenda, dataCadastro "
					+ "from produto inner join setor on produto.codSetor = setor.codSetor WHERE produto.codigoBarras LIKE ? ORDER BY descricao");
			ps.setString(1, barras);
			rs = ps.executeQuery();

		} catch (Exception e) {
			e.getMessage();
		}
		return rs;
	}

	// --Testar se o codigo de barras ja existe em outro item
	public boolean testaBarrasInclusao(String barras) {

		conn = DB.getConnection();
		PreparedStatement ps = null;

		ResultSet rs = null;

		if (barras.toString() != null) {
			try {
				ps = conn.prepareStatement("SELECT codigoBarras FROM produto WHERE codigoBarras = ?");
				ps.setString(1, barras);
				rs = ps.executeQuery();

				if (rs.next()) {
					return false;
				} else {
					ps = conn.prepareStatement("SELECT barras FROM barras_produto WHERE barras = ?");
					ps.setString(1, barras);
					rs = ps.executeQuery();
					if (rs.next()) {
						return false;
					} else {
						return true;
					}
				}
			} catch (Exception e) {
				e.getMessage();
				return false;
			}

		} else {
			return true;
		}
	}

	public boolean testaBarrasAlteracao(Integer codigo, String barras) {
		conn = DB.getConnection();
		PreparedStatement ps = null;

		ResultSet rs = null;

		if (barras.toString() != null) {

			try {
				ps = conn.prepareStatement("SELECT codigoBarras FROM produto WHERE codigoBarras = ? AND idProduto = ?");
				ps.setString(1, barras);
				ps.setInt(2, codigo);
				rs = ps.executeQuery();

				if (rs.next()) {
					return true;
				} else {
					ps = conn.prepareStatement("SELECT codigoBarras FROM produto WHERE codigoBarras = ?");
					ps.setString(1, barras);
					rs = ps.executeQuery();
					if (rs.next()) {
						return false;
					} else {
						ps = conn.prepareStatement(
								"SELECT barras FROM barras_produto WHERE barras = ? AND idProduto = ?");
						ps.setString(1, barras);
						ps.setInt(2, codigo);
						rs = ps.executeQuery();
						if (rs.next()) {
							return true;
						} else {
							ps = conn.prepareStatement("SELECT barras FROM barras_produto WHERE barras = ?");
							ps.setString(1, barras);
							rs = ps.executeQuery();
							if (rs.next()) {
								return false;
							} else {
								return true;
							}
						}
					}
				}
			} catch (Exception e) {
				e.getMessage();
				return false;
			}
		} else {
			return true;
		}

	}

}
