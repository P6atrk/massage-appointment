package hu.p6atrk.massage_appointment.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        // TODO toolbarokat kijavitani
        //Toolbar toolbar = findViewById(R.id.aboutToolbar);
        //setSupportActionBar(toolbar);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.masseurSelectRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MasseurItemAdapter(this, masseurItems);
        recyclerView.setAdapter(adapter);

        initializeList();
    }

    // TODO normálisan megcsinálni, szépen kiiratni
    private void initializeList() {
        db.collection("masseur").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    masseurItems.add(new MasseurItem(
                            document.getString("name"),
                            document.getString("desc")
                    ));// TODO toObject-tel megoldani ezt?
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}