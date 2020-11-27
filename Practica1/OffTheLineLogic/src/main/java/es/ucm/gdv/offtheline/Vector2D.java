package es.ucm.gdv.offtheline;

import java.lang.Math;
import java.util.Vector;

public class Vector2D {

    public float x_, y_;

    Vector2D(float x, float y){
        x_ = x;
        y_ = y;
    }

    Vector2D add(Vector2D a){
        float x = x_ + a.x_;
        float y = y_ + a.y_;

        return new Vector2D(x, y);
    }

    void normalize(){
        float n = (float)Math.sqrt(x_*x_ + y_*y_);
        x_ = x_/n;
        y_ = y_/n;
    }

    boolean isEqual(Vector2D a ){
        return a.x_ == x_ && a.y_ == y_;
    }

}
