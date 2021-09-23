package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Clientes {
	private Integer idCliente = null;
	private String nome;
	private String apelido;
	private String rg;
	private String cpf;
	private String cep;
	private String municipio;
	private enum tipoLogradouro {RUA, AVENIDA, VIELA, TRAVESSA, QUADRA, OUTRO};
	private String descricaoLogradouro;
	private String referencia;
	private Integer numero;
	private String bairro;
	private String email;
	private String celular;
	private String telefone;
	private Date dataCadastro;
	
	private List<Vendas> vendas =  new ArrayList<Vendas>();
}
