package com.example.pc3app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class ListarFirebase extends AppCompatActivity {
    private ListView lvmisjugadoresfirebase;
    Vibrator vibrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_firebase);

        lvmisjugadoresfirebase = (ListView) findViewById(R.id.lvmisjugadoresfirebase);
        vibrar = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

        MyAdaptador myAdaptador = new MyAdaptador(getApplicationContext());
        lvmisjugadoresfirebase.setAdapter(myAdaptador);

        lvmisjugadoresfirebase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Jugador j = MainActivity.jugadores.get(position);
                String nombre = j.getNombre();
                int puntaje = j.getPuntaje();
                String codigo = j.getCodigo();
                String key = j.getKey();
                String mensaje = nombre+" "+puntaje;
                Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainSubir.class);
                intent.putExtra("nombre",nombre);
                intent.putExtra("puntaje",puntaje);
                intent.putExtra("codigo",codigo);
                intent.putExtra("key",key);
                startActivity(intent);
                finish();
            }
        });
    }
}