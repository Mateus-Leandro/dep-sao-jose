package view.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import bkp.BkpBanco;
import icons.Icones;

public class Panel_bkp extends JPanel {
	private JLabel lblBackupSistema;
	private JSeparator backup_sistema;
	private JLabel lblDiretorioDestino;
	private JTextField txtPastaPadraoBkp;
	private JLabel lblDataUltimoBackup;
	private JTextField txtDataUltimoBackup;
	private JLabel lblInformacoesBasicas;
	private JSeparator separador_infoBasicas;
	private JLabel lblTempoGasto;
	private JTextField txtTempoGasto;
	private Icones icones = new Icones();
	private JLabel lblNovoBackup;
	private JSeparator separador_infoBasicas_1;
	private JButton btnRealizarBackup;
	private JLabel lblTamanhoAtualBanco;
	private JTextField txtTamanhoBanco;
	private File caminho_banco = new File("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Data\\banco_deposito");
	private JLabel lblTamanhoAproximado;
	private BkpBanco bkp_banco = new BkpBanco();
	private Properties props_bkp = bkp_banco.le_arquivo_conf_bkp();
	private JLabel lblPastaDestino;
	private JTextField txtDestinoBkp;
	private JButton btnPastaDestino;
	private JLabel lblInformacaoBkp;
	private JPanel panelValorTotal;
	private JLabel lblAtencao;
	private JTextPane txtpnARealizaoDo;
	private JSeparator separator;
	private JButton btnLimpaPastaSelecionada;

