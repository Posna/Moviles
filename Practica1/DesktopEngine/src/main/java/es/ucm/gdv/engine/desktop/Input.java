package es.ucm.gdv.engine.desktop;

import java.util.List;

public class Input implements es.ucm.gdv.engine.Input {

    public class TouchEvent {
        List <es.ucm.gdv.engine.Input.TouchEvent> events;
        public List<es.ucm.gdv.engine.Input.TouchEvent> getTouchEvents() {
            return  events;
        }

    }
}

