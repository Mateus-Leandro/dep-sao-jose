package tools;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFormattedTextField;
import javax.swing.JList;
import javax.swing.JScrollPane;

import dao.pessoa.FornecedorDAO;
import dao.produto.ProdutoDAO;
import entities.pessoa.Fornecedor;
import entities.produto.Produto_cadastro;

public class Jlist_tools {
	public void alimentar_lista_produtos(String tipo_busca, String texto_buscado,
			ArrayList<Produto_cadastro> lista_produtos, DefaultListModel<Produto_cadastro> list_model_produtos,
			JScrollPane scrollPaneListaProdutos, JList<Produto_cadastro> ltProdutos) {

		list_model_produtos.clear();
		lista_produtos.clear();
		ProdutoDAO produto_dao = new ProdutoDAO();

		switch (tipo_busca) {
			case "NOME":
				lista_produtos = produto_dao.listarProdutosNome(lista_produtos, texto_buscado + "%", 50);
				break;
			case "CODIGO":
				lista_produtos = produto_dao.listarProdutosCodigo(lista_produtos, texto_buscado, 50);
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

	public void alimentar_lista_fornecedores(String tipo_busca, String texto_buscado,
			ArrayList<Fornecedor> lista_fornecedores, DefaultListModel<Fornecedor> list_model_fornecedores,
			JScrollPane scrollPaneListaFornecedores, JList<Fornecedor> ltFornecedores) {

		list_model_fornecedores.clear();
		lista_fornecedores.clear();
		FornecedorDAO fornecedor_dao = new FornecedorDAO();

		switch (tipo_busca) {
			case "NOME":
				lista_fornecedores = fornecedor_dao.listarFornecedores(lista_fornecedores, tipo_busca,
						texto_buscado + "%", 50);
				break;
			case "CODIGO":
				lista_fornecedores = fornecedor_dao.listarFornecedores(lista_fornecedores, tipo_busca, texto_buscado,
						50);
				break;
			case "NOME FANT.":
				lista_fornecedores = fornecedor_dao.listarFornecedores(lista_fornecedores, tipo_busca,
						texto_buscado + "%", 50);
				break;
			case "DOCUMENTO":
				lista_fornecedores = fornecedor_dao.listarFornecedores(lista_fornecedores, tipo_busca,
						texto_buscado + "%", 50);
				break;
			default:
				break;
		}

		for (Fornecedor fornecedor : lista_fornecedores) {
			list_model_fornecedores.addElement(fornecedor);
		}

		ltFornecedores.setModel(list_model_fornecedores);

		if (!list_model_fornecedores.isEmpty()) {
			scrollPaneListaFornecedores.setVisible(true);
		} else {
			scrollPaneListaFornecedores.setVisible(false);
		}
	}

}
