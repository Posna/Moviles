package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Coin extends GameObject {
    Vector2D rot_;
    float extAngle_;
    float speedExtAngle_;
    float rad_;
    float lado_;

    public Coin(Vector2D rot, float rad, float speedExtAngle, float extAngle){
        super(rad, 0.0f);
        angleVel_ = 100;
        rot_ = rot;
        extAngle_ = extAngle;
        speedExtAngle_ = speedExtAngle;
        rad_ = rad;
        lado_ = 8;
    }

    @Override
    public void render(Graphics g) {
        g.save();
        g.translate(rot_.x_, rot_.y_);
        g.rotate(extAngle_);
        g.translate(pos_.x_, pos_.y_);
        g.rotate(angle_);
        g.drawLine(lado_/2,  lado_/2, - lado_/2,  lado_/2);
        g.drawLine(lado_/2, lado_/2, lado_/2, - lado_/2);
        g.drawLine(- lado_/2, - lado_/2, - lado_/2,  lado_/2);
        g.drawLine(- lado_/2, - lado_/2, lado_/2, - lado_/2);
        g.restore();
    }

    @Override
    public void update(float deltaTime){
        extAngle_+= speedExtAngle_*deltaTime;
        super.update(deltaTime);
    }
}
