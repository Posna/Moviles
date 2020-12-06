package es.ucm.gdv.offtheline;

import java.util.Stack;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Logic;
import es.ucm.gdv.engine.StatesMachine;

public class StateMachine implements StatesMachine {

    Stack<Logic> states;

    StateMachine(){
        states = new Stack<Logic>();
    }

    Logic getActuallState(){
        return states.peek();
    }

    void pushState(Logic newState){
        states.push(newState);
    }

    public void popState(){
        states.pop();
    }

    public void pushMainMenu(Engine e){
        pushState(new MainMenu(e, this));
    }

    public void render() { getActuallState().render();}
    public void update(float deltaTime){ getActuallState().update(deltaTime); }
    public void handleInput() { getActuallState().handleInput(); }
}
