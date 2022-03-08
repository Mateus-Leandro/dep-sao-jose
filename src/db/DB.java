package db;

import java.awt.HeadlessException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.commons.dbcp.BasicDataSource;


public class DB {

	private static Connection conn = null;
	private static Boolean reconectar;
	private static String url;
	private static Properties props;
	private static BasicDataSource dataSource;
	
	// Busca conex�o com o banco de dados.
	public static Connection getConnection() {

		File arquivo_db = new File("C:/dep/conf/db.properties");
		if (!arquivo_db.exists()) {

			File pasta_db = new File("C:/dep/conf/");
			if (!pasta_db.exists()) {
				pasta_db.mkdirs();
			}

			monta_arquivo_conexao(arquivo_db.getAbsolutePath());
			return null;
		} else {
			try {
				if (conn == null || conn.isClosed()) {
					try {
						props = carregarDados();
						url = props.getProperty("dburl");

						// Busca conex�o do pool
						conn = getDataSource().getConnection();
					} catch (SQLException e) {
						int opcao = JOptionPane.showConfirmDialog(null,
								"Erro na conex�o com o banco dados.\nDeseja tentar reconectar?",
								"Conex�o com o banco de dados.", JOptionPane.YES_OPTION,
								JOptionPane.WARNING_MESSAGE);

						reconectar = opcao == JOptionPane.YES_OPTION;
						e.printStackTrace();

						if (reconectar) {
							getConnection();
						} else {
							System.exit(0);
						}
					}
				}
			} catch (HeadlessException | SQLException e) {
				e.printStackTrace();
			}
			return conn;
		}
	}

	// L� os dados de conex�o do banco atrav�s de um arquivo.
	private static Properties carregarDados() {
		try (FileInputStream fs = new FileInputStream("C:/dep/conf/db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			return null;
		}
	}

	public static void closeStatement(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	public static void closeResultSet(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbException(e.getMessage());
			}
		}
	}

	// Fecha conex�o com o banco.
	public static void closeConnection(Connection conn) {
		try {
			if (conn != null || !conn.isClosed()) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new DbException(e.getMessage());
				}
			}
		} catch (SQLException e) {
			e.getMessage();
		}
	}

	public static void monta_arquivo_conexao(String path) {
		try {

			BufferedWriter saida = new BufferedWriter(new FileWriter(path));
			saida.write("useTimezone=true");
			saida.newLine();
			saida.write("serverTimezone=UTC");
			saida.newLine();
			saida.write("user=USUARIO_DO_BANCO");
			saida.newLine();
			saida.write("password=SENHA_DO_USUARIO");
			saida.newLine();
			saida.write("dburl=jdbc:mysql://IP_DO_BANCO:3306/banco_deposito");
			saida.newLine();
			saida.write("useSSL=false");
			saida.newLine();
			saida.write("allowPublicKeyRetrieval=true");
			saida.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		JOptionPane.showMessageDialog(null, "O arquivo C:/dep/conf/db.properties"
				+ ", necess�rio para a conex�o com o banco de dados, n�o foi encontrado ou est� com as configura��es inv�lidas."
				+ "\nGentileza verificar as informa��es presentes dentro do arquivo.",
				"Arquivo de conex�o inv�lido ou n�o encontrado.", JOptionPane.WARNING_MESSAGE);

		System.exit(0);
	}
	
	
	// pool para controle de conex�es com o banco de dados.
	public static BasicDataSource getDataSource () {
		if(dataSource == null) {
			BasicDataSource ds = new BasicDataSource();
			ds.setUrl(url);
			ds.setUsername(props.getProperty("user"));
			ds.setPassword(props.getProperty("password"));
			
			ds.setMinIdle(5);
			ds.setMaxIdle(10);
			ds.setMaxOpenPreparedStatements(50);
			dataSource = ds;
		}
		
		return dataSource;
	}
	
}