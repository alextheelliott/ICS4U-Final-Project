package buttons;

import javafx.scene.Node;
import javafx.scene.image.Image;

public class DropdownButton extends Buttons {
	
	private boolean state;
	private Node child;
	
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

	@Override
	public void action() {
		state = !state;
		child.setVisible(this.getState());
	}

	@Override
	public boolean getState() {
		return state;
	}

}
