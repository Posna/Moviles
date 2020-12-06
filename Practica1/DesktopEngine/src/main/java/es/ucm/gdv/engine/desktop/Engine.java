package es.ucm.gdv.engine.desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import es.ucm.gdv.engine.StatesMachine;
import sun.security.util.Resources;


public class Engine implements es.ucm.gdv.engine.Engine {
    Graphics graphics_;
    Input input_;
    Resources resource_;
    StatesMachine machine;

    /**
     * Inicializacion de del engine y sus atributos
     * @param m
     */
    public Engine(StatesMachine m){
        machine = m;
        resource_ = new Resources();
        graphics_ = new Graphics();
        if (!graphics_.init(640, 480))
            // Ooops. Ha fallado la inicialización.
            return;
        input_ = new Input();
        graphics_.addMouseListener(input_.m);
    }


    public Graphics getGraphics(){
        return graphics_;
    }

    public Input getInput(){
        return input_;
    }

    public InputStream openInputFile(String filename){
        File ini = new File("assets/" + filename);
        try {
            return new FileInputStream(ini);
        }catch (FileNotFoundException e){
            return  null;
        }
    }

    /**
     * Bucle principal del juego en pc
     */
    public void run(){
        //Empieza el juego en el menu principal
        machine.pushMainMenu(this);

        // Vamos allá.
        long lastFrameTime = System.nanoTime();
        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;

        // Bucle principal
        while(true) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            machine.handleInput();
            machine.update((float)elapsedTime);

            // Informe de FPS
            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;

            // Pintamos el frame con el BufferStrategy
            do {
                do {
                    getGraphics().preparePaint();
                    try {
                        machine.render();
                    }
                    finally {
                        getGraphics().dispose();
                    }
                } while(getGraphics().contentsRestored());
                getGraphics().show();
            } while(getGraphics().contentsLost());

        } // while
    }

}
