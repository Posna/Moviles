package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Line extends GameObject {
    float length_;

    public Line(Vector2D p1, Vector2D p2){
        super((p1.x_+p2.x_)/2.0f, (p1.y_+p2.y_)/2.0f);
        length_ = (float) Math.sqrt(Math.pow(p2.x_-p1.x_, 2) + Math.pow(p2.y_-p1.y_, 2));
    }

    public Line(Vector2D c, float angle, float lenght){
        super(c.x_, c.y_);
        angle_ = angle;
        length_ = lenght;
    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
    }

    public void render(Graphics g){
        g.save();
        g.translate(pos_.x_, pos_.y_);
        g.rotate(angle_);
        g.drawLine(- length_/2.0f, 0.0f, length_/2.0f, 0.0f);
        g.restore();
    }
}
