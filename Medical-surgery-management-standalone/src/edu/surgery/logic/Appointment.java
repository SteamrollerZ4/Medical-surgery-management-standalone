package edu.surgery.logic;

import java.sql.*;

public class Appointment
{
    private int patientId;
    private int appointmentId;
    private int doctorId;
    private Date appointmentDate;
    private Time appointmentTime;
    private String patientFullName;
    private String doctorFullName;
    
    public Appointment(Date appointmentDate, Time appointmentTime, int patientId, int doctorId)
    {
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }
    
    
    //setters
    public void setAppointmentId(int appointmentId)    {
        this.appointmentId = appointmentId;
    }
    public void setPatientId(int patientId) {
    	this.patientId=patientId;
    }
    public void setDoctorId(int patientId) {
    	this.patientId = patientId;
    }
    public void setAppointmentDate(Date appointmentDate) {
    	this.appointmentDate = appointmentDate;
    }
    public void setAppointmentTime(Time time) {
    	this.appointmentTime = appointmentTime;
    }
    public void setPatientFullName(String patientFullName) {
    	this.patientFullName = patientFullName;
    }
    public void setDoctorFullName(String doctorFullName) {
    	this.doctorFullName = doctorFullName;
    }
    //getters
    public int getPatientId(){        return patientId;    }
    public int getAppointmentId ()    {        return appointmentId;    }    
    public int getDoctorId ()    {        return doctorId;    }    
    public Date getAppointmentDate ()    {        return appointmentDate;    }    
    public Time getAppointmentTime ()    {        return appointmentTime;    }
    public String getPatientFullName() {return patientFullName;}
    public String getDoctorFullName() {  	return doctorFullName;    }
}
