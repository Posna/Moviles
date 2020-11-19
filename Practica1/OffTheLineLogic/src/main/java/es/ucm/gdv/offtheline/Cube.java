package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Cube extends GameObject {

    float lado_;


    Cube(Vector2D pos, float lado){
        super(pos.x_, pos.y_);
        lado_ = lado/2;
    }

    public void render(Graphics g){
        g.drawLine(pos_.x_ + lado_, pos_.y_ + lado_, pos_.x_ - lado_, pos_.y_ + lado_);
        g.drawLine(pos_.x_ + lado_, pos_.y_ + lado_, pos_.x_ + lado_, pos_.y_ - lado_);
        g.drawLine(pos_.x_ - lado_, pos_.y_ - lado_, pos_.x_ - lado_, pos_.y_ + lado_);
        g.drawLine(pos_.x_ - lado_, pos_.y_ - lado_, pos_.x_ + lado_, pos_.y_ - lado_);
    }
}
