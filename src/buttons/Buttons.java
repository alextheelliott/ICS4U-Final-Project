package buttons;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Buttons {

	public enum ImageCondition {
		HOVERDEPENDENT, STATEDEPENDENT
	}
	
	private ImageView image;
	private Image passive;
	private Image hover;
	
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
		if(ic == ImageCondition.HOVERDEPENDENT) {
			image.setOnMouseClicked(event -> { action(); });
			image.setOnMouseEntered(event -> { image.setImage(hover); });
			image.setOnMouseExited(event -> { image.setImage(passive); });
		} else {
			image.setOnMouseClicked(event -> { action(); setImage(); });
		}
		
		this.setLocation(posX, posY);
	}
	
	public void setLocation(double x, double y) {
		image.setLayoutX(x);
		image.setLayoutY(y);
	}
	
	public Node getNode() {
		return image;
	}
	
	public void setImage() {
		image.setImage((getState()) ? passive : hover);
	}
	
	public abstract void action();
	
	public abstract boolean getState();
	
}
