package com.example.casanovappv2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.casanovappv2.R;
import com.example.casanovappv2.models.Galeria;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GaleriaAdapter extends RecyclerView.Adapter<GaleriaAdapter.FotosViewHolder>{

    List<Galeria> galeriaList;
    Context context;

    public GaleriaAdapter(List<Galeria> galeriaList, Context context) {
        this.galeriaList = galeriaList;
        this.context = context;
    }

    @NonNull
    @Override
    public FotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_galeria,parent,false);
        FotosViewHolder holder = new FotosViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FotosViewHolder holder, int position) {
        Galeria gal= galeriaList.get(position);

        holder.tv_titulo.setText(gal.getTitulo());
        Picasso.with(context).load(gal.getFoto()).into(holder.img_foto, new Callback() {
            @Override
            public void onSuccess() {
                 holder.progress.setVisibility(View.GONE);
                 holder.img_foto.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {

                Toast.makeText(context, "Tienes un error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return galeriaList.size();
    }

    public class FotosViewHolder extends RecyclerView.ViewHolder {
        TextView tv_titulo;
        ImageView img_foto;
        ProgressBar progress;
        public FotosViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_titulo = itemView.findViewById(R.id.tv_titulo);
            img_foto = itemView.findViewById(R.id.img_foto);
            progress = itemView.findViewById(R.id.progress_bar_galeria);

        }
    }
}
