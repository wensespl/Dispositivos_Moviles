package com.example.myapplicationhilos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnboton1,btnboton2,btnira2;
    TextView lbl1,lbl2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnboton1 = (Button) findViewById(R.id.btnboton1);
        btnboton2 = (Button) findViewById(R.id.btnboton2);
        btnira2 = (Button) findViewById(R.id.btnira2);
        lbl1 = (TextView) findViewById(R.id.lbl1);
        lbl2 = (TextView) findViewById(R.id.lbl2);

        btnboton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = "|";
                for(int i = 0;i<70;i++){
                    x = x + "|";
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    lbl1.setText(x);
                }
            }
        });

        btnboton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = "|";
                for(int i = 0;i<70;i++){
                    x = x + "|";
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    lbl2.setText(x);
                }
            }
        });

        btnira2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity2.class);
                startActivity(i);
            }
        });
    }
}