package es.ucm.gdv.engine;

public interface Logic {

    void update(float deltaTime);
    void render();
    void handleInput();
}
