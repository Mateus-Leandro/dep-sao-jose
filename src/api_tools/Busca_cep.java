package api_tools;

import java.net.URL;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import entities.pessoa.Cliente;

public class Busca_cep {
	String cep = null;

	public Busca_cep(String cep) {
		this.cep = cep;
	}

	public Cliente busca_endereco(Cliente cliente) {
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
					cliente.setCidade(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					cliente.setBairro(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipo_logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}

			}

			cliente.setEndereco(tipo_logradouro + " " + logradouro);

			return cliente;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
