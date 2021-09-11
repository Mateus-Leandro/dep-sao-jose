package db;

import java.io.FileInputStream;
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

	// Busca conexão com o banco de dados.
	public static Connection getConnection() {
		if (conn == null) {
			try {
				Properties props = carregarDados();
				String url = props.getProperty("dburl");
				conn = DriverManager.getConnection(url, props);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro ao conectar com o Banco de dados!\n" + e.getMessage());
			}
		}
		return conn;
	}

	// Lê os dados de conexão do banco através de um arquivo.
	private static Properties carregarDados() {
		try (FileInputStream fs = new FileInputStream("db.properties")) {
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbException(e.getMessage());
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
	public static void closeConnection(Connection conn){
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

}
