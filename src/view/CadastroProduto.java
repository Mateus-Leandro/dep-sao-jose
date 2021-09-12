package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import dao.ProdutoDAO;
import dao.SetorDao;
import db.DB;
import entidades.Produto;
import entidades.Setor;
import tableModels.ModeloTabelaProdutos;

public class CadastroProduto extends JFrame {
	private JPanel panel;
	private JLabel lblNomeProduto;
	private JTextField txtCodigo;
	private JLabel lblCodigo;
	private JLabel lblCadatroDeProduto;
	private JLabel lblFatorDeVenda;
	private JLabel lblSetor;
	private JComboBox cbxSetor;
	private JComboBox cbxFatorDeVenda;
	private JLabel lblPrecoVenda;
	private JFormattedTextField fTxtPrecoVenda;
	private JCheckBox checkBoxBloqueadoVenda;
	private JButton btnNovo;
	private JButton btnSair;
	private JButton btnSalvar;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JTextField txtPesquisar;
	private JLabel lblPesquisarPor;
	private JComboBox cbxTipoPesquisa;
	private JLabel lblCdigoDeBarras;
	private JFormattedTextField fTxtCodigoBarras;
	private JTable tabelaProdutos;
	private JScrollPane scrollPaneProdutos;
	ArrayList<Produto> produtos = new ArrayList<Produto>();
	ModeloTabelaProdutos modelo = new ModeloTabelaProdutos(produtos);
	private JButton btnCancelar;
	private JFormattedTextField fTxtNomeProduto;
	Connection conn = DB.getConnection();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroProduto frame = new CadastroProduto();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CadastroProduto() {
		setAlwaysOnTop(false);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Produtos");
		this.setBounds(200, 400, 748, 583);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		panel = new JPanel();
		panel.setBounds(10, 54, 708, 223);
		panel.setBorder(UIManager.getBorder("PopupMenu.border"));
		getContentPane().add(panel);
		panel.setLayout(null);

		lblCodigo = new JLabel("C\u00F3digo");
		lblCodigo.setBounds(10, 11, 43, 17);
		lblCodigo.setToolTipText("");
		lblCodigo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtCodigo.setBounds(59, 11, 77, 20);
		txtCodigo.setToolTipText("Gerado autom\u00E1ticamente");
		txtCodigo.setEditable(false);
		txtCodigo.setColumns(10);
		panel.add(txtCodigo);

		lblNomeProduto = new JLabel("Nome");
		lblNomeProduto.setBounds(285, 11, 43, 17);
		lblNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblNomeProduto);

		lblFatorDeVenda = new JLabel("Fator de Venda");
		lblFatorDeVenda.setToolTipText("");
		lblFatorDeVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFatorDeVenda.setBounds(520, 63, 97, 17);
		panel.add(lblFatorDeVenda);

		lblSetor = new JLabel("Setor");
		lblSetor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetor.setBounds(7, 60, 33, 17);
		panel.add(lblSetor);

		// Alimentando ComboBox
		SetorDao setor_dao_cbx = new SetorDao();
		List<Setor> setores = setor_dao_cbx.listarSetores();

		try {
			cbxSetor = new JComboBox();
			cbxSetor.setEnabled(false);

			for (Setor set : setores) {
				Setor setor = new Setor();
				setor.setCodSetor(set.getCodSetor());
				setor.setNome(set.getNome());
				cbxSetor.addItem(setor);
			}

		} catch (Exception e1) {
			e1.getMessage();
		}
		cbxSetor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cbxSetor.setBounds(50, 59, 193, 22);
		panel.add(cbxSetor);

		cbxFatorDeVenda = new JComboBox();
		cbxFatorDeVenda.setEnabled(false);
		cbxFatorDeVenda.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cbxFatorDeVenda
				.setModel(new DefaultComboBoxModel(new String[] { "UN ", "CX ", "FD ", "PC", "KG ", "MT", "L" }));
		cbxFatorDeVenda.setBounds(621, 61, 77, 22);
		panel.add(cbxFatorDeVenda);

		lblPrecoVenda = new JLabel("Pre\u00E7o de Venda");
		lblPrecoVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecoVenda.setBounds(10, 107, 100, 17);
		panel.add(lblPrecoVenda);

		MaskFormatter mascara_preco = new MaskFormatter();
		try {
			mascara_preco = new MaskFormatter("*********");
			mascara_preco.setValidCharacters("0123456789,");
		} catch (ParseException e) {
			e.getMessage();
		}

