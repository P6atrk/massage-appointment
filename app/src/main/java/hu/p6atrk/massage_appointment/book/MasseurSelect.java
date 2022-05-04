package hu.p6atrk.massage_appointment.book;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

import hu.p6atrk.massage_appointment.R;

public class MasseurSelect extends AppCompatActivity {
    MasseurItemAdapter adapter;
    ArrayList<MasseurItem> masseurItems;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.masseur_select);

        masseurItems = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.masseurSelectRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MasseurItemAdapter(this, masseurItems);
        recyclerView.setAdapter(adapter);

        initializeList();
    }

    private void initializeList() {
        db.collection("masseur")
                .orderBy("name")
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    masseurItems.add(new MasseurItem(
                            document.getString("name"),
                            document.getString("desc")
                    ));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}