package es.ucm.gdv.engine.desktop;

import java.awt.Font;

import javax.swing.DefaultListSelectionModel;

import sun.security.util.Resources;

public class Font implements es.ucm.gdv.engine.Font {

    java.awt.Font myFont;
    int size_;
    boolean bold_;

    public Font(){
    }

    public void init(String filename, int size, boolean isBold){
        size_ = size;
        bold_= isBold;
        if(isBold)
            myFont = new java.awt.Font(filename, java.awt.Font.BOLD, size);
        else
            myFont = new java.awt.Font(filename, java.awt.Font.PLAIN, size);
    }

    public java.awt.Font getMyFont(){
        return myFont;
    }

}
