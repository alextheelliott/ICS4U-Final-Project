package application;

import static application.Constants.LayoutConstants.*;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Main extends Application {
	
	public Pane root;
	public static Scene scene;
	public static Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		root = new Pane();
		scene = new Scene(root, STAGE_WIDTH, STAGE_HEIGHT);
		stage = primaryStage;
		
		/* Creates the FX for the main scene (Application) */
		SceneFX.loadMainSceneFX(root);

		/* Sets scene to default scene */
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
