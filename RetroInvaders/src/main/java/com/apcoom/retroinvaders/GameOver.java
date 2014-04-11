package com.apcoom.retroinvaders;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class GameOver extends Activity {
    private Variables var= new Variables();
    private EditText nombre;
    private TextView highScore,yourScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        nombre = (EditText) findViewById(R.id.nombre);
        highScore = (TextView) findViewById(R.id.textHigh);
        yourScore = (TextView) findViewById(R.id.textScore);
        btnMainMenu();
        btnTryAgain();
        cargarDatos ();
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void btnMainMenu()
    {
        ImageButton mainMenu = (ImageButton) findViewById(R.id.btnMainMenu);
        mainMenu.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view){
                guardaryrestaurar();
               startActivity(new Intent(GameOver.this,Inicio.class));
            }
        });
    }

    private void btnTryAgain()
    {
        ImageButton tryAgain = (ImageButton) findViewById(R.id.btnTryAgain);
        tryAgain.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view){
                guardaryrestaurar();
                startActivity(new Intent(GameOver.this, SelectSpacecraft.class));
            }
        });
    }

    private void guardaryrestaurar()
    {
        DataBase admin = new DataBase(this,
                "admin", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String nom = nombre.getText().toString();
        int nuevoScore = var.getScore();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nom);
        registro.put("score", nuevoScore);

        bd.insert("score", null, registro);
        bd.close();

        var.resetScore();

    }

    private void cargarDatos (){
        DataBase admin = new DataBase(this,
                "admin", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        Cursor fila = bd.rawQuery(
                "select score, nombre from score ORDER BY score DESC LIMIT 1", null);
        try{
            if (fila.moveToFirst()) {
                highScore.setText("High Score: "+fila.getString(0));
                nombre.setText(""+fila.getString(1));
            }else{
                highScore.setText("High Score: "+var.getScore());
                nombre.setText("Tu nombre");
            }
        }catch (Exception e){
            Log.e("Base de datos", "Error al leer baíe de datos");
        }
        bd.close();
        yourScore.setText("Your score: "+var.getScore());

    }

    public void alta(View v) {
        //Toast.makeText(this, "Se cargaron los datos de la persona",
          //      Toast.LENGTH_SHORT).show();
    }
}
