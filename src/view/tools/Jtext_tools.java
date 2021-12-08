package view.tools;

import java.awt.Component;
import java.awt.TextField;
import java.awt.event.KeyEvent;

import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

public class Jtext_tools {
	
	
	public void move_cursor_inicio(JFormattedTextField fTxtNomeProduto) {
		if(fTxtNomeProduto.getText().trim().isEmpty()) {
			fTxtNomeProduto.setCaretPosition(0);
		}
	}
	
	public void proximo_campo(KeyEvent tecla, Component comp) {
		if(tecla.getKeyCode() == tecla.VK_ENTER) {
			comp.requestFocus();
		}
	}
}
