package application;

import buttons.Buttons.ImageCondition;
import buttons.DropdownButton;
import buttons.HelpButton;
import buttons.MenuButton;

import static application.Constants.LayoutConstants.*;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SceneFX {

	private static Pane pane;

	public static Text copied;

    public static void loadMainSceneFX(Pane r) {
		pane = r;

        /* Creates a coloured rectangle for the background of the scene */
		Rectangle background = new Rectangle(r.getWidth(), r.getHeight()); // Width, height
		background.setFill(Color.web("#3246a8"));
		r.getChildren().add(background);
		
		/* 
			JavaFX work to create the menu buttons: The dropdown, the new bookmark button, and the help button. 
			adds the last two buttons to a Vbox so it can be hidden and shown with the dropdown button.
		*/
		VBox menuBar = new VBox();
		menuBar.setMinWidth(0.0);
		menuBar.setPrefWidth(MENU_BUTTON_SIZE);
		menuBar.setMaxWidth(MENU_BUTTON_SIZE + MENU_TEXT_FIELD_WIDTH);
		menuBar.setPrefHeight(MENU_BUTTON_SIZE*3.0);
		menuBar.setLayoutY(MENU_BUTTON_SIZE);
		r.getChildren().add(menuBar);
		
		HBox menuNewSite = new HBox();
		menuNewSite.setPrefWidth(MENU_BUTTON_SIZE + MENU_TEXT_FIELD_WIDTH);
		menuNewSite.setPrefHeight(MENU_BUTTON_SIZE);
		//Creates the object for the New Bookmark Scene and to get there
		MenuButton menuNewSiteButton = new MenuButton(
				MENU_BUTTON_SIZE, 
				0.0, 0.0, 
				new Image(MENU_NEW_SITE_PASSIVE, false), 
				new Image(MENU_NEW_SITE_HOVER, false)
			);
		menuNewSite.getChildren().add( menuNewSiteButton.getNode() );
		
		HBox menuHelp = new HBox();
		menuHelp.setPrefWidth(MENU_BUTTON_SIZE + MENU_TEXT_FIELD_WIDTH);
		menuHelp.setPrefHeight(MENU_BUTTON_SIZE);
		//Creates the object for the Help Scene and how to get to the scene
		HelpButton menuHelpButton = new HelpButton(
				MENU_BUTTON_SIZE, 
				0.0, 0.0, 
				new Image(MENU_DROPDOWN_PASSIVE, false),
				new Image(MENU_DROPDOWN_HOVER, false)
			);
		menuHelp.getChildren().add( menuHelpButton.getNode() );
		
		/* Adds all the buttons to the Vbox */
		menuBar.getChildren().addAll(
				menuNewSite,
				menuHelp
			);
		
		DropdownButton menuDropdownButton = new DropdownButton(
				MENU_BUTTON_SIZE, 
				0.0, 0.0, 
				new Image(MENU_DROPDOWN_PASSIVE, false), 
				new Image(MENU_DROPDOWN_HOVER, false), 
				ImageCondition.HOVERDEPENDENT, 
				menuBar, true
			);
		r.getChildren().add(menuDropdownButton.getNode());
		
		/* Creates a transition to slowly pulse the background colour */
		FillTransition ft = new FillTransition(Duration.seconds(7), background, Color.web("#3246a8"), Color.web("#74248f"));
		ft.setCycleCount(FillTransition.INDEFINITE);
		ft.setAutoReverse(true);
        ft.play();
		
		copied = new Text("URL Copied");
		copied.setLayoutX((r.getWidth()/2.0) - 40.0);
		copied.setLayoutY(20);
		copied.setOpacity(0.0);
		copied.setFont(Font.font(16));
		copied.setFill(Color.WHITE);
		r.getChildren().add(copied);

        /* The animation timer constantly makes sure that the background is the size of the scene. (Changing the fill of the scene caused issues.) */
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				background.setWidth(r.getWidth() + 400);
				background.setHeight(r.getHeight() + 400);
				if(copied.getOpacity() > 0.0) {
					copied.setLayoutX((r.getWidth()/2.0) - 40.0);
					copied.setOpacity(copied.getOpacity() - 0.008);
				}
			}
		}.start();

		loadBookmarks();
	}
	
	private static HBox hbox;

	public static void loadBookmarks() {
		int vboxNum = 0;

		if(hbox != null) {
			pane.getChildren().remove(hbox);
		}
		hbox = new HBox();
		hbox.setLayoutX(100);
		hbox.setLayoutY(50);
		hbox.getChildren().add(new VBox());
		pane.getChildren().add(hbox);

		ArrayList<ArrayList<Bookmark>> bookmarks = Bookmark.getBookmarks();
		
		for(ArrayList<Bookmark> bookmarks2 : bookmarks)  {
			for(Bookmark bookmark : bookmarks2) {
				BookmarkFx bm = new BookmarkFx(bookmark, 50.0, 50.0);
				if(((VBox) hbox.getChildren().get(vboxNum)).getChildren().size() >= 8) {
					vboxNum++;
					hbox.getChildren().add(new VBox());
				}
				((VBox) hbox.getChildren().get(vboxNum)).getChildren().add(bm.getNode());
			}
		}
	}

    public static void loadNewBookmarkSceneFX(Pane r) {
		Rectangle background = new Rectangle();
		background.setWidth(400);
		background.setHeight(600);
		background.setFill(Color.web("#f5426c"));
		r.getChildren().add(background);

		ArrayList<Text> texts = new ArrayList<>();
		for (int i = 0; i < 3; i++) { texts.add(new Text()); r.getChildren().add(texts.get(i)); }

		texts.get(0).setText("Topic:");
		texts.get(0).setX(30);
		texts.get(0).setY(40);
		texts.get(0).setFont(Font.font(16));
		texts.get(0).setFill(Color.WHITE);

		texts.get(1).setText("Title:");
		texts.get(1).setX(30);
		texts.get(1).setY(120);
		texts.get(1).setFont(Font.font(16));
		texts.get(1).setFill(Color.WHITE);

		texts.get(2).setText("Website URL:*");
		texts.get(2).setX(30);
		texts.get(2).setY(200);
		texts.get(2).setFont(Font.font(16));
		texts.get(2).setFill(Color.WHITE);

		ArrayList<TextField> textFields = new ArrayList<>();
		for(int i = 0; i < 3; i++) { textFields.add(new TextField()); r.getChildren().add(textFields.get(i)); }

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
		for(int i = 0; i < 2; i++) { buttons.add(new Button()); r.getChildren().add(buttons.get(i)); }

		buttons.get(0).setText("Add New Bookmark");
		buttons.get(0).setPrefSize(150, 120);
		buttons.get(0).setLayoutX(30);
		buttons.get(0).setLayoutY(280);

		buttons.get(0).setOnAction(event -> {
			boolean canSubmit = true;
			if(textFields.get(2).getText().replace(" ", "").equals("")) {
				textFields.get(2).setPromptText("THIS NEEDS TO BE FILLED");
				canSubmit = false;
			}
			if(canSubmit) {
				Bookmark bm = new Bookmark(textFields.get(2).getText(), textFields.get(0).getText(), textFields.get(1).getText());
				ArrayList<Bookmark> bookmarks = Bookmark.loadCSV();
				bookmarks.add(bm);
				Bookmark.saveCSV(bookmarks);
				resetFields(textFields);
				loadBookmarks();
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

    private static void resetFields(ArrayList<TextField> tfl) {
		for(int i = 0; i < tfl.size(); i++) {
			tfl.get(i).setText("");
			tfl.get(i).setPromptText("");
		}
	}

    public static void loadHelpSceneFX(Pane r) {
        Rectangle background = new Rectangle();
		background.setWidth(500);
		background.setHeight(500);
		background.setFill(Color.web("#e03b22"));
		r.getChildren().add(background);
		
		Text text = new Text();
		text.setX(50);
		text.setY(50);
		text.setText("lmao");
		r.getChildren().add(text);
		
		Button b = new Button();
		b.setLayoutX(50);
		b.setLayoutY(50);
		b.setPrefSize(50, 50);
		b.setOnAction(event -> {
			application.Main.stage.setScene(application.Main.scene);
		});
		r.getChildren().add(b);
    }

}