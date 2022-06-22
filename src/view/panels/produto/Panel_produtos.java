package view.panels.produto;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;

import dao.configuracoes.ConfiguracaoDAO;
import dao.produto.ProdutoDAO;
import dao.produto.SetorDAO;
import entities.configuracoes.Configuracoes;
import entities.produto.Produto_cadastro;
import entities.produto.Setor;
import icons.Icones;
import tables.tableModels.ModeloTabelaProdutos;
import tables.tableRenders.Render_tabela_produtos;
import tables.tableSorters.SorterData;
import tables.tableSorters.SorterMonetario;
import tools.JTextFieldLimit;
import tools.Jtext_tools;
import view.dialog.CadastroSetor;
import view.dialog.VariosBarras;
import view.formatFields.FormataNumeral;

public class Panel_produtos extends JPanel {
	private JTextField txtCodigo;
	private JLabel lblCodigo;
	private JLabel lblNome;
	private JFormattedTextField fTxtNomeProduto = new JFormattedTextField();
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
	ArrayList<Produto_cadastro> produtos = new ArrayList<Produto_cadastro>();
	ModeloTabelaProdutos modelo = new ModeloTabelaProdutos(produtos);
	private JLabel lblPesquisarPor;
	private JComboBox<String> cbxTipoPesquisa;
	private JFormattedTextField fTxtPesquisa = new JFormattedTextField();
	private JButton btnReload = new JButton();
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JLabel lblMargemPraticada;
	private JFormattedTextField fTxtMargemPraticada;
	private JComboBox<String> cbxFatorVenda;
	private JComboBox<Setor> cbxSetor = new JComboBox<Setor>();
	private Jtext_tools text_tools = new Jtext_tools();
	private JLabel lblObg_nomeProduto;
	private JLabel lblObg_precoVenda;
	private JCheckBox chckbxProdutoBloqueado;
	private Render_tabela_produtos render = new Render_tabela_produtos();
	private ConfiguracaoDAO conf_dao = new ConfiguracaoDAO();
	private Configuracoes configuracoes_do_sistema = conf_dao.busca_configuracoes();
	private JLabel lblF1;
	private JLabel lblNovo;
	private JLabel lblF12;
	private JLabel lblExcluir;
	private JLabel lblEsc;
	private JLabel lblCancelar;
	private JLabel lblF3;
	private JLabel lblEditar;
	private JLabel lblF5;
	private JLabel lblRecarregar;
	private JLabel lblF7;
	private JLabel lblSetores;

