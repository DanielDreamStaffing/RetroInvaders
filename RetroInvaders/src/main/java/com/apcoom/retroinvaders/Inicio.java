package com.apcoom.retroinvaders;

import android.app.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
/**
 * Created by Daniel on 6/10/13.
 */
public class Inicio extends Activity{
    private Variables var= new Variables();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
        btnPlay();
        btnScores();
        btnCredits();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void btnPlay()
    {
        ImageButton play = (ImageButton) findViewById(R.id.btnPlay);
        play.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view){
                //startActivity(new Intent(MainActivity.this,selectSpacecraft.class));
                startActivity(new Intent(Inicio.this,SelectSpacecraft.class));
            }
        });
    }

    private void btnScores()
    {
        ImageButton scores = (ImageButton) findViewById(R.id.btnScores);
        scores.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view){
                startActivity(new Intent(Inicio.this,Score.class));
            }
        });
    }

    private void btnCredits()
    {
        ImageButton credits = (ImageButton) findViewById(R.id.btnCredits);
        credits.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view){
                startActivity(new Intent(Inicio.this,Credits.class));
            }
        });
    }
}

