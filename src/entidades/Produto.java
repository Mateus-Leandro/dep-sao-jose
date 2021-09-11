package entidades;

import java.util.Date;

public class Produto {
	private Integer idProduto;
	private String descricao;
	private Setor setor;
	private String unidadeVenda;
	private Double preco;
	private boolean bloqueadoVenda;
	private Date dataCadastro;
	private String codigo_barra;	
	
	public Produto() {
	}

	public Produto(Integer idProduto, String descricao, Setor setor, Double preco, boolean bloqueadoVenda,
			Date dataCadastro, String codigo_barra,String unidadeVenda) {
		this.idProduto = idProduto;
		this.descricao = descricao;
		this.setor = setor;
		this.preco = preco;
		this.bloqueadoVenda = bloqueadoVenda;
		this.dataCadastro = dataCadastro;
		this.codigo_barra = codigo_barra;
		this.unidadeVenda = unidadeVenda;
	}
	
	
	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public String getUnidadeVenda() {
		return unidadeVenda;
	}

	public void setUnidadeVenda(String unidadeVenda) {
		this.unidadeVenda = unidadeVenda;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public boolean isBloqueadoVenda() {
		return bloqueadoVenda;
	}

	public void setBloqueadoVenda(boolean bloqueadoVenda) {
		this.bloqueadoVenda = bloqueadoVenda;
	}
	
	public boolean getBloqueadoVenda() {
		return bloqueadoVenda;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getCodigo_barra() {
		return codigo_barra;
	}

	public void setCodigo_barra(String codigo_barra) {
		this.codigo_barra = codigo_barra;
	}


	
	@Override
	public String toString() {
		return "Produto [idProduto=" + idProduto + ", descricao=" + descricao + ", codSetor=" + setor + ", preco="
				+ preco + ", bloqueadoVenda=" + bloqueadoVenda + ", dataCadastro=" + dataCadastro + ", codigo_barra="
				+ codigo_barra;
	}
	
	
}
