package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import com.mysql.cj.x.protobuf.MysqlxNotice.Warning;

import dao.ProdutoDAO;
import dao.SetorDAO;
import entidades.Setor;
import tableModels.ModeloTabelaSetores;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class VariosBarras extends JDialog {

	private JPanel contentPane;
	private JLabel lblCadatroDeProduto;
	private JPanel panel;
	private JLabel lblNomeProduto;
	private JTextField txtNomeProduto;
	private JTextField txtCodigoProduto;
	private JLabel lblCodigoProduto;
	private JLabel lblBarrasPrincipal;
	ArrayList<Setor> setores = new ArrayList<Setor>();
	ModeloTabelaSetores modelo = new ModeloTabelaSetores(setores);
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroSetor frame = new CadastroSetor();
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
	public VariosBarras() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setModal(true);

		lblCadatroDeProduto = new JLabel("Cod. Barras Vinculados");
		lblCadatroDeProduto.setBounds(10, 11, 414, 32);
		lblCadatroDeProduto.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadatroDeProduto.setFont(new Font("Tahoma", Font.BOLD, 20));
		contentPane.add(lblCadatroDeProduto);

		panel = new JPanel();
		panel.setBounds(10, 41, 414, 77);
		panel.setBorder(UIManager.getBorder("PopupMenu.border"));
		contentPane.add(panel);
		panel.setLayout(null);

		lblNomeProduto = new JLabel("Nome");
		lblNomeProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeProduto.setBounds(10, 41, 36, 19);
		lblNomeProduto.setEnabled(false);
		panel.add(lblNomeProduto);

		txtNomeProduto = new JTextField();
		txtNomeProduto.setEnabled(false);
		txtNomeProduto.setBounds(56, 42, 348, 20);
		panel.add(txtNomeProduto);
		txtNomeProduto.setColumns(10);

		txtCodigoProduto = new JTextField();
		txtCodigoProduto.setEnabled(false);
		txtCodigoProduto.setColumns(10);
		txtCodigoProduto.setBounds(56, 11, 50, 20);
		panel.add(txtCodigoProduto);

		lblCodigoProduto = new JLabel("C\u00F3digo");
		lblCodigoProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigoProduto.setBounds(10, 12, 50, 19);
		lblCodigoProduto.setEnabled(false);
		panel.add(lblCodigoProduto);

		lblBarrasPrincipal = new JLabel("Cod. Barras Principal");
		lblBarrasPrincipal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBarrasPrincipal.setEnabled(false);
		lblBarrasPrincipal.setBounds(158, 12, 135, 19);
		panel.add(lblBarrasPrincipal);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setColumns(10);
		textField.setBounds(285, 11, 119, 20);
		panel.add(textField);

	}

}
