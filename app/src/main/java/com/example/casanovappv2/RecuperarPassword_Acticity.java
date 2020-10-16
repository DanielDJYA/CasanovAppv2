package com.example.casanovappv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

public class RecuperarPassword_Acticity extends AppCompatActivity {
    //LLAMAMOS A LOS COMPONENTES DE XML ACTIVITY_RECUPERAR_PASSWORD.XML
    private EditText mEditTextCorreo;
    //CREAMOS LAS VARIABLES DE LOS DATOS
    private Button mButtonEntrar;
    private String correo;
    //MENSAJE DE CARGA A LA HORA DE ENVIAR MENSAJE AL CORREO
    private AlertDialog mDialogo;
    //CREAMOS VARIABLES DE FIREBASE: AUTH
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //HACEMOS REFERENCIAS A LOS COMPONENTES DE ACTIVITY_RECUPERAR_PASSWORD.XML
        setContentView(R.layout.activity_recuperar_password);
        mEditTextCorreo=(EditText)findViewById(R.id.mEditTextCorreo);
        mEditTextCorreo.requestFocus();
        mButtonEntrar=(Button)findViewById(R.id.mButtonEntrar);
        //HACEMOS REFERENCIA A FIREBASE: AUTH
        mAuth=FirebaseAuth.getInstance();
        //PERSONALIZAMOS CARGA - CARGA DE ESPERA PERSONALIZADA BY:DANIEL
        mDialogo= new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un Momento")
                .setCancelable(false).build();
        //BOTON QUE NOS ENVIARA UN MENSAJE A NUESTRO CORREO PARA CAMBIAR CONTRASEÑA
        mButtonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EXTRAMOS EL DATO A LA VARIABLE CORREO
                correo=mEditTextCorreo.getText().toString();
                //VERIFICAMOS QUE EL CAMPO CORREO ESTE LLENO
                if(!correo.isEmpty()){
                    //MOSTRAMOS EL CARGA DE ESPERA
                    mDialogo.show();
                    //LLAMAMOS AL METODO RECUPERARCONTRASEÑA();
                    RecuperarContraseña();
                }else{
                    Toast.makeText(RecuperarPassword_Acticity.this, "Ingrese su correo electronico", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //CREAMOS EL METODO RecuperarContraseña(); PARA ENTRAR AL SISTEMA
    public void RecuperarContraseña(){
        //LE DECIMOS QUE EL MENSAJE QUE ENVIE A NUESTRO CORREO SERA EN ESPÑAOL - ES
        mAuth.setLanguageCode("es");
        //ENVIAMOS EL MENSAJE A NUESTRO CORREO
        mAuth.sendPasswordResetEmail(correo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //VERIFICAMOS QUE LA TAREA REALIZADA SEA EXITOSA
                if(task.isSuccessful()){
                    //REDIRECCIONAMOS AL LOGIN_ACTIVITY CUANDO LA TAREA ES EXITOSA
                    startActivity(new Intent(getApplicationContext(), Login_Acticity.class));
                    Toast.makeText(RecuperarPassword_Acticity.this, "Se ah enviado un correo para restablecer su contraseña, verifique su Correo", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(RecuperarPassword_Acticity.this, "El Correo es Invalido", Toast.LENGTH_LONG).show();
                }
                //CERRAMOS EL MENSAJE CARGA
                mDialogo.dismiss();
            }
        });

    }
}