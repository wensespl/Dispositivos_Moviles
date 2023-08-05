package com.example.myapplicationhilos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {
    Button btnboton5,btnboton6,btnira4;
    TextView lbl5,lbl6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        btnboton5 = (Button) findViewById(R.id.btnboton5);
        btnboton6 = (Button) findViewById(R.id.btnboton6);
        btnira4 = (Button) findViewById(R.id.btnira4);
        lbl5 = (TextView) findViewById(R.id.lbl5);
        lbl6 = (TextView) findViewById(R.id.lbl6);

        btnboton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MiHilo2 miHiloa = new MiHilo2(lbl5,lbl6);
                miHiloa.start();
            }
        });

        btnboton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MiHilo2 miHilob = new MiHilo2(lbl6,lbl5);
                miHilob.start();
            }
        });

        btnira4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity4.class);
                startActivity(i);
            }
        });
    }

    private class MiHilo2 extends Thread{
        TextView lbl1,lbl2;
        public MiHilo2(TextView etiqueta1,TextView etiqueta2){
            lbl1 = etiqueta1;
            lbl2 = etiqueta2;
        }
        @Override
        public void run(){
            String x = "|";
            for(int i = 0;i<70;i++){
                x = x + "|";
                try {
                    Thread.sleep(20);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                lbl1.setText(x);
                String y = "|";
                for(int j = 0;j<70;j++){
                    y = y + "|";
                    try {
                        Thread.sleep(2);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    lbl2.setText(y);
                }
                /*lbl1.post(new Runnable() {
                    @Override
                    public void run() {
                        String y = "|";
                        for(int j = 0;j<70;j++){
                            y = y + "|";
                            try {
                                Thread.sleep(50);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                            lbl2.setText(y);
                        }
                    }
                });*/
            }
        }
    }
}