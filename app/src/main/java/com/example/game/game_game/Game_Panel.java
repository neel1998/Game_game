package com.example.game.game_game;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Game_Panel extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread thread;
    public Player player;
    private ObstacleManager obstacleManager;
    public boolean move=true;
    public static Bitmap bitmap;
    public static int speed;
    public int degree=5;
    public int ob_gap=500;
    public boolean level_change=false;
    public int t=1;


    public Game_Panel(Context context) {
        super(context);
        speed=10;
        getHolder().addCallback(this);
        thread=new GameThread(getHolder(),this);
        bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.football2);
        player=new Player(Color.rgb(255,0,0),0,bitmap,speed);
        player.score=0;
        int gap_width = 300;
        obstacleManager=new ObstacleManager(speed,ob_gap, gap_width);
        obstacleManager.fill(player);
        player.killed=false;
        setFocusable(true);
    }


    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread=new GameThread(getHolder(),this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        thread.setRunning(false);
        thread.cancel();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            player.speed=0;
            obstacleManager.speed=0;
        }
        else{
            if(t==1){
                 player.speed=speed;
            }
            else{
                player.speed=0;
            }
            obstacleManager.speed=speed;
        }
        return true;
    }

    public void update() throws InterruptedException {
        if(player.killed){
            thread.setRunning(false);
            Thread.sleep(1000);
            thread.cancel();
            Intent i=new Intent(getContext(),MainActivity.class);
            i.putExtra("Score",String.valueOf(player.score));
            getContext().startActivity(i);
        }
        player.matrix=rotate(bitmap,degree);
        degree+=10;
        if(player.posy>obstacleManager.obstacles.get(obstacleManager.obstacles.size()-1).posy){
            level_change=true;
        }
        if(level_change){
            if(t==1){
                obstacleManager.speed=speed*3;
                player.speed=speed*(-3);
                move=false;
            }
        }
       if(obstacleManager.obstacles.get(obstacleManager.obstacles.size()-1).posy<=0){
           player.level+=1;
             obstacleManager.speed=speed;
             obstacleManager.fill(player);
             level_change=false;
             player.speed=speed;
             t=1;
             move=true;
        }
        player.update();
        obstacleManager.update(player);
    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.BLACK);
        Paint p=new Paint();
        p.setColor(Color.WHITE);
        player.draw(canvas);
        obstacleManager.draw(canvas);
    }
    public Matrix rotate(Bitmap source,int degree){
        Matrix matrix=new Matrix();
        matrix.postRotate(degree,source.getWidth()/2,source.getHeight()/2);
        matrix.postTranslate(Constants.SCREEN_W/2-player.bitmap.getWidth()/2,player.posy);
        return matrix;
    }

}
