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
    Texto offTheLine;
    Texto gameCopied;
    Texto easyMode;
    Texto explainEasy;
    Texto hardMode;
    Texto explainHard;
    boolean loading;

    MainMenu(Engine e, StateMachine machine){
        easyModeButton_ = new Button(new Vector2D(-300, -40), new Vector2D(100, -90), "Easy Mode", null);
        hardModeButton_ = new Button(new Vector2D(-300, -90), new Vector2D(100, -140), "Hard Mode", null);
        offTheLine = new Texto(new Vector2D(-300, 160), "OFF THE LINE", "Bungee-Regular.ttf", 60, true);
        offTheLine.setColor(66, 110, 255);
        gameCopied = new Texto(new Vector2D(-300, 130), "A GAME COPIED TO BRAYAN PERFETTO", "Bungee-Regular.ttf", 25, true);
        gameCopied.setColor(66, 110, 255);
        easyMode = new Texto(new Vector2D(-300, -80), "EASY MODE", "Bungee-Regular.ttf", 30, true);
        easyMode.setColor(255, 255, 255);
        explainEasy = new Texto(new Vector2D(-100, -80), "(SLOW SPEED, 10 LIVES)", "Bungee-Regular.ttf", 15, true);
        explainEasy.setColor(124, 124, 124);
        hardMode = new Texto(new Vector2D(-300, -130), "HARD MODE", "Bungee-Regular.ttf", 30, true);
        hardMode.setColor(255, 255, 255);
        explainHard = new Texto(new Vector2D(-90, -130), "(FAST SPEED, 5 LIVES)", "Bungee-Regular.ttf", 15, true);
        explainHard.setColor(124, 124, 124);

        machine_ = machine;
        engine_ = e;
    }

    public void update(float deltaTime){

    }

    public void render(){
        Graphics g = engine_.getGraphics();
        easyModeButton_.render(g);
        hardModeButton_.render(g);
        offTheLine.render(g);
        gameCopied.render(g);
        easyMode.render(g);
        explainEasy.render(g);
        hardMode.render(g);
        explainHard.render(g);
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
