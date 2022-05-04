package hu.p6atrk.massage_appointment.book;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

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

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.massageSelectRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MassageItemAdapter(this, massageItems);
        recyclerView.setAdapter(adapter);

        initializeList();
    }

    private void initializeList() {
        db.collection("massage")
                .orderBy("price")
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    massageItems.add(new MassageItem(
                            document.getString("name"),
                            document.getLong("price").intValue(),
                            document.getLong("time").intValue()
                    ));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}