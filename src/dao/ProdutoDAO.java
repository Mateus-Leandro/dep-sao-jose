package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import db.DB;
import entities.Produto;
import entities.Setor;

public class ProdutoDAO {

	private Connection conn;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public ProdutoDAO() {
	}

//------------Inserir-----------------
	public Produto inserirProduto(Produto produto) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn.setAutoCommit(false);
			produto.setIdProduto(null);
			ps = conn.prepareStatement(
					"INSERT INTO produto (descricao, codSetor, "
							+ "unidadeVenda, preco, bloqueadoVenda,dataCadastro) VALUES (?,?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, produto.getDescricao().trim());
			ps.setInt(2, produto.getSetor().getCodSetor());
			ps.setString(3, produto.getUnidadeVenda());
			ps.setDouble(4, produto.getPreco());
			ps.setBoolean(5, produto.getBloqueadoVenda());
			ps.setDate(6, new java.sql.Date(System.currentTimeMillis()));
			ps.execute();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				produto.setIdProduto(rs.getInt(1));

				if (produto.getCodigo_barra() != null) {
					ps = conn.prepareStatement(
							"INSERT INTO barras_produto (idProduto, barras, principal, dt_vinculacao) VALUES (?, ?, ?, ?)");
					ps.setInt(1, produto.getIdProduto());
					ps.setString(2, produto.getCodigo_barra());
					ps.setBoolean(3, true);
					ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
					ps.execute();

				}

			}
			conn.commit();

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

	// ----------- r---------------
	public boolean alterarProduto(Produto produto) {
		conn = DB.getConnection();
		PreparedStatement ps = null;

		try {
			conn.setAutoCommit(false);

			ps = conn.prepareStatement("UPDATE produto SET descricao = ?, "
					+ "preco = ?, codSetor = ?, unidadeVenda = ?, bloqueadoVenda = ? " + "WHERE idProduto = ?");
			ps.setString(1, produto.getDescricao());
			ps.setDouble(2, produto.getPreco());
			ps.setInt(3, produto.getSetor().getCodSetor());
			ps.setString(4, produto.getUnidadeVenda());
			ps.setBoolean(5, produto.getBloqueadoVenda());
			ps.setInt(6, produto.getIdProduto());
			ps.executeUpdate();
			
			ps = conn.prepareStatement("UPDATE barras_produto "
					+ "SET barras = ? "
					+ "WHERE idProduto = ? "
					+ "AND principal IS TRUE");
			ps.setString(1, produto.getCodigo_barra());
			ps.setInt(2, produto.getIdProduto());
			ps.execute();
			
			conn.commit();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao alterar produto!" + e.getMessage(), "Alteração de produto",
					JOptionPane.WARNING_MESSAGE);
			try {
				conn.rollback();
				return false;
			} catch (SQLException e1) {
				e.printStackTrace();
			}
		}
		JOptionPane.showMessageDialog(null, "Produto alterado!","Alteração de produto",JOptionPane.NO_OPTION);
		return true;
	}

	// --------Deletar--------------
	public boolean deletarProduto(int id) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		try {
			conn.setAutoCommit(false);

			ps = conn.prepareStatement("DELETE FROM barras_produto WHERE idProduto = ?");
			ps.setInt(1, id);
			ps.execute();

			ps = conn.prepareStatement("DELETE FROM produto WHERE idProduto = ?");
			ps.setInt(1, id);
			ps.execute();

			conn.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
	}

