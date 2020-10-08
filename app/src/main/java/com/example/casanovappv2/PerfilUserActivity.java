package com.example.casanovappv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilUserActivity extends AppCompatActivity {

    //LLAMAMOS A LOS COMPONENTES DE XML
    private TextView mTextViewNombres;
    private TextView mTextViewApellidos;
    private TextView mTextViewNDni;
    private TextView mTextViewEdad;
    private TextView mTextViewTelefono;
    private TextView mTextViewCorreo;
    private TextView mTextViewContraseña;
    private Button mButtonCerrarSesion;
    //CREAMOS LAS VARIABLES DE LOS DATOS
    private String Nombres;
    private String Apellidos;
    private String NDni;
    private String Edad;
    private String Telefono;
    private String Correo;
    private String Contraseña;
    //CREAMOS VARIABLES DE FIREBASE: AUTH Y DATABASE
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);
        mTextViewNombres=(TextView)findViewById(R.id.mTextViewNombres);
        mTextViewApellidos=(TextView)findViewById(R.id.mTextViewApellidos);
        mTextViewNDni=(TextView)findViewById(R.id.mTextViewNDni);
        mTextViewEdad=(TextView)findViewById(R.id.mTextViewEdad);
        mTextViewTelefono=(TextView)findViewById(R.id.mTextViewTelefono);
        mTextViewCorreo=(TextView)findViewById(R.id.mTextViewCorreo);
        mTextViewContraseña=(TextView)findViewById(R.id.mTextViewContraseña);
        mButtonCerrarSesion=(Button)findViewById(R.id.mButtonCerrarSesion);

        mAuth=FirebaseAuth.getInstance();
        mDatabase=FirebaseDatabase.getInstance().getReference();

        mButtonCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
        getUserInfo();
    }
    private void getUserInfo(){
        String Id=mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Nombres=snapshot.child("Nombres").getValue().toString();
                    Apellidos=snapshot.child("Apellidos").getValue().toString();
                    NDni=snapshot.child("NDni").getValue().toString();
                    Edad=snapshot.child("Edad").getValue().toString();
                    Telefono=snapshot.child("Telefono").getValue().toString();
                    Correo=snapshot.child("Correo").getValue().toString();
                    Contraseña=snapshot.child("Contraseña").getValue().toString();
                    mTextViewNombres.setText(Nombres);
                    mTextViewApellidos.setText(Apellidos);
                    mTextViewNDni.setText(NDni);
                    mTextViewEdad.setText(Edad);
                    mTextViewTelefono.setText(Telefono);
                    mTextViewCorreo.setText(Correo);
                    mTextViewContraseña.setText(Contraseña);
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}