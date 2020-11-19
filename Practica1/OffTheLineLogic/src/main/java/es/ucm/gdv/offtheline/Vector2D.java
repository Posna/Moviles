package es.ucm.gdv.offtheline;

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

}
