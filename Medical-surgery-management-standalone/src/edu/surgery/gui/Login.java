package edu.surgery.gui;

import edu.surgery.logic.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Login extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25,25,25,25));
		
		Text sceneTitle = new Text("Please login: ");
		sceneTitle.setFont(Font.font("Tahoma",FontWeight.NORMAL, 20));
		Label userName = new Label("User name: ");
		TextField userTextFied = new TextField();
		Label password = new Label("Password: ");
		PasswordField passwordField = new PasswordField();
		
		grid.add(sceneTitle, 0, 0, 2, 1);
		grid.add(userName,0,1);
		grid.add(userTextFied, 1, 1);
		grid.add(password,0,2);
		grid.add(passwordField, 1, 2);
		
//		grid.setGridLinesVisible(true);
		
		Button btn = new Button();
		btn.setText("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);
		
		final Text actionTarget = new Text();
		grid.add(actionTarget, 1, 6);
		
		
		btn.setOnAction(event ->
		{
			actionTarget.setFill(Color.FIREBRICK);
			actionTarget.setText("Sign in button pressed");
			
		});
		
		Scene scene = new Scene(grid,300,275);
		
		primaryStage.setTitle("Login page");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String args[]) 
	{
		launch(args);
	}

}
