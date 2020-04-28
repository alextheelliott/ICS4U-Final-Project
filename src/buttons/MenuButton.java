package buttons;

import javafx.scene.control.TextField;
import javafx.scene.image.Image;

public class MenuButton extends Buttons {

	private TextField textField;
	
	public MenuButton(double wid, double posX, double posY, Image ip, TextField tf) {
		super(wid, posX, posY, ip);
		
		textField = tf;
		textField.setVisible(false);
	}
	
	public MenuButton(double wid, double posX, double posY, Image ip, Image ih, TextField tf) {
		super(wid, posX, posY, ip, ih, ImageCondition.HOVERDEPENDENT);
		
		textField = tf;
		textField.setVisible(false);
	}

	@Override
	public void action() {
		textField.setVisible(!textField.visibleProperty().get());
	}

	@Override
	public boolean getState() {
		return false;
	}

}
