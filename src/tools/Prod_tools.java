package tools;

public class Prod_tools {

	public Boolean valida_barras(String barras) {
		int digito;
		int calculado;
		String ean;
		String checkSum = "131313131313";
		int soma = 0;

		if (barras.length() == 8 || barras.length() == 13) {
			digito = Integer.parseInt("" + barras.charAt(barras.length() - 1));
			ean = barras.substring(0, barras.length() - 1);
			for (int i = 0; i <= ean.length() - 1; i++) {
				soma += (Integer.parseInt("" + ean.charAt(i))) * (Integer.parseInt("" + checkSum.charAt(i)));
			}
			calculado = 10 - (soma % 10);
			return (digito == calculado);
		} else {
			return false;
		}
	}

}
