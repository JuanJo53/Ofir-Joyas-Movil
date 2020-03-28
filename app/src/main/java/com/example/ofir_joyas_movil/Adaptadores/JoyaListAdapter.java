package com.example.ofir_joyas_movil.Adaptadores;

import android.widget.*;
import android.content.*;
import android.view.*;

import androidx.annotation.NonNull;

import com.example.ofir_joyas_movil.Joya;

import java.util.List;

public class JoyaListAdapter extends ArrayAdapter<Joya> {
    public JoyaListAdapter(@NonNull Context context, int resource, @NonNull List<Joya> objects) {
        super(context, resource, objects);
    }
}
