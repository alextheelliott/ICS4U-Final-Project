package application;

import buttons.Buttons.ImageCondition;
import buttons.DropdownButton;
import buttons.HelpButton;
import buttons.MenuButton;

import static application.Constants.LayoutConstants.*;

import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Main extends Application {
	
	public Pane root;
	public static Scene scene;
	public static Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		root = new Pane();
		scene = new Scene(root,800,600);
		stage = primaryStage;
		
		/* Creates a coloured rectangle for the background of the scene */
		Rectangle background = new Rectangle(scene.getWidth(), scene.getHeight()); // Width, height
		background.setFill(Color.web("#3246a8"));
		root.getChildren().add(background);
		
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
		root.getChildren().add(menuBar);
		
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
		root.getChildren().add(menuDropdownButton.getNode());
		
		/* Creates a transition to slowly pulse the background colour */
		FillTransition ft = new FillTransition(Duration.seconds(7), background, Color.web("#3246a8"), Color.web("#74248f"));
		ft.setCycleCount(4);
		ft.setAutoReverse(true);
		ft.play();
		
		/* Sets scene to default scene */
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		
		/* The animation timer constantly makes sure that the background is the size of the scene. (Changing the fill of the scene caused issues.) */
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				background.setWidth(scene.getWidth() + 400);
				background.setHeight(scene.getHeight() + 400);
			}
		}.start();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
