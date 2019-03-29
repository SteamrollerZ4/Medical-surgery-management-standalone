package edu.surgery.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Registration
{    
	
	@FXML private Label lb_status;
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
	
	
    //Enter doctor,patient or receptionist user entry into login table
    public static void createUserameAndPass(String username ,String password) {	
    	//If the selected username isn't available return
    	if(!checkIfAvailabe(username))
    		return;
    	
    	//Hash password using MD5 algorithm
    	String passwordHash = Cryptography.getMd5(password);    	
    	
    	try {
	    	//insert user into table
	        String query = "INSERT INTO login (userId, passwordHash) VALUES ('"+username+"','"+passwordHash+"')";
	        
	        Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
	        stmt.executeUpdate(query);
	        }
    	catch (SQLException e) {
			System.out.println(e);
		}
    }

    
    //Checks if user name is available
    public static boolean checkIfAvailabe(String username) 
    {
    	 try{
             String query = "SELECT * FROM login where userId = '"+username+"'";
             Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query);             
             
             if(rs.next())
             {
            	 return false;
             }
             
         }
         catch(SQLException e)
         {
             System.out.println(e);
         }
    	 return true;
    }
    
    //Used when patient doesn't know their id
    public static int getPatientIdByNationalId(String nationalId)
    {
    	return 0;
    }
    //Used when patient doesn't know their id
    public static int[] getPatientIdByFullname(String name, String surname)
    {
    	return new int[] {};
    }
    
    
}
