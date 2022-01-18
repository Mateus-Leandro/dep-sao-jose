package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DB;
import entities.financeiro.Forma_pagamento;
import entities.financeiro.Parcela;
import entities.orcamentos.Orcamento;

public class FaturamentoDAO {

	private Connection conn;

	public ArrayList<Parcela> lista_parcelas(Orcamento orcamento) {
		conn = DB.getConnection();

		try {
			ResultSet rs = null;
			PreparedStatement ps = null;

			ps = conn.prepareStatement("SELECT parcelas.valor, parcelas.idFormaPagamento, "
					+ "forma_pagamento.descricao, parcelas.dataPagamento, parcelas.dataVencimento " + "FROM parcelas "
					+ "INNER JOIN forma_pagamento " + "ON parcelas.idFormaPagamento = forma_pagamento.idFormaPagamento "
					+ "WHERE idOrcamento = ? ORDER BY parcelas.dataVencimento");
			ps.setInt(1, orcamento.getId_orcamento());

			rs = ps.executeQuery();
			orcamento.getParcelas().clear();

			while (rs.next()) {
				Parcela parcela = new Parcela();
				parcela.setId_orcamento(orcamento.getId_orcamento());
				parcela.setData_pagamento(rs.getDate("dataPagamento"));
				parcela.setData_vencimento(rs.getDate("dataVencimento"));
				parcela.setValor_parcela(rs.getDouble("valor"));
				parcela.setForma_pagamento(
						new Forma_pagamento(rs.getInt("idFormaPagamento"), rs.getString("descricao")));
				orcamento.getParcelas().add(parcela);
			}

			return orcamento.getParcelas();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Boolean salvar_parcelas(Orcamento orcamento) {
		conn = DB.getConnection();

		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = null;

			// Removendo as parcelas existentes.
			ps = conn.prepareStatement("DELETE FROM parcelas WHERE idOrcamento = ?");
			ps.setInt(1, orcamento.getId_orcamento());
			ps.execute();

			// Incuindo as parcelas salvas.
			for (Parcela parc : orcamento.getParcelas()) {
				ps = conn.prepareStatement("INSERT INTO parcelas "
						+ "(`idOrcamento`,`valor`, `idFormaPagamento`, `dataPagamento`, `dataVencimento`) "
						+ "VALUES (?, ?, ?, ?, ? )");
				ps.setInt(1, orcamento.getId_orcamento());
				ps.setDouble(2, parc.getValor_parcela());
				ps.setInt(3, parc.getForma_pagamento().getCodigo());
				
				
				Date data_pagamento = null;
				if(parc.getData_pagamento() != null) {
				 data_pagamento = new java.sql.Date(parc.getData_pagamento().getTime());
				}
				
				Date data_vencimento = new java.sql.Date(parc.getData_vencimento().getTime());
				ps.setDate(4, data_pagamento);
				ps.setDate(5, data_vencimento);
				ps.execute();
			}

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return true;
	}

}
