package com.apcoom.retroinvaders;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Daniel on 6/10/13.
 */

public class DataBaseScores {
    public static final String ID_FILA= "id_score";
    public static final String ID_NOMBRE = "nombre";
    public static final String ID_SCORE = "score";

    public static final String NOMBRE_DB = "estadisticas";
    public static final String NOMBRE_TABLA = "scores";
    public static final int VERSION_DB = 1;

    private BDHelper nHelper;
    private final Context nContexto;
    private SQLiteDatabase nDB;

    private static class BDHelper extends SQLiteOpenHelper{

        public BDHelper (Context context){
            super(context,NOMBRE_DB,null,VERSION_DB);
            //COSAS
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CRATE TABLE " + NOMBRE_TABLA + "(" +
                    ID_FILA + "INTEGER PRIMARY KEY AUTOINCREMENT,"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int versionAnte, int versionNue) {
            db.execSQL("drop table if exists score");
            db.execSQL("create table score(dni integer primary key, nombre text, score integer)");
        }
    }

    public DataBaseScores (Context c){
        nContexto=c;
    }
}
