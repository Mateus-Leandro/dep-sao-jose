package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.DB;
import entities.cliente.Cliente;
import entities.financeiro.Parcela;
import entities.orcamentos.Orcamento;
import entities.orcamentos.Produto_Orcamento;

public class OrcamentoDAO {

	private Connection conn;

	public Orcamento salvar_novo_orcamento(Orcamento orcamento) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn.setAutoCommit(false);
			Integer id_cliente = null;

			// Testa se foi passado cliente no orçamento.
			if (orcamento.getCliente() == null) {
				id_cliente = 1;
			} else {
				id_cliente = orcamento.getCliente().getIdCliente();
			}

			// salvando cabeçalho do orçamento.
			ps = conn.prepareStatement("INSERT INTO `banco_deposito`.`orcamento` "
					+ "(`idCliente`, `quantidadeProdutos`, `totalMercadoriasBruto`, "
					+ "`totalMercadoriasLiquido`, `frete`, `descontoFinal`, `valorTotal`, `faturado`, `numeroParcelas`, `observacao`, `dataInclusao`) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, id_cliente);
			ps.setInt(2, orcamento.getQuantidade_produtos());
			ps.setDouble(3, orcamento.getTotal_mercadorias_bruto());
			ps.setDouble(4, orcamento.getTotal_mercadorias_liquido());
			ps.setDouble(5, orcamento.getFrete());
			ps.setDouble(6, orcamento.getDesconto_final());
			ps.setDouble(7, orcamento.getValor_total());
			ps.setBoolean(8, orcamento.getFaturado());
			ps.setInt(9, orcamento.getNumero_de_parcelas());
			ps.setString(10, orcamento.getObservacao());
			ps.setDate(11, new java.sql.Date(System.currentTimeMillis()));

			ps.execute();

			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				orcamento.setId_orcamento(rs.getInt(1));
			}

			// salvando produtos do orçamento na tabela produtos_orcamento
			grava_itens_orcamento(orcamento);

