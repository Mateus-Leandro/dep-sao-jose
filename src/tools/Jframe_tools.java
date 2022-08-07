package tools;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

public class Jframe_tools {
//variaveis usadas para armazenar as medidas e possi�oes
	public int width, height, left, top;
	private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
//m�todo utilizado para pega as medidas e possi�oes
//do Component passado como par�metro
	public void setDimensaoTela(Component component) {
		Insets in = Toolkit.getDefaultToolkit().getScreenInsets(component.getGraphicsConfiguration());
		width = d.width - (in.left + in.right);
		height = d.height - (in.top + in.bottom);
		left = in.left;
		top = in.top;
	}
	
	public Dimension getDimension() {
		return d;
	}
}