package com.hugocg21.bemanager.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hugocg21.bemanager.Clases.Equipo;
import com.hugocg21.bemanager.Menus.EquipoDashboard;
import com.hugocg21.bemanager.R;

import java.util.List;

public class AdaptadorEquipos extends RecyclerView.Adapter<AdaptadorEquipos.ViewHolder>{
    private List<Equipo> listaEquipos;
    private Context context;

    public AdaptadorEquipos(List<Equipo> listaEquipos, Context context) {
        this.listaEquipos = listaEquipos;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_equipo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Equipo equipo = listaEquipos.get(position);
        holder.textView_nombreEquipo.setText(equipo.getNombreEquipo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EquipoDashboard.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaEquipos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder/* implements View.OnClickListener*/{
        TextView textView_nombreEquipo;
        ImageView imageView_logo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_nombreEquipo = itemView.findViewById(R.id.textViewNombreEquipo);
            imageView_logo = itemView.findViewById(R.id.imageViewEquipo);
        }

        /*@Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Equipo equipo = listaEquipos.get(position);
                listener.onItemClick(equipo);
            }
        }*/
    }

    public interface OnItemClickListener {
        void onItemClick(Equipo equipo);
    }
}
