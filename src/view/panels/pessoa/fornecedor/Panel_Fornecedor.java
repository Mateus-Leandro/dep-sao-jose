package view.panels.pessoa.fornecedor;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import entities.pessoa.Fornecedor;
import entities.pessoa.Pessoa;
import entities.produto.Produto_cadastro;
import tables.tableModels.ModeloTabelaFornecedores;
import tables.tableRenders.Render_tabela_fornecedores;
import tables.tableSorters.SorterData;
import tools.Jlist_tools;
import view.panels.pessoa.Panel_pessoa;

public class Panel_Fornecedor extends Panel_pessoa {
	private JLabel lblCadastroDeFornecedor;
	private JComboBox<String> cbxTipoPesquisa;
	private JCheckBox checkBoxBloqueadoPedido;
	private JCheckBox checkBoxBloqueadoCotacao;
	private JLabel lblFornecedoresCadastrados;
	private ArrayList<Fornecedor> lista_fornecedores = new ArrayList<Fornecedor>();
	private JScrollPane scrollPane;
	private ModeloTabelaFornecedores modelo_tabela;
	private JTable tabela = new JTable(modelo_tabela);
	private Fornecedor fornecedor = new Fornecedor();
	protected Render_tabela_fornecedores render = new Render_tabela_fornecedores();
	private Jlist_tools jlist_tools = new Jlist_tools();
	private DefaultListModel<Fornecedor> list_model_fornecedores = new DefaultListModel<Fornecedor>();
	private JList<Fornecedor> ltFornecedores;

