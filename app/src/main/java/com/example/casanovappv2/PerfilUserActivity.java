package com.example.casanovappv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class PerfilUserActivity extends AppCompatActivity {
    //CREAMOS LAS VARIABLES DE LOS DATOS QUE IRAN EN EL NAV_HEADER_MAIN.XML
    private String Nombres;
    private String Apellidos;
    private String NDni;
    private String Edad;
    private String Telefono;
    private String Correo;

    private AlertDialog mDialogo;
    //CREAMOS VARIABLES DE FIREBASE: AUTH Y DATABASE

    //CREAMOS VARIABLES DE FIREBASE: AUTH Y DATABASE

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    Button ButtonActualziarDatos;
    EditText EditTextNombres, mEditTextApellidos, EditTextNDNI, EditTextEdad, mEditTextTelefono, editTextTextEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_user);
        EditTextNombres = (EditText) findViewById(R.id.EditTextNombres);
        mEditTextApellidos = (EditText) findViewById(R.id.mEditTextApellidos);
        EditTextNDNI = (EditText) findViewById(R.id.EditTextNDNI);
        EditTextEdad = (EditText) findViewById(R.id.EditTextEdad);
        mEditTextTelefono = (EditText) findViewById(R.id.mEditTextTelefono);
        editTextTextEmailAddress = (EditText) findViewById(R.id.editTextTextEmailAddress);

        ButtonActualziarDatos = (Button) findViewById(R.id.ButtonActualziarDatos);
        //HACEMOS REFERENCIA A FIREBASE: AUTH Y DATABASE
        mAuth=FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDialogo = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un Momento")
                .setCancelable(false).build();

        ButtonActualziarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nombres = EditTextNombres.getText().toString();
                Apellidos = mEditTextApellidos.getText().toString();
                NDni = EditTextNDNI.getText().toString();
                Edad = EditTextEdad.getText().toString();
                Telefono = mEditTextTelefono.getText().toString();
                Correo = editTextTextEmailAddress.getText().toString();

                if (!Nombres.isEmpty()) {
                    if (!Apellidos.isEmpty()) {
                        if (!NDni.isEmpty()) {
                            if (NDni.length() == 8) {
                                if (!Edad.isEmpty()) {
                                    if (!Telefono.isEmpty()) {
                                        if (Telefono.length() == 9) {
                                            if (!Correo.isEmpty()) {
                                                mDialogo.show();
                                                ActualizarUsurario();
                                            } else {
                                                Toast.makeText(PerfilUserActivity.this, "Ingrese su Correo", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(PerfilUserActivity.this, "El Telefono debe contener 9 caracteres", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(PerfilUserActivity.this, "Ingrese su Telefono", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(PerfilUserActivity.this, "Ingrese su Edad", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(PerfilUserActivity.this, "El N° de Dni tiene que tener 8 caracteres", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PerfilUserActivity.this, "Ingrese N° de Dni", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PerfilUserActivity.this, "Ingrese sus Apellidos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PerfilUserActivity.this, "Ingrese sus Nombres", Toast.LENGTH_SHORT).show();
                }

            }
        });


        getUserInfo();
    }

    public void ActualizarUsurario() {


        String id=mAuth.getCurrentUser().getUid();
        Map<String, Object> mapUsuarios = new HashMap<>();
        mapUsuarios.put("Nombres", Nombres);
        mapUsuarios.put("Apellidos", Apellidos);
        mapUsuarios.put("Edad", Edad);
        mapUsuarios.put("NDni", NDni);
        mapUsuarios.put("Telefono", Telefono);
        mapUsuarios.put("Correo", Correo);
        mDatabase.child("Usuarios").child(id).updateChildren(mapUsuarios).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(PerfilUserActivity.this, "Datos Correctamente Actualizados", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(PerfilUserActivity.this, "Error al Actualizar los Datos", Toast.LENGTH_SHORT).show();
                }
                mDialogo.dismiss();

            }
        });

    }


    private void getUserInfo() {
        String Id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Nombres = snapshot.child("Nombres").getValue().toString();
                    Apellidos = snapshot.child("Apellidos").getValue().toString();
                    NDni = snapshot.child("Edad").getValue().toString();
                    Edad = snapshot.child("NDni").getValue().toString();
                    Telefono = snapshot.child("Telefono").getValue().toString();
                    Correo = snapshot.child("Correo").getValue().toString();

                    EditTextNombres.setText(Nombres);
                    mEditTextApellidos.setText(Apellidos);
                    EditTextEdad.setText(NDni);
                    EditTextNDNI.setText(Edad);
                    mEditTextTelefono.setText(Telefono);
                    editTextTextEmailAddress.setText(Correo);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}
