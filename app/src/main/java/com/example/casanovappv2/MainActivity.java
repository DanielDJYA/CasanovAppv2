package com.example.casanovappv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.casanovappv2.Adapters.MensajeAdapters;
import com.example.casanovappv2.models.Mensaje;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editTextMensaje;
    private Button btnCrearDato;

    //CREAMOS VARIABLES DE FIREBASE:DATABASE
    DatabaseReference mDatabase;

    private MensajeAdapters mAdapter;
    private RecyclerView mRecyclerView;
    private ArrayList<Mensaje> mMensajesList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextMensaje=(EditText)findViewById(R.id.editTextMensaje);
        btnCrearDato=(Button)findViewById(R.id.btnCrearDato);
        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerViewMensajes);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mDatabase= FirebaseDatabase.getInstance().getReference();

        btnCrearDato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje=editTextMensaje.getText().toString();
                mDatabase.child("Mensajes").push().child("texto").setValue(mensaje);
            }
        });
        getMensajesFromFirebase();
    }

    private void getMensajesFromFirebase(){
        mDatabase.child("Mensajes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if(datasnapshot.exists()){
                    mMensajesList.clear();
                    for (DataSnapshot ds: datasnapshot.getChildren()) {
                        String texto=ds.child("texto").getValue().toString();

                        mMensajesList.add(new Mensaje(texto));

                    }
                    mAdapter = new MensajeAdapters(mMensajesList, R.layout.mensajeview);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}