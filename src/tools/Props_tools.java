package tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Props_tools {
	
	public Properties le_arquivo(File caminho) {
		FileInputStream fs;
		Properties props = new Properties();
		try {
			if(caminho.exists()) {
				fs = new FileInputStream(caminho);
				props.load(fs);
				fs.close();
			}else {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return props;
	}
}
