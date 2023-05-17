package tools;

import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.JFormattedTextField;

public class Calcula_sug_prvenda {

	public void calcula_sugestao_prvenda(JFormattedTextField fTxt_custo, JFormattedTextField fTxt_margem,
			JFormattedTextField fTxt_sugestao, JFormattedTextField fTxt_preco_venda,
			JFormattedTextField fTxt_margem_praticada) {
		DecimalFormat formata_sugestao = new DecimalFormat("#,###0.00");
		if (!fTxt_custo.getText().trim().isEmpty()) {
			Double preco_custo = 0.00;
			Double margem = 0.00;
			Double preco_venda = 0.00;
			Double margem_praticada = 0.00;

			try {
				preco_custo = formata_sugestao.parse(fTxt_custo.getText()).doubleValue();
				if (!fTxt_margem.getText().trim().isEmpty()) {
					margem = formata_sugestao.parse(fTxt_margem.getText()).doubleValue();
				}
				if (!fTxt_preco_venda.getText().trim().isEmpty()) {
					preco_venda = formata_sugestao.parse(fTxt_preco_venda.getText()).doubleValue();
				}

			} catch (ParseException e) {
				e.printStackTrace();
			}

			Double preco_sugerido = preco_custo * (1 + (margem / 100));
			fTxt_sugestao.setText(formata_sugestao.format(preco_sugerido));
			if (preco_custo != 0.0) {
				margem_praticada = ((preco_venda - preco_custo) / preco_custo) * 100;
			}

			if (preco_venda != 0.0) {
				fTxt_margem_praticada.setText(formata_sugestao.format(margem_praticada));
			} else {
				fTxt_margem_praticada.setText(formata_sugestao.format(0.0));
			}
		}
	}
}
