package buttons;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Buttons {

	/* Enum to decide how to change the image for the button: Hover dependent or state dependent (for dropdown). */
	public enum ImageCondition {
		HOVERDEPENDENT, STATEDEPENDENT
	}
	
	private ImageView image;
	private Image passive;
	private Image hover;
	
	/* Constructors (One with static image, other one with dynamic image) */
	public Buttons(double wid, double posX, double posY, Image ip) {
		passive = ip;
		
		image = new ImageView(passive);
		image.setPreserveRatio(true);
		image.setFitWidth(wid);
		image.setOnMouseClicked(event -> { action(); });
		
		this.setLocation(posX, posY);
	}
	
	public Buttons(double wid, double posX, double posY, Image ip, Image ih, ImageCondition ic) {
		passive = ip;
		hover = ih;
		
		image = new ImageView(passive);
		image.setPreserveRatio(true);
		image.setFitWidth(wid);

		//Uses the enum to decide which image should be shown on certain events
		if(ic == ImageCondition.HOVERDEPENDENT) {
			image.setOnMouseClicked(event -> { action(); });
			image.setOnMouseEntered(event -> { image.setImage(hover); });
			image.setOnMouseExited(event -> { image.setImage(passive); });
		} else {
			image.setOnMouseClicked(event -> { action(); setImage(); });
		}
		
		this.setLocation(posX, posY);
	}
	
	/* Method to make setting the position a single method call */
	public void setLocation(double x, double y) {
		image.setLayoutX(x);
		image.setLayoutY(y);
	}
	
	public Node getNode() {
		return image;
	}
	
	/* Sets the image based on the state of the object which the behaviour of which is created in the child. */
	public void setImage() {
		image.setImage((getState()) ? passive : hover);
	}
	
	/* Let's the child set the behaviour of the button action. (click) */
	abstract void action();
	
	/* 
		Let's the child set the behaviour of the state of the object. The only child that needs this method 
		is the dropdown tab but it is created abstractly here so we can use it in the parent constructor.
	*/
	public abstract boolean getState();
	
}
