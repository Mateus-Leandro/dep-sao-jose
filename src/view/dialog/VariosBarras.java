package view.dialog;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

import dao.BarrasDAO;
import entities.Barras_Produto;
import tableModels.ModeloTabelaBarras;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.Color;

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
	private JButton btnNovo;
	private JFormattedTextField fTxtCodigoVinculado;
	private JLabel lblVinculado;
	private JButton btnExcluir;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	BarrasDAO barras_dao = new BarrasDAO();
	ArrayList<Barras_Produto> lista = new ArrayList<Barras_Produto>();
	ModeloTabelaBarras modelo_tabela = new ModeloTabelaBarras(lista);

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
		lista = barras_dao.lista_barras(cod, lista);
		tecla_pressionada();
		alimentaTabela(cod);
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

		tabelaVariosBarras = new JTable(modelo_tabela);

		

			tabelaVariosBarras.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

				public void valueChanged(ListSelectionEvent e) {
					if (tabelaVariosBarras.getSelectedRow() != -1 && btnNovo.isVisible()) {
					barras_selecionado = tabelaVariosBarras.getValueAt(tabelaVariosBarras.getSelectedRow(), 0).toString();
					btnExcluir.setEnabled(true);
					}
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
				salvar_barras();
			}
		});
		btnSalvar.setBounds(198, 139, 104, 23);
		btnSalvar.setIcon(icone_salvar);
		btnSalvar.setVisible(false);
		contentPane.add(btnSalvar);

		btnCancelar = new JButton("Cancelar");
		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickCancelar) {
				cancela_barras();
			}
		});
		btnCancelar.setBounds(312, 139, 112, 23);
		btnCancelar.setIcon(icone_cancelar);
		btnCancelar.setVisible(false);
		contentPane.add(btnCancelar);

		btnNovo = new JButton("Novo");
		btnNovo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNovo) {
				novo_barras();
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
		fTxtCodigoVinculado.setFocusLostBehavior(JFormattedTextField.PERSIST);
		fTxtCodigoVinculado.setBounds(10, 142, 155, 20);
		fTxtCodigoVinculado.setVisible(false);
		contentPane.add(fTxtCodigoVinculado);

		lblVinculado = new JLabel("C\u00F3digo vinculado");
		lblVinculado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblVinculado.setBounds(10, 117, 129, 23);
		lblVinculado.setVisible(false);
		contentPane.add(lblVinculado);

		btnExcluir = new JButton("Excluir");
		btnExcluir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickExcluir) {
				exclui_barras();
			}
		});
		btnExcluir.setEnabled(false);
		btnExcluir.setIcon(icone_excluir);
		btnExcluir.setBounds(312, 139, 112, 23);
		contentPane.add(btnExcluir);

		lblF1 = new JLabel("F1:");
		lblF1.setFont(new Font("Arial", Font.BOLD, 12));
		lblF1.setBounds(283, 275, 21, 14);
		contentPane.add(lblF1);

		lblNovo = new JLabel("Novo");
		lblNovo.setForeground(new Color(34, 139, 34));
		lblNovo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNovo.setBounds(305, 275, 35, 14);
		contentPane.add(lblNovo);

		lblF12 = new JLabel("F12:");
		lblF12.setFont(new Font("Arial", Font.BOLD, 12));
		lblF12.setBounds(350, 275, 23, 14);
		contentPane.add(lblF12);

		lblExcluir = new JLabel("Excluir");
		lblExcluir.setForeground(new Color(255, 0, 0));
		lblExcluir.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblExcluir.setBounds(377, 275, 47, 14);
		contentPane.add(lblExcluir);

		lblEsc = new JLabel("Esc:");
		lblEsc.setFont(new Font("Arial", Font.BOLD, 12));
		lblEsc.setBounds(10, 275, 30, 14);
		contentPane.add(lblEsc);

		lblCancelar = new JLabel("Cancelar");
		lblCancelar.setForeground(Color.GRAY);
		lblCancelar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCancelar.setBounds(41, 275, 53, 14);
		contentPane.add(lblCancelar);

		// Alinhando c�digo de barras para esquerda.
		DefaultTableCellRenderer esquerda = new DefaultTableCellRenderer();
		esquerda.setHorizontalAlignment(SwingConstants.LEFT);
		tabelaVariosBarras.getColumnModel().getColumn(0).setCellRenderer(esquerda);
	}

	// -----------------Fun��es--------------------

	public ArrayList<Barras_Produto> alimentaTabela(String cod_produto) {

		ArrayList<entities.Barras_Produto> lista = new ArrayList<entities.Barras_Produto>();
		BarrasDAO barras_dao = new BarrasDAO();

		lista = barras_dao.lista_barras(cod_produto, lista);

		return lista;
	}

	public void novo_barras() {
		btnNovo.doClick();
		btnNovo.setVisible(false);
		btnSalvar.setVisible(true);
		btnCancelar.setVisible(true);
		fTxtCodigoVinculado.setVisible(true);
		lblVinculado.setVisible(true);
		fTxtCodigoVinculado.requestFocus();
	}

	public void cancela_barras() {
		btnCancelar.doClick();
		btnNovo.setVisible(true);
		btnCancelar.setVisible(false);
		btnSalvar.setVisible(false);
		fTxtCodigoVinculado.setVisible(false);
		lblVinculado.setVisible(false);
	}

	public void exclui_barras() {

		if (tabelaVariosBarras.getSelectedRow() != -1) {
			btnExcluir.doClick();

			if ((boolean) tabelaVariosBarras.getValueAt(tabelaVariosBarras.getSelectedRow(), 2)) {
				JOptionPane.showMessageDialog(null, "N�o � poss�vel realizar a exclus�o do c�digo principal!",
						"C�digo principal.", JOptionPane.WARNING_MESSAGE);
			} else {
				boolean flag;

				int opcao = JOptionPane.showConfirmDialog(null,
						"Deseja excluir c�digo de barras abaixo?\n" + "Barras = " + barras_selecionado,
						"Exclus�o de c�digo de barras", JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE);

				flag = opcao == JOptionPane.YES_OPTION;

				if (flag) {
					int linha_removida = tabelaVariosBarras.getSelectedRow();
					barras_dao.remove_barras(barras_selecionado);
					modelo_tabela.removeBarras(linha_removida);

				}
			}

		}
	}

	public void salvar_barras() {
		btnSalvar.doClick();
		if (!fTxtCodigoVinculado.getText().trim().isEmpty()) {
			String barras = fTxtCodigoVinculado.getText().trim();

			if (barras_dao.novo_barras(txtCodigoProduto.getText().trim(), barras)) {
				modelo_tabela
						.addBarras(new Barras_Produto(false, barras, new java.sql.Date(System.currentTimeMillis())));
				btnSalvar.setVisible(false);
				btnCancelar.setVisible(false);
				btnNovo.setVisible(true);
				fTxtCodigoVinculado.setVisible(false);
				lblVinculado.setVisible(false);
				
			}

		} else {
			JOptionPane.showMessageDialog(null, "Necess�rio informar c�digo de barras!", "C�digo de barras vazio.",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	public void tecla_pressionada() {
		InputMap inputMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "novo");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelar");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "salvar");
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0), "excluir");

		this.getRootPane().setInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW, inputMap);

		this.getRootPane().getActionMap().put("cancelar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_cancelar) {
				cancela_barras();
			}
		});

		this.getRootPane().getActionMap().put("salvar", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_salvar) {
				salvar_barras();
			}
		});

		this.getRootPane().getActionMap().put("novo", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_novo) {
				novo_barras();
			}
		});

		this.getRootPane().getActionMap().put("excluir", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent atalho_excluir) {
				exclui_barras();
			}
		});

	}

	// ------icons---------
	Icon icone_salvar = new ImageIcon(getClass().getResource("/icons/salvar.png"));
	Icon icone_cancelar = new ImageIcon(getClass().getResource("/icons/cancelar.png"));
	Icon icone_mais = new ImageIcon(getClass().getResource("/icons/mais.png"));
	Icon icone_excluir = new ImageIcon(getClass().getResource("/icons/excluir.png"));
	private JLabel lblF1;
	private JLabel lblNovo;
	private JLabel lblF12;
	private JLabel lblExcluir;
	private JLabel lblEsc;
	private JLabel lblCancelar;

}
