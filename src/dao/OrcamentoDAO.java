package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.mysql.cj.xdevapi.Result;

import db.DB;
import entities.cliente.Cliente;
import entities.orcamentos.Orcamento;
import entities.orcamentos.Produto_Orcamento;

public class OrcamentoDAO {

	private Connection conn;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public Orcamento salvar_orcamento(Orcamento orcamento) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn.setAutoCommit(false);

			orcamento.setId_orcamento(null);
			if (orcamento.getId_cliente() == null) {
				orcamento.setId_cliente(1);
			}

			ps = conn.prepareStatement("INSERT INTO `banco_deposito`.`orcamento` "
					+ "(`idCliente`, `quantidadeProdutos`, `totalMercadoriasBruto`, "
					+ "`totalMercadoriasLiquido`, `frete`, `descontoFinal`, `valorTotal`, `faturado`, `numeroParcelas`, `dataInclusao`) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setInt(1, orcamento.getId_cliente());
			ps.setInt(2, orcamento.getQuantidade_produtos());
			ps.setDouble(3, orcamento.getTotal_mercadorias_bruto());
			ps.setDouble(4, orcamento.getTotal_mercadorias_liquido());
			ps.setDouble(5, orcamento.getFrete());
			ps.setDouble(6, orcamento.getDesconto_final());
			ps.setDouble(7, orcamento.getValor_total());
			ps.setBoolean(8, orcamento.getFaturado());
			ps.setInt(9, orcamento.getNumero_de_parcelas());
			ps.setDate(10, new java.sql.Date(System.currentTimeMillis()));

			ps.execute();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				orcamento.setId_orcamento(rs.getInt(1));

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

				conn.commit();
				return orcamento;
			} else {
				return null;
			}

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
			ps = conn.prepareStatement("DELETE FROM financeiro WHERE idOrcamento = ?");
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

	public ArrayList<Orcamento> listar_orcamentos_do_cliente(ArrayList<Orcamento> lista_orcamentos, Cliente cliente) {

		conn = DB.getConnection();
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;

		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
			ps = conn.prepareStatement(
					"SELECT idOrcamento, faturado, quantidadeProdutos,totalMercadoriasBruto, totalMercadoriasLiquido, descontoFinal, frete, valorTotal, numeroParcelas, dataInclusao FROM orcamento WHERE idCliente = ?");
			ps.setInt(1, cliente.getIdCliente());
			rs = ps.executeQuery();

			while (rs.next()) {

				Orcamento orcamento = new Orcamento();
				orcamento.setId_orcamento(rs.getInt("idOrcamento"));
				orcamento.setId_cliente(cliente.getIdCliente());
				orcamento.setQuantidade_produtos(rs.getInt("quantidadeProdutos"));
				orcamento.setTotal_mercadorias_bruto(rs.getDouble("totalMercadoriasBruto"));
				orcamento.setTotal_mercadorias_liquido(rs.getDouble("totalMercadoriasLiquido"));
				orcamento.setFrete(rs.getDouble("frete"));
				orcamento.setDesconto_final(rs.getDouble("descontoFinal"));
				orcamento.setValor_total(rs.getDouble("valorTotal"));
				orcamento.setFaturado(rs.getBoolean("faturado"));
				orcamento.setNumero_de_parcelas(rs.getInt("numeroParcelas"));
				orcamento.setData_inclusao(rs.getDate("dataInclusao"));
				orcamento.setProdutos_do_orcamento(new ArrayList<Produto_Orcamento>());

				// Listando os produtos do orçamento.
				ps2 = conn.prepareStatement("SELECT produto_orcamento.idProdutoOrcamento AS codigo, "
						+ "produto.descricao as descricao, barras_produto.barras as barras, produto_orcamento.fator as fator, "
						+ "produto_orcamento.quantidade, produto_orcamento.precoUnitario AS preco_unitario, "
						+ "produto_orcamento.descontoProduto AS desconto, produto_orcamento.totalProduto AS total "
						+ "FROM produto_orcamento "
						+ "INNER JOIN produto on produto.idProduto = produto_orcamento.idProdutoOrcamento "
						+ "LEFT JOIN barras_produto on barras_produto.idProduto = produto_orcamento.idProdutoOrcamento "
						+ "WHERE produto_orcamento.idOrcamento = ?");
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

}
