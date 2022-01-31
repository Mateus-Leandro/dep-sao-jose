package view.frames;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.metal.MetalTabbedPaneUI.TabbedPaneLayout;

import dao.ConfiguracaoDAO;
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
		setTitle("Dep\u00F3sito S\u00E3o Jos\u00E9");
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
