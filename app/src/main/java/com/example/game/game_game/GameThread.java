package com.example.game.game_game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceHolder;



public class GameThread extends Thread {
    private static final int MAX_FPS=50;
    private static Canvas canvas=null;
    private boolean running;
    public final SurfaceHolder surfaceHolder;
    private Game_Panel game_panel;

    GameThread(SurfaceHolder sh, Game_Panel gp){
        this.surfaceHolder=sh;
        this.game_panel=gp;
        Bitmap bmp=Bitmap.createBitmap(Constants.SCREEN_W,Constants.SCREEN_H, Bitmap.Config.ARGB_8888);
        canvas=new Canvas(bmp);

    }
    void setRunning(boolean bool){ this.running=bool;}

    @Override
    public void run() {
        long startTime;
        long waitTime;
        long timemillis;
        long targetTime=1000/MAX_FPS;

        int frameCount=0;
        while(running){
            startTime=System.nanoTime();
            canvas=null;
            try {
                canvas=this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.game_panel.update();
                    this.game_panel.draw(canvas);
                }
            }
            catch (Exception ignored){}
            finally {
                if(canvas!=null){
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception ignored){}
                }
            }
            timemillis=(System.nanoTime()-startTime)*1000000;
            waitTime=targetTime-timemillis;
            try {
                if(waitTime>0){
                    sleep(waitTime);
                }
            }catch (Exception ignored){}
            frameCount++;
            if(frameCount==MAX_FPS){
                frameCount=0;
            }
        }
    }
    void cancel(){
        interrupt();
    }

}
