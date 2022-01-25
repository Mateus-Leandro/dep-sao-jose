package view.frames;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

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
	private  Configuracoes configuracoes_do_sistema = new ConfiguracaoDAO().busca_configuracoes();
	private JPanel contentPane;
	private JTabbedPane tabbedPane;
	private Panel_clientes clientes = new Panel_clientes();
	private Panel_produtos produtos = new Panel_produtos();
	private Panel_orcamento orcamentos = new Panel_orcamento();
	private Panel_configuracoes configuracoes = new Panel_configuracoes();

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
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
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

	}
}
