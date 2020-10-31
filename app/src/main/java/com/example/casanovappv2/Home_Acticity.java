package com.example.casanovappv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.casanovappv2.Adapters.HabitacionesAdapter;
import com.example.casanovappv2.models.Habitaciones;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URLEncoder;
import java.util.ArrayList;

public class Home_Acticity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //LLAMAMOS A LOS COMPONENTES DE XML ACTIVITY_LOGIN.XML
    private TextView mTextViewNombresYApellidos;
    private RecyclerView recyclerViewHabitaciones;
    //CREAMOS LAS VARIABLES DE LOS DATOS QUE IRAN EN EL NAV_HEADER_MAIN.XML
    private String NombresUsu;
    private String ApellidosUsu;
    //CREAMOS LAS VARIABLES DE LOS DATOS QUE IRAN EN EL
    private String Nombre;
    private String Descripcion;
    private String Precio;
    //LLAMAMOS A HABITACIONESADAPTER
    private HabitacionesAdapter mAdapterHabitaciones;
    //LLAMAMOS A HABITACIONES Y LO PONENEMOS EN UN ARRAY
    private ArrayList<Habitaciones> mHabitacionesList = new ArrayList<>();
    //CREAMOS VARIABLES DE FIREBASE: AUTH Y DATABASE
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;


    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        nvDrawer.setNavigationItemSelectedListener(this);
        //LLAMAMOS A NAV_HEADER_MAIN.XML PARA EXTRAER SUS COMPONENTES Y HACER REFERENCIA
        //View headerLayout = navigationView.getHeaderView(0);
        //mTextViewNombresYApellidos = (TextView) headerLayout.findViewById(R.id.mTextViewNombresYApellidos);

        //HACEMOS REFERENCIAS A LOS COMPONENTES DE ACTIVITY_HOME.XML
        recyclerViewHabitaciones = (RecyclerView) findViewById(R.id.recyclerViewHabitaciones);
        recyclerViewHabitaciones.setLayoutManager(new LinearLayoutManager(this));
        //HACEMOS REFERENCIA A FIREBASE: AUTH Y DATABASE
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //LLAMAMOS A LA LISTA DE HABITACIONES
        //ListarHabitaciones();
        //LLAMAMOS A LOS DATOS DE NOMBRE Y APELLIDOS DEL USUARIO
        getUserInfo();
    }

    //EL MENU LATERAL IZQUIERDO
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //LISTAR HABITACIONES
    private void ListarHabitaciones() {
        //HACEMOS REFERNCIA AL NOTO HABITACIONES
        mDatabase.child("Habitaciones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                //SI LA TAREA ES CORRECTA
                if (datasnapshot.exists()) {
                    //LIMPIAMOS PARA QUE NO SE REPITAN LOS DATOS QUE ESTA ESTAN REGISTRADOS
                    mHabitacionesList.clear();
                    //CREAMOS UN CICLO FOR
                    for (DataSnapshot ds : datasnapshot.getChildren()) {
                        //LLAMAMOS A LAS VARIABLES CREADAS Y ASIGAMOS CADA HIJO DEL NODO
                        Nombre = ds.child("Nombre").getValue().toString();
                        Descripcion = ds.child("Descripcion").getValue().toString();
                        Precio = ds.child("Precio").getValue().toString();
                        //LLAMAMOS AL CONSUTRUCTOR
                        mHabitacionesList.add(new Habitaciones(Nombre, Descripcion, Precio));
                    }
                    //LISTAMOS LO DATOS
                    mAdapterHabitaciones = new HabitacionesAdapter(mHabitacionesList, R.layout.activity_lista_habitaciones);
                    recyclerViewHabitaciones.setAdapter(mAdapterHabitaciones);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getUserInfo() {
        String Id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Usuarios").child(Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    NombresUsu = snapshot.child("Nombres").getValue().toString();
                    ApellidosUsu = snapshot.child("Apellidos").getValue().toString();
                    mTextViewNombresYApellidos.setText(NombresUsu + " " + ApellidosUsu);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}

//LISTAR USUARIOS
    /*private void ListarUsuarios(){
        mDatabase.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if(datasnapshot.exists()){
                    mMensajesList.clear();
                    for (DataSnapshot ds: datasnapshot.getChildren()) {
                        Nombres=ds.child("Nombres").getValue().toString();
                        Apellidos=ds.child("Apellidos").getValue().toString();
                        mMensajesList.add(new Usuarios(Nombres,Apellidos));
                    }
                    mAdapter = new UsuariosAdapter(mMensajesList, R.layout.activity_lista_habitaciones);
                    recyclerViewMensajes.setAdapter(mAdapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }*/

