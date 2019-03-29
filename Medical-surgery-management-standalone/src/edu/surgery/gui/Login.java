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
	private String currentUser;
	
	@FXML private Label lb_status;
	@FXML private TextField tf_user;
	@FXML private TextField tf_pass;	
	@FXML private TextField tf_pass_ori;
	@FXML private TextField tf_pass_con;
	@FXML private TextField tf_name;
	@FXML private TextField tf_surname;
	@FXML private TextField tf_nationalId;
	@FXML private DatePicker dp_dob;
	@FXML private TextField tf_cell;
	@FXML private TextField tf_tel;
	@FXML private TextField tf_email;
	@FXML private TextField tf_fb;
	@FXML private TextField tf_tweeter;
	@FXML private TextField tf_address;
	@FXML private TextField tf_city;
	@FXML private TextField tf_country;
	@FXML private ChoiceBox<String> cb_type;
	
	public void signup(ActionEvent action)
	{
		switch(cb_type.getValue()) 
		{
			case "Doctor":
				Doctor doc = new Doctor(tf_name.getText(), 
						tf_surname.getText(), 
						tf_nationalId.getText(), 
						dp_dob.getValue(), 
						tf_cell.getText(), 
						tf_tel.getText(), 
						tf_email.getText(), 
						tf_fb.getText(), 
						tf_tweeter.getText(), 
						tf_address.getText(), 
						tf_city.getText(), 
						tf_country.getText(),
						tf_user.getText());
				createDoctor(doc);
				break;
			case "Receptionist":
				Receptionist rec = new Receptionist(tf_name.getText(), 
						tf_surname.getText(), 
						tf_nationalId.getText(), 
						dp_dob.getValue(), 
						tf_cell.getText(), 
						tf_tel.getText(), 
						tf_email.getText(), 
						tf_fb.getText(), 
						tf_tweeter.getText(), 
						tf_address.getText(), 
						tf_city.getText(), 
						tf_country.getText(),
						tf_user.getText());
				createReceptionist(rec);
				break;
			case "Patient":
				Patient pat = new Patient(tf_name.getText(), 
						tf_surname.getText(), 
						tf_nationalId.getText(), 
						dp_dob.getValue(), 
						tf_cell.getText(), 
						tf_tel.getText(), 
						tf_email.getText(), 
						tf_fb.getText(), 
						tf_tweeter.getText(), 
						tf_address.getText(), 
						tf_city.getText(), 
						tf_country.getText(),
						tf_user.getText());
				createPatient(pat);
				break;			
		}
	}
	
	public void signin(ActionEvent action)
	{
		if(userLogin(tf_user.getText(), tf_pass.getText())){
			currentUser=tf_user.getText();
			lb_status.setText("login successful");
		}else {
			lb_status.setText("login failed");
		}		
	}
	
	public static boolean userLogin(String username ,String password)
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
            		return true;
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

	return false;
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
	
	//Insert entry into receptionist table
	public static void createReceptionist(Receptionist receptionist) 
	{
		 try{
	            String query = "INSERT INTO receptionist (receptionistName, receptionistSurname, receptionistNationalId ,receptionistDOB, receptionistCell, "
	            		+ "receptionistTel, receptionistEmail, receptionistFacebook, receptionistTweeter, receptionistAddress, receptionistCity, receptionistCountry) VALUES ("+
	            		"'"+receptionist.getReceptionistName()+"','"+receptionist.getReceptionistSurname()+"','"+receptionist.getReceptionistNationalId()+"',"+
	            		"'"+receptionist.getReceptionistDOB()+"','"+receptionist.getReceptionistCell()+"','"+receptionist.getReceptionistTel() +"','"+receptionist.getReceptionistEmail() +
	            		"','"+receptionist.getReceptionistFacebook() +"','"+receptionist.getReceptionistTweeter()+"','"+receptionist.getReceptionistAddress() +
	            		"','"+receptionist.getReceptionistCity() +"','"+receptionist.getReceptionistCountry() +"')";
	            
	            Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
	            stmt.executeUpdate(query);
	        }
	        catch(SQLException e){
	            System.out.println(e);
	        }
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
