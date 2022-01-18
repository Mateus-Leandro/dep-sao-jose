package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import db.DB;
import entities.financeiro.Forma_pagamento;

public class FormaPagamentoDAO {

	private Connection conn;

	public Forma_pagamento salvar_forma(Forma_pagamento forma) {

		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("INSERT INTO `banco_deposito`.`forma_pagamento` " + "(`descricao`) VALUES (?)",
					PreparedStatement.RETURN_GENERATED_KEYS);

			ps.setString(1, forma.getDescricao());
			ps.execute();

			conn.commit();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				forma.setCodigo(rs.getInt(1));
			} else {
				forma = null;
			}

			return forma;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return null;
		}
	}

	public Boolean alterar_forma(Forma_pagamento forma) {
		conn = DB.getConnection();
		PreparedStatement ps;

		try {
			ps = conn.prepareStatement("UPDATE forma_pagamento SET descricao = ? WHERE idFormaPagamento = ?");
			ps.setString(1, forma.getDescricao());
			ps.setInt(2, forma.getCodigo());

			ps.execute();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public Boolean excluir_forma_pagamento(Forma_pagamento forma_excluida) {
		conn = DB.getConnection();
		PreparedStatement ps;

		try {
			conn.setAutoCommit(false);

			ps = conn.prepareStatement("DELETE FROM forma_pagamento WHERE idFormaPagamento = ?");
			ps.setInt(1, forma_excluida.getCodigo());
			ps.execute();
			conn.commit();

			return true;
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Existem parcelas que utilizam a forma de pagamento selecionada.",
					"Impossível excluir forma de pagamento!", JOptionPane.WARNING_MESSAGE);
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
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

	public ArrayList<Forma_pagamento> listar_formas_pagamento(ArrayList<Forma_pagamento> formas_pagamento) {

		conn = DB.getConnection();
		PreparedStatement ps;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement("SELECT * FROM `banco_deposito`.`forma_pagamento`");
			rs = ps.executeQuery();

			while (rs.next()) {
				Forma_pagamento forma = new Forma_pagamento(rs.getInt("idFormaPagamento"), rs.getString("descricao"));
				formas_pagamento.add(forma);
			}

			return formas_pagamento;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
