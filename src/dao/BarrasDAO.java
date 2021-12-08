package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import db.DB;
import entities.produto.Barras_Produto;

public class BarrasDAO {

	private Connection conn;

	public BarrasDAO() {
	}

	public BarrasDAO(Connection conn) {
		this.conn = conn;
	}

	// Incluir novo barras
	public boolean novo_barras(String cod_item, String barras) {

		if (testa_barras_vinculado(barras)) {
			conn = DB.getConnection();
			PreparedStatement ps = null;
			try {

				conn.setAutoCommit(false);
				ps = conn.prepareStatement(
						"INSERT INTO barras_produto (idProduto, principal, barras, dt_vinculacao) VALUES (?, ?, ?, ?)");
				ps.setString(1, cod_item);
				ps.setBoolean(2, false);
				ps.setString(3, barras);
				ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
				ps.execute();
				conn.commit();
				JOptionPane.showMessageDialog(null, "C�digo vinculado corretamente.", "Vincula��o de c�digo de barras",
						JOptionPane.NO_OPTION);
				return true;
			}

			catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Erro ao vincular novo c�digo de barras!",
						"Vincula��o de c�digo de barras", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		} else {
			return false;
		}

	}

	// Incluir novo barras
	public boolean remove_barras(String barras) {
		conn = DB.getConnection();
		PreparedStatement ps = null;

		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("DELETE FROM barras_produto WHERE barras = ?");
			ps.setString(1, barras);
			ps.execute();
			conn.commit();
			JOptionPane.showMessageDialog(null, "C�digo de barras removido.", "Exclus�o de c�digo de barras vinculado.",
					JOptionPane.WARNING_MESSAGE);
			return true;
		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro ao remover c�digo de barras!",
					"Exclus�o de c�digo de barras vinculado.", JOptionPane.WARNING_MESSAGE);
			return false;

		}
	}

	// Verifica existencia do c�digo de barras
	public Boolean testa_barras_vinculado(String barras) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM barras_produto WHERE barras = ?");
			ps.setString(1, barras);
			rs = ps.executeQuery();
			if (rs.next()) {
				JOptionPane.showMessageDialog(null, "C�digo de barras ja utilizado em outro produto!",
						"Vincula��o de c�digo de barras", JOptionPane.WARNING_MESSAGE);
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao vincular novo c�digo de barras!",
					"Vincula��o de c�digo de barras", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}

	public ArrayList<Barras_Produto> lista_barras(String cod_produto, ArrayList<Barras_Produto> lista) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
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
		}
	}

	public boolean tornar_principal(String cod_produto, String barras) {

		conn = DB.getConnection();
		PreparedStatement ps = null;

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
		}
	}

}
