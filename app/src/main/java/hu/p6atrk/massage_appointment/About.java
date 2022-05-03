package hu.p6atrk.massage_appointment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import hu.p6atrk.massage_appointment.appointment.AppointmentList;
import hu.p6atrk.massage_appointment.book.Book;
import hu.p6atrk.massage_appointment.home.HomePage;

public class About extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        Toolbar toolbar = findViewById(R.id.aboutToolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null) {
            goToHomePage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu( Menu menu ) {
        getMenuInflater().inflate(R.menu.about, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.aboutMenuItemLogOut:
                mAuth.signOut();
                finish();
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goToHomePage() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void goToBook(View view) {
        Intent intent = new Intent(this, Book.class);
        startActivity(intent);
    }

    public void goToAppointmentList(View view) {
        Intent intent = new Intent(this, AppointmentList.class);
        startActivity(intent);
    }
}