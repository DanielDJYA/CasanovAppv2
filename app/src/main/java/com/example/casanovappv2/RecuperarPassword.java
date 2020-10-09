package com.example.casanovappv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;

public class RecuperarPassword extends AppCompatActivity {
    private EditText mEditTextCorreo;
    private Button mButtonEntrar;
    private String correo;
    //MENSAJE DE CARGA
    private AlertDialog mDialogo;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_password);
        mEditTextCorreo=(EditText)findViewById(R.id.mEditTextCorreo);
        mButtonEntrar=(Button)findViewById(R.id.mButtonEntrar);
        //CARGA DE ESPERA PERSONALIZADA BY DANIEL
        mDialogo= new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un Momento")
                .setCancelable(false).build();
        mAuth=FirebaseAuth.getInstance();
        mButtonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correo=mEditTextCorreo.getText().toString();
                if(!correo.isEmpty()){
                    mDialogo.show();
                    resetPassword();
                }else{
                    Toast.makeText(RecuperarPassword.this, "Ingrese su correo electronico", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void resetPassword(){
        mAuth.setLanguageCode("es");
        mAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    Toast.makeText(RecuperarPassword.this, "Se ah enviado un correo para restablecer su contraseña, verifique su Correo", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(RecuperarPassword.this, "El Correo es Invalido", Toast.LENGTH_LONG).show();
                }
                mDialogo.dismiss();
            }
        });

    }
}