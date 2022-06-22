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
import java.text.ParseException;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import api_tools.Busca_cep;
import api_tools.Busca_cnpj;
import dao.configuracoes.ConfiguracaoDAO;
import dao.pessoa.ClienteDAO;
import dao.pessoa.FornecedorDAO;
import entities.configuracoes.Configuracoes;
import entities.pessoa.Cliente;
import entities.pessoa.Fornecedor;
import entities.pessoa.Pessoa;
import icons.Icones;
import tables.tableRenders.Render_tabela_clientes;
import tools.JTextFieldLimit;
import tools.Jtext_tools;

public class Panel_pessoa extends JPanel {
	protected JTextField txtCodigo;
	private JLabel lblCodigo;
	protected JCheckBox checkBoxJuridica = new JCheckBox();
	private JLabel lblDocumento;
	protected JFormattedTextField fTxtDocumento;
	protected JLabel lblIe;
	protected JFormattedTextField fTxtIe;
	private JSeparator separador_titulo;
	private JLabel lblNome;
	protected JFormattedTextField fTxtNomePessoa;
	private JLabel lblApelido;
	protected JFormattedTextField fTxtApelido;
	private JLabel lblCep;
	protected JButton btnNovo;
	protected JFormattedTextField fTxtCep;
	protected JButton btnLimpaCep;
	protected JFormattedTextField fTxtCidade;
	private JLabel lblCidade;
	private JLabel lblEndereco;
	protected JFormattedTextField fTxtEndereco;
	private JLabel lblNumero;
	protected JFormattedTextField fTxtNumero;
	private JLabel lblReferencia;
	protected JFormattedTextField fTxtReferencia;
	private JLabel lblBairro;
	protected JFormattedTextField fTxtBairro;
	private JLabel lblCelular;
	protected JFormattedTextField fTxtCelular;
	private JLabel lblTelFixo;
	protected JFormattedTextField fTxtTelFixo;
	private JLabel lblEmai;
	protected JFormattedTextField fTxtEmail;
	private Icones icones = new Icones();
	protected JButton btnSalvar;
	protected JButton btnCancelar = new JButton("Cancelar");
	protected JButton btnEditar;
	protected JButton btnExcluir;
	private JLabel lblPesquisarPor;
	protected JFormattedTextField fTxtPesquisa;
	protected JSeparator separador_2;
	protected JSeparator separador_3;
	private MaskFormatter mascara_cpf = null;
	private MaskFormatter mascara_cnpj = null;
	protected JButton btnLimpaDocumento;
	private JLabel lblInformacoesBasicas;
	private JSeparator separador_1;
	private JLabel lblInfoEndereco;
	private JSeparator separador_clientes_4;
	protected JButton btnReload;
	private Jtext_tools text_tools = new Jtext_tools();
	private JLabel lblObg_nome;
	private JLabel lblObg_celular;
	private ClienteDAO cliente_dao = new ClienteDAO();
	protected FornecedorDAO fornecedor_dao = new FornecedorDAO();
	private Render_tabela_clientes render = new Render_tabela_clientes();
	private ConfiguracaoDAO conf_dao = new ConfiguracaoDAO();
	private Configuracoes configuracoes = conf_dao.busca_configuracoes();
	private JLabel lblEsc;
	private JLabel lblCancelar;
	private JLabel lblF1;
	private JLabel lblNovo;
	private JLabel lblF5;
	protected JLabel lblRecarregar;
	private JLabel lblF3;
	private JLabel lblEditar;
	private JLabel lblF12;
	private JLabel lblExcluir;
	protected AbstractTableModel modelo_tabela;
	protected JTable tabela;
	private String tipo_pessoa;
	private Panel_cliente panel_cliente;

