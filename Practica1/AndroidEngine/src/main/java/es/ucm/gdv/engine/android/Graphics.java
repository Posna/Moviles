package es.ucm.gdv.engine.android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import es.ucm.gdv.engine.AbstractGraphics;

public class Graphics extends AbstractGraphics {
    Canvas canvas_;
    Paint paint = new Paint();
    Context context_;
    int _width, _height;

    /**
     * Init de hraphics
     * @param w ancho logico
     * @param h alto logico
     * @param width ancho de la pantalla
     * @param height alto de la pantalla
     * @param c
     */
    public void init(float w, float h, int width, int height, Context c){
        context_ = c;
        _width = width;
        _height = height;
        initLogicSizes(w, h);
        calculateScale();
    }

    /**
     * Prepara el pintado
     */
    public void prepararPintado(Canvas c){
        canvas_ = c;
        clear(0, 0, 0);
        preRender();
    }

    /**
     * Pinta una linea dadas dos posiciones
     * @param x1 X del primer punto
     * @param y1 Y del primer punto
     * @param x2 X del segundo punto
     * @param y2 Y del segundo punto
     */
    public void drawLine(float x1, float y1, float x2, float y2){
        paint.setStrokeWidth(2);
        canvas_.drawLine(x1, y1, x2, y2, paint);
    }

    /**
     * Crea una nueva fuente
     * @param filename Nombre de la fuente
     * @param size Tamaño del texto
     * @param isBold Si se quiere en negrita
     * @return Devuelve una fuente
     */
    public Font newFont(String filename, int size, boolean isBold){
        Font f = new Font(context_);
        f.init(filename, size, isBold);
        paint.setTextSize(size);
        paint.setFakeBoldText(isBold);
        paint.setTypeface(f.getMyFont());
        paint.setStyle(Paint.Style.FILL);
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
     * @param w
     * @param h
     */
    public void fillRect(float x1, float y1, float w, float h){
        Rect r = new Rect((int)x1, (int)y1, (int)x1 + (int)w, (int)y1 - (int)h);
        paint.setStyle(Paint.Style.FILL);
        canvas_.drawRect(r, paint);
        paint.setStyle(Paint.Style.STROKE);
    }

    /**
     * Escribe el texto con la fuente y el color activos
     * @param text Texxto a escribir
     * @param x Posicion del texto
     * @param y Posicion del texto
     */
    public void drawText(String text, float x, float y){
        canvas_.drawText(text, x , y, paint);
    }

    /**
     * @return Devuelve el ancho de la pantalla
     */
    public int getWidth(){
        return _width;//canvas_.getWidth();
    }

    /**
     * @return Devuelve el alto de la pantalla
     */
    public int getHeight(){
        return _height;//.getHeight();
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
    }

    public void scale(float x){
        canvas_.scale(x, -x);
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
