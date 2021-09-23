package view.dialog;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JDialog;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

import dao.BarrasDAO;
import entities.Barras_Produto;
import tableModels.ModeloTabelaBarras;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

public class VariosBarras extends JDialog {

	private JPanel contentPane;
	private JLabel lblCadatroDeProduto;
	private JPanel panel;
	private JLabel lblNomeProduto;
	private JTextField txtNomeProduto;
	private JTextField txtCodigoProduto;
	private JLabel lblCodigoProduto;
	private JLabel lblBarrasPrincipal;
	private JTextField txtCodigoBarrasPrincipal;
	private JScrollPane scrollPaneVariosBarras;
	private JTable tabelaVariosBarras;
	private String barras_selecionado = null;
	private JButton btnSalvar;
	private JButton btnCancelar;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VariosBarras frame = new VariosBarras(null, null, null);
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
	public VariosBarras(String cod, String cod_barras_principal, String nome) {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 452, 329);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setModal(true);
		contentPane.setLayout(null);

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
		panel.add(lblNomeProduto);

		txtNomeProduto = new JTextField();
		txtNomeProduto.setEnabled(true);
		txtNomeProduto.setBounds(56, 42, 344, 20);
		txtNomeProduto.setText(nome);
		panel.add(txtNomeProduto);
		txtNomeProduto.setEditable(false);
		txtNomeProduto.setColumns(10);

		txtCodigoProduto = new JTextField();
		txtCodigoProduto.setEnabled(true);
		txtCodigoProduto.setColumns(10);
		txtCodigoProduto.setBounds(56, 11, 50, 20);
		txtCodigoProduto.setText(cod);
		txtCodigoProduto.setEditable(false);
		panel.add(txtCodigoProduto);

		lblCodigoProduto = new JLabel("C\u00F3digo");
		lblCodigoProduto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigoProduto.setBounds(10, 12, 50, 19);
		panel.add(lblCodigoProduto);

		lblBarrasPrincipal = new JLabel("Cod. Barras Principal");
		lblBarrasPrincipal.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBarrasPrincipal.setBounds(160, 12, 135, 19);
		panel.add(lblBarrasPrincipal);
		
		txtCodigoBarrasPrincipal = new JTextField();
		txtCodigoBarrasPrincipal.setEnabled(true);
		txtCodigoBarrasPrincipal.setColumns(10);
		txtCodigoBarrasPrincipal.setBounds(297, 11, 103, 20);
		txtCodigoBarrasPrincipal.setText(cod_barras_principal);
		txtCodigoBarrasPrincipal.setEditable(false);
		panel.add(txtCodigoBarrasPrincipal);
		
		
		ArrayList<Barras_Produto> lista = alimentaTabela(txtCodigoProduto.getText());
		
		ModeloTabelaBarras modelo_tabela = new ModeloTabelaBarras(lista);
		tabelaVariosBarras = new JTable(modelo_tabela);
		tabelaVariosBarras.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickLinhaTabela) {
				BarrasDAO barras_dao = new BarrasDAO();
				barras_selecionado = tabelaVariosBarras.getValueAt(tabelaVariosBarras.getSelectedRow(),0).toString();
				Integer cod = Integer.parseInt(txtCodigoProduto.getText().trim());
			}
		});
		tabelaVariosBarras.setBounds(10, 129, 414, 131);
		tabelaVariosBarras.getTableHeader().setReorderingAllowed(false);
		tabelaVariosBarras.getTableHeader().setResizingAllowed(false);
		tabelaVariosBarras.setAutoCreateRowSorter(true);
		scrollPaneVariosBarras = new JScrollPane(tabelaVariosBarras);
		scrollPaneVariosBarras.setBounds(10, 173, 414, 104);
		contentPane.add(scrollPaneVariosBarras);
		
		btnSalvar = new JButton("Salvar");
		btnSalvar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JOptionPane.showMessageDialog(null, "Oii Saulo meu amor, aqui é a marlene!");
			}});
		btnSalvar.setBounds(215, 139, 104, 23);
		btnSalvar.setIcon(icone_salvar);
		btnSalvar.setVisible(false);
		contentPane.add(btnSalvar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(329, 139, 95, 23);
		btnCancelar.setIcon(icone_cancelar);
		btnCancelar.setVisible(false);
		contentPane.add(btnCancelar);
		
		btnNovo = new JButton("Novo");
		btnNovo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNovo) {
				btnNovo.setVisible(false);
				btnSalvar.setVisible(true);
				btnCancelar.setVisible(true);
				fTxtCodigoVinculado.setVisible(true);
				lblVinculado.setVisible(true);
			}
		});
		btnNovo.setBounds(10, 129, 104, 23);
		btnNovo.setIcon(icone_mais);
		contentPane.add(btnNovo);
		
		MaskFormatter mascara_barras = null;
		try {
			mascara_barras = new MaskFormatter("##############");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fTxtCodigoVinculado = new JFormattedTextField(mascara_barras);
		fTxtCodigoVinculado.setBounds(10, 142, 155, 20);
		fTxtCodigoVinculado.setVisible(false);
		contentPane.add(fTxtCodigoVinculado);
		
		lblVinculado = new JLabel("C\u00F3digo vinculado");
		lblVinculado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblVinculado.setBounds(10, 117, 129, 23);
		lblVinculado.setVisible(false);
		contentPane.add(lblVinculado);
		
		
		//Alinhando código de barras para esquerda.
		DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
		esquerda.setHorizontalAlignment(SwingConstants.LEFT);
		tabelaVariosBarras.getColumnModel().getColumn(0).setCellRenderer(esquerda);		
	}

	

	//-------Funções--------
	public ArrayList<Barras_Produto> alimentaTabela(String cod_produto) {
		
		ArrayList<entities.Barras_Produto> lista = new ArrayList<entities.Barras_Produto>();
		BarrasDAO barras_dao = new BarrasDAO();
		
		lista = barras_dao.lista_barras(cod_produto, lista);
		
		return lista;
	}
	
	
	
	// ------icons---------
		Icon icone_salvar = new ImageIcon(getClass().getResource("/icons/salvar.png"));
		Icon icone_cancelar = new ImageIcon(getClass().getResource("/icons/cancelar.png"));
		Icon icone_mais = new ImageIcon(getClass().getResource("/icons/mais.png"));
		private JButton btnNovo;
		private JFormattedTextField fTxtCodigoVinculado;
		private JLabel lblVinculado;
}
