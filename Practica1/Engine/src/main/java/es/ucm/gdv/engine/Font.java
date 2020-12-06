package es.ucm.gdv.engine;

public interface Font {
    void init(String filename, int size, boolean isBold);
    java.awt.Font getMyFont();
}
