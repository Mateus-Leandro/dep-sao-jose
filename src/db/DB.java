package db;

import java.awt.HeadlessException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.swing.JOptionPane;

import entities.credenciaisDb.CredenciaisDb;
import tools.Grava_log;

public class DB {

	private static Connection conn = null;
	private static Boolean reconectar;
	private static Properties props;
	private static Grava_log log_tools = new Grava_log();

	private static String usuario;
	private static String senha;
	private static Integer NS; // Numero de série do cliente.
	private static String ipBanco;
	private static String nomeBanco;
	private static String url;

	private static CredenciaisDb credDb;

	// Busca conexão com o banco de dados.
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
						verifica_serie();
						url = "jdbc:mysql://" + ipBanco + ":3306" + "/" + credDb.getNomeBanco()
								+ "?allowPublicKeyRetrieval=true&useSSL=false&useTimezone=true&serverTimezone=UTC";

						conn = DriverManager.getConnection(url, credDb.getUsuario(), credDb.getSenha());

					} catch (SQLException e) {
						e.printStackTrace();
						log_tools.grava_log_error("DB.java", "getConnection" + " | [Erro de conexão]", e);

						int opcao = JOptionPane.showConfirmDialog(null,
								"Erro na conexão com o banco dados.\nDeseja tentar reconectar?",
								"Conexão com o banco de dados.", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

						reconectar = opcao == JOptionPane.YES_OPTION;

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

	// Lê os dados de conexão do banco através de um arquivo.
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

	// Fecha conexão com o banco.
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
			saida.write("NS=");
			saida.newLine();
			saida.write("ipBd=");
			saida.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}

		JOptionPane.showMessageDialog(null, "O arquivo C:/dep/conf/db.properties"
				+ ", necessário para a conexão com o banco de dados, não foi encontrado ou está com as configurações inválidas."
				+ "\nGentileza verificar as informações presentes dentro do arquivo.",
				"Arquivo de conexão inválido ou não encontrado.", JOptionPane.WARNING_MESSAGE);

		System.exit(0);
	}

	// Pega dados de acesso pelo número de serie do cliente.
	public static void verifica_serie() {
		NS = Integer.parseInt(props.getProperty("NS"));
		ipBanco = props.getProperty("ipBd");

		credDb = new CredenciaisDb(NS);
	}
	
	
	public Integer getSerie() {
		return NS;
	}
}