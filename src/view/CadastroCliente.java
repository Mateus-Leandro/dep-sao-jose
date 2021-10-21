package view;

import java.awt.AWTKeyStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import dao.ClienteDAO;
import entities.Cliente;
import tables.tableModels.ModeloTabelaClientes;

public class CadastroCliente extends JFrame {
	private JPanel panel;
	private JLabel lblCodigoCliente;
	private JTextField txtCodigoCliente;
	private JLabel lblNomeCliente;
	private JFormattedTextField fTxtNomeCliente;
	private JLabel lblDocumento;
	private JFormattedTextField fTxtDocumento;
	private JLabel lblApelido;
	private JFormattedTextField fTxtApelido;
	private JCheckBox checkBoxJuridica;
	private MaskFormatter mascara_cpf = null;
	private MaskFormatter mascara_cnpj = null;
	private JLabel lblCadastroDeClientes;
	private JLabel lblCep;
	private JFormattedTextField fTxtCep;
	private JLabel lblCidade;
	private JFormattedTextField fTxtCidade;
	private JLabel lblEndereco;
	private JFormattedTextField fTxtEndereco;
	private JLabel lblNumero;
	private JFormattedTextField fTxtNumero;
	private JFormattedTextField fTxtReferencia;
	private JLabel lblReferencia;
	private JLabel lblBairro;
	private JFormattedTextField fTxtBairro;
	private JLabel lblCelular;
	private JFormattedTextField fTxtCelular;
	private JLabel lblTelFixo;
	private JFormattedTextField fTxtTelFixo;
	private JScrollPane scrollPane;
	private JTable tabelaClientes;
	private JLabel lblPesquisarPor;
	private JTextField txtPesquisar;
	private JButton btnNovo;
	private JComboBox cbxTipoPesquisa;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnReload;
	private JFormattedTextField fTxtEmail;
	private JLabel lblEmail;
	private JLabel lblIe;
	private JFormattedTextField fTxtIe;
	ArrayList<Cliente> clientes = new ArrayList<Cliente>();
	ModeloTabelaClientes modelo = new ModeloTabelaClientes(clientes);
	ClienteDAO cliente_dao = new ClienteDAO();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroCliente frame = new CadastroCliente();
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
	public CadastroCliente() {
		tecla_pressionada();
		setResizable(false);
		setTitle("Clientes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 54, 780, 659);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		panel = new JPanel();
		panel.setBounds(10, 60, 742, 314);
		getContentPane().add(panel);
		panel.setBorder(UIManager.getBorder("PopupMenu.border"));
		panel.setLayout(null);

		lblCodigoCliente = new JLabel("C\u00F3digo");
		lblCodigoCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigoCliente.setBounds(10, 11, 43, 17);
		panel.add(lblCodigoCliente);

		txtCodigoCliente = new JTextField();
		txtCodigoCliente.setToolTipText("Gerado autom\u00E1ticamente");
		txtCodigoCliente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtCodigoCliente.setEditable(false);
		txtCodigoCliente.setColumns(10);
		txtCodigoCliente.setBounds(59, 11, 64, 20);
		panel.add(txtCodigoCliente);

		lblNomeCliente = new JLabel("Nome");
		lblNomeCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeCliente.setBounds(8, 49, 43, 17);
		panel.add(lblNomeCliente);

		MaskFormatter mascara_nome = null;
		try {
			mascara_nome = new MaskFormatter("*********************************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fTxtNomeCliente = new JFormattedTextField(mascara_nome);
		fTxtNomeCliente.setEnabled(false);
		fTxtNomeCliente.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeCliente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtNomeCliente.setBounds(51, 46, 344, 20);
		panel.add(fTxtNomeCliente);

		lblDocumento = new JLabel("Documento");
		lblDocumento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDocumento.setBounds(522, 12, 78, 17);
		panel.add(lblDocumento);

		try {
			mascara_cpf = new MaskFormatter("###.###.###-##");
			mascara_cnpj = new MaskFormatter("##.###.###/####-##");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtDocumento = new JFormattedTextField(mascara_cpf);
		fTxtDocumento.setEnabled(false);
		fTxtDocumento.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtDocumento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtDocumento.setBounds(604, 8, 125, 20);
		panel.add(fTxtDocumento);

		lblApelido = new JLabel("Apelido");
		lblApelido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblApelido.setBounds(415, 49, 43, 17);
		panel.add(lblApelido);

		MaskFormatter mascara_apelido = null;
		try {
			mascara_apelido = new MaskFormatter("********************");
		} catch (ParseException e2) {
			e2.printStackTrace();
		}

		fTxtApelido = new JFormattedTextField(mascara_apelido);
		fTxtApelido.setEnabled(false);
		fTxtApelido.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtApelido.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtApelido.setBounds(465, 46, 265, 20);
		panel.add(fTxtApelido);

		checkBoxJuridica = new JCheckBox("Pessoa Jur\u00EDdica");
		checkBoxJuridica.setEnabled(false);
		checkBoxJuridica.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent juridico) {
				fTxtDocumento.setValue(null);
				if (juridico.getStateChange() == ItemEvent.SELECTED) {
					fTxtDocumento.setFormatterFactory(new DefaultFormatterFactory(mascara_cnpj));
					fTxtIe.setVisible(true);
					lblIe.setVisible(true);
				} else {
					fTxtDocumento.setFormatterFactory(new DefaultFormatterFactory(mascara_cpf));
					fTxtIe.setText(null);
					fTxtIe.setVisible(false);
					lblIe.setVisible(false);
				}
			}
		});
		checkBoxJuridica.setFont(new Font("Tahoma", Font.PLAIN, 14));
		checkBoxJuridica.setBounds(198, 11, 119, 23);
		panel.add(checkBoxJuridica);

		lblCep = new JLabel("CEP");
		lblCep.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCep.setBounds(11, 84, 25, 17);
		panel.add(lblCep);

		MaskFormatter mascara_cep = null;

		try {
			mascara_cep = new MaskFormatter("#####-###");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtCep = new JFormattedTextField(mascara_cep);
		fTxtCep.setEnabled(false);
		fTxtCep.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCep.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtCep.setBounds(42, 81, 82, 20);
		panel.add(fTxtCep);

		lblCidade = new JLabel("Cidade");
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCidade.setBounds(182, 84, 48, 17);
		panel.add(lblCidade);

		MaskFormatter mascara_cidade = null;
		try {
			mascara_cidade = new MaskFormatter("******************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtCidade = new JFormattedTextField(mascara_cidade);
		fTxtCidade.setEnabled(false);
		fTxtCidade.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtCidade.setBounds(230, 81, 212, 20);
		panel.add(fTxtCidade);

		lblEndereco = new JLabel("Endere\u00E7o");
		lblEndereco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEndereco.setBounds(11, 121, 64, 17);
		panel.add(lblEndereco);

		MaskFormatter mascara_endereco = null;

		try {
			mascara_endereco = new MaskFormatter("*************************************************");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		fTxtEndereco = new JFormattedTextField(mascara_endereco);
		fTxtEndereco.setEnabled(false);
		fTxtEndereco.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtEndereco.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtEndereco.setBounds(76, 118, 361, 20);
		panel.add(fTxtEndereco);

		lblNumero = new JLabel("N\u00BA");
		lblNumero.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumero.setBounds(468, 121, 25, 17);
		panel.add(lblNumero);

		MaskFormatter mascara_numero = null;

		try {
			mascara_numero = new MaskFormatter("########");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fTxtNumero = new JFormattedTextField(mascara_numero);
		fTxtNumero.setEnabled(false);
		fTxtNumero.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtNumero.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNumero.setBounds(489, 118, 82, 20);
		panel.add(fTxtNumero);

		MaskFormatter mascara_referencia = null;

		try {
			mascara_referencia = new MaskFormatter("*************************************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fTxtReferencia = new JFormattedTextField(mascara_referencia);
		fTxtReferencia.setEnabled(false);
		fTxtReferencia.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtReferencia.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtReferencia.setBounds(85, 153, 352, 20);
		panel.add(fTxtReferencia);

		lblReferencia = new JLabel("Refer\u00EAncia");
		lblReferencia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReferencia.setBounds(10, 157, 64, 17);
		panel.add(lblReferencia);

		lblBairro = new JLabel("Bairro");
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBairro.setBounds(467, 157, 35, 17);
		panel.add(lblBairro);

		MaskFormatter mascara_bairro = null;
		try {
			mascara_bairro = new MaskFormatter("******************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtBairro = new JFormattedTextField(mascara_bairro);
		fTxtBairro.setEnabled(false);
		fTxtBairro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtBairro.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtBairro.setBounds(512, 154, 220, 20);
		panel.add(fTxtBairro);

		lblCelular = new JLabel("Celular");
		lblCelular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCelular.setBounds(10, 197, 43, 17);
		panel.add(lblCelular);

		MaskFormatter mascara_celular = null;
		try {
			mascara_celular = new MaskFormatter("(##)#####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtCelular = new JFormattedTextField(mascara_celular);
		fTxtCelular.setEnabled(false);
		fTxtCelular.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtCelular.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCelular.setBounds(60, 193, 100, 20);
		panel.add(fTxtCelular);

		lblTelFixo = new JLabel("Tel. Fixo");
		lblTelFixo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTelFixo.setBounds(182, 197, 58, 17);
		panel.add(lblTelFixo);

		MaskFormatter mascara_telefone = null;

		try {
			mascara_telefone = new MaskFormatter("(##)####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fTxtTelFixo = new JFormattedTextField(mascara_telefone);
		fTxtTelFixo.setEnabled(false);
		fTxtTelFixo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtTelFixo.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtTelFixo.setBounds(239, 193, 100, 20);
		panel.add(fTxtTelFixo);

		lblPesquisarPor = new JLabel("Pesquisar por");
		lblPesquisarPor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPesquisarPor.setEnabled(true);
		lblPesquisarPor.setBounds(10, 283, 87, 14);
		panel.add(lblPesquisarPor);

		txtPesquisar = new JTextField();
		txtPesquisar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPesquisar.setColumns(10);
		txtPesquisar.setBounds(199, 283, 480, 20);
		panel.add(txtPesquisar);

		lblCadastroDeClientes = new JLabel("Cadastro de Clientes");
		lblCadastroDeClientes.setBounds(10, 11, 742, 43);
		lblCadastroDeClientes.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastroDeClientes.setFont(new Font("Tahoma", Font.BOLD, 24));
		getContentPane().add(lblCadastroDeClientes);

		clientes = cliente_dao.listarClientes(clientes);

		// Criando tabela de clientes.
		tabelaClientes = new JTable(modelo);
		tabelaClientes.getTableHeader().setReorderingAllowed(false);
		scrollPane = new JScrollPane(tabelaClientes);
		scrollPane.setBounds(10, 385, 742, 224);
		getContentPane().add(scrollPane);
		ConfiguralarguracolunaTabela(tabelaClientes);

		btnNovo = new JButton("Novo");
		btnNovo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNovoCliente) {
				novo_cliente();
			}
		});
		btnNovo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNovo.setBounds(10, 238, 87, 29);
		btnNovo.setIcon(icone_mais);
		panel.add(btnNovo);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickSalvarCliente) {
				salvar_cliente();
			}
		});
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSalvar.setBounds(105, 238, 93, 29);
		btnSalvar.setIcon(icone_salvar);
		btnSalvar.setVisible(false);
		panel.add(btnSalvar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancelarCliente) {
				cancelar_cliente();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCancelar.setBounds(208, 238, 109, 29);
		btnCancelar.setIcon(icone_cancelar);
		btnCancelar.setVisible(false);
		panel.add(btnCancelar);

		btnEditar = new JButton("Editar");
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnEditar.setBounds(499, 238, 109, 29);
		btnEditar.setIcon(icone_editar);
		btnEditar.setEnabled(false);
		panel.add(btnEditar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnExcluir.setBounds(623, 238, 109, 29);
		btnExcluir.setIcon(icone_excluir);
		btnExcluir.setEnabled(false);
		panel.add(btnExcluir);

		btnReload = new JButton("");
		btnReload.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickReload) {
				recarregarTabela();
			}
		});
		btnReload.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnReload.setBounds(689, 277, 43, 29);
		btnReload.setIcon(icone_reload);
		panel.add(btnReload);

		cbxTipoPesquisa = new JComboBox();
		cbxTipoPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxTipoPesquisa.setModel(new DefaultComboBoxModel(new String[] { "C\u00F3digo", "Nome", "CPF" }));
		cbxTipoPesquisa.setSelectedIndex(1);
		cbxTipoPesquisa.setBounds(98, 278, 89, 25);
		panel.add(cbxTipoPesquisa);

		MaskFormatter mascara_email = null;

		try {
			mascara_email = new MaskFormatter("****************************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtEmail = new JFormattedTextField();
		fTxtEmail.setEnabled(false);
		fTxtEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtEmail.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtEmail.setBounds(397, 193, 221, 20);
		panel.add(fTxtEmail);

		lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmail.setBounds(357, 197, 37, 17);
		panel.add(lblEmail);

		lblIe = new JLabel("I.E.");
		lblIe.setHorizontalAlignment(SwingConstants.LEFT);
		lblIe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIe.setBounds(357, 14, 25, 17);
		lblIe.setVisible(false);
		panel.add(lblIe);

		MaskFormatter mascara_ie = null;

		try {
			mascara_ie = new MaskFormatter("#########.##-##");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fTxtIe = new JFormattedTextField(mascara_ie);
		fTxtIe.setEnabled(false);
		fTxtIe.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtIe.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtIe.setBounds(386, 9, 119, 20);
		fTxtIe.setVisible(false);
		panel.add(fTxtIe);

		JButton btnBuscaCep = new JButton();
		btnBuscaCep.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				buscar_cep();
			}
		});
		btnBuscaCep.setBounds(134, 77, 25, 23);
		btnBuscaCep.setIcon(icone_pesquisar);
		panel.add(btnBuscaCep);

	}

	// Configurando largura das colunas da tabela
	public void ConfiguralarguracolunaTabela(JTable tabelaProdutos) {
		tabelaClientes.getColumnModel().getColumn(0).setPreferredWidth(3);
		tabelaClientes.getColumnModel().getColumn(1).setPreferredWidth(100);
		tabelaClientes.getColumnModel().getColumn(2).setPreferredWidth(50);
		tabelaClientes.getColumnModel().getColumn(3).setPreferredWidth(50);
		tabelaClientes.getColumnModel().getColumn(4).setPreferredWidth(130);
		tabelaClientes.getColumnModel().getColumn(5).setPreferredWidth(5);
		tabelaClientes.getColumnModel().getColumn(6).setPreferredWidth(15);
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelo);
		tabelaClientes.setRowSorter(sorter);
		
		
		
	}

	private void salvar_cliente() {
		if (fTxtNomeCliente.getText().trim().isEmpty()) {
			fTxtNomeCliente.setBorder(new LineBorder(Color.RED));
			JOptionPane.showMessageDialog(null, "Necessário informar o nome do cliente.", "Cliente sem nome!",
					JOptionPane.WARNING_MESSAGE);
		} else {
			Cliente novo_cliente = new Cliente();
			novo_cliente = gravarNovoCliente(novo_cliente);
			novo_cliente = cliente_dao.inserirCliente(novo_cliente);

			if (novo_cliente.getIdCliente() != null) {
				JOptionPane.showMessageDialog(
						null, "Cliente cadastrado com sucesso! " + "\nCódigo: " + novo_cliente.getIdCliente()
								+ "\nProduto: " + novo_cliente.getNome(),
						"Cadastro de clientes", JOptionPane.NO_OPTION);
				desativar_campos();
				btnSalvar.setVisible(false);
				btnCancelar.setVisible(false);
				btnEditar.setVisible(true);
				btnExcluir.setVisible(true);
			}
			limpar_campos();
			recarregarTabela();
		}

		fTxtNomeCliente.setBorder(new LineBorder(Color.lightGray));
	}

	private Cliente gravarNovoCliente(Cliente novo_cliente) {
		String nome = fTxtNomeCliente.getText().trim();
		String apelido = null;
		String documento = null;
		String inscricao_estadual = null;
		String cep = null;
		String cidade = null;
		String tipo_logradouro = null;
		String endereco = null;
		String referencia = null;
		String numero = null;
		String bairro = null;
		String email = null;
		String celular = null;
		String telefone = null;

		if (!fTxtApelido.getText().trim().isEmpty()) {
			apelido = fTxtApelido.getText().trim();
		}

		if (!fTxtDocumento.getText().equals("   .   .   -  ") & !fTxtDocumento.getText().equals("  .   .   /    -  ")) {
			fTxtDocumento.getText().trim();
		}

		if (fTxtIe.isVisible()) {
			inscricao_estadual = fTxtIe.getText().trim();
		}

		if (!fTxtCep.getText().equals("     -   ")) {
			cep = fTxtCep.getText().trim();
		}

		if (!fTxtCidade.getText().trim().isEmpty()) {
			cidade = fTxtCidade.getText().trim();
		}

		if (!fTxtEndereco.getText().trim().isEmpty()) {
			endereco = fTxtEndereco.getText().trim();
		}

		if (!fTxtReferencia.getText().trim().isEmpty()) {
			referencia = fTxtReferencia.getText().trim();
		}

		if (!fTxtNumero.getText().trim().isEmpty()) {
			numero = fTxtNumero.getText().trim();
		}

		if (!fTxtBairro.getText().trim().isEmpty()) {
			bairro = fTxtBairro.getText().trim();
		}

		if (!fTxtEmail.getText().trim().isEmpty()) {
			email = fTxtEmail.getText().trim();
		}

		if (!fTxtCelular.getText().equals("(  )     -    ")) {
			celular = fTxtCelular.getText().trim();
		}

		if (!fTxtTelFixo.getText().equals("(  )    -    ")) {
			telefone = fTxtTelFixo.getText().trim();
		}

		Date data_cadastro = new java.sql.Date(System.currentTimeMillis());

		novo_cliente = new Cliente(null, nome, apelido, checkBoxJuridica.isSelected(), documento, inscricao_estadual,
				cep, cidade, endereco, referencia, numero, bairro, email, celular, data_cadastro, null);

		return novo_cliente;
	}

	// ------------- Funções tabela cliente-------------
	public void recarregarTabela() {
		clientes.clear();
		clientes = cliente_dao.listarClientes(clientes);
		modelo = new ModeloTabelaClientes(clientes);
		tabelaClientes.setModel(modelo);
		ConfiguralarguracolunaTabela(tabelaClientes);
		modelo.fireTableDataChanged();

	}

	public void editar_cliente() {
	}

	public void cancelar_cliente() {
		desativar_campos();
		btnSalvar.setVisible(false);
		btnCancelar.setVisible(false);
		btnNovo.setEnabled(true);
		btnEditar.setVisible(true);
		btnExcluir.setVisible(true);
		limpar_campos();
	}

	public void novo_cliente() {
		ativar_campos();
		btnSalvar.setVisible(true);
		btnCancelar.setVisible(true);
		btnNovo.setEnabled(false);
		btnEditar.setVisible(false);
		btnExcluir.setVisible(false);

		fTxtDocumento.requestFocusInWindow();
	}

	public void excluir_cliente() {

	}

	public void ativar_campos() {
		fTxtApelido.setEnabled(true);
		fTxtBairro.setEnabled(true);
		fTxtCelular.setEnabled(true);
		fTxtCep.setEnabled(true);
		fTxtCidade.setEnabled(true);
		fTxtDocumento.setEnabled(true);
		fTxtEmail.setEnabled(true);
		fTxtEndereco.setEnabled(true);
		fTxtIe.setEnabled(true);
		fTxtNomeCliente.setEnabled(true);
		fTxtNumero.setEnabled(true);
		fTxtReferencia.setEnabled(true);
		fTxtTelFixo.setEnabled(true);
		checkBoxJuridica.setEnabled(true);
	}

	public void desativar_campos() {
		fTxtApelido.setEnabled(false);
		fTxtBairro.setEnabled(false);
		fTxtCelular.setEnabled(false);
		fTxtCep.setEnabled(false);
		fTxtCidade.setEnabled(false);
		fTxtDocumento.setEnabled(false);
		fTxtEmail.setEnabled(false);
		fTxtEndereco.setEnabled(false);
		fTxtIe.setEnabled(false);
		fTxtNomeCliente.setEnabled(false);
		fTxtNumero.setEnabled(false);
		fTxtReferencia.setEnabled(false);
		fTxtTelFixo.setEnabled(false);
		checkBoxJuridica.setEnabled(false);
	}

	public void limpar_campos() {
		fTxtApelido.setText(null);
		fTxtBairro.setText(null);
		fTxtCelular.setText(null);
		fTxtCep.setText(null);
		fTxtCidade.setText(null);
		fTxtDocumento.setText(null);
		fTxtEmail.setText(null);
		fTxtEndereco.setText(null);
		fTxtIe.setText(null);
		fTxtNomeCliente.setText(null);
		fTxtNumero.setText(null);
		fTxtReferencia.setText(null);
		fTxtTelFixo.setText(null);
		checkBoxJuridica.setSelected(false);
	}

	// Metódo para buscar endereço via cep
	public void buscar_cep() {
		String logradouro = null;
		String tipoLogradouro = null;
		String cidade = null;
		String cep = fTxtCep.getText().trim().replaceAll("-", "");
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&fornato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();

			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					fTxtCidade.setText(element.getText());
					cidade = element.getText();
				}
				if (element.getQualifiedName().equals("bairro")) {
					fTxtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}

			}
			
			if (cidade.equals("")) {
				JOptionPane.showMessageDialog(null, "Cep não encontrado", "Busca por CEP",
						JOptionPane.WARNING_MESSAGE);
			}

			fTxtEndereco.setText(tipoLogradouro + " " + logradouro);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	


	// ---------Atalhos do teclado--------
	public void tecla_pressionada() {
		InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "reload");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "novo");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "editar");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "excluir");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelar");
		//inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "salvar");

		this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);

		this.getRootPane().getActionMap().put("reload", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_recarregar) {
				btnReload.doClick();
				recarregarTabela();
			}
		});

		this.getRootPane().getActionMap().put("editar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_editar) {
				btnEditar.doClick();
				editar_cliente();
			}
		}

		);

		this.getRootPane().getActionMap().put("cancelar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_cancelar) {
				cancelar_cliente();
			}

		}

		);

		
		this.getRootPane().getActionMap().put("salvar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_salvar) {
				btnSalvar.doClick();
				salvar_cliente();
			}

		}

		);

		this.getRootPane().getActionMap().put("novo", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_novo) {
				novo_cliente();
			}

		}

		);

		this.getRootPane().getActionMap().put("excluir", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_novo) {
				excluir_cliente();
			}

		}

		);
	}

	// ------icones---------
	Icon icone_salvar = new ImageIcon(getClass().getResource("/icons/salvar.png"));
	Icon icone_cancelar = new ImageIcon(getClass().getResource("/icons/cancelar.png"));
	Icon icone_mais = new ImageIcon(getClass().getResource("/icons/mais.png"));
	Icon icone_editar = new ImageIcon(getClass().getResource("/icons/editar.png"));
	Icon icone_excluir = new ImageIcon(getClass().getResource("/icons/excluir.png"));
	Icon icone_reload = new ImageIcon(getClass().getResource("/icons/reload.png"));
	Icon icone_pesquisar = new ImageIcon(getClass().getResource("/icons/pesquisar.png"));
}
