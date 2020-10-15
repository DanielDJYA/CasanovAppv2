package com.example.casanovappv2.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.casanovappv2.R;
import com.example.casanovappv2.models.Usuarios;

import java.util.ArrayList;

public class UsuariosAdapter extends RecyclerView.Adapter<UsuariosAdapter.ViewHolder> {

    private int resource;
    private ArrayList<Usuarios> mensajesList;
    public UsuariosAdapter(ArrayList<Usuarios> mensajesList, int resource){
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
        Usuarios mensaje=mensajesList.get(index);
        viewHolder.mtextViewMensajeNombres.setText(mensaje.getNombres());
        viewHolder.mtextViewMensajeApellidos.setText(mensaje.getApellidos());
    }

    @Override
    public int getItemCount() {
        return mensajesList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mtextViewMensajeNombres;
        private TextView mtextViewMensajeApellidos;
        public View view;
        public ViewHolder(View view){
            super(view);
            this.view=view;
            this.mtextViewMensajeNombres=(TextView) view.findViewById(R.id.mtextViewMensajeNombres);
            this.mtextViewMensajeApellidos=(TextView) view.findViewById(R.id.mtextViewMensajeApellidos);
        }

    }

}
