package com.apcoom.retroinvaders;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;

public class Allien {

    private Bitmap bitmap; // the actual bitmap
    private int x;   // the X coordinate
    private int y;   // the Y coordinate
    private boolean estado = true;   // the Y coordinate
    private int estadoPro = 0;   // the Y coordinate
    private Speed speed;	// the speed with its directions
    private int fila;	// the speed with its directions
    private int celda;	// the speed with its directions
    private boolean dispara;   // the Y coordinate
/*
    public Allien(Bitmap bitmap, int x) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = bitmap.getHeight()/2;
        this.speed = new Speed(2,bitmap.getHeight());
    }*/

    public Allien(Bitmap bitmap, int x, int fila, int celda, boolean dispara) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = bitmap.getHeight()/2;
        this.speed = new Speed(2,bitmap.getHeight());
        this.fila=fila;
        this.celda=celda;
        this.dispara=dispara;
    }

    public Allien(Bitmap bitmap, int x, int celda, boolean dispara) {
        this.bitmap = bitmap;
        this.x = x;
        this.y = bitmap.getHeight()/2;
        this.speed = new Speed(2,bitmap.getHeight());
        this.celda=celda;
        this.dispara=dispara;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getCelda() {
        return celda;
    }

    public void setCelda(int celda) {
        this.celda = celda;
    }

    public int getFila() {
        return fila;
    }

    public void setEstadoPro(int estadoPro) {
        this.estadoPro = estadoPro;
    }

    public int getEstadoPro() {
        return estadoPro;
    }

    public void setFila(int fila) {
        this.fila = fila;
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

    public boolean dispara() {
        return dispara;
    }

    public boolean estaVivo() {
        return estado;
    }

    public void fijaEstado(boolean estado) {
        this.estado = estado;
    }

    public Speed getSpeed() {
        return speed;
    }

    public void setSpeed(Speed speed) {
        this.speed = speed;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
    }

    public void update() {
        x += (speed.getXv() * speed.getxDirection());
        y += (speed.getYv() * speed.getyDirection());
    }

    public void updateX() {
        x += (speed.getXv() * speed.getxDirection());
    }

    public void updateY() {
        y += (speed.getYv() * speed.getyDirection());
    }
}
