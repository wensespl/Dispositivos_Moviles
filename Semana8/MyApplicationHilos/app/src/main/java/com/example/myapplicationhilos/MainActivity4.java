package com.example.myapplicationhilos;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity4 extends AppCompatActivity {
    private float ancho = 0;
    //private float alto = 0;
    Button btnboton7,btnboton8;
    TextView lbl7;
    private int puntaje = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        btnboton7 = (Button) findViewById(R.id.btnboton7);
        btnboton8 = (Button) findViewById(R.id.btnboton8);
        lbl7 = (TextView) findViewById(R.id.lbl7);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ancho = size.x;
        //alto = size.y;

        btnboton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MiHilo3 miHilo3 = new MiHilo3();
                miHilo3.start();
            }
        });

        btnboton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lbl7.setX(btnboton8.getX() + (int)(btnboton8.getWidth() / 2));
                lbl7.setY(btnboton8.getY());
                MiDisparo midisparo = new MiDisparo();
                midisparo.start();
                setPuntaje();
            }
        });
    }

    public void setPuntaje(){
        btnboton8.setText(String.valueOf(puntaje));
    }

    private class MiHilo3 extends Thread{
        private float x = btnboton7.getX();
        @Override
        public void run(){
            boolean sentido = true;
            while (true){
                if (x<=0 || x>=(ancho-btnboton7.getWidth())){
                    sentido = !sentido;
                }
                x = x + (sentido?1:-1);
                btnboton7.setX(x);
                try {
                    Thread.sleep(2);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }
    private class MiDisparo extends Thread{
        @Override
        public void run(){
            int xe = (int)lbl7.getX();
            int ye = (int)lbl7.getY();
            int xb;
            int yb;
            for (int y = ye;y>0;y--){
                xb = (int)btnboton7.getX();
                yb = (int)btnboton7.getY();
                if (ye>=yb && ye<=(yb+btnboton7.getHeight()) && xe>=xb && xe<=(xb+btnboton7.getWidth())){
                    puntaje += 1;
//                    btnboton8.setText(""+puntaje);
//                    Toast.makeText(getApplicationContext(),""+puntaje, Toast.LENGTH_LONG).show();
                    break;
                }
                try {
                    Thread.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                lbl7.setY(y);
            }
            lbl7.setX(btnboton8.getX() + (int)(btnboton8.getWidth() / 2));
            lbl7.setY(btnboton8.getY());
        }
    }
}