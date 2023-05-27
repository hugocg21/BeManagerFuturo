package com.hugocg21.bemanager.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.hugocg21.bemanager.Clases.Jugador;
import com.hugocg21.bemanager.Clases.Partido;
import com.hugocg21.bemanager.R;

public class AdaptadorPartidos extends FirestoreRecyclerAdapter<Partido, AdaptadorPartidos.ViewHolderPartidos> {
    private OnItemClickListener listener;

    public AdaptadorPartidos(@NonNull FirestoreRecyclerOptions<Partido> options, OnItemClickListener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull AdaptadorPartidos.ViewHolderPartidos holder, int position, @NonNull Partido partido) {
        holder.bindData(partido);
    }

    @NonNull
    @Override
    public AdaptadorPartidos.ViewHolderPartidos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_partido, parent, false);
        return new AdaptadorPartidos.ViewHolderPartidos(view, listener);
    }

    public class ViewHolderPartidos extends RecyclerView.ViewHolder {
        ImageView imgv_imagen;
        TextView tv_rivalPartido, tv_sedePartido, tv_fechaPartido, tv_horaPartido, tv_localVisitantePartido;

        public ViewHolderPartidos(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            imgv_imagen = itemView.findViewById(R.id.imageViewPartido);
            tv_rivalPartido = itemView.findViewById(R.id.textViewRivalPartido);
            tv_sedePartido = itemView.findViewById(R.id.textViewSedePartido);
            tv_fechaPartido = itemView.findViewById(R.id.textViewFechaPartido);
            tv_horaPartido = itemView.findViewById(R.id.textViewHoraPartido);
            tv_localVisitantePartido = itemView.findViewById(R.id.textViewLocalVisitantePartido);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int posicion = getAdapterPosition();
                    if (posicion != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getSnapshots().getSnapshot(posicion), posicion);
                    }
                }
            });
        }

        public void bindData(Partido partido){
            tv_rivalPartido.setText(partido.getRival());
            tv_sedePartido.setText(partido.getPabellon());
            tv_fechaPartido.setText(partido.getDia());
            tv_horaPartido.setText(partido.getHoraInic());
            tv_localVisitantePartido.setText(partido.getLocal_visitante());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
}
