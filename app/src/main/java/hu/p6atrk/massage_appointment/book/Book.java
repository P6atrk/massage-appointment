package hu.p6atrk.massage_appointment.book;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import hu.p6atrk.massage_appointment.R;

public class Book extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book);

        Toolbar toolbar = findViewById(R.id.aboutToolbar);
        setSupportActionBar(toolbar);
    }

    public void onBook(View view) {
    }

    public void goToMasseurSelect(View view) {
    }

    public void goToMassageSelect(View view) {
    }
}