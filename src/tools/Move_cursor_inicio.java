package tools;

import javax.swing.JFormattedTextField;

public class Move_cursor_inicio {
	
	public void move_cursor_inicio(JFormattedTextField nome_campo) {
		if(nome_campo.getText().trim().isEmpty()) {
			nome_campo.setCaretPosition(0);
		}/*else {
			nome_campo.setCaretPosition(nome_campo.getText().trim().length());
		}*/
	}
}
