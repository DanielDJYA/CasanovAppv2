package com.example.casanovappv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.casanovappv2.Adapters.HabitacionesAdapter;
import com.example.casanovappv2.Fragments.Fragment_Habitaciones;
import com.example.casanovappv2.Fragments.Fragment_Nosotros;
import com.example.casanovappv2.Fragments.Fragment_Perfiluser;
import com.example.casanovappv2.Fragments.Fragment_Reserva;
import com.example.casanovappv2.models.Habitaciones;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
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


    //MENU LATERAL, TOLBAR
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TOLBAR
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //MENU LATERAL
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        nvDrawer.setNavigationItemSelectedListener(this);

        //

        //View headerLayout = nav.getHeaderView(0);
        //mTextViewNombresYApellidos = (TextView) headerLayout.findViewById(R.id.mTextViewNombresYApellidos);

        //HACEMOS REFERENCIA A FIREBASE: AUTH Y DATABASE
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //getUserInfo();
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


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass;
        item.setChecked(true);
        switch(item.getItemId()) {
            case R.id.nav_Habitacion:
                fragmentClass = Fragment_Habitaciones.class;
                break;
            case R.id.nav_Reserva:
                fragmentClass = Fragment_Reserva.class;
                break;
            case R.id.nav_Perfil:
                fragmentClass = Fragment_Perfiluser.class;
                break;
            case R.id.nav_Nosotros:
                fragmentClass = Fragment_Nosotros.class;
                break;
            case R.id.nav_Salir_Sesion:
                fragmentClass = Fragment_Nosotros.class;
                break;
            case R.id.nav_Facebook:
                fragmentClass = Fragment_Nosotros.class;
                break;
            case R.id.nav_WhatsApp:
                fragmentClass = Fragment_Nosotros.class;
                break;
            case R.id.nav_Instangram:
                fragmentClass = Fragment_Nosotros.class;
                break;
            case R.id.nav_Ubicacion:
                fragmentClass = Fragment_Nosotros.class;
                break;
            case R.id.nav_LLamar:
                fragmentClass = Fragment_Nosotros.class;
                break;
            case R.id.nav_Compartir:
                fragmentClass = Fragment_Nosotros.class;
                break;
            default:
                fragmentClass = Fragment_Habitaciones.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        // Highlight the selected item has been done by NavigationView
        // Set action bar title
        setTitle(item.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
        return true;
    }
}