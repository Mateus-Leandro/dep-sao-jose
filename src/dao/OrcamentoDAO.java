package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import db.DB;
import entities.orcamentos.Orcamento;
import entities.orcamentos.Produto_Orcamento;

public class OrcamentoDAO {

	private Connection conn;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public Orcamento salvar_orcamento(Orcamento orcamento, ArrayList<Produto_Orcamento> produtos_do_orcamento) {
		conn = DB.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn.setAutoCommit(false);
			
			orcamento.setId_orcamento(null);
			if(orcamento.getId_cliente() == null) {
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

				for (Produto_Orcamento produto : produtos_do_orcamento) {
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

}
