package hu.p6atrk.massage_appointment.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import hu.p6atrk.massage_appointment.R;

public class LogIn extends AppCompatActivity {

    private static final String TAG = LogIn.class.getName();

    private FirebaseAuth mAuth;

    private String email;
    private String password;
    private TextView errorMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        mAuth = FirebaseAuth.getInstance();

        errorMessageTextView = findViewById(R.id.logInErrorTextView);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            finish();
        }
    }

    public void logInButton(View view) {
        email = ((TextView)findViewById(R.id.logInEmailTextView)).getText().toString();
        password = ((TextView)findViewById(R.id.logInPasswordTextView)).getText().toString();

        if(password.equals("") || email.equals("")) {
            errorMessageTextView.setVisibility(View.VISIBLE);
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Sikeres bejelentkezés!", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        errorMessageTextView.setVisibility(View.VISIBLE);
                    }
                });
    }
}
