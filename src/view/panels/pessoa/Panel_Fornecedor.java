package view.panels.pessoa;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class Panel_Fornecedor extends Panel_pessoa {
	private JLabel lblCadastroDeFornecedor;
	private JComboBox<String> cbxTipoPesquisa;
	private JCheckBox checkBoxBloqueadoPedido;
	private JCheckBox checkBoxBloqueadoCotacao;
	private JLabel lblFornecedoresCadastrados;
	private JScrollPane scrollPane;

	public Panel_Fornecedor() {
		separador_3.setBounds(506, 437, 202, 9);
		separador_2.setBounds(15, 437, 208, 9);

		lblCadastroDeFornecedor = new JLabel("Cadastro de Fornecedores");
		lblCadastroDeFornecedor.setHorizontalAlignment(SwingConstants.CENTER);
		lblCadastroDeFornecedor.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblCadastroDeFornecedor.setBounds(211, 11, 324, 29);
		add(lblCadastroDeFornecedor);

		cbxTipoPesquisa = new JComboBox<String>();
		cbxTipoPesquisa.setModel(new DefaultComboBoxModel(new String[] {"Nome", "Nome Fant.", "Cod."}));
		cbxTipoPesquisa.setSelectedIndex(0);
		cbxTipoPesquisa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbxTipoPesquisa.setBounds(105, 453, 96, 25);
		add(cbxTipoPesquisa);

		checkBoxBloqueadoPedido = new JCheckBox("Bloqueado p/ pedido");
		checkBoxBloqueadoPedido.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent clickBloqueadoPedido) {
				if (checkBoxBloqueadoPedido.isSelected()) {
					checkBoxBloqueadoPedido.setForeground(Color.red);

					checkBoxBloqueadoCotacao.setSelected(true);
					checkBoxBloqueadoCotacao.setEnabled(false);
				} else {
					checkBoxBloqueadoPedido.setForeground(Color.black);
					if(!btnNovo.isEnabled()) {
						checkBoxBloqueadoCotacao.setEnabled(true);
					}
				}
			}
		});
		checkBoxBloqueadoPedido.setFont(new Font("Tahoma", Font.PLAIN, 16));
		checkBoxBloqueadoPedido.setEnabled(false);
		checkBoxBloqueadoPedido.setBounds(318, 73, 183, 23);
		add(checkBoxBloqueadoPedido);

		checkBoxBloqueadoCotacao = new JCheckBox("Bloqueado p/ cota\u00E7\u00E3o");
		checkBoxBloqueadoCotacao.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent clickBloqueadoCotacao) {
				if (checkBoxBloqueadoCotacao.isSelected()) {
					checkBoxBloqueadoCotacao.setForeground(Color.red);
				} else {
					checkBoxBloqueadoCotacao.setForeground(Color.black);
				}
			}
		});
		checkBoxBloqueadoCotacao.setFont(new Font("Tahoma", Font.PLAIN, 16));
		checkBoxBloqueadoCotacao.setEnabled(false);
		checkBoxBloqueadoCotacao.setBounds(526, 73, 183, 23);
		add(checkBoxBloqueadoCotacao);
		
		lblFornecedoresCadastrados = new JLabel("Fornecedores Cadastrados");
		lblFornecedoresCadastrados.setHorizontalAlignment(SwingConstants.CENTER);
		lblFornecedoresCadastrados.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblFornecedoresCadastrados.setBounds(227, 422, 274, 29);
		add(lblFornecedoresCadastrados);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 487, 693, 131);
		add(scrollPane);

		btnNovo.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNovoCliente) {
				nova_pessoa(tabela);
				checkBoxBloqueadoPedido.setEnabled(true);
				checkBoxBloqueadoCotacao.setEnabled(true);
			}
		});

		btnCancelar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNovoCliente) {
				cancelar_pessoa();
				checkBoxBloqueadoPedido.setEnabled(false);
				checkBoxBloqueadoCotacao.setEnabled(false);
				
				checkBoxBloqueadoCotacao.setSelected(false);
				checkBoxBloqueadoPedido.setSelected(false);
			}
		});

		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent clickNovoCliente) {
				editar_pessoa();
				checkBoxBloqueadoPedido.setEnabled(true);
				checkBoxBloqueadoCotacao.setEnabled(true);
			}
		});

	}
}
