package hu.p6atrk.massage_appointment.appointment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import hu.p6atrk.massage_appointment.R;
import hu.p6atrk.massage_appointment.book.MassageItem;
import hu.p6atrk.massage_appointment.book.MassageSelect;
import hu.p6atrk.massage_appointment.book.MasseurItem;

public class AppointmentList extends AppCompatActivity {

    AppointmentItemAdapter adapter;
    ArrayList<AppointmentItem> appointmentItems;
    ArrayList<MassageItem> massageItems;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_list);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        appointmentItems = new ArrayList<>();
        massageItems = new ArrayList<>();

        initializeList();
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.appointmentListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AppointmentItemAdapter(this, appointmentItems);
        recyclerView.setAdapter(adapter);
    }

    private void initializeList() {
        // TODO megoldani hogy a user-en belul legyen a user/appointment
        String email = firebaseAuth.getCurrentUser().getEmail();
        firestore.collection("massage").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    massageItems.add(new MassageItem(
                            document.getString("name"),
                            document.getLong("price").intValue(),
                            document.getLong("time").intValue()
                    ));
                }
            }
        }); // TODO Async-el megcsinálni mert lehet nem kapom meg a massageItem-eket mire kellene használni
        firestore.collection("appointment").whereEqualTo("email", email).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    int time = -1, price = -1;
                    for (MassageItem massageItem : massageItems) {
                        if(massageItem.getName().equals(document.getString("massageName"))) {
                            time = massageItem.getTime();
                            price = massageItem.getPrice();
                        }
                    }
                    appointmentItems.add(new AppointmentItem(
                            document.getString("massageName"),
                            document.getString("masseurName"),
                            document.getTimestamp("date").toDate(),
                            document.getString("email"),
                            time,//time,
                            price//price
                    ));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}