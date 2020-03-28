package com.example.ofir_joyas_movil.ui.pedidos;

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

public class PedidosFragment extends Fragment {

    private PedidosViewModel pedidosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        pedidosViewModel =
                ViewModelProviders.of(this).get(PedidosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_pedidos, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        pedidosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
