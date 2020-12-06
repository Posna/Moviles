package es.ucm.gdv.offtheline;

import javax.swing.JFrame;

import es.ucm.gdv.engine.desktop.Engine;
import es.ucm.gdv.engine.desktop.Graphics;

public class Main {

    public static void main(String[] args){
        //Creacion de engine e inicio del bucle del juego
        Engine engine_ = new Engine(new StateMachine());
        engine_.run();
    }

}