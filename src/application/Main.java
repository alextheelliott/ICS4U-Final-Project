package application;

import buttons.Buttons.ImageCondition;
import buttons.DropdownButton;
import buttons.HelpButton;
import buttons.MenuButton;

import static application.Constants.LayoutConstants.*;

import javafx.animation.AnimationTimer;
//import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.stage.Stage;
//import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Main extends Application {
	
	public Pane root;
	public static Scene scene;
	public static Stage stage;
	
	private static double rx, ry;
	
	@Override
	public void start(Stage primaryStage) {
		root = new Pane();
		scene = new Scene(root,800,600);
		stage = primaryStage;
		
		Rectangle background = new Rectangle(scene.getWidth(), scene.getHeight()); // Width, height
		background.setFill(Color.web("#db5c0d"));
		root.getChildren().add(background);
		
		Rectangle r = new Rectangle(60, 50); 
		root.getChildren().add(r);
		r.setFill(Color.WHITE);
		rx = 100;
		ry = 200;
		r.setOnMouseDragged(e -> {
			rx = e.getX();
			ry = e.getY();
		});
		
		VBox menuBar = new VBox();
		menuBar.setMinWidth(0.0);
		menuBar.setPrefWidth(MENU_BUTTON_SIZE);
		menuBar.setMaxWidth(MENU_BUTTON_SIZE + MENU_TEXT_FIELD_WIDTH);
		menuBar.setPrefHeight(MENU_BUTTON_SIZE*3.0);
		menuBar.setLayoutY(MENU_BUTTON_SIZE);
		root.getChildren().add(menuBar);
			
		HBox menuNewTask = new HBox();
		menuNewTask.setPrefWidth(MENU_BUTTON_SIZE + MENU_TEXT_FIELD_WIDTH);
		menuNewTask.setPrefHeight(MENU_BUTTON_SIZE);
		TextField menuNewTaskTF = new TextField();
		menuNewTaskTF.setPrefWidth(MENU_TEXT_FIELD_WIDTH);
		menuNewTaskTF.setMinWidth(MENU_TEXT_FIELD_WIDTH);
		menuNewTaskTF.setPrefHeight(MENU_BUTTON_SIZE);
		menuNewTaskTF.setText("What Task would you like to add?");
		MenuButton menuNewTaskButton = new MenuButton(
				MENU_BUTTON_SIZE, 
				0.0, 0.0, 
				new Image(MENU_NEW_TASK_PASSIVE, false), 
				new Image(MENU_NEW_TASK_HOVER, false), 
				menuNewTaskTF
			);
		menuNewTask.getChildren().addAll(
				menuNewTaskButton.getNode(),
				menuNewTaskTF
			);
		
		HBox menuNewSite = new HBox();
		menuNewSite.setPrefWidth(MENU_BUTTON_SIZE + MENU_TEXT_FIELD_WIDTH);
		menuNewSite.setPrefHeight(MENU_BUTTON_SIZE);
		TextField menuNewSiteTF = new TextField();
		menuNewSiteTF.setPrefWidth(MENU_TEXT_FIELD_WIDTH);
		menuNewSiteTF.setMinWidth(MENU_TEXT_FIELD_WIDTH);
		menuNewSiteTF.setPrefHeight(MENU_BUTTON_SIZE);
		menuNewSiteTF.setText("What Site would you like to add?");
		menuNewSiteTF.setOnKeyPressed(event -> { if(event.getCode() == KeyCode.ENTER) System.out.println("hello"); });
		MenuButton menuNewSiteButton = new MenuButton(
				MENU_BUTTON_SIZE, 
				0.0, 0.0, 
				new Image(MENU_NEW_SITE_PASSIVE, false), 
				new Image(MENU_NEW_SITE_HOVER, false), 
				menuNewSiteTF
			);
		menuNewSite.getChildren().addAll(
				menuNewSiteButton.getNode(),
				menuNewSiteTF
			);
		
		HBox menuHelp = new HBox();
		menuHelp.setPrefWidth(MENU_BUTTON_SIZE + MENU_TEXT_FIELD_WIDTH);
		menuHelp.setPrefHeight(MENU_BUTTON_SIZE);
		HelpButton menuHelpButton = new HelpButton(
				MENU_BUTTON_SIZE, 
				0.0, 0.0, 
				new Image(MENU_DROPDOWN_PASSIVE, false)
			);
		menuHelp.getChildren().add( menuHelpButton.getNode() );
		
		menuBar.getChildren().addAll(
				menuNewTask,
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
		
		menuNewTaskTF.setOnKeyPressed(event -> { 
			if(event.getCode() == KeyCode.ENTER) {
				menuNewTaskButton.action();
				menuDropdownButton.action();
			}
		});
		menuNewSiteTF.setOnKeyPressed(event -> { 
			if(event.getCode() == KeyCode.ENTER) {
				menuNewSiteButton.action();
				menuDropdownButton.action();
			}
		});
		
//		FillTransition ft = new FillTransition(Duration.seconds(5), r, Color.web("#db5c0d"), Color.ALICEBLUE);
//		ft.setCycleCount(4);
//		ft.setAutoReverse(true);
//		ft.play();
		
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
		
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				background.setWidth(scene.getWidth() + 400);
				background.setHeight(scene.getHeight() + 400);
				
				double rxspeed = 0.1*(rx - (r.getWidth()/2) - r.getX());
				r.setX(r.getX() + rxspeed);
				double ryspeed = 0.1*(ry - 20 - r.getY());
				r.setY(r.getY() + ryspeed);
			}
		}.start();
		
	}
	
	public static void main(String[] args) {
		launch(args);
		System.out.println(Bookmark.getBookmarks());
	}
}
