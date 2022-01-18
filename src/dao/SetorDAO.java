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
import entities.produto.Setor;

public class SetorDAO {
	private Connection conn;

	public Setor inserirSetor(Setor setor) {

		conn = DB.getConnection();
		PreparedStatement ps = null;

		try {
			ResultSet rs = null;
			ps = conn.prepareStatement("INSERT INTO setor " + "(nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, setor.getNome());

			ps.execute();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				setor.setCodSetor(rs.getInt(1));
			}
			
			return setor;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao gravar setor!" + e.getMessage(),"Erro!",JOptionPane.WARNING_MESSAGE);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e.getMessage();
			}
			setor = null;
			return setor;
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
		} catch (SQLIntegrityConstraintViolationException setorComProduto) {
			try {
				JOptionPane.showMessageDialog(null, "Existem produto(s) que utilizam o setor selecionado.",
						"Impossível excluir setor!", JOptionPane.WARNING_MESSAGE);
				conn.rollback();
				return false;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao excluir setor!", "Erro!", JOptionPane.WARNING_MESSAGE);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
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
		} catch (Exception e) {
			e.getMessage();
		}
		return false;
	}
}
