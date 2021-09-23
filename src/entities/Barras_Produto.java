package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Barras_Produto {
	private String barras = new String();
	private Date dt_vinculacao = new Date();
	
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
	public Barras_Produto(String barras, Date dt_vinculacao) {
		this.barras = barras;
		this.dt_vinculacao = dt_vinculacao;
	}

}
