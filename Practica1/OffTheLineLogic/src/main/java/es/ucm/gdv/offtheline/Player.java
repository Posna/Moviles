package es.ucm.gdv.offtheline;

import java.util.Random;
import java.util.Vector;

import es.ucm.gdv.engine.Graphics;

public class Player extends Cube {

    Vector2D goingTo_;
    Vector2D lastPos_;
    float normalSpeed;

    Path actualPath_;
    boolean reloj = true;
    boolean saltando_ = false;
    float time;

    /***Variables para la muerte del juegador**/
    boolean death = false;
    Vector<Line> linesDied;

    Vector2D dir;

    Player(Vector2D pos, Path path, float speed){
        super(pos, 12);
        actualPath_ = path;
        speed_ = speed;
        normalSpeed = speed;
        goTo(actualPath_.getPunta2());
        time = Utils.pointDistance(pos_, actualPath_.getPunta2()) / speed_;
        angleVel_ = 180;

        linesDied = new Vector<Line>(10,  1);


    }

    @Override
    public void update(float deltaTime){
        if(death) {
            for (Line l : linesDied) {
                l.update(deltaTime);
            }
        }
        else {
            normalUpdate(deltaTime);
        }
    }

    /**
     * Update del jugador en caso de estar vivo
     * @param deltaTime
     */
    private void normalUpdate(float deltaTime){
        if(!saltando_){
            if(reloj)
                movimientoHorario();
            else
                movimientoAntihorario();
            time -= deltaTime;
        }
        lastPos_ = new Vector2D(pos_);
        super.update(deltaTime);
    }

    @Override
    public void render(Graphics g) {
        if(death){
            for (Line l: linesDied) {
                l.render(g);
            }
        }
        else
            super.render(g);
    }


    /**
     * Movimiento en sentido de las agujas del reloj sobre los paths
     */
    void movimientoHorario(){
        if(time <= 0) {
            pos_ = new Vector2D(actualPath_.getPunta2());
            actualPath_ = actualPath_.nextPath_;
            time = Utils.pointDistance(pos_, actualPath_.getPunta2()) / speed_;
            goTo(actualPath_.getPunta2());
        }
    }

    /**
     * Movimiento en sentido contrario a las agujas del reloj sobre los paths
     */
    void movimientoAntihorario(){
        if(time <= 0) {
            pos_ = new Vector2D(actualPath_.getPunta1());
            actualPath_ = actualPath_.lastPath_;
            time = Utils.pointDistance(pos_, actualPath_.getPunta1()) / speed_;
            goTo(actualPath_.getPunta1());
        }
    }

    /**
     * Calcula el vector velocidad necesario para ir hacia un puto
     * @param v Punto al que se quiere ir
     */
    public void goTo(Vector2D v){
        goingTo_ = v;
        vel_ = new Vector2D(goingTo_.x_- pos_.x_, goingTo_.y_ - pos_.y_ );
        vel_.normalize();
    }

    public Path getActualPath(){
        return actualPath_;
    }
    public Vector2D getLastPos_(){ return lastPos_; }

    /**
     * Salta desde el path actual en direccion normal del mismo
     */
    public void jump(){
        if(!saltando_) {
            dir  = new Vector2D(vel_);
            vel_ = actualPath_.getNormal();
            vel_.normalize();
            pos_ = new Vector2D(pos_.add(vel_));
            saltando_ = true;
            speed_ = 1500;
        }
    }

    /**
     * Calcula la nueva direccion y ruta cuando el jugador aterriza
     * sobre otro path
     * @param p Punto en el que ha aterrizado
     * @param path Path en el que se encuentra
     */
    public void land(Vector2D p, Path path){
        saltando_ = false;
        pos_ = new Vector2D(p.x_, p.y_);
        speed_ = normalSpeed;
        actualPath_ = path;
        goTo(actualPath_.getPunta2());
        reloj = dir.x_*vel_.x_ >= 0 && dir.y_*vel_.y_ >= 0;


        if(reloj) {
            time = Utils.pointDistance(pos_, actualPath_.getPunta2()) / speed_;
            goTo(actualPath_.getPunta2());
        }
        else {
            time = Utils.pointDistance(pos_, actualPath_.getPunta1()) / speed_;
            goTo(actualPath_.getPunta1());
        }
    }


    /**
     * Se llama a este metodo cuando se quiere "matar" al jugador
     */
    public void kill(){
        death = true;
        for (int i = 0; i < 10; i++){
            Random r = new Random();
            Line l = new Line(pos_, r.nextInt(361), 12, 0);
            l.setOffSet(new Vector2D(r.nextInt(200) - 100, r.nextInt(200) - 100), 2, 3);
            linesDied.add(l);
        }
    }
}
