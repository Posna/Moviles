package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class CrossCube extends Cube {

    float ladoCross_;
    boolean cross_ = false; //Renderiza una cruz o un cubo

    CrossCube(Vector2D p, float lado){
        super(p, lado);
        ladoCross_ = (float)Math.sqrt(lado*lado*2);
    }

    @Override
    public void render(Graphics g){
        if(cross_)
            crossRender(g);
        else
            super.render(g);
    }

    void crossRender(Graphics g){
        g.save();
        g.setColor(255, 0, 0, 255);
        g.translate(pos_.x_, pos_.y_);
        g.rotate(45);
        g.drawLine(-ladoCross_/2, 0, ladoCross_/2, 0);
        g.rotate(-90);
        g.drawLine(-ladoCross_/2, 0, ladoCross_/2, 0);
        g.restore();
    }

    void startRenderCross(){
        cross_ = true;
    }
}
