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
         Vector2D v1 = new Vector2D(b.x_ - a.x_, b.y_- a.y_);
         Vector2D v2 = new Vector2D(d.x_ - c.x_, d.y_- c.y_);


         if((v1.x_*v2.y_) == (v1.y_*v2.x_)) {
             //System.out.println("Paralelas");
             return null;
         }

         float s = (c.y_ - a.y_)*v2.x_*v1.x_ + a.x_*v1.y_*v2.x_-c.x_*v2.y_*v1.x_;
         float x = (s)/(v1.y_*v2.x_ - v2.y_*v1.x_);
         float y;
         if(v1.x_ == 0) {
             y = ((x * v2.y_ - c.x_ * v2.y_) / v2.x_) + c.y_;
         }
         else {
             y = ((x * v1.y_ - a.x_ * v1.y_) / v1.x_) + a.y_;
         }

         if(x > 2000 || x < -2000 || y > 2000 || y < -2000) {
             return null;
         }

         //System.out.println("Coordenadas de corte x: " + x + " y: " + y);
         Vector2D corte = new Vector2D(x, y);

         if(c.isEqual(corte, 0.0005f) || d.isEqual(corte, 0.0005f))
             return null;


         if(insideSegment(corte, a, b) && insideSegment( corte, c, d)){
             return corte;
         }
         //System.out.println("No se cortan");
         return null;
    }

    static boolean insideSegment(Vector2D p, Vector2D a, Vector2D b){
        Vector2D v1 = new Vector2D(b.x_ - a.x_, b.y_- a.y_);
        Vector2D v2 = new Vector2D(b.x_ - p.x_, b.y_- p.y_);

        return v1.getScalar() > v2.getScalar() && v1.x_*v2.x_ >= 0 && v1.y_*v2.y_ >= 0;
    }

}
