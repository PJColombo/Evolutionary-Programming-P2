package application;

import java.io.IOException;
import java.util.ArrayList;


import application.model.p1.model.genetic_algorithm.GeneticAlgorithm;
import application.view.ChartController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {

	private Stage primaryStage;
	private AnchorPane root;

	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/chartView.fxml"));
			this.root = loader.load();
			Scene scene = new Scene(this.root,1200,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			this.primaryStage.setScene(scene);
			this.primaryStage.setTitle("Genetic Algorithm App");

			ChartController controller = loader.getController();
			controller.setMainApp(this);

			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
