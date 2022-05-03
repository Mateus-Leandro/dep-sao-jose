package entities.produto;

import java.util.Date;

public class Produto_cadastro extends Produto{
	private Double PrCusto;
	private Double margem;
	private Double PrSugerido;
	private Double margemPraticada;
	private boolean bloqueadoVenda;
	
	
	
	public Produto_cadastro() {
	}
	
	public Produto_cadastro(Integer idProduto, String descricao, Setor setor, String unidadeVenda, Double prVenda,
			String codigo_barra, Date dataCadastro, Double prCusto, Double margem, Double prSugerido,
			Double margemPraticada, boolean bloqueadoVenda) {
		super(idProduto, descricao, setor, unidadeVenda, prVenda, codigo_barra, dataCadastro);
		this.PrCusto = prCusto;
		this.margem = margem;
		this.PrSugerido = prSugerido;
		this.margemPraticada = margemPraticada;
		this.bloqueadoVenda = bloqueadoVenda;
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

	public boolean isBloqueadoVenda() {
		return bloqueadoVenda;
	}

	public void setBloqueadoVenda(boolean bloqueadoVenda) {
		this.bloqueadoVenda = bloqueadoVenda;
	}

	@Override
	public String toString() {
		return getDescricao();
	}
	
}
