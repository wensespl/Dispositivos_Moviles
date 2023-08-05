package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import empresa.android.bean.ProductoBean;
import empresa.android.dao.ProductoDAO;

public class MainUpdateCompra extends AppCompatActivity {

    public static ConexionSQLite con = null;

    Spinner spnnewmarca, spnnewtalla, spnnewtipoventa;
    EditText txtnewnumpares;
    Button btnupdateventa;
    ProductoBean objproductoBean;
    ProductoDAO objproductoDAO;
    int idventa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_update_compra);

        spnnewmarca = (Spinner) findViewById(R.id.spnnewmarca);
        spnnewtalla = (Spinner) findViewById(R.id.spnnewtalla);
        spnnewtipoventa = (Spinner) findViewById(R.id.spnnewtipoventa);
        txtnewnumpares = (EditText) findViewById(R.id.txtnewnumpares);
        con = new ConexionSQLite(this);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b!= null) {
            int marca = (int) b.get("marca");
            int talla = (int) b.get("talla");
            int tipoventa = (int) b.get("tipoventa");
            int numpares = (int) b.get("numpares");
            String numparestxt = ""+numpares;
            idventa = (int) b.get("idventa");

            spnnewmarca.setSelection(marca);
            spnnewtalla.setSelection(talla);
            spnnewtipoventa.setSelection(tipoventa);
            txtnewnumpares.setText(numparestxt, TextView.BufferType.EDITABLE);
        }

        btnupdateventa = (Button) findViewById(R.id.btnupdatecompra);
        btnupdateventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });
    }

    public void actualizar(){
        int newmarca = spnnewmarca.getSelectedItemPosition();
        int newtalla = spnnewtalla.getSelectedItemPosition();
        int newtipoventa = spnnewtipoventa.getSelectedItemPosition();
        String newnumparescad = txtnewnumpares.getText().toString();
        int newnumpares = Integer.parseInt(newnumparescad);

        objproductoBean = new ProductoBean();
        objproductoBean.setMarca(newmarca);
        objproductoBean.setTalla(newtalla);
        objproductoBean.setTipoventa(newtipoventa);
        objproductoBean.setNumpares(newnumpares);

        objproductoDAO = new ProductoDAO();
        String mensaje = objproductoDAO.CalcularOperacion(objproductoBean);
        con.modificarVenta(objproductoDAO, this.idventa);

        Toast.makeText(getApplicationContext(), "OK Venta Actualizada", Toast.LENGTH_LONG).show();
    }
}