package view.dialog;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import dao.ClienteDAO;
import dao.OrcamentoDAO;
import entities.cliente.Cliente;
import entities.orcamentos.Orcamento;
import entities.orcamentos.Produto_Orcamento;
import icons.Icones;
import tables.tableModels.ModeloTabelaOrcamentos;
import tables.tableSorters.SorterMonetario;
import view.panels.Panel_orc_vend;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Orcamentos_do_cliente extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblOrcamentos_do_cliente;
	private JSeparator separador_orcamentos;
	private JScrollPane scrollPaneOrcamentos;
	private JTable tabelaOrcamentos;
	private JLabel lblPesquisarPorOrcamento;
	private JComboBox<String> cbxTipoPesquisaOrcamento;
	private JFormattedTextField fTxtPesquisaOrcamento;
	private DefaultListModel<Cliente> list_model_clientes = new DefaultListModel<Cliente>();
	private ArrayList<Cliente> lista_clientes = new ArrayList<Cliente>();
	private ArrayList<Orcamento> orcamentos_cliente = new ArrayList<Orcamento>();
	private ModeloTabelaOrcamentos modelo_tabela_orcamentos = new ModeloTabelaOrcamentos(orcamentos_cliente);
	private JLabel lblCliente;
	private JSeparator separador_cliente;
	private JComboBox<String> cbxTipoPesquisaCliente;
	private JLabel lblPesquisarPorCliente;
	private JFormattedTextField fTxtPesquisaCliente;
	private JSeparator separador_orcamento;
	private JLabel lblOrcamento;
	private JCheckBox checkBoxFaturado;
	private JList<Cliente> ltClientes;
	private JScrollPane scrollPaneLtClientes;
	private Cliente cliente_selecionado;
	private ArrayList<Produto_Orcamento> produtos_do_orcamento = new ArrayList<Produto_Orcamento>();
	private JSeparator separador_orcamentos1;
	private JLabel lblOrcamentosDoCliente;
	private JSeparator separador_orcamentos2;
	private JFormattedTextField formattedTextField;
	private JLabel lblObservacoes;
	private JSeparator separador_observacoes;
	private ListSelectionModel lsm;
	private SorterMonetario spv = new SorterMonetario();
	private Orcamento orcamento_selecionado = new Orcamento();
	private JButton btnExcluirOrcamento;
	private Icones icones = new Icones();
	private JButton btnEditarOrcamento;
	private NumberFormat nf = new DecimalFormat("R$ ,##0.00");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Orcamentos_do_cliente frame = new Orcamentos_do_cliente(null);
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
	public Orcamentos_do_cliente(Panel_orc_vend panel_orcamento) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent fechamentoDaJanela) {
				panel_orcamento.limpar_campos();
			}
		});
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 766, 445);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setModal(true);

		ltClientes = new JList<Cliente>();
		ltClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickListaClientes) {

				scrollPaneLtClientes.setVisible(false);
				cliente_selecionado = ltClientes.getSelectedValue();
				fTxtPesquisaCliente.setText(cliente_selecionado.getNome());

				alimentar_lista_orcamento(orcamentos_cliente);
			}
		});
		ltClientes.setBounds(197, 104, 514, 70);

		scrollPaneLtClientes = new JScrollPane(ltClientes);
		scrollPaneLtClientes.setBounds(198, 96, 514, 58);
		scrollPaneLtClientes.setVisible(false);
		contentPane.setLayout(null);
		contentPane.add(scrollPaneLtClientes);

		lblOrcamentos_do_cliente = new JLabel("Or\u00E7amentos do cliente");
		lblOrcamentos_do_cliente.setBounds(228, 11, 287, 29);
		lblOrcamentos_do_cliente.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrcamentos_do_cliente.setFont(new Font("Tahoma", Font.BOLD, 24));
		contentPane.add(lblOrcamentos_do_cliente);

		separador_orcamentos = new JSeparator();
		separador_orcamentos.setBounds(10, 50, 727, 9);
		contentPane.add(separador_orcamentos);

		tabelaOrcamentos = new JTable(modelo_tabela_orcamentos);
		tabelaOrcamentos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tabelaOrcamentos.setBounds(10, 87, 331, 185);
		tabelaOrcamentos.getTableHeader().setReorderingAllowed(false);
		tabelaOrcamentos.setAutoResizeMode(tabelaOrcamentos.AUTO_RESIZE_OFF);
		ConfiguraLarguraColunaTabelaOrcamento(tabelaOrcamentos);
		tabelaOrcamentos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent selecaoLinhaTabela) {

				lsm = (ListSelectionModel) selecaoLinhaTabela.getSource();
				if (!lsm.isSelectionEmpty()) {

					int linha_selecionada = tabelaOrcamentos.getSelectedRow();
					Integer id_orcamento_selecionado = (Integer) tabelaOrcamentos.getValueAt(linha_selecionada, 0);
					orcamento_selecionado = busca_orcamento_selecionado(id_orcamento_selecionado);
					panel_orcamento.lista_produtos_do_orcamento_selecionado(orcamento_selecionado);
					panel_orcamento.setNumeroOrcamento(orcamento_selecionado);

				}
			}
		});

		scrollPaneOrcamentos = new JScrollPane(tabelaOrcamentos);
		scrollPaneOrcamentos.setBounds(12, 205, 725, 111);
		contentPane.add(scrollPaneOrcamentos);

		lblPesquisarPorOrcamento = new JLabel("Pesquisar por");
		lblPesquisarPorOrcamento.setBounds(10, 128, 89, 20);
		lblPesquisarPorOrcamento.setToolTipText("");
		lblPesquisarPorOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblPesquisarPorOrcamento);

		cbxTipoPesquisaOrcamento = new JComboBox<String>();
		cbxTipoPesquisaOrcamento.setBounds(97, 127, 97, 22);
		cbxTipoPesquisaOrcamento.setModel(new DefaultComboBoxModel<String>(
				new String[] { "N\u00FAmero", "Valor Total", "Dt. cria\u00E7\u00E3o" }));
		cbxTipoPesquisaOrcamento.setSelectedIndex(0);
		cbxTipoPesquisaOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(cbxTipoPesquisaOrcamento);

		fTxtPesquisaOrcamento = new JFormattedTextField();
		fTxtPesquisaOrcamento.setBounds(202, 129, 386, 20);
		fTxtPesquisaOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtPesquisaOrcamento.setFocusLostBehavior(JFormattedTextField.PERSIST);
		contentPane.add(fTxtPesquisaOrcamento);

		lblCliente = new JLabel("Cliente");
		lblCliente.setBounds(10, 60, 48, 20);
		lblCliente.setToolTipText("");
		lblCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(lblCliente);

		separador_cliente = new JSeparator();
		separador_cliente.setBounds(62, 70, 675, 9);
		contentPane.add(separador_cliente);

		cbxTipoPesquisaCliente = new JComboBox<String>();
		cbxTipoPesquisaCliente.setBounds(98, 80, 89, 22);
		cbxTipoPesquisaCliente
				.setModel(new DefaultComboBoxModel<String>(new String[] { "Nome", "Apelido", "C\u00F3digo" }));
		cbxTipoPesquisaCliente.setSelectedIndex(0);
		cbxTipoPesquisaCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(cbxTipoPesquisaCliente);

		lblPesquisarPorCliente = new JLabel("Pesquisar por");
		lblPesquisarPorCliente.setBounds(9, 82, 89, 20);
		lblPesquisarPorCliente.setToolTipText("");
		lblPesquisarPorCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(lblPesquisarPorCliente);

		fTxtPesquisaCliente = new JFormattedTextField();
		fTxtPesquisaCliente.setBounds(198, 78, 514, 20);
		fTxtPesquisaCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaCliente) {
				if (fTxtPesquisaCliente.getText().trim().isEmpty()) {
					alimenta_lt_clientes(cbxTipoPesquisaCliente.getSelectedItem().toString(), null, lista_clientes);
				} else {
					alimenta_lt_clientes(cbxTipoPesquisaCliente.getSelectedItem().toString(),
							fTxtPesquisaCliente.getText().trim(), lista_clientes);
				}

			}
		});
		fTxtPesquisaCliente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtPesquisaCliente.setFocusLostBehavior(JFormattedTextField.PERSIST);
		contentPane.add(fTxtPesquisaCliente);

		separador_orcamento = new JSeparator();
		separador_orcamento.setBounds(91, 116, 646, 9);
		contentPane.add(separador_orcamento);

		lblOrcamento = new JLabel("Or\u00E7amento");
		lblOrcamento.setBounds(10, 106, 82, 20);
		lblOrcamento.setToolTipText("");
		lblOrcamento.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(lblOrcamento);

		checkBoxFaturado = new JCheckBox("Somente faturados");
		checkBoxFaturado.setBounds(594, 127, 143, 23);
		checkBoxFaturado.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(checkBoxFaturado);

		separador_orcamentos1 = new JSeparator();
		separador_orcamentos1.setBounds(12, 193, 265, 9);
		contentPane.add(separador_orcamentos1);

		lblOrcamentosDoCliente = new JLabel("Or\u00E7amentos do cliente");
		lblOrcamentosDoCliente.setToolTipText("");
		lblOrcamentosDoCliente.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblOrcamentosDoCliente.setBounds(281, 182, 160, 20);
		contentPane.add(lblOrcamentosDoCliente);

		separador_orcamentos2 = new JSeparator();
		separador_orcamentos2.setBounds(440, 193, 297, 9);
		contentPane.add(separador_orcamentos2);

		formattedTextField = new JFormattedTextField();
		formattedTextField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		formattedTextField.setBounds(10, 350, 725, 46);
		contentPane.add(formattedTextField);

		lblObservacoes = new JLabel("Observa\u00E7\u00F5es");
		lblObservacoes.setToolTipText("");
		lblObservacoes.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblObservacoes.setBounds(10, 327, 93, 20);
		contentPane.add(lblObservacoes);

		separador_observacoes = new JSeparator();
		separador_observacoes.setBounds(106, 338, 629, 9);
		contentPane.add(separador_observacoes);

		btnExcluirOrcamento = new JButton("Excluir");
		btnExcluirOrcamento.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickExcluirOrcamento) {

				int opcao = JOptionPane.showConfirmDialog(null, "Deseja realmente excluir o orçamento abaixo?"
						+ "\n\nOrçamento Nº:  " + orcamento_selecionado.getId_orcamento() + "\nCliente: "
						+ cliente_selecionado.getNome() + "\nValor: "
						+ nf.format(orcamento_selecionado.getValor_total())
						+ "\n\nCASO EXCLUÍDO NÃO SERÁ POSSÍVEL RECUPERÁ-LO E TODOS OS SEUS DADOS SERÃO REMOVIDOS PERMANENTIMENTE!",
						"Exclusão de orçamento.", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

				Boolean flag = opcao == JOptionPane.YES_OPTION;

				if (flag) {
					OrcamentoDAO orcamento_dao = new OrcamentoDAO();
					if (orcamento_dao.excluir_orcamento(orcamento_selecionado.getId_orcamento())) {
						JOptionPane.showMessageDialog(null,
								"Orçamento " + orcamento_selecionado.getId_orcamento() + "Excluído.",
								"Exclusão de orçamento", JOptionPane.ERROR_MESSAGE);
						orcamentos_cliente.remove(orcamento_selecionado);
						orcamento_selecionado = null;
						modelo_tabela_orcamentos.fireTableDataChanged();
						panel_orcamento.limpar_campos();
						tabelaOrcamentos.clearSelection();
					}
				}

			}
		});
		btnExcluirOrcamento.setIcon(icones.getIcone_excluir());
		btnExcluirOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExcluirOrcamento.setBounds(640, 157, 97, 29);
		contentPane.add(btnExcluirOrcamento);

		btnEditarOrcamento = new JButton("Editar");
		btnEditarOrcamento.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEditar) {
				panel_orcamento.novo_orcamento();
				dispose();
			}
		});
		btnEditarOrcamento.setIcon(icones.getIcone_editar());
		btnEditarOrcamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEditarOrcamento.setBounds(531, 157, 97, 29);

		contentPane.add(btnEditarOrcamento);
	}

	// Configurando largura das colunas da tabela de orçamentos
	public void ConfiguraLarguraColunaTabelaOrcamento(JTable tabela) {
		tabela.getColumnModel().getColumn(0).setPreferredWidth(50); // Número do orçamento.
		tabela.getColumnModel().getColumn(1).setPreferredWidth(70); // Faturado (Sim ou não).
		tabela.getColumnModel().getColumn(2).setPreferredWidth(70); // Quantidade de itens.
		tabela.getColumnModel().getColumn(3).setPreferredWidth(90); // Total mercadorias.
		tabela.getColumnModel().getColumn(4).setPreferredWidth(90); // Desconto.
		tabela.getColumnModel().getColumn(5).setPreferredWidth(90); // Frete.
		tabela.getColumnModel().getColumn(6).setPreferredWidth(90); // Valor total.
		tabela.getColumnModel().getColumn(7).setPreferredWidth(80); // Número de parcelas.
		tabela.getColumnModel().getColumn(8).setPreferredWidth(92); // Data inclusão.

		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelo_tabela_orcamentos);
		tabelaOrcamentos.setRowSorter(sorter);

		sorter.setComparator(3, spv);
		sorter.setComparator(4, spv);
		sorter.setComparator(5, spv);
		sorter.setComparator(6, spv);
	}

	public void alimenta_lt_clientes(String forma_pesquisa, String texto, ArrayList<Cliente> lista_clientes) {

		String tipo_busca = forma_pesquisa;
		String texto_buscado = texto;

		ClienteDAO cliente_dao = new ClienteDAO();
		cliente_dao.alimenta_lt_clientes(tipo_busca, texto_buscado, list_model_clientes, lista_clientes);

		if (!list_model_clientes.isEmpty()) {
			scrollPaneLtClientes.setVisible(true);
		} else {
			scrollPaneLtClientes.setVisible(false);
		}

		ltClientes.setModel(list_model_clientes);
	}

	public ArrayList<Orcamento> alimentar_lista_orcamento(ArrayList<Orcamento> orcamentos) {
		orcamentos.clear();

		OrcamentoDAO orcamento_dao = new OrcamentoDAO();
		orcamentos = orcamento_dao.listar_orcamentos_do_cliente(orcamentos_cliente, cliente_selecionado);

		modelo_tabela_orcamentos.fireTableDataChanged();
		return orcamentos;
	}

	public Orcamento busca_orcamento_selecionado(Integer numero_do_orcamento) {

		produtos_do_orcamento.clear();

		for (Orcamento orcamento : orcamentos_cliente) {
			if (orcamento.getId_orcamento() == numero_do_orcamento) {
				return orcamento;
			}
		}

		return null;
	}
}
