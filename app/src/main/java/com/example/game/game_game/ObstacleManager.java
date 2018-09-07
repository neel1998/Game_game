package com.example.game.game_game;

import android.graphics.Canvas;

import java.util.ArrayList;



class ObstacleManager {

    ArrayList<Obstacle> obstacles;
    int speed;
    private int gap;
    private int gap_speed;
    private int gap_width;
    ObstacleManager(int speed, int gap, int gw){
        this.speed=speed;
        this.gap=gap;
        this.gap_speed=speed/2;
        this.gap_width=gw;
//        fill();

    }
    void fill(Player player){
        if(player.level%3==1){
            obstacles=new ArrayList<>();
            int n=Constants.SCREEN_H/gap;
            int i;
            for(i=0;i<=n;i++){
                 int rand=(int)(Math.random()*(Constants.SCREEN_W-200+1));
                 obstacles.add(new Obstacle(1,Constants.SCREEN_W/2,Constants.SCREEN_H/2+i*gap,rand,gap_speed,gap_width));
            }
            obstacles.add(new Obstacle(-1,0,Constants.SCREEN_H/2+i*gap,0,gap_speed,gap_width));
        }
        else if(player.level%3==2){
            obstacles=new ArrayList<>();
            int n=Constants.SCREEN_H/gap;
            int i;
            for(i=0;i<=n;i++){
                obstacles.add(new Obstacle(2,0,Constants.SCREEN_H/2+i*gap,System.currentTimeMillis()+i*500));
            }
            obstacles.add(new Obstacle(-1,0,Constants.SCREEN_H/2+i*gap,0,gap_speed,gap_width));
        }
        else{
            obstacles=new ArrayList<>();
            int n=Constants.SCREEN_H/gap;
            obstacles.add(new Obstacle(3,0,Constants.SCREEN_H/2,System.currentTimeMillis()));
            obstacles.add(new Obstacle(-1,0,Constants.SCREEN_H+(n+1)*gap,0,gap_speed,gap_width));
        }
    }
    void update(Player player){
        for(Obstacle ob:obstacles){
            if (ob.gap >= Constants.SCREEN_W / 2 - player.bitmap.getWidth()/2 || ob.gap + ob.gap_width <= Constants.SCREEN_W / 2 + player.bitmap.getWidth()/2) {
                if(ob.type==1) {
                    if (player.posy <= ob.posy && player.posy + player.bitmap.getHeight() >= ob.posy && ob.type != -1) {
                        player.killed = true;
                        break;
                    }
                }
                else if(ob.type==2){
                    if(player.posy+player.bitmap.getHeight()>=ob.posy+25 && player.posy<=ob.posy+25 && ob.lazer_on){
                        player.killed= true;
                        break;
                    }
                }
            }
            if(ob.posy<=0){
                obstacles.remove(ob);
            }
            if(ob.type!=3) {
                ob.posy -= speed;
                if(ob.gap>=Constants.SCREEN_W){
                    ob.gap_speed=0-ob.gap_speed;
                    ob.gap-=10;
                }
                if(ob.gap<0){
                    ob.gap_speed=0-ob.gap_speed;
                    ob.gap+=10;
                }
                ob.gap+=ob.gap_speed;
            }
            else if(ob.type==3){
                ob.posx+=1;
            }

        }
    }
    void draw(Canvas canvas){
        for (Obstacle ob:obstacles){
            ob.draw(canvas);
        }
    }
}
