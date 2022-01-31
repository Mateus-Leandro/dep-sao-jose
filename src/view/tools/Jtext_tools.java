package view.tools;

import java.awt.Component;
import java.awt.TextField;
import java.awt.event.KeyEvent;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

public class Jtext_tools {
	
	public void move_cursor_inicio(JFormattedTextField nome_campo) {
		if(nome_campo.getText().trim().isEmpty()) {
			nome_campo.setCaretPosition(0);
		}else {
			nome_campo.setCaretPosition(nome_campo.getText().trim().length());
		}
	}
}
