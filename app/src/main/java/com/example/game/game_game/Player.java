package com.example.game.game_game;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;


class Player {
    Bitmap bitmap;
    Matrix matrix;
    private int color;
    int posy;
    int level;
    boolean killed=false;
    int score=0;
    int speed;
    Player(int color, int y, Bitmap bitmap, int s){
        this.bitmap=bitmap;
        this.color=color;
        this.posy=y;
        this.level=3;
        this.speed=s;

    }
    void draw(Canvas canvas){
        Paint paint=new Paint();
        paint.setColor(this.color);
        paint.setTextSize(30);
        canvas.drawBitmap(this.bitmap, matrix,paint);
        canvas.drawText("Level:"+String.valueOf(this.level),10,40,paint);
        canvas.drawText("Score:"+String.valueOf(this.score),Constants.SCREEN_W/2-100,40,paint);
    }
    void update(){
        if(this.speed!=0){
            this.score+=1;
        }
        this.posy+=this.speed;

    }
}
