package com.apcoom.retroinvaders;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class Score extends Activity {
    private TextView fila1,fila2,fila3,fila4,fila5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);
        fila1 = (TextView) findViewById(R.id.textPrimero);
        fila2 = (TextView) findViewById(R.id.textSegundo);
        fila3 = (TextView) findViewById(R.id.textTercero);
        fila4 = (TextView) findViewById(R.id.textCuarto);
        fila5 = (TextView) findViewById(R.id.textQuinto);
        consulta();
        btnReturn();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
                startActivity(new Intent(Score.this,Inicio.class));
            }
        });
    }

    private void consulta() {
        int aux=0;
        DataBase admin = new DataBase(this,
                "admin", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery(
                "select nombre,score from score ORDER BY score DESC LIMIT 5", null);
        try{
            if (fila.moveToFirst()) {
                do{
                    aux++;
                    switch (aux){
                        case 1: fila1.setText(aux+" "+fila.getString(0)+" - "+fila.getString(1));
                            break;
                        case 2: fila2.setText(aux+" "+fila.getString(0)+" - "+fila.getString(1));
                            break;
                        case 3: fila3.setText(aux+" "+fila.getString(0)+" - "+fila.getString(1));
                            break;
                        case 4: fila4.setText(aux+" "+fila.getString(0)+" - "+fila.getString(1));
                            break;
                        case 5: fila5.setText(aux+" "+fila.getString(0)+" - "+fila.getString(1));
                            break;
                    }
                }while(fila.moveToNext());
            }
        }catch (Exception e){
            Log.e("Base de datos", "Error al leer base de datos");
        }
            //Toast.makeText(this, "No existe una persona con dicho dni",
            //      Toast.LENGTH_SHORT).show();
        bd.close();
    }
}

