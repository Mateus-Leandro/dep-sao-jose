package view.frames;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import dao.ClienteDAO;
import dao.ConfiguracaoDAO;
import entities.cliente.Cliente;
import entities.configuracoes.Configuracoes;
import view.panels.Panel_clientes;
import view.panels.Panel_configuracoes;
import view.panels.Panel_orcamento;
import view.panels.Panel_produtos;

public class TelaPrincipal extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Configuracoes configuracoes_do_sistema = new ConfiguracaoDAO().busca_configuracoes();
	private JPanel contentPane;
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private Panel_clientes clientes = new Panel_clientes();
	private Panel_produtos produtos = new Panel_produtos();
	private Panel_orcamento orcamentos = new Panel_orcamento();
	private Panel_configuracoes configuracoes = new Panel_configuracoes(this);

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

		if (configuracoes_do_sistema != null) {
			if (configuracoes_do_sistema.getNome_empresa() != null) {
				setTitle(configuracoes_do_sistema.getNome_empresa());
			}
		}else {
			setTitle("NOME DA EMPRESA");
		}
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
		tabbedPane.addTab("Configurações", configuracoes);

		if (configuracoes_do_sistema == null) {
			ClienteDAO cliente_dao = new ClienteDAO();
			ArrayList<Cliente> clientes_cadastrados = new ArrayList<Cliente>();
			clientes_cadastrados = cliente_dao.listarClientes(clientes_cadastrados, 1);
			// Testa se existe algum cliente cadastrado. Se não existir é cadastrado um
			// consumidor final.
			if (clientes_cadastrados.size() < 1) {
				// Incluindo consumidor final
				Cliente consumidor_final = new Cliente(null, "Consumidor final", null, false, null, null, null, null,
						null, null, null, null, null, "(99)99999-9999", null, false,
						new java.sql.Date(System.currentTimeMillis()));
				clientes.salvar_cliente(consumidor_final);
			}

			desativa_abas_configuracao_inicial();
			JOptionPane.showMessageDialog(null, "Necessário realizar configuração inicial do sistema.",
					"Configuração inicial", JOptionPane.WARNING_MESSAGE);
		}

	}

	public void desativa_abas_configuracao_inicial() {
		tabbedPane.setEnabledAt(0, false);
		tabbedPane.setEnabledAt(1, false);
		tabbedPane.setEnabledAt(2, false);
		muda_aba(3);
	}

	public void ativa_abas_configuracao_inicial() {
		tabbedPane.setEnabledAt(0, true);
		tabbedPane.setEnabledAt(1, true);
		tabbedPane.setEnabledAt(2, true);
	}

	public void muda_aba(int numero_aba) {
		tabbedPane.setSelectedIndex(numero_aba);
	}
}
