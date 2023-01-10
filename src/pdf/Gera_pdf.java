package pdf;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.ServiceUI;
import javax.print.SimpleDoc;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaSizeName;
import javax.swing.JOptionPane;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dao.configuracoes.ConfiguracaoDAO;
import entities.configuracoes.Configuracoes;
import entities.orcamento.Orcamento;
import entities.produto.Produto_orcamento;

public class Gera_pdf {

	private ConfiguracaoDAO conf_dao = new ConfiguracaoDAO();
	private Configuracoes configuracoes_do_sistema = conf_dao.busca_configuracoes();
	private Document documento;
	private Boolean orcamento_nao_salvo;

	// tabela título
	private float[] largura_colunas_titulo = { 18f, 25f };
	private PdfPTable tabela_titulo = new PdfPTable(largura_colunas_titulo);
	private PdfPCell cel_via_cliente;
	private PdfPCell cel_titulo;

	// tabela cabeçalho
	private float[] largura_colunas_cabecalho1 = { 23f, 25f, 10f };
	private PdfPTable tabela_cabecalho1 = new PdfPTable(largura_colunas_cabecalho1);
	private PdfPCell cel_data_hora;
	private PdfPCell cel_nome_empresa;
	private PdfPCell cel_celular;

	// tabela cabeçalho
	private float[] largura_colunas_cabecalho2 = { 56f, 18f };
	private PdfPTable tabela_cabecalho2 = new PdfPTable(largura_colunas_cabecalho2);
	private PdfPCell cel_orcamento;
	private PdfPCell cel_data_criacao;
	private PdfPCell cel_cliente;
	private PdfPCell celular_cliente;
	private PdfPCell cel_endereco;
	private PdfPCell cel_quantidade_produtos;

	// Tabela de produtos
	private float[] largura_colunas_produtos = { 6.0f, 24f, 5f, 10f, 10f, 10f, 10f };
	private PdfPTable tabela_produtos = new PdfPTable(largura_colunas_produtos);
	private PdfPCell cel_codigo;
	private PdfPCell cel_nome;
	private PdfPCell cel_fator;
	private PdfPCell cel_quantidade;
	private PdfPCell cel_preco_unit;
	private PdfPCell cel_desc_unit;
	private PdfPCell cel_valor;

	// Tabela totais
	private float[] largura_colunas_totais = { 14f, 20f, 18f, 18f };
	private PdfPTable tabela_totais = new PdfPTable(largura_colunas_totais);
	private PdfPCell cel_frete;
	private PdfPCell cel_desc_prd;
	private PdfPCell cel_desc_orc;
	private PdfPCell cel_vlr_tot;

	// Tabela assinatura/vencimento
	private float[] largura_colunas_assinatura = { 90f, 30f };
	private PdfPTable tabela_assinatura;
	private PdfPCell cel_assinatura;
	private PdfPCell cel_vencimento;

	// Celula em branco
	private PdfPCell cel_em_branco = new PdfPCell();

