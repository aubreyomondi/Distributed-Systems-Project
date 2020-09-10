package com.distributed.systems;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DistributedApplication extends Application {

	public static void main(String[] args) {
		SpringApplication.run(DistributedApplication.class, args);
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/client.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
		primaryStage.setTitle("Client");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
