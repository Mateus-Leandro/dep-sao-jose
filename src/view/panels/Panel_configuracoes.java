package view.panels;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import dao.ClienteDAO;
import dao.ConfiguracaoDAO;
import entities.cliente.Cliente;
import entities.configuracoes.Configuracoes;
import icons.Icones;
import view.frames.TelaPrincipal;
import view.tools.Jtext_tools;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Panel_configuracoes extends JPanel {
	private JLabel lblconfiguracoes;
	private JSeparator separador_Configuracoes;
	private JLabel lblNomeEmpresa;
	private JFormattedTextField fTxtNomeEmpresa;
	private JButton btnConfigurar;
	private JButton btnCancelar;
	private JButton btnSalvar;
	private Icones icones = new Icones();
	private JLabel lblTelefone;
	private JFormattedTextField fTxtTelFixo;
	private JLabel lblCelular;
	private JFormattedTextField fTxtCelular;
	private JLabel lblInformacoesDaEmpresa;
	private JSeparator separado_informacoes_empresa;
	private JLabel lblEndereco;
	private JFormattedTextField fTxtEndereco;
	private JLabel lblResponsavel;
	private JFormattedTextField fTxtNomeResponsavel;
	private JLabel lblCnpj;
	private JFormattedTextField fTxtCnpj;
	private JLabel lblInscricaoEstadual;
	private JFormattedTextField fTxtInscricao;
	private JLabel lblEmail;
	private JFormattedTextField fTxtEmail;
	private JLabel lblConfiguracoes_faturamento;
	private JSeparator separado_informacoes_empresa_1;
	private JLabel lblSalvarParcelasDivergentes;
	private JComboBox<String> cbxParcelasDiferentes;
	private JComboBox<String> cbxGeraPdf;
	private JLabel lblAlterarOramentoJ;
	private JComboBox<String> cbxAltOrc;
	private Configuracoes configuracoes_do_sistema = new ConfiguracaoDAO().busca_configuracoes();
	private JLabel lblConsumidorFinal;
	private JFormattedTextField fTxtConsumidorFinal;
	private JScrollPane scrollPaneConsumidorFinal;
	private JList<Cliente> ltConsumidorFinal;
	private DefaultListModel<Cliente> list_model = new DefaultListModel<Cliente>();
	private ArrayList<Cliente> lista_consumidor_final = new ArrayList<Cliente>();
	private Cliente consumidor_final = new Cliente();
	private JLabel lblCodConsumidorFinal;
	private JFormattedTextField fTxtCodigoConsumidor;
	private JLabel lblNomeConsumidorFinal;
	private JLabel lblObg_celular;
	private JLabel lblObg_consumidor;
	private JLabel lblObg_nomeEmpresa;
	private Jtext_tools text_tools = new Jtext_tools();
	private JLabel lblObsConsumidor;

	/**
	 * Create the panel.
	 */
	public Panel_configuracoes(TelaPrincipal tela_principal) {

		ltConsumidorFinal = new JList<Cliente>();
		ltConsumidorFinal.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickConsumidorFinal) {
				scrollPaneConsumidorFinal.setVisible(false);
				consumidor_final = ltConsumidorFinal.getSelectedValue();
				fTxtConsumidorFinal.setText(consumidor_final.getNome());
				fTxtCodigoConsumidor.setText(consumidor_final.getIdCliente().toString());
				valida_campos();
			}
		});
		ltConsumidorFinal.setVisibleRowCount(10);
		ltConsumidorFinal.setBounds(122, 451, 276, 70);

		scrollPaneConsumidorFinal = new JScrollPane(ltConsumidorFinal);
		scrollPaneConsumidorFinal.setBounds(172, 508, 276, 70);
		scrollPaneConsumidorFinal.setVisible(false);
		setLayout(null);
		add(scrollPaneConsumidorFinal);

		lblconfiguracoes = new JLabel("Configura\u00E7\u00F5es do Sistema");
		lblconfiguracoes.setBounds(198, 11, 335, 29);
		lblconfiguracoes.setHorizontalAlignment(SwingConstants.CENTER);
		lblconfiguracoes.setFont(new Font("Tahoma", Font.BOLD, 24));
		add(lblconfiguracoes);

		separador_Configuracoes = new JSeparator();
		separador_Configuracoes.setBounds(10, 51, 709, 9);
		add(separador_Configuracoes);

		lblNomeEmpresa = new JLabel("Nome da empresa");
		lblNomeEmpresa.setBounds(10, 156, 117, 19);
		lblNomeEmpresa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblNomeEmpresa);

		MaskFormatter mascara_nome_empresa = null;
		try {
			mascara_nome_empresa = new MaskFormatter("****************************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtNomeEmpresa = new JFormattedTextField(mascara_nome_empresa);
		fTxtNomeEmpresa.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNomeEmpresa) {
				text_tools.move_cursor_inicio(fTxtNomeEmpresa);
			}
		});
		fTxtNomeEmpresa.setToolTipText("Nome mostrado na impress\u00E3o do or\u00E7amento");
		fTxtNomeEmpresa.setBounds(125, 155, 285, 20);
		fTxtNomeEmpresa.setEditable(false);
		fTxtNomeEmpresa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaNomeEmpresa) {
				valida_campos();

				if (digitaNomeEmpresa.getKeyCode() == digitaNomeEmpresa.VK_ENTER) {
					fTxtNomeResponsavel.requestFocus();
				}
			}
		});
		fTxtNomeEmpresa.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtNomeEmpresa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtNomeEmpresa.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeEmpresa.setColumns(10);
		add(fTxtNomeEmpresa);

		btnConfigurar = new JButton("Configurar");
		btnConfigurar.setBounds(10, 70, 124, 29);
		btnConfigurar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickConfigurar) {
				if (btnConfigurar.isEnabled()) {
					valida_campos();
					ativar_campos();
					btnConfigurar.setEnabled(false);
					btnSalvar.setVisible(true);
					btnCancelar.setVisible(true);
					fTxtNomeEmpresa.requestFocus();
				}

			}
		});
		btnConfigurar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnConfigurar.setIcon(icones.getIcone_engrenagem());
		add(btnConfigurar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(606, 622, 113, 29);
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancelar) {
				desativar_campos();
				exibe_configuracoes(configuracoes_do_sistema);
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.setIcon(icones.getIcone_cancelar());
		btnCancelar.setVisible(false);
		add(btnCancelar);

		btnSalvar = new JButton("Salvar");
		btnSalvar.setBounds(479, 622, 117, 29);
		btnSalvar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickSalvarConfiguracao) {
				if (btnSalvar.isEnabled()) {

					configuracoes_do_sistema = monta_configuracao();
					ConfiguracaoDAO conf_dao = new ConfiguracaoDAO();
					if (conf_dao.salva_configuracao(configuracoes_do_sistema)) {
						if (consumidor_final != null) {
							fTxtConsumidorFinal.setText(consumidor_final.getNome());
						}
						JOptionPane.showMessageDialog(fTxtInscricao, "Nova configuração salva.", "Configurações",
								JOptionPane.NO_OPTION);
						btnSalvar.setVisible(false);
						btnCancelar.setVisible(false);
						btnConfigurar.setEnabled(true);
						desativar_campos();
						tela_principal.ativa_abas_configuracao_inicial();
					}
				}
			}
		});
		btnSalvar.setEnabled(false);
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSalvar.setIcon(icones.getIcone_salvar());
		btnSalvar.setVisible(false);
		add(btnSalvar);

		lblTelefone = new JLabel("Tel. Fixo");
		lblTelefone.setBounds(10, 224, 50, 19);
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblTelefone);

		MaskFormatter mascara_tel_fixo = null;
		try {
			mascara_tel_fixo = new MaskFormatter("(##)####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtTelFixo = new JFormattedTextField(mascara_tel_fixo);
		fTxtTelFixo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterTelFixo) {
				if (enterTelFixo.getKeyChar() == enterTelFixo.VK_ENTER) {
					fTxtCelular.requestFocus();
				}
			}
		});
		fTxtTelFixo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickTelFixo) {
				fTxtTelFixo.setCaretPosition(0);
			}
		});
		fTxtTelFixo.setBounds(64, 223, 101, 20);
		fTxtTelFixo.setEditable(false);
		fTxtTelFixo.setToolTipText("");
		fTxtTelFixo.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtTelFixo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtTelFixo.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtTelFixo.setColumns(10);
		add(fTxtTelFixo);

		lblCelular = new JLabel("Celular");
		lblCelular.setBounds(185, 224, 44, 19);
		lblCelular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblCelular);

		MaskFormatter mascara_celular = null;

		try {
			mascara_celular = new MaskFormatter("(##)#####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtCelular = new JFormattedTextField(mascara_celular);
		fTxtCelular.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCelular) {
				fTxtCelular.setCaretPosition(0);
			}
		});
		fTxtCelular.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaCelular) {
				valida_campos();

				if (digitaCelular.getKeyCode() == digitaCelular.VK_ENTER) {
					fTxtEmail.requestFocus();
				}
			}
		});
		fTxtCelular.setBounds(229, 223, 107, 20);
		fTxtCelular.setEditable(false);
		fTxtCelular.setToolTipText("Telefone Mostrado na impress\u00E3o do or\u00E7amento");
		fTxtCelular.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtCelular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCelular.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCelular.setColumns(10);
		add(fTxtCelular);

		lblInformacoesDaEmpresa = new JLabel("Informa\u00E7\u00F5es da Empresa");
		lblInformacoesDaEmpresa.setBounds(10, 122, 233, 29);
		lblInformacoesDaEmpresa.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformacoesDaEmpresa.setFont(new Font("Tahoma", Font.BOLD, 18));
		add(lblInformacoesDaEmpresa);

		separado_informacoes_empresa = new JSeparator();
		separado_informacoes_empresa.setBounds(242, 139, 477, 9);
		add(separado_informacoes_empresa);

		lblEndereco = new JLabel("Endereco");
		lblEndereco.setBounds(10, 259, 64, 19);
		lblEndereco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblEndereco);

		MaskFormatter mascara_endereco = null;

		try {
			mascara_endereco = new MaskFormatter("**************************************************************");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		fTxtEndereco = new JFormattedTextField(mascara_endereco);
		fTxtEndereco.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterEndereco) {
				if (enterEndereco.getKeyCode() == enterEndereco.VK_ENTER) {
					cbxParcelasDiferentes.requestFocus();
				}
			}
		});
		fTxtEndereco.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEndereco) {
				text_tools.move_cursor_inicio(fTxtEndereco);
			}
		});
		fTxtEndereco.setBounds(74, 258, 364, 20);
		fTxtEndereco.setEditable(false);
		fTxtEndereco.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtEndereco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtEndereco.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtEndereco.setColumns(10);
		add(fTxtEndereco);

		lblResponsavel = new JLabel("Respons\u00E1vel");
		lblResponsavel.setBounds(428, 156, 82, 19);
		lblResponsavel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblResponsavel);

		fTxtNomeResponsavel = new JFormattedTextField();
		fTxtNomeResponsavel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterNomeResponsavel) {
				if (enterNomeResponsavel.getKeyCode() == enterNomeResponsavel.VK_ENTER) {
					fTxtCnpj.requestFocus();
				}
			}
		});
		fTxtNomeResponsavel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNomeResponsavel) {
				text_tools.move_cursor_inicio(fTxtNomeEmpresa);
			}
		});
		fTxtNomeResponsavel.setBounds(508, 155, 211, 20);
		fTxtNomeResponsavel.setEditable(false);
		fTxtNomeResponsavel.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtNomeResponsavel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtNomeResponsavel.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeResponsavel.setColumns(10);
		add(fTxtNomeResponsavel);

		lblCnpj = new JLabel("CNPJ");
		lblCnpj.setBounds(10, 191, 38, 19);
		lblCnpj.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblCnpj);

		MaskFormatter mascara_cnpj = null;
		try {
			mascara_cnpj = new MaskFormatter("##.###.###/####-##");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtCnpj = new JFormattedTextField(mascara_cnpj);
		fTxtCnpj.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaCnpj) {
				if (digitaCnpj.getKeyCode() == digitaCnpj.VK_ENTER) {
					fTxtInscricao.requestFocus();
				}
			}
		});
		fTxtCnpj.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCnpj) {
				fTxtCnpj.setCaretPosition(0);
			}
		});
		fTxtCnpj.setBounds(46, 190, 134, 20);
		fTxtCnpj.setEditable(false);
		fTxtCnpj.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtCnpj.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCnpj.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCnpj.setColumns(10);
		add(fTxtCnpj);

		lblInscricaoEstadual = new JLabel("Inscri\u00E7\u00E3o Estadual");
		lblInscricaoEstadual.setBounds(212, 190, 117, 19);
		lblInscricaoEstadual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblInscricaoEstadual);

		MaskFormatter mascara_ie = null;

		try {
			mascara_ie = new MaskFormatter("#########.##-##");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtInscricao = new JFormattedTextField(mascara_ie);
		fTxtInscricao.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterInscricao) {
				if (enterInscricao.getKeyCode() == enterInscricao.VK_ENTER) {
					fTxtTelFixo.requestFocus();
				}
			}
		});
		fTxtInscricao.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickInscricao) {
				fTxtInscricao.setCaretPosition(0);
			}
		});
		fTxtInscricao.setBounds(326, 190, 134, 20);
		fTxtInscricao.setEditable(false);
		fTxtInscricao.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtInscricao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtInscricao.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtInscricao.setColumns(10);
		add(fTxtInscricao);

		lblEmail = new JLabel("Email");
		lblEmail.setBounds(400, 224, 38, 19);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblEmail);

		fTxtEmail = new JFormattedTextField();
		fTxtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterEmail) {
				if (enterEmail.getKeyCode() == enterEmail.VK_ENTER) {
					fTxtEndereco.requestFocus();
				}
			}
		});
		fTxtEmail.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEmail) {
				text_tools.move_cursor_inicio(fTxtEmail);
			}
		});
		fTxtEmail.setBounds(434, 224, 285, 20);
		fTxtEmail.setEditable(false);
		fTxtEmail.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtEmail.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtEmail.setColumns(10);
		add(fTxtEmail);

		lblConfiguracoes_faturamento = new JLabel("Configura\u00E7\u00F5es do Faturamento");
		lblConfiguracoes_faturamento.setBounds(10, 329, 279, 29);
		lblConfiguracoes_faturamento.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfiguracoes_faturamento.setFont(new Font("Tahoma", Font.BOLD, 18));
		add(lblConfiguracoes_faturamento);

		separado_informacoes_empresa_1 = new JSeparator();
		separado_informacoes_empresa_1.setBounds(293, 346, 426, 9);
		add(separado_informacoes_empresa_1);

		lblSalvarParcelasDivergentes = new JLabel("Salvar parcelas \u2260 do total do or\u00E7amento");
		lblSalvarParcelasDivergentes.setBounds(11, 366, 246, 19);
		lblSalvarParcelasDivergentes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblSalvarParcelasDivergentes);

		cbxParcelasDiferentes = new JComboBox<String>();
		cbxParcelasDiferentes.setToolTipText(
				"Permitir gravar parcelas quando o total delas for diferente do total do or\u00E7amento.");
		cbxParcelasDiferentes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterParcelasDif) {
				if (enterParcelasDif.getKeyCode() == enterParcelasDif.VK_ENTER) {
					cbxGeraPdf.requestFocus();
				}
			}
		});
		cbxParcelasDiferentes.setBounds(259, 361, 114, 22);
		cbxParcelasDiferentes.setEnabled(false);
		cbxParcelasDiferentes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxParcelasDiferentes.setModel(new DefaultComboBoxModel(new String[] { "SIM", "N\u00C3O", "PERGUNTAR" }));
		add(cbxParcelasDiferentes);

		JLabel lblGeraPdf = new JLabel("PDF ap\u00F3s confirmar or\u00E7amento");
		lblGeraPdf.setBounds(400, 366, 191, 19);
		lblGeraPdf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblGeraPdf);

		cbxGeraPdf = new JComboBox<String>();
		cbxGeraPdf.setToolTipText("Gerar PDF ap\u00F3s confirmar o or\u00E7amento.");
		cbxGeraPdf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterGeraPdf) {
				if (enterGeraPdf.getKeyCode() == enterGeraPdf.VK_ENTER) {
					cbxAltOrc.requestFocus();
				}
			}
		});
		cbxGeraPdf.setBounds(593, 361, 117, 22);
		cbxGeraPdf.setEnabled(false);
		cbxGeraPdf.setModel(new DefaultComboBoxModel(new String[] { "SIM", "N\u00C3O", "PERGUNTAR" }));
		cbxGeraPdf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(cbxGeraPdf);

		lblAlterarOramentoJ = new JLabel("Alterar or\u00E7amentos que possuem parcelas");
		lblAlterarOramentoJ.setBounds(10, 400, 260, 19);
		lblAlterarOramentoJ.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblAlterarOramentoJ);

		cbxAltOrc = new JComboBox<String>();
		cbxAltOrc.setToolTipText(
				"Permitir editar os itens e valores do or\u00E7amento quando ele possuir parcelas lan\u00E7adas.");
		cbxAltOrc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterAltOrc) {
				if (enterAltOrc.getKeyCode() == enterAltOrc.VK_ENTER) {
					fTxtConsumidorFinal.requestFocus();
				}
			}
		});
		cbxAltOrc.setBounds(268, 395, 117, 22);
		cbxAltOrc.setEnabled(false);
		cbxAltOrc.setModel(new DefaultComboBoxModel(new String[] { "SIM", "N\u00C3O", "PERGUNTAR" }));
		cbxAltOrc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(cbxAltOrc);

		lblConsumidorFinal = new JLabel("Consumidor Final");
		lblConsumidorFinal.setBounds(10, 461, 163, 19);
		lblConsumidorFinal.setFont(new Font("Tahoma", Font.BOLD, 18));
		add(lblConsumidorFinal);

		MaskFormatter mascara_consumidor = null;

		try {
			mascara_consumidor = new MaskFormatter("****************************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtConsumidorFinal = new JFormattedTextField(mascara_consumidor);
		fTxtConsumidorFinal.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickConsumidorFinal) {
				if(!btnConfigurar.isEnabled()) {
					text_tools.move_cursor_inicio(fTxtConsumidorFinal);
					alimentar_lista_consumidor_final("NOME", "%");
				}
			}
		});
		fTxtConsumidorFinal.setBounds(172, 489, 276, 20);
		fTxtConsumidorFinal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaConsumidorFinal) {
				if (fTxtConsumidorFinal.getText().trim().isEmpty()) {
					alimentar_lista_consumidor_final("NOME", null);
				} else {
					alimentar_lista_consumidor_final("NOME", fTxtConsumidorFinal.getText().trim());
				}
			}
		});
		fTxtConsumidorFinal.setEditable(false);
		fTxtConsumidorFinal.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtConsumidorFinal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtConsumidorFinal.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtConsumidorFinal.setColumns(10);
		add(fTxtConsumidorFinal);

		lblCodConsumidorFinal = new JLabel("Cod.");
		lblCodConsumidorFinal.setBounds(10, 491, 33, 19);
		lblCodConsumidorFinal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblCodConsumidorFinal);

		fTxtCodigoConsumidor = new JFormattedTextField();
		fTxtCodigoConsumidor.setBounds(43, 490, 61, 20);
		fTxtCodigoConsumidor.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtCodigoConsumidor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCodigoConsumidor.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCodigoConsumidor.setEditable(false);
		fTxtCodigoConsumidor.setColumns(10);
		add(fTxtCodigoConsumidor);

		lblNomeConsumidorFinal = new JLabel("Nome");
		lblNomeConsumidorFinal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeConsumidorFinal.setBounds(127, 490, 41, 19);
		add(lblNomeConsumidorFinal);

		lblObg_celular = new JLabel("*");
		lblObg_celular.setForeground(Color.RED);
		lblObg_celular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObg_celular.setBounds(337, 235, 20, 15);
		add(lblObg_celular);

		lblObg_consumidor = new JLabel("*");
		lblObg_consumidor.setForeground(Color.RED);
		lblObg_consumidor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObg_consumidor.setBounds(450, 502, 20, 15);
		add(lblObg_consumidor);

		lblObg_nomeEmpresa = new JLabel("*");
		lblObg_nomeEmpresa.setForeground(Color.RED);
		lblObg_nomeEmpresa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObg_nomeEmpresa.setBounds(411, 166, 20, 15);
		add(lblObg_nomeEmpresa);

		lblObsConsumidor = new JLabel(
				"* O sistema utilizar\u00E1 o consumidor final sempre que n\u00E3o for informado algum cliente no or\u00E7amento.");
		lblObsConsumidor.setForeground(Color.BLUE);
		lblObsConsumidor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblObsConsumidor.setBounds(10, 519, 540, 19);
		add(lblObsConsumidor);

		exibe_configuracoes(configuracoes_do_sistema);

	}

	public Configuracoes monta_configuracao() {

		String nome_empresa = null;
		String responsavel = null;
		String CNPJ = null;
		String inscricao_estadual = null;
		String tel_fixo = null;
		String celular = null;
		String email = null;
		String endereco = null;
		String salva_parc_dif = null;
		String altera_orc = null;
		String gera_PDF = null;

		nome_empresa = fTxtNomeEmpresa.getText().trim();
		if (!fTxtNomeResponsavel.getText().trim().isEmpty()) {
			responsavel = fTxtNomeResponsavel.getText().trim();
		}

		if (!fTxtCnpj.getText().equals("  .   .   /    -  ")) {
			CNPJ = fTxtCnpj.getText().trim();
		}
		if (!fTxtInscricao.getText().equals("         .  -  ")) {
			inscricao_estadual = fTxtInscricao.getText().trim();
		}
		if (!fTxtTelFixo.getText().equals("(  )    -    ")) {
			tel_fixo = fTxtTelFixo.getText().trim();
		}
		if (!fTxtCelular.getText().equals("(  )     -    ")) {
			celular = fTxtCelular.getText().trim();
		}
		if (!fTxtEmail.getText().trim().isEmpty()) {
			email = fTxtEmail.getText().trim();
		}

		endereco = fTxtEndereco.getText().trim();

		salva_parc_dif = cbxParcelasDiferentes.getSelectedItem().toString();
		altera_orc = cbxAltOrc.getSelectedItem().toString();
		gera_PDF = cbxGeraPdf.getSelectedItem().toString();

		Configuracoes conf = new Configuracoes(nome_empresa, responsavel, CNPJ, inscricao_estadual, tel_fixo, celular,
				email, endereco, salva_parc_dif, altera_orc, gera_PDF, consumidor_final);

		return conf;
	}

	public void valida_campos() {
		if (!fTxtNomeEmpresa.getText().trim().isEmpty() && !fTxtCelular.getText().equals("(  )     -    ")
				&& consumidor_final != null) {
			btnSalvar.setEnabled(true);
		} else {
			btnSalvar.setEnabled(false);
		}
	}

	public void alimentar_lista_consumidor_final(String forma_pesquisa, String texto) {

		String tipo_busca = forma_pesquisa;
		String texto_buscado = texto;

		ClienteDAO cliente_dao = new ClienteDAO();
		cliente_dao.alimenta_lt_clientes(tipo_busca, texto_buscado, list_model, lista_consumidor_final);

		if (!list_model.isEmpty()) {
			scrollPaneConsumidorFinal.setVisible(true);
		} else {
			scrollPaneConsumidorFinal.setVisible(false);
		}

		ltConsumidorFinal.setModel(list_model);
	}

	public void exibe_configuracoes(Configuracoes configuracoes_do_sistema) {

		if (configuracoes_do_sistema != null) {
			fTxtNomeEmpresa.setText(configuracoes_do_sistema.getNome_empresa());
			fTxtNomeResponsavel.setText(configuracoes_do_sistema.getResponsavel());
			fTxtCelular
					.setText(configuracoes_do_sistema.getCelular().replace("(", "").replace(")", "").replace("-", ""));
			fTxtTelFixo.setText(configuracoes_do_sistema.getTel_fixo());
			fTxtCnpj.setText(configuracoes_do_sistema.getCNPJ());
			fTxtInscricao.setText(configuracoes_do_sistema.getInscricao_estadual());
			fTxtEndereco.setText(configuracoes_do_sistema.getEndereco());
			fTxtEmail.setText(configuracoes_do_sistema.getEmail());
			cbxAltOrc.setSelectedItem(configuracoes_do_sistema.getAltera_orc());
			cbxGeraPdf.setSelectedItem(configuracoes_do_sistema.getGera_PDF());
			cbxParcelasDiferentes.setSelectedItem(configuracoes_do_sistema.getSalva_parc_dif());

			if (configuracoes_do_sistema.getConsumidor_final() != null) {
				consumidor_final = configuracoes_do_sistema.getConsumidor_final();
				fTxtConsumidorFinal.setText(consumidor_final.getNome());
				fTxtCodigoConsumidor.setText(consumidor_final.getIdCliente().toString());
			} else {
				fTxtConsumidorFinal.setText(null);
				fTxtCodigoConsumidor.setText(null);
			}
		} else {
			limpa_campos();
		}

	}

	public void ativar_campos() {
		fTxtNomeEmpresa.setEditable(true);
		fTxtNomeResponsavel.setEditable(true);
		fTxtCelular.setEditable(true);
		fTxtTelFixo.setEditable(true);
		fTxtCnpj.setEditable(true);
		fTxtInscricao.setEditable(true);
		fTxtEndereco.setEditable(true);
		fTxtEmail.setEditable(true);
		cbxAltOrc.setEnabled(true);
		cbxGeraPdf.setEnabled(true);
		cbxParcelasDiferentes.setEnabled(true);
		fTxtConsumidorFinal.setEditable(true);
	}

	public void desativar_campos() {
		fTxtNomeEmpresa.setEditable(false);
		fTxtNomeResponsavel.setEditable(false);
		fTxtCelular.setEditable(false);
		fTxtTelFixo.setEditable(false);
		fTxtCnpj.setEditable(false);
		fTxtInscricao.setEditable(false);
		fTxtEndereco.setEditable(false);
		fTxtEmail.setEditable(false);
		cbxAltOrc.setEnabled(false);
		cbxGeraPdf.setEnabled(false);
		cbxParcelasDiferentes.setEnabled(false);
		btnConfigurar.setEnabled(true);
		btnSalvar.setVisible(false);
		btnCancelar.setVisible(false);
		fTxtConsumidorFinal.setEditable(false);
		scrollPaneConsumidorFinal.setVisible(false);
	}

	public void limpa_campos() {
		fTxtNomeEmpresa.setText(null);
		fTxtNomeResponsavel.setText(null);
		fTxtCelular.setText(null);
		fTxtTelFixo.setText(null);
		fTxtCnpj.setText(null);
		fTxtInscricao.setText(null);
		fTxtEndereco.setText(null);
		fTxtEmail.setText(null);
		fTxtConsumidorFinal.setText(null);
		fTxtCodigoConsumidor.setText(null);
	}
}
