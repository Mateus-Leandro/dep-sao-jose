package entities.configuracoes;

import entities.pessoa.Cliente;

public class Configuracoes {
	private String nome_empresa;
	private String responsavel;
	private String CNPJ;
	private String tel_fixo;
	private String celular;
	private String email;
	private String endereco;
	private String salva_parc_dif;
	private String altera_orc;
	private String gera_PDF;
	private String vincula_barras;
	private Cliente consumidor_final;
	private Boolean so_orcamento;

	public Configuracoes() {
	}

	public Configuracoes(String nome_empresa, String responsavel, String CNPJ, String tel_fixo, String celular,
			String email, String endereco, String salva_parc_dif, String altera_orc, String gera_PDF,
			String vincula_barras, Cliente consumidor_final) {
		this.nome_empresa = nome_empresa;
		this.responsavel = responsavel;
		this.CNPJ = CNPJ;
		this.tel_fixo = tel_fixo;
		this.celular = celular;
		this.email = email;
		this.endereco = endereco;
		this.salva_parc_dif = salva_parc_dif;
		this.altera_orc = altera_orc;
		this.gera_PDF = gera_PDF;
		this.vincula_barras = vincula_barras;
		this.consumidor_final = consumidor_final;
	}

	public String getNome_empresa() {
		return nome_empresa;
	}

	public void setNome_empresa(String nome_empresa) {
		this.nome_empresa = nome_empresa;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
	}

	public String getCNPJ() {
		return CNPJ;
	}

	public void setCNPJ(String cNPJ) {
		CNPJ = cNPJ;
	}

	public String getTel_fixo() {
		return tel_fixo;
	}

	public void setTel_fixo(String tel_fixo) {
		this.tel_fixo = tel_fixo;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getSalva_parc_dif() {
		return salva_parc_dif;
	}

	public void setSalva_parc_dif(String salva_parc_dif) {
		this.salva_parc_dif = salva_parc_dif;
	}

	public String getAltera_orc() {
		return altera_orc;
	}

	public void setAltera_orc(String altera_orc) {
		this.altera_orc = altera_orc;
	}

	public String getGera_PDF() {
		return gera_PDF;
	}

	public void setGera_PDF(String gera_PDF) {
		this.gera_PDF = gera_PDF;
	}

	public String getVincula_barras() {
		return vincula_barras;
	}

	public void setVincula_barras(String vincula_barras) {
		this.vincula_barras = vincula_barras;
	}

	public Cliente getConsumidor_final() {
		return consumidor_final;
	}

	public void setConsumidor_final(Cliente consumidor_final) {
		this.consumidor_final = consumidor_final;
	}

	public Boolean getSo_orcamento() {
		return so_orcamento;
	}

	public void setSo_orcamento(Boolean so_orcamento) {
		this.so_orcamento = so_orcamento;
	}

}
