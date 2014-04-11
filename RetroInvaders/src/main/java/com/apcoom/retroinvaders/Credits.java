package com.apcoom.retroinvaders;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;

public class Credits extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credits);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnReturn();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void btnReturn()
    {
        ImageButton regresar = (ImageButton) findViewById(R.id.btnReturn);
        regresar.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view){
                startActivity(new Intent(Credits.this,Inicio.class));
            }
        });
    }
}

