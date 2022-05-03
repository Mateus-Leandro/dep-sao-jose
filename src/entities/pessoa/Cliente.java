package entities.pessoa;

import java.util.Date;

public class Cliente extends Pessoa {

	public Cliente() {
		super();
	}

	public Cliente(Integer id, String nome, String apelido, boolean pessoa_juridica, String cpf_cnpj,
			String inscricao_estadual, String cep, String cidade, String endereco, String referencia, String numero,
			String bairro, String email, String celular, String telefone, Boolean bloqueado, Date dataCadastro) {
		super(id, nome, apelido, pessoa_juridica, cpf_cnpj, inscricao_estadual, cep, cidade, endereco, referencia,
				numero, bairro, email, celular, telefone, bloqueado, dataCadastro);
	}

	@Override
	public String toString() {
		return getNome();
	}
	
	

}
