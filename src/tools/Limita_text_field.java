package tools;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class Limita_text_field extends PlainDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int limite;
	private String formato;
	private char c;
	private byte n;

	public Limita_text_field(int limite, String formato) {
		super();
		this.limite = limite;
		this.formato = formato;
	}

	@Override
	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null) {
			return;
		}

		if ((getLength() + str.length()) <= limite) {
			if (formato.equalsIgnoreCase("inteiro")) {
				if(valida_inteiro(str)) {
					super.insertString(offset, str, attr);
				}
			}else {
				super.insertString(offset, str, attr);
			}
		}
	}

	public Boolean valida_inteiro(String str) {
		n = 1;
		// percorre a string
		for (byte i = 0; i < str.length(); i++) {
			c = str.charAt(i);

			if (!Character.isDigit(c)) {
				n = 0;
			}
		}
		if (n != 0) {
			return true;
		} else {
			return false;
		}
	}
}