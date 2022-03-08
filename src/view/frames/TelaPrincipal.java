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
import dao.ClienteDAO;
import dao.ConfiguracaoDAO;
import entities.cliente.Cliente;
import entities.configuracoes.Configuracoes;
import view.panels.Panel_bkp;
import view.panels.Panel_clientes;
import view.panels.Panel_configuracoes;
import view.panels.Panel_orcamento;
import view.panels.Panel_produtos;

public class TelaPrincipal extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ConfiguracaoDAO conf_dao = new ConfiguracaoDAO();
	private Configuracoes configuracoes_do_sistema = conf_dao.busca_configuracoes();
	private JPanel contentPane;
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private Panel_clientes clientes = new Panel_clientes();
	private Panel_produtos produtos = new Panel_produtos();
	private Panel_orcamento orcamentos = new Panel_orcamento();
	private Panel_configuracoes configuracoes = new Panel_configuracoes(this);
	private Panel_bkp panel_bkp = new Panel_bkp();
	private BkpBanco bkp_banco = new BkpBanco();
	private Boolean faz_bkp = bkp_banco.faz_bkp();
	private Boolean faz_bkp_diario = bkp_banco.faz_bkp_diario();
	public String versao = "versão 07.03.22";  
	
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
		if(faz_bkp_diario) {
			if(bkp_banco.bkp_diario()) {
				JOptionPane.showMessageDialog(null, "Backup diário realizado." , "Backup diário.",JOptionPane.NO_OPTION);
				panel_bkp.le_log();
			}
		}
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icons/ferramentas.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 759, 734);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		tabbedPane.setBorder(UIManager.getBorder("CheckBoxMenuItem.border"));
		tabbedPane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tabbedPane.setBounds(5, 5, 733, 687);
		contentPane.add(tabbedPane);
		setLocationRelativeTo(null);

		clientes.setVisible(true);
		produtos.setVisible(true);
		orcamentos.setVisible(true);

		tabbedPane.addTab("Clientes", clientes);
		tabbedPane.addTab("Produtos", produtos);
		tabbedPane.addTab("Orçamentos", orcamentos);

		if (!configuracoes_do_sistema.getSo_orcamento()) {
			tabbedPane.addTab("Configurações", configuracoes);
			// Testa se a máquina que o sistema está executando faz backup.
			if (faz_bkp) {
				tabbedPane.addTab("Backup", panel_bkp);
			}
		}
		
		// Verifica se existe configuração e se existe nome para a empresa.
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
			// Testa se existe algum cliente cadastrado. Se não existir é cadastrado um
			// consumidor final.
			if (clientes_cadastrados.size() < 1) {
				// Incluindo consumidor final
				Cliente consumidor_final = new Cliente(null, "Consumidor final", null, false, null, null, null, null,
						null, null, null, null, null, "(99)99999-9999", null, false,
						new java.sql.Date(System.currentTimeMillis()));
				clientes.salvar_cliente(consumidor_final);
			}

			abas_configuracao_inicial(false);
			JOptionPane.showMessageDialog(null, "Necessário realizar configuração inicial do sistema.",
					"Configuração inicial", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void abas_configuracao_inicial(Boolean ativada) {
		tabbedPane.setEnabledAt(0, ativada);
		tabbedPane.setEnabledAt(1, ativada);
		tabbedPane.setEnabledAt(2, ativada);
	}

	public void muda_aba(int numero_aba) {
		tabbedPane.setSelectedIndex(numero_aba);
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
				setTitle(configuracoes_do_sistema.getNome_empresa()  + " - " + versao);
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