	/**
	 * Create the panel.
	 */
	public Panel_bkp() {
		setLayout(null);
		lblBackupSistema = new JLabel("Configura\u00E7\u00F5es de Backup do Sistema");
		lblBackupSistema.setHorizontalAlignment(SwingConstants.CENTER);
		lblBackupSistema.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblBackupSistema.setBounds(143, 11, 439, 29);
		add(lblBackupSistema);

		backup_sistema = new JSeparator();
		backup_sistema.setBounds(10, 50, 702, 9);
		add(backup_sistema);

		lblDiretorioDestino = new JLabel("Pasta padrão para o backup");
		lblDiretorioDestino.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDiretorioDestino.setBounds(10, 107, 175, 19);
		add(lblDiretorioDestino);

		txtPastaPadraoBkp = new JTextField();
		txtPastaPadraoBkp.setEditable(false);
		txtPastaPadraoBkp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPastaPadraoBkp.setBounds(185, 104, 527, 23);
		txtPastaPadraoBkp.setColumns(10);
		txtPastaPadraoBkp.setText("C:\\dep\\backups\\");
		add(txtPastaPadraoBkp);

		lblDataUltimoBackup = new JLabel("Data do \u00FAltimo backup");
		lblDataUltimoBackup.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDataUltimoBackup.setBounds(10, 143, 144, 19);
		add(lblDataUltimoBackup);

		txtDataUltimoBackup = new JTextField();
		txtDataUltimoBackup.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDataUltimoBackup.setEditable(false);
		txtDataUltimoBackup.setBounds(155, 142, 96, 20);
		add(txtDataUltimoBackup);
		txtDataUltimoBackup.setColumns(10);

		lblInformacoesBasicas = new JLabel("Informa\u00E7\u00F5es B\u00E1sicas");
		lblInformacoesBasicas.setHorizontalAlignment(SwingConstants.CENTER);
		lblInformacoesBasicas.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblInformacoesBasicas.setBounds(10, 70, 188, 29);
		add(lblInformacoesBasicas);

		separador_infoBasicas = new JSeparator();
		separador_infoBasicas.setBounds(202, 87, 510, 9);
		add(separador_infoBasicas);

		lblTempoGasto = new JLabel("Tempo gasto no \u00FAltimo backup");
		lblTempoGasto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTempoGasto.setBounds(10, 205, 196, 19);
		add(lblTempoGasto);

		txtTempoGasto = new JTextField();
		txtTempoGasto.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtTempoGasto.setEditable(false);
		txtTempoGasto.setBounds(216, 204, 107, 20);
		add(txtTempoGasto);
		txtTempoGasto.setColumns(10);

		lblNovoBackup = new JLabel("Novo Backup");
		lblNovoBackup.setHorizontalAlignment(SwingConstants.CENTER);
		lblNovoBackup.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNovoBackup.setBounds(10, 235, 124, 29);
		add(lblNovoBackup);

		separador_infoBasicas_1 = new JSeparator();
		separador_infoBasicas_1.setBounds(135, 252, 577, 9);
		add(separador_infoBasicas_1);

		btnRealizarBackup = new JButton("Realizar backup");
		btnRealizarBackup.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickFazBackup) {
				if (btnRealizarBackup.isEnabled()) {
					if (bkp_banco.realiza_backup(txtDestinoBkp.getText())) {
						JOptionPane.showMessageDialog(null, "Backup realizado com sucesso!!!",
								"Backup do sistema.", JOptionPane.NO_OPTION);
					} else {
						JOptionPane.showMessageDialog(null,
								"Não foi possível realizar o backup do sistema!\nVerifique as configurações presentes no arquivo C:\\dep\\conf\\bkp.properties",
								"Backup do sistema.", JOptionPane.WARNING_MESSAGE);
					}
				}
			}
		});
		btnRealizarBackup.setEnabled(false);
		btnRealizarBackup.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRealizarBackup.setBounds(531, 337, 181, 29);
		btnRealizarBackup.setIcon(icones.getIcone_backup());
		add(btnRealizarBackup);

		lblTamanhoAtualBanco = new JLabel("Tamanho do banco de dados");
		lblTamanhoAtualBanco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTamanhoAtualBanco.setBounds(10, 174, 188, 19);
		add(lblTamanhoAtualBanco);

		txtTamanhoBanco = new JTextField();
		txtTamanhoBanco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtTamanhoBanco.setEditable(false);
		txtTamanhoBanco.setColumns(10);
		txtTamanhoBanco.setBounds(203, 173, 154, 20);
		add(txtTamanhoBanco);

		long tamanho = (long) (pega_tamanho_banco() / 1048576.0);
		txtTamanhoBanco.setText(Long.toString(tamanho) + " Megabytes");

		lblTamanhoAproximado = new JLabel("* Tamanho aproximado.");
		lblTamanhoAproximado.setForeground(Color.BLUE);
		lblTamanhoAproximado.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTamanhoAproximado.setBounds(367, 175, 135, 19);
		add(lblTamanhoAproximado);

		lblPastaDestino = new JLabel("Pasta de destino");
		lblPastaDestino.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPastaDestino.setBounds(10, 305, 109, 19);
		add(lblPastaDestino);

		txtDestinoBkp = new JTextField();
		txtDestinoBkp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDestinoBkp.setEditable(false);
		txtDestinoBkp.setColumns(10);
		txtDestinoBkp.setBounds(185, 306, 527, 20);
		add(txtDestinoBkp);

		btnPastaDestino = new JButton();
		btnPastaDestino.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickPastaDestino) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.setVisible(true);

				int selecionou = jfc.showOpenDialog(Panel_bkp.this);

				if (selecionou == JFileChooser.APPROVE_OPTION) {
					txtDestinoBkp.setText(jfc.getSelectedFile().toString());
					btnLimpaPastaSelecionada.setEnabled(true);
					btnRealizarBackup.setEnabled(true);
				}
			}
		});
		btnPastaDestino.setBounds(116, 305, 35, 22);
		btnPastaDestino.setIcon(icones.getIcone_pasta());
		add(btnPastaDestino);

		lblInformacaoBkp = new JLabel(
				"* Para realizar um novo Backup do sistema, selecione a pasta de destino e click em \"Realizar backup\".");
		lblInformacaoBkp.setForeground(Color.BLUE);
		lblInformacaoBkp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInformacaoBkp.setBounds(10, 275, 631, 19);
		add(lblInformacaoBkp);

		panelValorTotal = new JPanel();
		panelValorTotal.setLayout(null);
		panelValorTotal.setBorder(UIManager.getBorder("DesktopIcon.border"));
		panelValorTotal.setBounds(10, 527, 702, 125);
		add(panelValorTotal);

		lblAtencao = new JLabel("Aten\u00E7\u00E3o");
		lblAtencao.setForeground(Color.RED);
		lblAtencao.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblAtencao.setBounds(10, 11, 91, 28);
		panelValorTotal.add(lblAtencao);

		txtpnARealizaoDo = new JTextPane();
		txtpnARealizaoDo.setBackground(UIManager.getColor("CheckBox.light"));
		txtpnARealizaoDo.setEditable(false);
		txtpnARealizaoDo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		txtpnARealizaoDo.setText(
				"A realiza\u00E7\u00E3o do backup pode demorar um longo tempo caso houver um grande volume de dados. Ap\u00F3s iniciar o backup n\u00E3o ser\u00E1 poss\u00EDvel cancelar a opera\u00E7\u00E3o.");
		txtpnARealizaoDo.setBounds(10, 56, 685, 58);
		panelValorTotal.add(txtpnARealizaoDo);

		separator = new JSeparator();
		separator.setBounds(10, 43, 685, 2);
		panelValorTotal.add(separator);

		btnLimpaPastaSelecionada = new JButton();
		btnLimpaPastaSelecionada.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickLimpaPastaSelecionada) {
				if (btnLimpaPastaSelecionada.isEnabled()) {
					txtDestinoBkp.setText(null);
					btnRealizarBackup.setEnabled(false);
				}
			}
		});
		btnLimpaPastaSelecionada.setToolTipText("Limpar item escolhido.");
		btnLimpaPastaSelecionada.setEnabled(false);
		btnLimpaPastaSelecionada.setBounds(155, 305, 27, 21);
		btnLimpaPastaSelecionada.setIcon(icones.getIcone_limpar());
		add(btnLimpaPastaSelecionada);
	}

	public long pega_tamanho_banco() {
		long tamanho;
		try {
			tamanho = Files.walk(caminho_banco.toPath()).map(f -> f.toFile()).filter(f -> f.isFile())
					.mapToLong(f -> f.length()).sum();
		} catch (IOException e) {
			e.printStackTrace();
			return 0;
		}
		return tamanho;
	}

	public String pega_caminho_padrao_bkp() {
		String caminho_padrao = props_bkp.getProperty("destino_bkp");
		return caminho_padrao;
	}
}
