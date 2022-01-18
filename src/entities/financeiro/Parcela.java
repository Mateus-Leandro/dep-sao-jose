package entities.financeiro;

import java.util.Date;

public class Parcela {

	int id_orcamento;
	Double valor_parcela;
	Forma_pagamento forma_pagamento;
	Date data_pagamento;
	Date data_vencimento;
	
	
	public Parcela () {
	}

	
	public Parcela(int id_orcamento, Double valor_parcela, Forma_pagamento forma_pagamento, Date data_pagamento,
			Date data_vencimento) {
		this.id_orcamento = id_orcamento;
		this.valor_parcela = valor_parcela;
		this.forma_pagamento = forma_pagamento;
		this.data_pagamento = data_pagamento;
		this.data_vencimento = data_vencimento;
	}

	
	public int getId_orcamento() {
		return id_orcamento;
	}


	public void setId_orcamento(int id_orcamento) {
		this.id_orcamento = id_orcamento;
	}

	public Double getValor_parcela() {
		return valor_parcela;
	}


	public void setValor_parcela(Double valor_parcela) {
		this.valor_parcela = valor_parcela;
	}


	public Forma_pagamento getForma_pagamento() {
		return forma_pagamento;
	}


	public void setForma_pagamento(Forma_pagamento forma_pagamento) {
		this.forma_pagamento = forma_pagamento;
	}



	public Date getData_pagamento() {
		return data_pagamento;
	}



	public void setData_pagamento(Date data_pagamento) {
		this.data_pagamento = data_pagamento;
	}



	public Date getData_vencimento() {
		return data_vencimento;
	}



	public void setData_vencimento(Date data_vencimento) {
		this.data_vencimento = data_vencimento;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data_pagamento == null) ? 0 : data_pagamento.hashCode());
		result = prime * result + ((data_vencimento == null) ? 0 : data_vencimento.hashCode());
		result = prime * result + ((forma_pagamento == null) ? 0 : forma_pagamento.hashCode());
		result = prime * result + ((valor_parcela == null) ? 0 : valor_parcela.hashCode());
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
		Parcela other = (Parcela) obj;
		if (data_pagamento == null) {
			if (other.data_pagamento != null)
				return false;
		} else if (!data_pagamento.equals(other.data_pagamento))
			return false;
		if (data_vencimento == null) {
			if (other.data_vencimento != null)
				return false;
		} else if (!data_vencimento.equals(other.data_vencimento))
			return false;
		if (forma_pagamento == null) {
			if (other.forma_pagamento != null)
				return false;
		} else if (!forma_pagamento.equals(other.forma_pagamento))
			return false;
		if (valor_parcela == null) {
			if (other.valor_parcela != null)
				return false;
		} else if (!valor_parcela.equals(other.valor_parcela))
			return false;
		return true;
	}
	
}