//---------Listar---------------
	public ArrayList<Produto> listarTodosProdutos(ArrayList<Produto> produtos) {
		conn = DB.getConnection();
		PreparedStatement ps = null;

		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT produto.idProduto, descricao, barras_produto.barras, "
					+ "produto.codSetor, setor.nome, unidadeVenda, preco, bloqueadoVenda, dataCadastro "
					+ "FROM produto " + "INNER JOIN setor ON produto.codSetor = setor.codSetor "
					+ "LEFT JOIN barras_produto ON produto.idProduto = barras_produto.idProduto "
					+ "WHERE barras_produto.principal IS NOT FALSE " + "ORDER BY descricao");
			rs = ps.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setIdProduto(rs.getInt("idProduto"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setCodigo_barra(rs.getString(3));
				produto.setSetor(new Setor(rs.getInt("codSetor"), rs.getString("nome")));
				produto.setUnidadeVenda(rs.getString("unidadeVenda"));

				produto.setPreco(rs.getDouble("preco"));

				produto.setBloqueadoVenda(rs.getBoolean(8));
				String data_formatada = sdf.format(rs.getDate(9));
				produto.setDataCadastro(sdf.parse(data_formatada));
				produtos.add(produto);
			}
			return produtos;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao carregar produtos" + e.getMessage(), "Carregar produtos. ",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}

	// -----Listar produto por nome-----
	public ArrayList<Produto> listarProdutosNome(ArrayList<Produto> produtos, String nome) {
		conn = DB.getConnection();
		PreparedStatement ps = null;

		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT produto.idProduto, descricao, barras_produto.barras, produto.codSetor, "
					+ "setor.nome, unidadeVenda, preco, bloqueadoVenda, dataCadastro "
					+ "FROM produto INNER JOIN setor ON produto.codSetor = setor.codSetor "
					+ "LEFT JOIN barras_produto ON produto.idProduto = barras_produto.idProduto "
					+ "WHERE barras_produto.principal IS NOT FALSE AND produto.descricao LIKE ? "
					+ "ORDER BY descricao");
			ps.setString(1, nome);
			rs = ps.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setIdProduto(rs.getInt("idProduto"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setCodigo_barra(rs.getString("barras"));
				produto.setSetor(new Setor(rs.getInt("codSetor"), rs.getString("nome")));
				produto.setUnidadeVenda(rs.getString("unidadeVenda"));

				produto.setPreco(rs.getDouble("preco"));

				produto.setBloqueadoVenda(rs.getBoolean(8));
				String data_formatada = sdf.format(rs.getDate(9));
				produto.setDataCadastro(sdf.parse(data_formatada));
				produtos.add(produto);
			}
			return produtos;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao pesquisar produto por nome!", "Pesquisa produto por nome.",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			return null;
		}
	}

	// -----Listar produto por código interno----
	public ArrayList<Produto> listarProdutosCodigo(ArrayList<Produto> produtos, String codInterno) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
	
		
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT produto.idProduto, descricao, barras_produto.barras, produto.codSetor, "
					+ "setor.nome, unidadeVenda, preco, bloqueadoVenda, dataCadastro "
					+ "FROM produto INNER JOIN setor ON produto.codSetor = setor.codSetor "
					+ "INNER JOIN barras_produto ON produto.idProduto = barras_produto.idProduto "
					+ "WHERE barras_produto.principal IS NOT FALSE AND produto.idProduto LIKE ? "
					+ "ORDER BY descricao");
			ps.setString(1, codInterno);
			rs = ps.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setIdProduto(rs.getInt("idProduto"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setSetor(new Setor(rs.getInt("codSetor"), rs.getString("nome")));
				produto.setUnidadeVenda(rs.getString("unidadeVenda"));
				produto.setCodigo_barra(rs.getString("barras"));
				produto.setPreco(rs.getDouble("preco"));

				produto.setBloqueadoVenda(rs.getBoolean(8));
				String data_formatada = sdf.format(rs.getDate(9));
				produto.setDataCadastro(sdf.parse(data_formatada));
				produtos.add(produto);
			}
			return produtos;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao pesquisar produtos por código!", "Pesquisa por código interno",
					JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			return null;
		}
	}

	// -----Listar produto por código de barras----
	public ArrayList<Produto> listarProdutosBarras(ArrayList<Produto> produtos, String barras) {
		conn = DB.getConnection();
		PreparedStatement ps = null;

		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT produto.idProduto, descricao, barras_produto.barras, produto.codSetor, "
					+ "setor.nome, unidadeVenda, preco, bloqueadoVenda, dataCadastro "
					+ "FROM produto INNER JOIN setor ON produto.codSetor = setor.codSetor "
					+ "INNER JOIN barras_produto ON produto.idProduto = barras_produto.idProduto "
					+ "WHERE barras_produto.principal IS NOT FALSE AND barras_produto.barras LIKE ? "
					+ "ORDER BY descricao");
			ps.setString(1, barras);
			rs = ps.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setIdProduto(rs.getInt("idProduto"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setSetor(new Setor(rs.getInt("codSetor"), rs.getString("nome")));
				produto.setUnidadeVenda(rs.getString("unidadeVenda"));
				produto.setCodigo_barra(rs.getString("barras"));
				produto.setPreco(rs.getDouble("preco"));

				produto.setBloqueadoVenda(rs.getBoolean(8));
				String data_formatada = sdf.format(rs.getDate(9));
				produto.setDataCadastro(sdf.parse(data_formatada));
				produtos.add(produto);
			}

			return produtos;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao pesquisar produto por código de barras!",
					"Pesquisa por código de barras.", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			return null;
		}

	}

	// Pesquisar barras vinculados
	public ArrayList<Produto> listarProdutosBarrasVinculados(ArrayList<Produto> produtos, String barras) {
		conn = DB.getConnection();
		PreparedStatement ps = null;

		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT produto.idProduto, descricao, barras_produto.barras, produto.codSetor, "
					+ "setor.nome, unidadeVenda, preco, bloqueadoVenda, dataCadastro "
					+ "FROM produto INNER JOIN setor ON produto.codSetor = setor.codSetor "
					+ "LEFT JOIN barras_produto ON produto.idProduto = barras_produto.idProduto "
					+ "WHERE barras_produto.principal IS FALSE AND barras_produto.barras LIKE ? "
					+ "ORDER BY descricao");
			ps.setString(1, barras);
			rs = ps.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto();
				produto.setIdProduto(rs.getInt("idProduto"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setSetor(new Setor(rs.getInt("codSetor"), rs.getString("nome")));
				produto.setUnidadeVenda(rs.getString("unidadeVenda"));
				produto.setCodigo_barra(rs.getString("barras"));
				produto.setPreco(rs.getDouble("preco"));

				produto.setBloqueadoVenda(rs.getBoolean(8));
				String data_formatada = sdf.format(rs.getDate(9));
				produto.setDataCadastro(sdf.parse(data_formatada));
				produtos.add(produto);
			}

			return produtos;

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao pesquisar código de barras vinculado!" + e.getMessage(),
					"Erro!", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
			return null;
		}
	}

	// --Testar se o codigo de barras ja existe em outro item
	public boolean testaBarrasInclusao(String barras) {

		conn = DB.getConnection();
		PreparedStatement ps = null;

		ResultSet rs = null;

		if (barras.toString() != null) {
			try {
				ps = conn.prepareStatement("SELECT barras FROM barras_produto WHERE barras = ?");
				ps.setString(1, barras);
				rs = ps.executeQuery();

				if (rs.next()) {
					return false;
				} else {
					return true;
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
				ps = conn.prepareStatement("SELECT barras FROM barras_produto WHERE barras = ? AND idProduto = ?");
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
			} catch (Exception e) {
				e.getMessage();
				return false;
			}
		} else {
			return true;
		}

	}

}