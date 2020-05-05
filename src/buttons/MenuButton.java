package buttons;

import java.util.ArrayList;

import javax.security.auth.callback.TextInputCallback;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

public class MenuButton extends Buttons {
	
	private Pane createBMRoot;
	private Scene createBMScene;

	public MenuButton(double wid, double posX, double posY, Image ip) {
		super(wid, posX, posY, ip);
		
		createScene();
	}
	
	public MenuButton(double wid, double posX, double posY, Image ip, Image ih) {
		super(wid, posX, posY, ip, ih, ImageCondition.HOVERDEPENDENT);
		
		createScene();
	}

	public void createScene() {
		createBMRoot = new Pane();
		createBMScene = new Scene(createBMRoot, 400, 430);

		Rectangle background = new Rectangle();
		background.setWidth(400);
		background.setHeight(600);
		background.setFill(Color.web("#f5426c"));
		createBMRoot.getChildren().add(background);

		ArrayList<Text> texts = new ArrayList<>();
		for (int i = 0; i < 3; i++) { texts.add(new Text()); createBMRoot.getChildren().add(texts.get(i)); }

		texts.get(0).setText("Topic:");
		texts.get(0).setX(30);
		texts.get(0).setY(40);

		texts.get(1).setText("Title:");
		texts.get(1).setX(30);
		texts.get(1).setY(120);

		texts.get(2).setText("Website URL:");
		texts.get(2).setX(30);
		texts.get(2).setY(200);

		ArrayList<TextField> textFields = new ArrayList<>();
		for(int i = 0; i < 3; i++) { textFields.add(new TextField()); createBMRoot.getChildren().add(textFields.get(i)); }

		textFields.get(0).setPrefSize(340, 40);
		textFields.get(0).setLayoutX(30);
		textFields.get(0).setLayoutY(50);

		textFields.get(1).setPrefSize(340, 40);
		textFields.get(1).setLayoutX(30);
		textFields.get(1).setLayoutY(130);

		textFields.get(2).setPrefSize(340, 40);
		textFields.get(2).setLayoutX(30);
		textFields.get(2).setLayoutY(210);

		ArrayList<Button> buttons = new ArrayList<>();
		for(int i = 0; i < 2; i++) { buttons.add(new Button()); createBMRoot.getChildren().add(buttons.get(i)); }

		buttons.get(0).setText("Add New Bookmark");
		buttons.get(0).setPrefSize(150, 120);
		buttons.get(0).setLayoutX(30);
		buttons.get(0).setLayoutY(280);

		buttons.get(0).setOnAction(event -> {
			boolean canSubmit = true;
			for(int i = 0; i < textFields.size(); i++) {
				if(textFields.get(1).getText().replace(" ", "").equals("")) {
					textFields.get(1).setPromptText("THIS NEEDS TO BE FILLED");
					canSubmit = false;
				}
			}
			if(canSubmit) {
				resetFields(textFields);
				application.Main.stage.setScene(application.Main.scene);
			}
		});

		buttons.get(1).setText("Cancel");
		buttons.get(1).setPrefSize(150, 120);
		buttons.get(1).setLayoutX(220);
		buttons.get(1).setLayoutY(280);

		buttons.get(1).setOnAction(event -> {
			resetFields(textFields);
			application.Main.stage.setScene(application.Main.scene);
		});

	}

	public void resetFields(ArrayList<TextField> tfl) {
		for(int i = 0; i < tfl.size(); i++) {
			tfl.get(i).setText("");
			tfl.get(i).setPromptText("");
		}
	}

	@Override
	public void action() {
		application.Main.stage.setScene(createBMScene);
	}

	@Override
	public boolean getState() {
		return false;
	}

}
