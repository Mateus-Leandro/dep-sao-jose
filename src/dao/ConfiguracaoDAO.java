package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DB;
import entities.configuracoes.Configuracoes;
import entities.pessoa.Cliente;

public class ConfiguracaoDAO {
	private Connection conn;
	private	PreparedStatement ps;
	private ResultSet rs;

	public Boolean salva_configuracao(Configuracoes configuracao) {
		conn = DB.getConnection();

		try {
			conn.setAutoCommit(false);
			// Removendo configurações antigas.
			ps = conn.prepareStatement("DELETE FROM configuracoes");
			ps.execute();

			// salvando novas configurações.
			ps = conn.prepareStatement("INSERT INTO configuracoes "
					+ "(nome_empresa, responsavel, cnpj, inscricao_estadual, tel_fixo, celular, email, endereco, salva_parc_dif, alt_orc, gera_pdf, vincula_barras, idConsumidor_final) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			ps.setString(1, configuracao.getNome_empresa());
			ps.setString(2, configuracao.getResponsavel());
			ps.setString(3, configuracao.getCNPJ());
			ps.setString(4, configuracao.getInscricao_estadual());
			ps.setString(5, configuracao.getTel_fixo());
			ps.setString(6, configuracao.getCelular());
			ps.setString(7, configuracao.getEmail());
			ps.setString(8, configuracao.getEndereco());
			ps.setString(9, configuracao.getSalva_parc_dif());
			ps.setString(10, configuracao.getAltera_orc());
			ps.setString(11, configuracao.getGera_PDF());
			ps.setString(12, configuracao.getVincula_barras());
			ps.setInt(13, configuracao.getConsumidor_final().getId());

			ps.execute();
			conn.commit();
			return true;
		} catch (SQLException e) {
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

	public Boolean so_orcamentos(Boolean ativado) {
		conn = DB.getConnection();

		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement("UPDATE `banco_deposito`.`configuracoes` SET `so_orcamento` = ?");
			ps.setBoolean(1, ativado);
			ps.execute();

			conn.commit();
			return true;
		} catch (SQLException e) {
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

	public Configuracoes busca_configuracoes() {
		conn = DB.getConnection();
		Configuracoes conf = new Configuracoes();
		try {
			ps = conn.prepareStatement(
					"SELECT nome_empresa, responsavel, cnpj, inscricao_estadual, tel_fixo, configuracoes.celular, "
							+ "configuracoes.email, configuracoes.endereco, salva_parc_dif, alt_orc, gera_pdf, vincula_barras, so_orcamento, idConsumidor_final, clientes.nome, clientes.endereco, clientes.numero, clientes.bairro, clientes.cidade "
							+ "FROM configuracoes " + "LEFT JOIN clientes "
							+ "ON clientes.idCliente = configuracoes.idConsumidor_final");
			rs = ps.executeQuery();

			if (rs.next()) {
				conf.setNome_empresa(rs.getString("nome_empresa"));
				conf.setResponsavel(rs.getString("responsavel"));
				conf.setCNPJ(rs.getString("cnpj"));
				conf.setInscricao_estadual(rs.getString("inscricao_estadual"));
				conf.setTel_fixo(rs.getString("tel_fixo"));
				conf.setCelular(rs.getString("celular"));
				conf.setEmail(rs.getString("email"));
				conf.setEndereco(rs.getString("endereco"));
				conf.setSalva_parc_dif(rs.getString("salva_parc_dif"));
				conf.setAltera_orc(rs.getString("alt_orc"));
				conf.setGera_PDF(rs.getString("gera_pdf"));
				conf.setVincula_barras(rs.getString("vincula_barras"));
				conf.setSo_orcamento(rs.getBoolean("so_orcamento"));

				Cliente consumidor_final = new Cliente();
				consumidor_final.setId(rs.getInt("idConsumidor_final"));
				consumidor_final.setNome(rs.getString("clientes.nome"));
				consumidor_final.setEndereco(rs.getString("clientes.endereco"));
				consumidor_final.setNumero(rs.getString("clientes.numero"));
				consumidor_final.setBairro(rs.getString("clientes.bairro"));
				consumidor_final.setCidade(rs.getString("clientes.cidade"));
				conf.setConsumidor_final(consumidor_final);
			} else {
				return null;
			}

			return conf;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeStatement(ps);
			DB.closeResultSet(rs);
			DB.closeConnection(conn);
		}
	}
}
