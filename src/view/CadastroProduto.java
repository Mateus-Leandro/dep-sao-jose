package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;

import dao.ProdutoDAO;
import dao.SetorDAO;
import entities.Produto;
import entities.Setor;
import formatFields.FormataContabil;
import tableModels.ModeloTabelaProdutos;
import view.dialog.CadastroSetor;
import view.dialog.VariosBarras;

public class CadastroProduto extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JLabel lblNomeProduto;
	private JTextField txtCodigo;
	private JLabel lblCodigo;
	private JLabel lblCadatroDeProduto;
	private JLabel lblFatorDeVenda;
	private JLabel lblSetor;
	private JComboBox<Setor> cbxSetor;
	private JComboBox cbxFatorDeVenda;
	private JLabel lblPrecoVenda;
	private JFormattedTextField fTxtPrecoVenda;
	private JCheckBox checkBoxBloqueadoVenda;
	private JButton btnNovo;
	private JButton btnReload;
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
	private JButton btnMaisSetor;
	private JButton btnMaisBarras;

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
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setAlwaysOnTop(false);
		setResizable(false);
		setTitle("Produtos");
		this.setBounds(200, 400, 748, 583);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		tecla_pressionada();

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
		txtCodigo.setBounds(59, 11, 64, 20);
		txtCodigo.setToolTipText("Gerado autom\u00E1ticamente");
		txtCodigo.setEditable(false);
		txtCodigo.setColumns(10);
		panel.add(txtCodigo);

		lblNomeProduto = new JLabel("Nome");
		lblNomeProduto.setBounds(300, 14, 43, 17);
		lblNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panel.add(lblNomeProduto);

		lblFatorDeVenda = new JLabel("Fator de Venda");
		lblFatorDeVenda.setToolTipText("");
		lblFatorDeVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFatorDeVenda.setBounds(246, 106, 97, 17);
		panel.add(lblFatorDeVenda);

		lblSetor = new JLabel("Setor");
		lblSetor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetor.setBounds(10, 56, 33, 17);
		panel.add(lblSetor);

		cbxSetor = new JComboBox<Setor>();
		cbxSetor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent focoCbxSetor) {
				alimentaSetores();
			}
		});
		alimentaSetores();
		cbxSetor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cbxSetor.setBounds(53, 55, 214, 22);
		cbxSetor.setEnabled(false);
		panel.add(cbxSetor);

		cbxFatorDeVenda = new JComboBox();
		cbxFatorDeVenda.setEnabled(false);
		cbxFatorDeVenda.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cbxFatorDeVenda
				.setModel(new DefaultComboBoxModel(new String[] { "UN ", "CX ", "FD ", "PC", "KG ", "MT", "L" }));
		cbxFatorDeVenda.setBounds(348, 104, 48, 22);
		panel.add(cbxFatorDeVenda);

		lblPrecoVenda = new JLabel("Pre\u00E7o de Venda");
		lblPrecoVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecoVenda.setBounds(10, 104, 128, 17);
		panel.add(lblPrecoVenda);

		fTxtPrecoVenda = new JFormattedTextField();
		fTxtPrecoVenda.setDocument(new FormataContabil(9, 2));
		fTxtPrecoVenda.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtPrecoVenda.setEnabled(false);
		fTxtPrecoVenda.setBounds(117, 103, 87, 20);
		fTxtPrecoVenda.setHorizontalAlignment(JTextField.RIGHT);
		panel.add(fTxtPrecoVenda);

		checkBoxBloqueadoVenda = new JCheckBox("Bloqueado para venda");
		checkBoxBloqueadoVenda.setEnabled(false);
		checkBoxBloqueadoVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		checkBoxBloqueadoVenda.setToolTipText(
				"Caso marcado a caixa ao lado, n\u00E3o ser\u00E1 poss\u00EDvel realizar vendas com este produto!");
		checkBoxBloqueadoVenda.setBounds(517, 103, 163, 23);
		panel.add(checkBoxBloqueadoVenda);

		btnNovo = new JButton("Novo");
		btnNovo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNovo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNovoProduto) {
				novo_item();
			}
		});
		btnNovo.setBounds(10, 149, 87, 29);
		btnNovo.setIcon(icone_mais);
		panel.add(btnNovo);

		btnSalvar = new JButton("Salvar");
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSalvar.setVisible(false);
		btnSalvar.setIcon(icone_salvar);
		btnSalvar.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent click_salvar) {
				salvar_produto();
			}
		});

		btnSalvar.setBounds(107, 149, 97, 29);
		panel.add(btnSalvar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent clickDeletar) {

			}
		});
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnExcluir.setBounds(601, 149, 97, 29);
		btnExcluir.setEnabled(false);
		btnExcluir.setIcon(icone_excluir);
		panel.add(btnExcluir);

		btnEditar = new JButton("Editar");
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnEditar.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent clickEditar) {
				editar_item();

			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setBounds(504, 149, 87, 29);
		btnEditar.setIcon(icone_editar);
		panel.add(btnEditar);

		txtPesquisar = new JTextField();
		txtPesquisar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent escreveBarraPesquisa) {
				recarregarTabela();
			}
		});
		txtPesquisar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPesquisar.setColumns(10);
		txtPesquisar.setBounds(203, 191, 437, 20);
		panel.add(txtPesquisar);

		lblPesquisarPor = new JLabel("Pesquisar por");
		lblPesquisarPor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPesquisarPor.setBounds(10, 191, 87, 14);
		lblPesquisarPor.setEnabled(true);
		panel.add(lblPesquisarPor);

		cbxTipoPesquisa = new JComboBox();
		cbxTipoPesquisa.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent alteraTipoPesquisa) {
				recarregarTabela();
			}
		});
		cbxTipoPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cbxTipoPesquisa.setModel(new DefaultComboBoxModel(new String[] { "Nome", "Cod. Interno", "Cod. Barras" }));
		cbxTipoPesquisa.setSelectedIndex(0);
		cbxTipoPesquisa.setBounds(99, 189, 97, 22);
		panel.add(cbxTipoPesquisa);

		lblCdigoDeBarras = new JLabel("C\u00F3digo de Barras");
		lblCdigoDeBarras.setToolTipText("");
		lblCdigoDeBarras.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCdigoDeBarras.setBounds(407, 57, 109, 17);
		panel.add(lblCdigoDeBarras);

		MaskFormatter mascara_cod_barras = null;
		try {
			mascara_cod_barras = new MaskFormatter("##############");
		} catch (ParseException e) {
			e.getMessage();
		}
		fTxtCodigoBarras = new JFormattedTextField(mascara_cod_barras);
		fTxtCodigoBarras.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent ganhoFocoCodigoBarras) {
				if (fTxtCodigoBarras.getText().trim().isEmpty()) {
					fTxtCodigoBarras.setCaretPosition(0);
				}
			}
		});
		fTxtCodigoBarras.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtCodigoBarras.setEnabled(false);
		fTxtCodigoBarras.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCodigoBarras.setBounds(517, 56, 138, 20);
		panel.add(fTxtCodigoBarras);

		btnReload = new JButton();
		btnReload.setVisible(true);
		btnReload.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnReload.setBounds(650, 186, 48, 29);
		btnReload.setIcon(icone_reload);
		panel.add(btnReload);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancelar) {
				cancelar_produto();
			}
		});
		btnCancelar.setBounds(214, 149, 109, 29);
		btnCancelar.setVisible(false);
		btnCancelar.setIcon(icone_cancelar);
		panel.add(btnCancelar);

		MaskFormatter mascara_descricao_produto = null;
		try {
			mascara_descricao_produto = new MaskFormatter("*************************************************");
		} catch (ParseException e) {
			e.getMessage();
		}
		fTxtNomeProduto = new JFormattedTextField(mascara_descricao_produto);
		fTxtNomeProduto.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNomeProduto) {
				if (fTxtNomeProduto.getText().trim() == null || fTxtNomeProduto.getText().trim().isEmpty()) {
					fTxtNomeProduto.setCaretPosition(0);
				}
			}
		});
		fTxtNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtNomeProduto.setBounds(343, 11, 355, 20);
		fTxtNomeProduto.setEnabled(false);
		panel.add(fTxtNomeProduto);

		btnMaisSetor = new JButton("");
		btnMaisSetor.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickMaisSetor) {
				if (btnMaisSetor.isEnabled()) {
					CadastroSetor cadastro_setor = new CadastroSetor();
					cadastro_setor.setVisible(true);
				}
			}
		});
		btnMaisSetor.setBounds(274, 55, 33, 23);
		btnMaisSetor.setIcon(icone_mais);
		panel.add(btnMaisSetor);

		btnMaisBarras = new JButton("");
		btnMaisBarras.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickMaisBarras) {
				if (btnMaisBarras.isEnabled()) {
					JDialog varios_barras = new VariosBarras(txtCodigo.getText(), fTxtCodigoBarras.getText().trim(),
							fTxtNomeProduto.getText().trim());
					varios_barras.setVisible(rootPaneCheckingEnabled);
				}
			}
		});
		btnMaisBarras.setEnabled(false);
		btnMaisBarras.setBounds(665, 57, 33, 20);
		btnMaisBarras.setIcon(icone_mais);
		panel.add(btnMaisBarras);

		btnReload.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickSair) {
				recarregarTabela();
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
			public void mousePressed(MouseEvent clickLinhaTabela) {
				if (btnNovo.isEnabled()) {
					txtCodigo.setText(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 0).toString());
					fTxtNomeProduto.setText(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 1).toString());
					Object barras = tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 2);
					fTxtCodigoBarras.setText((String) barras);
					cbxSetor.setSelectedItem(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 3));
					cbxFatorDeVenda.getModel()
							.setSelectedItem(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 4));
					fTxtPrecoVenda.setText(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 5).toString()
							.replace("R$ ", ""));
					checkBoxBloqueadoVenda
							.setSelected((boolean) tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 6));
					btnMaisBarras.setEnabled(true);
					btnEditar.setVisible(true);
					btnExcluir.setVisible(true);
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
		Double preco_venda = Double.parseDouble(fTxtPrecoVenda.getText().replaceAll("\\.", "").replaceAll(",", "."));
		boolean bloqueado = checkBoxBloqueadoVenda.isSelected();
		String fator_venda = cbxFatorDeVenda.getSelectedItem().toString();
		String codigo_barras = fTxtCodigoBarras.getText().trim();

		if (codigo_barras.isEmpty()) {
			codigo_barras = null;
		}

		Produto produto = new Produto(null, nome, setor, preco_venda, bloqueado, null, codigo_barras, fator_venda);
		fTxtPrecoVenda.setBorder(new LineBorder(Color.LIGHT_GRAY));
		fTxtNomeProduto.setBorder(new LineBorder(Color.LIGHT_GRAY));

		try {
			boolean barras_valido = true;

			if (!fTxtCodigoBarras.getText().trim().isEmpty()) {
				barras_valido = produto_dao.testaBarrasInclusao(codigo_barras);
			}

			if (txtCodigo.getText().trim().isEmpty()) {
				if (barras_valido) {
					produto = produto_dao.inserirProduto(produto);
					String data_formatada = sdf.format(new java.sql.Date(System.currentTimeMillis()));
					produto.setDataCadastro(sdf.parse(data_formatada));
					limparCampos();
					desativarCampos();
					btnCancelar.setVisible(false);
					btnNovo.setEnabled(true);
					btnSalvar.setEnabled(false);
					lblPesquisarPor.setVisible(true);
					txtPesquisar.setVisible(true);
					cbxTipoPesquisa.setVisible(true);
					fTxtCodigoBarras.setBorder(new LineBorder(Color.lightGray));
					return produto;
				} else {
					JOptionPane.showMessageDialog(null, "Código de barras informado ja utilizado em outro item!",
							"Código de barras duplicado!", JOptionPane.WARNING_MESSAGE);
				}

			} else {
				produto.setIdProduto(Integer.parseInt(txtCodigo.getText().trim()));

				if (!fTxtCodigoBarras.getText().trim().isEmpty()) {
					barras_valido = produto_dao.testaBarrasAlteracao(produto.getIdProduto(), codigo_barras);
				}

				if (barras_valido) {
					produto_dao.alterarProduto(produto);
					limparCampos();
					desativarCampos();
					btnCancelar.setVisible(false);
					btnNovo.setEnabled(true);
					btnSalvar.setEnabled(false);
					lblPesquisarPor.setVisible(true);
					txtPesquisar.setVisible(true);
					cbxTipoPesquisa.setVisible(true);
					fTxtCodigoBarras.setBorder(new LineBorder(Color.lightGray));
					return produto;
				} else {
					JOptionPane.showMessageDialog(null, "Código de barras informado ja utilizado em outro item!",
							"Código de barras duplicado!", JOptionPane.WARNING_MESSAGE);
				}
			}

		} catch (Exception e) {
			e.getMessage();
		} finally {
			ConfiguralarguracolunaTabela(tabelaProdutos);
			btnReload.setVisible(true);
		}
		return null;
	}

	// Alimentar lista de produtos
	public ArrayList<Produto> alimentarListaProdutos(ArrayList<Produto> produtos) {
		ProdutoDAO produto_dao = new ProdutoDAO();

		String pesquisado = txtPesquisar.getText().trim() + "%";

		if (txtPesquisar.getText().trim().isEmpty()) {
			produtos = produto_dao.listarTodosProdutos(produtos);
			return produtos;
		} else {
			switch (cbxTipoPesquisa.getSelectedIndex()) {
			case 0:
				produtos = produto_dao.listarProdutosNome(produtos, pesquisado);
				break;
			case 1:
				produtos = produto_dao.listarProdutosCodigo(produtos, pesquisado);
				break;
			case 2:
				produtos = produto_dao.listarProdutosBarras(produtos, pesquisado);

				ArrayList<Produto> barras_vinculados = new ArrayList<Produto>();
				barras_vinculados = produto_dao.listarProdutosBarrasVinculados(barras_vinculados, pesquisado);

				produtos.addAll(barras_vinculados);
			}

			return produtos;
		}

	}

	// Recarrega a tabela de produtos.
	public void recarregarTabela() {
		if (btnReload.isEnabled()) {
			btnReload.doClick();
			produtos.clear();
			produtos = alimentarListaProdutos(produtos);
			modelo = new ModeloTabelaProdutos(produtos);
			tabelaProdutos.setModel(modelo);
			ConfiguralarguracolunaTabela(tabelaProdutos);
			modelo.fireTableDataChanged();
		}

	}

	// Recarrega tabela pesquisando por nome
	public void pesquisaPorNome() {
		produtos.clear();

		modelo = new ModeloTabelaProdutos(produtos);
		tabelaProdutos.setModel(modelo);
		ConfiguralarguracolunaTabela(tabelaProdutos);
		modelo.fireTableDataChanged();
	}

	// Alimentando comboBox de setores
	public void alimentaSetores() {
		SetorDAO setor_dao_cbx = new SetorDAO();
		ArrayList<Setor> setores = new ArrayList<>();
		setores = setor_dao_cbx.listarSetores(setores);
		try {
			cbxSetor.removeAllItems();
			for (Setor set : setores) {
				Setor setor = new Setor();
				setor.setCodSetor(set.getCodSetor());
				setor.setNome(set.getNome());
				cbxSetor.addItem(setor);

			}

		} catch (Exception e1) {
			e1.getMessage();
		}
	}

	// Ação cancelar produto
	public void cancelar_produto() {

		btnCancelar.doClick();
		btnCancelar.setVisible(false);
		btnNovo.setEnabled(true);
		btnExcluir.setVisible(true);
		btnEditar.setVisible(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnMaisBarras.setEnabled(false);
		btnSalvar.setVisible(false);
		txtPesquisar.setEnabled(true);
		cbxTipoPesquisa.setEnabled(true);
		lblPesquisarPor.setEnabled(true);
		btnReload.setEnabled(true);
		fTxtNomeProduto.setBorder(new LineBorder(Color.lightGray));
		fTxtPrecoVenda.setBorder(new LineBorder(Color.lightGray));
		desativarCampos();
		limparCampos();
	}

	// Ação para salvar um novo produto
	public void salvar_produto() {

		if (btnSalvar.isEnabled()) {
			boolean preco_vazio = fTxtPrecoVenda.getText().trim().isEmpty();

			Double preco;

			if (!preco_vazio) {
				preco = Double.parseDouble(fTxtPrecoVenda.getText().replaceAll("\\.", "").replaceAll(",", "."));
			} else {
				preco = 0.00;
			}

			if (fTxtNomeProduto.getText().trim().isEmpty() || preco < 0.01) {
				if (fTxtNomeProduto.getText().trim().isEmpty()) {
					fTxtNomeProduto.setBorder(new LineBorder(Color.RED));
					JOptionPane.showMessageDialog(null, "Necessário informar o nome do produto!");
				}

				if (preco < 0.01) {
					fTxtPrecoVenda.setBorder(new LineBorder(Color.RED));
					JOptionPane.showMessageDialog(null, "Necessario informar preço de venda!");
				}
			} else {
				gravarNovoProduto();
				recarregarTabela();
			}

		}

	}

	// Ação para habilitar campos de um novo produto.
	public void novo_item() {

		if (txtPesquisar.getText().trim().isEmpty()) {
			fTxtNomeProduto.setFocusable(true);
			btnCancelar.setVisible(true);
			btnNovo.setEnabled(false);
			btnEditar.setVisible(false);
			btnExcluir.setVisible(false);
			btnMaisBarras.setEnabled(false);
			btnReload.setEnabled(false);
			btnSalvar.setVisible(true);
			txtPesquisar.setEnabled(false);
			cbxTipoPesquisa.setEnabled(false);
			lblPesquisarPor.setEnabled(false);
			btnNovo.doClick();
			fTxtNomeProduto.requestFocusInWindow();
			recarregarTabela();
			limparCampos();
			ativarCampos();
		}
	}

	// Ação para excluir um produto
	public void excluir_item() {

		if (tabelaProdutos.getSelectedRow() != -1 && btnExcluir.isEnabled()) {
			try {
				ProdutoDAO produto_dao = new ProdutoDAO();
				boolean flag;

				int opcao = JOptionPane.showConfirmDialog(null,
						"Deseja excluir o produto abaixo?\n" + "Cod = "
								+ tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 0) + "\n" + "Nome = "
								+ tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 1),
						"Exclusão de Produto", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

				flag = opcao == JOptionPane.YES_OPTION;

				if (flag) {
					if (produto_dao
							.deletarProduto((Integer) tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 0))) {
						JOptionPane.showMessageDialog(null, "Produto excluído!", "Exclusão de produto",
								JOptionPane.ERROR_MESSAGE);
						recarregarTabela();
						limparCampos();
						btnExcluir.setEnabled(false);
						btnEditar.setEnabled(false);
					} else {
						JOptionPane.showMessageDialog(null, "Erro ao excluir produto!", "Exclusão de produto",
								JOptionPane.WARNING_MESSAGE);
					}
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Erro ao excluir produto!");
			}
		}
	}

	// Ação para habitar campos de edição do produto.
	public void editar_item() {

		if (btnEditar.isEnabled() && tabelaProdutos.getSelectedRow() != -1) {

			btnCancelar.setVisible(true);
			btnExcluir.setVisible(false);
			btnNovo.setEnabled(false);
			btnEditar.setVisible(false);
			btnSalvar.setVisible(true);
			btnMaisBarras.setEnabled(true);
			txtPesquisar.setEnabled(false);
			cbxTipoPesquisa.setEnabled(false);
			lblPesquisarPor.setEnabled(false);
			btnReload.setVisible(false);
			ativarCampos();
		}
	}

