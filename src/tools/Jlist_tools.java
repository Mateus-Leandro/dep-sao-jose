package tools;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JScrollPane;

import dao.produto.ProdutoDAO;
import entities.produto.Produto_cadastro;

public class Jlist_tools {
	public void alimentar_lista_produtos(String tipo_busca, String texto_buscado,
			ArrayList<Produto_cadastro> lista_produtos, DefaultListModel<Produto_cadastro> list_model_produtos,
			JScrollPane scrollPaneListaProdutos, JList<Produto_cadastro> ltProdutos,
			JFormattedTextField fTxtNomeProduto, JFormattedTextField fTxtCodigoProduto) {

		list_model_produtos.clear();
		lista_produtos.clear();
		ProdutoDAO produto_dao = new ProdutoDAO();

		switch (tipo_busca) {
		case "NOME":
			lista_produtos = produto_dao.listarProdutosNome(lista_produtos, fTxtNomeProduto.getText().trim() + "%", 50);
			break;
		case "CÓDIGO":
			lista_produtos = produto_dao.listarProdutosCodigo(lista_produtos, fTxtCodigoProduto.getText().trim(), 50);
			break;
		case "BARRAS":
			lista_produtos = produto_dao.listarProdutosBarras(lista_produtos, texto_buscado + "%", 50);
			break;
		default:
			break;
		}

		for (Produto_cadastro produto : lista_produtos) {
			list_model_produtos.addElement(produto);
		}

		ltProdutos.setModel(list_model_produtos);

		if (!list_model_produtos.isEmpty()) {
			scrollPaneListaProdutos.setVisible(true);
		} else {
			scrollPaneListaProdutos.setVisible(false);
		}
	}

}
