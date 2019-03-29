package edu.surgery.gui;

import edu.surgery.logic.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("LoginLayout.fxml"));
		primaryStage.setTitle("Login");
		primaryStage.setScene(new Scene(root,300,275));
		primaryStage.show();
	}
	
	public static void main(String args[]) 
	{
		launch(args);
	}
	
	@FXML private TextField tf_user;
	@FXML private TextField tf_pass;
	@FXML private Label lb_status;
	
	public void login(ActionEvent action)
	{
		if(Registration.userLogin(tf_user.getText(), tf_pass.getText())){
			lb_status.setText("login successful");
		}else
			lb_status.setText("login failed");			
	}
}
