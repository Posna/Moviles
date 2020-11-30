package es.ucm.gdv.engine;

import java.util.List;

public interface Input {

    public enum Eventos {Clicked, Entered}

    public class TouchEvent {
        public float x, y;
        public int type;
        public int click;
    }

    public List <TouchEvent> getTouchEvents();

}
