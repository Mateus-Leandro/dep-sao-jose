package tools;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log_tools {

	
	public void grava_log_error(String sourceClass, String sourceMethod, Exception msg){
        try {

        	File pasta_logs = new File("C:/dep/logs/");
        	if(!pasta_logs.exists()) {
        		pasta_logs.mkdirs();
        	}
        	
        	Logger log = Logger.getLogger("My Log");

            Date dt = new Date();
            DateFormat brasil = new SimpleDateFormat ("ddMMyyyy");
            String data = brasil.format(dt);
            
            // Cria um arquivo log no formato arquivoddmmyyyy
            FileHandler fh = new FileHandler(pasta_logs + "/error_" +data+".log", true);
            SimpleFormatter formatter = new SimpleFormatter(); 
            fh.setFormatter(formatter);

            // Escreve a mensagem de erro no arquivo
            log.addHandler(fh);
            log.logp(Level.WARNING, sourceClass, sourceMethod, "\n" + msg.toString() + "\n\n=============================\n" );
            fh.close();

        } catch (IOException | SecurityException ex) {
            System.out.println(ex.getMessage());
        } 
    }
}
