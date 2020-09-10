package com.distributed.systems;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class DistributedApplication extends Application {

	public static void main(String[] args) {
		SpringApplication.run(DistributedApplication.class, args);
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/server.fxml"));
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
		primaryStage.setTitle("Server");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
