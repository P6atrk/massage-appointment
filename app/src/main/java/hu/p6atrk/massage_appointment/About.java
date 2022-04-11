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

import hu.p6atrk.massage_appointment.book.Book;
import hu.p6atrk.massage_appointment.book.MassageSelect;

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
        /* TODO ha az about oldalon vagyok akkor kidobjon az alkalmazásból, mikor a vissza gombot nyomom.
         */
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

    private void goToHomePage() {
        Intent homePageIntent = new Intent(this, HomePage.class);
        startActivity(homePageIntent);
    }

    public void goToBook(View view) {
        Intent bookIntent = new Intent(this, Book.class);
        startActivity(bookIntent);
    }
}