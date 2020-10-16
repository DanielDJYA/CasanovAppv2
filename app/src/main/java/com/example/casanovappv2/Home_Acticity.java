package com.example.casanovappv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.casanovappv2.Adapters.HabitacionesAdapter;
/*import com.example.casanovappv2.Adapters.UsuariosAdapter;*/
import com.example.casanovappv2.models.Habitaciones;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout);
        //LLAMAMOS Toolbar QUE ESTA EN ACTIVITY_HOME.XML
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //LLAMAMOS A DRAWERLAYOUT.XML
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //LLAMAMOS A NAV_HEADER_MAIN.XML PARA EXTRAER SUS COMPONENTES Y HACER REFERENCIA
        View headerLayout=navigationView.getHeaderView(0);
        mTextViewNombresYApellidos=(TextView) headerLayout.findViewById(R.id.mTextViewNombresYApellidos);
        //HACEMOS REFERENCIAS A LOS COMPONENTES DE ACTIVITY_HOME.XML
        recyclerViewHabitaciones = (RecyclerView) findViewById(R.id.recyclerViewHabitaciones);
        recyclerViewHabitaciones.setLayoutManager(new LinearLayoutManager(this));
        //HACEMOS REFERENCIA A FIREBASE: AUTH Y DATABASE
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //LLAMAMOS A LA LISTA DE HABITACIONES
        ListarHabitaciones();
        //LLAMAMOS A LOS DATOS DE NOMBRE Y APELLIDOS DEL USUARIO
        getUserInfo();
    }
    //EL MENU LATERAL IZQUIERDO
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_perfil:
                startActivity(new Intent(this, PerfilUser_Acticity.class));
                break;
            case R.id.nav_habitacion:
                //startActivity(new Intent(this, Permisos.class));
                break;
            case R.id.nav_reserva:
                //startActivity(new Intent(this, Intenciones.class));
                break;
            case R.id.nav_nosotros:
                //startActivity(new Intent(this, Comunicacion1.class));
                break;
            case R.id.nav_compartir:
                Intent paramView;
                paramView = new Intent("android.intent.action.SEND");
                paramView.setType("text/plain");
                paramView.putExtra("android.intent.extra.TEXT", "Descarga nuestra app de la PlayStore" +
                        " \n" + "https://play.google.com/store/apps/details?id=app.product.demarktec.alo14_pasajero");
                startActivity(Intent.createChooser(paramView, "Comparte Nuestro aplicativo"));
                break;
            case R.id.nav_cerrar_sesion:
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), Login_Acticity.class));
                finish();
                break;
            default:
                break;
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
                    mTextViewNombresYApellidos.setText(NombresUsu+" "+ApellidosUsu);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }}

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

