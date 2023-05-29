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
        ImageView imageView_imagen, imageView_resultado;
        TextView textView_rivalPartido, textView_sedePartido, textView_fechaPartido, textView_horaPartido;

        public ViewHolderPartidos(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            imageView_imagen = itemView.findViewById(R.id.imageViewPartido);
            textView_rivalPartido = itemView.findViewById(R.id.textViewRivalPartido);
            textView_sedePartido = itemView.findViewById(R.id.textViewSedePartido);
            textView_fechaPartido = itemView.findViewById(R.id.textViewFechaPartido);
            textView_horaPartido = itemView.findViewById(R.id.textViewHoraPartido);
            imageView_resultado = itemView.findViewById(R.id.imageViewResultado);

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
            textView_rivalPartido.setText(partido.getRivalPartido());
            textView_sedePartido.setText(partido.getSedePartido());
            textView_fechaPartido.setText(partido.getFechaPartido());
            textView_horaPartido.setText(partido.getHoraPartido());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
}
