package view.frames;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import bkp.BkpBanco;
import dao.configuracoes.ConfiguracaoDAO;
import dao.pessoa.ClienteDAO;
import db.DbCriaTabelas;
import entities.configuracoes.Configuracoes;
import entities.pessoa.Cliente;
import view.panels.Panel_bkp;
import view.panels.configuracoes.Panel_configuracoes;
import view.panels.orcamento.Panel_orcamento;
import view.panels.pedido.Panel_pedido;
import view.panels.pessoa.Panel_cliente;
import view.panels.pessoa.fornecedor.Panel_Fornecedor;
import view.panels.produto.Panel_produtos;

public class TelaPrincipal extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConfiguracaoDAO conf_dao = new ConfiguracaoDAO();
	private Configuracoes configuracoes_do_sistema = conf_dao.busca_configuracoes();
	private DbCriaTabelas dbCriaTabelas = new DbCriaTabelas();
	private JPanel contentPane;
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	public Panel_cliente clientes;
	private Panel_Fornecedor fornecedores;
	private Panel_produtos produtos;
	private Panel_orcamento orcamentos;
	private Panel_pedido pedidos;
	private Panel_configuracoes configuracoes;
	private Panel_bkp panel_bkp;
	private BkpBanco bkp_banco = new BkpBanco();
	private Boolean faz_bkp = bkp_banco.faz_bkp();
	private Boolean faz_bkp_diario = bkp_banco.faz_bkp_diario();
	public String versao = "v. 05.01.23";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal frame = new TelaPrincipal();
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
	public TelaPrincipal() {
		UIManager.put("OptionPane.yesButtonText", "Sim");
		UIManager.put("OptionPane.noButtonText", "Não");


		dbCriaTabelas.gera_tabelas();

		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/ferramentas.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1060, 734);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane.setBorder(UIManager.getBorder("CheckBoxMenuItem.border"));
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabbedPane.setBounds(5, 5, 1035, 679);
		contentPane.add(tabbedPane);
		setLocationRelativeTo(null);

		produtos = new Panel_produtos();
		clientes = new Panel_cliente();
//		fornecedor_pedidos = new Panel_fornedor_pedido();
		fornecedores = new Panel_Fornecedor();
		orcamentos = new Panel_orcamento();
		pedidos = new Panel_pedido();
		configuracoes = new Panel_configuracoes(this);
		panel_bkp = new Panel_bkp();

		if (faz_bkp_diario) {
			if (bkp_banco.bkp_diario()) {
				JOptionPane.showMessageDialog(null, "Backup diário realizado.", "Backup diário.",
						JOptionPane.NO_OPTION);
				panel_bkp.le_log();
			}
		}

		clientes.setVisible(true);
		produtos.setVisible(true);
		orcamentos.setVisible(true);

		tabbedPane.addTab("Clientes", clientes);
		tabbedPane.addTab("Fornecedores", fornecedores);
		tabbedPane.addTab("Produtos", produtos);
		tabbedPane.addTab("Orçamentos", orcamentos);
		tabbedPane.addTab("Pedidos de compra", pedidos);

		if (!configuracoes_do_sistema.getSo_orcamento()) {
			tabbedPane.addTab("Configuraçoes", configuracoes);
			// Testa se a máquina que o sistema está executando faz backup.
			if (faz_bkp) {
				tabbedPane.addTab("Backup", panel_bkp);
			}
		}

		// Verifica se existe configurações e se existe nome para a empresa.
		if (configuracoes_do_sistema != null) {
			so_orcamentos();
			if (configuracoes_do_sistema.getNome_empresa() != null) {
				if (configuracoes_do_sistema.getSo_orcamento()) {
					setTitle(configuracoes_do_sistema.getNome_empresa() + "*" + " - " + versao);
				} else {
					setTitle(configuracoes_do_sistema.getNome_empresa() + " - " + versao);
				}
			}
		} else {
			setTitle("NOME DA EMPRESA" + " - " + versao);
			ClienteDAO cliente_dao = new ClienteDAO();
			ArrayList<Cliente> clientes_cadastrados = new ArrayList<Cliente>();
			clientes_cadastrados = cliente_dao.listarClientes(clientes_cadastrados, null, null, 1);

			if (clientes_cadastrados.size() < 1) {
				// Incluindo consumidor final
				Cliente consumidor_final = new Cliente(null, "Consumidor final", null, false, null, null, null, null,
						null, null, null, null, null, "(99)99999-9999", null, false,
						new java.sql.Date(System.currentTimeMillis()));
				clientes.salvar_pessoa(consumidor_final);
			}

			abas_configuracao_inicial(false);
			JOptionPane.showMessageDialog(null, "Necessário realizar configuração inicial do sistema.",
					"Configura��o inicial", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void abas_configuracao_inicial(Boolean ativada) {
		tabbedPane.setEnabledAt(0, ativada);
		tabbedPane.setEnabledAt(1, ativada);
		tabbedPane.setEnabledAt(2, ativada);
	}

	// Atalho para simplificar o sistema, não permitindo o controle financeiro,
	// acesso a configurações e tela de backup.
	public void so_orcamentos() {
		InputMap inputMap = rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0), "so_orcamentos_sim");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0), "so_orcamentos_nao");

		rootPane.getActionMap().put("so_orcamentos_sim", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_setores) {
				configuracoes_do_sistema = conf_dao.busca_configuracoes();
				setTitle(configuracoes_do_sistema.getNome_empresa() + "*" + " - " + versao);
				conf_dao.so_orcamentos(true);
				tabbedPane.remove(configuracoes);
				tabbedPane.remove(panel_bkp);
				orcamentos.so_orcamento(true);
			}
		});

		rootPane.getActionMap().put("so_orcamentos_nao", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_setores) {
				configuracoes_do_sistema = conf_dao.busca_configuracoes();
				setTitle(configuracoes_do_sistema.getNome_empresa() + " - " + versao);
				conf_dao.so_orcamentos(false);
				tabbedPane.addTab("Configurações", configuracoes);
				if (faz_bkp) {
					tabbedPane.addTab("Backup", panel_bkp);
				}
				orcamentos.so_orcamento(false);
			}
		});

	}
}
