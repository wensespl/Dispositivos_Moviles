package com.example.pc3app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GuardarActivity extends AppCompatActivity {
    EditText txtnombre;
    TextView txtpuntaje;
    Button btnguardarfirebase;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    String codigocel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardar);

        txtnombre = (EditText) findViewById(R.id.txtnombre);
        txtpuntaje = (TextView) findViewById(R.id.txtpuntaje);
        btnguardarfirebase = (Button) findViewById(R.id.btnguardarfirebase);

        codigocel = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        Intent iin= getIntent();
        Bundle b = iin.getExtras();
        if(b!=null){
            int puntaje = (int) b.get("puntaje");
            txtpuntaje.setText(""+puntaje);
        }

        btnguardarfirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtnombre.getText().toString();
                int puntaje = Integer.valueOf(txtpuntaje.getText().toString());
                String codigo = codigocel;

                Jugador j = new Jugador(nombre, puntaje, 0);

                String keyfirebase = databaseReference.push().getKey();

                j.setKey(keyfirebase);
                j.setCodigo(codigo);

                databaseReference.child("Jugador").child(keyfirebase).setValue(j);
                Toast.makeText(getApplicationContext(), "Puntaje Guardado", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}