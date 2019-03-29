package edu.surgery.logic;

import java.time.LocalDate;

public class Receptionist
{
    private int receptionistId;
    private String receptionistName;
    private String receptionistSurname;
    private String receptionistNationalId;
    private LocalDate receptionistDOB;
    private String receptionistCell;
    private String receptionistTel;
    private String receptionistEmail;
    private String receptionistFacebook;
    private String receptionistTweeter;
    private String receptionistAddress;
    private String receptionistCity;
    private String receptionistCountry;
    private String username;
    
    public Receptionist(String receptionistName, String receptionistSurname, String receptionistNationalId, LocalDate receptionistDOB, String receptionistCell,String receptionistAddress,
    		String receptionistCity, String receptionistCountry,String username)
    {
        this.receptionistName = receptionistName;
        this.receptionistSurname = receptionistSurname;
        this.receptionistNationalId = receptionistNationalId;
        this.receptionistDOB = receptionistDOB;
        this.receptionistCell = receptionistCell;
        this.receptionistTel = "unknown";
        this.receptionistEmail = "unknown";
        this.receptionistFacebook = "unknow";
        this.receptionistTweeter = "unknow";
        this.receptionistAddress = receptionistAddress;
        this.receptionistCity = receptionistCity;
        this.receptionistCountry = receptionistCountry;
        this.username = username;
    }
    
    public Receptionist(String receptionistName, String receptionistSurname, String receptionistNationalId, LocalDate receptionistDOB,
    		String receptionistCell, String receptionistTel, String receptionistEmail, String receptionistFacebook, 
    		String receptionistTweeter, String receptionistAddress, String receptionistCity, String receptionistCountry,String username)
    {
        this(receptionistName, receptionistSurname, receptionistNationalId, receptionistDOB, receptionistCell, receptionistAddress,receptionistCity, receptionistCountry,username);
        
        this.receptionistTel = receptionistTel;
        this.receptionistEmail = receptionistEmail;
        this.receptionistFacebook = receptionistFacebook;
        this.receptionistTweeter = receptionistTweeter;
        
    }
    //Setter
    public void setReceptionistId(int receptionistId) 
    {
    	this.receptionistId = receptionistId;
    }
    
    //Getters
    public int getReceptionistId() {    	return receptionistId;	}
	public String getReceptionistName() {		return receptionistName;	}
	public String getReceptionistSurname() {		return receptionistSurname;	}
	public String getReceptionistNationalId() {		return receptionistNationalId;	}
	public LocalDate getReceptionistDOB() {		return receptionistDOB;	}
	public String getReceptionistCell() {		return receptionistCell;	}
	public String getReceptionistTel() {		return receptionistTel;	}
	public String getReceptionistEmail() {		return receptionistEmail;	}
	public String getReceptionistFacebook() {		return receptionistFacebook;	}
	public String getReceptionistTweeter() {		return receptionistTweeter;	}
	public String getReceptionistAddress() {		return receptionistAddress;	}
	public String getReceptionistCity() {	return receptionistCity;	}
	public String getReceptionistCountry() {	return receptionistCountry;	}
}
