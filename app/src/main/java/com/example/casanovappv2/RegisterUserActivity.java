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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class RegisterUserActivity extends AppCompatActivity {
    //LLAMAMOS A LOS COMPONENTES DE XML
    private EditText mEditTextNombres;
    private EditText mEditTextApellidos;
    private EditText mEditTextNDni;
    private EditText mEditTextEdad;
    private EditText mEditTextTelefono;
    private EditText mEditTextCorreo;
    private EditText mEditTextContraseña;
    private EditText mEditTextConfirmarContraseña;
    private Button mButtonRegistrarUsuario;
    private Button mButtonLogin;
    //CREAMOS LAS VARIABLES DE LOS DATOS
    private String Nombres;
    private String Apellidos;
    private String NDni;
    private String Edad;
    private String Telefono;
    private String Correo;
    private String Contraseña;
    private String ConfirmarContraseña;
    //MENSAJE DE CARGA
    private AlertDialog mDialogo;
    //CREAMOS VARIABLES DE FIREBASE: AUTH Y DATABASE
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        //INSTANCIAMOS LOS ELEMENTOS DE XML
        mEditTextNombres = (EditText) findViewById(R.id.mEditTextNombres);
        mEditTextNombres.requestFocus();
        mEditTextApellidos = (EditText) findViewById(R.id.mEditTextApellidos);
        mEditTextNDni = (EditText) findViewById(R.id.mEditTextNDni);
        mEditTextEdad = (EditText) findViewById(R.id.mEditTextEdad);
        mEditTextTelefono = (EditText) findViewById(R.id.mEditTextTelefono);
        mEditTextCorreo = (EditText) findViewById(R.id.mEditTextCorreo);
        mEditTextContraseña = (EditText) findViewById(R.id.mEditTextContraseña);
        mEditTextConfirmarContraseña = (EditText) findViewById(R.id.mEditTextConfirmarContraseña);
        mButtonRegistrarUsuario = (Button) findViewById(R.id.mButtonRegistrarUsuario);
        mButtonLogin = (Button) findViewById(R.id.mButtonLogin);
        //CARGA DE ESPERA PERSONALIZADA BY DANIEL
        mDialogo = new SpotsDialog.Builder()
                .setContext(this)
                .setMessage("Espere un Momento")
                .setCancelable(false).build();
        //INSTANCIAMOS LOS ELEMENTOS DE FIREBASE
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //CREAMOS EL EVENTO IR LOGIN
        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                // no tendra retorno al registro usuario finish();
            }
        });
        //CREAMOS EL EVENTO REGISTRO DE USUARIO
        mButtonRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //UTILIZAMOS LAS VARIABLES CREADAS PARA INSERTAR LOS DATOS
                Nombres = mEditTextNombres.getText().toString();
                Apellidos = mEditTextApellidos.getText().toString();
                NDni = mEditTextNDni.getText().toString();
                Edad = mEditTextEdad.getText().toString();
                Telefono = mEditTextTelefono.getText().toString();
                Correo = mEditTextCorreo.getText().toString();
                Contraseña = mEditTextContraseña.getText().toString();
                ConfirmarContraseña = mEditTextConfirmarContraseña.getText().toString();
                //CONFIRMAMOS QUE LOS CAMPOS NO ESTEN VACIOS
                if (!Nombres.isEmpty()) {
                    if (!Apellidos.isEmpty()) {
                        if (!NDni.isEmpty()) {
                            if (NDni.length() == 8) {
                                if (!Edad.isEmpty()) {
                                    if (!Telefono.isEmpty()) {
                                        if (Telefono.length() == 9) {
                                            if (!Correo.isEmpty()) {
                                                if (!Contraseña.isEmpty()) {
                                                    if (Contraseña.length() >= 6) {
                                                        if (!ConfirmarContraseña.isEmpty()) {
                                                            if (Contraseña.equals(ConfirmarContraseña)) {
                                                                mDialogo.show();

                                                                RegisterUser();
                                                            } else {
                                                                Toast.makeText(RegisterUserActivity.this, "Las Contraseñas no Coinciden", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(RegisterUserActivity.this, "Confirme su Contraseña", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(RegisterUserActivity.this, "La Contraseña Debe tener Almenos 6 Caracteres", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(RegisterUserActivity.this, "Ingrese Contraseña", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(RegisterUserActivity.this, "Ingrese su Correo", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(RegisterUserActivity.this, "El Telefono debe contener 9 caracteres", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(RegisterUserActivity.this, "Ingrese su Telefono", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(RegisterUserActivity.this, "Ingrese su Edad", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Toast.makeText(RegisterUserActivity.this, "El N° de Dni tiene que tener 8 caracteres", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(RegisterUserActivity.this, "Ingrese N° de Dni", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterUserActivity.this, "Ingrese sus Apellidos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterUserActivity.this, "Ingrese sus Nombres", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    //CREAMOS EL METODO REGISTERUSER QUE SERA PARA CREAR USER
    private void RegisterUser() {
        //LE ENVIAMOS EL CORREO Y CONTRASEÑA
        mAuth.createUserWithEmailAndPassword(Correo, Contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //VERIFICAMOS QUE LA TAREA Q SE REALIZA AQUI SEA EXITOSA
                if (task.isSuccessful()) {
                    //CREAMOS UN MAP PARA ALMACENAR TODOS LOS CAMPOS Y LOS DATOS INSETADOS
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("Nombres", Nombres);
                    userMap.put("Apellidos", Apellidos);
                    userMap.put("NDni", NDni);
                    userMap.put("Edad", Edad);
                    userMap.put("Telefono", Telefono);
                    userMap.put("Correo", Correo);
                    userMap.put("Contraseña", Contraseña);
                    //OBTENER EL INDENTIFICADOR DE CADA USUARIO
                    String id = mAuth.getCurrentUser().getUid();
                    //CREAMOS LA TABAL USUARIOS Y COLOCALOS EL IDENTIFICADOR,
                    // Y POR ULTIMO ENVIAMOS LOS CAMPOS Y DATOS INSETADOS
                    mDatabase.child("Usuarios").child(id).setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        //CAMBIAMOS EL TASK A TASK2 PARA QUE NO HAYA PROBLEMAS A LA HORA DE VERIFICAR LAS TAREAS
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            //CCOMPROBAMOS SI LA TAREA2 TASK2 FUE EXISTOSA
                            if (task2.isSuccessful()) {
                                //ENVIAMOS AL PERFILUSER
                                startActivity(new Intent(getApplicationContext(), PerfilUserActivity.class));
                                //TERMINAMOS LA ACCION
                                finish();
                            } else {
                                //EL ERROR QUE NO SE PUDO COMPLETAR LA ACCION DE REGISTRO
                                Toast.makeText(RegisterUserActivity.this, "No se pudo Completar la Accion de Registro", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                } else {
                    //ERROR QUE NO SE PUDO REGISTRAR EL USUARIO
                    Toast.makeText(RegisterUserActivity.this, "Nose pudo Registrar este Usuario", Toast.LENGTH_SHORT).show();
                }
                //CERRAMOS EL MENSAJE DE ESPERA
                mDialogo.dismiss();
            }
        });
    }
}