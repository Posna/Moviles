package es.ucm.gdv.engine.desktop;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;

import es.ucm.gdv.engine.Font;

public class Graphics implements es.ucm.gdv.engine.Graphics {

    Graphics2D graphics_;
    JFrame paint;

    float translateX_; //Transformacion
    float translateY_;
    float scale_; //Escalado
    float angle;


    public void init(java.awt.Graphics g){
        graphics_  = (Graphics2D) g;
        paint= new JFrame();
    }
    /**
     * Pinta una linea dadas dos posiciones
     * @param x1 X del primer punto
     * @param y1 Y del primer punto
     * @param x2 X del segundo punto
     * @param y2 Y del segundo punto
     */
    public void drawLine(float x1, float y1, float x2, float y2){
        graphics_.drawLine((int)x1, (int)y1, (int)x2, (int)y2);
    }
    public void setColor(int r, int g, int b, int a){
        Color myColor = new Color(r, g, b);
        graphics_.setColor(myColor);
    }

    /**
     * Crea una nueva fuente
     * @param filename Nombre de la fuente
     * @param size Tamaño del texto
     * @param isBold Si se quiere en negrita
     * @return Devuelve una fuente
     */
    public Font newFont(String filename, int size, boolean isBold){
            return null;
    }

    /**
     * Borra toda la ventana poniendola del color que se desee
     * @param r
     * @param g
     * @param b
     */
    public void clear(int r, int g, int b){
        setColor(r,g,b, 255);
        fillRect(0,0,getWidth(),getHeight());
    }


    /**
     * Dibuja un rectangulo relleno
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void fillRect(float x1, float y1, float x2, float y2){
        Rectangle r = new Rectangle((int)x1,(int) y1, (int)x2-(int)x1, (int)y2-(int)y1);
        graphics_.fillRect(
                (int) r.getX(),
                (int)r.getY(),
                (int)r.getWidth(),
                (int)r.getHeight()
        );
    }

    /**
     * Escribe el texto con la fuente y el color activos
     * @param text Texxto a escribir
     * @param x Posicion del texto
     * @param y Posicion del texto
     */
    public void drawText(String text, float x, float y){
        graphics_.drawString(text, x, y);
    }

    /**
     * @return Devuelve el ancho de la pantalla
     */
    public int getWidth(){
        return paint.getWidth();
    }
    /**
     * @return Devuelve el alto de la pantalla
     */
    public int getHeight(){
        return  paint.getHeight();
    }

    public void translate(float x, float y){
        graphics_.translate(x, y);
        translateX_ = x; translateY_ = y;
    }

    public void scale(float x){
        graphics_.scale(-x, x);
        scale_  = x;
    }

    public void rotate(float angle){
        graphics_.rotate(angle);
    }

    //Disposes of this graphics context and releases any system resources that it is using.
    public void dispose(){
        graphics_.dispose();
    }

}
