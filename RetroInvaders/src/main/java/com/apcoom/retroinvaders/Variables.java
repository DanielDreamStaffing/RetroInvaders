package com.apcoom.retroinvaders;

public class Variables {
    private static int score=0;
    private static int naveElegida=0;

    public Variables() {
    }

    public void setNave(int nave){
        naveElegida=nave;
    }

    public int getNave(){
        return naveElegida;
    }

    public void sumarScore(int numero){
        score +=numero;
    }

    public void resetScore(){
        score =0;
    }

    public int getScore(){
        return score;
    }
}
