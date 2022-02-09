package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import db.DB;
import entities.produto.Barras_Produto;
import entities.produto.Produto;

public class BarrasDAO {

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;

	public BarrasDAO() {
	}

	// Incluir novo barras
	public Produto novo_barras(String cod_item, String barras, Boolean principal) {
		Produto prod = testa_barras_vinculado(barras);
		if (prod == null) {
			conn = DB.getConnection();
			try {
				conn.setAutoCommit(false);
				ps = conn.prepareStatement(
						"INSERT INTO barras_produto (idProduto, principal, barras, dt_vinculacao) VALUES (?, ?, ?, ?)");
				ps.setString(1, cod_item);
				ps.setBoolean(2, principal);
				ps.setString(3, barras);
				ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
				ps.execute();
				conn.commit();
				return null;
			}
			catch (Exception e) {
				e.printStackTrace();
				return new Produto();
			} finally {
				DB.closeStatement(ps);
				DB.closeConnection(conn);
			}
		} else {
			return prod;
		}
	}

	// Incluir novo barras
	public boolean remove_barras(String barras) {
		conn = DB.getConnection();

		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("DELETE FROM barras_produto WHERE barras = ?");
			ps.setString(1, barras);
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

	// Verifica existencia do c�digo de barras
	public Produto testa_barras_vinculado(String barras) {
		conn = DB.getConnection();
		
		Produto produto_encontrado = new Produto();
		
		try {
			ps = conn.prepareStatement("SELECT barras_produto.idProduto, produto.descricao "
					+ "FROM barras_produto "
					+ "INNER JOIN produto on barras_produto.idProduto = produto.idProduto WHERE barras = ?");
			ps.setString(1, barras);
			rs = ps.executeQuery();
			if (rs.next()) {
				produto_encontrado.setIdProduto(rs.getInt("idProduto"));
				produto_encontrado.setDescricao(rs.getString("descricao"));
				return produto_encontrado;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Produto();
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
			DB.closeConnection(conn);
		}
	}

	public ArrayList<Barras_Produto> lista_barras(String cod_produto, ArrayList<Barras_Produto> lista) {
		conn = DB.getConnection();
		lista.clear();
		try {
			ps = conn.prepareStatement(
					"SELECT principal, barras, dt_vinculacao FROM barras_produto WHERE idProduto = ?");
			ps.setString(1, cod_produto);
			rs = ps.executeQuery();

			while (rs.next()) {
				Barras_Produto barra = new Barras_Produto(rs.getBoolean("principal"), rs.getString("barras"),
						rs.getDate("dt_vinculacao"));
				lista.add(barra);
			}

			return lista;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao alimentar lista de c�digos de barras!", "Erro",
					JOptionPane.WARNING_MESSAGE);
			return null;
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
			DB.closeConnection(conn);
		}
	}

	public boolean tornar_principal(String cod_produto, String barras) {
		conn = DB.getConnection();

		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("UPDATE barras_produto SET principal = 0 WHERE idProduto = ?");
			ps.setString(1, cod_produto);
			ps.execute();

			ps = conn.prepareStatement("UPDATE barras_produto SET principal = 1 WHERE barras = ?");
			ps.setString(1, barras);
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
		} finally {
			DB.closeStatement(ps);
			DB.closeConnection(conn);
		}
	}

}
