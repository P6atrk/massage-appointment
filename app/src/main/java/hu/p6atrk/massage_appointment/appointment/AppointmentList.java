package hu.p6atrk.massage_appointment.appointment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import hu.p6atrk.massage_appointment.R;
import hu.p6atrk.massage_appointment.book.MassageItem;
import hu.p6atrk.massage_appointment.home.LogIn;

public class AppointmentList extends AppCompatActivity {
    private static final String TAG = LogIn.class.getName();

    private AppointmentItemAdapter adapter;
    private ArrayList<AppointmentItem> appointmentItems;
    private ArrayList<MassageItem> massageItems;

    private String email;

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    private final int REQUEST_APPOINTMENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_list);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        appointmentItems = new ArrayList<>();
        massageItems = new ArrayList<>();

        email = firebaseAuth.getCurrentUser().getEmail();

        RecyclerView recyclerView = findViewById(R.id.appointmentListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AppointmentItemAdapter(this, appointmentItems);
        recyclerView.setAdapter(adapter);

        initializeList(getIntent().getBooleanExtra("showOldAppointments", false));
    }

    private void initializeList(boolean showOldAppointments) {
        firestore.collection("massage").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    massageItems.add(new MassageItem(
                            document.getString("name"),
                            document.getLong("price").intValue(),
                            document.getLong("time").intValue()
                    ));
                }
                firestore.collection("appointment")
                        .whereEqualTo("email", email)
                        .get().addOnCompleteListener(task2 -> {
                    if (task2.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task2.getResult()) {
                            int time = -1, price = -1;
                            for (MassageItem massageItem : massageItems) {
                                if (massageItem.getName().equals(document.getString("massageName"))) {
                                    time = massageItem.getTime();
                                    price = massageItem.getPrice();
                                }
                            }
                            appointmentItems.add(new AppointmentItem(
                                    document.getString("massageName"),
                                    document.getString("masseurName"),
                                    document.getString("date"),
                                    document.getString("email"),
                                    time,
                                    price,
                                    document.getId()
                            ));
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_APPOINTMENT) {
            finish();
        }
    }
}