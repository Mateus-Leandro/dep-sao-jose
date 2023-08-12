package entities.produto;

import java.util.Date;

public class Produto_orcamento extends Produto {
	private Double quantidade;
	private Double preco_unitario;
	private Double valor_desconto;
	private Double valor_total;

	public Produto_orcamento() {

	}

	public Produto_orcamento(Integer idProduto, String descricao, Setor setor, String unidadeVenda, Double prVenda,
			Double margem, String codigo_barra, boolean bloqueadoVenda, Date dataCadastro, Double quantidade,
			Double preco_unitario, Double valor_desconto, Double valor_total) {
		super(idProduto, descricao, setor, unidadeVenda, prVenda, margem, codigo_barra, bloqueadoVenda, dataCadastro);
		this.quantidade = quantidade;
		this.preco_unitario = preco_unitario;
		this.valor_desconto = valor_desconto;
		this.valor_total = valor_total;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco_unitario() {
		return preco_unitario;
	}

	public void setPreco_unitario(Double preco_unitario) {
		this.preco_unitario = preco_unitario;
	}

	public Double getValor_desconto() {
		return valor_desconto;
	}

	public void setValor_desconto(Double valor_desconto) {
		this.valor_desconto = valor_desconto;
	}

	public Double getValor_total() {
		return valor_total;
	}

	public void setValor_total(Double valor_total) {
		this.valor_total = valor_total;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((valor_total == null) ? 0 : valor_total.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto_orcamento other = (Produto_orcamento) obj;
		if (other.getIdProduto() != null) {
			if (!super.getIdProduto().equals(other.getIdProduto()))
				return false;
		} else
			return false;
		if (valor_total == null) {
			if (other.valor_total != null)
				return false;
		} else if (!valor_total.equals(other.valor_total))
			return false;
		return true;
	}

}
