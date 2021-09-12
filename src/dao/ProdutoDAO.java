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
			ps.setString(2, produto.getCodigo_barra().trim());
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
			JOptionPane.showMessageDialog(null, "Erro ao inserir novo produto!");
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
	
	
	public boolean alterarProduto (Produto produto) {
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
		}catch (Exception e) {
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
	
	
	
	//--------Deletar--------------
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
	public ResultSet listarTodosProdutos() throws SQLException {
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


}