			conn.commit();
			return orcamento;

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			return null;
		}
	}

	public boolean salvar_orcamento_editado(Orcamento orcamento_editado) {

		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn.setAutoCommit(false);

			ps = conn.prepareStatement("UPDATE `banco_deposito`.`orcamento` SET `idCliente` = ?, "
					+ "`quantidadeProdutos` = ?, `totalMercadoriasBruto` = ?, "
					+ "`totalMercadoriasLiquido` = ?, `frete` = ?, `descontoFinal` = ?, "
					+ "`valorTotal` = ? "
					+ "WHERE (`idOrcamento` = ?)");
			if(orcamento_editado.getCliente() != null) {
				ps.setInt(1, orcamento_editado.getCliente().getIdCliente());
			}else {
				ps.setInt(1,1);
			}
			ps.setInt(2, orcamento_editado.getQuantidade_produtos());
			ps.setDouble(3, orcamento_editado.getTotal_mercadorias_bruto());
			ps.setDouble(4, orcamento_editado.getTotal_mercadorias_liquido());
			ps.setDouble(5, orcamento_editado.getFrete());
			ps.setDouble(6, orcamento_editado.getDesconto_final());
			ps.setDouble(7, orcamento_editado.getValor_total());
			ps.setInt(8, orcamento_editado.getId_orcamento());
			ps.execute();

			// Excluindo itens do orçamento editado.
			ps = conn.prepareStatement("DELETE FROM `banco_deposito`.`produto_orcamento` WHERE idOrcamento = ?");
			ps.setInt(1, orcamento_editado.getId_orcamento());
			ps.execute();

			// Salvando produtos do orçamento na tabeça produtos_orcamento.
			grava_itens_orcamento(orcamento_editado);

			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return false;
	}

	public Boolean excluir_orcamento(int numero_orcamento) {
		conn = DB.getConnection();
		PreparedStatement ps = null;

		try {
			conn.setAutoCommit(false);

			// Deletando produtos do orçamento.
			ps = conn.prepareStatement("DELETE FROM produto_orcamento WHERE idOrcamento = ?");
			ps.setInt(1, numero_orcamento);
			ps.execute();

			// Deletando financeiro do orçamento.
			ps = conn.prepareStatement("DELETE FROM parcelas WHERE idOrcamento = ?");
			ps.setInt(1, numero_orcamento);
			ps.execute();

			// Deletando orcamento.
			ps = conn.prepareStatement("DELETE FROM orcamento WHERE idOrcamento = ?");
			ps.setInt(1, numero_orcamento);
			ps.execute();

			conn.commit();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			return false;
		}

	}

	public ArrayList<Orcamento> listar_orcamentos_do_cliente(ArrayList<Orcamento> lista_orcamentos, Cliente cliente,
			String numero_orcamento) {

		conn = DB.getConnection();
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;

		ResultSet rs = null;
		ResultSet rs2 = null;
		try {

			ps = conn.prepareStatement("SELECT idOrcamento, orcamento.idCliente, clientes.nome as nomeCliente, "
					+ "quantidadeProdutos, totalMercadoriasBruto, " + "totalMercadoriasLiquido, frete, descontoFinal, "
					+ "valorTotal, faturado, numeroParcelas, observacao, dataInclusao "
					+ "FROM orcamento INNER JOIN clientes ON clientes.idCliente = orcamento.idCliente "
					+ "WHERE orcamento.idCliente LIKE ? AND idOrcamento LIKE ?");
			// Testa se foi passado cliente específico na busca de orçamentos.
			if (cliente != null) {
				ps.setInt(1, cliente.getIdCliente());
			} else {
				ps.setString(1, "%");
			}

			if (numero_orcamento != null) {
				ps.setString(2, numero_orcamento);
			} else {
				ps.setString(2, "%");
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				// Buscando dados do cliente.
				ClienteDAO cliente_dao = new ClienteDAO();
				ArrayList<Cliente> cliente_do_orcamento = new ArrayList<Cliente>();
				cliente_do_orcamento = cliente_dao.listarClientes_codigo(cliente_do_orcamento,
						rs.getString("idCliente"));

				Orcamento orcamento = new Orcamento();
				orcamento.setId_orcamento(rs.getInt("idOrcamento"));
				orcamento.setCliente(cliente_do_orcamento.get(0));
				orcamento.setQuantidade_produtos(rs.getInt("quantidadeProdutos"));
				orcamento.setTotal_mercadorias_bruto(rs.getDouble("totalMercadoriasBruto"));
				orcamento.setTotal_mercadorias_liquido(rs.getDouble("totalMercadoriasLiquido"));
				orcamento.setFrete(rs.getDouble("frete"));
				orcamento.setDesconto_final(rs.getDouble("descontoFinal"));
				orcamento.setValor_total(rs.getDouble("valorTotal"));
				orcamento.setFaturado(rs.getBoolean("faturado"));
				orcamento.setNumero_de_parcelas(rs.getInt("numeroParcelas"));
				orcamento.setObservacao(rs.getString("observacao"));
				orcamento.setData_inclusao(rs.getDate("dataInclusao"));
				orcamento.setProdutos_do_orcamento(new ArrayList<Produto_Orcamento>());
				orcamento.setParcelas(new ArrayList<Parcela>());

				// Listando os produtos do orçamento.
				ps2 = conn.prepareStatement("SELECT produto_orcamento.idProdutoOrcamento AS codigo, "
						+ "produto.descricao as descricao, barras_produto.barras as barras, produto_orcamento.fator as fator, "
						+ "produto_orcamento.quantidade, produto_orcamento.precoUnitario AS preco_unitario, "
						+ "produto_orcamento.descontoProduto AS desconto, produto_orcamento.totalProduto AS total "
						+ "FROM produto_orcamento "
						+ "INNER JOIN produto on produto.idProduto = produto_orcamento.idProdutoOrcamento "
						+ "LEFT JOIN barras_produto on barras_produto.idProduto = produto_orcamento.idProdutoOrcamento "
						+ "WHERE produto_orcamento.idOrcamento = ? ");
				ps2.setInt(1, orcamento.getId_orcamento());
				rs2 = ps2.executeQuery();

				// adicionando os itens do orçamento na lista de produtos do orçamento.
				while (rs2.next()) {
					Produto_Orcamento produto = new Produto_Orcamento();
					produto.setCodigo(rs2.getInt("codigo"));
					produto.setNome(rs2.getString("descricao"));
					produto.setCodigo_barras(rs2.getString("barras"));
					produto.setFator_venda(rs2.getString("fator"));
					produto.setQuantidade(rs2.getDouble("quantidade"));
					produto.setPreco_unitario(rs2.getDouble("preco_unitario"));
					produto.setValor_desconto(rs2.getDouble("desconto"));
					produto.setValor_total(rs2.getDouble("total"));

					orcamento.getProdutos_do_orcamento().add(produto);
				}

				lista_orcamentos.add(orcamento);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return lista_orcamentos;
	}

	public boolean salva_observacao(Orcamento orcamento) {
		conn = DB.getConnection();
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(
					"UPDATE `banco_deposito`.`orcamento` SET `observacao` = ? WHERE (`idOrcamento` = ?);");
			ps.setString(1, orcamento.getObservacao());
			ps.setInt(2, orcamento.getId_orcamento());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleta_observacao(Orcamento orcamento) {
		conn = DB.getConnection();
		PreparedStatement ps = null;

		try {
			ps = conn.prepareStatement(
					"UPDATE `banco_deposito`.`orcamento` SET `observacao` = null WHERE (`idOrcamento` = ?);");
			ps.setInt(1, orcamento.getId_orcamento());
			ps.execute();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Funções auxiliares
	public void grava_itens_orcamento(Orcamento orcamento) {

		PreparedStatement ps = null;

		try {
			// Inserindo os proutos do orçamento salvo.
			for (Produto_Orcamento produto : orcamento.getProdutos_do_orcamento()) {
				ps = conn.prepareStatement("INSERT INTO `banco_deposito`.`produto_orcamento` "
						+ "(`idOrcamento`, `idProdutoOrcamento`, `quantidade`,`fator`, "
						+ "`precoUnitario`, `descontoProduto`, `totalProduto`) " + "VALUES (?, ?, ?, ?, ?, ?, ?)");

				ps.setInt(1, orcamento.getId_orcamento());
				ps.setInt(2, produto.getCodigo());
				ps.setDouble(3, produto.getQuantidade());
				ps.setString(4, produto.getFator_venda());
				ps.setDouble(5, produto.getPreco_unitario());
				ps.setDouble(6, produto.getValor_desconto());
				ps.setDouble(7, produto.getValor_total());
				ps.execute();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
