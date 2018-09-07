package com.example.game.game_game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView score_view=(TextView) findViewById(R.id.score);
        Intent i=getIntent();
        String score=i.getStringExtra("Score");
        if(score!=null){
        score_view.setText("GAME OVER YOUR SCORE="+ score);
        }

        Button play=(Button)findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Game.class);
                startActivity(i);
            }
        });
    }
}
