package es.ucm.gdv.offtheline;

import java.lang.Math;
import java.util.Vector;

public class Vector2D {

    public float x_, y_;

    Vector2D(float x, float y){
        x_ = x;
        y_ = y;
    }

    Vector2D(Vector2D v){
        x_ = v.x_;
        y_ = v.y_;
    }

    /**
     * Suma dos vectores sin modificar ninguno de ellos
     * @param a Vector a sumar con this
     * @return Devuelve el vector sumado
     */
    Vector2D add(Vector2D a){
        float x = x_ + a.x_;
        float y = y_ + a.y_;

        return new Vector2D(x, y);
    }

    /**
     * Normaliza el vector
     */
    void normalize(){
        float n = (float)Math.sqrt(x_*x_ + y_*y_);
        x_ = x_/n;
        y_ = y_/n;
    }

    /**
     * Devuelve si dos vectores son iguales
     * @param a Vector con el que se comprueba la igualdad
     * @return True si son iguales (misma x e y)
     */
    boolean isEqual(Vector2D a ){
        return a.x_ == x_ && a.y_ == y_;
    }

    /**
     * Devuelve si los vectores son iguales con un margen de error
     * @param a Vector con el que comprobar
     * @param d Margen de error
     * @return devuelve true si son el mismo o muy cercanos
     */
    boolean isEqual(Vector2D a, float d){
        return a.x_ + d >= x_ && a.x_ - d <= x_ && a.y_ + d >= y_  && a.y_ - d <= y_;
    }

    /**
     * Calcula el escalar del vector
     * @return Devuelve el escalar
     */
    float getScalar(){
        return (float)Math.sqrt(x_*x_ + y_*y_);
    }

}
