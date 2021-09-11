package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import db.DB;
import entidades.Setor;

public class SetorDao {

	public void inserirSetor(Setor setor) throws SQLException {

		Connection conn = DB.getConnection();
		PreparedStatement ps = null;

		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("INSERT INTO setor " + "(codSetor, nome) VALUES (?, ?)");
			ps.setInt(1, setor.getCodSetor());
			ps.setString(2, setor.getNome());

			ps.execute();
			conn.commit();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Erro ao gravar novo setor!");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e.getMessage();
			}
		}
		finally {
			DB.closeStatement(ps);
			DB.closeConnection(conn);
		}
	}
	
	
	public List<Setor> listarSetores() {
		Connection con = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		List<Setor> setores = new ArrayList<Setor>();
		
		try {
			ps = con.prepareStatement("SELECT * FROM setor");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Setor setor = new Setor();
				setor.setCodSetor(rs.getInt(1));
				setor.setNome(rs.getString(2));
				
				setores.add(setor);
			}
		} catch (SQLException e) {
			e.getMessage();
		}
		return setores;
	}
}
