package es.ucm.gdv.offtheline;

public class Utils {
     static public float pointDistance(Vector2D a, Vector2D b){
         float x = b.x_ - a.x_;
         float y = b.y_ - a.y_;
         return (float)Math.sqrt(x*x + y*y);
     }

    static public float pointSegmentDistance(Vector2D a, Vector2D b, Vector2D c){
        float u = ((c.x_ - a.x_)*(b.x_ - a.x_) + (c.y_ - a.y_)*(b.y_ - a.y_))/((b.x_-a.x_)*(b.x_-a.x_) + (b.y_-a.y_)*(b.y_-a.y_));

        float x = b.x_ - a.x_;
        float y = b.y_ - a.y_;
        return (float)Math.sqrt(x*x + y*y);
    }

    static public Vector2D segmentCollition(Vector2D a, Vector2D b, Vector2D c, Vector2D d){


         return null;
    }

}
