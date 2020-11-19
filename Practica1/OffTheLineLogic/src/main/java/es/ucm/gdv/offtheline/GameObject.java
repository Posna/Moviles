package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public abstract class GameObject {

    Vector2D pos_;
    Vector2D vel_;

    GameObject(float x, float y){
        setPos(x, y);
    }

    void setPos(float x, float y){
        pos_ = new Vector2D(x, y);
    }

    void update(){
        pos_ = pos_.add(vel_);
    }
    abstract void render(Graphics g);

    float getPosX() { return pos_.x_; }
    float getPosY() { return pos_.y_; }
}
