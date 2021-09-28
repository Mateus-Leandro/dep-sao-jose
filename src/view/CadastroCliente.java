package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.text.ParseException;

import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CadastroCliente extends JFrame {
	private JPanel panel;
	private JLabel lblCodigoCliente;
	private JTextField txtCodigoCliente;
	private JLabel lblNomeCliente;
	private JFormattedTextField fTxtNomeCliente;
	private JLabel lblDocumento;
	private JFormattedTextField fTxtDocumento;
	private JLabel lblApelido;
	private JFormattedTextField fTxtApelido;
	private JCheckBox checkBoxJuridica;
	private MaskFormatter mascara_cpf = null;
	private MaskFormatter mascara_cnpj = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CadastroCliente frame = new CadastroCliente();
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
	public CadastroCliente() {
		setTitle("Clientes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 54, 778, 496);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(10, 11, 742, 237);
		getContentPane().add(panel);
		panel.setBorder(UIManager.getBorder("PopupMenu.border"));
		panel.setLayout(null);

		lblCodigoCliente = new JLabel("C\u00F3digo");
		lblCodigoCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCodigoCliente.setBounds(10, 11, 43, 17);
		panel.add(lblCodigoCliente);

		txtCodigoCliente = new JTextField();
		txtCodigoCliente.setToolTipText("Gerado autom\u00E1ticamente");
		txtCodigoCliente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtCodigoCliente.setEditable(false);
		txtCodigoCliente.setColumns(10);
		txtCodigoCliente.setBounds(59, 11, 64, 20);
		panel.add(txtCodigoCliente);

		lblNomeCliente = new JLabel("Nome");
		lblNomeCliente.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNomeCliente.setBounds(10, 42, 43, 17);
		panel.add(lblNomeCliente);

		MaskFormatter mascara_nome = null;
		try {
			mascara_nome = new MaskFormatter("*********************************************");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fTxtNomeCliente = new JFormattedTextField(mascara_nome);
		fTxtNomeCliente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtNomeCliente.setBounds(53, 39, 344, 20);
		panel.add(fTxtNomeCliente);

		lblDocumento = new JLabel("Documento");
		lblDocumento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDocumento.setBounds(142, 73, 78, 17);
		panel.add(lblDocumento);

	
		try {
			mascara_cpf = new MaskFormatter("###.###.###-##");
			mascara_cnpj = new MaskFormatter("##.###.###/####-##");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		fTxtDocumento = new JFormattedTextField(mascara_cpf);
		fTxtDocumento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtDocumento.setBounds(222, 70, 119, 20);
		panel.add(fTxtDocumento);

		lblApelido = new JLabel("Apelido");
		lblApelido.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblApelido.setBounds(418, 42, 43, 17);
		panel.add(lblApelido);

		fTxtApelido = new JFormattedTextField();
		fTxtApelido.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fTxtApelido.setBounds(467, 39, 265, 20);
		panel.add(fTxtApelido);

		checkBoxJuridica = new JCheckBox("Pessoa Jur\u00EDdica");
		checkBoxJuridica.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent juridico) {
				fTxtDocumento.setValue(null);
				if(juridico.getStateChange() == ItemEvent.SELECTED) {
					fTxtDocumento.setFormatterFactory(new DefaultFormatterFactory(mascara_cnpj));
				}else {
					fTxtDocumento.setFormatterFactory(new DefaultFormatterFactory(mascara_cpf));
				}
			}
		});
		checkBoxJuridica.setFont(new Font("Tahoma", Font.PLAIN, 14));
		checkBoxJuridica.setBounds(10, 70, 126, 23);
		panel.add(checkBoxJuridica);

	}
}
