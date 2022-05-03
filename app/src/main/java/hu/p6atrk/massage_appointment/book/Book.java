package hu.p6atrk.massage_appointment.book;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import hu.p6atrk.massage_appointment.R;
import hu.p6atrk.massage_appointment.appointment.AppointmentItem;

public class Book extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private TextView bookDateText;
    private TextView bookTimeText;
    private TextView bookMassageNameText;
    private TextView bookMassagePriceText;
    private TextView bookMassageTimeText;
    private TextView bookMasseurNameText;
    private TextView bookErrorLabel;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;

    private FirebaseAuth fireBaseAuth;
    private FirebaseFirestore firestore;

    ActivityResultLauncher<Intent> MassageResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        bookMassageNameText.setText(intent.getStringExtra("name"));
                        bookMassagePriceText.setText(intent.getIntExtra("price", -1) + " Ft");
                        bookMassageTimeText.setText(intent.getIntExtra("time", -1) + " perc");
                    }
                }
            });

    ActivityResultLauncher<Intent> MasseurResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        bookMasseurNameText.setText(intent.getStringExtra("name"));
                    }
                }
            });



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book);
        // TODO Fix Toolbar
        // TODO minden olyan oldalon ahol csak bejelentkezve lehet a felhasznalo, kidobjon, ha nincs bejelentkezve
        //Toolbar toolbar = findViewById(R.id.aboutToolbar);
        //setSupportActionBar(toolbar);
        bookDateText = findViewById(R.id.bookDateText);
        bookTimeText = findViewById(R.id.bookTimeText);
        bookMassageNameText = findViewById(R.id.bookMassageNameText);
        bookMassagePriceText = findViewById(R.id.bookMassagePriceText);
        bookMassageTimeText = findViewById(R.id.bookMassageTimeText);
        bookMasseurNameText = findViewById(R.id.bookMasseurNameText);
        bookErrorLabel = findViewById(R.id.bookErrorLabel);

        fireBaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public void onBook(View view) {
        if("".contentEquals(bookMassageNameText.getText()) || "".contentEquals(bookMasseurNameText.getText())
                || "".contentEquals(bookDateText.getText()) || "".contentEquals(bookTimeText.getText())) {
            bookErrorLabel.setVisibility(View.VISIBLE);
            return;
        }

        String[] dateStrArr = bookDateText.getText().toString().split("-");
        String[] timeStrArr = bookTimeText.getText().toString().split(":");
        int year = Integer.parseInt(dateStrArr[0]);
        int month = Integer.parseInt(dateStrArr[1]);
        int day = Integer.parseInt(dateStrArr[2]);
        int hour = Integer.parseInt(timeStrArr[0]);
        int minute = Integer.parseInt(timeStrArr[0]);
        Date date = (new GregorianCalendar(year, month, day, hour, minute)).getTime();
        String email = fireBaseAuth.getCurrentUser().getEmail();

        AppointmentItem appointmentItem = new AppointmentItem(
                bookMassageNameText.getText().toString(),
                bookMasseurNameText.getText().toString(),
                date,
                email);
        firestore.collection("appointment").add(appointmentItem);
        Toast.makeText(this, "Sikeres időpontfoglalás!", Toast.LENGTH_LONG).show();
        finish();
    }

    public void goToMassageSelect(View view) {
        Intent intent = new Intent(this, MassageSelect.class);
        MassageResultLauncher.launch(intent);
    }

    public void goToMasseurSelect(View view) {
        Intent intent = new Intent(this, MasseurSelect.class);
        MasseurResultLauncher.launch(intent);
    }

    public void onDatePick(View view) {
        datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        timePickerDialog = new TimePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE),
                true
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, month, dayOfMonth);
        bookDateText.setText(DateFormat.format("yyyy-MM-dd", calendar.getTime()));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Calendar calendar = new GregorianCalendar(-1, -1, -1, hour, minute);
        bookTimeText.setText(DateFormat.format("HH:mm", calendar.getTime()));
    }
}