	/**
	 * Create the panel.
	 */
	public Panel_produtos() {
		setLayout(null);
		tecla_pressionada(); // Teclas de atalho.
		txtCodigo = new JTextField();
		txtCodigo.setEditable(false);
		txtCodigo.setFont(new Font("Tahoma", Font.PLAIN, 14));
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

		JTextFieldLimit limitDocument_nomeProduto = new JTextFieldLimit(49, "texto");
		fTxtNomeProduto.setDocument(limitDocument_nomeProduto);
		fTxtNomeProduto.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtNomeProduto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterNomeProduto) {
				if (!fTxtNomeProduto.getText().trim().isEmpty()
						&& enterNomeProduto.getKeyCode() == enterNomeProduto.VK_ENTER) {
					cbxSetor.requestFocus();
				}
			}
		});
		fTxtNomeProduto.setEditable(false);
		fTxtNomeProduto.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNomeProduto) {
				text_tools.move_cursor_inicio(fTxtNomeProduto);
			}
		});
		fTxtNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
		fTxtCodigoBarras.setToolTipText(
				"Ap\u00F3s salvar o produto, vincule um c\u00F3digo de barras selecionando o item e clicando no bot\u00E3o ao lado.");
		fTxtCodigoBarras.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterBarras) {
				if (enterBarras.getKeyCode() == enterBarras.VK_ENTER) {
					cbxFatorVenda.requestFocus();
				}
			}
		});
		fTxtCodigoBarras.setEditable(false);
		fTxtCodigoBarras.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCodigoBarras.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCodigoBarras.setBounds(557, 187, 117, 20);
		add(fTxtCodigoBarras);

		btnMaisSetor = new JButton();
		btnMaisSetor.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickMaisSetor) {
				if (btnMaisSetor.isEnabled()) {
					novo_setor();
				}
			}
		});
		btnMaisSetor.setIcon(icones.getIcone_mais());
		btnMaisSetor.setBounds(272, 188, 26, 22);
		add(btnMaisSetor);

		btnMaisBarras = new JButton();
		btnMaisBarras.setEnabled(false);
		btnMaisBarras.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickMaisBarras) {
				if (btnMaisBarras.isEnabled()) {
					VariosBarras varios_barras = new VariosBarras(txtCodigo.getText().trim(), fTxtCodigoBarras,
							fTxtNomeProduto.getText().trim());
					varios_barras.setLocationRelativeTo(btnMaisBarras);
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
		fTxtPrecoCusto.setEditable(false);
		fTxtPrecoCusto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaCusto) {
				calcula_sugerido();
				calcula_margem_praticada();

				if (digitaCusto.getKeyCode() == digitaCusto.VK_ENTER) {
					fTxtMargem.requestFocus();
				}
			}
		});
		fTxtPrecoCusto.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
		fTxtMargem.setEditable(false);
		fTxtMargem.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaMargem) {
				calcula_sugerido();

				if (digitaMargem.getKeyCode() == digitaMargem.VK_ENTER) {
					fTxtPrecoVenda.requestFocus();
				}
			}
		});
		fTxtMargem.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtMargem.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtMargem.setDocument(new FormataNumeral(6, 2));
		fTxtMargem.setBounds(259, 300, 57, 20);
		add(fTxtMargem);

		lblPrecoSugerido = new JLabel("Pr. Sugerido");
		lblPrecoSugerido.setForeground(new Color(255, 140, 0));
		lblPrecoSugerido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrecoSugerido.setBounds(353, 301, 77, 19);
		add(lblPrecoSugerido);

		fTxtPrecoSugerido = new JFormattedTextField();
		fTxtPrecoSugerido.setEditable(false);
		fTxtPrecoSugerido.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtPrecoSugerido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtPrecoSugerido.setDocument(new FormataNumeral(9, 2));
		fTxtPrecoSugerido.setBounds(432, 299, 77, 20);
		add(fTxtPrecoSugerido);

		lblPrVenda = new JLabel("Pr. Venda");
		lblPrVenda.setForeground(new Color(0, 100, 0));
		lblPrVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrVenda.setBounds(549, 301, 67, 19);
		add(lblPrVenda);

		fTxtPrecoVenda = new JFormattedTextField();
		fTxtPrecoVenda.setEditable(false);
		fTxtPrecoVenda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaPrecoVenda) {
				calcula_margem_praticada();

				if (digitaPrecoVenda.getKeyCode() == digitaPrecoVenda.VK_ENTER) {
					salvar_produto();
				}
			}
		});
		fTxtPrecoVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
		fTxtMargemPraticada.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtMargemPraticada.setEditable(false);
		fTxtMargemPraticada.setFocusLostBehavior(JFormattedTextField.PERSIST);
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
		tabelaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaProdutos.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tabelaProdutos.getTableHeader().setReorderingAllowed(false);
		tabelaProdutos.setAutoResizeMode(tabelaProdutos.AUTO_RESIZE_OFF);
		scrollPane = new JScrollPane(tabelaProdutos);
		scrollPane.setBounds(16, 483, 693, 140);
		add(scrollPane);

		recarregarTabela();

		tabelaProdutos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent selecaoLinhaTabela) {

				ListSelectionModel lsm = (ListSelectionModel) selecaoLinhaTabela.getSource();
				if (!lsm.isSelectionEmpty() && btnNovo.isVisible()) {

					int linha_selecionada = tabelaProdutos.getSelectedRow();

					txtCodigo.setText(tabelaProdutos.getValueAt(linha_selecionada, 0).toString());
					fTxtNomeProduto.setText(tabelaProdutos.getValueAt(linha_selecionada, 1).toString());
					cbxFatorVenda.getModel()
							.setSelectedItem(tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 2));
					cbxSetor.getModel().setSelectedItem(tabelaProdutos.getValueAt(linha_selecionada, 3));
					fTxtPrecoCusto
							.setText(tabelaProdutos.getValueAt(linha_selecionada, 4).toString().replace("R$ ", ""));
					fTxtMargem.setText(tabelaProdutos.getValueAt(linha_selecionada, 5).toString());
					fTxtPrecoSugerido
							.setText(tabelaProdutos.getValueAt(linha_selecionada, 6).toString().replace("R$ ", ""));
					fTxtPrecoVenda
							.setText(tabelaProdutos.getValueAt(linha_selecionada, 7).toString().replace("R$ ", ""));
					fTxtMargemPraticada.setText(tabelaProdutos.getValueAt(linha_selecionada, 8).toString());
					fTxtCodigoBarras.setText((String) tabelaProdutos.getValueAt(linha_selecionada, 9));

					Boolean bloq = ((Boolean) tabelaProdutos.getValueAt(linha_selecionada, 10));

					if (bloq) {
						chckbxProdutoBloqueado.setSelected(true);
					} else {
						chckbxProdutoBloqueado.setSelected(false);
					}

					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnMaisBarras.setEnabled(true);
				} else {
					btnEditar.setEnabled(false);
					btnExcluir.setEnabled(false);
					btnMaisBarras.setEnabled(false);
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
		fTxtPesquisa.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent ganhoFocoPesquisa) {
				text_tools.move_cursor_inicio(fTxtPesquisa);
			}
		});
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

					ProdutoDAO produto_dao = new ProdutoDAO();

					if (produto_dao.produto_tem_orcamento(
							(Integer) tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 0))) {
						JOptionPane.showMessageDialog(lblPrecoSugerido,
								"Impossível excluir produto.\nO produto selecionado está presente em 1 ou mais orçamentos.",
								"Exclusão de produtos", JOptionPane.WARNING_MESSAGE);
					} else {
						excluir_item();
					}
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

		cbxFatorVenda = new JComboBox<String>();
		cbxFatorVenda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterFator) {
				if (enterFator.getKeyCode() == enterFator.VK_ENTER) {
					fTxtPrecoCusto.requestFocus();
				}
			}
		});
		cbxFatorVenda.setEnabled(false);
		cbxFatorVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxFatorVenda
				.setModel(new DefaultComboBoxModel(new String[] { "UN", "PAR", "MT", "KG", "L", "CX", "FD", "PCT" }));
		cbxFatorVenda.setBounds(113, 231, 57, 22);
		add(cbxFatorVenda);
		cbxSetor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterSetor) {
				if (enterSetor.getKeyCode() == enterSetor.VK_ENTER) {
					cbxFatorVenda.requestFocus();
				}
			}
		});
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
		cbxSetor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxSetor.setBounds(59, 189, 210, 20);
		add(cbxSetor);

		lblObg_nomeProduto = new JLabel("*");
		lblObg_nomeProduto.setForeground(Color.RED);
		lblObg_nomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObg_nomeProduto.setBounds(710, 163, 20, 15);
		add(lblObg_nomeProduto);

		lblObg_precoVenda = new JLabel("*");
		lblObg_precoVenda.setForeground(Color.RED);
		lblObg_precoVenda.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObg_precoVenda.setBounds(709, 309, 20, 15);
		add(lblObg_precoVenda);

		chckbxProdutoBloqueado = new JCheckBox("Produto Bloqueado");
		chckbxProdutoBloqueado.setEnabled(false);
		chckbxProdutoBloqueado.setForeground(Color.BLACK);
		chckbxProdutoBloqueado.setFont(new Font("Tahoma", Font.PLAIN, 16));
		chckbxProdutoBloqueado.setBounds(537, 76, 172, 23);
		add(chckbxProdutoBloqueado);

		lblF1 = new JLabel("F1:");
		lblF1.setFont(new Font("Arial", Font.BOLD, 12));
		lblF1.setBounds(128, 629, 21, 14);
		add(lblF1);

		lblNovo = new JLabel("Novo");
		lblNovo.setForeground(Color.BLUE);
		lblNovo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNovo.setBounds(145, 629, 35, 14);
		add(lblNovo);

		lblF12 = new JLabel("F12:");
		lblF12.setFont(new Font("Arial", Font.BOLD, 12));
		lblF12.setBounds(642, 629, 26, 14);
		add(lblF12);

		lblExcluir = new JLabel("Excluir");
		lblExcluir.setForeground(new Color(255, 0, 0));
		lblExcluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblExcluir.setBounds(667, 629, 42, 14);
		add(lblExcluir);

		lblEsc = new JLabel("Esc:");
		lblEsc.setFont(new Font("Arial", Font.BOLD, 12));
		lblEsc.setBounds(16, 629, 30, 14);
		add(lblEsc);

		lblCancelar = new JLabel("Cancelar");
		lblCancelar.setForeground(Color.GRAY);
		lblCancelar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCancelar.setBounds(42, 629, 53, 14);
		add(lblCancelar);

		lblF3 = new JLabel("F3:");
		lblF3.setFont(new Font("Arial", Font.BOLD, 12));
		lblF3.setBounds(216, 629, 21, 14);
		add(lblF3);

		lblEditar = new JLabel("Editar");
		lblEditar.setForeground(new Color(139, 69, 19));
		lblEditar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEditar.setBounds(234, 629, 35, 14);
		add(lblEditar);

		lblF5 = new JLabel("F5:");
		lblF5.setFont(new Font("Arial", Font.BOLD, 12));
		lblF5.setBounds(322, 629, 21, 14);
		add(lblF5);

		lblRecarregar = new JLabel("Recarregar Produtos");
		lblRecarregar.setForeground(new Color(0, 128, 0));
		lblRecarregar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRecarregar.setBounds(339, 629, 128, 14);
		add(lblRecarregar);

		lblF7 = new JLabel("F7:");
		lblF7.setFont(new Font("Arial", Font.BOLD, 12));
		lblF7.setBounds(519, 629, 21, 14);
		add(lblF7);

		lblSetores = new JLabel("Setores");
		lblSetores.setForeground(new Color(255, 140, 0));
		lblSetores.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSetores.setBounds(537, 629, 48, 14);
		add(lblSetores);

	}

	// --------------Funções--------------

	public void ativarCampos() {
		fTxtMargem.setEditable(true);
		fTxtNomeProduto.setEditable(true);
		fTxtPrecoCusto.setEditable(true);
		fTxtPrecoVenda.setEditable(true);
		cbxFatorVenda.setEnabled(true);
		cbxSetor.setEnabled(true);
	}

	public void desativarCampos() {
		fTxtMargem.setEditable(false);
		fTxtNomeProduto.setEditable(false);
		fTxtPrecoCusto.setEditable(false);
		fTxtPrecoVenda.setEditable(false);
		cbxFatorVenda.setEnabled(false);
		cbxSetor.setEnabled(false);
		chckbxProdutoBloqueado.setEnabled(false);

		fTxtNomeProduto.setBorder(new LineBorder(Color.lightGray));
		fTxtPrecoVenda.setBorder(new LineBorder(Color.lightGray));
	}

	public void limparCampos() {
		txtCodigo.setText(null);
		fTxtCodigoBarras.setText(null);
		fTxtMargem.setText(null);
		fTxtNomeProduto.setValue(null);
		fTxtPrecoCusto.setText(null);
		fTxtPrecoVenda.setText(null);
		fTxtPrecoSugerido.setText(null);
		fTxtMargemPraticada.setText(null);
		cbxFatorVenda.setSelectedIndex(0);
		cbxSetor.setSelectedIndex(-1);
		tabelaProdutos.clearSelection();
		chckbxProdutoBloqueado.setSelected(false);
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
		chckbxProdutoBloqueado.setEnabled(true);
		fTxtNomeProduto.requestFocus();
	}

	public void cancelar_item() {
		if (!btnNovo.isVisible()) {
			desativarCampos();
			limparCampos();
			btnNovo.setVisible(true);
			btnSalvar.setVisible(false);
			btnCancelar.setVisible(false);
			btnEditar.setVisible(true);
			btnExcluir.setVisible(true);
			chckbxProdutoBloqueado.setEnabled(false);
			btnNovo.requestFocus();
		}
	}

	public void editar_item() {
		if (btnEditar.isEnabled()) {
			ativarCampos();
			btnEditar.setVisible(false);
			btnNovo.setVisible(false);
			btnExcluir.setVisible(false);
			btnSalvar.setVisible(true);
			btnCancelar.setVisible(true);
			fTxtNomeProduto.requestFocus();
			chckbxProdutoBloqueado.setEnabled(true);
		}
	}

	public void excluir_item() {
		if (tabelaProdutos.getSelectedRow() != -1) {
			try {
				ProdutoDAO produto_dao = new ProdutoDAO();
				boolean flag;

				int opcao = JOptionPane.showConfirmDialog(lblPrecoSugerido,
						"Deseja excluir o produto abaixo?\n" + "Cod = "
								+ tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 0) + "\n" + "Nome = "
								+ tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 1),
						"Exclusão de Produto", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

				flag = opcao == JOptionPane.YES_OPTION;

				if (flag) {
					produto_dao.deletarProduto((Integer) tabelaProdutos.getValueAt(tabelaProdutos.getSelectedRow(), 0));
					JOptionPane.showMessageDialog(lblPrecoSugerido, "Produto excluído!", "Exclusão de produto",
							JOptionPane.ERROR_MESSAGE);
					recarregarTabela();
					limparCampos();
					btnExcluir.setEnabled(false);
					btnEditar.setEnabled(false);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(btnExcluir, "Erro ao excluir produto!");
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
				Setor setor = new Setor(set.getCodSetor(), set.getNome());
				cbxSetor.addItem(setor);
			}

		} catch (Exception e1) {
			e1.getMessage();
		}
	}

	// Recarrega a tabela de produtos.
	public void recarregarTabela() {
		if (btnReload.isEnabled()) {
			if (!produtos.isEmpty()) {
				produtos.clear();
			}
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
					JOptionPane.showMessageDialog(fTxtNomeProduto, "Necessário informar o nome do produto!",
							"Produto sem nome.", JOptionPane.WARNING_MESSAGE);
					fTxtNomeProduto.requestFocus();
				}

				if (preco < 0.01) {
					fTxtPrecoVenda.setBorder(new LineBorder(Color.RED));
					JOptionPane.showMessageDialog(fTxtPrecoVenda, "Necessario informar preço de venda!",
							"Preço de venda zerado.", JOptionPane.WARNING_MESSAGE);
					fTxtMargem.requestFocus();

				}
			} else {
				gravarNovoProduto();
				desativarCampos();
				limparCampos();
			}
		}
	}

	public void novo_setor() {
		CadastroSetor cadastro_setor = new CadastroSetor(getPanelProdutos());
		cadastro_setor.setLocationRelativeTo(btnMaisSetor);
		cadastro_setor.setVisible(true);
	}

	public Produto_cadastro gravarNovoProduto() {

		ProdutoDAO produto_dao = new ProdutoDAO();
		NumberFormat nf = new DecimalFormat("#,###0.00");
		String descricao = fTxtNomeProduto.getText().trim();

		Object setor = null;
		if (cbxSetor.getSelectedIndex() != -1) {
			setor = (Setor) cbxSetor.getModel().getSelectedItem();
		}

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
		Boolean bloqueadoVenda = chckbxProdutoBloqueado.isSelected();
		Date dataCadastro = null;

		String codigo_barra = null;

		if (!fTxtCodigoBarras.getText().trim().isEmpty()) {
			codigo_barra = fTxtCodigoBarras.getText().trim();
		}

		Produto_cadastro produto = new Produto_cadastro(null, descricao, (Setor) setor, unidadeVenda, precoVenda,
				codigo_barra, dataCadastro, precoCusto, margem, prSugerido, margemPraticada, bloqueadoVenda);

		fTxtPrecoVenda.setBorder(new LineBorder(Color.LIGHT_GRAY));
		fTxtNomeProduto.setBorder(new LineBorder(Color.LIGHT_GRAY));

		try {
			// Testa se está alterando ou incluindo um novo item.
			if (txtCodigo.getText().trim().isEmpty()) {
				produto = produto_dao.inserirProduto(produto);

				if (produto.getIdProduto() != null) {

					JOptionPane.showMessageDialog(
							lblPrecoSugerido, "Produto cadastrado com sucesso! " + "\nCódigo: " + produto.getIdProduto()
									+ "\nProduto: " + produto.getDescricao(),
							"Cadastro de produtos", JOptionPane.NO_OPTION);

					Boolean flag = false;
					configuracoes_do_sistema = conf_dao.busca_configuracoes();

					switch (configuracoes_do_sistema.getVincula_barras()) {
					case "SIM":
						flag = true;
						break;
					case "NÃO":
						flag = false;
						break;
					case "PERGUNTAR":
						int opcao = JOptionPane.showConfirmDialog(lblPrecoSugerido,
								"Deseja vincular um código de barras ao produto cadastrado?",
								"Vinculação de código de barras", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
						flag = opcao == JOptionPane.YES_OPTION;
						break;
					}

					if (flag) {
						VariosBarras varios_barras = new VariosBarras(produto.getIdProduto().toString(),
								fTxtCodigoBarras, produto.getDescricao());
						varios_barras.setLocationRelativeTo(lblPrecoSugerido);
						varios_barras.setVisible(true);
					}

				}
				btnEditar.setVisible(true);
				btnExcluir.setVisible(true);
				btnNovo.setVisible(true);
				btnNovo.setEnabled(true);
				btnSalvar.setVisible(false);

				btnCancelar.setVisible(false);

				chckbxProdutoBloqueado.setEnabled(false);
				lblPesquisarPor.setEnabled(true);
				lblPesquisarPor.setVisible(true);
				fTxtPesquisa.setVisible(true);
				fTxtPesquisa.setEnabled(true);
				cbxTipoPesquisa.setVisible(true);
				cbxTipoPesquisa.setEnabled(true);

				fTxtCodigoBarras.setBorder(new LineBorder(Color.lightGray));
				recarregarTabela();
				return produto;

			} else {
				// Alterando cadastro do produto ja salvo.
				produto.setIdProduto(Integer.parseInt(txtCodigo.getText().trim()));
				if (produto_dao.alterarProduto(produto)) {
					JOptionPane.showMessageDialog(lblPrecoSugerido, "Produto alterado!", "Alteração de produto",
							JOptionPane.NO_OPTION);

					btnNovo.setEnabled(true);

					btnNovo.setVisible(true);
					lblPesquisarPor.setVisible(true);
					fTxtPesquisa.setVisible(true);
					cbxTipoPesquisa.setVisible(true);
					btnExcluir.setVisible(true);
					btnEditar.setVisible(true);

					btnCancelar.setVisible(false);
					btnSalvar.setVisible(false);

					fTxtCodigoBarras.setBorder(new LineBorder(Color.lightGray));
					recarregarTabela();
					return produto;
				} else {
					JOptionPane.showMessageDialog(lblPrecoSugerido, "Erro ao alterar produto!", "Alteração de produto",
							JOptionPane.WARNING_MESSAGE);
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
	public ArrayList<Produto_cadastro> alimentarListaProdutos(ArrayList<Produto_cadastro> produtos) {
		ProdutoDAO produto_dao = new ProdutoDAO();

		String pesquisado = fTxtPesquisa.getText().trim() + "%";

		if (fTxtPesquisa.getText().trim().isEmpty()) {
			produtos = produto_dao.listarTodosProdutos(produtos);
			return produtos;
		} else {
			switch (cbxTipoPesquisa.getSelectedItem().toString()) {
			case "Nome":
				produtos = produto_dao.listarProdutosNome(produtos, pesquisado, 50);
				break;
			case "Código":
				produtos = produto_dao.listarProdutosCodigo(produtos, pesquisado, 50);
				break;
			case "Cod. Barras":
				produtos = produto_dao.listarProdutosBarras(produtos, pesquisado, 50);
			}

			return produtos;
		}

	}

	public void ConfiguraLarguraColunaTabela(JTable tabelaProdutos) {

		SorterMonetario spv = new SorterMonetario();
		SorterData sorter_data = new SorterData();
		tabelaProdutos.getColumnModel().getColumn(0).setPreferredWidth(40); // Código
		tabelaProdutos.getColumnModel().getColumn(1).setPreferredWidth(160); // Nome
		tabelaProdutos.getColumnModel().getColumn(2).setPreferredWidth(45); // Fator
		tabelaProdutos.getColumnModel().getColumn(3).setPreferredWidth(115); // Setor
		tabelaProdutos.getColumnModel().getColumn(4).setPreferredWidth(70); // Preco Custo
		tabelaProdutos.getColumnModel().getColumn(5).setPreferredWidth(52); // Margem
		tabelaProdutos.getColumnModel().getColumn(6).setPreferredWidth(70); // Preco Sugerido
		tabelaProdutos.getColumnModel().getColumn(7).setPreferredWidth(75); // Preco Venda
		tabelaProdutos.getColumnModel().getColumn(8).setPreferredWidth(52); // Margem Praticada
		tabelaProdutos.getColumnModel().getColumn(9).setPreferredWidth(110); // Código Barras
		tabelaProdutos.getColumnModel().getColumn(10).setPreferredWidth(40); // Bloqueado
		tabelaProdutos.getColumnModel().getColumn(11).setPreferredWidth(80); // Data Cadastro
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelo);
		sorter.setComparator(4, spv);
		sorter.setComparator(6, spv);
		sorter.setComparator(7, spv);
		sorter.setComparator(11, sorter_data);

		tabelaProdutos.setRowSorter(sorter);
		// Pintando linhas de acordo com o status do cliente(bloqueado)
		tabelaProdutos.setDefaultRenderer(Object.class, render);
		tabelaProdutos.getColumnModel().getColumn(0).setCellRenderer(render);
		tabelaProdutos.getColumnModel().getColumn(4).setCellRenderer(render);
		tabelaProdutos.getColumnModel().getColumn(5).setCellRenderer(render);
		tabelaProdutos.getColumnModel().getColumn(6).setCellRenderer(render);
		tabelaProdutos.getColumnModel().getColumn(7).setCellRenderer(render);
		tabelaProdutos.getColumnModel().getColumn(8).setCellRenderer(render);
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
		if (!btnNovo.isVisible()) {
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

	// Teclas de atalho
	public void tecla_pressionada() {
		InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "novo");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelar");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "excluir");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "editar");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "recarregar");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0), "setores");

		setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);

		getActionMap().put("cancelar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_cancelar) {
				cancelar_item();
			}
		});

		getActionMap().put("excluir", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_excluir) {
				excluir_item();
			}
		});

		getActionMap().put("novo", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_novo) {
				novo_item();
			}
		});

		getActionMap().put("editar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_editar) {
				editar_item();
			}
		});

		getActionMap().put("recarregar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_recarregar) {
				recarregarTabela();
			}
		});
		getActionMap().put("setores", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_setores) {
				novo_setor();
			}
		});

	}

	public Panel_produtos getPanelProdutos() {
		return this;
	}
}
