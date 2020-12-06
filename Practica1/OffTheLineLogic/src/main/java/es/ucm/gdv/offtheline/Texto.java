package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Texto extends GameObject {

    int r_, g_, b_, size_;
    String text_;
    String font_;
    boolean bold_;


    Texto(Vector2D p, String t, String font, int size, boolean bold){
        super(p.x_, p.y_);
        text_ = t;
    }

    void setColor(int r, int g, int b){
        r_ = r;
        g_ = g;
        b_ = b;
    }

    @Override
    void render(Graphics g) {
        g.save();
        g.translate(pos_.x_, pos_.y_);
        g.rotate(180);
        g.scale(-1);
        g.setColor(r_, g_, b_, 255);
        g.drawText(text_, 0, 0);
        g.restore();
    }
}
