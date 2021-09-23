package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Vendas {
	private Integer id;
	private int idCliente;
	private Double valorProdutos;
	private Double valorFinal;
	private Double frete;
	private Double desconto;
	private Double acrescimo;
	private enum tipoPagamento {A_VISTA,A_PRAZO};
	private int numeroDeParcelas;
	private boolean pago;
	private String observacao;
	private Date dataVenda;
	
	private List<Parcelas_Venda> parcelas = new ArrayList<>();
	private List<Produtos_Venda> produtos_venda = new ArrayList<>();
	
}
