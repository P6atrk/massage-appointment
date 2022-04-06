package hu.p6atrk.massage_appointment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private String fullName;
    private String email;
    private String tel;
    private String password;

    private TextView errorMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        Toolbar toolbar = findViewById(R.id.signUpToolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            finish();
        }
    }

    public void onSignUp(View view) {
        fullName = ((TextView)findViewById(R.id.signUpFullNameTextView)).getText().toString();
        email = ((TextView)findViewById(R.id.signUpEmailTextView)).getText().toString();
        tel = ((TextView)findViewById(R.id.signUpTelTextView)).getText().toString();
        password = ((TextView)findViewById(R.id.signUpPasswordTextView)).getText().toString();

        if(password.equals("") || email.equals("")) {
            errorMessageTextView.setVisibility(View.VISIBLE);
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        finish();
                    } else {
                        errorMessageTextView.setVisibility(View.VISIBLE);
                    }
                });
    }
}