	public Panel_Fornecedor() {
		fTxtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterEmail) {
				if (enterEmail.getKeyCode() == enterEmail.VK_ENTER) {
					salvar_fornecedor();
				}
			}
		});
		btnReload.setLocation(969, 411);
		lblRecarregar.setLocation(735, 629);
		tecla_pressionada(fornecedor);
		fTxtPesquisa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaPesquisaFornecedor) {
				recarregarTabela();
			}
		});
		btnExcluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickExcluirFornecedor) {
				if (excluir_pessoa(monta_pessoa(fornecedor))) {
					recarregarTabela();
				}
			}
		});
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEditarFornecedor) {
				if (btnEditar.isEnabled()) {
					editar_pessoa();
					if (checkBoxBloqueadoPedido.isSelected()) {
						checkBoxBloqueadoCotacao.setEnabled(false);
					} else {
						checkBoxBloqueadoCotacao.setEnabled(true);
					}
					checkBoxBloqueadoPedido.setEnabled(true);
				}
			}
		});
		separador_3.setBounds(664, 392, 339, 9);
		separador_2.setBounds(16, 392, 375, 9);
		fTxtPesquisa.setBounds(211, 413, 748, 20);
		setLayout(null);

		lblCadastroDeFornecedor = new JLabel("Cadastro de Fornecedores");
		lblCadastroDeFornecedor.setBounds(375, 11, 324, 29);
		lblCadastroDeFornecedor.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastroDeFornecedor.setFont(new Font("Tahoma", Font.BOLD, 24));
		add(lblCadastroDeFornecedor);

		cbxTipoPesquisa = new JComboBox<String>();
		cbxTipoPesquisa.setBounds(105, 411, 96, 25);
		cbxTipoPesquisa.setModel(new DefaultComboBoxModel(new String[] { "Nome", "Nome Fant.", "Cod." }));
		cbxTipoPesquisa.setSelectedIndex(0);
		cbxTipoPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(cbxTipoPesquisa);

		checkBoxBloqueadoPedido = new JCheckBox("Bloqueado p/ pedido");
		checkBoxBloqueadoPedido.setBounds(617, 76, 183, 23);
		checkBoxBloqueadoPedido.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent clickBloqueadoPedido) {
				if (checkBoxBloqueadoPedido.isSelected()) {
					checkBoxBloqueadoPedido.setForeground(Color.red);

					checkBoxBloqueadoCotacao.setSelected(true);
					checkBoxBloqueadoCotacao.setEnabled(false);
				} else {
					checkBoxBloqueadoPedido.setForeground(Color.black);
					if (!btnNovo.isEnabled()) {
						checkBoxBloqueadoCotacao.setEnabled(true);
					}
				}
			}
		});
		checkBoxBloqueadoPedido.setFont(new Font("Tahoma", Font.PLAIN, 16));
		checkBoxBloqueadoPedido.setEnabled(false);
		add(checkBoxBloqueadoPedido);

		checkBoxBloqueadoCotacao = new JCheckBox("Bloqueado p/ cota\u00E7\u00E3o");
		checkBoxBloqueadoCotacao.setBounds(825, 76, 183, 23);
		checkBoxBloqueadoCotacao.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent clickBloqueadoCotacao) {
				if (checkBoxBloqueadoCotacao.isSelected()) {
					checkBoxBloqueadoCotacao.setForeground(new Color(128, 0, 128));
				} else {
					checkBoxBloqueadoCotacao.setForeground(Color.black);
				}
			}
		});
		checkBoxBloqueadoCotacao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		checkBoxBloqueadoCotacao.setEnabled(false);
		add(checkBoxBloqueadoCotacao);

		lblFornecedoresCadastrados = new JLabel("Fornecedores Cadastrados");
		lblFornecedoresCadastrados.setBounds(391, 376, 274, 29);
		lblFornecedoresCadastrados.setHorizontalAlignment(SwingConstants.CENTER);
		lblFornecedoresCadastrados.setFont(new Font("Tahoma", Font.BOLD, 20));
		add(lblFornecedoresCadastrados);

		alimentarListaFornecedores();
		modelo_tabela = new ModeloTabelaFornecedores(lista_fornecedores);
		tabela.setModel(modelo_tabela);
		tabela.setBounds(16, 487, 692, 131);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getTableHeader().setReorderingAllowed(false);
		tabela.setAutoResizeMode(tabela.AUTO_RESIZE_OFF);
		ConfiguraLarguraColunaTabela(tabela);

		tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent selecaoLinhaTabela) {
				ListSelectionModel lsm = (ListSelectionModel) selecaoLinhaTabela.getSource();
				if (!lsm.isSelectionEmpty() && btnNovo.isEnabled()) {
					int linha_selecionada = tabela.getSelectedRow();
					selecao_linha_tabela(tabela, linha_selecionada);

					if ((Boolean) tabela.getValueAt(linha_selecionada, 1)) {
						checkBoxBloqueadoPedido.setSelected(true);
						checkBoxBloqueadoCotacao.setSelected(true);
					} else {
						checkBoxBloqueadoPedido.setSelected(false);
						if ((Boolean) tabela.getValueAt(linha_selecionada, 2)) {
							checkBoxBloqueadoCotacao.setSelected(true);
						} else {
							checkBoxBloqueadoCotacao.setSelected(false);
						}
					}

				} else {
					btnEditar.setEnabled(false);
					btnExcluir.setEnabled(false);
					tabela.clearSelection();
				}
			}
		});

		scrollPane = new JScrollPane(tabela);
		scrollPane.setBounds(16, 444, 987, 181);
		add(scrollPane);

		btnNovo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNovoCliente) {
				nova_pessoa(tabela);
				checkBoxBloqueadoPedido.setEnabled(true);
				checkBoxBloqueadoCotacao.setEnabled(true);
			}
		});

		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNovoCliente) {
				cancelar_pessoa();
				checkBoxBloqueadoPedido.setEnabled(false);
				checkBoxBloqueadoCotacao.setEnabled(false);

				checkBoxBloqueadoCotacao.setSelected(false);
				checkBoxBloqueadoPedido.setSelected(false);
				tabela.clearSelection();
			}
		});

		btnSalvar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickSalvarFornecedor) {
				fornecedor = (Fornecedor) monta_pessoa(fornecedor);
				fornecedor.setBloqueado_cotacao(checkBoxBloqueadoCotacao.isSelected());
				fornecedor.setBloqueado(checkBoxBloqueadoPedido.isSelected());

				if (valida_pessoa(fornecedor)) {
					if (salvar_pessoa(fornecedor)) {
						checkBoxBloqueadoCotacao.setEnabled(false);
						checkBoxBloqueadoPedido.setEnabled(false);

						checkBoxBloqueadoCotacao.setSelected(false);
						checkBoxBloqueadoPedido.setSelected(false);

						recarregarTabela();
					}
				}
			}
		});
		lblEsc.setBounds(16, 629, 30, 14);
		lblCancelar.setBounds(42, 629, 53, 14);
		lblF1.setBounds(237, 629, 21, 14);
		lblNovo.setBounds(254, 629, 35, 14);
		lblF5.setBounds(717, 629, 21, 14);
		lblF3.setBounds(441, 629, 21, 14);
		lblEditar.setBounds(459, 629, 35, 14);
		lblF12.setBounds(936, 629, 26, 14);
		lblExcluir.setBounds(961, 629, 42, 14);

	}

	public void alimentarListaFornecedores() {
		String pesquisado = null;
		lista_fornecedores.clear();
		if (fTxtPesquisa.getText().isBlank()) {
			lista_fornecedores = fornecedor_dao.listarFornecedores(lista_fornecedores, null, null, 1000);
		} else {
			pesquisado = fTxtPesquisa.getText().trim() + "%";
			lista_fornecedores = fornecedor_dao.listarFornecedores(lista_fornecedores,
					cbxTipoPesquisa.getSelectedItem().toString(), pesquisado, 50);
		}
	}

	public void recarregarTabela() {
		jlist_tools.alimentar_lista_fornecedores(cbxTipoPesquisa.getSelectedItem().toString(),
				fTxtPesquisa.getText().trim(), lista_fornecedores, list_model_fornecedores, scrollPane, ltFornecedores);
		ConfiguraLarguraColunaTabela(tabela);
		modelo_tabela.fireTableDataChanged();
	}

	@Override
	public void selecao_linha_tabela(JTable tabela, int linha_selecionada) {
		btnEditar.setEnabled(true);
		btnExcluir.setEnabled(true);

		if (tabela.getValueAt(tabela.getSelectedRow(), 12) != null) {
			// Verificando se o documento do cliente é um CPF ou CNPJ
			if (tabela.getValueAt(tabela.getSelectedRow(), 11).toString().length() > 14) {
				checkBoxJuridica.setSelected(true);
				fTxtIe.setVisible(true);
				lblIe.setVisible(true);
			} else {
				checkBoxJuridica.setSelected(false);
				fTxtIe.setVisible(false);
				lblIe.setVisible(false);
			}

		}
		fTxtDocumento.setText((String) tabela.getValueAt(linha_selecionada, 12));
		fTxtIe.setText((String) tabela.getValueAt(linha_selecionada, 13));
		fTxtNomePessoa.setText((String) tabela.getValueAt(linha_selecionada, 3));
		fTxtApelido.setText((String) tabela.getValueAt(linha_selecionada, 4));
		fTxtCep.setText((String) tabela.getValueAt(linha_selecionada, 11));
		fTxtCidade.setText((String) tabela.getValueAt(linha_selecionada, 9));
		fTxtEndereco.setText((String) tabela.getValueAt(linha_selecionada, 6));
		fTxtNumero.setText((String) tabela.getValueAt(linha_selecionada, 7));
		fTxtReferencia.setText((String) tabela.getValueAt(linha_selecionada, 8));
		fTxtBairro.setText((String) tabela.getValueAt(linha_selecionada, 10));
		fTxtCelular.setText((String) tabela.getValueAt(linha_selecionada, 5));
		fTxtTelFixo.setText((String) tabela.getValueAt(linha_selecionada, 15));
		fTxtEmail.setText((String) tabela.getValueAt(linha_selecionada, 14));

		String cod = tabela.getValueAt(linha_selecionada, 0).toString();
		txtCodigo.setText(cod);
	}

	// Configurando largura das colunas da tabela
	public void ConfiguraLarguraColunaTabela(JTable tabela) {
		SorterData sorter_data = new SorterData();

		tabela.getColumnModel().getColumn(0).setPreferredWidth(50); // Codigo
		tabela.getColumnModel().getColumn(1).setPreferredWidth(70); // Bloqueado
		tabela.getColumnModel().getColumn(2).setPreferredWidth(70); // Bloqueado
		tabela.getColumnModel().getColumn(3).setPreferredWidth(190); // Nome
		tabela.getColumnModel().getColumn(4).setPreferredWidth(170); // Apelido
		tabela.getColumnModel().getColumn(5).setPreferredWidth(100); // Celular
		tabela.getColumnModel().getColumn(6).setPreferredWidth(180); // Endereco
		tabela.getColumnModel().getColumn(7).setPreferredWidth(70); // Numero casa
		tabela.getColumnModel().getColumn(8).setPreferredWidth(180); // Referencia
		tabela.getColumnModel().getColumn(9).setPreferredWidth(160); // Cidade
		tabela.getColumnModel().getColumn(10).setPreferredWidth(160); // Bairro
		tabela.getColumnModel().getColumn(11).setPreferredWidth(80); // Cep
		tabela.getColumnModel().getColumn(12).setPreferredWidth(80); // Documento
		tabela.getColumnModel().getColumn(13).setPreferredWidth(80); // I.E.
		tabela.getColumnModel().getColumn(14).setPreferredWidth(200); // Email
		tabela.getColumnModel().getColumn(15).setPreferredWidth(90); // Telefone
		tabela.getColumnModel().getColumn(16).setPreferredWidth(70); // Data Cadastro

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelo_tabela);
		sorter.setComparator(10, sorter_data);
		tabela.setRowSorter(sorter);

		tabela.setDefaultRenderer(Object.class, render);
		tabela.getColumnModel().getColumn(0).setCellRenderer(render);
	}

	public boolean salvar_fornecedor() {
		fornecedor = (Fornecedor) monta_pessoa(fornecedor);
		fornecedor.setBloqueado_cotacao(checkBoxBloqueadoCotacao.isSelected());
		fornecedor.setBloqueado(checkBoxBloqueadoPedido.isSelected());

		if (valida_pessoa(fornecedor)) {
			if (salvar_pessoa(fornecedor)) {
				checkBoxBloqueadoCotacao.setEnabled(false);
				checkBoxBloqueadoPedido.setEnabled(false);

				checkBoxBloqueadoCotacao.setSelected(false);
				checkBoxBloqueadoPedido.setSelected(false);

				recarregarTabela();
				return true;
			}
		}

		return false;
	}

	// Teclas de atalho
	public void tecla_pressionada(Pessoa pessoa_atalho) {
		super.tecla_pressionada(pessoa_atalho, tabela);
		InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "excluir");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "atualizar");

		getActionMap().put("excluir", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent atalho_excluir) {
				if (excluir_pessoa(pessoa_atalho)) {
					recarregarTabela();
				}
			}
		});

		getActionMap().put("atualizar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent atalho_atualizar) {
				btnReload.doClick();
				fTxtPesquisa.setText(null);
				recarregarTabela();
			}
		});
	}
}
