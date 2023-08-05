package com.example.myapplicationhilos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    Button btnboton3,btnboton4,btnira3;
    TextView lbl3,lbl4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btnboton3 = (Button) findViewById(R.id.btnboton3);
        btnboton4 = (Button) findViewById(R.id.btnboton4);
        btnira3 = (Button) findViewById(R.id.btnira3);
        lbl3 = (TextView) findViewById(R.id.lbl3);
        lbl4 = (TextView) findViewById(R.id.lbl4);

        btnboton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MiHilo miHilo = new MiHilo(lbl3);
                miHilo.start();
            }
        });

        btnboton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MiHilo miHilo = new MiHilo(lbl4);
                miHilo.start();
            }
        });

        btnira3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity3.class);
                startActivity(i);
            }
        });
    }

    private class MiHilo extends Thread{
        TextView lbl;
        public MiHilo(TextView etiqueta){
            lbl = etiqueta;
        }
        @Override
        public void run(){
            String x = "|";
            for(int i = 0;i<70;i++){
                x = x + "|";
                try {
                    Thread.sleep(200);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                lbl.setText(x);
            }
        }
    }
}