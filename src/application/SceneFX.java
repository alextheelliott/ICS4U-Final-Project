package application;

import buttons.Buttons.ImageCondition;
import buttons.DropdownButton;
import buttons.HelpButton;
import buttons.MenuButton;

import static application.Constants.LayoutConstants.*;

import java.net.URL;
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
import javafx.scene.text.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SceneFX {

	/* Declares the main pane, the copied text, and the menu. */
	private static Pane pane;

	public static Text copied;

	public static VBox menuBar;
	public static DropdownButton menuDropdownButton;

	public static void loadMainSceneFX(Pane r) {
		pane = r;

		/* Creates a coloured rectangle for the background of the scene */
		Rectangle background = new Rectangle(r.getWidth(), r.getHeight()); // Width, height
		background.setFill(Color.web("#3246a8"));
		r.getChildren().add(background);

		/* Creates a transition to slowly pulse the background colour */
		FillTransition ft = new FillTransition(Duration.seconds(7), background, Color.web("#3246a8"),
				Color.web("#74248f"));
		ft.setCycleCount(FillTransition.INDEFINITE);
		ft.setAutoReverse(true);
		ft.play();

		/* Text title to notify the user when a url is copied. */
		copied = new Text("URL Copied");
		copied.setLayoutX((r.getWidth() / 2.0) - 40.0);
		copied.setLayoutY(20);
		copied.setOpacity(0.0);
		copied.setFont(Font.font(16));
		copied.setFill(Color.WHITE);
		r.getChildren().add(copied);

		/*
		 * The animation timer constantly makes sure that the background is the size of
		 * the scene. (Changing the fill of the scene caused issues.)
		 */
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				background.setWidth(r.getWidth() + 400);
				background.setHeight(r.getHeight() + 400);
				if (copied.getOpacity() > 0.0) {
					copied.setLayoutX((r.getWidth() / 2.0) - 40.0);
					copied.setOpacity(copied.getOpacity() - 0.008);
				}
			}
		}.start();

		loadBookmarks();

		/*
		 * JavaFX work to create the menu buttons: The dropdown, the new bookmark
		 * button, and the help button. adds the last two buttons to a Vbox so it can be
		 * hidden and shown with the dropdown button.
		 */
		menuBar = new VBox();
		menuBar.setMinWidth(0.0);
		menuBar.setPrefWidth(MENU_BUTTON_SIZE);
		menuBar.setMaxWidth(MENU_BUTTON_SIZE + MENU_TEXT_FIELD_WIDTH);
		menuBar.setPrefHeight(MENU_BUTTON_SIZE * 3.0);
		menuBar.setLayoutY(MENU_BUTTON_SIZE);
		r.getChildren().add(menuBar);

		HBox menuNewSite = new HBox();
		menuNewSite.setPrefWidth(MENU_BUTTON_SIZE + MENU_TEXT_FIELD_WIDTH);
		menuNewSite.setPrefHeight(MENU_BUTTON_SIZE);
		// Creates the object for the New Bookmark Scene and to get there
		MenuButton menuNewSiteButton = new MenuButton(MENU_BUTTON_SIZE, 0.0, 0.0,
				new Image(MENU_NEW_SITE_PASSIVE, false), new Image(MENU_NEW_SITE_HOVER, false));
		menuNewSite.getChildren().add(menuNewSiteButton.getNode());

		HBox menuHelp = new HBox();
		menuHelp.setPrefWidth(MENU_BUTTON_SIZE + MENU_TEXT_FIELD_WIDTH);
		menuHelp.setPrefHeight(MENU_BUTTON_SIZE);
		// Creates the object for the Help Scene and how to get to the scene
		HelpButton menuHelpButton = new HelpButton(MENU_BUTTON_SIZE, 0.0, 0.0, new Image(MENU_HELP_PASSIVE, false),
				new Image(MENU_HELP_HOVER, false));
		menuHelp.getChildren().add(menuHelpButton.getNode());

		/* Adds all the buttons to the Vbox */
		menuBar.getChildren().addAll(menuNewSite, menuHelp);

		menuDropdownButton = new DropdownButton(MENU_BUTTON_SIZE, 0.0, 0.0,
				new Image(MENU_DROPDOWN_PASSIVE, false), new Image(MENU_DROPDOWN_HOVER, false),
				ImageCondition.HOVERDEPENDENT, menuBar, true);
		r.getChildren().add(menuDropdownButton.getNode());
	}

	/*
	 * Method to load all of the bookmarks on the default pane. Called when we traverse back to the main pane.
	 */
	private static HBox hbox;
	private static double hboxTranslate = 0;
	public static void loadBookmarks() {
		/* 
		 * If the hbox has already been created we should remove it from the pane to prevent errors and to stop it from rendering old material,
		 * We then recreate it with the new bookmarks.
		 */
		if (hbox != null) {
			pane.getChildren().remove(hbox);
		}
		hbox = new HBox();
		hbox.setMinHeight(600);
		hbox.setLayoutX(100);
		hbox.setLayoutY(30);
		/* Sets an event so that when you scroll you can move the hbox to view all the categories but stops you from scrolling off the page. */
		hbox.setOnScroll(event -> {
			hboxTranslate += event.getDeltaY() / 2.0;
			hboxTranslate = Math.min(0.0, hboxTranslate);
			hboxTranslate = Math.max(-(hbox.getWidth()-((VBox) hbox.getChildren().get(hbox.getChildren().size()-1)).getWidth()), hboxTranslate);
			hbox.setTranslateX(hboxTranslate);
		});

		/* Adds to renderer. */
		hbox.getChildren().add(new VBox());
		pane.getChildren().add(hbox);

		/* Adds all the bookmarks to a grid of vboxes and hboxes based on the topic (Sorted in Bookmark class). */
		ArrayList<ArrayList<Bookmark>> bookmarks = Bookmark.getBookmarks();
		for (ArrayList<Bookmark> bookmarksByTopic : bookmarks) {
			String topic = bookmarksByTopic.get(0).getTopic();
			VBox vbox = new VBox();

			/* Title for the category. */
			Text t = new Text(topic);
			t.setFont(Font.font("Gibson", FontWeight.NORMAL, 25));
			t.setFill(Color.WHITE);
			vbox.getChildren().add(t);

			for (Bookmark bookmark : bookmarksByTopic) {
				BookmarkFx bm = new BookmarkFx(bookmark, 50.0, 50.0);
				vbox.getChildren().add(bm.getNode());
			}
			hbox.getChildren().add(vbox);
		}

		/* Brings the menu in front of the bookmarks */
		menuBar.toFront();
		menuDropdownButton.getNode().toFront();
	}

	/*
	 * Creates the scene for adding a new bookmark. Adds all the titles and fields.
	 */
	public static void loadNewBookmarkSceneFX(Pane r) {
		Rectangle background = new Rectangle();
		background.setWidth(400);
		background.setHeight(600);
		background.setFill(Color.web("#f5426c"));
		r.getChildren().add(background);

		ArrayList<Text> texts = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			texts.add(new Text());
			r.getChildren().add(texts.get(i));
		}

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
		for (int i = 0; i < 3; i++) {
			textFields.add(new TextField());
			r.getChildren().add(textFields.get(i));
		}

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
		for (int i = 0; i < 2; i++) {
			buttons.add(new Button());
			r.getChildren().add(buttons.get(i));
		}

		buttons.get(0).setText("Add New Bookmark");
		buttons.get(0).setPrefSize(150, 120);
		buttons.get(0).setLayoutX(30);
		buttons.get(0).setLayoutY(280);

		/* 
		 * When you click add bookmark this block of code validates to make sure the required field (url) is filled properly. 
		 * If it is valid than it adds a new bookmark to the csv file.
		 */
		buttons.get(0).setOnAction(event -> {
			boolean canSubmit = true;
			//Checks if field is empty
			if (textFields.get(2).getText().replace(" ", "").equals("")) {
				textFields.get(2).setPromptText("THIS NEEDS TO BE FILLED");
				canSubmit = false;
			}
			// Checks if field is a valid url
			// Source: https://stackoverflow.com/questions/3931696/how-to-verify-if-a-string-in-java-is-a-valid-url
			else {
				//Attempts to create a url and if it gives an error we know it is not an URL
				try {
					@SuppressWarnings("unused")
					URL urlAttempt = new URL(textFields.get(2).getText());
				} catch (Exception e) {
					textFields.get(2).setText("");
					textFields.get(2).setPromptText("THIS NEEDS TO BE A VALID URL");
					canSubmit = false;
				}
				
			}
			// Submits the fields to the csv.
			if (canSubmit) {
				Bookmark bm = new Bookmark(textFields.get(2).getText(), textFields.get(0).getText(),
						textFields.get(1).getText());
				ArrayList<Bookmark> bookmarks = Bookmark.loadCSV();
				bookmarks.add(bm);
				Bookmark.saveCSV(bookmarks);
				resetFields(textFields);
				loadBookmarks();
				application.Main.stage.setScene(application.Main.scene);
			}
		});

		/* Creates a button to get back to the main screen that also clears the textfields. */
		buttons.get(1).setText("Cancel");
		buttons.get(1).setPrefSize(150, 120);
		buttons.get(1).setLayoutX(220);
		buttons.get(1).setLayoutY(280);

		buttons.get(1).setOnAction(event -> {
			resetFields(textFields);
			application.Main.stage.setScene(application.Main.scene);
		});
	}

	//Iterates through a list of text fields and clears their texts.
	private static void resetFields(ArrayList<TextField> tfl) {
		for (int i = 0; i < tfl.size(); i++) {
			tfl.get(i).setText("");
			tfl.get(i).setPromptText("");
		}
	}

	/* 
	 * Creates a scene to instruct the users on how to use the program. No other functionality.
	 */
	public static void loadHelpSceneFX(Pane r) {
		Rectangle background = new Rectangle();
		background.setWidth(500);
		background.setHeight(500);
		background.setFill(Color.web("#81A1C1"));
		r.getChildren().add(background);

		Button b = new Button();
		b.setLayoutX(0);
		b.setLayoutY(0);
		b.setPrefSize(30, 30);
		b.setText("❌");
		b.setStyle("-fx-text-fill: #2E3440");
		b.setOnAction(event -> {
			application.Main.stage.setScene(application.Main.scene);
		});
		r.getChildren().add(b);

		Text text = new Text();
		text.setFont(Font.font("Gibson", FontWeight.BOLD, 30));
		text.setFill(Color.web("2E3440"));
		text.setX(100);
		text.setY(50);
		text.setText("gib ༼ つ ◕_◕ ༽つ help");
		r.getChildren().add(text);

		text = new Text();
		text.setFont(Font.font("Gibson", FontWeight.NORMAL, 25));
		text.setFill(Color.web("2E3440"));
		text.setX(20);
		text.setY(100);
		text.setText("Creating a bookmark");
		r.getChildren().add(text);

		text = new Text();
		text.setFont(Font.font("Gibson", FontWeight.LIGHT, 15));
		text.setFill(Color.web("2E3440"));
		text.setX(20);
		text.setY(120);
		text.setText("- Press the create button and enter the URL of the website\n"
				+ "- You can also set the title and the topic, but these are not required");
		r.getChildren().add(text);

		text = new Text();
		text.setFont(Font.font("Gibson", FontWeight.NORMAL, 25));
		text.setFill(Color.web("2E3440"));
		text.setX(20);
		text.setY(180);
		text.setText("Deleting a bookmark");
		r.getChildren().add(text);

		text = new Text();
		text.setFont(Font.font("Gibson", FontWeight.LIGHT, 15));
		text.setFill(Color.web("2E3440"));
		text.setX(20);
		text.setY(200);
		text.setText("- Press the X next to a bookmark to delete it");
		r.getChildren().add(text);

		text = new Text();
		text.setFont(Font.font("Gibson", FontWeight.NORMAL, 25));
		text.setFill(Color.web("2E3440"));
		text.setX(20);
		text.setY(240);
		text.setText("Opening a bookmark");
		r.getChildren().add(text);

		text = new Text();
		text.setFont(Font.font("Gibson", FontWeight.LIGHT, 15));
		text.setFill(Color.web("2E3440"));
		text.setX(20);
		text.setY(260);
		text.setText("- Click on a bookmark to copy its URL\n"+
					 "- You can also press the arrow next to it to see a preview");
		r.getChildren().add(text);
	}

}