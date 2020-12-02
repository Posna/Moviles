package es.ucm.gdv.engine.desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import sun.security.util.Resources;


public class Engine implements es.ucm.gdv.engine.Engine {
    Graphics graphics_;
    Input input_;
    //Resources resource_;
    sun.security.util.Resources resource_;

    public Engine(){
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

        //return resource_.getClass().getResourceAsStream("C:/Users/josel/Desktop/uni/4/Moviles/Moviles/Practica1/DesktopGame/levels.json");
        // new FileInputStream(filename);

    }

}
