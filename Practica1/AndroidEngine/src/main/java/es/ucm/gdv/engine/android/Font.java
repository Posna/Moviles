package es.ucm.gdv.engine.android;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class Font implements es.ucm.gdv.engine.Font {
    TextView textView;

    public void init(String filename, int size, boolean isBold){
        textView=t;
        Typeface typeface = Typeface.createFromAsset(c.getAssets(), "fonts/Bangers-Regular.ttf");
        textView.setTypeface(typeface);
    }

    public TextView getMyFont(){
        return  textView;
    }

}
