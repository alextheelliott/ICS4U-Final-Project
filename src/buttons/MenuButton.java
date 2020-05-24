package buttons;

import application.SceneFX;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class MenuButton extends Buttons {
	
	private Pane createBMRoot;
	private Scene createBMScene;

	/* Two constructors, one has a dynamic image, the other doesn't. */
	public MenuButton(double wid, double posX, double posY, Image ip) {
		super(wid, posX, posY, ip);
		
		createScene();
	}
	
	public MenuButton(double wid, double posX, double posY, Image ip, Image ih) {
		super(wid, posX, posY, ip, ih, ImageCondition.HOVERDEPENDENT);
		
		createScene();
	}

	/* Creates the FX for the create bookmark scene. */
	private void createScene() {
		createBMRoot = new Pane();
		createBMScene = new Scene(createBMRoot, 400, 430);

		SceneFX.loadNewBookmarkSceneFX(createBMRoot);
	}

	/* Creates the behaviour to get to the new bookmark scene. */
	@Override
	void action() {
		SceneFX.loadBookmarks();
		application.Main.stage.setScene(createBMScene);
	}

	/* Default getState method. (The only class that needs this method is Dropdown. */
	@Override
	public boolean getState() {
		return false;
	}

}
