package es.ucm.gdv.engine.android;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class Input implements es.ucm.gdv.engine.Input {

    List<TouchEvent> events;
    MyTouch m;

    Input(){
        events = new ArrayList<TouchEvent>();
        m = new MyTouch();
    }

    /**
     * Devuelve la lista de eventos desde la ultima vez que se llamo
     */
    synchronized public List<TouchEvent> getTouchEvents(){
        List<TouchEvent> aux = new ArrayList<TouchEvent>();
        for (TouchEvent a: events) {
            aux.add(a);
        }
        events.clear();
        return aux;
    }

    synchronized void pushEvent(TouchEvent e){
        events.add(e);
    }

    public MyTouch getMyTouch(){
        return m;
    }

    class MyTouch implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            TouchEvent e = new TouchEvent();
            e.x = event.getX();
            e.y = event.getY();
            e.type = event.getAction();
            e.click = event.getActionIndex();
            pushEvent(e);
            return true;
        }
    }

}
