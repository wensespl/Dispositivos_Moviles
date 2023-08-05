package com.example.pc3app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
    Button btniniciarjuego, btnlistarpuntajes;
    DatabaseReference dbReference;
    FirebaseDatabase firebaseDatabase;
    String codigocel;
    Vibrator vibrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btniniciarjuego = (Button) findViewById(R.id.btniniciarjuego);
        btnlistarpuntajes = (Button) findViewById(R.id.btnlistarpuntajes);
        vibrar = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);

        codigocel = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbReference = firebaseDatabase.getReference();

        btniniciarjuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameintent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(gameintent);
            }
        });

        btnlistarpuntajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListarFirebase.class);
                startActivity(intent);
            }
        });

        dbReference.child("Jugador").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MainActivity.jugadores.clear();
                String codigoultimo = codigocel;

                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    Jugador j = objSnapshot.getValue(Jugador.class);
                    MainActivity.jugadores.add(j);
                    codigoultimo = j.getCodigo();
                }

                if(!codigocel.equals(codigoultimo)) {
                    vibrar.vibrate(700);
                }

                Toast.makeText(getApplicationContext(),"Datos cargados correctamente",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}