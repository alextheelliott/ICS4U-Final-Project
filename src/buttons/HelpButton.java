package buttons;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class HelpButton extends Buttons {

	private Pane helpRoot;
	private Scene helpScene;
	
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
		
		Rectangle background = new Rectangle();
		background.setWidth(500);
		background.setHeight(500);
		background.setFill(Color.web("#e03b22"));
		helpRoot.getChildren().add(background);
		
		Text text = new Text();
		text.setX(50);
		text.setY(50);
		text.setText("lmao");
		helpRoot.getChildren().add(text);
		
		Button b = new Button();
		b.setLayoutX(50);
		b.setLayoutY(50);
		b.setPrefSize(50, 50);
		b.setOnAction(event -> {
			application.Main.stage.setScene(application.Main.scene);
		});
		helpRoot.getChildren().add(b);
	}
	
	@Override
	public void action() {
		application.Main.stage.setScene(helpScene);
	}

	@Override
	public boolean getState() {
		return false;
	}

}
