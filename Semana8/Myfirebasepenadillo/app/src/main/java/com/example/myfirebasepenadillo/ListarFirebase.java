package com.example.myfirebasepenadillo;

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
    private ListView lvmiscontactosfirebase;
    Vibrator vibrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_firebase);

        lvmiscontactosfirebase = (ListView) findViewById(R.id.lvmiscontactosfirebase);
        vibrar = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

        MyAdaptador myAdaptador = new MyAdaptador(getApplicationContext());
        lvmiscontactosfirebase.setAdapter(myAdaptador);

        lvmiscontactosfirebase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Contacto c = MainActivity.miscontactos.get(position);
                String nombre = c.getNombre();
                String alias = c.getAlias();
                String codigo = c.getCodigo();
                String key = c.getKey();
                String mensaje = nombre+" "+alias;
                Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainSubir.class);
                intent.putExtra("nombre",nombre);
                intent.putExtra("alias",alias);
                intent.putExtra("codigo",codigo);
                intent.putExtra("key",key);
                startActivity(intent);
            }
        });
    }
}