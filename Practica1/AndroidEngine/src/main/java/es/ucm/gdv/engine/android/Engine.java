package es.ucm.gdv.engine.android;


import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import es.ucm.gdv.engine.StatesMachine;

public class Engine implements es.ucm.gdv.engine.Engine{
    Graphics graphics_;
    Input input_;
    Context context_;
    StatesMachine _stateMachine;
    SurfaceView _sV;


    /**
     * Manejador de la superficie para poder acceder a su contenido.
     */
    SurfaceHolder _holder;

    /**
     * Bandera que indica si está o no en marcha la hebra de
     * active rendering, y que se utiliza para sincronización.
     * Es importante que el campo sea volatile.
     *
     * Java proporciona un mecanismo integrado para solicitar la
     * detencción de una hebra, aunque por simplicidad nosotros
     * motamos el nuestro propio.
     */
    volatile boolean _running = false;

    public Engine(StatesMachine stateMachine, SurfaceView sV){
        _sV = sV;
        graphics_ = new Graphics();
        input_ = new Input();
        context_ = _sV.getContext();
        _holder = _sV.getHolder();
        _stateMachine = stateMachine;

    }

    public void init(int w, int h){
        graphics_.init(640, 480, w, h, context_);
    }


    public Graphics getGraphics(){
        return graphics_;
    }

    public Input getInput(){
        return input_;
    }

    public InputStream openInputFile(String filename){
        try {
            return context_.getAssets().open(filename);
        }catch (IOException e){
            System.out.println("No se ha encontrasdo el archivo");
            return null;
        }

    }

    public void setRunning(boolean r){
        _running = r;
    }


    public void run(){
        //_stateMachine = new StatesMachine();
        init(_sV.getWidth(), _sV.getHeight());
        _sV.setOnTouchListener(getInput().getMyTouch());

        long lastFrameTime = System.nanoTime();

        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;
        _stateMachine.pushMainMenu(this);

        // Bucle principal.
        while(_running) {

            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            _stateMachine.handleInput();
            _stateMachine.update((float)elapsedTime);
            // Informe de FPS
            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;

            // Pintamos el frame
            while (!_holder.getSurface().isValid())
                ;
            Canvas canvas = _holder.lockCanvas();
            getGraphics().prepararPintado(canvas);
            _stateMachine.render();
            _holder.unlockCanvasAndPost(canvas);
                /*
                // Posibilidad: cedemos algo de tiempo. es una medida conflictiva...
                try {
                    Thread.sleep(1);
                }
                catch(Exception e) {}
    			*/

        } // while

    }
}
