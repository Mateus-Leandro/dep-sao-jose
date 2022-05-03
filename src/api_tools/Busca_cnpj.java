package api_tools;

import java.io.IOException;

import org.json.JSONObject;

import entities.pessoa.Cliente;

public class Busca_cnpj {

	// Api para buscar dados do cnpj
	public Cliente buscar_cnpj(String cnpj) {
		JSONObject object = null;

		String bairro = null;
		String nome_cliente = null;
		String apelido = null;
		String cep = null;
		String cidade = null;
		String endereco = null;
		String numero = null;
		String referencia = null;
		String telFixo = null;
		String email = null;

		try {
			object = chama_api_cnpj(object, cnpj);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (object.get("status").equals("OK")) {
			nome_cliente = (String) object.get("nome");
			apelido = (String) object.get("fantasia");
			cep = (String) object.get("cep").toString().replaceAll("[-.]", "");
			cidade = (String) object.get("municipio");
			endereco = (String) object.get("logradouro");
			numero = (String) object.get("numero");
			referencia = (String) object.get("complemento");
			bairro = (String) object.get("bairro");

			if (object.get("telefone").toString().trim().length() > 0) {
				telFixo = (String) object.get("telefone").toString().trim().replaceAll("[()-]", "")
						.replaceAll("\\s+", "").substring(0, 10);
			}

			email = (String) object.getString("email");
			Cliente cliente = new Cliente(null, nome_cliente, apelido, true, cnpj, telFixo, cep, cidade, endereco,
					referencia, numero, bairro, email, null, telFixo, false, null);

			return cliente;

		} else {
			return null;
		}

	}

	
	public JSONObject chama_api_cnpj(JSONObject objetoJson, String cnpj) throws IOException {
		@SuppressWarnings("unchecked")
		String url = "https://www.receitaws.com.br/v1/cnpj/" + cnpj;

		json_to_string site_api = new json_to_string();
		String retornoJson = site_api.chamaUrl(url);

		objetoJson = new JSONObject(retornoJson);

		return objetoJson;
	}

}
