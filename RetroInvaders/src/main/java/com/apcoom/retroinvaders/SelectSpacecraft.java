package com.apcoom.retroinvaders;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Daniel on 11/09/13.
 */
public class SelectSpacecraft extends Activity{
    private Variables var= new Variables();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_spacecraft);
        btnNave1();
        btnNave5();
        btnNaveS();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void btnNave1()
    {
        ImageButton nave1 = (ImageButton) findViewById(R.id.btnNave1);
        nave1.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view){
                var.setNave(1);
                startActivity(new Intent(SelectSpacecraft.this,MainActivity.class));
            }
        });
    }

    private void btnNave5()
    {
        ImageButton nave5 = (ImageButton) findViewById(R.id.btnNave5);
        nave5.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view){
                var.setNave(2);
                startActivity(new Intent(SelectSpacecraft.this,MainActivity.class));
            }
        });
    }

    private void btnNaveS()
    {
        ImageButton naves = (ImageButton) findViewById(R.id.btnNaveS);
        naves.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view){
                var.setNave(3);
                startActivity(new Intent(SelectSpacecraft.this,MainActivity.class));
            }
        });
    }

}

