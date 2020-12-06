package es.ucm.gdv.engine.desktop;

import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

import javax.swing.DefaultListSelectionModel;

import sun.security.util.Resources;

public class Font implements es.ucm.gdv.engine.Font {

    java.awt.Font myFont;
    int size_;
    boolean bold_;

    public Font(){
    }

    public void init(String filename, int size, boolean isBold){
        java.awt.Font f = null;
        try {
            f = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File("assets/fonts/" + filename));
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        size_ = size;
        bold_= isBold;
        if(isBold) {
            myFont = new java.awt.Font(f.getFontName(), java.awt.Font.BOLD, size);
        }
        else
            myFont = new java.awt.Font(f.getFontName(), java.awt.Font.PLAIN, size);
    }

    public java.awt.Font getMyFont(){
        return myFont;
    }

}
