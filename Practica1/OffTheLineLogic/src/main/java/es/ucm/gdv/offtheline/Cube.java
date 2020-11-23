package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Cube extends GameObject {

    float lado_;


    Cube(Vector2D pos, float lado){
        super(pos.x_, pos.y_);
        lado_ = lado;
        //vel_ = new Vector2D(100, 0);
    }

    public void render(Graphics g){
        g.save();
        g.translate(pos_.x_, pos_.y_);
        g.rotate(angle_);
        g.drawLine( lado_/2,  lado_/2, - lado_/2,  lado_/2);
        g.drawLine( lado_/2, lado_/2, lado_/2, - lado_/2);
        g.drawLine(- lado_/2, - lado_/2, - lado_/2,  lado_/2);
        g.drawLine(- lado_/2, - lado_/2, lado_/2, - lado_/2);
        g.restore();
    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
    }
}
