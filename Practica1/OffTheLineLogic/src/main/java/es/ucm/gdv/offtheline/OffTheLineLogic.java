package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class OffTheLineLogic {

    Cube prueba = new Cube(new Vector2D(0, 0), 100);

    float i = 0;

    public void update(double deltaTime){
        i+=deltaTime*100;
        i = i % 360;
    }

    public void render(Graphics g){
        //g.setColor(255, 255, 255, 255);
        g.clear(0, 0, 0);
        g.translate(-500, -500);
        g.rotate(i);


        g.setColor(255, 0, 0, 255);
        prueba.render(g);


        g.rotate(-i);
        g.translate(500, 500);


        g.drawLine(0, 0, 500, 500);

    }
}