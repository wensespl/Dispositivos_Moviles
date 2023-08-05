package com.example.pc4clientesapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc4clientesapi.Models.Cliente;

import java.util.List;

public class MiAdaptadorCliente extends BaseAdapter {
    Context context;
    List<Cliente> clientes;

    MiAdaptadorCliente(Context c, List<Cliente> cs){
        this.context = c;
        this.clientes = cs;
    }

    @Override
    public int getCount(){
        return clientes.size();
    }

    @Override
    public Object getItem(int position) {
        return clientes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View fila;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            fila = inflater.inflate(R.layout.misfilasclientes,null);
        }else{
            fila = convertView;
        }
        TextView txtminombreweb = fila.findViewById(R.id.txtminombreweb);
        TextView txtmidniweb = fila.findViewById(R.id.txtmidniweb);
        TextView txtmitelefonoweb = fila.findViewById(R.id.txtmitelefonoweb);
        TextView txtmicorreoweb = fila.findViewById(R.id.txtmicorreoweb);
        TextView txtmitipoclienteweb = fila.findViewById(R.id.txtmitipoclienteweb);
        ImageView imgImagen = fila.findViewById(R.id.imgimagenweb);

        txtminombreweb.setText(clientes.get(position).getNombre());
        txtmidniweb.setText(clientes.get(position).getDni());
        txtmitelefonoweb.setText(clientes.get(position).getTelefono());
        txtmicorreoweb.setText(clientes.get(position).getCorreo());
        txtmitipoclienteweb.setText(clientes.get(position).getTipoCliente().getNombre());

        /*String estado = clientes.get(position).getEstado();
        if (estado.equals("A"))
            imgImagen.setImageResource(R.drawable.A);
        else
            imgImagen.setImageResource(R.drawable.I);
        */
        return fila;
    }
}
