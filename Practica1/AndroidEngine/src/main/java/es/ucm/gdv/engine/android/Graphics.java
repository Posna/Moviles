package es.ucm.gdv.engine.android;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import es.ucm.gdv.engine.Font;

public class Graphics implements es.ucm.gdv.engine.Graphics {
    Canvas canvas_;
    Paint paint = new Paint();
    float translateX_; //Transformacion
    float translateY_;
    float scale_; //Escalado
    float angle;

    public void prepararPintado(Canvas c){
        canvas_ = c;
    }

    /**
     * Pinta una linea dadas dos posiciones
     * @param x1 X del primer punto
     * @param y1 Y del primer punto
     * @param x2 X del segundo punto
     * @param y2 Y del segundo punto
     */
    public void drawLine(float x1, float y1, float x2, float y2){
        canvas_.drawLine(x1, y1, x2, y2, paint);
        System.out.println("Pinta linea en: " + x2 + ", " + y2);
        //Puede que se necesite aumentar el grosor a 1 para que se escale correctamente
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
        canvas_.drawColor(Color.argb(255, r, g, b));
    }


    /**
     * Dibuja un rectangulo relleno
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void fillRect(float x1, float y1, float x2, float y2){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        canvas_.drawRect(x1*scale_ + translateX_, y1*scale_ + translateY_
                , x2*scale_ + translateX_, y2*scale_ + translateY_, paint);
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * Escribe el texto con la fuente y el color activos
     * @param text Texxto a escribir
     * @param x Posicion del texto
     * @param y Posicion del texto
     */
    public void drawText(String text, float x, float y){
        Paint paint = new Paint();
        canvas_.drawText(text, x*scale_ + translateX_, y*scale_ + translateY_, paint);
    }

    /**
     * @return Devuelve el ancho de la pantalla
     */
    public int getWidth(){
        return canvas_.getWidth();
    }

    /**
     * @return Devuelve el alto de la pantalla
     */
    public int getHeight(){
        return canvas_.getHeight();
    }

    /**
     * Establece el color a utilizar
     * @param r
     * @param g
     * @param b
     * @param a
     */
    public void setColor(int r, int g, int b, int a){
        paint.setColor(Color.argb(a, r, g, b ));
    }


    public void translate(float x, float y){
        canvas_.translate(x, y);
        translateX_ = x; translateY_ = y;
    }

    public void scale(float x){
        canvas_.scale(x, x);
        scale_  = x;
    }

    public void rotate(float angle){
        canvas_.rotate(angle);
    }

    public void save(){
        canvas_.save();
    }

    public void restore(){
        canvas_.restore();
    }
}
