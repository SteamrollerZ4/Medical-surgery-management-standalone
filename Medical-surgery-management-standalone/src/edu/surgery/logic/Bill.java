package edu.surgery.logic;

import java.sql.Time;
import java.sql.Date;
import java.math.BigDecimal;//For accurate representation of currency values

public class Bill{
    private Date billDate;
    private Time billTime;
    private int billId;
    private int patientId;
    private int doctorId;
    private int appointmentId;
    private BigDecimal billAmount;
    private String patientFullName;
    private String doctorFullName;
    
    public Bill(Date billDate,Time billTime,int doctorId ,int patientId, int appointmentId ,String billAmount){
        this.billDate = billDate;
        this.billTime = billTime;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.billAmount = new BigDecimal(billAmount);
    }


	public int getBillId() {
		return billId;
	}

	public int getPatientId() {
		return patientId;
	}

	public int getDoctorId() {
		return doctorId;
	}

	public int getAppointmentId() {
		return appointmentId;
	}

	public BigDecimal getBillAmount() {
		return billAmount;
	}
	
	public String getPatientFullName() {
		return patientFullName;
	}


	public String getDoctorFullName() {
		return doctorFullName;
	}
	public Date getBillDate() {
		return billDate;
	}


	public Time getBillTime() {
		return billTime;
	}
	

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}


	public void setBillTime(Time billTime) {
		this.billTime = billTime;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public void setBillAmount(BigDecimal billAmount) {
		this.billAmount = billAmount;
	}

	public void setPatientFullName(String patientFullName) {
		this.patientFullName = patientFullName;
	}


	public void setDoctorFullName(String doctorFullName) {
		this.doctorFullName = doctorFullName;
	}

}
