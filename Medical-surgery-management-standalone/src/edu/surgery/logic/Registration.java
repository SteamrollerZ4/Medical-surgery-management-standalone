package edu.surgery.logic;

import java.sql.SQLException;
import java.sql.Statement;

public class Registration
{    
    //Enter doctor user entry into login table
    public static void createUser(Doctor doctor, String password) {
    	StringBuilder sb = new StringBuilder("doc");
    	sb.append(doctor.getDoctorId());
    	sb.append("med");
    	
    	//Hash password using MD5 algorithm
    	String passwordHash = Cryptography.getMd5(password);
    	
    	try {
	    	//insert user into table
	        String query = "INSERT INTO login (userId, passwordHash) VALUES ('"+
	    	sb.toString()+"','"+passwordHash+"')";
	        
	        Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
	        stmt.executeUpdate(query);
	        }
    	catch (SQLException e) {
			System.out.println(e);
		}
    }
    public static void createUser(Patient patient) {
    	
    }    
    public static void createUser(Receptionist receptionist) {
    	
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
    public void createDoctor(Doctor doctor) 
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
    public void createPatient(Patient patient) 
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
