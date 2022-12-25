package dao.produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import db.DB;
import entities.produto.Produto_cadastro;
import entities.produto.Setor;

public class ProdutoDAO {

	private Connection conn;
	private PreparedStatement ps;
	private ResultSet rs;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public ProdutoDAO() {
	}

//------------Inserir-----------------
	public Produto_cadastro inserirProduto(Produto_cadastro produto) {
		conn = DB.getConnection();

		try {
			conn.setAutoCommit(false);
			produto.setIdProduto(null);
			ps = conn.prepareStatement(
					"INSERT INTO produto (descricao, codSetor, "
							+ "unidadeVenda, prVenda, prCusto, margem, prSugerido, margemPraticada, "
							+ " bloqueadoVenda, dataCadastro) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, produto.getDescricao().trim());

			if (produto.getSetor() != null) {
				ps.setInt(2, produto.getSetor().getCodSetor());
			} else {
				ps.setString(2, null);
			}
			ps.setString(3, produto.getUnidadeVenda());
			ps.setDouble(4, produto.getPrecoVenda());
			ps.setDouble(5, produto.getPrCusto());
			ps.setDouble(6, produto.getMargem());
			ps.setDouble(7, produto.getPrSugerido());
			ps.setDouble(8, produto.getMargemPraticada());
			ps.setBoolean(9, produto.isBloqueadoVenda());
			ps.setDate(10, new java.sql.Date(System.currentTimeMillis()));
			ps.execute();
			rs = ps.getGeneratedKeys();

			if (rs.next()) {
				produto.setIdProduto(rs.getInt(1));
			}
			conn.commit();

		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException erroRollBack) {
				erroRollBack.printStackTrace();
			}
			e.printStackTrace();
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
			DB.closeConnection(conn);
		}
		return produto;
	}

	// ----------- Alterar---------------
	public boolean alterarProduto(Produto_cadastro produto) {
		conn = DB.getConnection();

		try {
			conn.setAutoCommit(false);

			ps = conn.prepareStatement("UPDATE produto SET descricao = ?, codSetor = ?, unidadeVenda = ?, "
					+ "prVenda = ?, prCusto = ?, margem = ?, prSugerido = ?, margemPraticada = ?, bloqueadoVenda = ? "
					+ "WHERE idProduto = ?");
			ps.setString(1, produto.getDescricao());

			if (produto.getSetor() != null) {
				ps.setInt(2, produto.getSetor().getCodSetor());
			} else {
				ps.setString(2, null);
			}
			ps.setString(3, produto.getUnidadeVenda());
			ps.setDouble(4, produto.getPrecoVenda());
			ps.setDouble(5, produto.getPrCusto());
			ps.setDouble(6, produto.getMargem());
			ps.setDouble(7, produto.getPrSugerido());
			ps.setDouble(8, produto.getMargemPraticada());
			ps.setBoolean(9, produto.isBloqueadoVenda());
			ps.setInt(10, produto.getIdProduto());
			ps.executeUpdate();

			ps = conn.prepareStatement(
					"UPDATE barras_produto " + "SET barras = ? " + "WHERE idProduto = ? " + "AND principal IS TRUE");
			ps.setString(1, produto.getCodigo_barra());
			ps.setInt(2, produto.getIdProduto());
			ps.execute();

			conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
				return false;
			} catch (SQLException e1) {
				e.printStackTrace();
			}
			return false;
		} finally {
			DB.closeStatement(ps);
			DB.closeConnection(conn);
		}
		return true;
	}

	// --------Deletar--------------
	public boolean deletarProduto(int id) {
		conn = DB.getConnection();

		try {
			conn.setAutoCommit(false);

			ps = conn.prepareStatement("DELETE FROM barras_produto WHERE idProduto = ?");
			ps.setInt(1, id);
			ps.execute();

			ps = conn.prepareStatement("DELETE FROM produto WHERE idProduto = ?");
			ps.setInt(1, id);
			ps.execute();

			conn.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		} finally {
			DB.closeStatement(ps);
			DB.closeConnection(conn);
		}
	}

