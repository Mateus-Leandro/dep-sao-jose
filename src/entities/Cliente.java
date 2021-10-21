package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cliente {
	private Integer idCliente = null;
	private String nome;
	private String apelido;
	private boolean pessoa_juridica;
	private String cpf_cnpj;
	private String inscricao_estadual;
	private String cep;
	private String cidade;
	private String Endereco;
	private String referencia;
	private String numero;
	private String bairro;
	private String email;
	private String celular;
	private String telefone;
	private Date dataCadastro;
	
	private List<Vendas> vendas =  new ArrayList<Vendas>();

	
	
	
	public Cliente(Integer idCliente, String nome, String apelido, boolean pessoa_juridica, String cpf_cnpj,
			String inscricao_estadual, String cep, String cidade, String endereco,
			String referencia, String numero, String bairro, String email, String celular, Date dataCadastro, List<Vendas> vendas) {
		this.idCliente = idCliente;
		this.nome = nome;
		this.apelido = apelido;
		this.pessoa_juridica = pessoa_juridica;
		this.cpf_cnpj = cpf_cnpj;
		this.inscricao_estadual = inscricao_estadual;
		this.cep = cep;
		this.cidade = cidade;
		Endereco = endereco;
		this.referencia = referencia;
		this.numero = numero;
		this.bairro = bairro;
		this.email = email;
		this.celular = celular;
		this.telefone = telefone;
		this.dataCadastro = dataCadastro;
		this.vendas = vendas;
	}
	
	public Cliente() {
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public boolean isPessoa_juridica() {
		return pessoa_juridica;
	}

	public void setPessoa_juridica(boolean pessoa_juridica) {
		this.pessoa_juridica = pessoa_juridica;
	}

	public String getCpf_cnpj() {
		return cpf_cnpj;
	}

	public void setCpf_cnpj(String cpf_cnpj) {
		this.cpf_cnpj = cpf_cnpj;
	}

	public String getInscricao_estadual() {
		return inscricao_estadual;
	}

	public void setInscricao_estadual(String inscricao_estadual) {
		this.inscricao_estadual = inscricao_estadual;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	
	
	public String getEndereco() {
		return Endereco;
	}

	public void setEndereco(String Endereco) {
		this.Endereco = Endereco;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public List<Vendas> getVendas() {
		return vendas;
	}

	public void setVendas(List<Vendas> vendas) {
		this.vendas = vendas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCliente == null) ? 0 : idCliente.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (idCliente == null) {
			if (other.idCliente != null)
				return false;
		} else if (!idCliente.equals(other.idCliente))
			return false;
		return true;
	}
	
	
}
