package es.ucm.gdv.engine.desktop;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.List;

public class Input implements es.ucm.gdv.engine.Input {


    List<TouchEvent> events;
    Input(){
        events = new ArrayList<TouchEvent>();
        m = new MListener();
    }

    /**
     * Devuelve la lista de eventos
     * @return Lista de eventos
     */
    synchronized public List<TouchEvent> getTouchEvents() {
        List<TouchEvent> aux = new ArrayList<TouchEvent>();
        for (TouchEvent a: events) {
            aux.add(a);
        }
        events.clear();
        return aux;
    }

    /**
     * Añade un nuevo evento
     * @param e evento
     */
    synchronized void pushEvent(TouchEvent e){

        events.add(e);
    }
    MListener m;


    class MListener implements MouseListener {

        public void mouseClicked(MouseEvent mouseEvent) {
            System.out.println("Se ha pulsado " +  mouseEvent.getID());
            TouchEvent event = new TouchEvent();
            event.x = mouseEvent.getX();
            event.y = mouseEvent.getY();
            event.click = mouseEvent.getButton();
            event.type = 1;
            pushEvent(event);
        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {
            System.out.println("Se ha mouseEntered");
        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {
            System.out.println("Se ha mouseExited");
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            System.out.println("Se ha mousePressed");
            System.out.println("Se ha pulsado " +  mouseEvent.getID());
            TouchEvent event = new TouchEvent();
            event.x = mouseEvent.getX();
            event.y = mouseEvent.getY();
            event.click = mouseEvent.getButton();
            event.type = 0;
            pushEvent(event);

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {
            System.out.println("Se ha mouseReleased");
            System.out.println("Se ha pulsado " +  mouseEvent.getID());
            TouchEvent event = new TouchEvent();
            event.x = mouseEvent.getX();
            event.y = mouseEvent.getY();
            event.click = mouseEvent.getButton();
            event.type = 1;
            pushEvent(event);
        }


    }
}

