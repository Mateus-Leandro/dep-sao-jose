package tables.tableSorters;

import java.util.Comparator;

public class SorterMonetario implements Comparator<String>{
	

	@Override
	public int compare(String valor1, String valor2) {
		
		Double formatado1 = Double.parseDouble(valor1.substring(3, valor1.length()).replaceAll("\\.", "").replaceAll(",", "."));
		Double formatado2 = Double.parseDouble(valor2.substring(3, valor2.length()).replaceAll("\\.", "").replaceAll(",","."));
		
		
		return formatado1.compareTo(formatado2);
	}

}
