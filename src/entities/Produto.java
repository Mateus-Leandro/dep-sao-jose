package entities;

import java.util.Date;

public class Produto {
	private Integer idProduto;
	private String descricao;
	private Setor setor;
	private String unidadeVenda;
	private Double prVenda;
	private Double PrCusto;
	private Double margem;
	private Double PrSugerido;
	private Double margemPraticada;
	private boolean bloqueadoVenda;
	private Date dataCadastro;
	private String codigo_barra;	
	
	public Produto() {
	}

	
	
	
	public Produto(Integer idProduto, String descricao, Setor setor, String unidadeVenda, Double prVenda, Double prCusto,
			Double margem, Double prSugerido, Double margemPraticada, boolean bloqueadoVenda, Date dataCadastro,
			String codigo_barra) {
		this.idProduto = idProduto;
		this.descricao = descricao;
		this.setor = setor;
		this.unidadeVenda = unidadeVenda;
		this.prVenda = prVenda;
		PrCusto = prCusto;
		this.margem = margem;
		PrSugerido = prSugerido;
		this.margemPraticada = margemPraticada;
		this.bloqueadoVenda = bloqueadoVenda;
		this.dataCadastro = dataCadastro;
		this.codigo_barra = codigo_barra;
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




	public Double getPrCusto() {
		return PrCusto;
	}




	public void setPrCusto(Double prCusto) {
		PrCusto = prCusto;
	}




	public Double getMargem() {
		return margem;
	}




	public void setMargem(Double margem) {
		this.margem = margem;
	}




	public Double getPrSugerido() {
		return PrSugerido;
	}




	public void setPrSugerido(Double prSugerido) {
		PrSugerido = prSugerido;
	}




	public Double getMargemPraticada() {
		return margemPraticada;
	}




	public void setMargemPraticada(Double margemPraticada) {
		this.margemPraticada = margemPraticada;
	}




	public boolean getBloqueadoVenda() {
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




	public String getCodigo_barra() {
		return codigo_barra;
	}




	public void setCodigo_barra(String codigo_barra) {
		this.codigo_barra = codigo_barra;
	}



	
	

	@Override
	public String toString() {
		return "Produto [idProduto=" + idProduto + ", descricao=" + descricao + ", setor=" + setor + ", unidadeVenda="
				+ unidadeVenda + ", prVenda=" + prVenda + ", PrCusto=" + PrCusto + ", margem=" + margem + ", PrSugerido="
				+ PrSugerido + ", margemPraticada=" + margemPraticada + ", bloqueadoVenda=" + bloqueadoVenda
				+ ", dataCadastro=" + dataCadastro + ", codigo_barra=" + codigo_barra + "]";
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
