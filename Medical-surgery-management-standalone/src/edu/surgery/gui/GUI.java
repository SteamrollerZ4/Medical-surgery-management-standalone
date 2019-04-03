package edu.surgery.gui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;

import edu.surgery.logic.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
	@FXML private ChoiceBox<String> cb_times;
	@FXML private TextField tf_username;
	@FXML private GridPane gp_main;
	@FXML private TableView<Appointment> tv_available;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		window = primaryStage;
		window.setTitle("Login");
		Scene scene = new Scene(root,1280,720);
		scene.getStylesheets().add("/edu/surgery/gui/main.css");		
		window.setScene(scene);
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
			Scene scene = new Scene(root,1280,720);
			scene.getStylesheets().add("/edu/surgery/gui/main.css");		
			window.setScene(scene);
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
	
	void selectDashBoard(String currentUserName) {
		switch(Registration.getUserType(currentUserName)) {
		case "Doctor":
			//show doctor dashboard
			try {				
				Parent root = FXMLLoader.load(getClass().getResource("DashboardDoctor.fxml"));
				window.setTitle("Dashboard");
				Scene scene = new Scene(root,1280,720);
				scene.getStylesheets().add("/edu/surgery/gui/main.css");		
				window.setScene(scene);
			}catch (Exception e) {
				System.out.println(e);
			}		
			break;
		case "Patient":
			//show patient dashboard
			try {
				
				Parent root = FXMLLoader.load(getClass().getResource("DashboardPatient.fxml"));
				window.setTitle("Dashboard");
				Scene scene = new Scene(root,1280,720);
				scene.getStylesheets().add("/edu/surgery/gui/main.css");		
				window.setScene(scene);
				
			}catch (Exception e) {
				System.out.println(e);
			}
			break;
		case "Receptionist":
			//show receptionist dashboard
			try {
				
				Parent root = FXMLLoader.load(getClass().getResource("DashboardReceptionist.fxml"));
				window.setTitle("Dashboard");
				Scene scene = new Scene(root,1280,720);
				scene.getStylesheets().add("/edu/surgery/gui/main.css");		
				window.setScene(scene);
				
			}catch (Exception e) {
				System.out.println(e);
			}
			break;
		}
	}
	
	//sigin button callback
	public void signin(ActionEvent actionEvent)	{
		if(tf_user.getText().isEmpty() || tf_pass.getText().isEmpty()) {
			lb_status.setText("all fields are required");
			return;
		}
		if(userLogin(tf_user.getText(), tf_pass.getText())){
			currentUserName=tf_user.getText();
			lb_status.setText("login successful");
			//switch scene to appropriate dashboard
			if(doesUserHaveAcc(currentUserName))
				selectDashBoard(currentUserName);
			else {
				try {
					Parent root = FXMLLoader.load(getClass().getResource("NewDocRecPat.fxml"));
					window.setTitle("Capture user details");
					Scene scene = new Scene(root,1280,720);
					scene.getStylesheets().add("/edu/surgery/gui/main.css");		
					window.setScene(scene);
				}catch (Exception e) {
					System.out.println(e);
				}
			}
			
		}else {
			lb_status.setText("login failed");
		}		
	}
	
	//returns true if loggin was successful. Not a button callback method.
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
	            		+ "receptionistTel, receptionistEmail, receptionistFacebook, receptionistTweeter, receptionistAddress, receptionistCity, receptionistCountry, username) VALUES ("+
	            		"'"+receptionist.getReceptionistName()+"','"+receptionist.getReceptionistSurname()+"','"+receptionist.getReceptionistNationalId()+"',"+
	            		"'"+receptionist.getReceptionistDOB()+"','"+receptionist.getReceptionistCell()+"','"+receptionist.getReceptionistTel() +"','"+receptionist.getReceptionistEmail() +
	            		"','"+receptionist.getReceptionistFacebook() +"','"+receptionist.getReceptionistTweeter()+"','"+receptionist.getReceptionistAddress() +
	            		"','"+receptionist.getReceptionistCity() +"','"+receptionist.getReceptionistCountry() +"','"+receptionist.getUsername()+"')";
	            
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
    
    //Create username and password only, and open the details capturing form.
	public void createUP(ActionEvent actionEvent) 
	{
		if(tf_pass_ori.getText().isEmpty() || tf_pass_con.getText().isEmpty() || tf_username.getText().isEmpty()){
			lb_status.setText("No field can be left empty");
		}else {
		
			if(tf_pass_ori.getText().equals(tf_pass_con.getText())) {
				if(!Registration.createUserameAndPass(tf_username.getText(),  tf_pass_ori.getText()))
					{	
						lb_status.setText("Username is taken.");
						return;
					}
				
				try {
						currentUserName = tf_username.getText();
						Parent root = FXMLLoader.load(getClass().getResource("NewDocRecPat.fxml"));
						window.setTitle("Capture user details");
						Scene scene = new Scene(root,1280,720);
						scene.getStylesheets().add("/edu/surgery/gui/main.css");		
						window.setScene(scene);
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
	
	//Schedule appointment, create appointment button callback
	public void scheduleAppointment(ActionEvent action){
		AppointmentScheduling.addAppointment(MedicalSurgeryManager.getConnection(),		
			new Appointment(
					java.sql.Date.valueOf(dp_appointment_date.getValue()),
					java.sql.Time.valueOf(cb_times.getValue()),
					Registration.getPatientIdByUsername(currentUserName),
					AppointmentScheduling.getAvailableDoctor()
					));
	}
	
	//show to appointments view window
	public void displayAppointments(ActionEvent action) 
	{
		ObservableList<Appointment> apps = FXCollections.observableArrayList();
		
		switch (Registration.getUserType(currentUserName)) {
		case "Doctor":
			for(Object a : AppointmentScheduling.getAppointmentsByDoctorId(Registration.getDoctorIdByUsername(currentUserName)))
				apps.add((Appointment)a);
			break;
		case "Patient":
			for(Object a : AppointmentScheduling.getAppointmentsByPatientId(Registration.getPatientIdByUsername(currentUserName)))
				apps.add((Appointment)a);
			break;
		case "Receptionist":
			for(Object a : AppointmentScheduling.getAppointments())
				apps.add((Appointment)a);
			break;
		default:
			break;
		}

	
		//Patient full name column
		TableColumn<Appointment, String> patientFullName = new TableColumn<>("Patient");
		patientFullName.setMinWidth(200);
		patientFullName .setCellValueFactory(new PropertyValueFactory<>("patientFullName"));
		//Doctor full name column
		TableColumn<Appointment, String> doctorFullName = new TableColumn<>("Doctor");
		doctorFullName.setMinWidth(200);
		doctorFullName .setCellValueFactory(new PropertyValueFactory<>("doctorFullName"));
		//Date column
		TableColumn<Appointment, java.sql.Date> date = new TableColumn<>("Date");
		date.setMinWidth(200);
		date.setCellValueFactory(new PropertyValueFactory<>("appointmentDate"));
		//Time column
		TableColumn<Appointment, java.sql.Time> time = new TableColumn<>("Time");
		time.setMinWidth(200);
		time.setCellValueFactory(new PropertyValueFactory<>("appointmentTime"));
		//Appointment id column
		TableColumn<Appointment, Integer> id = new TableColumn<>("AppointmentId");
		id.setMinWidth(200);
		id .setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
		tv_available = new TableView<Appointment>();
		tv_available.setItems(apps);
		tv_available.getColumns().addAll(patientFullName,doctorFullName,date,time,id);
		GridPane root = new GridPane();
		
		root.setVgap(20);
		root.setHgap(20);
		root.setAlignment(Pos.CENTER);
		
		root.setConstraints(tv_available, 0, 0);
		Button btnClose = new Button("Close");
		btnClose.setOnAction(e->{
			//go back to previous window
			selectDashBoard(currentUserName);
		});
		root.setConstraints(btnClose, 1, 1);
		root.getChildren().addAll(tv_available,btnClose);
		Scene scene = new Scene(root,1280,720);
		scene.getStylesheets().add("/edu/surgery/gui/main.css");		
		window.setScene(scene);
	}
	
	//show to appointment scheduling window
	public void showAppointmentScheduling(ActionEvent action) 
	{
		//create and display scheduling window at run time
		GridPane root = new GridPane();
		
		root.setVgap(20);
		root.setHgap(20);
		root.setAlignment(Pos.CENTER);
		
		Label lb_username = new Label("username: ");
		root.setConstraints(lb_username, 0, 0);
		
		TextField tf_username = new TextField();
		tf_username.setPromptText("username");
		root.setConstraints(tf_username, 1, 0);
		
		//if current user is patient, set patient id automatically
		if(Registration.getUserType(currentUserName).equals("Patient")) {
			tf_username.setText(currentUserName);
			tf_username.setDisable(true);
		}
		
		Label lb_date = new Label("date: ");
		root.setConstraints(lb_date, 0, 1);
		
		DatePicker dp = new DatePicker();
		root.setConstraints(dp, 1, 1);
		
		Label lb_time = new Label("time: ");
		root.setConstraints(lb_time, 0, 2);
		
		ChoiceBox<String> cb = new ChoiceBox<>();
		root.setConstraints(cb, 1, 2);
		
		dp.setOnAction(e->{
			cb.getItems().clear();
			for(Object t : AppointmentScheduling.getAvailableTimesOnDate(
					MedicalSurgeryManager.getConnection(),
					java.sql.Date.valueOf(dp.getValue()))) {
					cb.getItems().add(t.toString()+":00");
			}});
		
		dp.setValue(LocalDate.now());
		for(Object t : AppointmentScheduling.getAvailableTimesOnDate(
				MedicalSurgeryManager.getConnection(),
				java.sql.Date.valueOf(dp.getValue()))) {
				cb.getItems().add(t.toString()+":00");
		}
		
		Button btnClose = new Button("Close");
		Button btnSubmit = new Button("Submit");
		
		root.setConstraints(btnSubmit, 2, 3);
		root.setConstraints(btnClose, 3, 3);
		
		btnSubmit.setOnAction(e->{
			//go back to previous window
			AppointmentScheduling.addAppointment(MedicalSurgeryManager.getConnection(),		
					new Appointment(
							java.sql.Date.valueOf(dp.getValue()),
							java.sql.Time.valueOf(cb.getValue()),
							Registration.getPatientIdByUsername(tf_username.getText()),
							AppointmentScheduling.getAvailableDoctor()
							));
			cb.getItems().clear();
			for(Object t : AppointmentScheduling.getAvailableTimesOnDate(
					MedicalSurgeryManager.getConnection(),
					java.sql.Date.valueOf(dp.getValue()))) {
					cb.getItems().add(t.toString()+":00");
			}
		});
		
		btnClose.setOnAction(e->{
			//go back to previous window
			selectDashBoard(currentUserName);
		});
		root.getChildren().addAll(lb_username,tf_username,lb_date,dp,lb_time,cb,btnClose,btnSubmit);
				Scene scene = new Scene(root,1280,720);
				scene.getStylesheets().add("/edu/surgery/gui/main.css");		
				window.setScene(scene);
	}
	
	//return to default dashboard, callback method
	public void homeDashboard(ActionEvent action) {
		selectDashBoard(currentUserName);
	}
	
	//Log out of system i.e. show login screen
	public void logout(ActionEvent action) 
	{
		currentUserName=null;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
			window.setTitle("Login");
			Scene scene = new Scene(root,1280,720);
			scene.getStylesheets().add("/edu/surgery/gui/main.css");		
			window.setScene(scene);			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	//show bills for patient
	public void showBills(ActionEvent event)
	{
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ViewBills.fxml"));
			window.setTitle("Bills");
			Scene scene = new Scene(root,1280,720);
			scene.getStylesheets().add("/edu/surgery/gui/main.css");		
			window.setScene(scene);			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	//show receipts for patient
	public void showReceipts(ActionEvent event)
	{
		try {
			Parent root = FXMLLoader.load(getClass().getResource("ViewReceipts.fxml"));
			window.setTitle("Receipts");
			Scene scene = new Scene(root,1280,720);
			scene.getStylesheets().add("/edu/surgery/gui/main.css");		
			window.setScene(scene);			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}
