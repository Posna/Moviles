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
                vel_ = new Vector2D(0.0f, 0.0f);
                if(t2_ >= time2_){
                    vel_ = new Vector2D(-velAux_.x_, -velAux_.y_);
                    velAux_ = vel_;
                    t2_ = 0;
                    t1_ = 0;
                    sentido = sentido*-1;
                    System.out.println(sentido);
                    if(sentido > 0){
                        pos_ = posIni_;
                    }
                }else
                    t2_ += deltaTime;
            }else
                t1_ += deltaTime;

        }
        super.update(deltaTime);
    }

    public void render(Graphics g){
        g.save();
        g.translate(pos_.x_, pos_.y_);
        g.rotate(angle_);
        g.drawLine(- length_/2.0f, 0.0f, length_/2.0f, 0.0f);
        g.restore();
    }

    public void setOffSet(Vector2D offSet, float time1, float time2){
        haveOffset_ = true;
        offSet_ = offSet;
        time1_ = time1;
        time2_ = time2;
        vel_ = new Vector2D(offSet_.x_/time1, offSet_.y_/time1);
        posIni_ = pos_;
        posFin_ = new Vector2D(pos_.x_ + offSet_.x_, pos_.y_ + offSet_.y_);
        System.out.println("La X ini: " + posIni_.x_);
        System.out.println("La Y ini: " + posIni_.y_);

        System.out.println("La X: " + posFin_.x_);
        System.out.println("La Y: " + posFin_.y_);
        //vel_.normalize();
        velAux_ = vel_;
    }
}
