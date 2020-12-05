package es.ucm.gdv.engine.desktop;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;

import es.ucm.gdv.engine.AbstractGraphics;
import es.ucm.gdv.engine.Font;

public class Graphics extends AbstractGraphics {

    Graphics2D graphics_;
    JFrame paint;
    java.awt.image.BufferStrategy strategy_;
    AffineTransform transform_;

    float translateX_; //Transformacion
    float translateY_;
    float scale_; //Escalado
    float angle;
    int offset_ = 22;


    public boolean init(float w, float h){
        paint = new JFrame("Practica 1");
        paint.setSize(800, (800+offset_));
        paint.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        paint.setIgnoreRepaint(true);
        paint.setVisible(true);
        createBufferStrategy();

        initLogicSizes(w, h);

        return true;
    }

    private void createBufferStrategy(){
        // Intentamos crear el buffer strategy con 2 buffers.
        int intentos = 100;
        while(intentos-- > 0) {
            try {
                paint.createBufferStrategy(2);
                break;
            }
            catch(Exception e) {
            }
        } // while pidiendo la creación de la buffeStrategy
        if (intentos == 0) {
            System.err.println("No pude crear la BufferStrategy");
            return;
        }
        else {
            // En "modo debug" podríamos querer escribir esto.
            //System.out.println("BufferStrategy tras " + (100 - intentos) + " intentos.");
        }

        // Obtenemos el Buffer Strategy que se supone que acaba de crearse.
        strategy_ = paint.getBufferStrategy();
    }

    public void preparePaint(){
        graphics_ = (Graphics2D)strategy_.getDrawGraphics();
        clear(0, 0, 0);
        calculateScale();
        preRender();
        //translate(0, 0);
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
        setColor(r ,g ,b, 255);
        fillRect(0,getHeight(), getWidth(),getHeight());
    }


    /**
     * Dibuja un rectangulo relleno
     * @param x1
     * @param y1
     * @param w
     * @param h
     */
    public void fillRect(float x1, float y1, float w, float h){
        Rectangle r = new Rectangle((int)x1,(int) y1 - (int)h, (int)w, (int)h);
        graphics_.fillRect(
                (int) r.getX(),
                (int)r.getY(),
                (int)r.getWidth(),
                (int)r.getHeight()
        );
        //graphics_.drawRect((int)r.getX(),(int)r.getY(), (int)r.getWidth(), (int)r.getHeight());
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
        translateX_ = x; translateY_ = y + offset_;
    }

    public void scale(float x){
        graphics_.scale(x, -x);
        scale_  = x;
    }

    public void rotate(float angle){
        graphics_.rotate(Math.toRadians(angle));
    }


    public void save() {
        transform_ = graphics_.getTransform();
    }

    public void restore() {
        graphics_.setTransform(transform_);
    }

    public void show(){
        strategy_.show();
    }

    //Disposes of this graphics context and releases any system resources that it is using.
    public void dispose(){
        graphics_.dispose();
    }

    public boolean contentsRestored(){
        return strategy_.contentsRestored();
    }

    public boolean contentsLost(){
        return strategy_.contentsLost();
    }

    public void addMouseListener(MouseListener m){
        paint.addMouseListener(m);
    }

}
