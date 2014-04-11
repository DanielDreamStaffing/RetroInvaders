package com.apcoom.retroinvaders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener {

    public final Bitmap NAVE1 = BitmapFactory.decodeResource(getResources(), R.drawable.navejuego1_hd);
    public final Bitmap NAVE5 = BitmapFactory.decodeResource(getResources(), R.drawable.navejuego5_hd);
    public final Bitmap PAUSE_DECORACION = BitmapFactory.decodeResource(getResources(), R.drawable.nave_resume);
    public final Bitmap PLANETA_RESUME = BitmapFactory.decodeResource(getResources(), R.drawable.resume_hd);
    public final Bitmap NAVES = BitmapFactory.decodeResource(getResources(), R.drawable.navejuegos_hd);
    public final Bitmap NAVE_VERDE	= BitmapFactory.decodeResource(getResources(), R.drawable.allienv_hd);
    public final Bitmap NAVE_VERDE_D	= BitmapFactory.decodeResource(getResources(), R.drawable.allienvd_hd);
    public final Bitmap NAVE_MORADA_D	= BitmapFactory.decodeResource(getResources(), R.drawable.allienmd_hd);
    public final Bitmap NAVE_MORADA	= BitmapFactory.decodeResource(getResources(), R.drawable.allienm_hd);
    private final int NAVE_ANCHO =  BitmapFactory.decodeResource(getResources(), R.drawable.allienv_hd).getWidth();
    private final Bitmap DISPARO_MORADO =  BitmapFactory.decodeResource(getResources(), R.drawable.disparomorado_hd);

    private MainThread thread;
    private int estadoJuego = 0;
    private int contadorCelda=0;
    private int contadorDisparos=0;
    private int altoPantalla=0;
    private int anchoPantalla=0;
    private int dificultad=400;
    private Bitmap fondo;
    private Bitmap fondoP;
    private Bitmap disparo;
    private int delayDisparos=40;
    private int contadorDelayDiparos=750;
    private int contadorInsertar=0;
    private int delayInsertar=210;
    private Paint pincel = new Paint();
    private ArrayList<Allien> enemigos = new  ArrayList<Allien>();
    private ArrayList<DisparosEnemigos> disparos_enemigo = new  ArrayList<DisparosEnemigos>();
    private ArrayList<Disparos> disparos = new  ArrayList<Disparos>();
    private Spacecraft nave;
    private Rect fondoScore;
    private Random r = new Random();
    private Variables variable = new Variables();
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private int movimientoX=0;

    public MainGamePanel(Context context) {
        super(context);
        // adding the callback (this) to the surface holder to intercept events
        getHolder().addCallback(this);
        //crear objetos
        pincel.setTextSize(45);
        switch(variable.getNave())
        {
            case 1: nave = new Spacecraft(NAVE1, 300,700);
                    disparo = BitmapFactory.decodeResource(getResources(), R.drawable.disparonaranja_hd);
                    break;
            case 2: nave = new Spacecraft(NAVE5, 300,700);
                    disparo = BitmapFactory.decodeResource(getResources(), R.drawable.disparorojo_hd);
                    break;
            case 3: nave = new Spacecraft(NAVES, 300,700);
                    disparo = BitmapFactory.decodeResource(getResources(), R.drawable.disparorosa_hd);
                    break;

        }
        insertarFila();
        // create the game loop thread
        thread = new MainThread(getHolder(), this);
        // make the GamePanel focusable so it can handle events
        setFocusable(true);
        /*
        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        */

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if(sensors.size()>0){
            mAccelerometer = sensors.get(0);
            mSensorManager.registerListener(this,mAccelerometer,SensorManager.SENSOR_DELAY_GAME);
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.setStop(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent e) {
        movimientoX = (int) (e.values[SensorManager.DATA_X]);
        Log.d ("ACELEROMETRO:", ""+movimientoX);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d ("touch:", "Entro al touch");
        try{
            if(estadoJuego==1){

                if(event.getY() < 50 ||
                        (
                                ((event.getX()>=(anchoPantalla/2)-(PLANETA_RESUME.getWidth()/2)) && (event.getX()<=((anchoPantalla/2)-(PLANETA_RESUME.getWidth()/2)+PLANETA_RESUME.getWidth())))
                                        &&
                                        ((event.getY()>=(altoPantalla/2)) && (event.getY()<=((altoPantalla/2)+PLANETA_RESUME.getHeight())))
                        )
                )
                {
                    //REANUDAR
                    mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
                    estadoJuego=0;
                    thread.setRunning(true);
                }
            }
            else{
                if(event.getY() > (getHeight() - nave.getBitmap().getWidth())){
                   /* if (event.getX() > (getWidth()/2))
                    {
                        if (nave.getX() + nave.getBitmap().getWidth() / 2 >= getWidth()) {
                            nave.setX(getWidth()-(nave.getBitmap().getWidth() / 2));
                        }else{
                            nave.updateDerecha();
                        }
                    }else{
                        if (nave.getX() - nave.getBitmap().getWidth() / 2 <= 0) {
                            nave.setX(0);
                        }else{
                            nave.updateIzquierda();
                        }
                    }*/
                }else{
                    if(event.getY() > 50){
                        if(contadorDelayDiparos>=delayDisparos){
                            if(disparos.size()<19)
                            {
                                disparos.add(new Disparos(disparo,nave.getX(),altoPantalla-nave.getBitmap().getHeight(),contadorDisparos));
                                contadorDisparos++;
                                contadorDelayDiparos=0;
                            }
                        }
                    }else{
                            //PAUSAR
                            mSensorManager.unregisterListener(this);
                            thread.setPausar(true);
                            estadoJuego=1;
                            //Canvas canvas = getHolder().lockCanvas();
                            //render(canvas);
                    }
                }
            }
        }catch(Exception e){
            Log.e("NO FUNCIONO EN EL TAP", "ALGO HORRIBLE PASO");
        }
        return super.onTouchEvent(event);
    }

    protected void render(Canvas canvas) {
        canvas.drawBitmap(fondo,0,0,null);

        for(  Allien enemigo : enemigos ){
            enemigo.draw(canvas);
        }
        if(!disparos_enemigo.isEmpty())
        {
            for(  DisparosEnemigos disparor : disparos_enemigo ){
                disparor.draw(canvas);
            }
        }
        if(!disparos.isEmpty())
        {
            for(  Disparos disparoYo : disparos ){
                disparoYo.draw(canvas);
            }
        }
        nave.draw(canvas);
        if(estadoJuego==1)
            canvas.drawBitmap(fondoP,0,0,null);
        pincel.setColor(Color.BLACK);
        canvas.drawRect(fondoScore, pincel);
        pincel.setARGB(255,255,255,255);
        canvas.drawText(""+variable.getScore(), 20, 40, pincel);
        if(estadoJuego==1){
            pincel.setColor(Color.DKGRAY);
        }
        canvas.drawText("||", anchoPantalla-45, 35, pincel);
        if(estadoJuego==1){
            canvas.drawBitmap(PAUSE_DECORACION,-(PAUSE_DECORACION.getWidth()/2),altoPantalla/9,null);
            pincel.setARGB(255,255,255,255);
            pincel.setTextSize(70);
            canvas.drawText("PAUSE", (PAUSE_DECORACION.getWidth()/2)-30, (altoPantalla/9)+190, pincel);
            pincel.setTextSize(45);
            canvas.drawBitmap(PLANETA_RESUME,(anchoPantalla/2)-(PLANETA_RESUME.getWidth()/2),(altoPantalla/2),null);
        }
    }

    protected void update() {
        if(altoPantalla==0){
            altoPantalla=getHeight();
            anchoPantalla=getWidth();
            fondo = Bitmap.createScaledBitmap (BitmapFactory.decodeResource(getResources(), R.drawable.fondo), anchoPantalla,altoPantalla , false);
            fondoP = Bitmap.createScaledBitmap (BitmapFactory.decodeResource(getResources(), R.drawable.fp_hd), anchoPantalla,altoPantalla , false);
            nave.setY(altoPantalla-nave.getBitmap().getHeight()/2);
            nave.setX(anchoPantalla / 2);
            fondoScore=new Rect(0,0, anchoPantalla, 50);
        }
        if(!enemigos.isEmpty()){
            for(Allien enemigo : enemigos ){
                if(enemigo.getY()>(getHeight() - nave.getBitmap().getWidth())){
                    /*******************************************TERMINA JUEGO****************************************/
                    estadoJuego=2;
                }
                int aleatorio=r.nextInt(dificultad - 0 + 1) + 0;
                if(enemigo.getEstadoPro()==2){
                    if(enemigos.remove(enemigo)) break;
                }
                if(enemigo.getEstadoPro()==1 ){
                    if(enemigo.dispara()){
                        enemigo.setBitmap(NAVE_MORADA_D);
                    }else{
                        enemigo.setBitmap(NAVE_VERDE_D);
                    }
                    enemigo.setEstadoPro(2);
                }
                if(enemigo.dispara() && aleatorio==2){
                    if(disparos_enemigo.size()<19){
                        disparos_enemigo.add(new DisparosEnemigos(DISPARO_MORADO,enemigo.getX(),enemigo.getY(),0));
                    }
                }
                if (enemigo.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
                        && enemigo.getX() + enemigo.getBitmap().getWidth() / 2 >= getWidth()) {
                    enemigo.getSpeed().toggleXDirection();
                    enemigo.updateY();
                }
                // check collision with left wall if heading left
                if (enemigo.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
                        && enemigo.getX() - enemigo.getBitmap().getWidth() / 2 <= 0) {
                    enemigo.getSpeed().toggleXDirection();
                    enemigo.updateY();
                }
                // check collision with bottom wall if heading down
                if (enemigo.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
                        && enemigo.getY() + enemigo.getBitmap().getHeight() / 2 >= getHeight()) {
                }
                enemigo.updateX();
            }
        }

        for(  Disparos disparoy : disparos ){
            if(disparoy.getEstado()==1){
                if(disparos.remove(disparoy)) break;
            }else{
                if(disparoy.getY()<=0){
                    if(disparos.remove(disparoy)) break;
                }else{
                    disparoy.subir();
                }
            }
        }

        for(DisparosEnemigos disparor : disparos_enemigo ){
            if(disparor.getY()>getHeight()){
                if(disparos_enemigo.remove(disparor)) break;
            }else{
                disparor.bajar();
            }
        }


        if (movimientoX<0)
        {
            if (nave.getX() + nave.getBitmap().getWidth() / 2 >= getWidth()) {
                nave.setX(getWidth()-(nave.getBitmap().getWidth() / 2));
            }else{
                nave.updateDerecha();
            }
        }
        if (movimientoX>0){
            if (nave.getX() - nave.getBitmap().getWidth() / 2 <= 0) {
                nave.setX((nave.getBitmap().getWidth() / 2));
            }else{
                nave.updateIzquierda();
            }
        }

        contactoEnemigo();
        contactoNave();

        if(contadorInsertar==delayInsertar){
            insertarFila();
            contadorInsertar=0;
        }
        contadorInsertar++;
        contadorDelayDiparos++;
        if(estadoJuego==2){
            //****************************************TERMINADO**********************************************************************/
            mSensorManager.unregisterListener(this);
            thread.setRunning(false);
            thread.setStop(false);
            Intent intent = new Intent(getContext(), GameOver.class);
            getContext().startActivity(intent);
        }
    }

    private void insertarFila() {
            switch (contadorCelda){
                case 4:
                    enemigos.add(new Allien(NAVE_VERDE, ((NAVE_ANCHO)),contadorCelda,false));
                    contadorCelda++;
                    enemigos.add(new Allien(NAVE_MORADA, ((NAVE_ANCHO*2)),contadorCelda,true));
                    contadorCelda++;
                    enemigos.add(new Allien(NAVE_MORADA, ((NAVE_ANCHO*3)),contadorCelda,true));
                    contadorCelda++;
                    enemigos.add(new Allien(NAVE_VERDE, ((NAVE_ANCHO*4)),contadorCelda,false));
                    contadorCelda++;
                    break;
                case 8:
                    enemigos.add(new Allien(NAVE_MORADA, ((NAVE_ANCHO)),contadorCelda,true));
                    contadorCelda++;
                    enemigos.add(new Allien(NAVE_VERDE, ((NAVE_ANCHO*2)),contadorCelda,false));
                    contadorCelda++;
                    enemigos.add(new Allien(NAVE_VERDE, ((NAVE_ANCHO*3)),contadorCelda,false));
                    contadorCelda++;
                    enemigos.add(new Allien(NAVE_MORADA, ((NAVE_ANCHO*4)),contadorCelda,true));
                    contadorCelda++;
                    contadorCelda=0;
                    if(dificultad>120){
                        dificultad-=10;
                    }
                    break;
                default:
                    enemigos.add(new Allien(NAVE_VERDE, ((NAVE_ANCHO)),contadorCelda,false));
                    contadorCelda++;
                    enemigos.add(new Allien(NAVE_VERDE, ((NAVE_ANCHO)*2),contadorCelda,false));
                    contadorCelda++;
                    enemigos.add(new Allien(NAVE_VERDE, ((NAVE_ANCHO)*3),contadorCelda,false));
                    contadorCelda++;
                    enemigos.add(new Allien(NAVE_VERDE, ((NAVE_ANCHO)*4),contadorCelda,false));
                    contadorCelda++;
                    break;
            }
    }

    private boolean hitNave(DisparosEnemigos a,Spacecraft b) {
        boolean hit = false;
        if((b.getX()+(b.getBitmap().getWidth()/2)) >= (a.getX()-(a.getBitmap().getWidth()/2)) && (b.getX()-(b.getBitmap().getWidth()/2)) < (a.getX()+(a.getBitmap().getWidth()/2)) ){
            if((b.getY()-(b.getBitmap().getHeight()/2)) >= (a.getY()-(a.getBitmap().getHeight()/2)) && (b.getY()+(b.getBitmap().getHeight()/2)) < (a.getY()+(a.getBitmap().getHeight()/2))){
                hit=true;
            }
        }
        if ((b.getX()-(b.getBitmap().getWidth()/2)) <= (a.getX()-(a.getBitmap().getWidth()/2)) && (b.getX()+(b.getBitmap().getWidth()/2)) >= (a.getX()+(a.getBitmap().getWidth()/2))) {
            if((b.getY()-(b.getBitmap().getHeight()/2)) <= (a.getY()-(a.getBitmap().getHeight()/2)) && (b.getY()+(b.getBitmap().getHeight()/2)) >= (a.getY()+(a.getBitmap().getHeight()/2))){
                hit=true;
            }
        };
        if ((a.getX()-(a.getBitmap().getWidth()/2)) <= (b.getX()-(b.getBitmap().getWidth()/2)) && (a.getX()+(a.getBitmap().getWidth()/2)) >= (b.getX()+(b.getBitmap().getWidth()/2))) {
            if((a.getY()-(a.getBitmap().getHeight()/2)) <= (b.getY()-(b.getBitmap().getHeight()/2)) && (a.getY()+(a.getBitmap().getHeight()/2)) >= (b.getY()+(b.getBitmap().getHeight()/2))){
                hit=true;
            }
        };
        return hit;
    }

    private boolean hitEnemigo(Disparos a,Allien b) {
        boolean hit = false;
        if((b.getX()+(b.getBitmap().getWidth()/2)) >= (a.getX()-(a.getBitmap().getWidth()/2)) && (b.getX()-(b.getBitmap().getWidth()/2)) < (a.getX()+(a.getBitmap().getWidth()/2)) ){
            if((b.getY()-(b.getBitmap().getHeight()/2)) >= (a.getY()-(a.getBitmap().getHeight()/2)) && (b.getY()+(b.getBitmap().getHeight()/2)) < (a.getY()+(a.getBitmap().getHeight()/2))){
                hit=true;
            }
        }
        if ((b.getX()-(b.getBitmap().getWidth()/2)) <= (a.getX()-(a.getBitmap().getWidth()/2)) && (b.getX()+(b.getBitmap().getWidth()/2)) >= (a.getX()+(a.getBitmap().getWidth()/2))) {
            if((b.getY()-(b.getBitmap().getHeight()/2)) <= (a.getY()-(a.getBitmap().getHeight()/2)) && (b.getY()+(b.getBitmap().getHeight()/2)) >= (a.getY()+(a.getBitmap().getHeight()/2))){
                hit=true;
            }
        };
        if ((a.getX()-(a.getBitmap().getWidth()/2)) <= (b.getX()-(b.getBitmap().getWidth()/2)) && (a.getX()+(a.getBitmap().getWidth()/2)) >= (b.getX()+(b.getBitmap().getWidth()/2))) {
            if((a.getY()-(a.getBitmap().getHeight()/2)) <= (b.getY()-(b.getBitmap().getHeight()/2)) && (a.getY()+(a.getBitmap().getHeight()/2)) >= (b.getY()+(b.getBitmap().getHeight()/2))){
                hit=true;
            }
        };
        return hit;
    }

    private void contactoEnemigo(){
        for(Disparos disparoNave : disparos ){
            for(Allien enemigo : enemigos ){
                if(hitEnemigo(disparoNave,enemigo)){
                    if(enemigo.dispara())
                    {
                        variable.sumarScore(10);
                    }else
                    {
                        variable.sumarScore(5);
                    }
                    enemigo.setEstadoPro(1);
                    disparoNave.setEstado(1);
                }
            }
        }
    }

    private void contactoNave(){
        for(DisparosEnemigos disparoEnemigo : disparos_enemigo ){
            if(hitNave(disparoEnemigo,nave)){
                estadoJuego=2;
            }
        }
    }
}
