package edu.surgery.logic;

import edu.surgery.gui.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import edu.surgery.gui.GUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class Registration
{    	
    //Enter doctor,patient or receptionist user entry into login table
    public static boolean createUserameAndPass(String username ,String password) {	
    	//If the selected username isn't available return
    	if(!checkIfAvailabe(username))
    		return false;
    	
    	//Hash password using MD5 algorithm
    	String passwordHash = Cryptography.getMd5(password);    	
    	
    	try {
	    	//insert user into table
	        String query = "INSERT INTO login (userId, passwordHash) VALUES ('"+username+"','"+passwordHash+"')";
	        
	        Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
	        stmt.executeUpdate(query);
	        return true;
	        }
    	catch (SQLException e) {
			System.out.println(e);
		}
    	return false;
    }
    
    //Gets the user's type given the username
    public static String getUserType(String userName) {
    	 try{
    		 //Check if user is of type doctor
             String query = "SELECT doctorId FROM doctor WHERE username = '"+userName+"'";
             Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query);
             if(rs.next())//true if user has been found            	 
            	 return "Doctor";
             
             //Check if user is of type patient
             query = "SELECT patientId FROM patient WHERE username = '"+userName+"'";
             stmt = MedicalSurgeryManager.getConnection().createStatement();
             rs = stmt.executeQuery(query);
             if(rs.next())//true if user has been found            	 
            	 return "Patient";
             
             //Check if user is of type receptionist
             query = "SELECT receptionistId FROM receptionist WHERE username = '"+userName+"'";
             stmt = MedicalSurgeryManager.getConnection().createStatement();
             rs = stmt.executeQuery(query);
             if(rs.next())//true if user has been found            	 
            	 return "Patient";
             
         }
         catch(SQLException e)
         {
             System.out.println(e);
         }
    	 return null;
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
