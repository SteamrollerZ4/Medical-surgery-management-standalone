package edu.surgery.logic;

import java.util.*;
import java.time.*;
import java.sql.*;
import java.math.BigDecimal;

public class Billing{

    public static void recordBill(Appointment appointment, Bill bill){
         try{
            //insert appointment into db
            String query = "INSERT INTO bill (billDate, billTime, doctorId ,patientId, appointmentId, billAmount) VALUES ("+ "'"+appointment.getDate()+"','"+appointment.getTime()+"','"+appointment.getDoctorId()+"',"+"'"+appointment.getPatientId()+"','"+appointment.getAppointmentId()+"','"+bill.getAmount() +"')";
            
            Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
            stmt.executeUpdate(query);
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    
    public static BigDecimal getTotalBill(int patientId)
    {
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
    
    public static Object[] getBillsByDateAndPatientId(LocalDate date, int patientId){
        ArrayList<Bill> bills = new ArrayList<>();
        
        String query = "SELECT amount FROM bill WHERE patientId = "+"'"+patientId+"'" +" AND appointmentDate = "+ "'" +java.sql.Date.valueOf(date) + "'";
        
        return bills.toArray();
    }
    
    public static Object[] getBillsByDateAndDoctorId(LocalDate data, int patientId){
        ArrayList<Bill> bills = new ArrayList<>();
        
        return bills.toArray();
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
	        	Bill bill = new Bill(LocalDateTime.of(rs.getDate("billDate").toLocalDate(), rs.getTime("billTime").toLocalTime()),rs.getInt("doctorId"),rs.getInt("patientId"),rs.getInt("appointmentId"),rs.getString("billAmount"));
	        	bill.setBillId(rs.getInt("billId"));
	        	bills.add(bill);
	        }
        }
        catch (SQLException e) {
			System.out.println(e);
		}
        finally {
        	        	
        }
        
        return bills.toArray();
    }

    
}
