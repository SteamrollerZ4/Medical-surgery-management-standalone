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
    
    public void setBillId(int billId) 
    {
    	this.billId = billId;
    }
    
    public void addAmount(String billAmount)
    {
        this.billAmount.add(new BigDecimal(billAmount));
    }
    
    public void subtractAmount(String billAmount)
    {
        this.billAmount.subtract(new BigDecimal(billAmount));
    }
    
    public BigDecimal getAmount()
    {
        return billAmount;
    }
}
