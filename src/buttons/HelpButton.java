package buttons;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

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
		background.setFill(Color.web("#81A1C1"));
		helpRoot.getChildren().add(background);

		Button b = new Button();
		b.setLayoutX(0);
		b.setLayoutY(0);
		b.setPrefSize(30, 30);
		b.setText("❌");
		b.setStyle("-fx-text-fill: #2E3440");
		b.setOnAction(event -> {
			application.Main.stage.setScene(application.Main.scene);
		});
		helpRoot.getChildren().add(b);
		
		Text text = new Text();
		text.setFont(Font.font("Gibson", FontWeight.BOLD, 30));
		text.setX(100);
		text.setY(50);
		text.setText("gib ༼ つ ◕_◕ ༽つ help");
		helpRoot.getChildren().add(text);

		text = new Text();
		text.setFont(Font.font("Gibson", FontWeight.NORMAL, 25));
		text.setX(20);
		text.setY(100);
		text.setText("Creating a bookmark");
		helpRoot.getChildren().add(text);

		text = new Text();
		text.setFont(Font.font("Gibson", FontWeight.LIGHT, 15));
		text.setX(20);
		text.setY(120);
		text.setText("- Press the create button and enter the URL of the website\n"+
					 "- You can also set the title and the topic, but these are not required");
		helpRoot.getChildren().add(text);


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