	// Fontes
	private static Font fonteCabecalho = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
	private static Font fontePadraoPequena = new Font(Font.FontFamily.HELVETICA, 10);
	private static Font fontePadraoMedia = new Font(Font.FontFamily.HELVETICA, 11);
	private static Font fontePadraoMediaVermelha = new Font(Font.FontFamily.HELVETICA, 11, 0, BaseColor.RED);
	private static Font negritoPequena = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
	private static Font negritoPequenaVermelha = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.RED);

	// Formatação
	private SimpleDateFormat formata_data = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat formata_hora = new SimpleDateFormat("HH:mm:ss");
	private NumberFormat nf = new DecimalFormat(",##0.00");
	private NumberFormat nf2 = new DecimalFormat("R$ ,##0.00");

	private String via;

	public Gera_pdf() {
	}

	public void monta_pdf_orcamento(Orcamento orcamento) {
		try {
			documento = new Document();
			cria_pastas();

			// Teste para verificar se está sendo impresso um orçamento sem salvar (Sem Id)
			orcamento_nao_salvo = orcamento.getId_orcamento() == null;
			File arquivo;
			if (!orcamento_nao_salvo) {
				arquivo = new File("C:/dep/pdf/Orçamento_" + orcamento.getId_orcamento().toString() + ".pdf");
			} else {
				String home_usuario = System.getProperty("user.home");
				arquivo = new File(home_usuario + "/Desktop/" + "Orçamento.pdf");
			}

			PdfWriter.getInstance(documento, new FileOutputStream(arquivo));
			documento.open();

			for (int n = 0; n < 2; n++) {

				if (n == 1) {
					via = "via cliente";
				} else {
					via = "via loja";
				}
				monta_cabecalho_orcamento(orcamento);
				imprime_produtos(orcamento);
				monta_rodape(orcamento);

				if (orcamento.getProdutos_do_orcamento().size() > 11) {
					documento.newPage();
				}
			}

			documento.close();
//			imprimir(arquivo.getAbsolutePath());
			Desktop.getDesktop().open(arquivo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cria_pastas() {
		File pasta_pdf = new File("C:/dep/pdf");
		if (!pasta_pdf.exists()) {
			pasta_pdf.mkdirs();
		}
	}

	public void monta_cabecalho_orcamento(Orcamento orcamento) {
		try {
			tabela_titulo = new PdfPTable(largura_colunas_titulo);

			Paragraph p = new Paragraph();
			p.setFont(negritoPequena);
			p.add("-" + via + "-");
			cel_via_cliente = new PdfPCell(p);

			p = new Paragraph();
			p.setFont(fonteCabecalho);
			p.add("ORÇAMENTO");
			cel_titulo = new PdfPCell(p);

			linha_invisivel_tabela_titulo();

			tabela_titulo.addCell(cel_via_cliente);
			tabela_titulo.addCell(cel_titulo);
			tabela_titulo.setHorizontalAlignment(Element.ALIGN_LEFT);
			tabela_titulo.setWidthPercentage(100);
			documento.add(tabela_titulo);

			p = new Paragraph();
			p.add(gera_string(74, "="));
			documento.add(p);

			tabela_cabecalho1 = new PdfPTable(largura_colunas_cabecalho1);
			p = new Paragraph(formata_data.format(new Date()) + " - " + formata_hora.format(new Date()));
			cel_data_hora = new PdfPCell(p);

			p = new Paragraph(String.format("%-35.35s", configuracoes_do_sistema.getNome_empresa()));
			cel_nome_empresa = new PdfPCell(p);

			p = new Paragraph(configuracoes_do_sistema.getCelular());
			cel_celular = new PdfPCell(p);

			linha_invisivel_tabela_cabecalho1();

			tabela_cabecalho1.addCell(cel_data_hora);
			tabela_cabecalho1.addCell(cel_nome_empresa);
			tabela_cabecalho1.addCell(cel_celular);

			tabela_cabecalho1.setHorizontalAlignment(Element.ALIGN_LEFT);
			tabela_cabecalho1.setWidthPercentage(100);
			documento.add(tabela_cabecalho1);

			p = new Paragraph();
			p.add(gera_string(74, "="));
			documento.add(p);

			tabela_cabecalho2 = new PdfPTable(largura_colunas_cabecalho2);
			p = new Paragraph();
			String numero_orcamento;
			if (!orcamento_nao_salvo) {
				numero_orcamento = "Orçamento Nº: " + String.format("%06d", orcamento.getId_orcamento());
				p.add(numero_orcamento);
			} else {
				numero_orcamento = "Informações do cliente";
				p.setFont(new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD));
				p.add(numero_orcamento);
			}
			cel_orcamento = new PdfPCell(p);

			p = new Paragraph("");
			if (!orcamento_nao_salvo) {
				p.add("Criado em: " + formata_data.format(orcamento.getData_inclusao()));
			}
			cel_data_criacao = new PdfPCell(p);

			String apelido = "";
			if (orcamento.getCliente().getApelido() != null) {
				apelido = " - (" + orcamento.getCliente().getApelido() + ")";
			}

			p = new Paragraph("Cliente: " + String.format("%-50.50s", orcamento.getCliente().getNome() + apelido));
			p.setFont(fontePadraoMedia);
			cel_cliente = new PdfPCell(p);

			p = new Paragraph("Cel: " + orcamento.getCliente().getCelular());
			p.setFont(fontePadraoMedia);
			celular_cliente = new PdfPCell(p);

			String rua = "";
			String numero = "";
			String bairro = "";
			String cidade = "";

			if (orcamento.getCliente().getEndereco() != null) {
				rua = orcamento.getCliente().getEndereco();
			}

			if (orcamento.getCliente().getNumero() != null) {
				numero = ", Nº " + orcamento.getCliente().getNumero();
			}

			if (orcamento.getCliente().getBairro() != null) {
				bairro = ", " + orcamento.getCliente().getBairro();
			}
			if (orcamento.getCliente().getCidade() != null) {
				cidade = " - " + orcamento.getCliente().getCidade();
			}

			p = new Paragraph("Endereço: "
					+ String.format("%-52.52s", String.format("%-28.28s", rua) + numero + bairro + cidade));
			p.setFont(fontePadraoMedia);
			cel_endereco = new PdfPCell(p);

			p = new Paragraph("Qtd. Produtos: " + orcamento.getProdutos_do_orcamento().size());
			p.setFont(fontePadraoMedia);
			cel_quantidade_produtos = new PdfPCell(p);

			linha_invisivel_tabela_cabecalho2();

			tabela_cabecalho2.addCell(cel_orcamento);
			tabela_cabecalho2.addCell(cel_data_criacao);
			tabela_cabecalho2.addCell(cel_cliente);

			tabela_cabecalho2.addCell(celular_cliente);
			tabela_cabecalho2.addCell(cel_endereco);
			tabela_cabecalho2.addCell(cel_quantidade_produtos);

			tabela_cabecalho2.setHorizontalAlignment(Element.ALIGN_LEFT);
			tabela_cabecalho2.setWidthPercentage(100);
			documento.add(tabela_cabecalho2);

			p = new Paragraph();
			p.add(gera_string(130, "-"));
			documento.add(p);

			p = new Paragraph();
			p.setLeading(10);
			p.setFont(new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD));
			p.add("CÓD." + gera_string(7, " ") + "DESCRIÇÃO" + gera_string(38, " ") + "UN" + gera_string(8, " ") + "QTD"
					+ gera_string(17, " ") + "PR.UNIT." + gera_string(10, " ") + "DESC.TOT." + gera_string(6, " ")
					+ "PR.TOTAL");
			documento.add(p);

			p = new Paragraph();
			p.setLeading(10);
			p.add(gera_string(130, "-"));
			documento.add(p);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public void imprime_produtos(Orcamento orcamento) {
		nf.setRoundingMode(RoundingMode.DOWN);

		Paragraph p;
		tabela_produtos = new PdfPTable(largura_colunas_produtos);

		Boolean linha_negrito = false;
		for (Produto_orcamento produto : orcamento.getProdutos_do_orcamento()) {

			if (linha_negrito) {
				fontePadraoPequena = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
				linha_negrito = false;
			} else {
				fontePadraoPequena = new Font(Font.FontFamily.HELVETICA, 10);
				linha_negrito = true;
			}

			p = new Paragraph();
			p.setFont(fontePadraoPequena);
			p.add(produto.getIdProduto().toString());
			cel_codigo = new PdfPCell(p);

			p = new Paragraph();
			p.setFont(fontePadraoPequena);
			p.add(String.format("%-28.28s", produto.getDescricao()));
			cel_nome = new PdfPCell(p);

			p = new Paragraph();
			p.setFont(fontePadraoPequena);
			p.add(produto.getUnidadeVenda());
			cel_fator = new PdfPCell(p);

			p = new Paragraph();
			p.setFont(fontePadraoPequena);
			p.add(nf.format(produto.getQuantidade()));
			cel_quantidade = new PdfPCell(p);

			p = new Paragraph();
			p.setFont(fontePadraoPequena);
			p.add(nf2.format(produto.getPreco_unitario()));
			cel_preco_unit = new PdfPCell(p);

			p = new Paragraph();
			p.setFont(fontePadraoPequena);
			p.add(nf2.format(produto.getValor_desconto()));
			cel_desc_unit = new PdfPCell(p);

			p = new Paragraph();
			p.setFont(fontePadraoPequena);
			p.add(nf2.format(produto.getValor_total() - produto.getValor_desconto()));
			cel_valor = new PdfPCell(p);

			linha_invisivel_tabela_produtos();

			tabela_produtos.addCell(cel_codigo);
			tabela_produtos.addCell(cel_nome);
			tabela_produtos.addCell(cel_fator);
			tabela_produtos.addCell(cel_quantidade);
			tabela_produtos.addCell(cel_preco_unit);
			tabela_produtos.addCell(cel_desc_unit);
			tabela_produtos.addCell(cel_valor);

		}
		try {
			tabela_produtos.setHorizontalAlignment(Element.ALIGN_LEFT);
			tabela_produtos.setWidthPercentage(100);

			int linhas = 0;
			if (orcamento.getProdutos_do_orcamento().size() < 12) {
				if (orcamento.getProdutos_do_orcamento().size() == 1) {
					linhas = 155;
				} else {
					linhas = 158;
				}
			} else if (orcamento.getProdutos_do_orcamento().size() < 40) {
				linhas = 548;
			} else {
				linhas = 1310;
			}
			tabela_produtos.setSpacingAfter(linhas - (orcamento.getProdutos_do_orcamento().size() * 14));

			documento.add(tabela_produtos);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public void monta_rodape(Orcamento orcamento) {
		Paragraph p = new Paragraph();
		Double desconto_orcamento = 0.00;
		Double frete = 0.00;

		if (orcamento.getFrete() != null) {
			frete = orcamento.getFrete();
		}

		if (orcamento.getDesconto_final() != null) {
			desconto_orcamento = orcamento.getDesconto_final();
		}

		try {
			p.add(gera_string(130, "-"));
			documento.add(p);

			tabela_totais = new PdfPTable(largura_colunas_totais);

			p = new Paragraph();
			p.add("Frete: " + nf2.format(frete));
			cel_frete = new PdfPCell(p);

			p = new Paragraph();
			p.add("Desc. Orç.: " + nf2.format(desconto_orcamento));
			cel_desc_orc = new PdfPCell(p);

			p = new Paragraph();
			p.add("Desc. Prd.: "
					+ nf2.format(orcamento.getTotal_mercadorias_bruto() - orcamento.getTotal_mercadorias_liquido()));
			cel_desc_prd = new PdfPCell(p);

			p = new Paragraph();
			p.add("Total: " + nf2.format(orcamento.getValor_total()));
			cel_vlr_tot = new PdfPCell(p);

			linha_invisivel_tabela_totais();
			tabela_totais.addCell(cel_frete);
			tabela_totais.addCell(cel_desc_orc);
			tabela_totais.addCell(cel_desc_prd);
			tabela_totais.addCell(cel_vlr_tot);
			tabela_totais.setHorizontalAlignment(Element.ALIGN_LEFT);
			tabela_totais.setWidthPercentage(100);

			documento.add(tabela_totais);

			p = new Paragraph();
			p.add(gera_string(130, "-"));
			p.setLeading(10);
			documento.add(p);

			tabela_assinatura = new PdfPTable(largura_colunas_assinatura);
			p = new Paragraph();
			p.setFont(fontePadraoMedia);
			p.add("Assinatura do cliente:" + gera_string(38, "_"));

			cel_assinatura = new PdfPCell(p);

			p = new Paragraph();
			p.setFont(fontePadraoMediaVermelha);
			p.add("Vencimento:___/___/___");
			cel_vencimento = new PdfPCell(p);

			linha_invisivel_tabela_assinatura();
			tabela_assinatura.addCell(cel_assinatura);
			tabela_assinatura.addCell(cel_vencimento);
			tabela_assinatura.setHorizontalAlignment(Element.ALIGN_LEFT);
			tabela_assinatura.setWidthPercentage(100);

			documento.add(tabela_assinatura);

			p = new Paragraph();
			p.setFont(negritoPequenaVermelha);
			p.add("Orçamento válido por até 30 dias. Após esse período os valores podem sofrer alterações.");
			documento.add(p);

			if (via.equals("via loja") && orcamento.getProdutos_do_orcamento().size() < 12) {
				p = new Paragraph();
				p.add(gera_string(130, "-"));
				p.setLeading(10);
				documento.add(p);
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}

	}

	public String gera_string(int quantidade_string, String string_passada) {
		String string_ = "";
		for (int n = 0; n < quantidade_string; n++) {
			string_ += string_passada;
		}
		return string_;
	}

	public void linha_invisivel_tabela_produtos() {
		cel_codigo.setBorderColor(BaseColor.WHITE);
		cel_nome.setBorderColor(BaseColor.WHITE);
		cel_fator.setBorderColor(BaseColor.WHITE);
		cel_quantidade.setBorderColor(BaseColor.WHITE);
		cel_preco_unit.setBorderColor(BaseColor.WHITE);
		cel_desc_unit.setBorderColor(BaseColor.WHITE);
		cel_valor.setBorderColor(BaseColor.WHITE);
	}

	public void linha_invisivel_tabela_cabecalho1() {
		cel_data_hora.setBorderColor(BaseColor.WHITE);
		cel_nome_empresa.setBorderColor(BaseColor.WHITE);
		cel_celular.setBorderColor(BaseColor.WHITE);
	}

	public void linha_invisivel_tabela_titulo() {
		cel_titulo.setBorderColor(BaseColor.WHITE);
		cel_via_cliente.setBorderColor(BaseColor.WHITE);
	}

	public void linha_invisivel_tabela_cabecalho2() {
		cel_orcamento.setBorderColor(BaseColor.WHITE);
		cel_data_criacao.setBorderColor(BaseColor.WHITE);
		cel_cliente.setBorderColor(BaseColor.WHITE);
		celular_cliente.setBorderColor(BaseColor.WHITE);
		cel_endereco.setBorderColor(BaseColor.WHITE);
		cel_quantidade_produtos.setBorderColor(BaseColor.WHITE);
		cel_em_branco.setBorderColor(BaseColor.WHITE);
	}

	public void linha_invisivel_tabela_totais() {
		cel_frete.setBorderColor(BaseColor.WHITE);
		cel_desc_prd.setBorderColor(BaseColor.WHITE);
		cel_desc_orc.setBorderColor(BaseColor.WHITE);
		cel_vlr_tot.setBorderColor(BaseColor.WHITE);
	}

	public void linha_invisivel_tabela_assinatura() {
		cel_assinatura.setBorderColor(BaseColor.WHITE);
		cel_vencimento.setBorderColor(BaseColor.WHITE);
	}

	public void imprimir(String arquivo) {
		PrintService[] printService = PrintServiceLookup.lookupPrintServices(DocFlavor.INPUT_STREAM.PDF, null);
		PrintService impressoraPadrao = PrintServiceLookup.lookupDefaultPrintService();
		DocFlavor docFlavor = DocFlavor.INPUT_STREAM.PDF;
		HashDocAttributeSet hashDocAttributeSet = new HashDocAttributeSet();

		try {
			FileInputStream fileInputStream = new FileInputStream(arquivo);
			Doc doc = new SimpleDoc(fileInputStream, docFlavor, hashDocAttributeSet);
			PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
			
			PrintService printServico = ServiceUI.printDialog(null, 300, 200, printService, impressoraPadrao, docFlavor,
					printRequestAttributeSet);

			if (impressoraPadrao != null) {
				JOptionPane.showMessageDialog(null,
						"Orçamento enviado para a impressora, aguarde a impressão....",
						"Impressão do orçamento.", JOptionPane.NO_OPTION);
				DocPrintJob docPrintJob = impressoraPadrao.createPrintJob();
				docPrintJob.print(doc, printRequestAttributeSet);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
