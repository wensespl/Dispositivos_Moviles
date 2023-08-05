package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainReporte extends AppCompatActivity {

    EditText txtreporte;
    Button btnregresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_reporte);

        txtreporte = (EditText) findViewById(R.id.txtreporte);
        btnregresar = (Button) findViewById(R.id.btnregresar);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b!= null) {
            String mensaje = (String) b.get("mensaje");
            txtreporte.setText(mensaje);
        }

        btnregresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
            }
        });
    }
}