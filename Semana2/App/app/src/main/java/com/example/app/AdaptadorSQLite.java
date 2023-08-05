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

public class AdaptadorSQLite extends BaseAdapter {
    Context context;
    ArrayList<ProductoDAO> ventas;
    LayoutInflater inflater;

    public AdaptadorSQLite(Context ctx, ArrayList<ProductoDAO> ventas) {
        this.context = ctx;
        this.ventas = ventas;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return this.ventas.size();
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
        ventas.get(i).CalcularOperacion(ventas.get(i).getObjProductoBean());

        ImageView imgicono = (ImageView) view.findViewById(R.id.imgicono);
        txtmensajecompra.setText(ventas.get(i).getMensaje());
        if (ventas.get(i).getObjProductoBean().getMarca() == 0) {
            imgicono.setImageResource(R.drawable.nike);
        }
        if (ventas.get(i).getObjProductoBean().getMarca() == 1) {
            imgicono.setImageResource(R.drawable.adidas);
        }
        if (ventas.get(i).getObjProductoBean().getMarca() == 2) {
            imgicono.setImageResource(R.drawable.fila);
        }

        ImageView imgtipoventa = (ImageView) view.findViewById(R.id.imgtipoventa);
        if (ventas.get(i).getObjProductoBean().getTipoventa() == 0) {
            imgtipoventa.setImageResource(R.drawable.boleta);
        }
        if (ventas.get(i).getObjProductoBean().getTipoventa() == 1) {
            imgtipoventa.setImageResource(R.drawable.factura);
        }

        return view;
    }
}
