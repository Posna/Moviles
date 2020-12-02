package es.ucm.gdv.offtheline;

import java.util.Stack;

import es.ucm.gdv.engine.Logic;

public class StateMachine {

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

    void popState(){
        states.pop();
    }

    void render() { getActuallState().render();}
    void update(float deltaTime){ getActuallState().update(deltaTime); }
    void handleInput() { getActuallState().handleInput(); }
}
