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

    @NonNull
    @Override
    public AdaptadorJugadores.ViewHolderJugadores onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_jugador, parent, false);
        return new AdaptadorJugadores.ViewHolderJugadores(view, listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull AdaptadorJugadores.ViewHolderJugadores holder, int position, @NonNull Jugador jugador) {
        holder.bindData(jugador);
    }

    public class ViewHolderJugadores extends RecyclerView.ViewHolder {
        TextView textView_dorsalJugador, textView_nombreJugador, textView_apellidosJugador, textView_equipoJugador, textView_posicionJugador;

        public ViewHolderJugadores(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            textView_nombreJugador = itemView.findViewById(R.id.textViewNombreJugador);
            textView_apellidosJugador = itemView.findViewById(R.id.textViewApellidosJugador);
            textView_equipoJugador = itemView.findViewById(R.id.textViewEquipoJugador);
            textView_posicionJugador = itemView.findViewById(R.id.textViewPosicionJugador);
            textView_dorsalJugador = itemView.findViewById(R.id.textViewDorsalJugador);

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

        public void bindData(Jugador jugador) {
            textView_nombreJugador.setText(jugador.getNombreJugador());
            textView_apellidosJugador.setText(jugador.getApellidosJugador());
            textView_equipoJugador.setText(jugador.getEquipoJugador());
            textView_posicionJugador.setText(jugador.getPosicionJugador());
            textView_dorsalJugador.setText(jugador.getDorsalJugador());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

}
