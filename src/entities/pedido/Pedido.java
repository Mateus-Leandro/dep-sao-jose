package entities.pedido;

import java.util.ArrayList;
import java.util.Date;

import entities.pessoa.Fornecedor;
import entities.produto.Produto_pedido;

public class Pedido {
	private Fornecedor fornecedor;
	private Date data_criacao;
	private Double valor_total;
	private Boolean pedido_entregue;
	private ArrayList<Produto_pedido> produtos_pedido = new ArrayList<Produto_pedido>();
	private Integer numero_cotacao;

	public Pedido(Fornecedor fornecedor, Date data_criacao, Double valor_total, Boolean pedido_entregue,
			ArrayList<Produto_pedido> produtos_pedido, Integer numero_cotacao) {
		super();
		this.fornecedor = fornecedor;
		this.data_criacao = data_criacao;
		this.valor_total = valor_total;
		this.pedido_entregue = pedido_entregue;
		this.produtos_pedido = produtos_pedido;
		this.numero_cotacao = numero_cotacao;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public Date getData_criacao() {
		return data_criacao;
	}

	public Double getValor_total() {
		return valor_total;
	}

	public void setValor_total(Double valor_total) {
		this.valor_total = valor_total;
	}

	public Boolean getPedido_entregue() {
		return pedido_entregue;
	}

	public void setPedido_entregue(Boolean pedido_entregue) {
		this.pedido_entregue = pedido_entregue;
	}

	public ArrayList<Produto_pedido> getProdutos_pedido() {
		return produtos_pedido;
	}

	public void setProdutos_pedido(ArrayList<Produto_pedido> produtos_pedido) {
		this.produtos_pedido = produtos_pedido;
	}

	public Integer getNumero_cotacao() {
		return numero_cotacao;
	}

	public void setNumero_cotacao(Integer numero_cotacao) {
		this.numero_cotacao = numero_cotacao;
	}
	

}
