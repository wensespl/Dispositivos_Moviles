package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import empresa.android.bean.ProductoBean;
import empresa.android.dao.ProductoDAO;

public class MainActivity extends AppCompatActivity {
    public static ConexionSQLite con = null;

    Spinner spnmarca, spntalla, spntipoventa;
    EditText txtnumpares, txtresultado;
    Button btncalcular, btnlistar;
    ProductoBean objproductoBean;
    ProductoDAO objproductoDAO;

    public static ArrayList<ProductoDAO> compras = new ArrayList<ProductoDAO>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnmarca = (Spinner) findViewById(R.id.spnmarca);
        spntalla = (Spinner) findViewById(R.id.spntalla);
        spntipoventa = (Spinner) findViewById(R.id.spntipoventa);
        txtnumpares = (EditText) findViewById(R.id.txtnumpares);
        txtresultado = (EditText) findViewById(R.id.txtresultado);
        btncalcular = (Button) findViewById(R.id.btncalcular);
        btncalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calcular();
            }
        });
        btnlistar = (Button) findViewById(R.id.btnlistar);
        con = new ConexionSQLite(this);
        btnlistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListarCompras.class);
                startActivity(intent);
            }
        });
    }

    public void calcular(){
        int marca = spnmarca.getSelectedItemPosition();
        int talla = spntalla.getSelectedItemPosition();
        int tipoventa = spntipoventa.getSelectedItemPosition();
        String numparescad = txtnumpares.getText().toString();
        int numpares = Integer.parseInt(numparescad);

        objproductoBean = new ProductoBean();
        objproductoBean.setMarca(marca);
        objproductoBean.setTalla(talla);
        objproductoBean.setTipoventa(tipoventa);
        objproductoBean.setNumpares(numpares);

        objproductoDAO = new ProductoDAO();
        String mensaje = objproductoDAO.CalcularOperacion(objproductoBean);
        con.insertarVenta(objproductoDAO);
        txtresultado.setText(mensaje);
        Toast.makeText(getApplicationContext(), "OK Insertado", Toast.LENGTH_LONG).show();
        //compras.add(objproductoDAO);

        /*
        Intent intent = new Intent(getApplicationContext(), MainReporte.class);
        intent.putExtra("mensaje", mensaje);
        startActivity(intent);
         */
    }
}