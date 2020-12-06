package es.ucm.gdv.offtheline;

import java.util.Stack;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Logic;
import es.ucm.gdv.engine.StatesMachine;

public class StateMachine implements StatesMachine {

    Stack<Logic> states; //Pila para los estados

    StateMachine(){
        states = new Stack<Logic>();
    }

    /**
     * Devuelve el estado actual
     * @return
     */
    Logic getActuallState(){
        return states.peek();
    }

    /**
     * Añade un estado a la maquina de estados
     * @param newState Estado nuevo
     */
    void pushState(Logic newState){
        states.push(newState);
    }

    /**
     * Elimina de la maquina el estado actual
     */
    public void popState(){
        states.pop();
    }

    /**
     * Añade a la maquina el estado menu
     * @param e Engine
     */
    public void pushMainMenu(Engine e){
        pushState(new MainMenu(e, this));
    }

    /****** Render, update y handleInput del estado actual ******/
    public void render() { getActuallState().render();}
    public void update(float deltaTime){ getActuallState().update(deltaTime); }
    public void handleInput() { getActuallState().handleInput(); }
}
