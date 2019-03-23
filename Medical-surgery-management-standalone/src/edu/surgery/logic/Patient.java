package edu.surgery.logic;

import java.time.LocalDate;

public class Patient
{
    private String patientId;
    private String patientName;
    private String patientSurname;
    private String patientNationalId;
    private LocalDate patientDOB;
    private String patientCell;
    private String patientTel;
    private String patientEmail;
    private String patientFacebook;
    private String patientTweeter;
    private String patientAddress;
    private String patientCity;
    private String patientCountry;
    
    public Patient(String patientName,String patientSurname ,String patientNationalId, LocalDate patientDOB, String patientCell,
                    String patientTel, String patientEmail, String patientFacebook, String patientTweeter, String patientAddress, 
                    String patientCity, String patientCountry)
    {
    	this(patientName, patientSurname, patientNationalId, patientDOB, patientCell, patientAddress, patientCity, patientCountry);        
        
        this.patientTel = patientTel;
        this.patientEmail = patientEmail;
        this.patientFacebook = patientFacebook;
        this.patientTweeter = patientTweeter;
        this.patientAddress = patientAddress;
       
    }
    
    
    //Used to register a new patient
    public Patient(String patientName,String patientSurname ,String patientNationalId, LocalDate patientDOB, String patientCell,
                    String patientAddress, String patientCity, String patientCountry)
    {
        this.patientName = patientName;
        this.patientSurname = patientSurname;
        this.patientNationalId = patientNationalId;
        this.patientDOB = patientDOB;
        this.patientCell = patientCell;
        
        this.patientTel = "unknown";
        this.patientEmail = "unknown";
        this.patientFacebook = "unknown";
        this.patientTweeter = "unknown";
        
        this.patientAddress = patientAddress;
        this.patientCity = patientCity;
        this.patientCountry = patientCountry;
    }
    
    //Setter
    public void setPatientId(String patientId) {this.patientId = patientId;}
    
    //Getters
    public String getPatStringientId(){    return patientId;    }    
    public String getPatientName(){    return patientName;    }
    public String getPatientSurname(){    return patientSurname;    }
    public String getPatientNationalId(){    return patientNationalId;    }    
    public LocalDate getPatientDOB(){    return patientDOB;    }    
    public String getPatientCell(){    return patientCell;    }    
    public String getPatientTel(){    return patientTel;	}    
    public String getPatientEmail(){    return patientEmail;	}    
    public String getPatientFacebook(){    return patientFacebook;    }    
    public String getPatientTweeter(){    return patientTweeter;    }    
    public String getPatientAddress(){    return patientAddress;    }
	public String getPatientId() {		return patientId;	}
	public String getPatientCity() {		return patientCity;	}
	public String getPatientCountry() {		return patientCountry;	}

}
