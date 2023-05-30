package com.hugocg21.bemanager.Menus.Estadisticas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugocg21.bemanager.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstadisticasEquidoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstadisticasEquidoFragment extends Fragment {
    public EstadisticasEquidoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estadisticas, container, false);
    }
}