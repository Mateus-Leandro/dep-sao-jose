package entities;

import java.util.Date;

public class Barras_Produto {
	private Boolean principal;
	private String barras = new String();
	private Date dt_vinculacao = new Date();
	
	public Barras_Produto(Boolean principal, String barras, Date dt_vinculacao) {
		this.principal = principal;
		this.barras = barras;
		this.dt_vinculacao = dt_vinculacao;
	}

	public Boolean getPrincipal() {
		return principal;
	}

	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}

	public String getBarras() {
		return barras;
	}

	public void setBarras(String barras) {
		this.barras = barras;
	}

	public Date getDt_vinculacao() {
		return dt_vinculacao;
	}

	public void setDt_vinculacao(Date dt_vinculacao) {
		this.dt_vinculacao = dt_vinculacao;
	}
	
}
