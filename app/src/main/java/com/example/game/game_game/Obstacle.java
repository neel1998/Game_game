package com.example.game.game_game;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.ArcShape;


class Obstacle {
    int type;
    int posx;
    int posy;
    int gap;
    int gap_speed;
    int gap_width;
    double time;
    public boolean lazer_on=false;

    Obstacle(int t, int x, int y, int g, int gs, int gw){
        this.type=t;
        this.posx=x;
        this.posy=y;
        this.gap=g;
        this.gap_speed=gs;
        this.gap_width=gw;
    }
    Obstacle(int t,int x,int y,double time){
        this.type=t;
        this.posx=x;
        this.posy=y;
        this.time=time;
    }
    void draw(Canvas canvas){
        if(this.type==1){
            draw_type1(canvas);
        }
        else if(this.type==2){
            draw_type2(canvas);
        }
        else if(this.type==3){
            draw_type3(canvas);
        }
        else if(this.type==-1){
            draw_level(canvas);
        }
    }
    private void draw_level(Canvas canvas){
        Rect rect1=new Rect(this.posx,this.posy,this.posx+Constants.SCREEN_W/2-150,this.posy+200);
        Rect rect2=new Rect(Constants.SCREEN_W/2+150,this.posy,Constants.SCREEN_W,this.posy+200);
        Paint p=new Paint();
        p.setColor(Color.WHITE);
        canvas.drawRect(rect1,p);
        canvas.drawRect(rect2,p);
    }
    private void draw_type1(Canvas canvas){
        Rect rect1=new Rect(0,this.posy,this.gap,this.posy+50);
        Rect rect2=new Rect(this.gap+gap_width,this.posy,Constants.SCREEN_W,this.posy+50);
        Paint p=new Paint();
        p.setColor(Color.parseColor("#83f52c"));
        canvas.drawRect(rect1,p);
        canvas.drawRect(rect2,p);
    }
    private void draw_type2(Canvas canvas){
        Rect rect1=new Rect(0,this.posy,50,this.posy+50);
        Rect rect2=new Rect(Constants.SCREEN_W-50,this.posy,Constants.SCREEN_W,this.posy+50);
        Paint p1=new Paint();
        p1.setColor(Color.RED);

        Paint p2=new Paint();
        p2.setColor(Color.WHITE);
        p2.setStrokeWidth(10);

        canvas.drawRect(rect1,p1);
        canvas.drawRect(rect2,p1);
        if(System.currentTimeMillis()-this.time>1000 ){
//            this.time=System.currentTimeMillis();
            lazer_on=true;
            canvas.drawLine(50,this.posy+25,Constants.SCREEN_W-50,this.posy+25,p2);
            if(System.currentTimeMillis()-this.time>2000){
                this.time=System.currentTimeMillis();
                lazer_on=false;
            }
        }
    }
    private void draw_type3(Canvas canvas){
        int stroke_width=50;
        Paint p=new Paint();
        p.setColor(Color.WHITE);
        p.setStrokeWidth(stroke_width);
        p.setStyle(Paint.Style.STROKE);
        RectF oval=new RectF();
        int top_gap=(Constants.SCREEN_H-Constants.SCREEN_W+20+2*stroke_width)/2;
        oval.set(stroke_width+10,top_gap,Constants.SCREEN_W-stroke_width-10,Constants.SCREEN_H-top_gap);
        canvas.drawArc(oval,this.posx,360-45,false,p);
    }
}
