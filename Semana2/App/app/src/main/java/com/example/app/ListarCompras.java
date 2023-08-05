package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import empresa.android.dao.ProductoDAO;

public class ListarCompras extends AppCompatActivity {

    ListView lstvcompras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_compras);

        lstvcompras = (ListView) findViewById(R.id.lstvcompras);
        //Adaptador adaptadorSQLite = new Adaptador(getApplicationContext(), MainActivity.compras);
        AdaptadorSQLite adaptadorSQLite = new AdaptadorSQLite(getApplicationContext(), MainActivity.con.listarVentas());
        lstvcompras.setAdapter(adaptadorSQLite);
        lstvcompras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                ArrayList<ProductoDAO> ventas = MainActivity.con.listarVentas();
                Intent intent = new Intent(getApplicationContext(), MainUpdateCompra.class);
                intent.putExtra("marca", ventas.get(i).getObjProductoBean().getMarca());
                intent.putExtra("talla", ventas.get(i).getObjProductoBean().getTalla());
                intent.putExtra("tipoventa", ventas.get(i).getObjProductoBean().getTipoventa());
                intent.putExtra("numpares", ventas.get(i).getObjProductoBean().getNumpares());
                intent.putExtra("idventa", ventas.get(i).getObjProductoBean().getIdventa());
                startActivity(intent);
            }
        });

//        lstvcompras.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                ArrayList<ProductoDAO> ventas = MainActivity.con.listarVentas();
//                MainActivity.con.eliminarVenta(ventas.get(position).getObjProductoBean().getIdventa());
//                String mensaje = "Venta eliminada";
//                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
//                lstvcompras.setAdapter(adaptadorSQLite);
//                return false;
//            }
//        });
    }
}