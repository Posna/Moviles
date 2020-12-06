package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class EndGame extends GameObject {

    boolean win_;
    int lvl_;
    Texto endText_;
    Texto mode_;
    Texto score_;
    boolean hardmode_;

    EndGame(boolean win, int lvl, boolean hardmode){
        super(0.0f, 0.0f);
        win_ = win;
        lvl_ = lvl;
        hardmode_ = hardmode;
    }

    @Override
    void render(Graphics g) {
        g.save();
        g.setColor(155, 155, 155, 255);
        g.fillRect(-g.getWidth()/2 + 100, g.getHeight()/5, g.getWidth() - 100, g.getHeight()/5);

        //mode
        if(!hardmode_) {
            mode_ = new Texto(new Vector2D(-110, 160), "Easy Mode", "Bungee-Regular.ttf", 30, true);
        }
        else {
            mode_ = new Texto(new Vector2D(-110, 160), "Hard mode", "Bungee-Regular.ttf", 30, true);
        }
        mode_.setColor(255, 255, 255);
        // win
        if(!win_) {
            endText_ = new Texto(new Vector2D(-160, 200), "Game Over", "Bungee-Regular.ttf", 50, true);
            endText_.setColor(255, 0, 0);

            //score
            score_= new Texto(new Vector2D(-80, 120), "Score: " + lvl_, "Bungee-Regular.ttf", 30, true);
            score_.setColor(255, 255, 255);

        }
        else {
            endText_ = new Texto(new Vector2D(-260, 200), "Congratulations", "Bungee-Regular.ttf", 50, true);
            endText_.setColor(0, 0, 255);
            mode_.text_ += " complete";
            mode_.setPos(-180, 160);

            //score
            score_= new Texto(new Vector2D(-240, 120), "Click to quit to main menu", "Bungee-Regular.ttf", 30, true);
            score_.setColor(255, 255, 255);
        }

        mode_.render(g);
        endText_.render(g);
        score_.render(g);
        g.restore();
    }
}
