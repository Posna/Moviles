package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public abstract class GameObject {

    Vector2D pos_; //Posicion del objeto
    Vector2D vel_ = new Vector2D(0, 0);//Vector velocidad
    float angle_ = 0; //angulo
    float angleVel_ = 0; //velocidad de rotacion
    float speed_ = 1; //Velocidad de desplazamiento


    GameObject(float x, float y){
        setPos(x, y);
    }

    void setPos(float x, float y){
        pos_ = new Vector2D(x, y);
    }
    void setVel(float x, float y) { vel_ = new Vector2D(x, y); }
    void setAngularVel(float aV) { angleVel_ = aV; }
    void setAngle(float a) { angle_ = a; }
    void setSpeed(float speed) { speed_ = speed; }

    void update(float deltaTime){
        pos_.x_ +=  vel_.x_*speed_*deltaTime;
        pos_.y_ +=  vel_.y_*speed_*deltaTime;

        angle_ +=deltaTime*angleVel_;
        angle_ = angle_ % 360;
    }
    abstract void render(Graphics g);

    float getPosX() { return pos_.x_; }
    float getPosY() { return pos_.y_; }
    Vector2D getPos(){ return pos_; }
}
