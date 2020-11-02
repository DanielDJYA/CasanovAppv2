package com.example.casanovappv2.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.casanovappv2.PerfilUserActivity;
import com.example.casanovappv2.R;
import com.example.casanovappv2.models.Habitaciones;


import java.util.ArrayList;

public class HabitacionesAdapter extends RecyclerView.Adapter<HabitacionesAdapter.ViewHolder> {

    private int resource;
    private ArrayList<Habitaciones> mHabitacionesList;
    public HabitacionesAdapter(ArrayList<Habitaciones> mHabitacionesList, int resource){
        this.mHabitacionesList=mHabitacionesList;
        this.resource=resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {
        Habitaciones habitaciones=mHabitacionesList.get(index);
        viewHolder.mTextViewNombre.setText(habitaciones.getNombre());
        viewHolder.mTextViewCaracteristicas.setText(habitaciones.getDescripcion());
        viewHolder.mTextViewPrecio.setText(habitaciones.getPrecio());
    }

    @Override
    public int getItemCount() {
        return mHabitacionesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mTextViewNombre;
        private TextView mTextViewCaracteristicas;
        private TextView mTextViewPrecio;
        public View view;
        public ViewHolder(final View view){
            super(view);
            this.view=view;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(view.getContext(), "HOLA", Toast.LENGTH_SHORT).show();
                }
            });


            this.mTextViewNombre=(TextView) view.findViewById(R.id.mTextViewNombre);
            this.mTextViewCaracteristicas=(TextView) view.findViewById(R.id.mTextViewCaracteristicas);
            this.mTextViewPrecio=(TextView) view.findViewById(R.id.mTextViewPrecio);
        }
    }
}