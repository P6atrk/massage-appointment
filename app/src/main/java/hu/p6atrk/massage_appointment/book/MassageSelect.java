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

public class MassageSelect extends AppCompatActivity {
    private static final String TAG = MassageSelect.class.getName();
    MassageItemAdapter adapter;
    ArrayList<MassageItem> massageItems;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.massage_select);

        massageItems = new ArrayList<>();
        db = FirebaseFirestore.getInstance();

        Toolbar toolbar = findViewById(R.id.aboutToolbar);
        setSupportActionBar(toolbar);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.massageSelectRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MassageItemAdapter(this, massageItems);
        recyclerView.setAdapter(adapter);

        initializeList();
    }

    // TODO normálisan megcsinálni, szépen kiiratni
    private void initializeList() {
        db.collection("massage").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    massageItems.add(new MassageItem(
                            document.getString("name"),
                            document.getLong("price").intValue(),
                            document.getLong("time").intValue()
                    ));// TODO toObject-tel megoldani ezt?
                    //massageItems.add(document.toObject(MassageItem.class));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}