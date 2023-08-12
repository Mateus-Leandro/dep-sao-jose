package entities.produto;

public class Produto_pedido extends Produto{
	private Double preco_fornecedor;
	private Double quantidade_pedida;
	private Double custo_atual;
	
	public Produto_pedido() {
	}
	
	public Produto_pedido(Double preco_fornecedor, Double quantidade_pedida, Double quantidade_entregue,
			Double custo_atual) {
		super();
		this.preco_fornecedor = preco_fornecedor;
		this.quantidade_pedida = quantidade_pedida;
		this.custo_atual = custo_atual;
	}

	public Double getPreco_fornecedor() {
		return preco_fornecedor;
	}

	public void setPreco_fornecedor(Double preco_fornecedor) {
		this.preco_fornecedor = preco_fornecedor;
	}

	public Double getQuantidade_pedida() {
		return quantidade_pedida;
	}

	public void setQuantidade_pedida(Double quantidade_pedida) {
		this.quantidade_pedida = quantidade_pedida;
	}

	public Double getCusto_atual() {
		return custo_atual;
	}

	public void setCusto_atual(Double custo_atual) {
		this.custo_atual = custo_atual;
	}
	
}
