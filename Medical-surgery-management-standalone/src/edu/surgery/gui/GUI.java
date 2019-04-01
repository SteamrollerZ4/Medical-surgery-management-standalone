package edu.surgery.gui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUI extends Application {
	private	static Stage window;
	private static String currentUserName;
	
	@FXML private Label lb_status;
	@FXML private TextField tf_user;
	@FXML private TextField tf_pass;	
	@FXML private TextField tf_pass_ori;
	@FXML private TextField tf_pass_con;
	@FXML private TextField tf_name;
	@FXML private TextField tf_surname;
	@FXML private TextField tf_nationalId;
	@FXML private DatePicker dp_dob;
	@FXML private DatePicker dp_appointment_date;
	@FXML private TextField tf_cell;
	@FXML private TextField tf_tel;
	@FXML private TextField tf_email;
	@FXML private TextField tf_fb;
	@FXML private TextField tf_tweeter;
	@FXML private TextField tf_address;
	@FXML private TextField tf_city;
	@FXML private TextField tf_country;
	@FXML private ChoiceBox<String> cb_type;
	@FXML private ChoiceBox<String> cb_usernameOrNatId;
	@FXML private ChoiceBox<String> cb_time;
	@FXML private TextField tf_username;
	@FXML private GridPane gp_main;
	@FXML private TableView tv_available;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		window = primaryStage;
		window.setTitle("Login");
		window.setScene(new Scene(root,300,275));
		window.show();
	}
	
	public static void main(String args[]) 
	{
		launch(args);
	}
	
	//go to user creation page i.e signup button callback
	public void signup(ActionEvent action)
	{
		try {
			Parent root = FXMLLoader.load(getClass().getResource("NewUser.fxml"));
			window.setTitle("Create user");
			window.setScene(new Scene(root,720,560));
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//create user button callback
	public void create(ActionEvent action) {
		switch(cb_type.getValue()) 
		{
			case "Doctor":
				Doctor doc = new Doctor(
						tf_name.getText(), 
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
						currentUserName);
				createDoctor(doc);
				break;
			case "Receptionist":
				Receptionist rec = new Receptionist(
						tf_name.getText(), 
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
						currentUserName);
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
						currentUserName);
				createPatient(pat);
				break;			
		}
	}
	
	//sigin button callback
	public void signin(ActionEvent action)	{
		if(userLogin(tf_user.getText(), tf_pass.getText())){
			currentUserName=tf_user.getText();
			lb_status.setText("login successful");
			//switch scene to appropriate dashboard
			switch(Registration.getUserType(tf_user.getText())) {
				case "Doctor":
					//show doctor dashboard
					try {
						
						Parent root = FXMLLoader.load(getClass().getResource("DashboardDoctor.fxml"));
						window.setTitle("Dashboard");
						window.setScene(new Scene(root,720,560));
					}catch (Exception e) {
						System.out.println(e);
					}		
					break;
				case "Patient":
					//show patient dashboard
					try {
						
						Parent root = FXMLLoader.load(getClass().getResource("DashboardPatient.fxml"));
						window.setTitle("Dashboard");
						window.setScene(new Scene(root,720,560));
					}catch (Exception e) {
						System.out.println(e);
					}
					break;
				case "Receptionist":
					//show receptionist dashboard
					try {
						
						Parent root = FXMLLoader.load(getClass().getResource("DashboardReceptionist.fxml"));
						window.setTitle("Dashboard");
						window.setScene(new Scene(root,720,560));
					}catch (Exception e) {
						System.out.println(e);
					}
					break;
			}
			
		}else {
			lb_status.setText("login failed");
		}		
	}
	
	//returns true if loggin was successful. Not a button callback function.
	public static boolean userLogin(String username ,String password){
		try {
			if(Registration.checkIfAvailabe(username))//If specified user does exist, throw exception
	    		throw new UserNotFound();
			
			//Select password hash
			String query = "SELECT passwordHash from login where userId = '"+username+"'";
			Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if(rs.next()) {
            	if (rs.getString("passwordHash").equals(Cryptography.getMd5(password))) 
            	{
            		if(doesUserHaveAcc(username)) {
            			System.out.println("User exists");
            		}else {
            			System.out.println("User doesn't exist");
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
	
	//Check if specified user has an account
	public static boolean doesUserHaveAcc(String username) {
   	 try{
         String query_1 = "SELECT * FROM doctor where username = '"+username+"'";
         String query_2 = "SELECT * FROM receptionist where username = '"+username+"'";
         String query_3 = "SELECT * FROM patient where username = '"+username+"'";
         
         Statement stmt_1 = MedicalSurgeryManager.getConnection().createStatement();
         Statement stmt_2 = MedicalSurgeryManager.getConnection().createStatement();
         Statement stmt_3 = MedicalSurgeryManager.getConnection().createStatement();
         
         ResultSet rs_1 = stmt_1.executeQuery(query_1);
         ResultSet rs_2 = stmt_2.executeQuery(query_2);
         ResultSet rs_3 = stmt_3.executeQuery(query_3);
         
         if(rs_1.next() || rs_2.next() || rs_3.next() )
         {
        	 return true;
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
            		+ "doctorTel, doctorEmail, doctorFacebook, doctorTweeter, doctorAddress, doctorCity, doctorCountry, username) VALUES ("+
            		"'"+doctor.getDoctorName()+"','"+doctor.getDoctorSurname()+"','"+doctor.getDoctorNationalId()+"',"+
            		"'"+doctor.getDoctorDOB()+"','"+doctor.getDoctorCell()+"','"+doctor.getDoctorTel() +"','"+doctor.getDoctorEmail() +
            		"','"+doctor.getDoctorFacebook() +"','"+doctor.getDoctorTweeter()+"','"+doctor.getDoctorAddress() +
            		"','"+doctor.getDoctorCity() +"','"+doctor.getDoctorCountry() +"','"+doctor.getUsername() +"')";
            
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
            		+ "patientTel, patientEmail, patientFacebook, patientTweeter, patientAddress, patientCity, patientCountry, username) VALUES ("+ 
        "'"+patient.getPatientName()+"','"+patient.getPatientSurname()+"','"+patient.getPatientNationalId()+"',"+"'"+patient.getPatientDOB()+
        "','"+patient.getPatientCell()+"','"+patient.getPatientTel() +"','"+patient.getPatientEmail() +"','"+patient.getPatientFacebook() 
        +"','"+patient.getPatientTweeter() +"','"+patient.getPatientAddress() +"','"+patient.getPatientCity() +"','"+patient.getPatientCountry() 
        +"','"+patient.getUsername()+"')";
            
            Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
            stmt.executeUpdate(query);
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    
    //Create username and password only and open the details capturing form.
	public void createUP(ActionEvent actionEvent) 
	{
		if(tf_pass_ori.getText().isEmpty() || tf_pass_con.getText().isEmpty() || tf_username.getText().isEmpty()){
			lb_status.setText("No field can be left empty");
		}else {
		
			if(tf_pass_ori.getText().equals(tf_pass_con.getText())) {
				if(!Registration.createUserameAndPass(tf_username.getText(),  tf_pass_ori.getText()))
					return;
				
				try {
					currentUserName = tf_username.getText();
					Parent root = FXMLLoader.load(getClass().getResource("NewDocRecPat.fxml"));
					window.setTitle("Capture user details");
					window.setScene(new Scene(root,720,560));
				}catch (Exception e) {
					System.out.println(e);
				}		    	
			}
			else{
				lb_status.setText("Password doesn't match");
			}
		}
	}
	
	
	//Check avialable times on particular date, callback method
	public void checkAvailableOnDate(ActionEvent action) {
		//add available times to the date selection drop down menu
		AppointmentScheduling.getAvailableTimesOnDate(MedicalSurgeryManager.getConnection(),
				java.sql.Date.valueOf(dp_appointment_date.getValue()));
	}
	
	//Check booked appointments
	public void displayAppointments(ActionEvent action)
	{
		//switch scene
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ViewAppointments.fxml"));
			window.setTitle("Active appointments");
			window.setScene(new Scene(root,720,560));
		}catch (Exception e) {
			System.out.println(e);
		}
		
		ObservableList<Appointment> available = FXCollections.observableArrayList();
		
		for(Object a : AppointmentScheduling.getAvailableTimesOnDate(MedicalSurgeryManager.getConnection(), java.sql.Date.valueOf(dp_appointment_date.getValue())))
			available.add((Appointment) a);
		
		//Patient id column
		TableColumn<Appointment, Integer> patientId = new TableColumn<>("PatientId");
		patientId.setMinWidth(200);
		patientId .setCellValueFactory(new PropertyValueFactory<>("patientId"));
		//Doctor id column
		TableColumn<Appointment, Integer> doctorId = new TableColumn<>("DoctorId");
		doctorId.setMinWidth(200);
		doctorId .setCellValueFactory(new PropertyValueFactory<>("doctorId"));
		//Date column
		TableColumn<Appointment, java.sql.Date> date = new TableColumn<>("Date");
		date.setMinWidth(200);
		date .setCellValueFactory(new PropertyValueFactory<>("doctorId"));
		//Time column
		TableColumn<Appointment, java.sql.Time> time = new TableColumn<>("Time");
		time.setMinWidth(200);
		time .setCellValueFactory(new PropertyValueFactory<>("time"));
		
		tv_available.setItems(available);
		
	}
	//Close the appointmentview window and go back to dashboard
	public void closeAppintmentView(ActionEvent action) {
		
	}
	
	//Schedule appointment, create appointment button callback
	public void scheduleAppointment(ActionEvent action){
		AppointmentScheduling.addAppointment(MedicalSurgeryManager.getConnection(),		
			new Appointment(
					java.sql.Date.valueOf(dp_appointment_date.getValue()),
					java.sql.Time.valueOf(cb_time.getValue()),
					Registration.getPatientIdByUsername(currentUserName),
					AppointmentScheduling.getAvailableDoctor()
					));
	}
	
	//show to appointment scheduling window
	public void showAppointmentScheduling(ActionEvent action) 
	{
		try {			
			Parent root = FXMLLoader.load(getClass().getResource("ScheduleAppointment.fxml"));
			window.setTitle("Dashboard");
			window.setScene(new Scene(root,720,560));
		}catch (Exception e) {
			System.out.println(e);
		}		
	}
	
	//Log out of system i.e. show login screen
	public void logout(ActionEvent action) 
	{
		
	}
}
