package buttons;

import static application.Constants.LayoutConstants.*;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import javafx.scene.layout.VBox;

public class DropdownButton extends Buttons {
	
	private boolean state;
	private Node child;
	
	/* Three constructors. One with a static image, one with two images switching dynamically, and one with default state. */
	public DropdownButton(double wid, double posX, double posY, Image ip, Node cld) {
		super(wid, posX, posY, ip);
		
		state = false;
		child = cld;
		child.setVisible(this.getState());
	}
	
	public DropdownButton(double wid, double posX, double posY, Image ip, Image ih, ImageCondition ic, Node cld) {
		super(wid, posX, posY, ip, ih, ic);
		
		state = false;
		child = cld;
		child.setVisible(this.getState());
	}
	
	/* Note:
	 * dfState must be false if the ImageCondition is State Dependent
	 */
	public DropdownButton(double wid, double posX, double posY, Image ip, Image ih, ImageCondition ic, Node cld, boolean dfState) {
		super(wid, posX, posY, ip, ih, ic);
		
		state = dfState;
		child = cld;
		child.setVisible(this.getState());
	}

	/* Adds functionality to use the state for whether it is dropped down or not. */
	@Override
	void action() {
		state = !state;
		//If the child is a WebView is shrinks the parent VBox so that it collapses properly
		if(child.getClass() == WebView.class) {
			((VBox) child.getParent()).setMaxHeight(this.getState() ? 250.0 * (9.0/16.0) + MENU_BUTTON_SIZE/3 : MENU_BUTTON_SIZE/3);
		}
		child.setVisible(this.getState());
	}

	/* Returns a boolean for dropped or not. */
	@Override
	public boolean getState() {
		return state;
	}

}
