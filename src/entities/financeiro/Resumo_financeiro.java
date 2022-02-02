package entities.financeiro;

import java.util.Date;

public class Resumo_financeiro {
	
	private Double valor_aberto;
	private Double total_comprado;
	private Double maior_compra;
	private Date primeira_compra;
	private Date ultima_compra;
	
	
	public Resumo_financeiro() {
		
	}
	
	public Resumo_financeiro(Double valor_aberto, Double total_comprado, Double maior_compra, Date primeira_compra,
			Date ultima_compra) {
		this.valor_aberto = valor_aberto;
		this.total_comprado = total_comprado;
		this.maior_compra = maior_compra;
		this.primeira_compra = primeira_compra;
		this.ultima_compra = ultima_compra;
	}


	public Double getValor_aberto() {
		return valor_aberto;
	}


	public void setValor_aberto(Double valor_aberto) {
		this.valor_aberto = valor_aberto;
	}


	public Double getTotal_comprado() {
		return total_comprado;
	}


	public void setTotal_comprado(Double total_comprado) {
		this.total_comprado = total_comprado;
	}


	public Double getMaior_compra() {
		return maior_compra;
	}


	public void setMaior_compra(Double maior_compra) {
		this.maior_compra = maior_compra;
	}


	public Date getPrimeira_compra() {
		return primeira_compra;
	}


	public void setPrimeira_compra(Date primeira_compra) {
		this.primeira_compra = primeira_compra;
	}


	public Date getUltima_compra() {
		return ultima_compra;
	}


	public void setUltima_compra(Date ultima_compra) {
		this.ultima_compra = ultima_compra;
	}
	
	
}
