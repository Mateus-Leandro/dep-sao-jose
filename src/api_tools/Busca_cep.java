package api_tools;

import java.net.URL;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import entities.pessoa.Cliente;
import entities.pessoa.Pessoa;

public class Busca_cep {
	String cep = null;

	public Busca_cep(String cep) {
		this.cep = cep;
	}

	public Pessoa busca_endereco(Pessoa pessoa) {
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&fornato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			String tipo_logradouro = null;
			String logradouro = null;

			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					pessoa.setCidade(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					pessoa.setBairro(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipo_logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}

			}

			pessoa.setEndereco(tipo_logradouro + " " + logradouro);

			return pessoa;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
