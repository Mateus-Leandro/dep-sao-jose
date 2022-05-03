package view.panels;

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
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

import api_tools.Busca_cep;
import api_tools.Busca_cnpj;
import dao.ClienteDAO;
import dao.ConfiguracaoDAO;
import entities.configuracoes.Configuracoes;
import entities.pessoa.Cliente;
import icons.Icones;
import tables.tableModels.ModeloTabelaClientes;
import tables.tableRenders.Render_tabela_clientes;
import tools.JTextFieldLimit;
import tools.Jtext_tools;

public class Panel_clientes extends JPanel {
	private JLabel lblCadastroDeClientes;
	private JTextField txtCodigo;
	private JLabel lblCodigoCliente;
	private JCheckBox checkBoxJuridica;
	private JLabel lblDocumento;
	private JFormattedTextField fTxtDocumento;
	private JLabel lblIe;
	private JFormattedTextField fTxtIe;
	private JSeparator separador_clientes;
	private JLabel lblNome;
	private JFormattedTextField fTxtNomeCliente;
	private JLabel lblApelido;
	private JFormattedTextField fTxtApelido;
	private JLabel lblCep;
	private JButton btnNovo;
	private JFormattedTextField fTxtCep;
	private JButton btnLimpaCep;
	private JFormattedTextField fTxtCidade;
	private JLabel lblCidade;
	private JLabel lblEndereco;
	private JFormattedTextField fTxtEndereco;
	private JLabel lblNumero;
	private JFormattedTextField fTxtNumero;
	private JLabel lblReferencia;
	private JFormattedTextField fTxtReferencia;
	private JLabel lblBairro;
	private JFormattedTextField fTxtBairro;
	private JLabel lblCelular;
	private JFormattedTextField fTxtCelular;
	private JLabel lblTelFixo;
	private JFormattedTextField fTxtTelFixo;
	private JLabel lblEmai;
	private JFormattedTextField fTxtEmail;
	private Icones icones = new Icones();
	private JScrollPane scrollPane;
	private JTable tabelaClientes;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JLabel lblPesquisarPor;
	private JComboBox<String> cbxTipoPesquisa;
	private JFormattedTextField fTxtPesquisa;
	private JLabel lblClienteCadastrados;
	private JSeparator separador_clientes_1;
	private JSeparator separador_clientes_2;
	private MaskFormatter mascara_cpf = null;
	private MaskFormatter mascara_cnpj = null;
	private ArrayList<Cliente> lista_clientes = new ArrayList<Cliente>();
	private ModeloTabelaClientes modelo = new ModeloTabelaClientes(lista_clientes);
	private JButton btnLimpaDocumento;
	private JCheckBox checkBoxBloqueado;
	private JLabel lblInformacoesBasicas;
	private JSeparator separador_clientes_3;
	private JLabel lblInfoEndereco;
	private JSeparator separador_clientes_4;
	private JButton btnReload;
	private Jtext_tools text_tools = new Jtext_tools();
	private JLabel lblObg_nome;
	private JLabel lblObg_celular;
	private ClienteDAO cliente_dao = new ClienteDAO();
	private Cliente cliente = new Cliente();
	private Render_tabela_clientes render = new Render_tabela_clientes();
	private ConfiguracaoDAO conf_dao = new ConfiguracaoDAO();
	private Configuracoes configuracoes = conf_dao.busca_configuracoes();
	private JLabel lblEsc;
	private JLabel lblCancelar;
	private JLabel lblF1;
	private JLabel lblNovo;
	private JLabel lblF5;
	private JLabel lblRecarregarCliente;
	private JLabel lblF3;
	private JLabel lblEditar;
	private JLabel lblF12;
	private JLabel lblExcluir;

