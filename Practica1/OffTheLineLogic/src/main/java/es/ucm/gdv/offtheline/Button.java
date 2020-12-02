package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Font;
import es.ucm.gdv.engine.Graphics;

public class Button extends GameObject {

    Vector2D r1_;
    Vector2D r2_;
    String texto_;

    Button(Vector2D r1, Vector2D r2, String Texto, Font fuente){
        super((r2.x_ - r1.x_)/2.0f, (r2.y_ - r1.y_)/2.0f);
        r1_ = r1;
        r2_ = r2;

        texto_ = Texto;
    }

    @Override
    void render(Graphics g) {
        g.save();
        g.translate(r1_.x_, r1_.y_);
        g.rotate(180);
        g.scale(-1);
        g.setColor(255, 0, 0, 255);
        g.drawText(texto_, 0, 0);
        g.restore();
    }

    boolean handleInput(float x, float y){
        return r1_.x_ <= x && r2_.x_ >= x && r1_.y_ >= y && r2_.y_ <= y;
    }
}
