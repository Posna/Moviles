package es.ucm.gdv.offtheline;

import java.util.List;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;
import es.ucm.gdv.engine.Logic;

public class MainMenu implements Logic {

    Engine engine_;

    StateMachine machine_;

    /*** Menu ***/
    Button easyModeButton_;
    Button hardModeButton_;
    boolean loading = false;

    MainMenu(Engine e, StateMachine machine){
        easyModeButton_ = new Button(new Vector2D(0, 0), new Vector2D(e.getGraphics().getWidth(), e.getGraphics().getHeight()), "Easy Mode", null);
        hardModeButton_ = new Button(new Vector2D(-e.getGraphics().getWidth()/2, -100), new Vector2D(0, -140), "Hard Mode", null);

        machine_ = machine;
    }

    public void update(float deltaTime){

    }

    public void render(){
        Graphics g = engine_.getGraphics();

        easyModeButton_.render(g);
        hardModeButton_.render(g);
    }

    public void handleInput(){
        /*List<Input.TouchEvent> l = engine_.getInput().getTouchEvents();
        if(l.size()!=0){
            System.out.println("HandleInput");
            for (Input.TouchEvent e: l) {
                switch (e.type){
                    case 1:
                        Vector2D aux = transformCoord(e.x, e.y);
                        System.out.println(" X" + aux.x_ + " Y" + aux.y_);
                        if(hardModeButton_.handleInput(aux.x_, aux.y_) && !loading){
                            System.out.println("hard paso!");

                        }
                        if(easyModeButton_.handleInput(aux.x_, aux.y_) && !loading){
                            System.out.println("Easy paso!");
                        }

                        break;
                    default:
                        break;
                }
            }
        }*/
    }



}
