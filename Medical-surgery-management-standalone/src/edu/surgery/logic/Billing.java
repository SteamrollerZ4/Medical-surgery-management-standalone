package edu.surgery.logic;

import java.util.*;
import java.time.*;
import java.sql.*;
import java.math.BigDecimal;

public class Billing{
	//insert bill entry into bill table
    public static void recordBill(Appointment appointment, Bill bill){
         try{
            String query =  "INSERT INTO bill (billDate, billTime, doctorId ,patientId, "
        		+ "appointmentId, billAmount) VALUES ("+"'"+
        				appointment.getAppointmentDate()+"','"+
        				appointment.getAppointmentTime()+"','"+
        				appointment.getDoctorId()+"',"+"'"+
        				appointment.getPatientId()+"','"+
        				appointment.getAppointmentId()+"','"+
        				bill.getBillAmount() +"')";
            
            Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
            stmt.executeUpdate(query);
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public static Bill getBillById(int billId) {
    	try {
	        String query = "SELECT * FROM bill WHERE billId = '" + billId + "'";
	        Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        if(rs.next()) 
	        {
	        	Bill bill = new Bill(rs.getDate("billDate"),rs.getTime("billTime"),
	        			rs.getInt("doctorId"),rs.getInt("patientId"),rs.getInt("appointmentId"),rs.getString("billAmount"));
	        	bill.setBillId(rs.getInt("billId"));
	        	return bill;
	        }
        }catch (Exception e) {
			System.out.println(e);
		}
    	return null;
    }
    
    public static BigDecimal getTotalBill(int patientId)    {
        try{
                String query = "SELECT billAmount FROM bill WHERE patientId = '" + patientId+"'";
                Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
                ResultSet rs = stmt.executeQuery(query);                
                
                BigDecimal total = new BigDecimal("0");
                
                while(rs.next())
                {
                	total = total.add(new BigDecimal(rs.getString("billAmount")));
                }
                return total;
            }
            catch(SQLException e)
            {
                System.out.println(e);
            }
        return null;
    }
    
    public static Object[] getBillsByDate(LocalDate date) {
    	ArrayList<Bill> bills = new ArrayList<>();
        try {
	        String query = "SELECT * FROM bill WHERE appointmentDate = "+ "'" +java.sql.Date.valueOf(date) + "'";
	        Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while(rs.next()) 
	        {
	        	Bill bill = new Bill(rs.getDate("billDate"),rs.getTime("billTime"),
	        			rs.getInt("doctorId"),rs.getInt("patientId"),rs.getInt("appointmentId"),rs.getString("billAmount"));
	        	bill.setBillId(rs.getInt("billId"));
	        	bills.add(bill);
	        }
        }catch (Exception e) {
			System.out.println(e);
		}
        if (bills.size()>0)
        	return bills.toArray();
        else
        	return null;
    }
    
    public static Object[] getBillsByDateAndPatientId(LocalDate date, int patientId){
        ArrayList<Bill> bills = new ArrayList<>();
        try {
	        String query = "SELECT * FROM bill WHERE patientId = "+"'"+patientId+"'" +" AND appointmentDate = "+ "'" +java.sql.Date.valueOf(date) + "'";
	        Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while(rs.next()) 
	        {
	        	Bill bill = new Bill(rs.getDate("billDate"),rs.getTime("billTime"),
	        			rs.getInt("doctorId"),rs.getInt("patientId"),rs.getInt("appointmentId"),rs.getString("billAmount"));
	        	bill.setBillId(rs.getInt("billId"));
	        	bills.add(bill);
	        }
        }catch (Exception e) {
			System.out.println(e);
		}
        if (bills.size()>0)
        	return bills.toArray();
        else
        	return null;
    }
    
    
    public static Object[] getBillsByDateAndDoctorId(LocalDate date, int doctorId){
    	ArrayList<Bill> bills = new ArrayList<>();
        try {
	        String query = "SELECT * FROM bill WHERE doctorId = "+"'"+doctorId+"'" +" AND appointmentDate = "+ "'" +java.sql.Date.valueOf(date) + "'";
	        Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while(rs.next()) 
	        {
	        	Bill bill = new Bill(rs.getDate("billDate"),rs.getTime("billTime"),
	        			rs.getInt("doctorId"),rs.getInt("patientId"),rs.getInt("appointmentId"),rs.getString("billAmount"));
	        	bill.setBillId(rs.getInt("billId"));
	        	bills.add(bill);
	        }
        }catch (Exception e) {
			System.out.println(e);
		}
        if (bills.size()>0)
        	return bills.toArray();
        else
        	return null;
    }
    
    public static Object[] getBillsByPatientId(int patientId) {
    	ArrayList<Bill> bills = new ArrayList<>();
        try {
	        String query = "SELECT * FROM bill WHERE patientId = '" + patientId + "'";	        
	        Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while(rs.next()) 
	        {
	        	Bill bill = new Bill(rs.getDate("billDate"),rs.getTime("billTime"),
	        			rs.getInt("doctorId"),rs.getInt("patientId"),rs.getInt("appointmentId"),rs.getString("billAmount"));
	        	bill.setBillId(rs.getInt("billId"));
	        	//get patient's full name
    			String _query = "SELECT patientName,patientSurname FROM patient WHERE patientId = '"+rs.getInt("patientId")+"'";
	            Statement _stmt = MedicalSurgeryManager.getConnection().createStatement();
	            ResultSet _rs = _stmt.executeQuery(_query);
	            if(_rs.next()) {
	            	bill.setPatientFullName(_rs.getString("patientName")+" "+ _rs.getString("patientSurname"));
	            }
    			//get doctor's full name
	            _query = "SELECT doctorName,doctorSurname FROM doctor WHERE doctorId = '"+rs.getInt("doctorId")+"'";
	            _stmt = MedicalSurgeryManager.getConnection().createStatement();
	            _rs = _stmt.executeQuery(_query);
	            if(_rs.next()) {
	            	bill.setDoctorFullName(_rs.getString("doctorName")+" "+ _rs.getString("doctorSurname"));
	            }
 	        	bills.add(bill);
	        }
        }
        catch (SQLException e) {
			System.out.println(e);
		}        
        if (bills.size()>0)
        	return bills.toArray();
        else
        	return null;
    }
    
    //get all bills in the system
    public static Object[] getBills() 
    {
    	 ArrayList<Bill> bills = new ArrayList<>();
         try {
 	        String query = "SELECT * FROM bill";	        
 	        Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
 	        ResultSet rs = stmt.executeQuery(query);
 	        
 	        while(rs.next()) 
 	        {
 	        	Bill bill = new Bill(rs.getDate("billDate"),rs.getTime("billTime"),
 	        			rs.getInt("doctorId"),rs.getInt("patientId"),rs.getInt("appointmentId"),rs.getString("billAmount"));
 	        	bill.setBillId(rs.getInt("billId"));
 	        	//get patient's full name
    			String _query = "SELECT patientName,patientSurname FROM patient WHERE patientId = '"+rs.getInt("patientId")+"'";
	            Statement _stmt = MedicalSurgeryManager.getConnection().createStatement();
	            ResultSet _rs = _stmt.executeQuery(_query);
	            if(_rs.next()) {
	            	bill.setPatientFullName(_rs.getString("patientName")+" "+ _rs.getString("patientSurname"));
	            }
    			//get doctor's full name
	            _query = "SELECT doctorName,doctorSurname FROM doctor WHERE doctorId = '"+rs.getInt("doctorId")+"'";
	            _stmt = MedicalSurgeryManager.getConnection().createStatement();
	            _rs = _stmt.executeQuery(_query);
	            if(_rs.next()) {
	            	bill.setDoctorFullName(_rs.getString("doctorName")+" "+ _rs.getString("doctorSurname"));
	            }
 	        	bills.add(bill);
 	        }
         }
         catch (SQLException e) {
 			System.out.println(e);
 		}
         if (bills.size()>0)
         	return bills.toArray();
         else
         	return null;
    }
    
    public static Object[] getBillsByDoctorId(int doctorId)
    {
        ArrayList<Bill> bills = new ArrayList<>();
        try {
	        String query = "SELECT * FROM bill WHERE doctorId = '" + doctorId + "'";	        
	        Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while(rs.next()) 
	        {
	        	Bill bill = new Bill(rs.getDate("billDate"),rs.getTime("billTime"),
	        			rs.getInt("doctorId"),rs.getInt("patientId"),rs.getInt("appointmentId"),rs.getString("billAmount"));
	        	bill.setBillId(rs.getInt("billId"));
	        	bills.add(bill);
	        }
        }
        catch (SQLException e) {
			System.out.println(e);
		}
        if (bills.size()>0)
        	return bills.toArray();
        else
        	return null;
    }
}

