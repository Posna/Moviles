package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Path extends GameObject {

    float length_;
    Vector2D p1_;
    Vector2D p2_;

    Vector2D normal_;

    Path nextPath_; //Path siguiente
    Path lastPath_; //Path anterior

    int id_; //identificador del path

    public Path(Vector2D p1, Vector2D p2, int id){
        super((p1.x_+p2.x_)/2.0f, (p1.y_+p2.y_)/2.0f);
        length_ = (float) Math.sqrt(Math.pow(p2.x_-p1.x_, 2) + Math.pow(p2.y_-p1.y_, 2));
        p1_ = p1;
        p2_ = p2;

        id_ = id;

        normal_ = new Vector2D((p2.y_-p1.y_),-(p2.x_ - p1.x_));
    }


    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
    }

    public void render(Graphics g){
        g.save();
        g.drawLine(p1_.x_, p1_.y_, p2_.x_, p2_.y_);
        g.restore();
    }

    /**
     * Asignacion de la normal
     */
    public void setNormal(Vector2D normal){
        normal_ = normal;
    }
    public Vector2D getNormal(){ return normal_; }

    /**
     * @return devuelve un extremo del path
     */
    public Vector2D getPunta1(){
        return p1_;
    }

    /**
     * @return devuelve otro extremo del path
     */
    public Vector2D getPunta2(){
        return p2_;
    }

    /**
     * Añade path anterior
     */
    public void setLastPath(Path lastPath) {
        lastPath_ = lastPath;
    }

    /**
     * Añade path sig
     */
    public void setNextPath(Path nextPath){
        nextPath_ = nextPath;
    }

    public int getId(){ return id_; }
}
