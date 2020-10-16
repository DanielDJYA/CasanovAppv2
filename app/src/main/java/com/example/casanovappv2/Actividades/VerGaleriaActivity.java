package com.example.casanovappv2.Actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.casanovappv2.Adapters.GaleriaAdapter;
import com.example.casanovappv2.R;
import com.example.casanovappv2.models.Galeria;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VerGaleriaActivity extends AppCompatActivity {
    RecyclerView rv_galeria;
    GaleriaAdapter adapter;
    ArrayList<Galeria> galeriaArrayList;
    LinearLayoutManager mLayautManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_galeria);

        mLayautManager = new LinearLayoutManager(this);
        mLayautManager.setReverseLayout(true);
        mLayautManager.setStackFromEnd(true);
        rv_galeria = findViewById(R.id.rv);
        rv_galeria.setLayoutManager(mLayautManager);

        galeriaArrayList = new ArrayList<>();
        adapter = new  GaleriaAdapter(galeriaArrayList,getApplicationContext());
        rv_galeria.setAdapter(adapter);

        FirebaseDatabase database =  FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Fotos_subidas");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    galeriaArrayList.removeAll(galeriaArrayList);
                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                        Galeria gal = snapshot1.getValue(Galeria.class);
                        galeriaArrayList.add(gal);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}