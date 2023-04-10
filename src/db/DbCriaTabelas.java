package db;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;

import tools.Grava_log;

public class DbCriaTabelas {
	public String texto_lido;
	public File pasta = new File("C:/dep/DB/");
	public File arquivo = new File(pasta + "/gera_tabelas.txt");
	private Path caminho = Paths.get(arquivo.getAbsolutePath());
	private Connection conn;
	private PreparedStatement ps;
	private static Grava_log log_tools = new Grava_log();

	
	public void gera_tabelas() {

		if (!pasta.exists()) {
			pasta.mkdirs();
		} else {
			if (arquivo.exists()) {
				try {
					byte[] texto = Files.readAllBytes(caminho);
					texto_lido = new String(texto);

					String[] comandos = texto_lido.replaceAll("\\r\\n|\\n", "").split(";");

					conn = DB.getConnection();
					for (String i : comandos) {
						String inicio_comando = i.substring(0, 4);
						if (!inicio_comando.equals("DROP")) {
							ps = conn.prepareStatement(i);
							ps.executeUpdate();
						}
					}

					while (arquivo.exists()) {
						arquivo.delete();
					}

					DB.closeStatement(ps);
					DB.closeConnection(conn);

				} catch (Exception e) {
					log_tools.grava_log_error("DbCriaTabelas.java", "gera_tabelas" + " | [Erro ao alterar DB]\n", e);
					e.printStackTrace();
				}
			}
		}
	}
}
