package edu.surgery;

import java.time.*;
import java.util.*;
import java.sql.*;

public class AppointmentScheduling
{
    //returns true if appointment is available
    public static boolean  checkIfAvailable(Connection conn, Appointment appointment)//Checks if appointment is available
    {
        for(Object t :  getAvailableTimesOnDate(conn, appointment.getDate()))
        {
            if(appointment.getTime().toLocalTime().equals((LocalTime) t))
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
    
    //Add appointment to db
    public static void addAppointment(Connection conn, Appointment appointment)
    {
        try{
            //insert appointment into db
            String query = "INSERT INTO appointment (appointmentDate, appointmentTime, patientId, doctorId) VALUES ("+ "'"+appointment.getDate()+"'"+","+"'"+appointment.getTime()+"'"+","+"'"+appointment.getPatientId()+"'"+","+"'"+appointment.getDoctorId()+"'" +")";
            
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
    public static void cancelAppointment(Connection conn, Appointment appointment)
    {
    //delete appointment in db
    }
    
    //Returns list of all scheduled appointments
    public static Object[] getAppointments(Connection conn)
    {
        try{
            String query = "SELECT * FROM appointment";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            ArrayList<Appointment> appointmentsList = new ArrayList<>();
            
            while(rs.next()){
                appointmentsList.add(
                new Appointment(
                rs.getDate("appointmentDate"),
                rs.getTime("appointmentTime"),
                rs.getInt("patientId"),
                rs.getInt("doctorId")
                ).setAppointmentId(rs.getInt("appointmentId")));
                }
            return appointmentsList.toArray();
            }
        catch(SQLException e){
            System.out.println(e);
        }
        return null;
    }
}
