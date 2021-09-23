package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Fator_venda {

	private List<String> fator = new ArrayList<String>(Arrays.asList(new String[] { "UN", "CX", "MT", "FD", "L" }));

	public Fator_venda() {
	}

	public void addFatorVenda(List<String> fatores, String novo_fator) {
		fatores.add(novo_fator);
	}

}
