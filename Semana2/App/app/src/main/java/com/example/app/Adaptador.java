package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import empresa.android.dao.ProductoDAO;

public class Adaptador extends BaseAdapter {

    Context context;
    ArrayList<ProductoDAO> compras = new ArrayList<ProductoDAO>();
    LayoutInflater inflater;

    public Adaptador(Context ctx, ArrayList<ProductoDAO> compras) {
        this.context = ctx;
        this.compras = compras;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return this.compras.size();
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
        view = inflater.inflate(R.layout.compraslistview, null);
        TextView txtmensajecompra = (TextView) view.findViewById(R.id.lblmensajecompra);
        ImageView imgicono = (ImageView) view.findViewById(R.id.imgicono);
        txtmensajecompra.setText(compras.get(i).getMensaje());
        if (compras.get(i).getObjProductoBean().getMarca() == 0) {
            imgicono.setImageResource(R.drawable.nike);
        }
        if (compras.get(i).getObjProductoBean().getMarca() == 1) {
            imgicono.setImageResource(R.drawable.adidas);
        }
        if (compras.get(i).getObjProductoBean().getMarca() == 2) {
            imgicono.setImageResource(R.drawable.fila);
        }
        return view;
    }
}
