package tools;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

public class Jframe_tools {
//variaveis usadas para armazenar as medidas e possiçoes
	public int width, height, left, top;
	private Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
//método utilizado para pega as medidas e possiçoes
//do Component passado como parâmetro
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