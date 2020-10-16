package com.example.casanovappv2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.casanovappv2.Actividades.VerGaleriaActivity;
import com.example.casanovappv2.models.Galeria;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import id.zelory.compressor.Compressor;

public class SubirFoto extends AppCompatActivity {
    EditText titulo;
    ImageView foto;
    Button subir, seleccionar,vergaleria;
    DatabaseReference imgref;
    StorageReference storageReference;
    ProgressDialog cargando;
    Bitmap thumb_bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_foto);
        foto = findViewById(R.id.img_foto);
        seleccionar = findViewById(R.id.btn_selefoto);
        subir = findViewById(R.id.btn_subirfoto);
        vergaleria = (Button) findViewById(R.id.btn_verGaleria);
        titulo = findViewById(R.id.et_titulo);
        imgref = FirebaseDatabase.getInstance().getReference().child("Fotos_subidas");
        storageReference = FirebaseStorage.getInstance().getReference().child("img_comprimidas");
        cargando = new ProgressDialog(this);

        seleccionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.startPickImageActivity(SubirFoto.this);
            }
        });


        vergaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(v.getContext(), VerGaleriaActivity.class);
                startActivity(p);
            }
        });
    }// fin del oncreate!!!

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Uri imageuri = CropImage.getPickImageResultUri(this,data);

            //Recortar Imagen..

            CropImage.activity(imageuri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setRequestedSize(640,480)
                    .setAspectRatio(2,1).start(SubirFoto.this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result= CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();

                File url = new File(resultUri.getPath());
                Picasso.with(this).load(url).into(foto);

                //comprimiendo imagen

                try {
                    thumb_bitmap= new Compressor(this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .compressToBitmap(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG,90,byteArrayOutputStream);
                final byte [] thumb_byte = byteArrayOutputStream.toByteArray();

                // Fin del Compresor.....

                int p = (int) (Math.random() * 25 + 1 );int s =(int) (Math.random() * 25 + 1 );
                int t = (int) (Math.random() * 25 + 1 );int c =(int) (Math.random() * 25 + 1 );
                int numero1 = (int) (Math.random() * 1012 + 2111);
                int numero2 = (int) (Math.random() * 1012 + 2111);

                String[] elementos = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n",
                        "o","p","q","r","s","u","v","w","x","y","z"};

                final String aleatorio   = elementos[p] + elementos[s] +
                        numero1 + elementos[t] + elementos[c] + numero2 + "comprimido.jpg";

                subir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cargando.setTitle("Subiendo Foto...");
                        cargando.setMessage("Espere por favor...");
                        cargando.show();

                        final StorageReference ref = storageReference.child(aleatorio);
                        UploadTask uploadTask = ref.putBytes(thumb_byte);

                        //Subir imagen en storage----...

                        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                                if (!task.isSuccessful()){
                                    throw Objects.requireNonNull(task.getException());
                                }

                                return ref.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                Uri downloaduri = task.getResult();

                                String titulo_string = titulo.getText().toString();

                                Galeria gal = new Galeria(titulo_string,downloaduri.toString());

                                imgref.push().setValue(gal);

                                cargando.dismiss();

                                Toast.makeText(SubirFoto.this, "Imagen cargada con exito", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            }
        }
    }

}



































