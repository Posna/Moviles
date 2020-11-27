package es.ucm.gdv.offtheline;

public class Player extends Cube {

    Vector2D goingTo_;
    Path actualPath_;
    boolean reloj = true;
    boolean saltando_ = false;
    float time;

    Player(Vector2D pos, Path path, float speed){
        super(pos, 12);
        actualPath_ = path;
        speed_ = speed;
        goTo(actualPath_.getPunta2());
        time = Utils.pointDistance(pos_, actualPath_.getPunta2()) / speed_;
        angleVel_ = 200;
    }

    @Override
    public void update(float deltaTime){
        if(!saltando_){
            if(reloj)
                movimientoHorario();
            else
                movimientoAntihorario();
            time -= deltaTime;

        }
        super.update(deltaTime);
    }

    void movimientoHorario(){
        if(time <= 0) {
            pos_ = new Vector2D(actualPath_.getPunta2().x_, actualPath_.getPunta2().y_);
            actualPath_ = actualPath_.nextPath_;
            time = Utils.pointDistance(pos_, actualPath_.getPunta2()) / speed_;
            goTo(actualPath_.getPunta2());
        }
        /*else {


        }*/
    }

    void movimientoAntihorario(){
        if(time <= 0) {
            pos_ = new Vector2D(actualPath_.getPunta1().x_, actualPath_.getPunta1().y_);
            actualPath_ = actualPath_.lastPath_;
            time = Utils.pointDistance(pos_, actualPath_.getPunta1()) / speed_;
            goTo(actualPath_.getPunta1());
        }
    }

    public void goTo(Vector2D v){
        goingTo_ = v;
        vel_ = new Vector2D(goingTo_.x_- pos_.x_, goingTo_.y_ - pos_.y_ );
        vel_.normalize();
    }

    public Path getActualPath(){
        return actualPath_;
    }
}
