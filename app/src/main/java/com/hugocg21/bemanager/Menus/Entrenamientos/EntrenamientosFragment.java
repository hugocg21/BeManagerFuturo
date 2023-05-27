package com.hugocg21.bemanager.Menus.Entrenamientos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hugocg21.bemanager.R;

public class EntrenamientosFragment extends Fragment {

    public EntrenamientosFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_entrenamientos, container, false);
    }
}