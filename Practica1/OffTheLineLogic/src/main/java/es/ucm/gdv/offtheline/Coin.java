package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Coin extends Cube {
    Vector2D rot_;
    float extAngle_;
    float speedExtAngle_;
    float rad_;

    public Coin(Vector2D rot, float rad, float speedExtAngle, float extAngle){
        super(new Vector2D(rad, 0.0f), 8);
        angleVel_ = 100;
        rot_ = rot;
        extAngle_ = extAngle;
        speedExtAngle_ = speedExtAngle;
        rad_ = rad;
    }

    @Override
    public void render(Graphics g) { //No gira bien, posiblemente haya que hacer otro distinto (no llamar a super)
        g.save();
        g.translate(rot_.x_, rot_.y_);
        g.rotate(extAngle_);
        super.render(g);
        g.restore();
        //g.translate(-rot_.x_, -rot_.y_);
        //g.rotate(-extAngle_);
    }

    @Override
    public void update(float deltaTime){
        extAngle_+= speedExtAngle_*deltaTime;
        super.update(deltaTime);
    }
}
