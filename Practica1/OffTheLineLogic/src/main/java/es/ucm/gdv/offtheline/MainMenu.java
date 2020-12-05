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
    boolean loading;

    MainMenu(Engine e, StateMachine machine){
        easyModeButton_ = new Button(new Vector2D(-300, 0), new Vector2D(0, -50), "Easy Mode", null);
        hardModeButton_ = new Button(new Vector2D(-300, -200), new Vector2D(0, -250), "Hard Mode", null);
        System.out.println(e.getGraphics().getWidth()/2);

        machine_ = machine;
        engine_ = e;
    }

    public void update(float deltaTime){

    }

    public void render(){
        Graphics g = engine_.getGraphics();
        easyModeButton_.render(g);
        hardModeButton_.render(g);
    }

    public void handleInput(){
        List<Input.TouchEvent> l = engine_.getInput().getTouchEvents();
        if(l.size()!=0){

            for (Input.TouchEvent e: l) {
                System.out.println("HandleInput");
                switch (e.type){
                    case 1:
                        Vector2D aux = new Vector2D(engine_.getGraphics().transformXToCenter(e.x),
                                engine_.getGraphics().transformYToCenter(e.y));
                        System.out.println(" X" + aux.x_ + " Y" + aux.y_);
                        if(hardModeButton_.handleInput(aux.x_, aux.y_)){
                            System.out.println("hard paso!");
                            machine_.pushState(new OffTheLineLogic(engine_, machine_, true));
                            return;

                        }
                        if(easyModeButton_.handleInput(aux.x_, aux.y_)){
                            System.out.println("Easy paso!");
                            machine_.pushState(new OffTheLineLogic(engine_, machine_, false));//El click del raton no va bien en android
                            return;
                        }

                        break;
                    default:
                        break;
                }
            }
        }
    }

}