//---------Listar---------------
	public ArrayList<Produto_cadastro> listarTodosProdutos(ArrayList<Produto_cadastro> produtos, Integer limite) {
		conn = DB.getConnection();

		String query = "SELECT produto.idProduto, descricao, barras_produto.barras, "
				+ "produto.codSetor, setor.nome, unidadeVenda, prCusto, margem, prSugerido, "
				+ "prVenda, margemPraticada,bloqueadoVenda, dataCadastro " + "FROM produto "
				+ "LEFT JOIN setor ON produto.codSetor = setor.codSetor "
				+ "LEFT JOIN barras_produto ON produto.idProduto = barras_produto.idProduto "
				+ "WHERE barras_produto.principal IS NOT FALSE " + "ORDER BY descricao";

		try {
			if (limite == null) {
				ps = conn.prepareStatement(query);
			} else {
				ps = conn.prepareStatement(query + " " + "LIMIT ?");
				ps.setInt(1, limite);
			}
			rs = ps.executeQuery();

			while (rs.next()) {
				Produto_cadastro produto = new Produto_cadastro();
				produto.setIdProduto(rs.getInt("idProduto"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setCodigo_barra(rs.getString(3));
				produto.setSetor(new Setor(rs.getInt("codSetor"), rs.getString("nome")));
				produto.setUnidadeVenda(rs.getString("unidadeVenda"));
				produto.setPrCusto(rs.getDouble("prCusto"));
				produto.setMargem(rs.getDouble("margem"));
				produto.setPrSugerido(rs.getDouble("prSugerido"));
				produto.setPrecoVenda(rs.getDouble("prVenda"));
				produto.setMargemPraticada(rs.getDouble("margemPraticada"));
				produto.setBloqueadoVenda(rs.getBoolean("bloqueadoVenda"));
				String data_formatada = sdf.format(rs.getDate("dataCadastro"));
				produto.setDataCadastro(sdf.parse(data_formatada));
				produtos.add(produto);
			}
			return produtos;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
			DB.closeConnection(conn);
		}
	}

	// -----Listar produto por nome-----
	public ArrayList<Produto_cadastro> listarProdutosNome(ArrayList<Produto_cadastro> produtos, String nome,
			Integer limite) {
		conn = DB.getConnection();

		String select = "SELECT produto.idProduto, descricao, barras_produto.barras, "
				+ "produto.codSetor, setor.nome, unidadeVenda, prCusto, margem, prSugerido, "
				+ "prVenda, margemPraticada,bloqueadoVenda, dataCadastro " + "FROM produto "
				+ "LEFT JOIN setor ON produto.codSetor = setor.codSetor "
				+ "LEFT JOIN barras_produto ON produto.idProduto = barras_produto.idProduto "
				+ "WHERE barras_produto.principal IS NOT FALSE AND produto.descricao LIKE ? ORDER BY descricao";
		try {
			if (limite == null) {
				ps = conn.prepareStatement(select);
			} else {
				ps = conn.prepareStatement(select + " LIMIT " + limite);
			}

			ps.setString(1, nome);
			rs = ps.executeQuery();

			while (rs.next()) {
				Produto_cadastro produto = new Produto_cadastro();
				produto.setIdProduto(rs.getInt("idProduto"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setCodigo_barra(rs.getString(3));
				produto.setSetor(new Setor(rs.getInt("codSetor"), rs.getString("nome")));
				produto.setUnidadeVenda(rs.getString("unidadeVenda"));
				produto.setPrCusto(rs.getDouble("prCusto"));
				produto.setMargem(rs.getDouble("margem"));
				produto.setPrSugerido(rs.getDouble("prSugerido"));
				produto.setPrecoVenda(rs.getDouble("prVenda"));
				produto.setMargemPraticada(rs.getDouble("margemPraticada"));
				produto.setBloqueadoVenda(rs.getBoolean("bloqueadoVenda"));
				String data_formatada = sdf.format(rs.getDate("dataCadastro"));
				produto.setDataCadastro(sdf.parse(data_formatada));

				produtos.add(produto);
			}
			return produtos;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
			DB.closeConnection(conn);
		}
	}

	// -----Listar produto por c�digo interno----
	public ArrayList<Produto_cadastro> listarProdutosCodigo(ArrayList<Produto_cadastro> produtos, String codInterno,
			Integer limite) {
		conn = DB.getConnection();

		String select = "SELECT produto.idProduto, descricao, barras_produto.barras, "
				+ "produto.codSetor, setor.nome, unidadeVenda, prCusto, margem, prSugerido, "
				+ "prVenda, margemPraticada,bloqueadoVenda, dataCadastro " + "FROM produto "
				+ "LEFT JOIN setor ON produto.codSetor = setor.codSetor "
				+ "LEFT JOIN barras_produto ON produto.idProduto = barras_produto.idProduto "
				+ "WHERE barras_produto.principal IS NOT FALSE AND produto.idProduto LIKE ? ORDER BY descricao";
		try {

			if (limite == null) {
				ps = conn.prepareStatement(select);
			} else {
				ps = conn.prepareStatement(select + " LIMIT " + limite);
			}

			ps.setString(1, codInterno);
			rs = ps.executeQuery();

			while (rs.next()) {
				Produto_cadastro produto = new Produto_cadastro();
				produto.setIdProduto(rs.getInt("idProduto"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setCodigo_barra(rs.getString(3));
				produto.setSetor(new Setor(rs.getInt("codSetor"), rs.getString("nome")));
				produto.setUnidadeVenda(rs.getString("unidadeVenda"));
				produto.setPrCusto(rs.getDouble("prCusto"));
				produto.setMargem(rs.getDouble("margem"));
				produto.setPrSugerido(rs.getDouble("prSugerido"));
				produto.setPrecoVenda(rs.getDouble("prVenda"));
				produto.setMargemPraticada(rs.getDouble("margemPraticada"));
				produto.setBloqueadoVenda(rs.getBoolean("bloqueadoVenda"));
				String data_formatada = sdf.format(rs.getDate("dataCadastro"));
				produto.setDataCadastro(sdf.parse(data_formatada));

				produtos.add(produto);
			}
			return produtos;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
			DB.closeConnection(conn);
		}
	}

	// -----Listar produto por c�digo de barras----
	public ArrayList<Produto_cadastro> listarProdutosBarras(ArrayList<Produto_cadastro> produtos, String barras,
			Integer limite) {
		conn = DB.getConnection();

		String select = "SELECT produto.idProduto, descricao, barras_produto.barras, produto.codSetor, "
				+ "setor.nome, unidadeVenda, prCusto, margem, prSugerido, prVenda, margemPraticada, bloqueadoVenda, dataCadastro "
				+ "FROM produto LEFT JOIN setor ON produto.codSetor = setor.codSetor "
				+ "LEFT JOIN barras_produto ON produto.idProduto = barras_produto.idProduto "
				+ "WHERE barras_produto.principal IS NOT FALSE AND barras_produto.barras LIKE ? "
				+ "ORDER BY descricao";

		try {
			if (limite == null) {
				ps = conn.prepareStatement(select);
				ps.setString(1, barras);
			} else {
				ps = conn.prepareStatement(select + " LIMIT " + limite);
				ps.setString(1, barras);
			}

			rs = ps.executeQuery();

			while (rs.next()) {
				Produto_cadastro produto = new Produto_cadastro();
				produto.setIdProduto(rs.getInt("idProduto"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setCodigo_barra(rs.getString(3));
				produto.setSetor(new Setor(rs.getInt("codSetor"), rs.getString("nome")));
				produto.setUnidadeVenda(rs.getString("unidadeVenda"));
				produto.setPrCusto(rs.getDouble("prCusto"));
				produto.setMargem(rs.getDouble("margem"));
				produto.setPrSugerido(rs.getDouble("prSugerido"));
				produto.setPrecoVenda(rs.getDouble("prVenda"));
				produto.setMargemPraticada(rs.getDouble("margemPraticada"));
				produto.setBloqueadoVenda(rs.getBoolean("bloqueadoVenda"));
				String data_formatada = sdf.format(rs.getDate("dataCadastro"));
				produto.setDataCadastro(sdf.parse(data_formatada));
				produtos.add(produto);
			}

			// Alimentando produtos atrav�s do c�digo de barras vinculado.
			ArrayList<Produto_cadastro> barras_vinculados = new ArrayList<Produto_cadastro>();
			barras_vinculados = listarProdutosBarrasVinculados(barras_vinculados, barras, 50);
			for (Produto_cadastro prod : barras_vinculados) {
				if (!produtos.contains(prod)) {
					produtos.add(prod);
				}
			}

			return produtos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
			DB.closeConnection(conn);
		}

	}

	// Pesquisar barras vinculados
	private ArrayList<Produto_cadastro> listarProdutosBarrasVinculados(ArrayList<Produto_cadastro> produtos,
			String barras, Integer limite) {
		conn = DB.getConnection();

		String select = "SELECT produto.idProduto, descricao, barras_produto.barras, produto.codSetor, "
				+ "setor.nome, unidadeVenda, prCusto, prVenda, margem, prSugerido, margemPraticada,  "
				+ "bloqueadoVenda, dataCadastro FROM produto LEFT JOIN setor ON produto.codSetor = setor.codSetor "
				+ "LEFT JOIN barras_produto ON produto.idProduto = barras_produto.idProduto "
				+ "WHERE barras_produto.principal IS NOT TRUE AND barras_produto.barras LIKE ? "
				+ "GROUP BY idProduto ORDER BY descricao";

		try {
			if (limite == null) {
				ps = conn.prepareStatement(select);
			} else {
				ps = conn.prepareStatement(select + " LIMIT " + limite);
			}

			ps.setString(1, barras);
			rs = ps.executeQuery();

			while (rs.next()) {
				Produto_cadastro produto = new Produto_cadastro();
				produto.setIdProduto(rs.getInt("idProduto"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setCodigo_barra(rs.getString(3));
				produto.setSetor(new Setor(rs.getInt("codSetor"), rs.getString("nome")));
				produto.setUnidadeVenda(rs.getString("unidadeVenda"));
				produto.setPrCusto(rs.getDouble("prCusto"));
				produto.setMargem(rs.getDouble("margem"));
				produto.setPrSugerido(rs.getDouble("prSugerido"));
				produto.setPrecoVenda(rs.getDouble("prVenda"));
				produto.setMargemPraticada(rs.getDouble("margemPraticada"));
				produto.setBloqueadoVenda(rs.getBoolean("bloqueadoVenda"));
				String data_formatada = sdf.format(rs.getDate("dataCadastro"));
				produto.setDataCadastro(sdf.parse(data_formatada));

				produtos.add(produto);
			}

			return produtos;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
			DB.closeConnection(conn);
		}
	}

	// --Testar se o codigo de barras ja existe em outro item
	public boolean testaBarrasInclusao(String barras) {
		conn = DB.getConnection();

		if (barras.toString() != null) {
			try {
				ps = conn.prepareStatement("SELECT barras FROM barras_produto WHERE barras = ?");
				ps.setString(1, barras);
				rs = ps.executeQuery();

				if (rs.next()) {
					return false;
				} else {
					return true;
				}
			} catch (Exception e) {
				e.getMessage();
				return false;
			} finally {
				DB.closeResultSet(rs);
				DB.closeStatement(ps);
				DB.closeConnection(conn);
			}
		} else {
			return true;
		}
	}

	public Boolean produto_tem_orcamento(Integer cod_prod) {
		conn = DB.getConnection();

		try {
			ps = conn.prepareStatement("SELECT * FROM produto_orcamento WHERE idProdutoOrcamento = ?");
			ps.setInt(1, cod_prod);
			rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(ps);
			DB.closeConnection(conn);
		}
	}

	public boolean testaBarrasAlteracao(Integer codigo, String barras) {
		conn = DB.getConnection();

		if (barras.toString() != null) {
			try {
				ps = conn.prepareStatement("SELECT barras FROM barras_produto WHERE barras = ? AND idProduto = ?");
				ps.setString(1, barras);
				ps.setInt(2, codigo);
				rs = ps.executeQuery();

				if (rs.next()) {
					return true;
				} else {
					ps = conn.prepareStatement("SELECT barras FROM barras_produto WHERE barras = ?");
					ps.setString(1, barras);
					rs = ps.executeQuery();
					if (rs.next()) {
						return false;
					} else {
						return true;
					}
				}
			} catch (Exception e) {
				e.getMessage();
				return false;
			} finally {
				DB.closeResultSet(rs);
				DB.closeStatement(ps);
				DB.closeConnection(conn);
			}
		} else {
			return true;
		}

	}

}