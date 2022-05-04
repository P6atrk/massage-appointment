package hu.p6atrk.massage_appointment.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import hu.p6atrk.massage_appointment.About;
import hu.p6atrk.massage_appointment.R;

// TODO fektetett design

public class HomePage extends AppCompatActivity {
    //private static final String TAG = HomePage.class.getName();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            goToAbout();
        }
    }

    public void goToAbout() {
        Intent aboutIntent = new Intent(this, About.class);
        startActivity(aboutIntent);
    }

    public void goToLogIn(View view) {
        Intent logInIntent = new Intent(this, LogIn.class);
        startActivity(logInIntent);
    }

    public void goToSignUp(View view) {
        Intent signUpIntent = new Intent(this, SignUp.class);
        startActivity(signUpIntent);
    }
}