// Ação para ativar campos.
	public void ativarCampos() {
		fTxtNomeProduto.setEnabled(true);
		fTxtCodigoBarras.setEnabled(true);
		fTxtPrecoVenda.setEnabled(true);
		cbxFatorDeVenda.setEnabled(true);
		cbxSetor.setEnabled(true);
		checkBoxBloqueadoVenda.setEnabled(true);
		btnSalvar.setEnabled(true);
		btnMaisSetor.setEnabled(true);
	}

	// ação para desativar campos.
	public void desativarCampos() {
		fTxtNomeProduto.setEnabled(false);
		fTxtCodigoBarras.setEnabled(false);
		fTxtPrecoVenda.setEnabled(false);
		cbxFatorDeVenda.setEnabled(false);
		cbxSetor.setEnabled(false);
		checkBoxBloqueadoVenda.setEnabled(false);
		btnSalvar.setEnabled(false);
	}

	// Ação para limpas campos
	public void limparCampos() {
		txtCodigo.setText(null);
		fTxtNomeProduto.setText(null);
		fTxtCodigoBarras.setText(null);
		fTxtPrecoVenda.setText(null);
		checkBoxBloqueadoVenda.setSelected(false);
	}

	// Configurando largura das colunas
	public void ConfiguralarguracolunaTabela(JTable tabelaProdutos) {
		tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(8);
		tabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(140);
		tabelaProdutos.getColumnModel().getColumn(2).setPreferredWidth(50);
		tabelaProdutos.getColumnModel().getColumn(3).setPreferredWidth(50);
		tabelaProdutos.getColumnModel().getColumn(4).setPreferredWidth(10);
		tabelaProdutos.getColumnModel().getColumn(5).setPreferredWidth(25);
		tabelaProdutos.getColumnModel().getColumn(6).setPreferredWidth(25);
		tabelaProdutos.getColumnModel().getColumn(7).setPreferredWidth(30);
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelo);
		tabelaProdutos.setRowSorter(sorter);
	}

	// ------icons---------
	Icon icone_salvar = new ImageIcon(getClass().getResource("/icons/salvar.png"));
	Icon icone_cancelar = new ImageIcon(getClass().getResource("/icons/cancelar.png"));
	Icon icone_mais = new ImageIcon(getClass().getResource("/icons/mais.png"));
	Icon icone_editar = new ImageIcon(getClass().getResource("/icons/editar.png"));
	Icon icone_excluir = new ImageIcon(getClass().getResource("/icons/excluir.png"));
	Icon icone_reload = new ImageIcon(getClass().getResource("/icons/reload.png"));

	// ---------Atalhos do teclado--------
	public void tecla_pressionada() {
		InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "reload");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "novo");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "editar");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "excluir");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelar");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "salvar");

		this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);

		this.getRootPane().getActionMap().put("reload", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_recarregar) {
				recarregarTabela();
			}
		});

		this.getRootPane().getActionMap().put("editar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_editar) {
				btnEditar.doClick();
				editar_item();
			}
		}

		);

		this.getRootPane().getActionMap().put("cancelar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_cancelar) {
				cancelar_produto();
			}
		}

		);

		this.getRootPane().getActionMap().put("salvar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_salvar) {
				btnSalvar.doClick();
				salvar_produto();
			}
		}

		);

		this.getRootPane().getActionMap().put("novo", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_novo) {
				novo_item();
			}
		}

		);

		this.getRootPane().getActionMap().put("excluir", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_novo) {
				excluir_item();
			}
		}

		);
	}
}
