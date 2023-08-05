package com.example.appthreadsgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
    Context context;

    private Thread gameThread = null;

    private SurfaceHolder ourHolder;

    private volatile boolean playing;

    private boolean paused = true;

    private Canvas canvas;
    private Paint paint;

    private long fps;

    private long timeThisFrame;

    private int screenX;
    private int screenY;

    private Player player;

    private Bullet bullet;

    Alien[] aliens = new Alien[60];
    int numAliens = 0;

    int score = 0;

    public GameView(Context context, int x, int y) {
        super(context);
        this.context = context;

        ourHolder = getHolder();
        paint = new Paint();

        screenX = x;
        screenY = y;

        prepareLevel();
    }

    private void prepareLevel(){
        player = new Player(context, screenX, screenY);

        // Prepare the players bullet

        // Initialize the invadersBullets array

        // Build an army of invaders

        // Build the shelters

    }

    @Override
    public void run() {
        while (playing) {

            long startFrameTime = System.currentTimeMillis();

            if (!paused) {
                update();
            }

            draw();

            timeThisFrame = System.currentTimeMillis() - startFrameTime;
            if (timeThisFrame >= 1) {
                fps = 1000 / timeThisFrame;
            }

            // We will do something new here towards the end of the project

        }
    }
    private void update(){

        boolean bumped = false;

        boolean lost = false;

        player.update(fps);

        // Update the invaders if visible

        // Update all the invaders bullets if active

        // Did an invader bump into the edge of the screen

        if(lost){
            prepareLevel();
        }

        // Update the players bullet

        // Has the player's bullet hit the top of the screen

        // Has an invaders bullet hit the bottom of the screen

        // Has the player's bullet hit an invader

        // Has an alien bullet hit a shelter brick

        // Has a player bullet hit a shelter brick

        // Has an invader bullet hit the player ship

    }

    private void draw(){
        if (ourHolder.getSurface().isValid()) {
            canvas = ourHolder.lockCanvas();

            canvas.drawColor(Color.argb(255, 26, 128, 182));

            paint.setColor(Color.argb(255,  255, 255, 255));

            canvas.drawBitmap(player.getBitmap(), player.getX(), screenY - 250, paint);

            // Draw the invaders

            // Draw the bricks if visible

            // Draw the players bullet if active

            // Draw the invaders bullets if active

            paint.setColor(Color.argb(255,  249, 129, 0));
            paint.setTextSize(40);
            canvas.drawText("Score: " + score, 10,50, paint);

            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            Log.e("Error:", "joining thread");
        }

    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }
}
