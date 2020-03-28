package com.example.ofir_joyas_movil.ui.ventas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.ofir_joyas_movil.R;

public class VentasFragment extends Fragment {

    private VentasViewModel ventasViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        ventasViewModel =
                ViewModelProviders.of(this).get(VentasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ventas, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        ventasViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