	/**
	 * Create the panel.
	 */
	public Panel_pessoa() {

		setBorder(null);
		setLayout(null);

		btnEditar = new JButton("Editar");
		btnEditar.setEnabled(false);
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEditar.setBounds(16, 393, 101, 29);
		btnEditar.setIcon(icones.getIcone_editar());
		add(btnEditar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExcluir.setBounds(132, 393, 104, 29);
		btnExcluir.setIcon(icones.getIcone_excluir());
		btnExcluir.setEnabled(false);
		add(btnExcluir);

		fTxtPesquisa = new JFormattedTextField();
		JTextFieldLimit limitDocument_pesquisa = new JTextFieldLimit(30, "texto");
		fTxtPesquisa.setDocument(limitDocument_pesquisa);
		fTxtPesquisa.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (fTxtPesquisa.getText().isBlank()) {
					fTxtPesquisa.setCaretPosition(0);
				}
			}
		});
		fTxtPesquisa.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtPesquisa.setBounds(211, 456, 454, 20);
		add(fTxtPesquisa);

		txtCodigo = new JTextField();
		txtCodigo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtCodigo.setBounds(63, 152, 65, 20);
		txtCodigo.setEditable(false);
		add(txtCodigo);
		txtCodigo.setColumns(10);

		lblCodigo = new JLabel("C\u00F3digo");
		lblCodigo.setBounds(16, 154, 46, 20);
		lblCodigo.setToolTipText("Gerado Autom\u00E1ticamente");
		lblCodigo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblCodigo);

		checkBoxJuridica = new JCheckBox("Pessoa Jur\u00EDdica");
		checkBoxJuridica.setBounds(146, 151, 119, 23);
		checkBoxJuridica.setFont(new Font("Tahoma", Font.PLAIN, 14));
		checkBoxJuridica.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent juridico) {
				fTxtDocumento.setText(null);

				if (juridico.getStateChange() == ItemEvent.SELECTED) {
					fTxtDocumento.setValue(null);
					lblDocumento.setText("CNPJ");
					fTxtDocumento.setFormatterFactory(new DefaultFormatterFactory(mascara_cnpj));
					fTxtIe.setVisible(true);
					lblIe.setVisible(true);
				} else {
					fTxtDocumento.setValue(null);
					lblDocumento.setText("CPF");
					fTxtDocumento.setFormatterFactory(new DefaultFormatterFactory(mascara_cpf));
					fTxtIe.setText(null);
					fTxtIe.setVisible(false);
					lblIe.setVisible(false);
					btnLimpaDocumento.setVisible(false);
				}

				btnLimpaCep.setVisible(false);

				if (!btnNovo.isEnabled()) {
					fTxtDocumento.requestFocus();
				}
			}
		});
		checkBoxJuridica.setEnabled(false);
		add(checkBoxJuridica);

		lblDocumento = new JLabel("CPF");
		lblDocumento.setBounds(339, 156, 34, 17);
		lblDocumento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblDocumento);

		try {
			mascara_cpf = new MaskFormatter("###.###.###-##");
			mascara_cnpj = new MaskFormatter("##.###.###/####-##");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtDocumento = new JFormattedTextField(mascara_cpf);
		fTxtDocumento.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickDocumento) {
				fTxtDocumento.setCaretPosition(0);
			}
		});
		fTxtDocumento.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtDocumento.setEditable(false);
		fTxtDocumento.setBounds(376, 152, 125, 20);
		fTxtDocumento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtDocumento.setFocusLostBehavior(JFormattedTextField.PERSIST);
		add(fTxtDocumento);
		fTxtDocumento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterDocumento) {
				if (enterDocumento.getKeyCode() == enterDocumento.VK_ENTER) {
					if (fTxtIe.isVisible()) {
						fTxtIe.requestFocus();
						pega_dados_pessoa_juridica();
					} else {
						fTxtNomePessoa.requestFocus();
					}
				}
			}
		});

		lblIe = new JLabel("I. E.");
		lblIe.setBounds(550, 155, 24, 17);
		lblIe.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIe.setVisible(false);
		add(lblIe);

		MaskFormatter mascara_ie = null;

		try {
			mascara_ie = new MaskFormatter("#########.##-##");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fTxtIe = new JFormattedTextField(mascara_ie);
		fTxtIe = new JFormattedTextField(mascara_ie);
		fTxtIe.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterIe) {
				if (enterIe.getKeyCode() == enterIe.VK_ENTER) {
					fTxtNomePessoa.requestFocus();
				}
			}
		});
		fTxtIe.setEditable(false);
		fTxtIe.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickIe) {
				fTxtIe.setCaretPosition(0);
			}
		});
		fTxtIe.setBounds(580, 152, 129, 20);
		fTxtIe.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtIe.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtIe.setVisible(false);
		add(fTxtIe);

		separador_titulo = new JSeparator();
		separador_titulo.setBounds(10, 50, 698, 9);
		add(separador_titulo);

		lblNome = new JLabel("Nome");
		lblNome.setBounds(15, 188, 40, 20);
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblNome);

		fTxtNomePessoa = new JFormattedTextField();
		JTextFieldLimit limitDocument_nome = new JTextFieldLimit(45, "texto");
		fTxtNomePessoa.setDocument(limitDocument_nome);
		fTxtNomePessoa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterNome) {
				if (!fTxtNomePessoa.getText().isBlank() && enterNome.getKeyCode() == enterNome.VK_ENTER) {
					fTxtApelido.requestFocus();
				}
			}
		});
		fTxtNomePessoa.setEditable(false);
		fTxtNomePessoa.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNomeCliente) {
				text_tools.move_cursor_inicio(fTxtNomePessoa);
			}
		});
		fTxtNomePessoa.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtNomePessoa.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomePessoa.setBounds(57, 186, 317, 20);
		add(fTxtNomePessoa);

		lblApelido = new JLabel("Apelido");
		lblApelido.setBounds(398, 189, 46, 20);
		lblApelido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblApelido);

		fTxtApelido = new JFormattedTextField();
		JTextFieldLimit limitDocument_apelido = new JTextFieldLimit(40, "texto");
		fTxtApelido.setDocument(limitDocument_apelido);
		fTxtApelido.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterApelido) {
				if (enterApelido.getKeyCode() == enterApelido.VK_ENTER) {
					fTxtCep.requestFocus();
				}
			}
		});
		fTxtApelido.setEditable(false);
		fTxtApelido.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickApelido) {
				text_tools.move_cursor_inicio(fTxtApelido);
			}
		});
		fTxtApelido.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtApelido.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtApelido.setBounds(444, 186, 265, 20);
		add(fTxtApelido);

		lblCep = new JLabel("Cep");
		lblCep.setBounds(16, 255, 28, 20);
		lblCep.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblCep);

		btnNovo = new JButton("Novo");
		btnNovo.setBounds(10, 70, 89, 29);
		btnNovo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNovo.setIcon(null);
		btnNovo.setIcon(icones.getIcone_mais());
		add(btnNovo);

		MaskFormatter mascara_cep = null;

		try {
			mascara_cep = new MaskFormatter("#####-###");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtCep = new JFormattedTextField(mascara_cep);
		fTxtCep.setEditable(false);
		fTxtCep.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCep) {
				fTxtCep.setCaretPosition(0);
			}
		});
		fTxtCep.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtCep.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				if (!fTxtCep.getText().trim().equals("-")) {
					if (fTxtCep.getText().trim().length() == 9) {
						buscaCep(fTxtCep.getText().trim());
						btnLimpaCep.setVisible(true);
					}
				}

				if (e.getKeyCode() == e.VK_ENTER) {
					fTxtCidade.requestFocus();
				}
			}
		});
		fTxtCep.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCep.setBounds(46, 257, 78, 20);
		add(fTxtCep);

		btnLimpaCep = new JButton();
		btnLimpaCep.setVisible(false);
		btnLimpaCep.setBounds(132, 256, 24, 19);
		btnLimpaCep.setIcon(icones.getIcone_limpar());
		btnLimpaCep.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent limpa_cep) {
				limpar_endereco();
			}
		});
		btnLimpaCep.setToolTipText("Limpar Cep");
		add(btnLimpaCep);

		fTxtCidade = new JFormattedTextField();
		JTextFieldLimit limitDocument_cidade = new JTextFieldLimit(30, "texto");
		fTxtCidade.setDocument(limitDocument_cidade);
		fTxtCidade.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterCidade) {
				if (enterCidade.getKeyCode() == enterCidade.VK_ENTER) {
					fTxtNumero.requestFocus();
				}
			}
		});
		fTxtCidade.setEditable(false);
		fTxtCidade.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCidade) {
				text_tools.move_cursor_inicio(fTxtCidade);
			}
		});
		fTxtCidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtCidade.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCidade.setBounds(262, 257, 239, 20);
		add(fTxtCidade);

		lblCidade = new JLabel("Cidade");
		lblCidade.setBounds(211, 259, 44, 20);
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblCidade);

		lblEndereco = new JLabel("Endereco");
		lblEndereco.setBounds(16, 295, 65, 20);
		lblEndereco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblEndereco);

		fTxtEndereco = new JFormattedTextField();
		JTextFieldLimit limitDocument_endereco = new JTextFieldLimit(49, "texto");
		fTxtEndereco.setDocument(limitDocument_endereco);
		fTxtEndereco.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterEndereco) {
				if (enterEndereco.getKeyCode() == enterEndereco.VK_ENTER) {
					fTxtBairro.requestFocus();
				}
			}
		});
		fTxtEndereco.setEditable(false);
		fTxtEndereco.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEndereco) {
				text_tools.move_cursor_inicio(fTxtEndereco);
			}
		});
		fTxtEndereco.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtEndereco.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtEndereco.setBounds(79, 293, 332, 20);
		add(fTxtEndereco);

		lblNumero = new JLabel("N\u00B0");
		lblNumero.setBounds(525, 257, 16, 20);
		lblNumero.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblNumero);

		fTxtNumero = new JFormattedTextField();
		JTextFieldLimit limitDocument_numero = new JTextFieldLimit(8, "texto");
		fTxtNumero.setDocument(limitDocument_numero);
		fTxtNumero.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterNumero) {
				if (enterNumero.getKeyCode() == enterNumero.VK_ENTER) {
					fTxtEndereco.requestFocus();
				}
			}
		});
		fTxtNumero.setEditable(false);
		fTxtNumero.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNumero) {
				text_tools.move_cursor_inicio(fTxtNumero);
			}
		});
		fTxtNumero.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtNumero.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNumero.setBounds(543, 256, 70, 20);
		add(fTxtNumero);

		lblReferencia = new JLabel("Referencia");
		lblReferencia.setBounds(16, 328, 65, 20);
		lblReferencia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblReferencia);

		fTxtReferencia = new JFormattedTextField();
		JTextFieldLimit limitDocument_referencia = new JTextFieldLimit(49, "texto");
		fTxtReferencia.setDocument(limitDocument_referencia);
		fTxtReferencia.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterReferencia) {
				if (enterReferencia.getKeyCode() == enterReferencia.VK_ENTER) {
					fTxtEmail.requestFocus();
				}
			}
		});

		fTxtReferencia.setEditable(false);
		fTxtReferencia.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickReferencia) {
				text_tools.move_cursor_inicio(fTxtReferencia);
			}
		});
		fTxtReferencia.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtReferencia.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtReferencia.setBounds(86, 326, 325, 20);
		add(fTxtReferencia);

		lblBairro = new JLabel("Bairro");
		lblBairro.setBounds(425, 288, 40, 20);
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblBairro);

		fTxtBairro = new JFormattedTextField();
		JTextFieldLimit limitDocument_bairro = new JTextFieldLimit(30, "texto");
		fTxtBairro.setDocument(limitDocument_bairro);
		fTxtBairro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterBairro) {
				if (enterBairro.getKeyCode() == enterBairro.VK_ENTER) {
					fTxtReferencia.requestFocus();
				}
			}
		});
		fTxtBairro.setEditable(false);
		fTxtBairro.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickBairro) {
				text_tools.move_cursor_inicio(fTxtBairro);
			}
		});
		fTxtBairro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtBairro.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtBairro.setBounds(466, 288, 243, 20);
		add(fTxtBairro);

		lblCelular = new JLabel("Celular");
		lblCelular.setBounds(16, 361, 46, 20);
		lblCelular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblCelular);

		MaskFormatter mascara_celular = null;
		try {
			mascara_celular = new MaskFormatter("(##)#####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtCelular = new JFormattedTextField(mascara_celular);
		fTxtCelular.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterCelular) {
				if (enterCelular.getKeyCode() == enterCelular.VK_ENTER
						&& !fTxtCelular.getText().equals("(  )     -    ")) {
					fTxtTelFixo.requestFocus();
				}
			}
		});
		fTxtCelular.setEditable(false);
		fTxtCelular.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCelular) {
				fTxtCelular.setCaretPosition(0);
			}
		});
		fTxtCelular.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtCelular.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCelular.setBounds(62, 361, 104, 20);
		add(fTxtCelular);

		lblTelFixo = new JLabel("Tel. Fixo");
		lblTelFixo.setBounds(194, 362, 50, 20);
		lblTelFixo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblTelFixo);

		MaskFormatter mascara_telefone = null;
		try {
			mascara_telefone = new MaskFormatter("(##)####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtTelFixo = new JFormattedTextField(mascara_telefone);
		fTxtTelFixo.setEditable(false);
		fTxtTelFixo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickTelefone) {
				fTxtTelFixo.setCaretPosition(0);
			}
		});

		fTxtTelFixo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtTelFixo.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtTelFixo.setBounds(251, 361, 104, 20);
		add(fTxtTelFixo);

		lblEmai = new JLabel("Email");
		lblEmai.setBounds(426, 326, 34, 20);
		lblEmai.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblEmai);

		fTxtEmail = new JFormattedTextField();
		JTextFieldLimit limitDocument_email = new JTextFieldLimit(40, "texto");
		fTxtEmail.setDocument(limitDocument_email);
		fTxtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterEmail) {
				if (enterEmail.getKeyCode() == enterEmail.VK_ENTER) {
					fTxtCelular.requestFocus();
				}
			}
		});
		fTxtEmail.setEditable(false);
		fTxtEmail.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEmail) {
				text_tools.move_cursor_inicio(fTxtEmail);
			}
		});

		fTxtEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtEmail.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtEmail.setBounds(466, 325, 243, 20);
		add(fTxtEmail);

		btnSalvar = new JButton("Salvar");
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSalvar.setBounds(482, 393, 108, 29);
		btnSalvar.setIcon(icones.getIcone_salvar());
		btnSalvar.setVisible(false);
		add(btnSalvar);

		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.setBounds(595, 393, 114, 29);
		btnCancelar.setIcon(icones.getIcone_cancelar());
		btnCancelar.setVisible(false);
		add(btnCancelar);

		lblPesquisarPor = new JLabel("Pesquisar por");
		lblPesquisarPor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPesquisarPor.setBounds(16, 456, 89, 20);
		add(lblPesquisarPor);

		separador_2 = new JSeparator();
		separador_2.setBounds(15, 437, 239, 9);
		add(separador_2);

		separador_3 = new JSeparator();
		separador_3.setBounds(476, 437, 232, 9);
		add(separador_3);

		btnLimpaDocumento = new JButton();
		btnLimpaDocumento.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickLimpaDocumento) {
				limpar_campos();
			}
		});
		btnLimpaDocumento.setBounds(511, 154, 24, 19);
		btnLimpaDocumento.setIcon(icones.getIcone_limpar());
		btnLimpaDocumento.setVisible(false);
		add(btnLimpaDocumento);

		lblInformacoesBasicas = new JLabel("Informa\u00E7\u00F5es B\u00E1sicas");
		lblInformacoesBasicas.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformacoesBasicas.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblInformacoesBasicas.setBounds(15, 110, 188, 29);
		add(lblInformacoesBasicas);

		separador_1 = new JSeparator();
		separador_1.setBounds(207, 127, 502, 9);
		add(separador_1);

		lblInfoEndereco = new JLabel("Endere\u00E7o e Contato");
		lblInfoEndereco.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoEndereco.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblInfoEndereco.setBounds(10, 215, 184, 29);
		add(lblInfoEndereco);

		separador_clientes_4 = new JSeparator();
		separador_clientes_4.setBounds(194, 234, 516, 9);
		add(separador_clientes_4);

		btnReload = new JButton();
		btnReload.setIcon(icones.getIcone_reload());
		btnReload.setBounds(675, 454, 34, 22);
		add(btnReload);

		lblObg_nome = new JLabel("*");
		lblObg_nome.setForeground(Color.RED);
		lblObg_nome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObg_nome.setBounds(375, 196, 20, 15);
		add(lblObg_nome);

		lblObg_celular = new JLabel("*");
		lblObg_celular.setForeground(Color.RED);
		lblObg_celular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObg_celular.setBounds(166, 371, 20, 15);
		add(lblObg_celular);

		lblEsc = new JLabel("Esc:");
		lblEsc.setFont(new Font("Arial", Font.BOLD, 12));
		lblEsc.setBounds(16, 629, 30, 14);
		add(lblEsc);

		lblCancelar = new JLabel("Cancelar");
		lblCancelar.setForeground(Color.GRAY);
		lblCancelar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCancelar.setBounds(42, 629, 53, 14);
		add(lblCancelar);

		lblF1 = new JLabel("F1:");
		lblF1.setFont(new Font("Arial", Font.BOLD, 12));
		lblF1.setBounds(170, 629, 21, 14);
		add(lblF1);

		lblNovo = new JLabel("Novo");
		lblNovo.setForeground(Color.BLUE);
		lblNovo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNovo.setBounds(187, 629, 35, 14);
		add(lblNovo);

		lblF5 = new JLabel("F5:");
		lblF5.setFont(new Font("Arial", Font.BOLD, 12));
		lblF5.setBounds(473, 629, 21, 14);
		add(lblF5);

		lblRecarregar = new JLabel("Recarregar");
		lblRecarregar.setForeground(new Color(0, 128, 0));
		lblRecarregar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRecarregar.setBounds(490, 629, 65, 14);
		add(lblRecarregar);

		lblF3 = new JLabel("F3:");
		lblF3.setFont(new Font("Arial", Font.BOLD, 12));
		lblF3.setBounds(320, 629, 21, 14);
		add(lblF3);

		lblEditar = new JLabel("Editar");
		lblEditar.setForeground(new Color(139, 69, 19));
		lblEditar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEditar.setBounds(338, 629, 35, 14);
		add(lblEditar);

		lblF12 = new JLabel("F12:");
		lblF12.setFont(new Font("Arial", Font.BOLD, 12));
		lblF12.setBounds(639, 629, 26, 14);
		add(lblF12);

		lblExcluir = new JLabel("Excluir");
		lblExcluir.setForeground(Color.RED);
		lblExcluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblExcluir.setBounds(664, 629, 42, 14);
		add(lblExcluir);

	}

	public void ativar_campos() {
		fTxtDocumento.requestFocus();
		checkBoxJuridica.setEnabled(true);
		fTxtDocumento.setEditable(true);
		fTxtIe.setEditable(true);
		fTxtNomePessoa.setEditable(true);
		fTxtApelido.setEditable(true);
		fTxtCep.setEditable(true);
		fTxtCidade.setEditable(true);
		fTxtEndereco.setEditable(true);
		fTxtNumero.setEditable(true);
		fTxtReferencia.setEditable(true);
		fTxtBairro.setEditable(true);
		fTxtCelular.setEditable(true);
		fTxtTelFixo.setEditable(true);
		fTxtEmail.setEditable(true);
		btnNovo.setEnabled(false);
		btnCancelar.setVisible(true);
		btnSalvar.setVisible(true);
	}

	public void desativar_campos() {
		checkBoxJuridica.setEnabled(false);
		fTxtDocumento.setEditable(false);
		fTxtIe.setEditable(false);
		fTxtNomePessoa.setEditable(false);
		fTxtApelido.setEditable(false);
		fTxtCep.setEditable(false);
		fTxtCidade.setEditable(false);
		fTxtEndereco.setEditable(false);
		fTxtNumero.setEditable(false);
		fTxtReferencia.setEditable(false);
		fTxtBairro.setEditable(false);
		fTxtCelular.setEditable(false);
		fTxtTelFixo.setEditable(false);
		fTxtEmail.setEditable(false);
		btnCancelar.setVisible(false);
		btnSalvar.setVisible(false);
		btnNovo.setEnabled(true);
	}

	public void limpar_campos() {
		checkBoxJuridica.setSelected(false);
		txtCodigo.setText(null);
		fTxtDocumento.setText(null);
		fTxtIe.setText(null);
		fTxtNomePessoa.setText(null);
		fTxtApelido.setText(null);
		fTxtNumero.setText(null);
		fTxtReferencia.setText(null);
		fTxtCelular.setText(null);
		fTxtTelFixo.setText(null);
		fTxtEmail.setText(null);
		limpar_endereco();
	}

	public void pega_dados_pessoa_juridica() {
		Cliente cliente = new Cliente();
		String documento;
		if (fTxtDocumento.getText().equals("   .   .   -  ") && fTxtDocumento.getText().equals("  .   .   /    -  ")) {
			documento = null;
		} else {
			documento = fTxtDocumento.getText().trim();
		}

		if (documento != null) {
			if (cliente_dao.validarDocumento(documento, txtCodigo.getText().trim()) == null) {
				if (documento.length() > 14) {
					if (checkBoxJuridica.isSelected()) {
						Busca_cnpj api_cnpj = new Busca_cnpj();
						documento = fTxtDocumento.getText().trim().replaceAll("[./-]", "");
						cliente = api_cnpj.buscar_cnpj(documento);

						if (cliente != null) {
							fTxtNomePessoa.setText(cliente.getNome());
							fTxtApelido.setText(cliente.getApelido());
							fTxtCep.setText(cliente.getCep());
							buscaCep(fTxtCep.getText().trim());
							fTxtNumero.setText(cliente.getNumero());
							fTxtReferencia.setText(cliente.getReferencia());
							fTxtTelFixo.setText(cliente.getTelefone());
							fTxtEmail.setText(cliente.getEmail());
							btnLimpaDocumento.setVisible(true);
						}
					}
				}
			}

		}
	}

	public boolean salvar_pessoa(Pessoa pessoa) {
		Boolean salvo = true;
		if (pessoa instanceof Cliente) {
			tipo_pessoa = "Cliente";
			if (pessoa.getId() == null) {
				pessoa = cliente_dao.inserirCliente(pessoa);
				salvo = pessoa.getId() != null;
			} else {
				salvo = cliente_dao.alterar_cliente(pessoa);
			}
		} else {
			tipo_pessoa = "Fornecedor";
			if (pessoa.getId() == null) {
				pessoa = fornecedor_dao.inserirFornecedor(pessoa);
				salvo = pessoa.getId() != null;
			} else {
				salvo = fornecedor_dao.alterarFornecedor(pessoa);
			}
		}

		if (salvo) {
			JOptionPane
					.showMessageDialog(
							fTxtCidade, tipo_pessoa + " salvo corretamente." + "\nCódigo: " + pessoa.getId()
									+ "\nNome: " + pessoa.getNome(),
							"Cadastro de " + tipo_pessoa, JOptionPane.WARNING_MESSAGE);
			cancelar_pessoa();
			return true;
		} else {
			return false;
		}
	}

	public void nova_pessoa(JTable tabela) {
		if (btnNovo.isEnabled()) {
			ativar_campos();
			limpar_campos();
			checkBoxJuridica.setSelected(false);
			btnExcluir.setEnabled(false);
			btnEditar.setEnabled(false);
			btnNovo.setEnabled(false);
			fTxtDocumento.requestFocus();
			if (tabela != null) {
				tabela.clearSelection();
			}
		}
	}

	public void cancelar_pessoa() {
		desativar_campos();
		limpar_campos();
		btnNovo.setVisible(true);
		btnEditar.setVisible(true);
		btnExcluir.setVisible(true);
		checkBoxJuridica.setSelected(false);
		fTxtNomePessoa.setBorder(new LineBorder(Color.lightGray));
		fTxtCelular.setBorder(new LineBorder(Color.lightGray));
		btnNovo.requestFocus();
	}

	public boolean excluir_pessoa(Pessoa pessoa) {
		if (btnExcluir.isEnabled() && pessoa != null) {
			pessoa = monta_pessoa(pessoa);
			boolean flag;
			Boolean excluido = false;
			String tipo_pessoa = "fornecedor";

			if (pessoa instanceof Cliente) {
				tipo_pessoa = "cliente";

				if (cliente_dao.cliente_com_orcamento(pessoa.getId().toString())) {
					JOptionPane.showMessageDialog(fTxtCidade,
							"Impossível excluir cliente.\nO cliente selecionado possui pelo menos 1 orçamento salvo em seu nome.",
							"Exclusão de clientes.", JOptionPane.WARNING_MESSAGE);
					return false;
				}

				if (pessoa.getId().equals(configuracoes.getConsumidor_final().getId())) {
					JOptionPane.showMessageDialog(fTxtCidade,
							"Impossível excluir cliente.\nO cliente selecionado está sendo usado como consumidor final nas configurações do sistema.",
							"Exclusão de clientes.", JOptionPane.WARNING_MESSAGE);
					return false;
				}
			}

			int opcao = JOptionPane.showConfirmDialog(fTxtCidade,
					"Deseja excluir o " + tipo_pessoa + " abaixo?\n" + "Código: " + pessoa.getId() + "\n" + "Nome: "
							+ pessoa.getNome(),
					"Exclusão de " + tipo_pessoa, JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

			flag = opcao == JOptionPane.YES_OPTION;
			if (flag) {
				switch (tipo_pessoa) {
				case "cliente":
					excluido = cliente_dao.excluirCliente(pessoa.getId());
					break;
				case "fornecedor":
					excluido = fornecedor_dao.excluirFornecedor(pessoa.getId());
					break;
				default:
					break;
				}
				if (excluido) {
					JOptionPane.showMessageDialog(fTxtCidade, tipo_pessoa + " excluído com sucesso.",
							"Exclusão de " + tipo_pessoa + ".", JOptionPane.NO_OPTION);
					fTxtPesquisa.setText(null);
					limpar_campos();
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	public Pessoa monta_pessoa(Pessoa pessoa) {
		Integer id = null;
		String documento = null;
		String inscricao_estadual = null;
		String nome = null;
		String apelido = null;
		String cep = null;
		String cidade = null;
		String endereco = null;
		String numero = null;
		String referencia = null;
		String bairro = null;
		String celular = null;
		String telefone = null;
		String email = null;

		if (!txtCodigo.getText().isBlank()) {
			id = Integer.parseInt(txtCodigo.getText().trim());
		}

		nome = fTxtNomePessoa.getText().trim();
		if (!fTxtApelido.getText().isBlank()) {
			apelido = fTxtApelido.getText().trim();
		}

		if (!fTxtDocumento.getText().equals("  .   .   /    -  ")
				&& !fTxtDocumento.getText().equals("   .   .   -  ")) {
			documento = fTxtDocumento.getText().trim();
		} else {
			documento = null;
		}

		if (fTxtIe.isVisible()) {
			if (!fTxtIe.getText().equals("         .  -  ")) {
				inscricao_estadual = fTxtIe.getText().trim();
			}
		}

		if (!fTxtCep.getText().equals("     -   ")) {
			cep = fTxtCep.getText().trim();
		}

		if (!fTxtCidade.getText().isBlank()) {
			cidade = fTxtCidade.getText().trim();
		}

		if (!fTxtEndereco.getText().isBlank()) {
			endereco = fTxtEndereco.getText().trim();
		}

		if (!fTxtReferencia.getText().isBlank()) {
			referencia = fTxtReferencia.getText().trim();
		}

		if (!fTxtNumero.getText().isBlank()) {
			numero = fTxtNumero.getText().trim();
		}

		if (!fTxtBairro.getText().isBlank()) {
			bairro = fTxtBairro.getText().trim();
		}

		if (!fTxtEmail.getText().isBlank()) {
			email = fTxtEmail.getText().trim();
		}

		if (!fTxtCelular.getText().equals("(  )     -    ")) {
			celular = fTxtCelular.getText().trim();
		}

		if (!fTxtTelFixo.getText().equals("(  )    -    ")) {
			telefone = fTxtTelFixo.getText().trim();
		}

		Date data_cadastro = new java.sql.Date(System.currentTimeMillis());

		if (pessoa instanceof Cliente) {
			pessoa = new Cliente(id, nome, apelido, checkBoxJuridica.isSelected(), documento, inscricao_estadual, cep,
					cidade, endereco, referencia, numero, bairro, email, celular, telefone, false, data_cadastro);
		} else {
			pessoa = new Fornecedor(id, nome, apelido, checkBoxJuridica.isSelected(), documento, inscricao_estadual,
					cep, cidade, endereco, referencia, numero, bairro, email, celular, telefone, false, data_cadastro,
					false, 0, null);
		}

		return pessoa;
	}

	public boolean valida_pessoa(Pessoa pessoa) {
		String tipo_pessoa = null;
		Boolean valido = true;

		if (pessoa instanceof Cliente) {
			tipo_pessoa = "cliente";
		} else {
			tipo_pessoa = "fornecedor";
		}

		valido = valida_documento(tipo_pessoa);

		if (fTxtNomePessoa.getText().isBlank() || fTxtCelular.getText().equals("(  )     -    ")) {
			if (fTxtNomePessoa.getText().isBlank()) {
				fTxtNomePessoa.setBorder(new LineBorder(Color.RED));
				JOptionPane.showMessageDialog(fTxtNomePessoa, "Necessário informar o nome do " + tipo_pessoa + ".",
						tipo_pessoa + " sem nome.", JOptionPane.WARNING_MESSAGE);
				valido = false;
			}

			if (fTxtCelular.getText().equals("(  )     -    ")) {
				fTxtCelular.setBorder(new LineBorder(Color.RED));
				JOptionPane.showMessageDialog(fTxtCelular, "Necessário informar o celular do " + tipo_pessoa + ".",
						tipo_pessoa + " sem celular.", JOptionPane.WARNING_MESSAGE);
				valido = false;
			}
		}

		return valido;
	}

	public void editar_pessoa() {
		ativar_campos();
		btnEditar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnNovo.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnSalvar.setVisible(true);
		btnCancelar.setVisible(true);
		fTxtDocumento.requestFocus();
	}

	public void buscaCep(String cep) {
		Pessoa endereco_pessoa = new Pessoa();
		Busca_cep busca_cep = new Busca_cep(cep);
		endereco_pessoa = busca_cep.busca_endereco(endereco_pessoa);
		fTxtBairro.setText(endereco_pessoa.getBairro());
		fTxtCidade.setText(endereco_pessoa.getCidade());
		fTxtEndereco.setText(endereco_pessoa.getEndereco());
		btnLimpaCep.setVisible(true);
	}

	public void limpar_endereco() {
		fTxtBairro.setText(null);
		fTxtCidade.setText(null);
		fTxtCep.setText(null);
		fTxtEndereco.setText(null);
		fTxtReferencia.setText(null);
		fTxtNumero.setText(null);
		btnLimpaCep.setVisible(false);
	}

	public Boolean valida_documento(String tipo_pessoa) {
		if (!fTxtDocumento.getText().equals("   .   .   -  ")
				&& !fTxtDocumento.getText().equals("  .   .   /    -  ")) {
			String documento = fTxtDocumento.getText().trim();

			String codigo = null;
			if (txtCodigo.getText().isBlank()) {
				codigo = null;
			} else {
				codigo = txtCodigo.getText().trim();
			}

			String nome_pessoa = null;

			switch (tipo_pessoa) {
			case "cliente":
				nome_pessoa = cliente_dao.validarDocumento(documento, codigo);
				break;
			case "fornecedor":
				nome_pessoa = fornecedor_dao.validarDocumento(documento, codigo);
			default:
				break;
			}

			if (nome_pessoa != null) {
				fTxtDocumento.requestFocus();
				JOptionPane.showMessageDialog(fTxtCidade,
						"Documento ja informado para o " + tipo_pessoa + " abaixo:" + "\n" + nome_pessoa,
						"Documento ja utilizado.", JOptionPane.WARNING_MESSAGE);
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	public void selecao_linha_tabela(JTable tabela, int linha_selecionada) {
		btnEditar.setEnabled(true);
		btnExcluir.setEnabled(true);

		if (tabela.getValueAt(tabela.getSelectedRow(), 11) != null) {
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
		fTxtDocumento.setText((String) tabela.getValueAt(linha_selecionada, 11));
		fTxtIe.setText((String) tabela.getValueAt(linha_selecionada, 12));
		fTxtNomePessoa.setText((String) tabela.getValueAt(linha_selecionada, 2));
		fTxtApelido.setText((String) tabela.getValueAt(linha_selecionada, 3));
		fTxtCep.setText((String) tabela.getValueAt(linha_selecionada, 10));
		fTxtCidade.setText((String) tabela.getValueAt(linha_selecionada, 8));
		fTxtEndereco.setText((String) tabela.getValueAt(linha_selecionada, 5));
		fTxtNumero.setText((String) tabela.getValueAt(linha_selecionada, 6));
		fTxtReferencia.setText((String) tabela.getValueAt(linha_selecionada, 7));
		fTxtBairro.setText((String) tabela.getValueAt(linha_selecionada, 9));
		fTxtCelular.setText((String) tabela.getValueAt(linha_selecionada, 4));
		fTxtTelFixo.setText((String) tabela.getValueAt(linha_selecionada, 14));
		fTxtEmail.setText((String) tabela.getValueAt(linha_selecionada, 13));

		String cod = tabela.getValueAt(linha_selecionada, 0).toString();
		txtCodigo.setText(cod);
	}

	// Teclas de atalho
	public void tecla_pressionada(Pessoa pessoa_atalho) {
		InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "novo");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelar");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "editar");

		setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);

		getActionMap().put("cancelar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_cancelar) {
				cancelar_pessoa();
			}
		});

		getActionMap().put("novo", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_novo) {
				ativar_campos();
			}
		});

		getActionMap().put("editar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_editar) {
				if (btnEditar.isEnabled()) {
					editar_pessoa();
				}
			}
		});

	}
}
