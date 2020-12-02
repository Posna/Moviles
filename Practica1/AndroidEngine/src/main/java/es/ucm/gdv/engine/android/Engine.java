package es.ucm.gdv.engine.android;


import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Engine implements es.ucm.gdv.engine.Engine{
    Graphics graphics_;
    Input input_;
    Context context_;

    public Engine(Context context){
        graphics_ = new Graphics();
        input_ = new Input();
        context_ = context;
    }

    public void init(int w, int h){
        graphics_.init(640, 480, w, h);
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
}
