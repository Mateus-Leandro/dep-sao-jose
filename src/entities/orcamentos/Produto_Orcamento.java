package entities.orcamentos;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Produto_Orcamento {
	Integer codigo;
	String nome;
	String fator_venda;
	String codigo_barras;
	Double quantidade;
	Double preco_unitario;
	Double valor_desconto;
	Double valor_total;
	
	public Produto_Orcamento() {
		
	}
	
	public Produto_Orcamento(Integer codigo, String nome, String fator_venda, String codigo_barras, Double quantidade,
			Double preco_unitario, Double valor_desconto, Double valor_total) {
		this.codigo = codigo;
		this.nome = nome;
		this.fator_venda = fator_venda;
		this.codigo_barras = codigo_barras;
		this.quantidade = quantidade;
		this.preco_unitario = preco_unitario;
		this.valor_desconto = valor_desconto;
		this.valor_total = valor_total;
	}
	public Double getValor_total() {
		return valor_total;
	}
	public void setValor_total(Double valor_total) {
		this.valor_total = valor_total;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getFator_venda() {
		return fator_venda;
	}
	public void setFator_venda(String fator_venda) {
		this.fator_venda = fator_venda;
	}
	public String getCodigo_barras() {
		return codigo_barras;
	}
	public void setCodigo_barras(String codigo_barras) {
		this.codigo_barras = codigo_barras;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((codigo_barras == null) ? 0 : codigo_barras.hashCode());
		result = prime * result + ((fator_venda == null) ? 0 : fator_venda.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((preco_unitario == null) ? 0 : preco_unitario.hashCode());
		result = prime * result + ((quantidade == null) ? 0 : quantidade.hashCode());
		result = prime * result + ((valor_desconto == null) ? 0 : valor_desconto.hashCode());
		result = prime * result + ((valor_total == null) ? 0 : valor_total.hashCode());
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
		Produto_Orcamento other = (Produto_Orcamento) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (codigo_barras == null) {
			if (other.codigo_barras != null)
				return false;
		} else if (!codigo_barras.equals(other.codigo_barras))
			return false;
		if (fator_venda == null) {
			if (other.fator_venda != null)
				return false;
		} else if (!fator_venda.equals(other.fator_venda))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (preco_unitario == null) {
			if (other.preco_unitario != null)
				return false;
		} else if (!preco_unitario.equals(other.preco_unitario))
			return false;
		if (quantidade == null) {
			if (other.quantidade != null)
				return false;
		} else if (!quantidade.equals(other.quantidade))
			return false;
		if (valor_desconto == null) {
			if (other.valor_desconto != null)
				return false;
		} else if (!valor_desconto.equals(other.valor_desconto))
			return false;
		if (valor_total == null) {
			if (other.valor_total != null)
				return false;
		} else if (!valor_total.equals(other.valor_total))
			return false;
		return true;
	}
		
}
