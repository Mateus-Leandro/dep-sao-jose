package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import db.DB;
import entities.Barras_Produto;

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
				JOptionPane.showMessageDialog(null, "Código vinculado corretamente.", "Vinculação de código de barras",
						JOptionPane.NO_OPTION);
				return true;
			}

			catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Erro ao vincular novo código de barras!",
						"Vinculação de código de barras", JOptionPane.WARNING_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "Código de barras removido.",
					"Exclusão de código de barras vinculado.", JOptionPane.WARNING_MESSAGE);
			return true;
		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, "Erro ao remover código de barras!",
					"Exclusão de código de barras vinculado.", JOptionPane.WARNING_MESSAGE);
			return false;

		}
	}

	// Verifica existencia do código de barras
	public Boolean testa_barras_vinculado(String barras) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM barras_produto WHERE barras = ?");
			ps.setString(1, barras);
			rs = ps.executeQuery();
			if (rs.next()) {
				JOptionPane.showMessageDialog(null, "Código de barras ja utilizado em outro produto!",
						"Vinculação de código de barras", JOptionPane.WARNING_MESSAGE);
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro ao vincular novo código de barras!",
					"Vinculação de código de barras", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}

	public ArrayList<Barras_Produto> lista_barras(String cod_produto, ArrayList<Barras_Produto> lista) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

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
			JOptionPane.showMessageDialog(null, "Erro ao alimentar lista de códigos de barras!", "Erro",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}

}
