package es.ucm.gdv.offtheline;

import javax.swing.JFrame;

import es.ucm.gdv.engine.desktop.Engine;
import es.ucm.gdv.engine.desktop.Graphics;

public class Main {

    public static void main(String[] args){
        Engine engine_ = new Engine();
        OffTheLineLogic logic_ = new OffTheLineLogic(engine_, false);

        // Vamos allá.
        long lastFrameTime = System.nanoTime();
        long informePrevio = lastFrameTime; // Informes de FPS
        int frames = 0;
        // Bucle principal
        while(true) {
            long currentTime = System.nanoTime();
            long nanoElapsedTime = currentTime - lastFrameTime;
            lastFrameTime = currentTime;
            double elapsedTime = (double) nanoElapsedTime / 1.0E9;
            logic_.update((float)elapsedTime);
            // Informe de FPS
            if (currentTime - informePrevio > 1000000000l) {
                long fps = frames * 1000000000l / (currentTime - informePrevio);
                System.out.println("" + fps + " fps");
                frames = 0;
                informePrevio = currentTime;
            }
            ++frames;
            // Pintamos el frame con el BufferStrategy
            do {
                do {
                    engine_.getGraphics().preparePaint();
                    logic_.setLogicalScale(engine_.getGraphics().getWidth(), engine_.getGraphics().getHeight());
                    try {
                        engine_.getGraphics().clear(0, 0, 0);
                        //engine_.getGraphics().translate(400, 400);
                        //engine_.getGraphics().scale(1.2f);

                        logic_.render();
                        //engine_.getGraphics().setColor(125, 225, 100, 255);
                        //engine_.getGraphics().drawLine(0.0f, 0.0f, 400.0f, 400.0f);
                    }
                    finally {
                        engine_.getGraphics().dispose();
                    }
                } while(engine_.getGraphics().contentsRestored());
                engine_.getGraphics().show();
            } while(engine_.getGraphics().contentsLost());
			/*
			// Posibilidad: cedemos algo de tiempo. es una medida conflictiva...
			try {
				Thread.sleep(1);
			}
			catch(Exception e) {}
			*/
        } // while

        // Si tuvieramos un mecanismo para acabar limpiamente, tendríamos
        // que liberar el BufferStrategy.
        // strategy.dispose();
    }

}