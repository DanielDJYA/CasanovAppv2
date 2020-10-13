package com.example.casanovappv2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.casanovappv2.R;
import com.example.casanovappv2.models.Mensaje;

import java.util.ArrayList;

public class MensajeAdapters extends RecyclerView.Adapter<MensajeAdapters.ViewHolder> {

    private int resource;
    private ArrayList<Mensaje> mensajesList;
    public MensajeAdapters(ArrayList<Mensaje> mensajesList, int resource){
        this.mensajesList=mensajesList;
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
        Mensaje mensaje=mensajesList.get(index);
        viewHolder.textViewMensaje.setText(mensaje.getTexto());
    }

    @Override
    public int getItemCount() {
        return mensajesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewMensaje;
        public View view;
        public ViewHolder(View view){
            super(view);
            this.view=view;
            this.textViewMensaje=(TextView) view.findViewById(R.id.textViewMensaje);
        }

    }

}