	/**
	 * Create the panel.
	 */
	public Panel_clientes() {

		setBorder(null);
		setLayout(null);

		// Atalhos do teclado
		tecla_pressionada();

		btnEditar = new JButton("Editar");
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEditar) {
				editar_cliente();
			}
		});
		btnEditar.setEnabled(false);
		btnEditar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEditar.setBounds(16, 393, 101, 29);
		btnEditar.setIcon(icones.getIcone_editar());
		add(btnEditar);

		btnExcluir = new JButton("Excluir");
		btnExcluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickExcluir) {
				if (btnExcluir.isEnabled()) {

					if (!cliente_dao.cliente_com_orcamento(
							tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 0).toString())) {
						excluir_cliente();
					} else {
						JOptionPane.showMessageDialog(fTxtCidade,
								"Impossível excluir cilente.\nO cliente selecionado possui pelo menos 1 orçamento salvo em seu nome.",
								"Exclusão de cliente", JOptionPane.WARNING_MESSAGE);
					}
				}

			}
		});
		btnExcluir.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnExcluir.setBounds(132, 393, 104, 29);
		btnExcluir.setIcon(icones.getIcone_excluir());
		btnExcluir.setEnabled(false);
		add(btnExcluir);

		lblCadastroDeClientes = new JLabel("Cadastro de Clientes");
		lblCadastroDeClientes.setBounds(247, 11, 244, 29);
		lblCadastroDeClientes.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastroDeClientes.setFont(new Font("Tahoma", Font.BOLD, 24));
		add(lblCadastroDeClientes);

		fTxtPesquisa = new JFormattedTextField();
		JTextFieldLimit limitDocument_pesquisa = new JTextFieldLimit(30, "texto");
		fTxtPesquisa.setDocument(limitDocument_pesquisa);
		fTxtPesquisa.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if (fTxtPesquisa.getText().trim().isEmpty()) {
					fTxtPesquisa.setCaretPosition(0);
				}
			}
		});
		fTxtPesquisa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent escreveBarraPesquisa) {
				recarregarTabela();
			}
		});
		fTxtPesquisa.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtPesquisa.setBounds(211, 464, 454, 20);
		add(fTxtPesquisa);

		txtCodigo = new JTextField();
		txtCodigo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtCodigo.setBounds(63, 152, 65, 20);
		txtCodigo.setEditable(false);
		add(txtCodigo);
		txtCodigo.setColumns(10);

		lblCodigoCliente = new JLabel("C\u00F3digo");
		lblCodigoCliente.setBounds(16, 154, 46, 20);
		lblCodigoCliente.setToolTipText("Gerado Autom\u00E1ticamente");
		lblCodigoCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblCodigoCliente);

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

				if (!btnNovo.isVisible()) {
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
						fTxtNomeCliente.requestFocus();
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
					fTxtNomeCliente.requestFocus();
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

		separador_clientes = new JSeparator();
		separador_clientes.setBounds(10, 50, 698, 9);
		add(separador_clientes);

		lblNome = new JLabel("Nome");
		lblNome.setBounds(15, 188, 40, 20);
		lblNome.setToolTipText("");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblNome);

		fTxtNomeCliente = new JFormattedTextField();
		JTextFieldLimit limitDocument_nome = new JTextFieldLimit(45, "texto");
		fTxtNomeCliente.setDocument(limitDocument_nome);
		fTxtNomeCliente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterNome) {
				if (!fTxtNomeCliente.getText().trim().isEmpty() && enterNome.getKeyCode() == enterNome.VK_ENTER) {
					fTxtApelido.requestFocus();
				}
			}
		});
		fTxtNomeCliente.setEditable(false);
		fTxtNomeCliente.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNomeCliente) {
				text_tools.move_cursor_inicio(fTxtNomeCliente);
			}
		});
		fTxtNomeCliente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtNomeCliente.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeCliente.setBounds(57, 186, 317, 20);
		add(fTxtNomeCliente);

		lblApelido = new JLabel("Apelido");
		lblApelido.setBounds(398, 189, 46, 20);
		lblApelido.setToolTipText("");
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
		lblCep.setToolTipText("");
		lblCep.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblCep);

		btnNovo = new JButton("Novo");
		btnNovo.setBounds(10, 70, 89, 29);
		btnNovo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNovo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNovo) {
				novo_cliente();
			}
		});
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
						buscaCep();
						btnLimpaCep.setVisible(true);
					} else {
						btnLimpaCep.setVisible(false);
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
				fTxtBairro.setText(null);
				fTxtCidade.setText(null);
				fTxtCep.setText(null);
				fTxtEndereco.setText(null);
				fTxtReferencia.setText(null);
				fTxtNumero.setText(null);
				btnLimpaCep.setVisible(false);
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
		lblCidade.setToolTipText("");
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblCidade);

		lblEndereco = new JLabel("Endereco");
		lblEndereco.setBounds(16, 295, 65, 20);
		lblEndereco.setToolTipText("");
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
		lblNumero.setToolTipText("");
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
		lblReferencia.setToolTipText("");
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
		lblBairro.setToolTipText("");
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
		lblCelular.setToolTipText("");
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
		lblTelFixo.setToolTipText("");
		lblTelFixo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblTelFixo);

		MaskFormatter mascara_telefone = null;
		try {
			mascara_telefone = new MaskFormatter("(##)####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtTelFixo = new JFormattedTextField(mascara_telefone);
		fTxtTelFixo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterTelFixo) {
				if (enterTelFixo.getKeyCode() == enterTelFixo.VK_ENTER) {
					if (valida_documento()) {
						valida_cliente();
						btnNovo.requestFocus();
					}

				}
			}
		});
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
		lblEmai.setToolTipText("");
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

		// alimentando lista de clientes.
		lista_clientes = alimentarListaClientes(lista_clientes);

		modelo = new ModeloTabelaClientes(lista_clientes);
		tabelaClientes = new JTable(modelo);
		tabelaClientes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tabelaClientes.setSurrendersFocusOnKeystroke(true);
		tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaClientes.getTableHeader().setReorderingAllowed(false);
		tabelaClientes.setAutoResizeMode(tabelaClientes.AUTO_RESIZE_OFF);
		tabelaClientes.setBounds(14, 325, 694, 216);
		tabelaClientes.setDefaultRenderer(Object.class, render);

		ConfiguraLarguraColunaTabela(tabelaClientes);

		scrollPane = new JScrollPane(tabelaClientes);
		scrollPane.setBounds(16, 495, 693, 140);
		add(scrollPane);

		tabelaClientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent selecaoLinhaTabela) {

				ListSelectionModel lsm = (ListSelectionModel) selecaoLinhaTabela.getSource();

				if (!lsm.isSelectionEmpty() && btnNovo.isVisible()) {

					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);

					// Verificando se o documento do cliente é um CPF ou CNPJ

					if (tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 11) != null) {

						if (tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 11).toString().length() > 14) {
							checkBoxJuridica.setSelected(true);
							fTxtIe.setVisible(true);
							lblIe.setVisible(true);
						} else {
							checkBoxJuridica.setSelected(false);
							fTxtIe.setVisible(false);
							lblIe.setVisible(false);
						}

					}
					checkBoxBloqueado
							.setSelected((Boolean) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 1));
					fTxtDocumento.setText((String) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 11));
					fTxtIe.setText((String) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 12));
					fTxtNomeCliente.setText((String) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 2));
					fTxtApelido.setText((String) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 3));
					fTxtCep.setText((String) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 10));
					fTxtCidade.setText((String) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 8));
					fTxtEndereco.setText((String) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 5));
					fTxtNumero.setText((String) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 6));
					fTxtReferencia.setText((String) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 7));
					fTxtBairro.setText((String) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 9));
					fTxtCelular.setText((String) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 4));
					fTxtTelFixo.setText((String) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 14));
					fTxtEmail.setText((String) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 13));

					String cod = tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 0).toString();

					txtCodigo.setText(cod);

				} else {
					btnEditar.setEnabled(false);
					btnExcluir.setEnabled(false);
				}

			}
		});

		btnSalvar = new JButton("Salvar");
		btnSalvar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickSalvarCliente) {
				if (valida_documento()) {
					valida_cliente();
				}
			}
		});
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSalvar.setBounds(482, 393, 108, 29);
		btnSalvar.setIcon(icones.getIcone_salvar());
		btnSalvar.setVisible(false);
		add(btnSalvar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancelar) {
				cancelar_cliente();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.setBounds(595, 393, 114, 29);
		btnCancelar.setIcon(icones.getIcone_cancelar());
		btnCancelar.setVisible(false);
		add(btnCancelar);

		lblPesquisarPor = new JLabel("Pesquisar por");
		lblPesquisarPor.setToolTipText("");
		lblPesquisarPor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPesquisarPor.setBounds(16, 464, 89, 20);
		add(lblPesquisarPor);

		cbxTipoPesquisa = new JComboBox();
		cbxTipoPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxTipoPesquisa.setModel(new DefaultComboBoxModel<String>(new String[] { "Nome", "Apelido", "C\u00F3digo" }));
		cbxTipoPesquisa.setSelectedIndex(0);
		cbxTipoPesquisa.setBounds(105, 462, 96, 22);
		add(cbxTipoPesquisa);

		lblClienteCadastrados = new JLabel("Clientes Cadastrados");
		lblClienteCadastrados.setHorizontalAlignment(SwingConstants.CENTER);
		lblClienteCadastrados.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblClienteCadastrados.setBounds(254, 422, 225, 29);
		add(lblClienteCadastrados);

		separador_clientes_1 = new JSeparator();
		separador_clientes_1.setBounds(15, 437, 239, 9);
		add(separador_clientes_1);

		separador_clientes_2 = new JSeparator();
		separador_clientes_2.setBounds(476, 437, 232, 9);
		add(separador_clientes_2);

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

		checkBoxBloqueado = new JCheckBox("Cliente Bloqueado");
		checkBoxBloqueado.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent alteraBloqueado) {
				if (checkBoxBloqueado.isSelected()) {
					checkBoxBloqueado.setForeground(Color.red);
				} else {
					checkBoxBloqueado.setForeground(Color.black);
				}
			}
		});
		checkBoxBloqueado.setForeground(Color.BLACK);
		checkBoxBloqueado.setFont(new Font("Tahoma", Font.PLAIN, 16));
		checkBoxBloqueado.setEnabled(false);
		checkBoxBloqueado.setBounds(549, 73, 159, 23);
		add(checkBoxBloqueado);

		lblInformacoesBasicas = new JLabel("Informa\u00E7\u00F5es B\u00E1sicas");
		lblInformacoesBasicas.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformacoesBasicas.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblInformacoesBasicas.setBounds(15, 110, 188, 29);
		add(lblInformacoesBasicas);

		separador_clientes_3 = new JSeparator();
		separador_clientes_3.setBounds(207, 127, 502, 9);
		add(separador_clientes_3);

		lblInfoEndereco = new JLabel("Endere\u00E7o e Contato");
		lblInfoEndereco.setHorizontalAlignment(SwingConstants.CENTER);
		lblInfoEndereco.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblInfoEndereco.setBounds(10, 215, 184, 29);
		add(lblInfoEndereco);

		separador_clientes_4 = new JSeparator();
		separador_clientes_4.setBounds(194, 234, 516, 9);
		add(separador_clientes_4);

		btnReload = new JButton();
		btnReload.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickRecarregarTabela) {
				recarregarTabela();
			}
		});
		btnReload.setIcon(icones.getIcone_reload());
		btnReload.setBounds(675, 462, 34, 22);
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
		lblEsc.setBounds(16, 638, 30, 14);
		add(lblEsc);

		lblCancelar = new JLabel("Cancelar");
		lblCancelar.setForeground(Color.GRAY);
		lblCancelar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCancelar.setBounds(42, 638, 53, 14);
		add(lblCancelar);

		lblF1 = new JLabel("F1:");
		lblF1.setFont(new Font("Arial", Font.BOLD, 12));
		lblF1.setBounds(149, 638, 21, 14);
		add(lblF1);

		lblNovo = new JLabel("Novo");
		lblNovo.setForeground(Color.BLUE);
		lblNovo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNovo.setBounds(166, 638, 35, 14);
		add(lblNovo);

		lblF5 = new JLabel("F5:");
		lblF5.setFont(new Font("Arial", Font.BOLD, 12));
		lblF5.setBounds(425, 638, 21, 14);
		add(lblF5);

		lblRecarregarCliente = new JLabel("Recarregar Clientes");
		lblRecarregarCliente.setForeground(new Color(0, 128, 0));
		lblRecarregarCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRecarregarCliente.setBounds(442, 638, 128, 14);
		add(lblRecarregarCliente);

		lblF3 = new JLabel("F3:");
		lblF3.setFont(new Font("Arial", Font.BOLD, 12));
		lblF3.setBounds(288, 638, 21, 14);
		add(lblF3);

		lblEditar = new JLabel("Editar");
		lblEditar.setForeground(new Color(139, 69, 19));
		lblEditar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblEditar.setBounds(306, 638, 35, 14);
		add(lblEditar);

		lblF12 = new JLabel("F12:");
		lblF12.setFont(new Font("Arial", Font.BOLD, 12));
		lblF12.setBounds(639, 638, 26, 14);
		add(lblF12);

		lblExcluir = new JLabel("Excluir");
		lblExcluir.setForeground(Color.RED);
		lblExcluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblExcluir.setBounds(664, 638, 42, 14);
		add(lblExcluir);

	}

	// Configurando largura das colunas da tabela
	public void ConfiguraLarguraColunaTabela(JTable tabelaProdutos) {
		tabelaClientes.getColumnModel().getColumn(0).setPreferredWidth(40); // Codigo
		tabelaClientes.getColumnModel().getColumn(1).setPreferredWidth(50); // Bloqueado
		tabelaClientes.getColumnModel().getColumn(2).setPreferredWidth(150); // Nome
		tabelaClientes.getColumnModel().getColumn(3).setPreferredWidth(150); // Apelido
		tabelaClientes.getColumnModel().getColumn(4).setPreferredWidth(100); // Celular
		tabelaClientes.getColumnModel().getColumn(5).setPreferredWidth(180); // Endereco
		tabelaClientes.getColumnModel().getColumn(6).setPreferredWidth(70); // Numero casa
		tabelaClientes.getColumnModel().getColumn(7).setPreferredWidth(180); // Referencia
		tabelaClientes.getColumnModel().getColumn(8).setPreferredWidth(160); // Cidade
		tabelaClientes.getColumnModel().getColumn(9).setPreferredWidth(160); // Bairro
		tabelaClientes.getColumnModel().getColumn(10).setPreferredWidth(80); // Cep
		tabelaClientes.getColumnModel().getColumn(11).setPreferredWidth(80); // Documento
		tabelaClientes.getColumnModel().getColumn(12).setPreferredWidth(80); // I.E.
		tabelaClientes.getColumnModel().getColumn(13).setPreferredWidth(150); // Email
		tabelaClientes.getColumnModel().getColumn(14).setPreferredWidth(90); // Telefone
		tabelaClientes.getColumnModel().getColumn(15).setPreferredWidth(70); // Data Cadastro

		// Definindo o sorter da tabela para ordenação das colunas.
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelo);
		tabelaClientes.setRowSorter(sorter);

		// Definindo o render da coluna para que seja pintada corretamente quando o
		// cliente está bloqueado.
		tabelaClientes.getColumnModel().getColumn(0).setCellRenderer(render);
	}

	public void ativar_campos() {
		checkBoxJuridica.setEnabled(true);
		fTxtDocumento.setEditable(true);
		fTxtIe.setEditable(true);
		fTxtNomeCliente.setEditable(true);
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
		btnCancelar.setVisible(true);
		btnSalvar.setVisible(true);
		checkBoxBloqueado.setEnabled(true);
	}

	public void desativar_campos() {
		checkBoxJuridica.setEnabled(false);
		fTxtDocumento.setEditable(false);
		fTxtIe.setEditable(false);
		fTxtNomeCliente.setEditable(false);
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
		checkBoxBloqueado.setEnabled(false);
	}

	public void limpar_campos() {
		checkBoxBloqueado.setSelected(false);
		checkBoxJuridica.setSelected(false);
		txtCodigo.setText(null);
		fTxtDocumento.setText(null);
		fTxtIe.setText(null);
		fTxtNomeCliente.setText(null);
		fTxtApelido.setText(null);
		fTxtCep.setText(null);
		fTxtCidade.setText(null);
		fTxtEndereco.setText(null);
		fTxtNumero.setText(null);
		fTxtReferencia.setText(null);
		fTxtBairro.setText(null);
		fTxtCelular.setText(null);
		fTxtTelFixo.setText(null);
		fTxtEmail.setText(null);
	}

	public Boolean valida_documento() {
		if (!fTxtDocumento.getText().equals("   .   .   -  ")
				&& !fTxtDocumento.getText().equals("  .   .   /    -  ")) {
			String documento = fTxtDocumento.getText().trim();

			String codigo = null;
			if (txtCodigo.getText().trim().isEmpty()) {
				codigo = null;
			} else {
				codigo = txtCodigo.getText().trim();
			}

			String nome_cliente = null;
			nome_cliente = cliente_dao.validarDocumento(documento, codigo);

			if (nome_cliente != null) {
				fTxtDocumento.requestFocus();
				JOptionPane.showMessageDialog(fTxtCidade,
						"Documento ja informado para o cliente abaixo:" + "\n" + nome_cliente,
						"Documento ja utilizado.", JOptionPane.WARNING_MESSAGE);
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	public void valida_cliente() {
		cliente = monta_cliente();

		if (fTxtNomeCliente.getText().trim().isEmpty() || fTxtCelular.getText().equals("(  )     -    ")) {
			if (fTxtNomeCliente.getText().trim().isEmpty()) {
				fTxtNomeCliente.setBorder(new LineBorder(Color.RED));
				JOptionPane.showMessageDialog(fTxtNomeCliente, "Necessário informar o nome do cliente.",
						"Cliente sem nome.", JOptionPane.WARNING_MESSAGE);
			}

			if (fTxtCelular.getText().equals("(  )     -    ")) {
				fTxtCelular.setBorder(new LineBorder(Color.RED));
				JOptionPane.showMessageDialog(fTxtCelular, "Necessário informar o celular do cliente.",
						"Cliente sem celular.", JOptionPane.WARNING_MESSAGE);
			}
		} else {

			if (txtCodigo.getText().trim().isEmpty()) {
				if (salvar_cliente(cliente)) {
					fTxtNomeCliente.setBorder(new LineBorder(Color.lightGray));
					fTxtCelular.setBorder(new LineBorder(Color.lightGray));
					limpar_campos();
					desativar_campos();
					recarregarTabela();
					btnNovo.setVisible(true);
					btnEditar.setVisible(true);
					btnExcluir.setVisible(true);
					JOptionPane.showMessageDialog(fTxtCidade, "Cliente cadastrado com sucesso." + "\nCódigo: "
							+ cliente.getId() + "\nNome: " + cliente.getNome(), "Novo cliente", JOptionPane.NO_OPTION);
				}
			} else {

				cliente.setId(Integer.parseInt(txtCodigo.getText().trim()));

				if (cliente_dao.alterar_cliente(cliente)) {
					fTxtNomeCliente.setBorder(new LineBorder(Color.lightGray));
					fTxtCelular.setBorder(new LineBorder(Color.lightGray));
					JOptionPane.showMessageDialog(fTxtCidade, "Cliente alterado com sucesso.", "Alteração de cliente",
							JOptionPane.NO_OPTION);
					limpar_campos();
					desativar_campos();
					recarregarTabela();
					tabelaClientes.clearSelection();
					btnEditar.setVisible(true);
					btnExcluir.setVisible(true);
					btnNovo.setVisible(true);
				}
			}
		}
	}

	public Cliente monta_cliente() {
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
		Boolean bloqueado = checkBoxBloqueado.isSelected();

		nome = fTxtNomeCliente.getText().trim();

		if (!fTxtApelido.getText().trim().isEmpty()) {
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

		Cliente novo_cliente = new Cliente(null, nome, apelido, checkBoxJuridica.isSelected(), documento,
				inscricao_estadual, cep, cidade, endereco, referencia, numero, bairro, email, celular, telefone,
				bloqueado, data_cadastro);

		return novo_cliente;
	}

	public boolean salvar_cliente(Cliente cliente) {
		cliente = cliente_dao.inserirCliente(cliente);

		if (cliente.getId() != null) {
			return true;
		} else {
			return false;
		}
	}

	public void novo_cliente() {
		if (btnNovo.isEnabled()) {
			ativar_campos();
			limpar_campos();
			checkBoxJuridica.setSelected(false);
			btnExcluir.setVisible(false);
			btnEditar.setVisible(false);
			btnNovo.setVisible(false);
			fTxtDocumento.requestFocus();
			tabelaClientes.clearSelection();
		}
	}

	public void cancelar_cliente() {
		tabelaClientes.clearSelection();
		desativar_campos();
		limpar_campos();
		btnNovo.setVisible(true);
		btnEditar.setVisible(true);
		btnExcluir.setVisible(true);
		checkBoxJuridica.setSelected(false);
		fTxtNomeCliente.setBorder(new LineBorder(Color.lightGray));
		fTxtCelular.setBorder(new LineBorder(Color.lightGray));
		btnNovo.requestFocus();
	}

	public void editar_cliente() {
		if (btnEditar.isEnabled()) {
			ativar_campos();
			btnEditar.setVisible(false);
			btnEditar.setEnabled(false);
			btnNovo.setVisible(false);
			btnExcluir.setVisible(false);
			btnSalvar.setVisible(true);
			btnCancelar.setVisible(true);
			fTxtDocumento.requestFocus();
		}
	}

	public void excluir_cliente() {
		if (btnExcluir.isEnabled()) {
			boolean flag;
			Integer codigo = (Integer) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 0);

			if (codigo.equals(configuracoes.getConsumidor_final().getId())) {
				JOptionPane.showMessageDialog(null,
						"Impossível excluir cliente.\nO cliente selecionado está sendo usado como consumidor final nas configurações do sistema.",
						"Exclusão de clientes.", JOptionPane.WARNING_MESSAGE);
			} else {

				int opcao = JOptionPane.showConfirmDialog(fTxtCidade,
						"Deseja excluir o cliente abaixo?\n" + "Código: " + codigo + "\n" + "Nome: "
								+ tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 2),
						"Exclusão de Cliente", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

				flag = opcao == JOptionPane.YES_OPTION;

				if (flag) {
					if (cliente_dao.excluirCliente(codigo)) {
						JOptionPane.showMessageDialog(fTxtCidade, "Cliente excluído com sucesso.",
								"Exclusão de clientes.", JOptionPane.NO_OPTION);
						recarregarTabela();
						limpar_campos();
					}
				}
			}
		}
	}

	public ArrayList<Cliente> alimentarListaClientes(ArrayList<Cliente> clientes) {

		String pesquisado = null;

		if (fTxtPesquisa.getText().trim().isEmpty()) {
			clientes = cliente_dao.listarClientes(clientes, null, null, 50);
			return clientes;
		} else {
			pesquisado = fTxtPesquisa.getText().trim() + "%";
			clientes = cliente_dao.listarClientes(clientes, cbxTipoPesquisa.getSelectedItem().toString(), pesquisado,
					50);
		}
		return clientes;

	}

	public void recarregarTabela() {
		lista_clientes.clear();
		lista_clientes = alimentarListaClientes(lista_clientes);
		modelo = new ModeloTabelaClientes(lista_clientes);
		tabelaClientes.setModel(modelo);
		ConfiguraLarguraColunaTabela(tabelaClientes);
		modelo.fireTableDataChanged();

	}

	public void buscaCep() {
		Cliente endereco_cliente = new Cliente();
		Busca_cep busca_cep = new Busca_cep(fTxtCep.getText().trim());
		endereco_cliente = busca_cep.busca_endereco(endereco_cliente);
		fTxtBairro.setText(endereco_cliente.getBairro());
		fTxtCidade.setText(endereco_cliente.getCidade());
		fTxtEndereco.setText(endereco_cliente.getEndereco());
		btnLimpaCep.setVisible(true);
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
							fTxtNomeCliente.setText(cliente.getNome());
							fTxtApelido.setText(cliente.getApelido());
							fTxtCep.setText(cliente.getCep());
							buscaCep();
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

	// Teclas de atalho
	public void tecla_pressionada() {
		InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "novo");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelar");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "excluir");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0), "editar");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "recarregar");

		setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);

		getActionMap().put("cancelar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_cancelar) {
				cancelar_cliente();
			}
		});

		getActionMap().put("excluir", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_excluir) {
				excluir_cliente();
			}
		});

		getActionMap().put("novo", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_novo) {
				novo_cliente();
			}
		});

		getActionMap().put("editar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_editar) {
				editar_cliente();
			}
		});

		getActionMap().put("recarregar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_recarregar) {
				recarregarTabela();
			}
		});
	}
}
