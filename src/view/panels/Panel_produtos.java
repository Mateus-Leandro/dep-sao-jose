package view.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;

import dao.ProdutoDAO;
import dao.SetorDAO;
import entities.Produto;
import entities.Setor;
import formatFields.FormataNumeral;
import icons.Icones;
import tables.tableModels.ModeloTabelaProdutos;
import tables.tableSorters.SorterMonetario;
import view.dialog.CadastroSetor;
import view.dialog.VariosBarras;

public class Panel_produtos extends JPanel {
	private JTextField txtCodigo;
	private JLabel lblCodigo;
	private JLabel lblNome;
	private JFormattedTextField fTxtNomeProduto;
	private JLabel lblSetor;
	private JLabel lblCodigoBarras;
	private JFormattedTextField fTxtCodigoBarras;
	private JButton btnMaisSetor;
	private JButton btnMaisBarras;
	private JLabel lblPrecoCusto;
	private JFormattedTextField fTxtPrecoCusto;
	private JLabel lblMargem;
	private JFormattedTextField fTxtMargem;
	private JLabel lblPrecoSugerido;
	private JFormattedTextField fTxtPrecoSugerido;
	private JLabel lblPrVenda;
	private JFormattedTextField fTxtPrecoVenda;
	private JLabel lblPrecos;
	private JSeparator separador_precos;
	private JLabel lblFatorVenda;
	private JLabel lblInformacoesBasicas;
	private JSeparator separador_infoBasicas;
	private Icones icones = new Icones();
	private JButton btnNovo;
	private JLabel lblCadastroDeProdutos;
	private JSeparator separador_produtos;
	private JSeparator separador_clientes_cadastrados;
	private JLabel lblProdutosCadastrados;
	private JSeparator separador_clientes_cadastrados_2;
	private JTable tabelaProdutos;
	private JScrollPane scrollPane;
	private ArrayList<Setor> lista_setores = new ArrayList<Setor>();
	ArrayList<Produto> produtos = new ArrayList<Produto>();
	ModeloTabelaProdutos modelo = new ModeloTabelaProdutos(produtos);
	private JLabel lblPesquisarPor;
	private JComboBox cbxTipoPesquisa;
	private JFormattedTextField fTxtPesquisa = new JFormattedTextField();
	private JButton btnReload = new JButton();
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JLabel lblMargemPraticada;
	private JFormattedTextField fTxtMargemPraticada;
	private JComboBox cbxFatorVenda;
	private JComboBox<Setor> cbxSetor = new JComboBox<Setor>();

