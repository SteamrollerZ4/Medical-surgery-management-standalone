package edu.surgery.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Registration
{    
    //Enter doctor user entry into login table
    public static void createUser(String username ,String password) {
    	
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
