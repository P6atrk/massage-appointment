package hu.p6atrk.massage_appointment.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import hu.p6atrk.massage_appointment.R;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    private String fullName;
    private String email;
    private String tel;
    private String password;

    private TextView signUpFullNameTextView;
    private TextView signUpEmailTextView;
    private TextView signUpTelTextView;
    private TextView signUpPasswordTextView;

    private TextView errorMessageLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        errorMessageLabel = findViewById(R.id.signUpErrorLabel);
        signUpFullNameTextView = findViewById(R.id.signUpFullNameTextView);
        signUpEmailTextView = findViewById(R.id.signUpEmailTextView);
        signUpTelTextView = findViewById(R.id.signUpTelTextView);
        signUpPasswordTextView = findViewById(R.id.signUpPasswordTextView);


        Toolbar toolbar = findViewById(R.id.signUpToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null) {
            finish();
        }
    }

    public void onSignUp(View view) {
        fullName = signUpFullNameTextView.getText().toString();
        email = signUpEmailTextView.getText().toString();
        tel = signUpTelTextView.getText().toString();
        password = signUpPasswordTextView.getText().toString();

        if(fullName.equals("") || tel.equals("") || password.equals("") || email.equals("")) {
            errorMessageLabel.setVisibility(View.VISIBLE);
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        firestore.collection("user").add(new User(fullName, tel, email));
                        Toast.makeText(this, "Sikeres regisztráció!", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        errorMessageLabel.setVisibility(View.VISIBLE);
                    }
                });
    }
}