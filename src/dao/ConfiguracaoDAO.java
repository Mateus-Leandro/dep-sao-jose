package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.DB;
import entities.configuracoes.Configuracoes;

public class ConfiguracaoDAO {
	Connection conn;

	public Boolean salva_configuracao(Configuracoes configuracao) {

		conn = DB.getConnection();
		PreparedStatement ps = null;

		try {
			conn.setAutoCommit(false);
			// Removendo configurações antigas.
			ps = conn.prepareStatement("DELETE FROM configuracoes");
			ps.execute();

			// salvando novas configurações.
			ps = conn.prepareStatement("INSERT INTO configuracoes "
					+ "(nome_empresa, responsavel, cnpj, inscricao_estadual, tel_fixo, celular, email, endereco, salva_parc_dif, alt_orc, gera_pdf) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
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
		}

	}
	
	
	
	public Configuracoes busca_configuracoes() {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs;
		Configuracoes conf = new Configuracoes();
		try {
			ps = conn.prepareStatement("SELECT * FROM configuracoes");
			rs = ps.executeQuery();
			
			if(rs.next()) {
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
			}
			return conf;
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
