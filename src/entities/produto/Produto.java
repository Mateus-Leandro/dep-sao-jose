package entities.produto;

import java.util.Date;

public abstract class Produto {
	private Integer idProduto;
	private String descricao;
	private Setor setor;
	private String unidadeVenda;
	private Double prVenda;
	private Double margem;
	private String codigo_barra;
	private boolean bloqueadoVenda;
	private Date dataCadastro;

	public Produto() {
	}

	public Produto(Integer idProduto, String descricao, Setor setor, String unidadeVenda, Double prVenda, Double margem,
			String codigo_barra, boolean bloqueadoVenda, Date dataCadastro) {
		this.idProduto = idProduto;
		this.descricao = descricao;
		this.setor = setor;
		this.unidadeVenda = unidadeVenda;
		this.prVenda = prVenda;
		this.margem = margem;
		this.codigo_barra = codigo_barra;
		this.bloqueadoVenda = bloqueadoVenda;
		this.dataCadastro = dataCadastro;
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

	public Double getPrecoVenda() {
		return prVenda;
	}

	public void setPrecoVenda(Double prVenda) {
		this.prVenda = prVenda;
	}

	public Double getMargem() {
		return margem;
	}

	public void setMargem(Double margem) {
		this.margem = margem;
	}

	public String getCodigo_barra() {
		return codigo_barra;
	}
	
	public void setCodigo_barra(String codigo_barra) {
		this.codigo_barra = codigo_barra;
	}
	

	public boolean isBloqueadoVenda() {
		return bloqueadoVenda;
	}

	public void setBloqueadoVenda(boolean bloqueadoVenda) {
		this.bloqueadoVenda = bloqueadoVenda;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idProduto == null) ? 0 : idProduto.hashCode());
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
		Produto other = (Produto) obj;
		if (idProduto == null) {
			if (other.idProduto != null)
				return false;
		} else if (!idProduto.equals(other.idProduto))
			return false;
		return true;
	}
}
