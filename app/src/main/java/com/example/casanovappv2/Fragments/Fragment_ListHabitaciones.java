package com.example.casanovappv2.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.casanovappv2.Adapters.HabitacionesAdapter;
import com.example.casanovappv2.R;
import com.example.casanovappv2.models.Habitaciones;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_ListHabitaciones#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_ListHabitaciones extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerViewHabitaciones;
    HabitacionesAdapter mAdapterHabitaciones;
    ArrayList<Habitaciones> mHabitacionesList = new ArrayList<>();
    //CREAMOS LAS VARIABLES DE LOS DATOS QUE IRAN EN EL
    private String Nombre;
    private String Descripcion;
    private String Precio;

    DatabaseReference mDatabase;



    public Fragment_ListHabitaciones() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_ListHabitaciones.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_ListHabitaciones newInstance(String param1, String param2) {
        Fragment_ListHabitaciones fragment = new Fragment_ListHabitaciones();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_list_habitaciones, container, false);
        mHabitacionesList=new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerViewHabitaciones=(RecyclerView) vista.findViewById(R.id.recyclerViewHabitaciones);
        recyclerViewHabitaciones.setLayoutManager(new LinearLayoutManager(getContext()));
        ListarHabitaciones();
        return vista;
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
}