	/**
	 * Create the panel.
	 */
	public Panel_produtos() {
		setLayout(null);

		txtCodigo = new JTextField();
		txtCodigo.setEnabled(false);
		txtCodigo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtCodigo.setBounds(63, 152, 77, 20);
		add(txtCodigo);
		txtCodigo.setColumns(10);

		lblCodigo = new JLabel("C\u00F3digo");
		lblCodigo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigo.setBounds(16, 154, 48, 19);
		add(lblCodigo);

		lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNome.setBounds(325, 153, 38, 19);
		add(lblNome);

		MaskFormatter mascara_nome_produto = null;
		try {
			mascara_nome_produto = new MaskFormatter("*************************************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtNomeProduto = new JFormattedTextField(mascara_nome_produto);
		fTxtNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtNomeProduto.setEnabled(false);
		fTxtNomeProduto.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeProduto.setBounds(365, 152, 344, 20);
		add(fTxtNomeProduto);

		lblSetor = new JLabel("Setor");
		lblSetor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSetor.setBounds(16, 188, 33, 19);
		add(lblSetor);

		lblCodigoBarras = new JLabel("C\u00F3digo de barras");
		lblCodigoBarras.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigoBarras.setBounds(447, 188, 104, 19);
		add(lblCodigoBarras);

		MaskFormatter mascara_barras = null;
		try {
			mascara_barras = new MaskFormatter("##############");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtCodigoBarras = new JFormattedTextField(mascara_barras);
		fTxtCodigoBarras.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtCodigoBarras.setEnabled(false);
		fTxtCodigoBarras.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCodigoBarras.setBounds(557, 187, 117, 20);
		add(fTxtCodigoBarras);

		btnMaisSetor = new JButton();
		btnMaisSetor.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickMaisSetor) {
				if (btnMaisSetor.isEnabled()) {
					CadastroSetor cadastro_setor = new CadastroSetor();
					cadastro_setor.setVisible(true);
				}
			}
		});
		btnMaisSetor.setIcon(icones.getIcone_mais());
		btnMaisSetor.setBounds(272, 188, 26, 19);
		add(btnMaisSetor);

		btnMaisBarras = new JButton();
		btnMaisBarras.setEnabled(false);
		btnMaisBarras.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickMaisBarras) {

				if (btnMaisBarras.isEnabled()) {
					VariosBarras varios_barras = new VariosBarras(txtCodigo.getText().trim(),
							fTxtCodigoBarras.getText().trim(), fTxtNomeProduto.getText().trim());
					varios_barras.setVisible(true);
				}
			}
		});
		btnMaisBarras.setIcon(icones.getIcone_mais());
		btnMaisBarras.setBounds(684, 188, 25, 19);
		add(btnMaisBarras);

		lblPrecoCusto = new JLabel("Pr. Custo");
		lblPrecoCusto.setForeground(new Color(255, 0, 0));
		lblPrecoCusto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecoCusto.setBounds(20, 301, 57, 19);
		add(lblPrecoCusto);

		fTxtPrecoCusto = new JFormattedTextField();
		fTxtPrecoCusto.setEnabled(false);
		fTxtPrecoCusto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaCusto) {
				calcula_sugerido();
				calcula_margem_praticada();
			}
		});
		fTxtPrecoCusto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtPrecoCusto.setDocument(new FormataNumeral(9, 2));
		fTxtPrecoCusto.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtPrecoCusto.setBounds(80, 300, 77, 20);
		add(fTxtPrecoCusto);

		lblMargem = new JLabel("Margem%");
		lblMargem.setForeground(new Color(0, 0, 128));
		lblMargem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMargem.setBounds(191, 301, 67, 19);
		add(lblMargem);

		fTxtMargem = new JFormattedTextField();
		fTxtMargem.setEnabled(false);
		fTxtMargem.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaMargem) {
				calcula_sugerido();
			}
		});
		fTxtMargem.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtMargem.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtMargem.setDocument(new FormataNumeral(6, 2));
		fTxtMargem.setBounds(259, 300, 57, 20);
		add(fTxtMargem);

		lblPrecoSugerido = new JLabel("Pr. Sugerido");
		lblPrecoSugerido.setForeground(new Color(255, 140, 0));
		lblPrecoSugerido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecoSugerido.setBounds(353, 301, 77, 19);
		add(lblPrecoSugerido);

		fTxtPrecoSugerido = new JFormattedTextField();
		fTxtPrecoSugerido.setEnabled(false);
		fTxtPrecoSugerido.setEditable(false);
		fTxtPrecoSugerido.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtPrecoSugerido.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtPrecoSugerido.setDocument(new FormataNumeral(9, 2));
		fTxtPrecoSugerido.setBounds(432, 299, 77, 20);
		add(fTxtPrecoSugerido);

		lblPrVenda = new JLabel("Pr. Venda");
		lblPrVenda.setForeground(new Color(0, 100, 0));
		lblPrVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrVenda.setBounds(549, 301, 67, 19);
		add(lblPrVenda);

		fTxtPrecoVenda = new JFormattedTextField();
		fTxtPrecoVenda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaPrecoVenda) {
				calcula_margem_praticada();
			}
		});
		fTxtPrecoVenda.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtPrecoVenda.setEnabled(false);
		fTxtPrecoVenda.setDocument(new FormataNumeral(9, 2));
		fTxtPrecoVenda.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtPrecoVenda.setBounds(612, 299, 97, 20);
		add(fTxtPrecoVenda);

		lblMargemPraticada = new JLabel("Margem% Praticada");
		lblMargemPraticada.setForeground(Color.BLUE);
		lblMargemPraticada.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMargemPraticada.setBounds(514, 338, 128, 19);
		add(lblMargemPraticada);

		fTxtMargemPraticada = new JFormattedTextField();
		fTxtMargemPraticada.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtMargemPraticada.setEnabled(false);
		fTxtMargemPraticada.setEditable(false);
		fTxtMargemPraticada.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtMargemPraticada.setEnabled(false);
		fTxtMargemPraticada.setBounds(642, 337, 67, 20);
		add(fTxtMargemPraticada);

		lblPrecos = new JLabel("Pre\u00E7os");
		lblPrecos.setHorizontalAlignment(SwingConstants.CENTER);
		lblPrecos.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblPrecos.setBounds(10, 261, 80, 29);
		add(lblPrecos);

		separador_precos = new JSeparator();
		separador_precos.setBounds(87, 278, 622, 9);
		add(separador_precos);

		lblFatorVenda = new JLabel("Fator de venda");
		lblFatorVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFatorVenda.setBounds(16, 231, 97, 19);
		add(lblFatorVenda);

		cbxFatorVenda = new JComboBox<String>();
		cbxFatorVenda.setFont(new Font("Tahoma", Font.PLAIN, 12));
		add(cbxFatorVenda);

		lblInformacoesBasicas = new JLabel("Informa\u00E7\u00F5es B\u00E1sicas");
		lblInformacoesBasicas.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformacoesBasicas.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblInformacoesBasicas.setBounds(15, 110, 188, 29);
		add(lblInformacoesBasicas);

		separador_infoBasicas = new JSeparator();
		separador_infoBasicas.setBounds(207, 127, 502, 9);
		add(separador_infoBasicas);

		btnNovo = new JButton("Novo");
		btnNovo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNovo) {
				novo_item();
			}
		});
		btnNovo.setIcon(icones.getIcone_mais());
		btnNovo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNovo.setBounds(10, 70, 89, 29);
		add(btnNovo);

		lblCadastroDeProdutos = new JLabel("Cadastro de Produtos");
		lblCadastroDeProdutos.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastroDeProdutos.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblCadastroDeProdutos.setBounds(247, 11, 262, 29);
		add(lblCadastroDeProdutos);

		separador_produtos = new JSeparator();
		separador_produtos.setBounds(10, 50, 698, 9);
		add(separador_produtos);

		separador_clientes_cadastrados = new JSeparator();
		separador_clientes_cadastrados.setBounds(16, 427, 238, 9);
		add(separador_clientes_cadastrados);

		lblProdutosCadastrados = new JLabel("Produtos Cadastrados");
		lblProdutosCadastrados.setHorizontalAlignment(SwingConstants.CENTER);
		lblProdutosCadastrados.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblProdutosCadastrados.setBounds(255, 412, 225, 29);
		add(lblProdutosCadastrados);

		separador_clientes_cadastrados_2 = new JSeparator();
		separador_clientes_cadastrados_2.setBounds(481, 427, 228, 9);
		add(separador_clientes_cadastrados_2);

		tabelaProdutos = new JTable(modelo);
		tabelaProdutos.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tabelaProdutos.setAutoResizeMode(tabelaProdutos.AUTO_RESIZE_OFF);
		scrollPane = new JScrollPane(tabelaProdutos);
		scrollPane.setBounds(16, 483, 693, 170);
		add(scrollPane);

		recarregarTabela();

		tabelaProdutos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent selecaoLinhaTabela) {

				ListSelectionModel lsm = (ListSelectionModel) selecaoLinhaTabela.getSource();
				if (!lsm.isSelectionEmpty() && btnNovo.isVisible()) {

					txtCodigo.setText(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 0).toString());
					fTxtNomeProduto.setText(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 1).toString());
					cbxSetor.getModel().setSelectedItem(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 3));
					fTxtPrecoCusto.setText(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 4).toString()
							.replace("R$ ", ""));
					fTxtMargem.setText(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 5).toString());
					fTxtPrecoSugerido.setText(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 6).toString()
							.replace("R$ ", ""));
					fTxtPrecoVenda.setText(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 7).toString()
							.replace("R$ ", ""));
					fTxtMargemPraticada
							.setText(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 8).toString());
					fTxtCodigoBarras.setText((String) tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 9));
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnMaisBarras.setEnabled(true);
				}
			}
		});

		lblPesquisarPor = new JLabel("Pesquisar por");
		lblPesquisarPor.setToolTipText("");
		lblPesquisarPor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPesquisarPor.setBounds(16, 452, 89, 20);
		add(lblPesquisarPor);

		cbxTipoPesquisa = new JComboBox<String>();
		cbxTipoPesquisa.setMaximumRowCount(3);
		cbxTipoPesquisa.setModel(new DefaultComboBoxModel(new String[] { "C\u00F3digo", "Nome", "Cod. Barras" }));
		cbxTipoPesquisa.setSelectedIndex(0);
		cbxTipoPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxTipoPesquisa.setBounds(105, 450, 96, 26);
		add(cbxTipoPesquisa);
		fTxtPesquisa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaBarraPesquisa) {
				recarregarTabela();
			}
		});

		fTxtPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtPesquisa.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtPesquisa.setBounds(211, 452, 454, 20);
		add(fTxtPesquisa);
		btnReload.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickRecarregarTabela) {
				recarregarTabela();
			}
		});

		btnReload.setIcon(icones.getIcone_reload());
		btnReload.setBounds(675, 450, 34, 22);
		add(btnReload);

		btnEditar = new JButton("Editar");
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEditar) {
				editar_item();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setIcon(icones.getIcone_editar());
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEditar.setBounds(15, 378, 97, 29);
		add(btnEditar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickExcluir) {
				if (btnExcluir.isEnabled()) {
					excluir_item();
				}
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setIcon(icones.getIcone_excluir());
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExcluir.setBounds(122, 378, 97, 29);
		add(btnExcluir);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickSalvarProduto) {
				if (btnSalvar.isEnabled()) {
					salvar_produto();
				}
			}
		});
		btnSalvar.setVisible(false);
		btnSalvar.setIcon(icones.getIcone_salvar());
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSalvar.setBounds(481, 378, 104, 29);
		add(btnSalvar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancelar) {
				cancelar_item();
			}
		});
		btnCancelar.setVisible(false);
		btnCancelar.setIcon(icones.getIcone_cancelar());
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.setBounds(595, 378, 113, 29);
		add(btnCancelar);

		cbxFatorVenda = new JComboBox();
		cbxFatorVenda.setEnabled(false);
		cbxFatorVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxFatorVenda.setModel(new DefaultComboBoxModel(new String[] { "UN", "MT", "KG", "L", "CX", "FD", "PCT" }));
		cbxFatorVenda.setBounds(113, 231, 57, 22);
		add(cbxFatorVenda);
		cbxSetor.setEnabled(false);

		cbxSetor.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent focoCbxSetor) {
				Object setor_antigo = null;

				if (cbxSetor.getSelectedIndex() != -1) {
					setor_antigo = (Setor) cbxSetor.getSelectedItem();
				}
				alimenta_setores();
				cbxSetor.setSelectedItem(setor_antigo);

			}
		});
		cbxSetor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cbxSetor.setBounds(59, 189, 210, 20);
		add(cbxSetor);

	}

	// --------------Funções--------------

	public void ativarCampos() {
		fTxtCodigoBarras.setEnabled(true);
		fTxtMargem.setEnabled(true);
		fTxtNomeProduto.setEnabled(true);
		fTxtPrecoCusto.setEnabled(true);
		fTxtPrecoVenda.setEnabled(true);
		fTxtPrecoSugerido.setEnabled(true);
		fTxtMargemPraticada.setEnabled(true);
		cbxFatorVenda.setEnabled(true);
		cbxSetor.setEnabled(true);
	}

	public void desativarCampos() {
		fTxtCodigoBarras.setEnabled(false);
		fTxtMargem.setEnabled(false);
		fTxtNomeProduto.setEnabled(false);
		fTxtPrecoCusto.setEnabled(false);
		fTxtPrecoVenda.setEnabled(false);
		fTxtPrecoSugerido.setEnabled(false);
		fTxtMargemPraticada.setEnabled(false);
		cbxFatorVenda.setEnabled(false);
		cbxSetor.setEnabled(false);
	}

	public void limparCampos() {
		txtCodigo.setText(null);
		fTxtCodigoBarras.setText(null);
		fTxtMargem.setText(null);
		fTxtNomeProduto.setText(null);
		fTxtPrecoCusto.setText(null);
		fTxtPrecoVenda.setText(null);
		fTxtPrecoSugerido.setText(null);
		fTxtMargemPraticada.setText(null);
		cbxFatorVenda.setSelectedIndex(0);
		cbxSetor.getModel().setSelectedItem("");
	}

	public void novo_item() {
		alimenta_setores();
		limparCampos();
		ativarCampos();
		btnNovo.setVisible(false);
		btnEditar.setVisible(false);
		btnExcluir.setVisible(false);
		btnCancelar.setVisible(true);
		btnSalvar.setVisible(true);
		btnCancelar.setEnabled(true);
		btnSalvar.setEnabled(true);
		cbxSetor.setSelectedIndex(0);
	}

	public void cancelar_item() {
		desativarCampos();
		limparCampos();
		btnNovo.setVisible(true);
		btnSalvar.setVisible(false);
		btnCancelar.setVisible(false);
		btnEditar.setVisible(true);
		btnExcluir.setVisible(true);
	}

	public void editar_item() {
		ativarCampos();
		btnEditar.setVisible(false);
		btnExcluir.setVisible(false);
		btnSalvar.setVisible(true);
		btnCancelar.setVisible(true);
	}

	public void excluir_item() {
		if (tabelaProdutos.getSelectedRow() != -1) {
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

	public void alimenta_setores() {
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

	// Recarrega a tabela de produtos.
	public void recarregarTabela() {
		if (btnReload.isEnabled()) {
			produtos.clear();
			produtos = alimentarListaProdutos(produtos);
			modelo = new ModeloTabelaProdutos(produtos);
			tabelaProdutos.setModel(modelo);
			ConfiguraLarguraColunaTabela(tabelaProdutos);
			modelo.fireTableDataChanged();
		}

	}

	public void salvar_produto() {
		if (btnSalvar.isVisible()) {

			Double preco = null;
			preco = testa_preco_vazio(preco);

			if (fTxtNomeProduto.getText().trim().isEmpty() || preco < 0.01) {
				if (fTxtNomeProduto.getText().trim().isEmpty()) {
					fTxtNomeProduto.setBorder(new LineBorder(Color.RED));
					JOptionPane.showMessageDialog(null, "Necessário informar o nome do produto!");
				}

				if (preco < 0.01) {
					fTxtPrecoVenda.setBorder(new LineBorder(Color.RED));
					JOptionPane.showMessageDialog(null, "Necessario informar preço de venda!", "Preço de venda zerado.",
							JOptionPane.WARNING_MESSAGE);
				}
			} else {
				gravarNovoProduto();
			}

		}

	}

	public Produto gravarNovoProduto() {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		ProdutoDAO produto_dao = new ProdutoDAO();
		NumberFormat nf = new DecimalFormat("#,###0.00");
		String descricao = fTxtNomeProduto.getText().trim();

		Object setor = (Setor) cbxSetor.getModel().getSelectedItem();

		Double precoVenda = null;
		Double precoCusto = 0.00;
		Double margem = 0.00;
		Double prSugerido = 0.00;
		Double margemPraticada = 100.00;
		try {
			precoVenda = nf.parse(fTxtPrecoVenda.getText()).doubleValue();

			if (!fTxtPrecoCusto.getText().trim().isEmpty()) {
				precoCusto = nf.parse(fTxtPrecoCusto.getText().trim()).doubleValue();
			}

			if (!fTxtMargem.getText().trim().isEmpty()) {
				margem = nf.parse(fTxtMargem.getText()).doubleValue();
			}

			if (!fTxtPrecoSugerido.getText().trim().isEmpty()) {
				prSugerido = nf.parse(fTxtPrecoSugerido.getText()).doubleValue();
			}

			if (!fTxtMargemPraticada.getText().trim().isEmpty()) {
				margemPraticada = nf.parse(fTxtMargemPraticada.getText()).doubleValue();
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		String unidadeVenda = cbxFatorVenda.getSelectedItem().toString();
		Boolean bloqueadoVenda = false;
		Date dataCadastro = null;

		String codigo_barra = null;

		if (!fTxtCodigoBarras.getText().trim().isEmpty()) {
			codigo_barra = fTxtCodigoBarras.getText().trim();
		}

		Produto produto = new Produto(null, descricao, (Setor) setor, unidadeVenda, precoVenda, precoCusto, margem,
				prSugerido, margemPraticada, bloqueadoVenda, dataCadastro, codigo_barra);

		fTxtPrecoVenda.setBorder(new LineBorder(Color.LIGHT_GRAY));
		fTxtNomeProduto.setBorder(new LineBorder(Color.LIGHT_GRAY));

		try {
			boolean barras_valido = true;

			if (!fTxtCodigoBarras.getText().trim().isEmpty()) {
				barras_valido = produto_dao.testaBarrasInclusao(codigo_barra);
			}

			if (txtCodigo.getText().trim().isEmpty()) {
				if (barras_valido) {
					produto = produto_dao.inserirProduto(produto);

					if (produto.getIdProduto() != null) {

						JOptionPane.showMessageDialog(null,
								"Produto cadastrado com sucesso! " + "\nCódigo: " + produto.getIdProduto()
										+ "\nProduto: " + produto.getDescricao(),
								"Cadastro de produtos", JOptionPane.NO_OPTION);
						btnEditar.setVisible(true);
						btnExcluir.setVisible(true);
						btnSalvar.setVisible(false);
						btnNovo.setVisible(true);
						limparCampos();
					}

					String data_formatada = sdf.format(new java.sql.Date(System.currentTimeMillis()));
					produto.setDataCadastro(sdf.parse(data_formatada));
					limparCampos();
					desativarCampos();
					btnCancelar.setVisible(false);
					btnNovo.setEnabled(true);
					lblPesquisarPor.setVisible(true);
					lblPesquisarPor.setEnabled(true);
					fTxtPesquisa.setVisible(true);
					fTxtPesquisa.setEnabled(true);
					cbxTipoPesquisa.setVisible(true);
					cbxTipoPesquisa.setEnabled(true);
					fTxtCodigoBarras.setBorder(new LineBorder(Color.lightGray));
					recarregarTabela();
					return produto;
				} else {
					JOptionPane.showMessageDialog(null, "Código de barras informado ja utilizado em outro item!",
							"Código de barras duplicado!", JOptionPane.WARNING_MESSAGE);
				}

			} else {
				produto.setIdProduto(Integer.parseInt(txtCodigo.getText().trim()));

				if (!fTxtCodigoBarras.getText().trim().isEmpty()) {
					barras_valido = produto_dao.testaBarrasAlteracao(produto.getIdProduto(), codigo_barra);
				}

				if (barras_valido) {
					if (produto_dao.alterarProduto(produto)) {
						JOptionPane.showMessageDialog(null, "Produto alterado!", "Alteração de produto",
								JOptionPane.NO_OPTION);
						limparCampos();
						desativarCampos();
						btnCancelar.setVisible(false);
						btnNovo.setEnabled(true);
						btnSalvar.setVisible(false);
						lblPesquisarPor.setVisible(true);
						fTxtPesquisa.setVisible(true);
						cbxTipoPesquisa.setVisible(true);
						btnExcluir.setVisible(true);
						btnEditar.setVisible(true);
						fTxtCodigoBarras.setBorder(new LineBorder(Color.lightGray));
						recarregarTabela();
						return produto;
					} else {
						JOptionPane.showMessageDialog(null, "Erro ao alterar produto!", "Alteração de produto",
								JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Código de barras informado ja utilizado em outro item!",
							"Código de barras duplicado!", JOptionPane.WARNING_MESSAGE);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConfiguraLarguraColunaTabela(tabelaProdutos);
			btnReload.setVisible(true);
		}
		return null;
	}

	public void editar_produto() {
		if (btnEditar.isEnabled()) {
			ativarCampos();
		}
	}

	public Double testa_preco_vazio(Double preco) {
		boolean preco_vazio = fTxtPrecoVenda.getText().trim().isEmpty();

		if (!preco_vazio) {
			preco = Double.parseDouble(fTxtPrecoVenda.getText().replaceAll("\\.", "").replaceAll(",", "."));
		} else {
			preco = 0.00;
		}
		return preco;
	}

	// Alimentar lista de produtos
	public ArrayList<Produto> alimentarListaProdutos(ArrayList<Produto> produtos) {
		ProdutoDAO produto_dao = new ProdutoDAO();

		String pesquisado = fTxtPesquisa.getText().trim() + "%";

		if (fTxtPesquisa.getText().trim().isEmpty()) {
			produtos = produto_dao.listarTodosProdutos(produtos);
			return produtos;
		} else {
			switch (cbxTipoPesquisa.getSelectedItem().toString()) {
			case "Nome":
				produtos = produto_dao.listarProdutosNome(produtos, pesquisado);
				break;
			case "Código":
				produtos = produto_dao.listarProdutosCodigo(produtos, pesquisado);
				break;
			case "Cod. Barras":
				produtos = produto_dao.listarProdutosBarras(produtos, pesquisado);

				ArrayList<Produto> barras_vinculados = new ArrayList<Produto>();
				barras_vinculados = produto_dao.listarProdutosBarrasVinculados(barras_vinculados, pesquisado);

				produtos.addAll(barras_vinculados);
			}

			return produtos;
		}

	}

	public void ConfiguraLarguraColunaTabela(JTable tabelaProdutos) {

		SorterMonetario spv = new SorterMonetario();

		tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(40); // Código
		tabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(160); // Nome
		tabelaProdutos.getColumnModel().getColumn(2).setPreferredWidth(45); // Fator
		tabelaProdutos.getColumnModel().getColumn(3).setPreferredWidth(115); // Setor
		tabelaProdutos.getColumnModel().getColumn(4).setPreferredWidth(70); // Preco Custo
		tabelaProdutos.getColumnModel().getColumn(5).setPreferredWidth(52); // Margem
		tabelaProdutos.getColumnModel().getColumn(6).setPreferredWidth(70); // Preco Sugerido
		tabelaProdutos.getColumnModel().getColumn(7).setPreferredWidth(70); // Preco Venda
		tabelaProdutos.getColumnModel().getColumn(8).setPreferredWidth(52); // Margem Praticada
		tabelaProdutos.getColumnModel().getColumn(9).setPreferredWidth(80); // Código Barras
		tabelaProdutos.getColumnModel().getColumn(10).setPreferredWidth(40); // Bloqueado
		tabelaProdutos.getColumnModel().getColumn(11).setPreferredWidth(80); // Data Cadastro
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelo);
		sorter.setComparator(5, spv);
		tabelaProdutos.setRowSorter(sorter);
	}

	public void calcula_sugerido() {

		// Testa se margem e custo estão vazios.
		if (!fTxtPrecoCusto.getText().trim().isEmpty() && !fTxtMargem.getText().trim().isEmpty()) {
			DecimalFormat formata_sugestao = new DecimalFormat("#,###0.00");

			Double preco_custo = null;
			Double margem = null;
			try {
				preco_custo = formata_sugestao.parse(fTxtPrecoCusto.getText()).doubleValue();
				margem = formata_sugestao.parse(fTxtMargem.getText()).doubleValue();
			} catch (ParseException e) {
				e.printStackTrace();
			}

			Double preco_sugerido = preco_custo * (1 + (margem / 100));

			fTxtPrecoSugerido.setText(formata_sugestao.format(preco_sugerido));
		}
	}

	public void calcula_margem_praticada() {

		if (!fTxtPrecoVenda.getText().trim().isEmpty()) {
			DecimalFormat formata_numero = new DecimalFormat("#,###0.00");

			Double preco_custo = null;
			Double preco_venda = null;
			Double margem_praticada = 100.00;

			if (!fTxtPrecoCusto.getText().trim().isEmpty()) {
				try {
					preco_custo = formata_numero.parse(fTxtPrecoCusto.getText()).doubleValue();
					preco_venda = formata_numero.parse(fTxtPrecoVenda.getText()).doubleValue();
				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (preco_custo != 0.0) {
					margem_praticada = ((preco_venda - preco_custo) / preco_custo) * 100;
				}
				fTxtMargemPraticada.setText(formata_numero.format(margem_praticada));
			}

		}
	}
}
