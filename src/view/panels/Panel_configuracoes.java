package view.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.MaskFormatter;

import dao.ConfiguracaoDAO;
import entities.configuracoes.Configuracoes;
import icons.Icones;

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
	private JTextPane txtPaviso;
	private Configuracoes configuracoes_do_sistema = new ConfiguracaoDAO().busca_configuracoes();

	/**
	 * Create the panel.
	 */
	public Panel_configuracoes() {
		setLayout(null);
		lblconfiguracoes = new JLabel("Configura\u00E7\u00F5es do Sistema");
		lblconfiguracoes.setHorizontalAlignment(SwingConstants.CENTER);
		lblconfiguracoes.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblconfiguracoes.setBounds(198, 11, 335, 29);
		add(lblconfiguracoes);

		separador_Configuracoes = new JSeparator();
		separador_Configuracoes.setBounds(10, 51, 709, 9);
		add(separador_Configuracoes);

		lblNomeEmpresa = new JLabel("Nome da empresa");
		lblNomeEmpresa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeEmpresa.setBounds(10, 256, 117, 19);
		add(lblNomeEmpresa);

		MaskFormatter mascara_nome_empresa = null;
		try {
			mascara_nome_empresa = new MaskFormatter("****************************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtNomeEmpresa = new JFormattedTextField(mascara_nome_empresa);
		fTxtNomeEmpresa.setEditable(false);
		fTxtNomeEmpresa.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaNomeEmpresa) {
				valida_campos();
			}
		});
		fTxtNomeEmpresa.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtNomeEmpresa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtNomeEmpresa.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeEmpresa.setColumns(10);
		fTxtNomeEmpresa.setBounds(125, 255, 293, 20);
		add(fTxtNomeEmpresa);

		btnConfigurar = new JButton("Configurar");
		btnConfigurar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickConfigurar) {
				valida_campos();
				ativar_campos();
				btnConfigurar.setVisible(false);
				btnSalvar.setVisible(true);
				btnCancelar.setVisible(true);
			}
		});
		btnConfigurar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnConfigurar.setBounds(10, 70, 124, 29);
		btnConfigurar.setIcon(icones.getIcone_engrenagem());
		add(btnConfigurar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancelar) {

				desativar_campos();
			}
		});
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.setBounds(606, 622, 113, 29);
		btnCancelar.setIcon(icones.getIcone_cancelar());
		btnCancelar.setVisible(false);
		add(btnCancelar);

		btnSalvar = new JButton("Salvar");
		btnSalvar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickSalvarConfiguracao) {
				if (btnSalvar.isEnabled()) {

					Configuracoes nova_conf = monta_configuracao();
					ConfiguracaoDAO conf_dao = new ConfiguracaoDAO();
					if (conf_dao.salva_configuracao(nova_conf)) {
						JOptionPane.showMessageDialog(fTxtInscricao, "Nova configuração salva.", "Configurações",
								JOptionPane.NO_OPTION);
						btnSalvar.setVisible(false);
						btnCancelar.setVisible(false);
						btnConfigurar.setVisible(true);
						desativar_campos();
					}
				}
			}
		});
		btnSalvar.setEnabled(false);
		btnSalvar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSalvar.setBounds(479, 622, 117, 29);
		btnSalvar.setIcon(icones.getIcone_salvar());
		btnSalvar.setVisible(false);
		add(btnSalvar);

		lblTelefone = new JLabel("Tel. Fixo");
		lblTelefone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTelefone.setBounds(10, 324, 50, 19);
		add(lblTelefone);

		MaskFormatter mascara_tel_fixo = null;
		try {
			mascara_tel_fixo = new MaskFormatter("(##)####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtTelFixo = new JFormattedTextField(mascara_tel_fixo);
		fTxtTelFixo.setEditable(false);
		fTxtTelFixo.setToolTipText("Nome mostrado na impress\u00E3o do or\u00E7amento");
		fTxtTelFixo.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtTelFixo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtTelFixo.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtTelFixo.setColumns(10);
		fTxtTelFixo.setBounds(64, 323, 101, 20);
		add(fTxtTelFixo);

		lblCelular = new JLabel("Celular");
		lblCelular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCelular.setBounds(185, 324, 44, 19);
		add(lblCelular);

		MaskFormatter mascara_celular = null;

		try {
			mascara_celular = new MaskFormatter("(##)#####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtCelular = new JFormattedTextField(mascara_celular);
		fTxtCelular.setEditable(false);
		fTxtCelular.setToolTipText("Nome mostrado na impress\u00E3o do or\u00E7amento");
		fTxtCelular.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtCelular.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCelular.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCelular.setColumns(10);
		fTxtCelular.setBounds(229, 323, 107, 20);
		add(fTxtCelular);

		lblInformacoesDaEmpresa = new JLabel("Informa\u00E7\u00F5es da Empresa");
		lblInformacoesDaEmpresa.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformacoesDaEmpresa.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblInformacoesDaEmpresa.setBounds(10, 216, 233, 29);
		add(lblInformacoesDaEmpresa);

		separado_informacoes_empresa = new JSeparator();
		separado_informacoes_empresa.setBounds(242, 233, 477, 9);
		add(separado_informacoes_empresa);

		lblEndereco = new JLabel("Endereco");
		lblEndereco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEndereco.setBounds(10, 359, 64, 19);
		add(lblEndereco);

		MaskFormatter mascara_endereco = null;

		try {
			mascara_endereco = new MaskFormatter("**************************************************************");
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		fTxtEndereco = new JFormattedTextField(mascara_endereco);
		fTxtEndereco.setEditable(false);
		fTxtEndereco.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaEndereco) {
				valida_campos();
			}
		});
		fTxtEndereco.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtEndereco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtEndereco.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtEndereco.setColumns(10);
		fTxtEndereco.setBounds(74, 358, 364, 20);
		add(fTxtEndereco);

		lblResponsavel = new JLabel("Respons\u00E1vel");
		lblResponsavel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblResponsavel.setBounds(428, 256, 82, 19);
		add(lblResponsavel);

		fTxtNomeResponsavel = new JFormattedTextField();
		fTxtNomeResponsavel.setEditable(false);
		fTxtNomeResponsavel.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtNomeResponsavel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtNomeResponsavel.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtNomeResponsavel.setColumns(10);
		fTxtNomeResponsavel.setBounds(508, 255, 211, 20);
		add(fTxtNomeResponsavel);

		lblCnpj = new JLabel("CNPJ");
		lblCnpj.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCnpj.setBounds(10, 291, 38, 19);
		add(lblCnpj);

		MaskFormatter mascara_cnpj = null;
		try {
			mascara_cnpj = new MaskFormatter("##.###.###/####-##");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtCnpj = new JFormattedTextField(mascara_cnpj);
		fTxtCnpj.setEditable(false);
		fTxtCnpj.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent digitaCNPJ) {
				valida_campos();
			}
		});
		fTxtCnpj.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtCnpj.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtCnpj.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCnpj.setColumns(10);
		fTxtCnpj.setBounds(46, 290, 134, 20);
		add(fTxtCnpj);

		lblInscricaoEstadual = new JLabel("Inscri\u00E7\u00E3o Estadual");
		lblInscricaoEstadual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInscricaoEstadual.setBounds(212, 290, 117, 19);
		add(lblInscricaoEstadual);

		MaskFormatter mascara_ie = null;

		try {
			mascara_ie = new MaskFormatter("#########.##-##");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		fTxtInscricao = new JFormattedTextField(mascara_ie);
		fTxtInscricao.setEditable(false);
		fTxtInscricao.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtInscricao.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtInscricao.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtInscricao.setColumns(10);
		fTxtInscricao.setBounds(326, 290, 134, 20);
		add(fTxtInscricao);

		lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEmail.setBounds(400, 324, 38, 19);
		add(lblEmail);

		fTxtEmail = new JFormattedTextField();
		fTxtEmail.setEditable(false);
		fTxtEmail.setHorizontalAlignment(SwingConstants.LEFT);
		fTxtEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		fTxtEmail.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtEmail.setColumns(10);
		fTxtEmail.setBounds(434, 324, 285, 20);
		add(fTxtEmail);

		lblConfiguracoes_faturamento = new JLabel("Configura\u00E7\u00F5es do Faturamento");
		lblConfiguracoes_faturamento.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfiguracoes_faturamento.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblConfiguracoes_faturamento.setBounds(10, 434, 279, 29);
		add(lblConfiguracoes_faturamento);

		separado_informacoes_empresa_1 = new JSeparator();
		separado_informacoes_empresa_1.setBounds(293, 451, 426, 9);
		add(separado_informacoes_empresa_1);

		lblSalvarParcelasDivergentes = new JLabel("Salva parcelas diferentes do total");
		lblSalvarParcelasDivergentes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSalvarParcelasDivergentes.setBounds(10, 470, 206, 19);
		add(lblSalvarParcelasDivergentes);

		cbxParcelasDiferentes = new JComboBox<String>();
		cbxParcelasDiferentes.setEnabled(false);
		cbxParcelasDiferentes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxParcelasDiferentes.setModel(new DefaultComboBoxModel(new String[] { "SIM", "N\u00C3O", "PERGUNTAR" }));
		cbxParcelasDiferentes.setBounds(214, 465, 114, 22);
		add(cbxParcelasDiferentes);

		JLabel lblGeraPdf = new JLabel("Gerar PDF ap\u00F3s confirmar or\u00E7amento");
		lblGeraPdf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGeraPdf.setBounds(368, 470, 228, 19);
		add(lblGeraPdf);

		cbxGeraPdf = new JComboBox<String>();
		cbxGeraPdf.setEnabled(false);
		cbxGeraPdf.setModel(new DefaultComboBoxModel(new String[] { "SIM", "N\u00C3O", "PERGUNTAR" }));
		cbxGeraPdf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxGeraPdf.setBounds(602, 465, 117, 22);
		add(cbxGeraPdf);

		lblAlterarOramentoJ = new JLabel("Alterar or\u00E7amentos j\u00E1 fechados");
		lblAlterarOramentoJ.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAlterarOramentoJ.setBounds(10, 505, 190, 19);
		add(lblAlterarOramentoJ);

		cbxAltOrc = new JComboBox<String>();
		cbxAltOrc.setEnabled(false);
		cbxAltOrc.setModel(new DefaultComboBoxModel(new String[] { "SIM", "N\u00C3O", "PERGUNTAR" }));
		cbxAltOrc.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxAltOrc.setBounds(204, 502, 117, 22);
		add(cbxAltOrc);

		txtPaviso = new JTextPane();
		txtPaviso.setBackground(new Color(240, 240, 240));
		txtPaviso.setForeground(Color.RED);
		txtPaviso.setText(
				"ATEN\u00C7\u00C3O\r\nApós alterar alguma configuração, é necessário fechar o sistema e abrir novamente para que o mesmo utilize as novas configurações salvas.");
		txtPaviso.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPaviso.setEditable(false);
		txtPaviso.setBounds(6, 130, 713, 64);
		add(txtPaviso);

		String texto = "ATEN\u00C7\u00C3O\r\nApós alterar alguma configuração, é necessário fechar o sistema e abrir novamente para que o mesmo utilize as novas configurações salvas.";
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
		CNPJ = fTxtCnpj.getText().trim();
		if (!fTxtInscricao.getText().trim().isEmpty()) {
			inscricao_estadual = fTxtInscricao.getText().trim();
		}
		if (!fTxtTelFixo.getText().trim().isEmpty()) {
			tel_fixo = fTxtTelFixo.getText().trim();
		}
		if (!fTxtCelular.getText().trim().isEmpty()) {
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
				email, endereco, salva_parc_dif, altera_orc, gera_PDF);

		return conf;
	}

	public void valida_campos() {
		if (!fTxtNomeEmpresa.getText().trim().isEmpty() && !fTxtCnpj.getText().trim().isEmpty()
				&& !fTxtEndereco.getText().trim().isEmpty()) {
			btnSalvar.setEnabled(true);
		} else {
			btnSalvar.setEnabled(false);
		}
	}

	public void exibe_configuracoes(Configuracoes configuracoes_do_sistema) {

		if (configuracoes_do_sistema != null) {
			fTxtNomeEmpresa.setText(configuracoes_do_sistema.getNome_empresa());
			fTxtNomeResponsavel.setText(configuracoes_do_sistema.getResponsavel());
			fTxtCelular.setText(configuracoes_do_sistema.getCelular());
			fTxtTelFixo.setText(configuracoes_do_sistema.getTel_fixo());
			fTxtCnpj.setText(configuracoes_do_sistema.getCNPJ());
			fTxtInscricao.setText(configuracoes_do_sistema.getInscricao_estadual());
			fTxtEndereco.setText(configuracoes_do_sistema.getEndereco());
			fTxtEmail.setText(configuracoes_do_sistema.getEmail());
			cbxAltOrc.setSelectedItem(configuracoes_do_sistema.getAltera_orc());
			cbxGeraPdf.setSelectedItem(configuracoes_do_sistema.getGera_PDF());
			cbxParcelasDiferentes.setSelectedItem(configuracoes_do_sistema.getSalva_parc_dif());
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
		btnConfigurar.setVisible(true);
		btnSalvar.setVisible(false);
		btnCancelar.setVisible(false);
	}
}
