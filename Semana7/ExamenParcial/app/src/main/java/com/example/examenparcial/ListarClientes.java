package com.example.examenparcial;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class ListarClientes extends AppCompatActivity {

    ListView lstvclientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_clientes);

        lstvclientes = (ListView) findViewById(R.id.lstvclientes);
        AdaptadorSQLite adaptadorSQLite = new AdaptadorSQLite(getApplicationContext(), ClientesActivity.con.listarClientes());
        lstvclientes.setAdapter(adaptadorSQLite);
    }
}