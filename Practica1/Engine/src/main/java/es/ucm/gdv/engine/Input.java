package es.ucm.gdv.engine;

import java.util.List;

public interface Input {

    class TouchEvent{
        List <TouchEvent> events;
        List <TouchEvent> getTouchEvents();

    }
}
