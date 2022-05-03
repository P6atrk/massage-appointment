package hu.p6atrk.massage_appointment.home;

import com.google.firebase.firestore.CollectionReference;

public class User {
    private String fullName;
    private String tel;
    private String email;

    public User(String fullName, String tel, String email) {
        this.fullName = fullName;
        this.tel = tel;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
