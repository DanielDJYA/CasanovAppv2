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
    //HABITACION para agregar era
    private Button mButtonCrearDatoHabitacion;
    private EditText mEditTextNombre;
    private EditText mEditTextDescripcion;
    private EditText mEditTextPrecio;
    //VARIABLES
    private String Nombre;
    private String Descripcion;
    private String Precio;
    //elementos de lista
    private HabitacionesAdapter mAdapterHabitaciones;
    private RecyclerView recyclerViewHabitaciones;
    private ArrayList<Habitaciones> mHabitacionesList = new ArrayList<>();

    //VARIABLE DEL NOMBRE Y APELLIDO DEL MENU BIENVENIDO
    private TextView mTextViewNombresYApellidos;
    private String NombresUsu;
    private String ApellidosUsu;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawerlayout);
        //TOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Navigation Drawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //
        View headerLayout=navigationView.getHeaderView(0);
        mTextViewNombresYApellidos=(TextView) headerLayout.findViewById(R.id.mTextViewNombresYApellidos);
        //DATOS DEL FIREBASE
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //RECIBLER VIEW
        recyclerViewHabitaciones = (RecyclerView) findViewById(R.id.recyclerViewHabitaciones);
        recyclerViewHabitaciones.setLayoutManager(new LinearLayoutManager(this));
        //LLAMAMOS A LA LISTA
        ListarHabitaciones();
        getUserInfo();
    }

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
        DrawerLayout drawer = (DrawerLayout) findViewById(
                R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(
                R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //LISTAR
    private void ListarHabitaciones() {
        mDatabase.child("Habitaciones").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                if (datasnapshot.exists()) {
                    mHabitacionesList.clear();
                    for (DataSnapshot ds : datasnapshot.getChildren()) {
                        Nombre = ds.child("Nombre").getValue().toString();
                        Descripcion = ds.child("Descripcion").getValue().toString();
                        Precio = ds.child("Precio").getValue().toString();
                        mHabitacionesList.add(new Habitaciones(Nombre, Descripcion, Precio));
                    }
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

