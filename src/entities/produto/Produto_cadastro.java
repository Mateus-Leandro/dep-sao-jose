package entities.produto;

import java.util.Date;

public class Produto_cadastro extends Produto {
	private Double PrCusto;
	private Double PrSugerido;
	private Double margemPraticada;

	public Produto_cadastro() {
	}

	public Produto_cadastro(Integer idProduto, String descricao, Setor setor, String unidadeVenda, Double prVenda,
			Double margem, String codigo_barra, Date dataCadastro, Double prCusto, Double prSugerido,
			Double margemPraticada, boolean bloqueadoVenda) {
		super(idProduto, descricao, setor, unidadeVenda, prVenda, margem, codigo_barra,bloqueadoVenda, dataCadastro);
		this.PrCusto = prCusto;
		this.PrSugerido = prSugerido;
		this.margemPraticada = margemPraticada;
	}

	public Double getPrCusto() {
		return PrCusto;
	}

	public void setPrCusto(Double prCusto) {
		PrCusto = prCusto;
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

	@Override
	public String toString() {
		return getDescricao();
	}

}
