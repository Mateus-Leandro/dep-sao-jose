package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import db.DB;
import entities.cliente.Cliente;
import entities.financeiro.Resumo_financeiro;

public class Resumo_financeiroDAO {
	private Connection conn;

	public Resumo_financeiro carregar_resumo_financeiro(Cliente cliente, Resumo_financeiro resumo) {
		conn = DB.getConnection();
		ResultSet rs = null;
		PreparedStatement ps;

		int id_cliente = cliente.getIdCliente();
		try {

			// Calculando total em aberto
			Double parcelas_pagas = 0.00;
			ps = conn.prepareStatement("SELECT SUM(parcelas.valor) as parcelas_pagas " + "FROM orcamento "
					+ "INNER JOIN parcelas ON orcamento.idOrcamento = parcelas.idOrcamento "
					+ "WHERE orcamento.idCliente = ? " + "AND parcelas.dataPagamento IS NOT NULL");
			ps.setInt(1, id_cliente);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				parcelas_pagas = rs.getDouble("parcelas_pagas");
			}
			resumo.setValor_aberto(resumo.getTotal_comprado() - parcelas_pagas);

			
			// Calculando valor total em orçamentos
			ps = conn.prepareStatement(
					"SELECT SUM(valorTotal) AS valor_comprado FROM orcamento WHERE idCliente = ? AND faturado = true");
			ps.setInt(1, id_cliente);
			rs = ps.executeQuery();

			if (rs.next()) {
				resumo.setTotal_comprado(rs.getDouble("valor_comprado"));
			}


			// Buscando valor da maior compra
			Double maior_compra = 0.00;
			ps = conn.prepareStatement(
					"SELECT MAX(valorTotal) AS maior_compra FROM orcamento WHERE idCliente = ? AND faturado = true");
			ps.setInt(1, id_cliente);
			rs = ps.executeQuery();

			if (rs.next()) {
				maior_compra = rs.getDouble("maior_compra");
			}
			resumo.setMaior_compra(maior_compra);

			// Buscando data da primeira compra
			Date primeira_compra = null;
			ps = conn.prepareStatement(
					"SELECT MIN(dataInclusao) AS primeira_compra FROM orcamento WHERE idCliente = ? AND faturado = true");
			ps.setInt(1, id_cliente);
			rs = ps.executeQuery();

			if (rs.next()) {
				primeira_compra = rs.getDate("primeira_compra");
			}
			resumo.setPrimeira_compra(primeira_compra);

			// Buscando data da última compra
			Date ultima_compra = null;
			ps = conn.prepareStatement(
					"SELECT MAX(dataInclusao) AS ultima_compra FROM orcamento WHERE idCliente = ? AND faturado = true");
			ps.setInt(1, id_cliente);
			rs = ps.executeQuery();

			if (rs.next()) {
				ultima_compra = rs.getDate("ultima_compra");
			}
			resumo.setUltima_compra(ultima_compra);

			return resumo;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
