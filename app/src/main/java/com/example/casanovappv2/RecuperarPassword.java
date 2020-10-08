package com.example.casanovappv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RecuperarPassword extends AppCompatActivity {
    private EditText mEditTextCorreo;
    private Button mButtonEntrar;

    private String eamil;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_password);
        mEditTextCorreo=(EditText)findViewById(R.id.mEditTextCorreo);
        mButtonEntrar=(Button)findViewById(R.id.mButtonEntrar);

        mAuth=FirebaseAuth.getInstance();
        mButtonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eamil=mEditTextCorreo.getText().toString();
                if(!eamil.isEmpty()){
                    resetPassword();

                }else{
                    Toast.makeText(RecuperarPassword.this, "Complete los Campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void resetPassword(){
        mAuth.setLanguageCode("es");
        mAuth.set

    }
}