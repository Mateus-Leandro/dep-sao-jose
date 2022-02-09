package bkp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class BkpBanco {

	private File pasta_conf = new File("C:/dep/conf/");
	private File arquivo_conf_bkp = new File(pasta_conf + "/bkp.properties");
	private File pasta_bkp = new File("C:/dep/backups/");
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private String data_backup;

	public Boolean realiza_backup(String pasta_destino) {
		monta_arquivo_conf_db();
		monta_bkp(pasta_destino);
		File arquivo_bkp = new File(pasta_destino + "\\bkp_" + data_backup + ".sql");
		try {
			// Aguardando alguns milésimos para que seja verificado o tamanho do arquivo.
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (arquivo_bkp.exists()) {
			if (arquivo_bkp.length() > 0) {
				return true;
			} else {
				while (!arquivo_bkp.delete())
					;
				return false;
			}
		}else {
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
				saida.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}

	public void monta_bkp(String pasta_destino) {
		try {

			Properties props = le_arquivo_conf_bkp();
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Properties le_arquivo_conf_bkp() {
		FileInputStream fs;
		Properties props = new Properties();
		monta_arquivo_conf_db();
		try {
			fs = new FileInputStream(arquivo_conf_bkp);
			props.load(fs);
			fs.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

	public Boolean faz_bkp(Properties prop) {
		prop = le_arquivo_conf_bkp();
		if (prop.getProperty("faz_bkp").equals("SIM")) {
			return true;
		} else {
			return false;
		}
	}
}
