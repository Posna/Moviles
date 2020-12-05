package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class EndGame extends GameObject {

    boolean win_;
    int lvl_;

    EndGame(boolean win, int lvl){
        super(0.0f, 0.0f);
        win_ = win;
        lvl_ = lvl;
    }

    @Override
    void render(Graphics g) {
        g.save();
        g.setColor(155, 155, 155, 255);
        g.fillRect(-g.getWidth()/2, g.getHeight()/4, g.getWidth(), g.getHeight()/4);
        g.restore();
    }
}
