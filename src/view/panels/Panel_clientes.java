package view.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import entities.Cliente;
import icons.Icones;
import tables.tableModels.ModeloTabelaClientes;
import tables.tableRenders.Render_tabela_clientes;

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
	private JComboBox cbxTipoPesquisa;
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

	/**
	 * Create the panel.
	 */
	public Panel_clientes() {
	
		setBorder(null);
		setLayout(null);

		MaskFormatter mascara_pesquisa = null;

		btnEditar = new JButton("Editar");
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEditar) {
				if (btnEditar.isEnabled()) {
					ativar_campos();
					btnEditar.setVisible(false);
					btnEditar.setEnabled(false);
					btnNovo.setVisible(false);
					btnExcluir.setVisible(false);
					btnSalvar.setVisible(true);
					btnCancelar.setVisible(true);
				}

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

					boolean flag;

					ClienteDAO cliente_dao = new ClienteDAO();
					Integer codigo = (Integer) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 0);

					int opcao = JOptionPane.showConfirmDialog(null,
							"Deseja excluir o cliente abaixo?\n" + "Código: " + codigo + "\n" + "Nome: "
									+ tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 2),
							"Exclusão de Cliente", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

					flag = opcao == JOptionPane.YES_OPTION;

					if (flag) {
						if (cliente_dao.excluirCliente(codigo)) {
							JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso.",
									"Exclusão de clientes.", JOptionPane.NO_OPTION);
							recarregarTabela();
						}
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

		try {
			mascara_pesquisa = new MaskFormatter("******************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtPesquisa = new JFormattedTextField(mascara_pesquisa);
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
		txtCodigo.setEnabled(false);
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
					btnLimpaDocumento.setVisible(false);
					limpar_campos();
				}
				btnLimpaCep.setVisible(false);

				if (!btnNovo.isVisible()) {
					fTxtDocumento.requestFocus();
				}
			}
		});
		checkBoxJuridica.setEnabled(false);
		add(checkBoxJuridica);

		lblDocumento = new JLabel("Documento");
		lblDocumento.setBounds(297, 155, 78, 17);
		lblDocumento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblDocumento);

		try {
			mascara_cpf = new MaskFormatter("###.###.###-##");
			mascara_cnpj = new MaskFormatter("##.###.###/####-##");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtDocumento = new JFormattedTextField(mascara_cpf);
		fTxtDocumento.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent perdaFocoDocumento) {

				if (fTxtDocumento.isEnabled() && !fTxtDocumento.getText().trim().isEmpty()) {
					String documento = fTxtDocumento.getText().trim();
					String codigo = txtCodigo.getText().trim();
					ClienteDAO cliente_dao = new ClienteDAO();
					String nome_cliente = null;
					nome_cliente = cliente_dao.validarDocumento(documento, codigo);
					if (nome_cliente != null) {
						JOptionPane.showMessageDialog(null,
								"Documento ja informado para o cliente abaixo:" + "\n" + nome_cliente,
								"Documento ja utilizado.", JOptionPane.WARNING_MESSAGE);
						fTxtDocumento.requestFocus();
					}
				}

			}
		});
		fTxtDocumento.setBounds(376, 152, 125, 20);
		fTxtDocumento.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtDocumento.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtDocumento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterDocumento) {
				String documento = fTxtDocumento.getText().trim();
				Cliente cliente = new Cliente();
				ClienteDAO cliente_dao = new ClienteDAO();

				if (enterDocumento.getKeyCode() == enterDocumento.VK_ENTER) {

					if (cliente_dao.validarDocumento(documento, txtCodigo.getText().trim()) == null) {
						if (documento.length() > 14) {
							if (checkBoxJuridica.isSelected()) {
								Busca_cnpj api_cnpj = new Busca_cnpj();
								documento = fTxtDocumento.getText().trim().replaceAll("[./-]", "");
								cliente = api_cnpj.buscar_cnpj(documento);
								
								if(cliente != null) {
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

					if (fTxtIe.isVisible()) {
						fTxtIe.requestFocus();
					} else {
						fTxtNomeCliente.requestFocus();
					}

				}
			}
		});
		fTxtDocumento.setEnabled(false);
		add(fTxtDocumento);

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
		fTxtIe.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterIe) {
				if (enterIe.getKeyCode() == enterIe.VK_ENTER) {
					fTxtNomeCliente.requestFocus();
				}
			}
		});

		fTxtIe = new JFormattedTextField(mascara_ie);
		fTxtIe.setBounds(580, 152, 129, 20);
		fTxtIe.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtIe.setFocusLostBehavior(JFormattedTextField.PERSIST);

		fTxtIe.setEnabled(false);
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

		MaskFormatter mascara_nome = null;
		try {
			mascara_nome = new MaskFormatter("*********************************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fTxtNomeCliente = new JFormattedTextField(mascara_nome);
		fTxtNomeCliente.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtNomeCliente.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeCliente.setBounds(57, 186, 317, 20);
		fTxtNomeCliente.setEnabled(false);
		add(fTxtNomeCliente);

		MaskFormatter mascara_apelido = null;
		try {
			mascara_apelido = new MaskFormatter("********************");
		} catch (ParseException e2) {
			e2.printStackTrace();
		}

		lblApelido = new JLabel("Apelido");
		lblApelido.setBounds(398, 189, 46, 20);
		lblApelido.setToolTipText("");
		lblApelido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblApelido);

		fTxtApelido = new JFormattedTextField(mascara_apelido);
		fTxtApelido.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtApelido.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtApelido.setBounds(444, 186, 265, 20);
		fTxtApelido.setEnabled(false);
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
		fTxtCep.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtCep.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (fTxtCep.getText().trim().length() == 9) {
					buscaCep();
					btnLimpaCep.setVisible(true);
				} else {
					btnLimpaCep.setVisible(false);
				}
				if (e.getKeyCode() == e.VK_ENTER) {
					fTxtCidade.requestFocus();
				}
			}
		});
		fTxtCep.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCep.setBounds(46, 257, 78, 20);
		fTxtCep.setEnabled(false);
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

		MaskFormatter mascara_cidade = null;
		try {
			mascara_cidade = new MaskFormatter("******************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtCidade = new JFormattedTextField(mascara_cidade);
		fTxtCidade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtCidade.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCidade.setBounds(262, 257, 239, 20);
		fTxtCidade.setEnabled(false);
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

		MaskFormatter mascara_endereco = null;

		try {
			mascara_endereco = new MaskFormatter("*************************************************");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		fTxtEndereco = new JFormattedTextField(mascara_endereco);
		fTxtEndereco.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtEndereco.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtEndereco.setBounds(79, 293, 348, 20);
		fTxtEndereco.setEnabled(false);
		add(fTxtEndereco);

		lblNumero = new JLabel("N\u00B0");
		lblNumero.setBounds(471, 293, 16, 20);
		lblNumero.setToolTipText("");
		lblNumero.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblNumero);

		MaskFormatter mascara_numero = null;
		try {
			mascara_numero = new MaskFormatter("********");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtNumero = new JFormattedTextField(mascara_numero);
		fTxtNumero.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtNumero.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNumero.setBounds(489, 292, 70, 20);
		fTxtNumero.setEnabled(false);
		add(fTxtNumero);

		lblReferencia = new JLabel("Referencia");
		lblReferencia.setBounds(16, 328, 65, 20);
		lblReferencia.setToolTipText("");
		lblReferencia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblReferencia);

		MaskFormatter mascara_referencia = null;

		try {
			mascara_referencia = new MaskFormatter("*************************************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtReferencia = new JFormattedTextField(mascara_referencia);
		fTxtReferencia.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtReferencia.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtReferencia.setBounds(86, 326, 342, 20);
		fTxtReferencia.setEnabled(false);
		add(fTxtReferencia);

		lblBairro = new JLabel("Bairro");
		lblBairro.setBounds(444, 328, 40, 20);
		lblBairro.setToolTipText("");
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblBairro);

		MaskFormatter mascara_bairro = null;
		try {
			mascara_bairro = new MaskFormatter("******************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtBairro = new JFormattedTextField(mascara_bairro);
		fTxtBairro.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtBairro.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtBairro.setBounds(489, 324, 220, 20);
		fTxtBairro.setEnabled(false);
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
		fTxtCelular.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtCelular.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCelular.setBounds(62, 361, 104, 20);
		fTxtCelular.setEnabled(false);
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
		fTxtTelFixo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtTelFixo.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtTelFixo.setBounds(251, 361, 104, 20);
		fTxtTelFixo.setEnabled(false);
		add(fTxtTelFixo);

		lblEmai = new JLabel("Email");
		lblEmai.setBounds(372, 363, 40, 20);
		lblEmai.setToolTipText("");
		lblEmai.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblEmai);

		MaskFormatter mascara_email = null;
		try {
			mascara_email = new MaskFormatter("********************************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtEmail = new JFormattedTextField(mascara_email);
		fTxtEmail.setFont(new Font("Tahoma", Font.PLAIN, 12));
		fTxtEmail.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtEmail.setBounds(412, 362, 297, 20);
		fTxtEmail.setEnabled(false);
		add(fTxtEmail);

		// alimentando lista de clientes.
		lista_clientes = alimentarListaClientes(lista_clientes);

		modelo = new ModeloTabelaClientes(lista_clientes);
		tabelaClientes = new JTable(modelo);
		tabelaClientes.setSurrendersFocusOnKeystroke(true);
		tabelaClientes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tabelaClientes.getTableHeader().setReorderingAllowed(false);
		tabelaClientes.setAutoResizeMode(tabelaClientes.AUTO_RESIZE_OFF);
		tabelaClientes.setBounds(14, 325, 694, 216);
		Render_tabela_clientes render = new Render_tabela_clientes();
		tabelaClientes.setDefaultRenderer(Object.class, render);

		ConfiguralarguracolunaTabela(tabelaClientes);

		scrollPane = new JScrollPane(tabelaClientes);
		scrollPane.setBounds(16, 495, 693, 153);
		add(scrollPane);

		tabelaClientes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent selecaoLinhaTabela) {

				ListSelectionModel lsm = (ListSelectionModel) selecaoLinhaTabela.getSource();

				if (!lsm.isSelectionEmpty() && btnNovo.isVisible()) {

					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					
					// Verificando se o documento do cliente é um CPF ou CNPJ
					if (tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 11).toString().length() > 14) {
						checkBoxJuridica.setSelected(true);
						fTxtIe.setVisible(true);
						lblIe.setVisible(true);
					} else {
						checkBoxJuridica.setSelected(false);
						fTxtIe.setVisible(false);
						lblIe.setVisible(false);
					}
					checkBoxBloqueado
							.setSelected((Boolean) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 1));
					fTxtDocumento.setText((String) tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 11));
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

					Integer codigo = Integer.parseInt(tabelaClientes.getValueAt(tabelaClientes.getSelectedRow(), 0).toString());

					txtCodigo.setText(codigo.toString());

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
				Cliente cliente = novo_cliente();

				if (fTxtNomeCliente.getText().trim().isEmpty() || fTxtCelular.getText().equals("(  )     -    ")) {
					if (fTxtNomeCliente.getText().trim().isEmpty()) {
						fTxtNomeCliente.setBorder(new LineBorder(Color.RED));
						JOptionPane.showMessageDialog(null, "Necessário informar o nome do cliente.",
								"Cliente sem nome.", JOptionPane.WARNING_MESSAGE);
					}

					if (fTxtCelular.getText().equals("(  )     -    ")) {
						fTxtCelular.setBorder(new LineBorder(Color.RED));
						JOptionPane.showMessageDialog(null, "Necessário informar o celular do cliente.",
								"Cliente sem celular.", JOptionPane.WARNING_MESSAGE);
					}
				} else {

					if (txtCodigo.getText().trim().isEmpty()) {
						if (salvar_cliente(cliente)) {
							fTxtNomeCliente.setBorder(new LineBorder(Color.lightGray));
							fTxtCelular.setBorder(new LineBorder(Color.lightGray));
							JOptionPane
									.showMessageDialog(null,
											"Cliente cadastrado com sucesso." + "\nCódigo: " + cliente.getIdCliente()
													+ "\nNome: " + cliente.getNome(),
											"Novo cliente", JOptionPane.NO_OPTION);
							limpar_campos();
							desativar_campos();
							recarregarTabela();
							btnNovo.setVisible(true);
						}
					} else {

						ClienteDAO cliente_dao = new ClienteDAO();

						cliente.setIdCliente(Integer.parseInt(txtCodigo.getText().trim()));

						if (cliente_dao.alterar_cliente(cliente)) {
							fTxtNomeCliente.setBorder(new LineBorder(Color.lightGray));
							fTxtCelular.setBorder(new LineBorder(Color.lightGray));
							JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso.", "Alteração de cliente",
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
				tabelaClientes.clearSelection();
				desativar_campos();
				limpar_campos();
				btnNovo.setVisible(true);
				btnEditar.setVisible(true);
				btnExcluir.setVisible(true);
				checkBoxJuridica.setSelected(false);
				fTxtNomeCliente.setBorder(new LineBorder(Color.lightGray));
				fTxtCelular.setBorder(new LineBorder(Color.lightGray));
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
		cbxTipoPesquisa.setModel(new DefaultComboBoxModel(new String[] { "Nome", "Apelido", "C\u00F3digo" }));
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

	}

	// Configurando largura das colunas da tabela
	public void ConfiguralarguracolunaTabela(JTable tabelaProdutos) {
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
		RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(modelo);
		tabelaClientes.setRowSorter(sorter);

	}

	public void ativar_campos() {
		checkBoxJuridica.setEnabled(true);
		fTxtDocumento.setEnabled(true);
		fTxtIe.setEnabled(true);
		fTxtNomeCliente.setEnabled(true);
		fTxtApelido.setEnabled(true);
		fTxtCep.setEnabled(true);
		fTxtCidade.setEnabled(true);
		fTxtEndereco.setEnabled(true);
		fTxtNumero.setEnabled(true);
		fTxtReferencia.setEnabled(true);
		fTxtBairro.setEnabled(true);
		fTxtCelular.setEnabled(true);
		fTxtTelFixo.setEnabled(true);
		fTxtEmail.setEnabled(true);
		btnCancelar.setVisible(true);
		btnSalvar.setVisible(true);
		checkBoxBloqueado.setEnabled(true);
	}

	public void desativar_campos() {
		checkBoxJuridica.setEnabled(false);
		fTxtDocumento.setEnabled(false);
		fTxtIe.setEnabled(false);
		fTxtNomeCliente.setEnabled(false);
		fTxtApelido.setEnabled(false);
		fTxtCep.setEnabled(false);
		fTxtCidade.setEnabled(false);
		fTxtEndereco.setEnabled(false);
		fTxtNumero.setEnabled(false);
		fTxtReferencia.setEnabled(false);
		fTxtBairro.setEnabled(false);
		fTxtCelular.setEnabled(false);
		fTxtTelFixo.setEnabled(false);
		fTxtEmail.setEnabled(false);
		btnCancelar.setVisible(false);
		btnSalvar.setVisible(false);
		checkBoxBloqueado.setEnabled(false);
	}

	public void limpar_campos() {
		checkBoxBloqueado.setSelected(false);
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

	public Cliente novo_cliente() {
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

		if (!fTxtDocumento.getText().equals("   .   .   -  ")
				|| !fTxtDocumento.getText().equals("  .   .   /    -  ")) {
			documento = fTxtDocumento.getText().trim();
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
				bloqueado, data_cadastro, null);

		return novo_cliente;
	}

	public boolean salvar_cliente(Cliente cliente) {

		ClienteDAO cliente_dao = new ClienteDAO();
		cliente = cliente_dao.inserirCliente(cliente);

		if (cliente.getIdCliente() != null) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<Cliente> alimentarListaClientes(ArrayList<Cliente> clientes) {
		ClienteDAO cliente_dao = new ClienteDAO();

		String pesquisado = null;
		
		if (fTxtPesquisa.getText().trim().isEmpty()) {
			clientes = cliente_dao.listarClientes(clientes);
			return clientes;
		} else {

			pesquisado = fTxtPesquisa.getText().trim() + "%";

			switch (cbxTipoPesquisa.getSelectedItem().toString()) {

			case "Código":
				clientes = cliente_dao.listarClientes_codigo(clientes, pesquisado);
				break;
			case "Nome":
				clientes = cliente_dao.listarClientes_nome(clientes, pesquisado);
				break;
			case "Apelido":
				clientes = cliente_dao.listarClientes_apelido(clientes, pesquisado);
				break;
			}
			
		}

		return clientes;

	}

	public void recarregarTabela() {
		lista_clientes.clear();
		lista_clientes = alimentarListaClientes(lista_clientes);
		modelo = new ModeloTabelaClientes(lista_clientes);
		tabelaClientes.setModel(modelo);
		ConfiguralarguracolunaTabela(tabelaClientes);
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
}
