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
			ResultSet rs = null;

			try {
				ps = conn
						.prepareStatement("INSERT INTO barras_produto (idProduto,barras,dt_vinculacao) VALUES (?,?,?)");
				ps.setString(1, cod_item);
				ps.setString(2, barras);
				ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
				ps.execute();
				return true;
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao vincular novo código de barras!",
						"Vinculação de código de barras", JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}

		return true;
	}

	// Verifica existencia do código de barras
	public boolean testa_barras_vinculado(String barras) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM barras_produto WHERE barras = ?");
			rs = ps.executeQuery();
			if (rs.next()) {
				JOptionPane.showMessageDialog(null, "Código de barras ja utilizado em outro produto!",
						"Vinculação de código de barras", JOptionPane.WARNING_MESSAGE);
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
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
			ps = conn.prepareStatement("SELECT barras, dt_vinculacao FROM barras_produto WHERE idProduto = ?");
			ps.setString(1, cod_produto);
			rs = ps.executeQuery();

			while (rs.next()) {
				Barras_Produto barra = new Barras_Produto(rs.getString(1), rs.getDate(2));
				lista.add(barra);
			}
			
			return lista;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao alimentar lista de códigos de barras!", "Erro",
					JOptionPane.WARNING_MESSAGE);
			return null;
		}
	}
	
	
	
	//Vincular novo codigo de barras
	public boolean vinculaNovoCodigo(Integer cod, String barras) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("INSERT INTO barras_produtos(idProduto,barras) VALUES (?, ?)");
			ps.execute();
			conn.commit();
			return true;
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao vincular código de barras.","Erro na vinculação!",JOptionPane.WARNING_MESSAGE);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}
}
