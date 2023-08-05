package com.example.examenparcial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import empresa.android.ClienteBean;

public class AdaptadorSQLite extends BaseAdapter {
    Context context;
    ArrayList<ClienteBean> clientes;
    LayoutInflater inflater;

    public AdaptadorSQLite(Context ctx, ArrayList<ClienteBean> clientes) {
        this.context = ctx;
        this.clientes = clientes;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return this.clientes.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.clienteslistview, null);
        TextView txtcliente = (TextView) view.findViewById(R.id.lblcliente);
        clientes.get(i).generarTexto();
        txtcliente.setText(clientes.get(i).getTextocliente());
        ImageView imgicono = (ImageView) view.findViewById(R.id.imgicono);
        imgicono.setImageResource(R.drawable.clienteicon);

        return view;
    }
}
