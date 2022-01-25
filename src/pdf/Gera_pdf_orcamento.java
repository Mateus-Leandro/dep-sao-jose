package pdf;

import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class Gera_pdf_orcamento {
 
	public static void main(String[] arq) {
		try {
 
			// Criação do objeto que será um documento PDF
			Document documento = new Document();
			
			// Faz o apontamento para o arquivo de destino
			PdfWriter.getInstance(documento, new FileOutputStream(
					"C:/dep/novoPDF.pdf"));
			
			// Realiza a abertura do arquivo para escrita
			documento.open();
			
			// Escreve uma mensagem no arquivo
			documento.add(new Paragraph("Alo mundo!"));
 
			// Fecha o arquivo após a escrita da mensagem
			documento.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
 
	}
	
	
	public void gera_cabecalho() {
		
	}
}
