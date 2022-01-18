package entities.orcamentos;

import java.util.ArrayList;
import java.util.Date;

import entities.cliente.Cliente;
import entities.financeiro.Parcela;

public class Orcamento {

	Integer id_orcamento;
	Cliente cliente;
	Integer quantidade_produtos;
	Double total_mercadorias_bruto;
	Double total_mercadorias_liquido;
	Double frete;
	Double desconto_final;
	Double valor_total;
	Boolean faturado;
	Integer numero_de_parcelas;
	String observacao;
	Date data_inclusao;
	ArrayList<Produto_Orcamento> produtos_do_orcamento;
	ArrayList<Parcela> parcelas;

	public Orcamento() {
	}

	

	public Orcamento(Integer id_orcamento, Cliente cliente, Integer quantidade_produtos, Double total_mercadorias_bruto,
			Double total_mercadorias_liquido, Double frete, Double desconto_final, Double valor_total, Boolean faturado,
			Integer numero_de_parcelas, String observacao, Date data_inclusao,
			ArrayList<Produto_Orcamento> produtos_do_orcamento, ArrayList<Parcela> parcelas) {
		this.id_orcamento = id_orcamento;
		this.cliente = cliente;
		this.quantidade_produtos = quantidade_produtos;
		this.total_mercadorias_bruto = total_mercadorias_bruto;
		this.total_mercadorias_liquido = total_mercadorias_liquido;
		this.frete = frete;
		this.desconto_final = desconto_final;
		this.valor_total = valor_total;
		this.faturado = faturado;
		this.numero_de_parcelas = numero_de_parcelas;
		this.observacao = observacao;
		this.data_inclusao = data_inclusao;
		this.produtos_do_orcamento = produtos_do_orcamento;
		this.parcelas = parcelas;
	}



	public Integer getId_orcamento() {
		return id_orcamento;
	}

	public void setId_orcamento(Integer id_orcamento) {
		this.id_orcamento = id_orcamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Integer getQuantidade_produtos() {
		return quantidade_produtos;
	}

	public void setQuantidade_produtos(Integer quantidade_produtos) {
		this.quantidade_produtos = quantidade_produtos;
	}

	public Double getTotal_mercadorias_bruto() {
		return total_mercadorias_bruto;
	}

	public void setTotal_mercadorias_bruto(Double total_mercadorias_bruto) {
		this.total_mercadorias_bruto = total_mercadorias_bruto;
	}

	public Double getTotal_mercadorias_liquido() {
		return total_mercadorias_liquido;
	}

	public void setTotal_mercadorias_liquido(Double total_mercadorias_liquido) {
		this.total_mercadorias_liquido = total_mercadorias_liquido;
	}

	public Double getFrete() {
		return frete;
	}

	public void setFrete(Double frete) {
		this.frete = frete;
	}

	public Double getDesconto_final() {
		return desconto_final;
	}

	public void setDesconto_final(Double desconto_final) {
		this.desconto_final = desconto_final;
	}

	public Double getValor_total() {
		return valor_total;
	}

	public void setValor_total(Double valor_total) {
		this.valor_total = valor_total;
	}

	public Boolean getFaturado() {
		return faturado;
	}

	public void setFaturado(Boolean faturado) {
		this.faturado = faturado;
	}

	public Integer getNumero_de_parcelas() {
		return numero_de_parcelas;
	}

	public void setNumero_de_parcelas(Integer numero_de_parcelas) {
		this.numero_de_parcelas = numero_de_parcelas;
	}

	
	public String getObservacao() {
		return observacao;
	}
	
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	public Date getData_inclusao() {
		return data_inclusao;
	}

	public void setData_inclusao(Date data_inclusao) {
		this.data_inclusao = data_inclusao;
	}

	public ArrayList<Produto_Orcamento> getProdutos_do_orcamento() {
		return produtos_do_orcamento;
	}

	public void setProdutos_do_orcamento(ArrayList<Produto_Orcamento> produtos_do_orcamento) {
		this.produtos_do_orcamento = produtos_do_orcamento;
	}
	

	public ArrayList<Parcela> getParcelas() {
		if(parcelas != null) {
			return parcelas;
		}else {
			return null;
		}
	}

	public void setParcelas(ArrayList<Parcela> parcelas) {
		this.parcelas = parcelas;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id_orcamento == null) ? 0 : id_orcamento.hashCode());
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
		Orcamento other = (Orcamento) obj;
		if (id_orcamento == null) {
			if (other.id_orcamento != null)
				return false;
		} else if (!id_orcamento.equals(other.id_orcamento))
			return false;
		return true;
	}

}
