package entities.pessoa;

import java.util.Date;

public class Fornecedor extends Pessoa {

	private boolean bloqueado_cotacao;
	private Integer numero_de_pedidos;
	private Integer ultimo_pedido;
	
	public Fornecedor() {
	}

	public Fornecedor(Integer id, String nome, String apelido, boolean pessoa_juridica, String cpf_cnpj,
			String inscricao_estadual, String cep, String cidade, String endereco, String referencia, String numero,
			String bairro, String email, String celular, String telefone, Boolean bloqueado, Date dataCadastro,
			boolean bloqueado_cotacao, Integer numero_de_pedidos, Integer ultimo_pedido) {
		super(id, nome, apelido, pessoa_juridica, cpf_cnpj, inscricao_estadual, cep, cidade, endereco, referencia,
				numero, bairro, email, celular, telefone, bloqueado, dataCadastro);
		this.bloqueado_cotacao = bloqueado_cotacao;
		this.numero_de_pedidos = numero_de_pedidos;
		this.ultimo_pedido = ultimo_pedido;
	}

	public boolean getBloqueadoCotacao() {
		return bloqueado_cotacao;
	}

	public void setBloqueado_cotacao(boolean bloqueado_cotacao) {
		this.bloqueado_cotacao = bloqueado_cotacao;
	}

	public Integer getNumero_de_pedidos() {
		return numero_de_pedidos;
	}

	public void setNumero_de_pedidos(Integer numero_de_pedidos) {
		this.numero_de_pedidos = numero_de_pedidos;
	}

	public Integer getUltimo_pedido() {
		return ultimo_pedido;
	}

	public void setUltimo_pedido(Integer ultimo_pedido) {
		this.ultimo_pedido = ultimo_pedido;
	}

	@Override
	public String toString() {
		return super.getNome();
	}
}
