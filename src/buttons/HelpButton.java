package buttons;

import application.SceneFX;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class HelpButton extends Buttons {

	private Pane helpRoot;
	private Scene helpScene;
	
	/* Constructors (One with two images, one with one) */
	public HelpButton(double wid, double posX, double posY, Image ip) {
		super(wid, posX, posY, ip);
		
		createScene();
	}
	
	public HelpButton(double wid, double posX, double posY, Image ip, Image ih) {
		super(wid, posX, posY, ip, ih, ImageCondition.HOVERDEPENDENT);
		
		createScene();
	}

	public void createScene() {
		helpRoot = new Pane();
		helpScene = new Scene(helpRoot, 500, 500);
		
		SceneFX.loadHelpSceneFX(helpRoot);
	}
	
	/* Creates the behaviour to go to the help scene. */
	@Override
	void action() {
		application.Main.stage.setScene(helpScene);
	}

	/* Default get state (not necessary for the help button) */
	@Override
	public boolean getState() {
		return false;
	}

}
