package bkp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import tools.Props_tools;

public class BkpBanco {

	private File pasta_conf = new File("C:/dep/conf/");
	private File arquivo_conf_bkp = new File(pasta_conf + "/bkp.properties");
	private File pasta_bkp = new File("C:/dep/backups/");
	private File log_bkp = new File(pasta_bkp + "/log_bkp.properties");
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private String data_backup;
	private Props_tools props_tools = new Props_tools();
	private Properties prop;
	private long inicio;

	public Boolean realiza_backup(String pasta_destino) {
		inicio = System.currentTimeMillis();

		monta_arquivo_conf_db();
		monta_bkp(pasta_destino);
		File arquivo_bkp = new File(pasta_destino + "\\bkp_" + data_backup + ".sql");

		try {
			// Aguardando alguns milésimos para que seja verificado o tamanho do arquivo e
			// se ele existe.
			Thread.sleep(700);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (arquivo_bkp.exists()) {
			if (arquivo_bkp.length() > 0) {
				grava_log(System.currentTimeMillis() - inicio);
				return true;
			} else {
				while (!arquivo_bkp.delete());
				return false;
			}
		} else {
			return false;
		}
	}

	public void monta_arquivo_conf_db() {
		if (!pasta_conf.exists()) {
			pasta_conf.mkdirs();
		}

		if (!pasta_bkp.exists()) {
			pasta_bkp.mkdirs();
		}

		if (!arquivo_conf_bkp.exists()) {
			try {
				BufferedWriter saida = new BufferedWriter(new FileWriter(arquivo_conf_bkp));
				String brs = "\\";

				saida.write("faz_bkp=NÃO");
				saida.newLine();
				saida.write("mysql_path=C:\\" + brs + "Program Files\\" + brs + "MySQL\\" + brs + "MySQL Server 8.0\\"
						+ brs + "bin\\" + brs);
				saida.newLine();
				saida.write("nome_banco=banco_deposito");
				saida.newLine();
				saida.write("usuario_banco=");
				saida.newLine();
				saida.write("senha_banco=");
				saida.newLine();
				saida.write("bkp_diario=NÃO");
				saida.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void monta_bkp(String pasta_destino) {
		try {
			Properties props = props_tools.le_arquivo(arquivo_conf_bkp);
			if(props != null) {
				String faz_bkp = props.getProperty("faz_bkp");
				if (faz_bkp.toUpperCase().equals("SIM")) {
					String mysql_path = props.getProperty("mysql_path");
					String usuario = props.getProperty("usuario_banco");
					String senha = props.getProperty("senha_banco");
					String nome_banco = props.getProperty("nome_banco");
					String comando = mysql_path + "mysqldump.exe";
					
					data_backup = sdf.format(new Date());
					
					ProcessBuilder proc = new ProcessBuilder(comando, "--user=" + usuario, "--password=" + senha,
							nome_banco, "--result-file=" + pasta_destino + "\\bkp_" + data_backup + ".sql");
					proc.start();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Boolean faz_bkp() {
		return props_tools.getProperty(prop,"faz_bkp", arquivo_conf_bkp);
	}
	
	public Boolean faz_bkp_diario() {
		return props_tools.getProperty(prop,"bkp_diario", arquivo_conf_bkp);
	}
	
	public void grava_log(long tempo_gasto) {
		try {
			BufferedWriter log = new BufferedWriter(new FileWriter(log_bkp));

			Date hoje = new Date();
			log.write("data_bkp=" + sdf.format(hoje).replaceAll("[-]", "/"));
			log.newLine();
			log.write("tempo=" + tempo_gasto);
			log.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Boolean bkp_diario() {
		inicio = System.currentTimeMillis();
		if(realiza_backup(pasta_bkp.getAbsolutePath())) {
			grava_log(System.currentTimeMillis() - inicio);
			return true;
		}else {
			return false;
		}
	}
}
