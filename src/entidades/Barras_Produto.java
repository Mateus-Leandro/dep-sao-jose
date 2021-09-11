package entidades;

import java.util.ArrayList;
import java.util.List;

public class Barras_Produto {
	private Integer idProduto;
	private List<String> barras = new ArrayList<>();
	
	Barras_Produto(){
	}
	
	public Barras_Produto(Integer idProduto, List<String> barras) {
		this.idProduto = idProduto;
		this.barras = barras;
	}

	public Integer getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Integer idProduto) {
		this.idProduto = idProduto;
	}

	public List<String> getBarras() {
		return barras;
	}

	public void setBarras(List<String> barras) {
		this.barras = barras;
	}
	
}
