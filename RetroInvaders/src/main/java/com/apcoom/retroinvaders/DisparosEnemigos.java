package com.apcoom.retroinvaders;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Daniel on 1/10/13.
 */
public class DisparosEnemigos {
    private Bitmap bitmap; // the actual bitmap
    private int x;   // the X coordinate
    private int y;   // the Y coordinate
    private int posicion;   // the Y coordinate
    private Speed speed;	// the speed with its directions
    private int estado =0;

    public DisparosEnemigos(Bitmap bitmap, int x, int y, int posicion) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.speed = new Speed(0,2);
        this.posicion=posicion;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getEstado() {
        return estado;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public int posicion() {
        return posicion;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }

    public void bajar() {
        y += (speed.getYv() * 1);
    }
}
