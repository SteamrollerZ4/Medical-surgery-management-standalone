package edu.surgery.logic;

import java.time.LocalDateTime;
import java.math.BigDecimal;//For accurate representation of currency values

public class Bill{
    private LocalDateTime dateTime;
    private int billId;
    private int patientId;
    private int doctorId;
    private int appointmentId;
    private BigDecimal billAmount;
    
    public Bill(LocalDateTime dateTime,int doctorId ,int patientId, int appointmentId ,String billAmount){
        this.dateTime = dateTime;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.billAmount = new BigDecimal(billAmount);
    }

	public LocalDateTime getDateTime() {
		return dateTime;
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

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
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
}
