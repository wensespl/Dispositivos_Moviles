package com.example.myfirebasepenadillo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.provider.Settings.Secure;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Contacto> miscontactos = new ArrayList<Contacto>();
    EditText txtnombre, txtalias;
    Button btnagregarfirebase,btnlistarfarebase;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    Vibrator vibrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtnombre = (EditText) findViewById(R.id.txtnombre);
        txtalias = (EditText) findViewById(R.id.txtalias);
        vibrar = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
        btnagregarfirebase = (Button) findViewById(R.id.btnagregarfirebase);
        btnlistarfarebase = (Button) findViewById(R.id.btnlistarfirebase);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        btnagregarfirebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txtnombre.getText().toString();
                String alias = txtalias.getText().toString();
                String codigo = Secure.getString(getBaseContext().getContentResolver(), Secure.ANDROID_ID);
                Contacto c = new Contacto(nombre, alias, 0);
                String keyfirebase = databaseReference.push().getKey();
                c.setKey(keyfirebase);
                c.setCodigo(codigo);
                databaseReference.child("Contacto").child(keyfirebase).setValue(c);
                Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
            }
        });

        btnlistarfarebase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListarFirebase.class);
                startActivity(intent);
            }
        });

        databaseReference.child("Contacto").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                MainActivity.miscontactos.clear();
                String codigo = Settings.Secure.getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                String codigoultimo = "";
                for( DataSnapshot objSnapshot : snapshot.getChildren()){
                    Contacto c = objSnapshot.getValue(Contacto.class);
                    MainActivity.miscontactos.add(c);
                    codigoultimo = c.getCodigo();
                }
                if (!codigo.equals(codigoultimo)){
                    vibrar.vibrate(700);
                }
                Toast.makeText(getApplicationContext(),"Datos cargados correctamente",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}