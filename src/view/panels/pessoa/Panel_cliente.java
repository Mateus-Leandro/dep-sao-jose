package view.panels.pessoa;

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
import javax.swing.InputMap;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import dao.configuracoes.ConfiguracaoDAO;
import dao.pessoa.ClienteDAO;
import entities.configuracoes.Configuracoes;
import entities.pessoa.Cliente;
import entities.pessoa.Pessoa;
import tables.tableModels.ModeloTabelaClientes;
import tables.tableRenders.Render_tabela_clientes;
import tables.tableSorters.SorterData;
import tools.Jtext_tools;

public class Panel_cliente extends Panel_pessoa {
	protected JLabel lblTitulo;
	protected JComboBox<String> cbxTipoPesquisa;
	protected ArrayList<Cliente> lista_clientes = new ArrayList<Cliente>();
	protected Jtext_tools text_tools = new Jtext_tools();
	protected ClienteDAO cliente_dao = new ClienteDAO();
	protected Cliente cliente = new Cliente();
	protected Render_tabela_clientes render = new Render_tabela_clientes();
	protected ConfiguracaoDAO conf_dao = new ConfiguracaoDAO();
	protected Configuracoes configuracoes = conf_dao.busca_configuracoes();
	private Pessoa novo_cliente = new Cliente();
	private JScrollPane scrollPaneTabela;
	private JTable tabela;
	private JCheckBox checkBoxBloqueado;
	private JLabel lblClientesCadastrados;

