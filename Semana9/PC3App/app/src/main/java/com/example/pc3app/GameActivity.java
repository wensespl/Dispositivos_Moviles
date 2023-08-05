package com.example.pc3app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {
    ImageView invader, player, bullet;
    TextView lblpuntaje;
    Button btnstart, btnguardar;
    float ancho, alto;
    int puntaje = 0;
    boolean jugando = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        invader = (ImageView) findViewById(R.id.imginvader);
        player = (ImageView) findViewById(R.id.imgplayer);
        bullet = (ImageView) findViewById(R.id.imgbullet);
        lblpuntaje = (TextView) findViewById(R.id.lblpuntaje);
        btnstart = (Button) findViewById(R.id.btnstart);
        btnguardar = (Button) findViewById(R.id.btnguardar);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        ancho = size.x;
        alto = size.y;

        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MiHilo miHilo = new MiHilo(lblpuntaje);
                miHilo.start();
            }
        });

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!jugando) {
                    GameLoop gameLoop = new GameLoop();
                    gameLoop.start();
                    btnstart.setText("Stop");
                } else {
                    btnstart.setText("Play");
                    jugando = false;
                }
            }
        });

        btnguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guardar = new Intent(getApplicationContext(), GuardarActivity.class);
                guardar.putExtra("puntaje", puntaje);
                startActivity(guardar);
                finish();
            }
        });
    }

    private class MiHilo extends Thread {
        TextView lbl;
        public MiHilo(TextView etiqueta){
            lbl = etiqueta;
        }
        @Override
        public void run(){
            if(!jugando) return;
            bullet.setX(player.getX() + (int)(player.getWidth() / 2) - (int)(bullet.getWidth() / 2));
            bullet.setY(player.getY());

            int xb, yb = (int)bullet.getY();
            int xi, yi;
            float iwidth = invader.getWidth();
            float iheight = invader.getHeight();

            for (int y = yb; y > 0; y--) {
                yb = (int)bullet.getY();
                xb = (int)bullet.getX();
                yi = (int)invader.getY();
                xi = (int)invader.getX();

                if (xb>=xi && xb<=xi+iwidth && yb>=yi && yb<=yi+iheight) {
                    puntaje += 1 ;
                    jugando = false;
                    break;
                }

                try {
                    Thread.sleep(1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                bullet.setY(y);
            }

            String x = "Puntaje: " + puntaje;
            lbl.setText(x);

            bullet.setX(player.getX() + (int)(player.getWidth() / 2) - (int)(bullet.getWidth() / 2));
            bullet.setY(player.getY());
        }
    }

    private class GameLoop extends Thread {
        boolean sentido = true;
        private float x, y;

        @Override
        public void run(){
            invader.setX(ancho/2 - (int)(invader.getWidth()/2));
            invader.setY(200);

            x = invader.getX();
            y = invader.getY();

            jugando = true;
            while (jugando) {
                if (x<= 0 || x>= ancho-invader.getWidth()) {
                    sentido = !sentido;
                    y += 10;
                    invader.setY(y);
                }

                if (y > alto * 0.7) {
                    jugando = false;
                }

                x = x + (sentido?1:-1);
                invader.setX(x);

                try {
                    Thread.sleep(2);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

            invader.setX(ancho/2 - (int)(invader.getWidth()/2));
            invader.setY(200);
        }
    }
}