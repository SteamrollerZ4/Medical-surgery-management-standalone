package edu.surgery.logic;

import java.sql.*;

public class Appointment
{
    private int patientId;
    private int appointmentId;
    private int doctorId;
    private Date date;
    private Time time;
    
    public Appointment(Date date, Time time, int patientId, int doctorId)
    {
        this.date = date;
        this.time = time;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }
    
    //set appointmentId from db auto generated value
    public Appointment setAppointmentId(int appointmentId)
    {
        this.appointmentId = appointmentId;
        return this;
    }
    
    //getters
    public int getPatientId()
    {
        return patientId;
    }
    
    public int getAppointmentId ()
    {
        return appointmentId;
    }
    
    public int getDoctorId ()
    {
        return doctorId;
    }
    
    public Date getDate ()
    {
        return date;
    }
    
    public Time getTime ()
    {
        return time;
    }
}
