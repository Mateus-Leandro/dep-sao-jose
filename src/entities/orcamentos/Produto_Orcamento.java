package entities.orcamentos;

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
		return ((preco_unitario - valor_desconto) * quantidade);
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
		return true;
	}
	
	
	
}
