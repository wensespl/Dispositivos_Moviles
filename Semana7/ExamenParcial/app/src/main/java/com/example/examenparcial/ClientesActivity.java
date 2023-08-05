package com.example.examenparcial;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import empresa.android.ClienteBean;

public class ClientesActivity extends AppCompatActivity {

    public static ConexionSQLite con = null;

    EditText txtnombre, txtdni;
    Button btnaddcliente, btnlistar;
    ClienteBean clienteBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);

        txtnombre = (EditText) findViewById(R.id.textnombre);
        txtdni = (EditText) findViewById(R.id.textdni);
        btnaddcliente = (Button) findViewById(R.id.btnaddcliente);
        btnlistar = (Button) findViewById(R.id.btnlistar);
        con = new ConexionSQLite(this);

        btnaddcliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtnombre.getText().toString();
                int dni = Integer.parseInt(txtdni.getText().toString());

                clienteBean = new ClienteBean();
                clienteBean.setNombre(nombre);
                clienteBean.setDni(dni);

                con.insertarCliente(clienteBean);

                Toast.makeText(getApplicationContext(), "OK Insertado", Toast.LENGTH_LONG).show();
            }
        });

        btnlistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListarClientes.class);
                startActivity(intent);
            }
        });
    }

    public void regresar(View view) {
        finish();
    }
}