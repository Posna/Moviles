package es.ucm.gdv.engine.android;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class Font implements es.ucm.gdv.engine.Font {
    Typeface typeface_;
    Context context_;

    Font(Context context){
        context_ = context;
    }

    /**
     * Cambia la fuente del texto
     * @param filename Nombre de la fuente
     * @param size Tamaño
     * @param isBold Negrita
     */
    public void init(String filename, int size, boolean isBold){
        TextView t = new TextView(context_);
        typeface_ = Typeface.createFromAsset(context_.getAssets(), "fonts/" + filename);
        t.setTextSize(size);
    }

    public Typeface getMyFont(){
        return typeface_;
    }

}
