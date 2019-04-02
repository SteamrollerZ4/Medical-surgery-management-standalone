package edu.surgery.logic;

import java.time.*;
import java.util.*;
import java.sql.*;

public class AppointmentScheduling
{
    //returns true if appointment is available
    public static boolean  checkIfAvailable(Connection conn, Appointment appointment)//Checks if appointment is available
    {
        for(Object t :  getAvailableTimesOnDate(conn, appointment.getAppointmentDate()))
        {
            if(appointment.getAppointmentTime().toLocalTime().equals((LocalTime) t))
                return true;
        }
    
    return false;
    }
    
    //returns array of available times on a particular date
    public static Object[] getAvailableTimesOnDate(Connection conn, java.sql.Date date)
    {
        try{
            String query = "SELECT appointmentTime FROM appointment WHERE appointmentDate = "+"'"+date+"'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<LocalTime> takenTimes = new ArrayList<>();
            while(rs.next())
            {
                takenTimes.add(rs.getTime("appointmentTime").toLocalTime());
            }
            
            //Assuming the medical surgery opens form 08:00 to 17:00 hrs
            //Appointments are allocated on 30 minute intervals
            //Launch is 1 hour at 13:00 hrs
            
            ArrayList<LocalTime> availableTimes = new ArrayList<>(16 - takenTimes.size());
            
            LocalTime time = LocalTime.of(8,0,0);

            for(int i = 0; i < 16; i++)
            {
                if(!takenTimes.contains(time.plusMinutes(i*30)))
                    availableTimes.add(time.plusMinutes(i*30));
            }     
            
            return availableTimes.toArray();
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return null;
    }
    
    public static Integer getAvailableDoctor() 
    {
    	try{
            String query = "SELECT doctorId FROM doctor";
            Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            ArrayList<Integer> doctorIds = new ArrayList<>();

            while(rs.next())
            {
            	doctorIds.add(rs.getInt("doctorId"));
            }     
            //return doctorIds.get((int)Math.round(Math.random())%doctorIds.size());
            return doctorIds.get(doctorIds.size()-1);
        }
        catch(SQLException e){
            System.out.println(e);
        }
    	return null;
    }
    
    //Add appointment to db
    public static void addAppointment(Connection conn, Appointment appointment)
    {
        try{
            //insert appointment into db
            String query = "INSERT INTO appointment (appointmentDate, appointmentTime, patientId, doctorId) VALUES ("+ "'"+appointment.getAppointmentDate()+"'"+","+"'"+appointment.getAppointmentTime()+"'"+","+"'"+appointment.getPatientId()+"'"+","+"'"+appointment.getDoctorId()+"'" +")";
            
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    
    //Cancel an appointment on db
    /*
     * Cancellation are only allowed at least T hours before appointment otherwise client is still billed. 
    */
    public static void cancelAppointment(Appointment appointment)
    {
    //delete appointment in db
    	try {
	    	 String query = "DELETE FROM appointment WHERE appointmentId = '"+appointment.getAppointmentId()+"'";
	         Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
	         stmt.executeUpdate(query);
    	}catch(SQLException e) {
    		System.out.println(e);
    	}
    }
    
    //Returns appointments by doctor id
    public static Object[] getAppointmentsByDoctorId(int doctorId) {
    	try{
            String query = "SELECT * FROM appointment WHERE doctorId = '"+doctorId+"'";
            Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            ArrayList<Appointment> appointmentsList = new ArrayList<>();
            
            while(rs.next()){          	
            	Appointment app =  new Appointment(
                        rs.getDate("appointmentDate"),
                        rs.getTime("appointmentTime"),
                        rs.getInt("patientId"),
                        rs.getInt("doctorId")
                        );
    			app.setAppointmentId(rs.getInt("appointmentId"));
    			//get patient's full name
    			String _query = "SELECT patientName,patientSurname FROM patient WHERE patientId = '"+rs.getInt("patientId")+"'";
	            Statement _stmt = MedicalSurgeryManager.getConnection().createStatement();
	            ResultSet _rs = _stmt.executeQuery(_query);
	            if(_rs.next()) {
	            	app.setPatientFullName(_rs.getString("patientName")+" "+ _rs.getString("patientSurname"));
	            }
    			//get doctor's full name
	            _query = "SELECT doctorName,doctorSurname FROM doctor WHERE doctorId = '"+rs.getInt("doctorId")+"'";
	            _stmt = MedicalSurgeryManager.getConnection().createStatement();
	            _rs = _stmt.executeQuery(_query);
	            if(_rs.next()) {
	            	app.setDoctorFullName(_rs.getString("doctorName")+" "+ _rs.getString("doctorSurname"));
	            }
                appointmentsList.add(app);
                }
            return appointmentsList.toArray();
            }
        catch(SQLException e){
            System.out.println(e);
        }
        return null;
    }
    
    //Return appointments by patient id
    public static Object[] getAppointmentsByPatientId(int patientId) {
    	
    	try{
            String query = "SELECT * FROM appointment WHERE patientId = '"+patientId+"'";
            Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            ArrayList<Appointment> appointmentsList = new ArrayList<>();
            
            while(rs.next()){          	
            	Appointment app =  new Appointment(
                        rs.getDate("appointmentDate"),
                        rs.getTime("appointmentTime"),
                        rs.getInt("patientId"),
                        rs.getInt("doctorId")
                        );
    			app.setAppointmentId(rs.getInt("appointmentId"));
    			//get patient's full name
    			String _query = "SELECT patientName,patientSurname FROM patient WHERE patientId = '"+rs.getInt("patientId")+"'";
	            Statement _stmt =  MedicalSurgeryManager.getConnection().createStatement();
	            ResultSet _rs = _stmt.executeQuery(_query);
	            if(_rs.next()) {
	            	app.setPatientFullName(_rs.getString("patientName")+" "+ _rs.getString("patientSurname"));
	            }
    			//get doctor's full name
	            _query = "SELECT doctorName,doctorSurname FROM doctor WHERE doctorId = '"+rs.getInt("doctorId")+"'";
	            _stmt = MedicalSurgeryManager.getConnection().createStatement();
	            _rs = _stmt.executeQuery(_query);
	            if(_rs.next()) {
	            	app.setDoctorFullName(_rs.getString("doctorName")+" "+ _rs.getString("doctorSurname"));
	            }
                appointmentsList.add(app);
                }
            return appointmentsList.toArray();
            }
        catch(SQLException e){
            System.out.println(e);
        }
        return null;
    }
    
    
    //Returns list of all scheduled appointments
    public static Object[] getAppointments()
    {
        try{
            String query = "SELECT * FROM appointment";
            Statement stmt = MedicalSurgeryManager.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            ArrayList<Appointment> appointmentsList = new ArrayList<>();
            
            while(rs.next()){          	
            	Appointment app =  new Appointment(
                        rs.getDate("appointmentDate"),
                        rs.getTime("appointmentTime"),
                        rs.getInt("patientId"),
                        rs.getInt("doctorId")
                        );
    			app.setAppointmentId(rs.getInt("appointmentId"));
    			//get patient's full name
    			String _query = "SELECT patientName,patientSurname FROM patient WHERE patientId = '"+rs.getInt("patientId")+"'";
	            Statement _stmt = MedicalSurgeryManager.getConnection().createStatement();
	            ResultSet _rs = _stmt.executeQuery(_query);
	            if(_rs.next()) {
	            	app.setPatientFullName(_rs.getString("patientName")+" "+ _rs.getString("patientSurname"));
	            }
    			//get doctor's full name
	            _query = "SELECT doctorName,doctorSurname FROM doctor WHERE doctorId = '"+rs.getInt("doctorId")+"'";
	            _stmt = MedicalSurgeryManager.getConnection().createStatement();
	            _rs = _stmt.executeQuery(_query);
	            if(_rs.next()) {
	            	app.setDoctorFullName(_rs.getString("doctorName")+" "+ _rs.getString("doctorSurname"));
	            }
                appointmentsList.add(app);
                }
            return appointmentsList.toArray();
            }
        catch(SQLException e){
            System.out.println(e);
        }
        return null;
    }
}
