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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import dmax.dialog.SpotsDialog;

public class Login_Acticity extends AppCompatActivity {
    //LLAMAMOS A LOS COMPONENTES DE XML ACTIVITY_LOGIN.XML
    private EditText mEditTextCorreo;
    private EditText mEditTextContraseña;
    private  Button mButtonEntrar;
    private Button mButtonOlvidoContraseña;
    private Button mButtonCuentaNueva;
    //CREAMOS LAS VARIABLES DE LOS DATOS
    private String Correo;
    private String Contraseña;
    //MENSAJE DE CARGA A LA HORA DE ENTRAR AL SISTEMA
    private AlertDialog mDialogo;
    //CREAMOS VARIABLES DE FIREBASE: AUTH
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //HACEMOS REFERENCIAS A LOS COMPONENTES DE ACTIVITY_LOGIN.XML
        mEditTextCorreo=(EditText)findViewById(R.id.mEditTextCorreo);
        mEditTextCorreo.requestFocus();
        mEditTextContraseña=(EditText)findViewById(R.id.mEditTextContraseña);
        mButtonEntrar=(Button)findViewById(R.id.mButtonEntrar);
        mButtonOlvidoContraseña=(Button)findViewById(R.id.mButtonOlvidoContraseña);
        mButtonCuentaNueva=(Button)findViewById(R.id.mButtonCuentaNueva);
        //HACEMOS REFERENCIA A FIREBASE: AUTH
        mAuth=FirebaseAuth.getInstance();
        //HACEMOS REFERENCIAS AL MENSAJE DE CARGA
        mDialogo=new ProgressDialog(this);
        //PERSONALIZAMOS CARGA - CARGA DE ESPERA PERSONALIZADA BY:DANIEL
        mDialogo= new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Verificando Usuario")
                .setCancelable(false).build();
        //BOTON QUE NOS REDIRECCIONA A RECUPERAR CONTRASEÑA - ACTIVIDAD: RECUPERARPASSWORD_ACTIVITY
        mButtonOlvidoContraseña.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RecuperarPassword_Acticity.class));
            }
        });
        //BOTON QUE NOS REDIRECCIONA A REGISTRAR USUARIO - ACTIVIDAD: REGISTERUSER_ACTIVITY
        mButtonCuentaNueva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterUser_Acticity.class));
            }
        });
        //BOTON ENTRAR QUE REDIRECCIONARA A HOME - ACTIVIDAD: HOME_ACTIVITY
        mButtonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Correo=mEditTextCorreo.getText().toString();
                Contraseña=mEditTextContraseña.getText().toString();
                if(!Correo.isEmpty()){
                    if(!Contraseña.isEmpty()){
                        if(Contraseña.length()>=6){
                            //MOSTRAMOS EL CARGA DE ESPERA
                            mDialogo.show();
                            //LLAMAMOS AL METODO LOGINUSER();
                            LoginUser();
                        }else{
                            Toast.makeText(Login_Acticity.this, "El password debe tener contener 6 caracteres", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(Login_Acticity.this, "Ingrese su contraseña", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Login_Acticity.this, "Ingrese su correo electronico", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //CREAMOS EL METODO LOGINUSER(); PARA ENTRAR AL SISTEMA
    private void LoginUser(){
        //IDENTIFICACION CON EMAILANDPASSWOD Y LE PASAMOS LAS VARIABLES CORREO Y CONTRASEÑA
        mAuth.signInWithEmailAndPassword(Correo,Contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //SI LA TAREA ES EXITOSA NOS LLEGA A HOME_ACTIVITY Y TERMINA EL PROCESO DE LOGIN
            if(task.isSuccessful()){
                startActivity(new Intent(getApplicationContext(), Home_Acticity.class));
                finish();
                Toast.makeText(Login_Acticity.this, "Usuario Valido", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Login_Acticity.this, "Nose Pudo Iniciar Sesion Compruebe los Datos", Toast.LENGTH_LONG).show();
            }
            //CERRAMOS EL MENSAJE CARGA
                mDialogo.dismiss();
            }
        });
    }
}