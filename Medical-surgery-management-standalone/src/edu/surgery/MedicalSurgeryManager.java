package edu.surgery;

import java.sql.*;
import java.time.*;
import java.math.BigDecimal;

public class MedicalSurgeryManager
{

    private static Connection conn = null;
    
    public static Connection getConnection(){
        if(conn!=null)
            return conn;
        else{
            conn = createConnection();
            return conn;
        }
    }
    
    //Establish a connection and return it
    private static Connection createConnection()
    {
        Connection conn;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
             return DriverManager.getConnection("jdbc:mysql://localhost/MedicalSurgeryDB","medSurgeryUser","medicals101");
        }
        catch(ClassNotFoundException e)
        {
            System.out.println(e);
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
        return null;
    }
    
    public static void main(String[] args)
    {
        Appointment app = (Appointment) AppointmentScheduling.getAppointments(getConnection())[0];
//        Bill bill = new Bill(app.getDate().toLocalDate().atTime(app.getTime().toLocalTime()), 1 ,1 ,2, "10");
//        Billing.recordBill(app, bill);
        
         for(Object o : Billing.getBillsByDoctorId(1)) 
         {
        	 System.out.println(((Bill) o).getAmount());
         }
         
    }
}
