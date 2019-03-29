package edu.surgery.gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.surgery.logic.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

		primaryStage.setTitle("Login");
		primaryStage.setScene(new Scene(root,300,275));
		primaryStage.show();
	}
	
	public static void main(String args[]) 
	{
		launch(args);
	}
	
	@FXML private Label lb_status;
	@FXML private TextField tf_user;
	@FXML private TextField tf_pass;	

	
	public void signin(ActionEvent action)
	{
		switch(userLogin(tf_user.getText(), tf_pass.getText())){
		case "doc":
			lb_status.setText("login successful");
			break;
		case "pat":
			lb_status.setText("login successful");
			break;
		default:
			lb_status.setText("login failed");
		}		
	}
	
	public static String userLogin(String username ,String password)
	{
		try {
			if(Registration.checkIfAvailabe(username))//If specified user does exist, throw exception
	    		throw new UserNotFound();
			
			//Select password hash
			String query = "SELECT type from login where userId = '"+username+"' AND passwordHash = " +Cryptography.getMd5(password)+"'";
			Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()) {
            	if (rs.next()) 
            	{
            		if(doesUserHaveAcc(username)) {
            			//continue to system and display appropriate information
            		}else {
            			//show account creation form i.e. NewUser.fxml
            		}
            	}
            	else
            		throw new PasswordWrong();
        	}
			
		}catch(UserNotFound e) {
			System.out.println(e);
		}
		catch (PasswordWrong e) {
			System.out.println(e);
		}
		catch (SQLException e) {
			System.out.println(e);
		}

	return null;
	}
	
	public static boolean doesUserHaveAcc(String username) {
   	 try{
         String query_1 = "SELECT * FROM doctor where username = '"+username+"'";
         String query_2 = "SELECT * FROM receptionist where username = '"+username+"'";
         String query_3 = "SELECT * FROM patient where username = '"+username+"'";
         Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
         
         ResultSet rs_1 = stmt.executeQuery(query_1);
         ResultSet rs_2 = stmt.executeQuery(query_2);
         ResultSet rs_3 = stmt.executeQuery(query_3);
         
         if(rs_1.next() || rs_2.next() || rs_3.next() )
         {
        	 return false;
         }
         
     }
     catch(SQLException e)
     {
         System.out.println(e);
     }
		return false;
	}
	
	//Insert entry into doctor table
    public static void createDoctor(Doctor doctor) 
    {
        try{
            String query = "INSERT INTO doctor (doctorName, doctorSurname, doctorNationalId ,doctorDOB, doctorCell, "
            		+ "doctorTel, doctorEmail, doctorFacebook, doctorTweeter, doctorAddress, doctorCity, doctorCountry) VALUES ("+
            		"'"+doctor.getDoctorName()+"','"+doctor.getDoctorSurname()+"','"+doctor.getDoctorNationalId()+"',"+
            		"'"+doctor.getDoctorDOB()+"','"+doctor.getDoctorCell()+"','"+doctor.getDoctorTel() +"','"+doctor.getDoctorEmail() +
            		"','"+doctor.getDoctorFacebook() +"','"+doctor.getDoctorTweeter()+"','"+doctor.getDoctorAddress() +
            		"','"+doctor.getDoctorCity() +"','"+doctor.getDoctorCountry() +"')";
            
            Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
            stmt.executeUpdate(query);
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    
    //Insert entry into patient table
    public static void createPatient(Patient patient) 
    {
        try{
            String query = "INSERT INTO patient (patientName, patientSurname, patientNationalId ,patientDOB, patientCell, "
            		+ "patientTel, patientEmail, patientFacebook, patientTweeter, patientAddress, patientCity, patientCountry) VALUES ("+ 
        "'"+patient.getPatientName()+"','"+patient.getPatientSurname()+"','"+patient.getPatientNationalId()+"',"+"'"+patient.getPatientDOB()+
        "','"+patient.getPatientCell()+"','"+patient.getPatientTel() +"','"+patient.getPatientEmail() +"','"+patient.getPatientFacebook() 
        +"','"+patient.getPatientTweeter() +"','"+patient.getPatientAddress() +"','"+patient.getPatientCity() +"','"+patient.getPatientCountry() 
        +"')";
            
            Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
            stmt.executeUpdate(query);
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
}
