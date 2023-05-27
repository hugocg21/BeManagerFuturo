package com.hugocg21.bemanager.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.hugocg21.bemanager.Clases.Jugador;
import com.hugocg21.bemanager.R;

public class AdaptadorJugadores extends FirestoreRecyclerAdapter<Jugador, AdaptadorJugadores.ViewHolderJugadores> {
    private OnItemClickListener listener;

    public AdaptadorJugadores(@NonNull FirestoreRecyclerOptions<Jugador> options, OnItemClickListener listener) {
        super(options);
        this.listener = listener;
    }

    @Override
    protected void onBindViewHolder(@NonNull AdaptadorJugadores.ViewHolderJugadores holder, int position, @NonNull Jugador jugador) {
        holder.bindData(jugador);
    }

    @NonNull
    @Override
    public AdaptadorJugadores.ViewHolderJugadores onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_jugador, parent, false);
        return new AdaptadorJugadores.ViewHolderJugadores(view, listener);
    }

    public class ViewHolderJugadores extends RecyclerView.ViewHolder {
        TextView tv_dorsalJugador, tv_nombreJugador, tv_apellidosJugador, tv_equipoJugador, tv_posicionJugador;

        public ViewHolderJugadores(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            tv_dorsalJugador = itemView.findViewById(R.id.textViewDorsalJugador);
            tv_nombreJugador = itemView.findViewById(R.id.textViewNombreJugador);
            tv_apellidosJugador = itemView.findViewById(R.id.textViewApellidosJugador);
            tv_equipoJugador = itemView.findViewById(R.id.textViewEquipoJugador);
            tv_posicionJugador = itemView.findViewById(R.id.textViewPosicionJugador);

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

        public void bindData(Jugador jugador){
            tv_nombreJugador.setText(jugador.getNombreJugador());
            tv_apellidosJugador.setText(jugador.getApellidosJugador());
            tv_equipoJugador.setText(jugador.getEquipoJugador());
            tv_posicionJugador.setText(jugador.getPosicionJugador());
            tv_dorsalJugador.setText(jugador.getDorsalJugador());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

}
