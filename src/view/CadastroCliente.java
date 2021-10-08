package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

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
	private JComboBox cbxLogradouro;
	private JLabel lblTipoLogradouro;
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
		setResizable(false);
		setTitle("Clientes");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 54, 780, 659);
		getContentPane().setLayout(null);

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
		fTxtNomeCliente.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeCliente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtNomeCliente.setBounds(51, 46, 344, 20);
		panel.add(fTxtNomeCliente);

		lblDocumento = new JLabel("Documento");
		lblDocumento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDocumento.setBounds(533, 14, 78, 17);
		panel.add(lblDocumento);

	
		try {
			mascara_cpf = new MaskFormatter("###.###.###-##");
			mascara_cnpj = new MaskFormatter("##.###.###/####-##");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		fTxtDocumento = new JFormattedTextField(mascara_cpf);
		fTxtDocumento.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtDocumento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtDocumento.setBounds(613, 11, 119, 20);
		panel.add(fTxtDocumento);

		lblApelido = new JLabel("Apelido");
		lblApelido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblApelido.setBounds(415, 49, 43, 17);
		panel.add(lblApelido);
		
		
		
		
		MaskFormatter mascara_apelido = null;
		try {
			mascara_apelido = new MaskFormatter ("********************");
		} catch (ParseException e2) {
			e2.printStackTrace();
		}

		fTxtApelido = new JFormattedTextField(mascara_apelido);
		fTxtApelido.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtApelido.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtApelido.setBounds(465, 46, 265, 20);
		panel.add(fTxtApelido);

		checkBoxJuridica = new JCheckBox("Pessoa Jur\u00EDdica");
		checkBoxJuridica.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent juridico) {
				fTxtDocumento.setValue(null);
				if(juridico.getStateChange() == ItemEvent.SELECTED) {
					fTxtDocumento.setFormatterFactory(new DefaultFormatterFactory(mascara_cnpj));
				}else {
					fTxtDocumento.setFormatterFactory(new DefaultFormatterFactory(mascara_cpf));
				}
			}
		});
		checkBoxJuridica.setFont(new Font("Tahoma", Font.PLAIN, 14));
		checkBoxJuridica.setBounds(129, 8, 126, 23);
		panel.add(checkBoxJuridica);
		
		lblCep = new JLabel("CEP");
		lblCep.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCep.setBounds(10, 82, 25, 17);
		panel.add(lblCep);
		
		
		MaskFormatter mascara_cep = null;
		
		try {
			mascara_cep = new MaskFormatter("#####-###");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		fTxtCep = new JFormattedTextField(mascara_cep);
		fTxtCep.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCep.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtCep.setBounds(41, 79, 82, 20);
		panel.add(fTxtCep);
		
		lblCidade = new JLabel("Cidade");
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCidade.setBounds(150, 82, 48, 17);
		panel.add(lblCidade);
		
		MaskFormatter mascara_cidade = null;
		try {
			mascara_cidade = new MaskFormatter ("******************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		fTxtCidade = new JFormattedTextField(mascara_cidade);
		fTxtCidade.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCidade.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtCidade.setBounds(198, 79, 119, 20);
		panel.add(fTxtCidade);
		
		cbxLogradouro = new JComboBox();
		cbxLogradouro.setModel(new DefaultComboBoxModel(new String[] {"RUA", "AVENIDA\t", "RODOVIA", "CH\u00C1CARA", "S\u00CDTIO", "VALE"}));
		cbxLogradouro.setBounds(632, 77, 100, 22);
		panel.add(cbxLogradouro);
		
		lblTipoLogradouro = new JLabel("Tipo do logradouro");
		lblTipoLogradouro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTipoLogradouro.setBounds(503, 84, 119, 17);
		panel.add(lblTipoLogradouro);
		
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
		fTxtNumero.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtNumero.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNumero.setBounds(489, 118, 82, 20);
		panel.add(fTxtNumero);
		
		fTxtReferencia = new JFormattedTextField(mascara_endereco);
		fTxtReferencia.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtReferencia.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtReferencia.setBounds(85, 153, 302, 20);
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
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 385, 742, 224);
		getContentPane().add(scrollPane);
		
		tabelaClientes = new JTable();
		scrollPane.setColumnHeaderView(tabelaClientes);
		
		btnNovo = new JButton("Novo");
		btnNovo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnNovo.setBounds(10, 238, 87, 29);
		btnNovo.setIcon(icone_mais);
		panel.add(btnNovo);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSalvar.setBounds(105, 238, 93, 29);
		btnSalvar.setIcon(icone_salvar);
		panel.add(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCancelar.setBounds(208, 238, 109, 29);
		btnCancelar.setIcon(icone_cancelar);
		panel.add(btnCancelar);
		
		btnEditar = new JButton("Editar");
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnEditar.setBounds(499, 238, 109, 29);
		btnEditar.setIcon(icone_editar);
		panel.add(btnEditar);
		
		btnExcluir = new JButton("Excluir");
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnExcluir.setBounds(623, 238, 109, 29);
		btnExcluir.setIcon(icone_excluir);
		panel.add(btnExcluir);
		
		btnReload = new JButton("");
		btnReload.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnReload.setBounds(689, 277, 43, 29);
		btnReload.setIcon(icone_reload);
		panel.add(btnReload);
		
		
		cbxTipoPesquisa = new JComboBox();
		cbxTipoPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxTipoPesquisa.setModel(new DefaultComboBoxModel(new String[] {"C\u00F3digo", "Nome", "CPF"}));
		cbxTipoPesquisa.setSelectedIndex(1);
		cbxTipoPesquisa.setBounds(98, 278, 89, 25);
		panel.add(cbxTipoPesquisa);
		
		
		

	}
	// ------icones---------
		Icon icone_salvar = new ImageIcon(getClass().getResource("/icons/salvar.png"));
		Icon icone_cancelar = new ImageIcon(getClass().getResource("/icons/cancelar.png"));
		Icon icone_mais = new ImageIcon(getClass().getResource("/icons/mais.png"));
		Icon icone_editar = new ImageIcon(getClass().getResource("/icons/editar.png"));
		Icon icone_excluir = new ImageIcon(getClass().getResource("/icons/excluir.png"));
		Icon icone_reload = new ImageIcon(getClass().getResource("/icons/reload.png"));
		
		
		
}
