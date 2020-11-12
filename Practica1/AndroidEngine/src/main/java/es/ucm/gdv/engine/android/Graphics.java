package es.ucm.gdv.engine.android;

import android.graphics.Color;

import es.ucm.gdv.engine.Font;

public class Graphics implements es.ucm.gdv.engine.Graphics {
    /**
     * Pinta una linea dadas dos posiciones
     * @param x1 X del primer punto
     * @param y1 Y del primer punto
     * @param x2 X del segundo punto
     * @param y2 Y del segundo punto
     */
    public void drawLine(float x1, float y1, float x2, float y2){

    }

    /**
     * Crea una nueva fuente
     * @param filename Nombre de la fuente
     * @param size Tamaño del texto
     * @param isBold Si se quiere en negrita
     * @return Devuelve una fuente
     */
    public Font newFont(String filename, int size, boolean isBold){

    }

    /**
     * Borra toda la ventana poniendola del color que se desee
     * @param color Color del fondo
     */
    public void clear(Color color){

    }


    /**
     * Dibuja un rectangulo relleno
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    public void fillRect(float x1, float y1, float x2, float y2){

    }

    /**
     * Escribe el texto con la fuente y el color activos
     * @param text Texxto a escribir
     * @param x Posicion del texto
     * @param y Posicion del texto
     */
    public void drawText(String text, float x, float y){

    }

    /**
     * @return Devuelve el ancho de la pantalla
     */
    public int getWidth(){

    }

    /**
     * @return Devuelve el alto de la pantalla
     */
    public int getHeight(){

    }
}
