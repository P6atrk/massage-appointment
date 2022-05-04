package hu.p6atrk.massage_appointment.appointment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import hu.p6atrk.massage_appointment.R;

public class AppointmentItem implements Parcelable {
    private String massageName;
    private String masseurName;
    private String date;
    private String email;
    private int time;
    private int price;
    private String id;

    public AppointmentItem(String massageName, String masseurName, String date, String email) {
        this.massageName = massageName;
        this.masseurName = masseurName;
        this.date = date;
        this.email = email;
    }

    public AppointmentItem(String massageName, String masseurName, String date, String email, int time, int price, String id) {
        this.massageName = massageName;
        this.masseurName = masseurName;
        this.date = date;
        this.email = email;
        this.time = time;
        this.price = price;
        this.id = id;
    }

    protected AppointmentItem(Parcel in) {
        massageName = in.readString();
        masseurName = in.readString();
        email = in.readString();
        time = in.readInt();
        price = in.readInt();
        id = in.readString();
    }

    public static final Creator<AppointmentItem> CREATOR = new Creator<AppointmentItem>() {
        @Override
        public AppointmentItem createFromParcel(Parcel in) {
            return new AppointmentItem(in);
        }

        @Override
        public AppointmentItem[] newArray(int size) {
            return new AppointmentItem[size];
        }
    };

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public String _getId() {
        return id;
    }

    public void _setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(massageName);
        parcel.writeString(masseurName);
        parcel.writeString(email);
        parcel.writeInt(time);
        parcel.writeInt(price);
        parcel.writeString(id);
    }
}