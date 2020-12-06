package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Line extends GameObject {
    float length_;
    Vector2D offSet_;
    boolean haveOffset_ = false;
    boolean idea_ = true;
    float time1_ = 0;
    float time2_ = 0;
    float t1_ = 0;
    float t2_ = 0;
    int sentido = 1;
    Vector2D velAux_;

    Vector2D p1_;
    Vector2D p2_;

    Vector2D posIni_;
    Vector2D posFin_;

    public Line(Vector2D p1, Vector2D p2){
        super((p1.x_+p2.x_)/2.0f, (p1.y_+p2.y_)/2.0f);
        length_ = (float) Math.sqrt(Math.pow(p2.x_-p1.x_, 2) + Math.pow(p2.y_-p1.y_, 2));
    }

    public Line(Vector2D c, float angle, float lenght, float speed){
        super(c.x_, c.y_);
        angle_ = angle;
        length_ = lenght;
        angleVel_ = speed;
    }

    @Override
    public void update(float deltaTime){
        if(haveOffset_){
            if(t1_>= time1_){
                if(t2_ >= time2_){
                    Vector2D aux = posFin_;
                    posFin_ = posIni_;
                    posIni_ = aux;
                    t2_ = 0;
                    t1_ = 0;
                }else
                    t2_ += deltaTime;
            }else {
                t1_ += deltaTime;
                float porcentaje = t1_/time1_;
                if(porcentaje > 1)
                    porcentaje = 1.0f;
                pos_ = new Vector2D(posFin_.x_ * porcentaje + posIni_.x_ *  (1.0f-porcentaje), posFin_.y_ * porcentaje + posIni_.y_ *  (1.0f-porcentaje));
            }

        }
        calculatePoints();
        super.update(deltaTime);
    }

    public void render(Graphics g){
        g.save();
        g.translate(pos_.x_, pos_.y_);
        g.rotate(angle_);
        g.drawLine(- length_/2.0f, 0.0f, length_/2.0f, 0.0f);
        g.restore();
    }


    /**
     * Añade offset a la linea
     * @param offSet vector de movimiento
     * @param time1 Tiempo que tarda en llegar
     * @param time2 Tiempo de espera hasta volver a moverse
     */
    public void setOffSet(Vector2D offSet, float time1, float time2){
        haveOffset_ = true;
        offSet_ = offSet;
        time1_ = time1;
        time2_ = time2;
        vel_ = new Vector2D(0, 0);
        posIni_ = pos_;
        posFin_ = new Vector2D(pos_.x_ + offSet_.x_, pos_.y_ + offSet_.y_);
        velAux_ = vel_;
    }

    /**
     * Calculo de los extremos de la linea
     */
    void calculatePoints(){
        p1_ = new Vector2D(pos_.x_ + (float)Math.cos(Math.toRadians(angle_))*length_/2, pos_.y_ + (float)Math.sin(Math.toRadians(angle_))*length_/2);
        p2_ = new Vector2D(pos_.x_ + (float)Math.cos(Math.toRadians(angle_))*(-length_/2), pos_.y_ + (float)Math.sin(Math.toRadians(angle_))*(-length_/2));
    }
}
