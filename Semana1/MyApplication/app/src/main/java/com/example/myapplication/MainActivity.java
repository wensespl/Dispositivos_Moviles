package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnmensaje;
    TextView lblmensaje;
    EditText txtnombre;
    EditText txtedad;
    int n = 0;
    public static ArrayList<Contacto> miscontactos = new ArrayList<Contacto>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnmensaje = (Button) findViewById(R.id.btnmensaje);
        lblmensaje = (TextView) findViewById(R.id.lblmensaje);
        txtnombre = (EditText) findViewById(R.id.txtnombre);
        txtedad = (EditText) findViewById(R.id.txtedad);

        btnmensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n = n + 1;
                String nombre = txtnombre.getText().toString();
                int edad = Integer.parseInt( txtedad.getText().toString());
                miscontactos.add(new Contacto(n, nombre, edad));
                String mensaje = "Contacto " + n + "Agregado";
                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
                lblmensaje.setText(mensaje);
                for (int i = 0 ; i< miscontactos.size();i++) {
                    Contacto c = miscontactos.get(i);
                    String contacto = c.getIdcontacto() + " " + c.getNombre() + " " + c.getEdad();
                    Log.i("Contacto: ", contacto);
                }
                //Log.i("mensaje", mensaje);
            }
        });
    }
}