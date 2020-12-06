package es.ucm.gdv.engine;

import java.awt.FontFormatException;
import java.io.IOException;

public interface Graphics {
    /**
     * Crea una nueva fuente
     * @param filename Nombre de la fuente
     * @param size Tamaño del texto
     * @param isBold Si se quiere en negrita
     * @return Devuelve una fuente
     */
    Font newFont(String filename, int size, boolean isBold);

    /**
     * Borra toda la ventana poniendola del color que se desee
     * @param color Color del fondo
     */
    void clear(int r, int g, int b);

    /**
     * Pinta una linea dadas dos posiciones
     * @param x1 X del primer punto
     * @param y1 Y del primer punto
     * @param x2 X del segundo punto
     * @param y2 Y del segundo punto
     */
    void drawLine(float x1, float y1, float x2, float y2);

    /**
     * Dibuja un rectangulo relleno
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    void fillRect(float x1, float y1, float w, float h);

    /**
     * Escribe el texto con la fuente y el color activos
     * @param text Texxto a escribir
     * @param x Posicion del texto
     * @param y Posicion del texto
     */
    void drawText(String text, float x, float y);

    /**
     * @return Devuelve el ancho de la pantalla
     */
    int getWidth();

    /**
     * @return Devuelve el alto de la pantalla
     */
    int getHeight();

    /**
     * Establece el color a utilizar
     * @param r
     * @param g
     * @param b
     * @param a
     */
    void setColor(int r, int g, int b, int a);

    /**
     * Transforma las coordenadas
     * @param x
     * @param y
     */
    void translate(float x, float y);

    /**
     * Escala las coordenadas
     * @param x
     * @param y
     */
    void scale(float x);

    /**
     * Gira las cordinadas un angulo concreto
     * @param angle
     */
    void rotate(float angle);

    void save();

    void restore();

    float transformXToCenter(float x);

    float transformYToCenter(float y);
}