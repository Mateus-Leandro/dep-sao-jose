package bkp;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import entities.credenciaisDb.CredenciaisDb;
import tools.Props_tools;

public class BkpBanco {

	private File pasta_conf = new File("C:/dep/conf/");
	private File arquivo_conf_bkp = new File(pasta_conf + "/bkp.properties");
	private File pasta_bkp = new File("C:/dep/backups/");
	private File log_bkp = new File(pasta_bkp + "/log_bkp.properties");
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private String data_backup = sdf.format(new Date());
	private String arquivo_bkp = "/bkp_" + data_backup + ".sql";
	private Props_tools props_tools = new Props_tools();
	private Properties prop;
	private long inicio;
	private CredenciaisDb credDb;
	
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
				
				saida.write("NS=");
				saida.newLine();
				saida.write("faz_bkp=NÃO");
				saida.newLine();
				saida.write("mysql_path=C:\\" + brs + "Program Files\\" + brs + "MySQL\\" + brs + "MySQL Server 8.0\\"
						+ brs + "bin\\" + brs);
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
					credDb = new CredenciaisDb(Integer.parseInt(props.getProperty("NS")));
					String mysql_path = props.getProperty("mysql_path");
					String usuario = credDb.getUsuario();
					String senha = credDb.getSenha();
					String nome_banco = credDb.getNomeBanco();
					String comando = mysql_path + "mysqldump.exe";

					ProcessBuilder proc = new ProcessBuilder(comando, "--user=" + usuario, "--password=" + senha,
							nome_banco, "--result-file=" + pasta_destino + arquivo_bkp);
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
		File bkp = new File(pasta_bkp + arquivo_bkp);
		remove_bkp_antigo();

		if(bkp.exists()) {
			return false;
		}else {
			if(realiza_backup(pasta_bkp.getAbsolutePath())) {
				grava_log(System.currentTimeMillis() - inicio);
				return true;
			}else {
				return false;
			}
		}
	}
	
	
	public void remove_bkp_antigo() {
		Calendar hoje = Calendar.getInstance();
		Date data_bkp;
		File bkp_antigo;
		
		for(int n = 5; n < 30; n++) {
			hoje.setTime(new Date());
			hoje.add(Calendar.DAY_OF_MONTH, -n);
			data_bkp = hoje.getTime();
			
			bkp_antigo = new File(pasta_bkp.getAbsoluteFile() + "\\bkp_" + sdf.format(data_bkp) + ".sql");
			while(bkp_antigo.exists()) {
				bkp_antigo.delete();
			}
		}
	}
}
