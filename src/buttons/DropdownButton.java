package buttons;

import javafx.scene.Node;
import javafx.scene.image.Image;

public class DropdownButton extends Buttons {
	
	private boolean state;
	private Node child;
	
	/* Two constructors. One with a static image one with two images switching dynamically. */
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

	/* Adds functionality to use the state for whther it is dropped down or not. */
	@Override
	void action() {
		state = !state;
		child.setVisible(this.getState());
	}

	/* Returns a boolean for dropped or not. */
	@Override
	public boolean getState() {
		return state;
	}

}