		fTxtPrecoVenda = new JFormattedTextField(mascara_preco);
		fTxtPrecoVenda.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent ganhoFocoPrecoVenda) {
				fTxtPrecoVenda.setCaretPosition(0);
			}
		});
		fTxtPrecoVenda.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtPrecoVenda.setEnabled(false);
		fTxtPrecoVenda.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtPrecoVenda.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtPrecoVenda.setBounds(120, 107, 87, 20);
		panel.add(fTxtPrecoVenda);

		checkBoxBloqueadoVenda = new JCheckBox("Produto bloqueado pra venda");
		checkBoxBloqueadoVenda.setEnabled(false);
		checkBoxBloqueadoVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		checkBoxBloqueadoVenda.setToolTipText(
				"Caso marcado a caixa ao lado, n\u00E3o ser\u00E1 poss\u00EDvel realizar vendas com este produto!");
		checkBoxBloqueadoVenda.setBounds(221, 105, 214, 23);
		panel.add(checkBoxBloqueadoVenda);

		btnNovo = new JButton("Novo");
		btnNovo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNovo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNovoProduto) {
				limparCampos();
				ativarCampos();
				btnCancelar.setVisible(true);
				btnNovo.setEnabled(false);
				btnEditar.setEnabled(false);
				btnExcluir.setEnabled(false);
			}
		});
		btnNovo.setBounds(10, 149, 87, 29);
		panel.add(btnNovo);

		btnSalvar = new JButton("Salvar");
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSalvar.setEnabled(false);
		btnSalvar.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent click_salvar) {

				if (btnSalvar.isEnabled()) {
					if (fTxtNomeProduto.getText().trim().isEmpty() || fTxtPrecoVenda.getText().trim().isEmpty()) {
						if (fTxtNomeProduto.getText().trim().isEmpty()) {
							fTxtNomeProduto.setBorder(new LineBorder(Color.RED));
							JOptionPane.showMessageDialog(null, "Necessário informar o nome do produto!");
						}

						if (fTxtPrecoVenda.getText().trim().isEmpty()) {
							fTxtPrecoVenda.setBorder(new LineBorder(Color.RED));
							JOptionPane.showMessageDialog(null, "Necessario informar preço de venda!");
						}
					} else {
						gravarNovoProduto();
						recarregarTabela();
						limparCampos();
						btnCancelar.setVisible(false);
						btnNovo.setEnabled(true);
						btnSalvar.setEnabled(false);

					}
				}
			}
		});

		btnSalvar.setBounds(107, 149, 97, 29);
		panel.add(btnSalvar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent clickDeletar) {
					try {
						ProdutoDAO produto_dao = new ProdutoDAO();
						if (produto_dao.deletarProduto(
								(Integer) tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 0))) {
							JOptionPane.showMessageDialog(null, "Produto excluído!");
							modelo.removeProduto(tabelaProdutos.getSelectedRow());
							btnExcluir.setEnabled(false);
							btnEditar.setEnabled(false);
						}
					} catch (Exception e) {
						e.getMessage();
					}
			}
		});
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnExcluir.setBounds(601, 149, 97, 29);
		btnExcluir.setEnabled(false);
		panel.add(btnExcluir);

		btnEditar = new JButton("Editar");
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnEditar.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent Presseditar) {
				if (btnEditar.isEnabled()) {
					btnCancelar.setVisible(true);
					btnExcluir.setEnabled(false);
					btnNovo.setEnabled(false);
					ativarCampos();
				}

				btnEditar.setEnabled(false);
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setBounds(504, 149, 87, 29);
		panel.add(btnEditar);

		txtPesquisar = new JTextField();
		txtPesquisar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPesquisar.setColumns(10);
		txtPesquisar.setBounds(203, 191, 388, 20);
		panel.add(txtPesquisar);

		lblPesquisarPor = new JLabel("Pesquisar por");
		lblPesquisarPor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPesquisarPor.setBounds(10, 191, 87, 14);
		panel.add(lblPesquisarPor);

		cbxTipoPesquisa = new JComboBox();
		cbxTipoPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cbxTipoPesquisa.setModel(new DefaultComboBoxModel(new String[] { "Nome", "Cod. Interno", "Barras", "Preco" }));
		cbxTipoPesquisa.setSelectedIndex(1);
		cbxTipoPesquisa.setBounds(99, 189, 97, 22);
		panel.add(cbxTipoPesquisa);

		lblCdigoDeBarras = new JLabel("C\u00F3digo de Barras");
		lblCdigoDeBarras.setToolTipText("");
		lblCdigoDeBarras.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCdigoDeBarras.setBounds(274, 62, 109, 17);
		panel.add(lblCdigoDeBarras);

		MaskFormatter mascara_cod_barras = null;
		try {
			mascara_cod_barras = new MaskFormatter("#############");
		} catch (ParseException e) {
			e.getMessage();
		}
		fTxtCodigoBarras = new JFormattedTextField(mascara_cod_barras);
		fTxtCodigoBarras.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent ganhoFocoCodigoBarras) {
				fTxtCodigoBarras.setCaretPosition(0);
			}
		});
		fTxtCodigoBarras.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtCodigoBarras.setEnabled(false);
		fTxtCodigoBarras.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCodigoBarras.setBounds(387, 62, 109, 20);
		panel.add(fTxtCodigoBarras);

		btnSair = new JButton("Sair");
		btnSair.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSair.setBounds(601, 186, 97, 29);
		panel.add(btnSair);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancelar) {
				desativarCampos();
				limparCampos();
				btnCancelar.setVisible(false);
				btnNovo.setEnabled(true);
				btnExcluir.setEnabled(false);
			}
		});
		btnCancelar.setBounds(214, 149, 87, 29);
		btnCancelar.setVisible(false);
		panel.add(btnCancelar);

		MaskFormatter mascara_descricao_produto = null;
		try {
			mascara_descricao_produto = new MaskFormatter("*************************************************");
		} catch (ParseException e) {
			e.getMessage();
		}
		fTxtNomeProduto = new JFormattedTextField(mascara_descricao_produto);
		fTxtNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtNomeProduto.setBounds(338, 11, 360, 20);
		fTxtNomeProduto.setEnabled(false);
		panel.add(fTxtNomeProduto);

		btnSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickSair) {
				dispose();
				clickSair.consume();
			}
		});

		lblCadatroDeProduto = new JLabel("Cadastro de Produtos");
		lblCadatroDeProduto.setBounds(10, 11, 708, 32);
		lblCadatroDeProduto.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCadatroDeProduto.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblCadatroDeProduto);

		produtos = alimentarListaProdutos(produtos);
		tabelaProdutos = new JTable(modelo);
		tabelaProdutos.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tabelaProdutos.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickMouseLinhaTabela) {
				if (!btnSalvar.isEnabled()) {
					ProdutoDAO produto_dao = new ProdutoDAO();
					Produto produto = new Produto();

					txtCodigo.setText(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 0).toString());
					fTxtNomeProduto.setText(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 1).toString());
					Object barras = tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 2);
					fTxtCodigoBarras.setText((String) barras);
					cbxSetor.setSelectedItem(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 3));
					cbxFatorDeVenda.setSelectedItem(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 4));
					fTxtPrecoVenda.setText(String.valueOf(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 5))
							.replaceAll("\\.", ","));
					checkBoxBloqueadoVenda
							.setSelected((boolean) tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 6));

					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
				}
			}
		});

		tabelaProdutos.getTableHeader().setReorderingAllowed(false);
		tabelaProdutos.setBounds(20, 317, 595, 146);
		ConfiguralarguracolunaTabela(tabelaProdutos);
		scrollPaneProdutos = new JScrollPane(tabelaProdutos);
		scrollPaneProdutos.setBounds(10, 288, 708, 243);
		getContentPane().add(scrollPaneProdutos);

	}

	// ----------- Métodos------------
	public Produto gravarNovoProduto() {
		// Criando produto

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		ProdutoDAO produto_dao = new ProdutoDAO();
		String nome = fTxtNomeProduto.getText().trim();
		Setor setor = (Setor) cbxSetor.getSelectedItem();
		String preco = fTxtPrecoVenda.getText();
		int indice = preco.indexOf(",");
		preco = preco.substring(0, indice + 3);
		Double preco_venda = Double.parseDouble(preco.replaceAll(",", "."));
		boolean bloqueado = checkBoxBloqueadoVenda.isSelected();
		String fator_venda = cbxFatorDeVenda.getSelectedItem().toString();
		String codigo_barras = fTxtCodigoBarras.getText().trim();

		Produto produto = new Produto(null, nome, setor, preco_venda, bloqueado, null, codigo_barras, fator_venda);
		fTxtPrecoVenda.setBorder(new LineBorder(Color.LIGHT_GRAY));
		fTxtNomeProduto.setBorder(new LineBorder(Color.LIGHT_GRAY));

		try {
			if (txtCodigo.getText().isEmpty()) {

				// Gravando produto no banco de dados.
				produto = produto_dao.inserirProduto(produto);

				String data_formatada = sdf.format(new java.sql.Date(System.currentTimeMillis()));
				produto.setDataCadastro(sdf.parse(data_formatada));

				return produto;

			} else {
				produto.setIdProduto(Integer.parseInt(txtCodigo.getText().trim()));
				produto_dao.alterarProduto(produto);
				desativarCampos();
				return produto;
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConfiguralarguracolunaTabela(tabelaProdutos);
		}
		return null;
	}

	// Alimentar lista de produtos
	public ArrayList<Produto> alimentarListaProdutos(ArrayList<Produto> produtos) {
		ProdutoDAO produto_dao = new ProdutoDAO();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		ResultSet rs = null;
		try {
			rs = produto_dao.listarTodosProdutos();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			while (rs.next()) {
				Produto produto = new Produto();
				produto.setIdProduto(rs.getInt("idProduto"));
				produto.setDescricao(rs.getString("descricao"));
				produto.setCodigo_barra(rs.getString("codigoBarras"));
				produto.setSetor(new Setor(rs.getInt(4), rs.getString(5)));
				produto.setUnidadeVenda(rs.getString("unidadeVenda"));
				produto.setPreco(rs.getDouble("preco"));
				produto.setBloqueadoVenda(rs.getBoolean(8));
				String data_formatada = sdf.format(rs.getDate(9));
				produto.setDataCadastro(sdf.parse(data_formatada));
				produtos.add(produto);
			}
		} catch (SQLException e) {
			e.getMessage();
		} catch (ParseException e) {
			e.getMessage();
		}
		return produtos;
	}

	// Recarrega a tabela de produtos.
	public void recarregarTabela() {
		produtos.clear();
		produtos = alimentarListaProdutos(produtos);
		modelo = new ModeloTabelaProdutos(produtos);
		tabelaProdutos.setModel(modelo);
		ConfiguralarguracolunaTabela(tabelaProdutos);
		modelo.fireTableDataChanged();
	}

	public void ativarCampos() {
		fTxtNomeProduto.setEnabled(true);
		fTxtCodigoBarras.setEnabled(true);
		fTxtPrecoVenda.setEnabled(true);
		cbxFatorDeVenda.setEnabled(true);
		cbxSetor.setEnabled(true);
		checkBoxBloqueadoVenda.setEnabled(true);
		btnSalvar.setEnabled(true);
	}

	public void desativarCampos() {
		fTxtNomeProduto.setEnabled(false);
		fTxtCodigoBarras.setEnabled(false);
		fTxtPrecoVenda.setEnabled(false);
		cbxFatorDeVenda.setEnabled(false);
		cbxSetor.setEnabled(false);
		checkBoxBloqueadoVenda.setEnabled(false);
		btnSalvar.setEnabled(false);
	}

	public void limparCampos() {
		txtCodigo.setText(null);
		fTxtNomeProduto.setText(null);
		fTxtCodigoBarras.setText(null);
		fTxtPrecoVenda.setText(null);
		checkBoxBloqueadoVenda.setSelected(false);
	}

	// Configurando largura das colunas
	public void ConfiguralarguracolunaTabela(JTable tabelaProdutos) {
		tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(10);
		tabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(150);
		tabelaProdutos.getColumnModel().getColumn(2).setPreferredWidth(60);
		tabelaProdutos.getColumnModel().getColumn(3).setPreferredWidth(50);
		tabelaProdutos.getColumnModel().getColumn(4).setPreferredWidth(10);
		tabelaProdutos.getColumnModel().getColumn(5).setPreferredWidth(25);
		tabelaProdutos.getColumnModel().getColumn(6).setPreferredWidth(20);
		tabelaProdutos.getColumnModel().getColumn(7).setPreferredWidth(30);
		tabelaProdutos.setAutoCreateRowSorter(true);
	}
}
