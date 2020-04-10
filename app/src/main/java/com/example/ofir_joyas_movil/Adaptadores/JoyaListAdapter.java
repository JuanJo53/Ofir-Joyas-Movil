package com.example.ofir_joyas_movil.Adaptadores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.*;
import android.content.*;
import android.view.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ofir_joyas_movil.Entidades.Joya;
import com.example.ofir_joyas_movil.R;

import java.util.ArrayList;

public class JoyaListAdapter extends ArrayAdapter<Joya> {
    Context context;
    ArrayList<Joya>joyas;
    public JoyaListAdapter(Context context, ArrayList<Joya> joyas) {
        super(context, R.layout.layout_item_joya, joyas);
        this.context = context;
        this.joyas = joyas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_item_joya, parent, false);

        Joya joya = joyas.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(joya.getFoto(),0,joya.getFoto().length);
        ImageView imageJoya = (ImageView) view.findViewById(R.id.ivJoya);
        imageJoya.setImageBitmap(bitmap);

        TextView tvnombrejoya = (TextView) view.findViewById(R.id.tvNombreJoya);
        tvnombrejoya.setText(joya.getNombre());

        TextView tvpreciojoya = (TextView) view.findViewById(R.id.tvPrecioJoya);
        tvpreciojoya.setText(joya.getPrecio().toString()+"Bs.");

        TextView tvstockjoya = (TextView) view.findViewById(R.id.tvStockJoya);
        tvstockjoya.setText(String.valueOf(joya.getStock()));
        return view;
    }
}
