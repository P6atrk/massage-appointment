package hu.p6atrk.massage_appointment.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Date;

import hu.p6atrk.massage_appointment.R;

public class AppointmentItem {
    private String massageName;
    private String masseurName;
    private Date date;
    private String email;
    private int time;
    private int price;

    public AppointmentItem(String massageName, String masseurName, Date date, String email) {
        this.massageName = massageName;
        this.masseurName = masseurName;
        this.date = date;
        this.email = email;
    }

    public AppointmentItem(String massageName, String masseurName, Date date, String email, int time, int price) {
        this.massageName = massageName;
        this.masseurName = masseurName;
        this.date = date;
        this.email = email;
        this.time = time;
        this.price = price;
    }

    public String getMassageName() {
        return massageName;
    }

    public void setMassageName(String massageName) {
        this.massageName = massageName;
    }

    public String getMasseurName() {
        return masseurName;
    }

    public void setMasseurName(String masseurName) {
        this.masseurName = masseurName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int _getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int _getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}