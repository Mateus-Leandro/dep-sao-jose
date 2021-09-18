package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import db.DB;
import entidades.Setor;

public class SetorDAO {
	private Connection conn;

	public Integer inserirSetor(Setor setor) {

		conn = DB.getConnection();
		PreparedStatement ps = null;
		
		try {
			ResultSet rs = null;
			ps = conn.prepareStatement("INSERT INTO setor " + "(nome) VALUES (?)",Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, setor.getNome());

			ps.execute();
			rs = ps.getGeneratedKeys();
		
			if(rs.next()) {
				setor.setCodSetor(rs.getInt(1));
			}
			
			return setor.getCodSetor();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao gravar novo setor!");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e.getMessage();
			}
			return null;
		}
	}

	public boolean alterarSetor(Setor setor) {
		conn = DB.getConnection();
		PreparedStatement ps = null;

		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("UPDATE setor SET nome = ? WHERE codSetor = ?");
			ps.setString(1, setor.getNome());
			ps.setInt(2, setor.getCodSetor());

			ps.executeUpdate();
			conn.commit();
			return true;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao alterar setor!");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e.getMessage();
			}
			return false;
		} finally {
			DB.closeStatement(ps);
			DB.closeConnection(conn);
		}
	}
	public boolean excluirSetor(Integer codigo_setor) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("DELETE FROM setor WHERE codSetor = ?");
			ps.setInt(1, codigo_setor);
			
			ps.execute();
			conn.commit();
			return true;
		} catch (Exception e) {
			try {
				conn.rollback();
				return false;
			} catch (SQLException e1) {	
			}
			return false;
		}
	}
	
	

	public ArrayList<Setor> listarSetores(ArrayList<Setor> lista_setores) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			
			ps = conn.prepareStatement("SELECT * FROM setor");
			rs = ps.executeQuery();
			while (rs.next()) {
				Setor setor = new Setor();
				setor.setCodSetor(rs.getInt("codSetor"));
				setor.setNome(rs.getString("nome"));
				lista_setores.add(setor);
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return lista_setores;
	}
	
	
	public boolean testaExisteCodigo(String string) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("SELECT * from setor WHERE codSetor = ?");
			ps.setString(1, string);
			rs = ps.executeQuery();
			return rs.next();
		}catch (Exception e) {
			e.getMessage();
		}
		return false;
	}
}
