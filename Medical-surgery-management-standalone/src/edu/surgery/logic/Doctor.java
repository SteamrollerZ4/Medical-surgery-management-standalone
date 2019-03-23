package edu.surgery.logic;

import java.time.LocalDate;

public class Doctor
{
    private int doctorId;
    private String doctorName;
    private String doctorSurname;
    private String doctorNationalId;
    private LocalDate doctorDOB;
    private String doctorCell;
    private String doctorTel;
    private String doctorEmail;
    private String doctorFacebook;
    private String doctorTweeter;
    private String doctorAddress;
    private String doctorCity;
    private String doctorCountry;
    
    public Doctor(String doctorName, String doctorSurname, String doctorNationalId, LocalDate doctorDOB, String doctorCell,String doctorAddress)
    {
        this.doctorName = doctorName;
        this.doctorSurname = doctorSurname;
        this.doctorNationalId = doctorNationalId;
        this.doctorDOB = doctorDOB;
        this.doctorCell = doctorCell;
        this.doctorTel = "unknown";
        this.doctorEmail = "unknown";
        this.doctorFacebook = "unknow";
        this.doctorTweeter = "unknow";
        this.doctorAddress = doctorAddress;
    }
    
    public Doctor(String doctorName, String doctorSurname, String doctorNationalId, LocalDate doctorDOB,
    		String doctorCell, String doctorTel, String doctorEmail, String doctorFacebook, 
    		String doctorTweeter, String doctorAddress)
    {
        this(doctorName, doctorSurname, doctorNationalId, doctorDOB, doctorCell, doctorAddress);
        
        this.doctorTel = doctorTel;
        this.doctorEmail = doctorEmail;
        this.doctorFacebook = doctorFacebook;
        this.doctorTweeter = doctorTweeter;
        
    }
    //Setter
    public void setDoctorId(int doctorId) 
    {
    	this.doctorId = doctorId;
    }
    
    //Getters
    public int getDoctorId() {    	return doctorId;	}
	public String getDoctorName() {		return doctorName;	}
	public String getDoctorSurname() {		return doctorSurname;	}
	public String getDoctorNationalId() {		return doctorNationalId;	}
	public LocalDate getDoctorDOB() {		return doctorDOB;	}
	public String getDoctorCell() {		return doctorCell;	}
	public String getDoctorTel() {		return doctorTel;	}
	public String getDoctorEmail() {		return doctorEmail;	}
	public String getDoctorFacebook() {		return doctorFacebook;	}
	public String getDoctorTweeter() {		return doctorTweeter;	}
	public String getDoctorAddress() {		return doctorAddress;	}
	public String getDoctorCity() {	return doctorCity;	}
	public String getDoctorCountry() {	return doctorCountry;	}
	

}
