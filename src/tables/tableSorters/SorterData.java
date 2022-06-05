package tables.tableSorters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class SorterData implements Comparator<String> {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public int compare(String data1, String data2) {

		Date data_formatada1 = null;
		Date data_formatada2 = null;
		try {
			data_formatada1 = sdf.parse(data1);
			data_formatada2 = sdf.parse(data2);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return data_formatada1.compareTo(data_formatada2);
	}

}
