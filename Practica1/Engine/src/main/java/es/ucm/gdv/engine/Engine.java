package es.ucm.gdv.engine;

import java.io.InputStream;

public interface Engine {
    Graphics getGraphics();
    Input getInput();
    InputStream openInputFile(String filename);
}
