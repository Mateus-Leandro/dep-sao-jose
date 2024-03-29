package view.panels.configuracoes;

import java.awt.Color;
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

import dao.configuracoes.ConfiguracaoDAO;
import dao.pessoa.ClienteDAO;
import entities.configuracoes.Configuracoes;
import entities.pessoa.Cliente;
import icons.Icones;
import tools.Move_cursor_inicio;
import view.frames.TelaPrincipal;

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
	private JSeparator separador_informacoes_empresa;
	private JLabel lblEndereco;
	private JFormattedTextField fTxtEndereco;
	private JLabel lblResponsavel;
	private JFormattedTextField fTxtNomeResponsavel;
	private JLabel lblCnpj;
	private JFormattedTextField fTxtCnpj;
	private JLabel lblEmail;
	private JFormattedTextField fTxtEmail;
	private JLabel lblConfiguracoes_orcamento;
	private JSeparator separador_conf_orcamento;
	private JLabel lblSalvarParcelasDivergentes;
	private JComboBox<String> cbxParcelasDiferentes;
	private JComboBox<String> cbxGeraPdf;
	private JLabel lblAlterarOramentoJ;
	private JComboBox<String> cbxAltOrc;
	private ConfiguracaoDAO conf_dao = new ConfiguracaoDAO();
	private Configuracoes configuracoes_do_sistema = conf_dao.busca_configuracoes();
	private JLabel lblConsumidorFinal;
	private JFormattedTextField fTxtConsumidorFinal;
	private JScrollPane scrollPaneConsumidorFinal;
	private JList<Cliente> ltConsumidorFinal;
	private DefaultListModel<Cliente> list_model = new DefaultListModel<Cliente>();
	private ArrayList<Cliente> lista_consumidor_final = new ArrayList<Cliente>();
	private Cliente consumidor_final = null;
	private JLabel lblCodConsumidorFinal;
	private JFormattedTextField fTxtCodigoConsumidor;
	private JLabel lblNomeConsumidorFinal;
	private JLabel lblObg_celular;
	private JLabel lblObg_consumidor;
	private JLabel lblObg_nomeEmpresa;
	private Move_cursor_inicio move_cursor_inicio = new Move_cursor_inicio();
	private JLabel lblObsConsumidor;
	private JLabel lblConfiguracoes_faturamento_1;
	private JSeparator separador_configuracoes_produtos;
	private JLabel lblVinculaBarras;
	private JComboBox<String> cbxVinculaBarras;
	private JLabel lblDica;
	private JButton btnReload;
	private JSeparator separador_configuracoes_produtos_1;
	private TelaPrincipal tela_principal;

	/**
	 * Create the panel.
	 */
	public Panel_configuracoes(TelaPrincipal tela_principal) {
		this.tela_principal = tela_principal;
		ltConsumidorFinal = new JList<Cliente>();
		ltConsumidorFinal.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickConsumidorFinal) {
				scrollPaneConsumidorFinal.setVisible(false);
				consumidor_final = ltConsumidorFinal.getSelectedValue();
				fTxtConsumidorFinal.setText(consumidor_final.getNome());
				fTxtCodigoConsumidor.setText(consumidor_final.getId().toString());
				valida_campos();
			}
		});
		ltConsumidorFinal.setVisibleRowCount(10);
		ltConsumidorFinal.setBounds(122, 451, 276, 70);

		scrollPaneConsumidorFinal = new JScrollPane(ltConsumidorFinal);
		scrollPaneConsumidorFinal.setBounds(263, 550, 404, 70);
		scrollPaneConsumidorFinal.setVisible(false);
		setLayout(null);
		add(scrollPaneConsumidorFinal);

		lblconfiguracoes = new JLabel("Configura\u00E7\u00F5es do Sistema");
		lblconfiguracoes.setBounds(351, 11, 335, 29);
		lblconfiguracoes.setHorizontalAlignment(SwingConstants.CENTER);
		lblconfiguracoes.setFont(new Font("Tahoma", Font.BOLD, 24));
		add(lblconfiguracoes);

		separador_Configuracoes = new JSeparator();
		separador_Configuracoes.setBounds(10, 51, 1001, 9);
		add(separador_Configuracoes);

		lblNomeEmpresa = new JLabel("Nome da empresa");
		lblNomeEmpresa.setBounds(10, 186, 117, 19);
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
				move_cursor_inicio.move_cursor_inicio(fTxtNomeEmpresa);
			}
		});
		fTxtNomeEmpresa.setToolTipText("Nome mostrado na impress\u00E3o do or\u00E7amento");
		fTxtNomeEmpresa.setBounds(126, 185, 277, 20);
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
					btnReload.setEnabled(false);
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
		btnCancelar.setBounds(896, 586, 113, 29);
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
		btnSalvar.setBounds(769, 586, 117, 29);
		btnSalvar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickSalvarConfiguracao) {
				if (btnSalvar.isEnabled()) {
					salvar_configuracoes();
				}
			}
		});
		btnSalvar.setEnabled(false);
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSalvar.setIcon(icones.getIcone_salvar());
		btnSalvar.setVisible(false);
		add(btnSalvar);

		lblTelefone = new JLabel("Telefone Fixo");
		lblTelefone.setBounds(10, 249, 82, 19);
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
				if (fTxtTelFixo.getText().equals("(  )    -    ")) {
					fTxtTelFixo.setCaretPosition(0);
				}
			}
		});
		fTxtTelFixo.setBounds(94, 248, 101, 20);
		fTxtTelFixo.setEditable(false);
		fTxtTelFixo.setToolTipText("");
		fTxtTelFixo.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtTelFixo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtTelFixo.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtTelFixo.setColumns(10);
		add(fTxtTelFixo);

		lblCelular = new JLabel("Celular");
		lblCelular.setBounds(219, 250, 44, 19);
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
				if (fTxtCelular.getText().equals("(  )     -    ")) {
					fTxtCelular.setCaretPosition(0);
				}
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
		fTxtCelular.setBounds(263, 249, 116, 20);
		fTxtCelular.setEditable(false);
		fTxtCelular.setToolTipText("Telefone Mostrado na impress\u00E3o do or\u00E7amento");
		fTxtCelular.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtCelular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCelular.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCelular.setColumns(10);
		add(fTxtCelular);

		lblInformacoesDaEmpresa = new JLabel("Informa\u00E7\u00F5es da Empresa");
		lblInformacoesDaEmpresa.setBounds(10, 151, 226, 29);
		lblInformacoesDaEmpresa.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformacoesDaEmpresa.setFont(new Font("Tahoma", Font.BOLD, 18));
		add(lblInformacoesDaEmpresa);

		separador_informacoes_empresa = new JSeparator();
		separador_informacoes_empresa.setBounds(238, 169, 773, 9);
		add(separador_informacoes_empresa);

		lblEndereco = new JLabel("Endereco");
		lblEndereco.setBounds(10, 217, 63, 19);
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
					fTxtTelFixo.requestFocus();
				}
			}
		});
		fTxtEndereco.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEndereco) {
				move_cursor_inicio.move_cursor_inicio(fTxtEndereco);
			}
		});
		fTxtEndereco.setBounds(74, 216, 363, 20);
		fTxtEndereco.setEditable(false);
		fTxtEndereco.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtEndereco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtEndereco.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtEndereco.setColumns(10);
		add(fTxtEndereco);

		lblResponsavel = new JLabel("Respons\u00E1vel");
		lblResponsavel.setBounds(483, 187, 82, 19);
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
				move_cursor_inicio.move_cursor_inicio(fTxtNomeEmpresa);
			}
		});
		fTxtNomeResponsavel.setBounds(561, 185, 252, 20);
		fTxtNomeResponsavel.setEditable(false);
		fTxtNomeResponsavel.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtNomeResponsavel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtNomeResponsavel.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeResponsavel.setColumns(10);
		add(fTxtNomeResponsavel);

		lblCnpj = new JLabel("CNPJ");
		lblCnpj.setBounds(841, 187, 38, 19);
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
			public void keyReleased(KeyEvent enterCnpj) {
				if (enterCnpj.getKeyCode() == enterCnpj.VK_ENTER) {
					fTxtEndereco.requestFocus();
				}
			}
		});
		fTxtCnpj.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCnpj) {
				fTxtCnpj.setCaretPosition(0);
			}
		});
		fTxtCnpj.setBounds(877, 186, 134, 20);
		fTxtCnpj.setEditable(false);
		fTxtCnpj.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtCnpj.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCnpj.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCnpj.setColumns(10);
		add(fTxtCnpj);

		MaskFormatter mascara_ie = null;

		try {
			mascara_ie = new MaskFormatter("#########.##-##");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		lblEmail = new JLabel("Email");
		lblEmail.setBounds(516, 248, 38, 19);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblEmail);

		fTxtEmail = new JFormattedTextField();
		fTxtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterEmail) {
				if (enterEmail.getKeyCode() == enterEmail.VK_ENTER) {
					cbxParcelasDiferentes.requestFocus();
				}
			}
		});
		fTxtEmail.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickEmail) {
				move_cursor_inicio.move_cursor_inicio(fTxtEmail);
			}
		});
		fTxtEmail.setBounds(550, 248, 285, 20);
		fTxtEmail.setEditable(false);
		fTxtEmail.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtEmail.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtEmail.setColumns(10);
		add(fTxtEmail);

		lblConfiguracoes_orcamento = new JLabel("Configura\u00E7\u00F5es do Or\u00E7amento");
		lblConfiguracoes_orcamento.setBounds(10, 305, 263, 29);
		lblConfiguracoes_orcamento.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfiguracoes_orcamento.setFont(new Font("Tahoma", Font.BOLD, 18));
		add(lblConfiguracoes_orcamento);

		separador_conf_orcamento = new JSeparator();
		separador_conf_orcamento.setBounds(275, 320, 736, 9);
		add(separador_conf_orcamento);

		lblSalvarParcelasDivergentes = new JLabel(
				"Permitir salvar parcelas onde a soma seja \u2260 do total do or\u00E7amento");
		lblSalvarParcelasDivergentes.setToolTipText(
				"Permitir gravar parcelas quando o total delas for diferente do total do or\u00E7amento.");
		lblSalvarParcelasDivergentes.setBounds(10, 338, 427, 19);
		lblSalvarParcelasDivergentes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblSalvarParcelasDivergentes);

		cbxParcelasDiferentes = new JComboBox<String>();
		cbxParcelasDiferentes.setToolTipText(
				"Permitir gravar parcelas quando o total delas for diferente do total do or\u00E7amento.");
		cbxParcelasDiferentes.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterParcelasDif) {
				if (enterParcelasDif.getKeyCode() == enterParcelasDif.VK_ENTER) {
					cbxAltOrc.requestFocus();
				}
			}
		});
		cbxParcelasDiferentes.setBounds(421, 336, 114, 22);
		cbxParcelasDiferentes.setEnabled(false);
		cbxParcelasDiferentes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxParcelasDiferentes.setModel(new DefaultComboBoxModel(new String[] { "SIM", "N\u00C3O", "PERGUNTAR" }));
		add(cbxParcelasDiferentes);

		JLabel lblGeraPdf = new JLabel("Gerar e abrir PDF ap\u00F3s incluir novo or\u00E7amento");
		lblGeraPdf.setToolTipText("Gerar PDF ap\u00F3s confirmar o or\u00E7amento.");
		lblGeraPdf.setBounds(10, 378, 285, 19);
		lblGeraPdf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblGeraPdf);

		cbxGeraPdf = new JComboBox<String>();
		cbxGeraPdf.setToolTipText("Gerar PDF ap\u00F3s confirmar o or\u00E7amento.");
		cbxGeraPdf.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterGeraPdf) {
				if (enterGeraPdf.getKeyCode() == enterGeraPdf.VK_ENTER) {
					cbxVinculaBarras.requestFocus();
				}
			}
		});
		cbxGeraPdf.setBounds(296, 376, 117, 22);
		cbxGeraPdf.setEnabled(false);
		cbxGeraPdf.setModel(new DefaultComboBoxModel(new String[] { "SIM", "N\u00C3O", "PERGUNTAR" }));
		cbxGeraPdf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(cbxGeraPdf);

		lblAlterarOramentoJ = new JLabel("Permite alterar or\u00E7amento com parcelas lan\u00E7adas");
		lblAlterarOramentoJ.setToolTipText(
				"Permitir editar os itens e valores do or\u00E7amento quando ele possuir parcelas lan\u00E7adas.");
		lblAlterarOramentoJ.setBounds(591, 340, 307, 19);
		lblAlterarOramentoJ.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblAlterarOramentoJ);

		cbxAltOrc = new JComboBox<String>();
		cbxAltOrc.setToolTipText(
				"Permitir editar os itens e valores do or\u00E7amento quando ele possuir parcelas lan\u00E7adas.");
		cbxAltOrc.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterAltOrc) {
				if (enterAltOrc.getKeyCode() == enterAltOrc.VK_ENTER) {
					cbxGeraPdf.requestFocus();
				}
			}
		});
		cbxAltOrc.setBounds(894, 336, 117, 22);
		cbxAltOrc.setEnabled(false);
		cbxAltOrc.setModel(new DefaultComboBoxModel(new String[] { "SIM", "N\u00C3O", "PERGUNTAR" }));
		cbxAltOrc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(cbxAltOrc);

		lblConsumidorFinal = new JLabel("Consumidor Final");
		lblConsumidorFinal.setBounds(10, 500, 163, 19);
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
				if (!btnConfigurar.isEnabled()) {
					move_cursor_inicio.move_cursor_inicio(fTxtConsumidorFinal);
					alimentar_lista_consumidor_final("NOME", "%");
				}
			}
		});
		fTxtConsumidorFinal.setBounds(263, 531, 404, 20);
		fTxtConsumidorFinal.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaConsumidorFinal) {

				if (valida_campos()) {
					if (digitaConsumidorFinal.getKeyCode() == digitaConsumidorFinal.VK_ENTER) {
						salvar_configuracoes();
						btnConfigurar.requestFocus();
					} else {
						if (fTxtConsumidorFinal.getText().trim().isEmpty()) {
							alimentar_lista_consumidor_final("NOME", null);
							consumidor_final = null;
							fTxtCodigoConsumidor.setText(null);
						} else {
							alimentar_lista_consumidor_final("NOME", fTxtConsumidorFinal.getText().trim());
						}
					}
				}
			}
		});
		fTxtConsumidorFinal.setEditable(false);
		fTxtConsumidorFinal.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtConsumidorFinal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtConsumidorFinal.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtConsumidorFinal.setColumns(10);
		add(fTxtConsumidorFinal);

		lblCodConsumidorFinal = new JLabel("C\u00F3digo");
		lblCodConsumidorFinal.setBounds(10, 532, 44, 19);
		lblCodConsumidorFinal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblCodConsumidorFinal);

		fTxtCodigoConsumidor = new JFormattedTextField();
		fTxtCodigoConsumidor.setBounds(55, 530, 72, 20);
		fTxtCodigoConsumidor.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtCodigoConsumidor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCodigoConsumidor.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCodigoConsumidor.setEditable(false);
		fTxtCodigoConsumidor.setColumns(10);
		add(fTxtCodigoConsumidor);

		lblNomeConsumidorFinal = new JLabel("Nome");
		lblNomeConsumidorFinal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeConsumidorFinal.setBounds(219, 532, 41, 19);
		add(lblNomeConsumidorFinal);

		lblObg_celular = new JLabel("*");
		lblObg_celular.setForeground(Color.RED);
		lblObg_celular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObg_celular.setBounds(381, 260, 20, 15);
		add(lblObg_celular);

		lblObg_consumidor = new JLabel("*");
		lblObg_consumidor.setForeground(Color.RED);
		lblObg_consumidor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObg_consumidor.setBounds(670, 536, 20, 15);
		add(lblObg_consumidor);

		lblObg_nomeEmpresa = new JLabel("*");
		lblObg_nomeEmpresa.setForeground(Color.RED);
		lblObg_nomeEmpresa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblObg_nomeEmpresa.setBounds(404, 193, 20, 15);
		add(lblObg_nomeEmpresa);

		lblObsConsumidor = new JLabel(
				"* O sistema utilizar\u00E1 o consumidor final sempre que n\u00E3o for informado algum cliente no or\u00E7amento.");
		lblObsConsumidor.setForeground(Color.BLUE);
		lblObsConsumidor.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblObsConsumidor.setBounds(10, 593, 610, 19);
		add(lblObsConsumidor);

		lblConfiguracoes_faturamento_1 = new JLabel("Configura\u00E7\u00F5es do cadastro de produtos");
		lblConfiguracoes_faturamento_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfiguracoes_faturamento_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblConfiguracoes_faturamento_1.setBounds(10, 409, 353, 29);
		add(lblConfiguracoes_faturamento_1);

		separador_configuracoes_produtos = new JSeparator();
		separador_configuracoes_produtos.setBounds(366, 426, 645, 9);
		add(separador_configuracoes_produtos);

		lblVinculaBarras = new JLabel("Abrir vincula\u00E7\u00E3o de cod. barras ao cadastrar novo produto");
		lblVinculaBarras
				.setToolTipText("Ap\u00F3s cadastrar o produto, abrir tela para inserir c\u00F3digo de barras.");
		lblVinculaBarras.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVinculaBarras.setBounds(10, 440, 358, 19);
		add(lblVinculaBarras);

		cbxVinculaBarras = new JComboBox<String>();
		cbxVinculaBarras.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent enterVinculaBarras) {
				if (enterVinculaBarras.getKeyCode() == enterVinculaBarras.VK_ENTER) {
					fTxtConsumidorFinal.requestFocus();
				}
			}
		});
		cbxVinculaBarras.setModel(new DefaultComboBoxModel(new String[] { "SIM", "N\u00C3O", "PERGUNTAR" }));
		cbxVinculaBarras
				.setToolTipText("Ap\u00F3s cadastrar o produto, abrir tela para inserir c\u00F3digo de barras.");
		cbxVinculaBarras.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxVinculaBarras.setEnabled(false);
		cbxVinculaBarras.setBounds(373, 437, 114, 22);
		add(cbxVinculaBarras);

		lblDica = new JLabel(
				"Dica: *Passe o mouse em cima da configura\u00E7\u00E3o e aguarde para ver a explica\u00E7\u00E3o da sua fun\u00E7\u00E3o.");
		lblDica.setToolTipText("Exemplo: Explica\u00E7\u00E3o da fun\u00E7\u00E3o.");
		lblDica.setForeground(new Color(0, 128, 0));
		lblDica.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDica.setBounds(10, 121, 590, 19);
		add(lblDica);

		btnReload = new JButton();
		btnReload.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickRecarregarConfiguracoes) {
				if (btnReload.isEnabled()) {
					configuracoes_do_sistema = conf_dao.busca_configuracoes();
					exibe_configuracoes(configuracoes_do_sistema);
				}
			}
		});
		btnReload.setToolTipText("Recarregar configura\u00E7\u00F5es");
		btnReload.setIcon(icones.getIcone_reload());
		btnReload.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnReload.setBounds(961, 71, 50, 29);
		add(btnReload);

		separador_configuracoes_produtos_1 = new JSeparator();
		separador_configuracoes_produtos_1.setBounds(158, 500, 853, 9);
		add(separador_configuracoes_produtos_1);

		exibe_configuracoes(configuracoes_do_sistema);

	}

	public Configuracoes monta_configuracao() {

		String nome_empresa = null;
		String responsavel = null;
		String CNPJ = null;
		String tel_fixo = null;
		String celular = null;
		String email = null;
		String endereco = null;
		String salva_parc_dif = null;
		String altera_orc = null;
		String gera_PDF = null;
		String vincula_barras = null;

		nome_empresa = fTxtNomeEmpresa.getText().trim();
		if (!fTxtNomeResponsavel.getText().trim().isEmpty()) {
			responsavel = fTxtNomeResponsavel.getText().trim();
		}

		if (!fTxtCnpj.getText().equals("  .   .   /    -  ")) {
			CNPJ = fTxtCnpj.getText().trim();
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
		vincula_barras = cbxVinculaBarras.getSelectedItem().toString();

		Configuracoes conf = new Configuracoes(nome_empresa, responsavel, CNPJ, tel_fixo, celular, email, endereco,
				salva_parc_dif, altera_orc, gera_PDF, vincula_barras, consumidor_final);

		return conf;
	}

	public boolean valida_campos() {
		if (!fTxtNomeEmpresa.getText().trim().isEmpty() && !fTxtCelular.getText().equals("(  )     -    ")
				&& consumidor_final != null) {
			btnSalvar.setEnabled(true);
			return true;
		} else {
			btnSalvar.setEnabled(false);
			return false;
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
			fTxtEndereco.setText(configuracoes_do_sistema.getEndereco());
			fTxtEmail.setText(configuracoes_do_sistema.getEmail());
			cbxAltOrc.setSelectedItem(configuracoes_do_sistema.getAltera_orc());
			cbxGeraPdf.setSelectedItem(configuracoes_do_sistema.getGera_PDF());
			cbxParcelasDiferentes.setSelectedItem(configuracoes_do_sistema.getSalva_parc_dif());
			cbxVinculaBarras.setSelectedItem(configuracoes_do_sistema.getVincula_barras());

			if (configuracoes_do_sistema.getConsumidor_final() != null) {
				consumidor_final = configuracoes_do_sistema.getConsumidor_final();
				fTxtConsumidorFinal.setText(consumidor_final.getNome());
				fTxtCodigoConsumidor.setText(consumidor_final.getId().toString());
			} else {
				fTxtConsumidorFinal.setText(null);
				fTxtCodigoConsumidor.setText(null);
			}
		} else {
			limpa_campos();
		}

	}

	public boolean salvar_configuracoes() {
		configuracoes_do_sistema = monta_configuracao();
		ConfiguracaoDAO conf_dao = new ConfiguracaoDAO();
		if (conf_dao.salva_configuracao(configuracoes_do_sistema)) {
			if (consumidor_final != null) {
				fTxtConsumidorFinal.setText(consumidor_final.getNome());
			}
			JOptionPane.showMessageDialog(null, "Nova configuração salva.", "Configurações", JOptionPane.NO_OPTION);
			btnSalvar.setVisible(false);
			btnCancelar.setVisible(false);
			btnConfigurar.setEnabled(true);
			desativar_campos();
			tela_principal.abas_configuracao_inicial(true);
			tela_principal.setTitle(configuracoes_do_sistema.getNome_empresa() + " - " + tela_principal.versao);

			return true;
		} else {
			return false;
		}
	}

	public void ativar_campos() {
		fTxtNomeEmpresa.setEditable(true);
		fTxtNomeResponsavel.setEditable(true);
		fTxtCelular.setEditable(true);
		fTxtTelFixo.setEditable(true);
		fTxtCnpj.setEditable(true);
		fTxtEndereco.setEditable(true);
		fTxtEmail.setEditable(true);
		cbxAltOrc.setEnabled(true);
		cbxGeraPdf.setEnabled(true);
		cbxParcelasDiferentes.setEnabled(true);
		cbxVinculaBarras.setEnabled(true);
		fTxtConsumidorFinal.setEditable(true);
	}

	public void desativar_campos() {
		fTxtNomeEmpresa.setEditable(false);
		fTxtNomeResponsavel.setEditable(false);
		fTxtCelular.setEditable(false);
		fTxtTelFixo.setEditable(false);
		fTxtCnpj.setEditable(false);
		fTxtEndereco.setEditable(false);
		fTxtEmail.setEditable(false);
		cbxAltOrc.setEnabled(false);
		cbxGeraPdf.setEnabled(false);
		cbxParcelasDiferentes.setEnabled(false);
		btnSalvar.setVisible(false);
		btnCancelar.setVisible(false);
		cbxVinculaBarras.setEnabled(false);
		fTxtConsumidorFinal.setEditable(false);
		scrollPaneConsumidorFinal.setVisible(false);

		btnConfigurar.setEnabled(true);
		btnReload.setEnabled(true);
	}

	public void limpa_campos() {
		fTxtNomeEmpresa.setText(null);
		fTxtNomeResponsavel.setText(null);
		fTxtCelular.setText(null);
		fTxtTelFixo.setText(null);
		fTxtCnpj.setText(null);
		fTxtEndereco.setText(null);
		fTxtEmail.setText(null);
		fTxtConsumidorFinal.setText(null);
		fTxtCodigoConsumidor.setText(null);
	}
}
