package edu.surgery.logic;

import java.sql.*;
import java.time.*;
import java.math.BigDecimal;

/*
 * This class is a singleton
 * */

public class MedicalSurgeryManager
{

    private static Connection conn = null;
    
    //Return a connection to client
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
}
