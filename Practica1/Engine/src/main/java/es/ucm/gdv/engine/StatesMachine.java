package es.ucm.gdv.engine;

public interface StatesMachine {

    void update(float deltaTime);
    void render();
    void handleInput();

    void pushMainMenu(Engine e);
    void popState();
}
