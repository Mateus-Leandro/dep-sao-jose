package entidades;

import java.util.Date;

public class Parcelas_Venda {
	private int idVenda;
	private Double valorParcela;
	private boolean paga;
	private int numeroDaParcela;
	private enum formaPagamento{DINHEIRO, CARTAO, CHEQUE, TRANSFERENCIA, PIX, OUTROS}
	private Date dataVencimento;
}
