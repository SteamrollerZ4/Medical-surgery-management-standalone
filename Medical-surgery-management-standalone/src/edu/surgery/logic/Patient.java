package edu.surgery.logic;

import java.time.LocalDate;

public class Patient
{
    private String patientId;
    private String patientFullName;
    private String patientNationalId;
    private LocalDate patientDOB;
    private String patientCell;
    private String patientTel;
    private String patientEmail;
    private String patientFacebook;
    private String patientTweeter;
    private String patientAddress;
    
    public Patient(String patient)
    {
        //query database for patient's information
    }
    
    //Used to register a new patient
    public Patient(String patipatientAddressent,String patientFullName, String patientNationalId, LocalDate patientDOB, String patientCell,
                    String patientTel, String patientEmail, String patientFacebook, String patientTweeter, String patientAddress)
    {
        this.patientId = patientId;
        this.patientFullName = patientFullName;
        this.patientNationalId = patientNationalId;
        this.patientDOB = patientDOB;
        this.patientCell = patientCell;
        this.patientTel = patientTel;
        this.patientEmail = patientEmail;
        this.patientFacebook = patientFacebook;
        this.patientTweeter = patientTweeter;
        this.patientAddress = patientAddress;
    }
    
    //Getters
    public String getPatStringientId(){
    return patientId;
    }
    
    public String getPatientFullName(){
    return patientFullName;
    }
    
    public String getPatientNationalId(){
    return patientNationalId;
    }
    
    public LocalDate getPatientDOB(){
    return patientDOB;
    }
    
    public String getPatientCell(){
    return patientCell;
    }
    
    public String getPatientTel(){
    return patientTel;
    }
    
    public String getPatientEmail(){
    return patientEmail;
    }
    
    public String getPatientFacebook(){
    return patientFacebook;
    }
    
    public String getPatientTweeter(){
    return patientTweeter;
    }
    
    public String getPatientAddress(){
    return patientAddress;
    }
}
