package es.ucm.gdv.offtheline;

import es.ucm.gdv.engine.Graphics;

public class Coin extends GameObject {
    Vector2D rot_; //Vector donde rota
    float extAngle_; //Angulo exterior
    float speedExtAngle_; //Velocidad del angulo exterior
    float rad_; //Radio
    float lado_; //lado del coin

    boolean kill = false; //Boleano de muerte
    float timeDying_; //Tiempo muriendo
    float expansionN_; //numero de explansion

    public Coin(Vector2D rot, float rad, float speedExtAngle, float extAngle){
        super(rad, 0.0f);
        angleVel_ = 180;
        rot_ = rot;
        extAngle_ = extAngle;
        speedExtAngle_ = speedExtAngle;
        rad_ = rad;
        lado_ = 8;
    }

    @Override
    public void render(Graphics g) {
        g.save();
        g.translate(rot_.x_, rot_.y_);
        g.rotate(extAngle_);
        g.translate(pos_.x_, pos_.y_);
        g.rotate(angle_);
        g.drawLine(lado_/2,  lado_/2, - lado_/2,  lado_/2);
        g.drawLine(lado_/2, lado_/2, lado_/2, - lado_/2);
        g.drawLine(- lado_/2, - lado_/2, - lado_/2,  lado_/2);
        g.drawLine(- lado_/2, - lado_/2, lado_/2, - lado_/2);
        g.restore();
    }

    @Override
    public void update(float deltaTime){
        extAngle_+= speedExtAngle_*deltaTime;
        super.update(deltaTime);
        if(kill && timeDying_ >= 0) {
            lado_ += deltaTime*expansionN_;
            timeDying_ -= deltaTime;
        }
    }

    /**
     * Posicion real del coin
     */
    public Vector2D getRealPos(){
        double x = rot_.x_ + Math.cos(Math.toRadians(extAngle_))*rad_;
        double y = rot_.y_ + Math.sin(Math.toRadians(extAngle_))*rad_;
        return new Vector2D((float)x, (float)y);
    }

    /**
     * Empieza la cuenta atras y la expansion hasta que muere
     * @param timeDying Tiempo muriendo
     * @param expansionN numero de expansion
     *
     */
    public void kill(float timeDying, float expansionN){
        if(!kill) {
            kill = true;
            timeDying_ = timeDying;
            expansionN_ = expansionN;
        }
    }
}
