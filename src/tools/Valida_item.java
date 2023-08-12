package tools;

import java.util.ArrayList;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;

import entities.produto.Produto;

public class Valida_item {
	public Boolean bloqueado;
	public Produto produto;

	public Valida_item() {
	}

	public boolean bloqueado(Produto produto_selecionado, ArrayList<?> lista_produtos, JFormattedTextField cod_item,
			JFormattedTextField nome_item, JFormattedTextField barra_item) {
		this.produto = produto_selecionado;
		if (produto_selecionado.isBloqueadoVenda()) {
			lista_produtos.clear();
			produto_selecionado = null;
			cod_item.setText(null);
			nome_item.setText(null);
			barra_item.setText(null);
			JOptionPane.showMessageDialog(null,
					"O produto selecionado está bloqueado.\nAltere o cadastro e retire o bloqueio caso desejar utilizá-lo.",
					"Produto Bloqueado", JOptionPane.WARNING_MESSAGE);
		}
		return produto.isBloqueadoVenda();
	}

	public boolean ja_incluso(int cod_item, ArrayList<?> lista_produtos) {
		for (Object produto : lista_produtos) {
			this.produto = (Produto) produto;
			if (this.produto.getIdProduto().equals(cod_item)
					&& !this.produto.getDescricao().substring(0, 3).equals("*__")) {
				JOptionPane.showMessageDialog(null, "Produto já incluso!", "Produto já incluso anteriormente.",
						JOptionPane.WARNING_MESSAGE);
				produto = null;
				return true;
			}
		}
		return false;
	}
}
