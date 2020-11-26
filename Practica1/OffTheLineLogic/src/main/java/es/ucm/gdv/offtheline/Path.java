package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Path extends GameObject {

    float length_;
    Vector2D p1_;
    Vector2D p2_;

    Vector2D normal_;

    public Path(Vector2D p1, Vector2D p2){
        super((p1.x_+p2.x_)/2.0f, (p1.y_+p2.y_)/2.0f);
        length_ = (float) Math.sqrt(Math.pow(p2.x_-p1.x_, 2) + Math.pow(p2.y_-p1.y_, 2));
        p1_ = p1;
        p2_ = p2;

        normal_ = new Vector2D((p2.y_-p1.y_),-(p2.x_ - p1.x_));
    }


    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
    }

    public void render(Graphics g){
        g.save();
        //g.translate(pos_.x_, pos_.y_);
        //g.rotate(angle_);
        g.drawLine(p1_.x_, p1_.y_, p2_.x_, p2_.y_);
        g.restore();
    }

    public void setNormal(Vector2D normal){
        normal_ = normal;
    }

    public Vector2D getPunta(){
        return p1_;
    }
}
