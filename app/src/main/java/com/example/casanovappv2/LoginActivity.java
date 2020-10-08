package com.example.casanovappv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {
    //LLAMAMOS A LOS COMPONENTES DE XML
    private EditText mEditTextCorreo;
    private EditText mEditTextContraseña;
    private  Button mButtonEntrar;
    //CREAMOS LAS VARIABLES DE LOS DATOS
    private String Correo;
    private String Contraseña;
    //CREAMOS VARIABLES DE FIREBASE: AUTH Y DATABASE
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEditTextCorreo=(EditText)findViewById(R.id.mEditTextCorreo);
        mEditTextContraseña=(EditText)findViewById(R.id.mEditTextContraseña);
        mButtonEntrar=(Button)findViewById(R.id.mButtonEntrar);

        mAuth=FirebaseAuth.getInstance();

        mButtonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Correo=mEditTextCorreo.getText().toString();
                Contraseña=mEditTextContraseña.getText().toString();
                if(!Correo.isEmpty() && !Contraseña.isEmpty()) {
                    LoginUser();
                }else{
                    Toast.makeText(LoginActivity.this, "Complete los Campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void LoginUser(){
        mAuth.signInWithEmailAndPassword(Correo,Contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                startActivity(new Intent(getApplicationContext(), PerfilUserActivity.class));
                finish();
            }else{
                Toast.makeText(LoginActivity.this, "Nose Pudo Iniciar Sesion Compruebe los Datos", Toast.LENGTH_SHORT).show();
            }
            }
        });
    }
}