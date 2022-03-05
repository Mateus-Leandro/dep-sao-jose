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

public class DB {

	private static Connection conn = null;
	private boolean existe_pasta;

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
						Properties props = carregarDados();
						String url = props.getProperty("dburl");

						conn = DriverManager.getConnection(url, props);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null,
								"Não foi possível conectar com o banco de dados.",
								"Conexão com o banco de dados.", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
						return null;
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
			JOptionPane.showMessageDialog(null,
					"Impossível conectar com o banco de dados.\nVerifique as informações de conexão presentes no arquivo "
							+ "C:/dep/conf/db.properties",
					"Conexão com o banco de dados.", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
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
				+ ", necessário para a conexão com o banco de dados, não foi encontrado ou está com as configurações inválidas."
				+ "\nGentileza verificar as informações presentes dentro do arquivo.",
				"Arquivo de conexão inválido ou não encontrado.", JOptionPane.WARNING_MESSAGE);

		System.exit(0);
	}
}