	/**
	 * Create the panel.
	 */
	public Panel_cliente() {
		fTxtPesquisa.setBounds(211, 411, 748, 20);
		btnReload.setLocation(969, 409);
		lblNovo.setLocation(228, 629);
		lblF1.setLocation(211, 629);
		lblEditar.setLocation(456, 629);
		lblF3.setLocation(438, 629);
		lblRecarregar.setLocation(694, 629);
		lblF5.setLocation(677, 629);
		lblExcluir.setLocation(961, 629);
		lblF12.setLocation(936, 629);
		separador_3.setBounds(629, 392, 374, 9);
		separador_2.setBounds(16, 392, 379, 9);
		checkBoxBloqueado = new JCheckBox("Cliente Bloqueado");
		checkBoxBloqueado.setEnabled(false);
		checkBoxBloqueado.setFont(new Font("Tahoma", Font.PLAIN, 16));
		checkBoxBloqueado.setBounds(844, 73, 159, 23);
		add(checkBoxBloqueado);
		checkBoxBloqueado.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent clickClienteBloqueado) {
				if (checkBoxBloqueado.isSelected()) {
					checkBoxBloqueado.setForeground(Color.red);
				} else {
					checkBoxBloqueado.setForeground(Color.black);
				}
			}
		});
		btnReload.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickRecarregarCliente) {
				fTxtPesquisa.setText(null);
				recarregarTabela();
			}
		});
		fTxtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterEmail) {
				if (enterEmail.getKeyCode() == enterEmail.VK_ENTER) {
					if (valida_documento("cliente")) {
						novo_cliente = new Cliente();
						if (valida_pessoa(novo_cliente)) {
							novo_cliente = monta_pessoa(novo_cliente);
							novo_cliente.setBloqueado(checkBoxBloqueado.isSelected());
							if (salvar_pessoa(novo_cliente)) {
								recarregarTabela();
							}
						}
					}
				}
			}
		});
		fTxtPesquisa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaPesquisa) {
				recarregarTabela();
			}
		});
		btnExcluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickExcluirCliente) {
				if (excluir_pessoa(cliente)) {
					recarregarTabela();
				}
			}
		});
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEditarCliente) {
				editar_pessoa();
			}
		});
		btnNovo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNovoCliente) {
				nova_pessoa(tabela);
			}
		});

		alimentarListaClientes();
		modelo_tabela = new ModeloTabelaClientes(lista_clientes);
		tabela = new JTable(modelo_tabela);

		// Atalhos do teclado
		tecla_pressionada(novo_cliente, tabela);

		lblTitulo = new JLabel("T\u00EDtulo da tela");
		lblTitulo.setBounds(393, 11, 244, 29);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
		add(lblTitulo);
		lblTitulo.setText("Cadastro de Clientes");

		cbxTipoPesquisa = new JComboBox();
		cbxTipoPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxTipoPesquisa.setModel(new DefaultComboBoxModel<String>(new String[] { "Nome", "Apelido", "C\u00F3digo" }));
		cbxTipoPesquisa.setSelectedIndex(0);
		cbxTipoPesquisa.setBounds(105, 408, 96, 25);
		add(cbxTipoPesquisa);

		setBorder(null);
		setLayout(null);

		btnSalvar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickSalvarCliente) {
				if (valida_documento("cliente")) {
					novo_cliente = new Cliente();
					if (valida_pessoa(novo_cliente)) {
						novo_cliente = monta_pessoa(novo_cliente);
						novo_cliente.setBloqueado(checkBoxBloqueado.isSelected());
						if (salvar_pessoa(novo_cliente)) {
							recarregarTabela();
						}
					}
				}
			}
		});

		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancelarCliente) {
				cancelar_pessoa();
				checkBoxBloqueado.setEnabled(false);
				tabela.clearSelection();
			}
		});

		btnLimpaCep.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent limpa_cep) {
				fTxtBairro.setText(null);
				fTxtCidade.setText(null);
				fTxtCep.setText(null);
				fTxtEndereco.setText(null);
				fTxtReferencia.setText(null);
				fTxtNumero.setText(null);
				btnLimpaCep.setVisible(false);
			}
		});

		tabela.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tabela.setSurrendersFocusOnKeystroke(true);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabela.getTableHeader().setReorderingAllowed(false);
		tabela.setAutoResizeMode(tabela.AUTO_RESIZE_OFF);
		tabela.setBounds(14, 325, 694, 216);
		tabela.setDefaultRenderer(Object.class, render);

		ConfiguraLarguraColunaTabela(tabela);

		scrollPaneTabela = new JScrollPane(tabela);
		scrollPaneTabela.setBounds(16, 442, 987, 181);
		add(scrollPaneTabela);

		lblClientesCadastrados = new JLabel("Clientes Cadastrados");
		lblClientesCadastrados.setHorizontalAlignment(SwingConstants.CENTER);
		lblClientesCadastrados.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblClientesCadastrados.setBounds(405, 378, 214, 29);
		add(lblClientesCadastrados);

		tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent selecaoLinhaTabela) {
				ListSelectionModel lsm = (ListSelectionModel) selecaoLinhaTabela.getSource();
				if (!lsm.isSelectionEmpty() && btnNovo.isEnabled()) {
					int linha_selecionada = tabela.getSelectedRow();
					selecao_linha_tabela(tabela, linha_selecionada);

					if ((Boolean) tabela.getValueAt(linha_selecionada, 1)) {
						checkBoxBloqueado.setSelected(true);
					} else {
						checkBoxBloqueado.setSelected(false);
					}
				} else {
					btnEditar.setEnabled(false);
					btnExcluir.setEnabled(false);
					tabela.clearSelection();
				}
			}
		});

	}

	public void alimentarListaClientes() {
		String pesquisado = null;
		lista_clientes.clear();
		if (fTxtPesquisa.getText().isBlank()) {
			lista_clientes = cliente_dao.listarClientes(lista_clientes, null, null, 1000);
		} else {
			pesquisado = fTxtPesquisa.getText().trim() + "%";
			lista_clientes = cliente_dao.listarClientes(lista_clientes, cbxTipoPesquisa.getSelectedItem().toString(),
					pesquisado, 50);
		}
	}

	public void recarregarTabela() {
		alimentarListaClientes();
		modelo_tabela = new ModeloTabelaClientes(lista_clientes);
		tabela.setModel(modelo_tabela);
		ConfiguraLarguraColunaTabela(tabela);
		modelo_tabela.fireTableDataChanged();
	}

	@Override
	public void ativar_campos() {
		super.ativar_campos();
		checkBoxBloqueado.setEnabled(true);
	}

	@Override
	public void desativar_campos() {
		super.desativar_campos();
		checkBoxBloqueado.setEnabled(false);
	}

	@Override
	public void limpar_campos() {
		super.limpar_campos();
		checkBoxBloqueado.setSelected(false);
	}

	// Configurando largura das colunas da tabela
	public void ConfiguraLarguraColunaTabela(JTable tabelaProdutos) {
		SorterData sorter_data = new SorterData();
		tabela.getColumnModel().getColumn(0).setPreferredWidth(40); // Codigo
		tabela.getColumnModel().getColumn(1).setPreferredWidth(50); // Bloqueado
		tabela.getColumnModel().getColumn(2).setPreferredWidth(150); // Nome
		tabela.getColumnModel().getColumn(3).setPreferredWidth(150); // Apelido
		tabela.getColumnModel().getColumn(4).setPreferredWidth(100); // Celular
		tabela.getColumnModel().getColumn(5).setPreferredWidth(180); // Endereco
		tabela.getColumnModel().getColumn(6).setPreferredWidth(70); // Numero casa
		tabela.getColumnModel().getColumn(7).setPreferredWidth(180); // Referencia
		tabela.getColumnModel().getColumn(8).setPreferredWidth(160); // Cidade
		tabela.getColumnModel().getColumn(9).setPreferredWidth(160); // Bairro
		tabela.getColumnModel().getColumn(10).setPreferredWidth(80); // Cep
		tabela.getColumnModel().getColumn(11).setPreferredWidth(80); // Documento
		tabela.getColumnModel().getColumn(12).setPreferredWidth(80); // I.E.
		tabela.getColumnModel().getColumn(13).setPreferredWidth(150); // Email
		tabela.getColumnModel().getColumn(14).setPreferredWidth(90); // Telefone
		tabela.getColumnModel().getColumn(15).setPreferredWidth(70); // Data Cadastro

		// Definindo o sorter da tabela para ordenação das colunas.
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelo_tabela);
		tabela.setRowSorter(sorter);
		sorter.setComparator(15, sorter_data);
		// Definindo o render da coluna para que seja pintada corretamente quando o
		// cliente está bloqueado.
		tabela.getColumnModel().getColumn(0).setCellRenderer(render);
	}

	// Teclas de atalho.
	@Override
	public void tecla_pressionada(Pessoa pessoa_atalho, JTable tabela) {
		super.tecla_pressionada(pessoa_atalho, tabela);
		InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "excluir");

		getActionMap().put("excluir", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_excluir) {
				if (excluir_pessoa(pessoa_atalho)) {
					recarregarTabela();
				}
			}
		});

	}
}
