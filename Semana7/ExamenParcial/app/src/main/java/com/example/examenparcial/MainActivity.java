package com.example.examenparcial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuprincipal,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menucalculadora:
                Intent calculadoraIntent = new Intent(getApplicationContext(), CalculadoraActivity.class);
                startActivity(calculadoraIntent);
                break;
            case R.id.menuclientes:
                Intent clientesIntent = new Intent(getApplicationContext(), ClientesActivity.class);
                startActivity(clientesIntent);
                break;
            case R.id.menufiltros:
                Intent filtrosIntent = new Intent(getApplicationContext(), FiltrosActivity.class);
                startActivity(filtrosIntent);
                break;
            default:
                break;
        }
        return true;
    }
}