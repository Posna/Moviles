package es.ucm.gdv.engine;

public abstract class AbstractGraphics implements Graphics {

    public float s_, w_, h_;

    /**
     * Añade las dimensiones logicas
     * @param w ancho logico
     * @param h alto logico
     */
    protected void initLogicSizes(float w, float h){
        w_ = w;
        h_ = h;
    }

    /**
     * Calcula y asigna la escala oportuna
     */
    protected void calculateScale(){
        float wAux = (float)getWidth() / w_;
        float hAux = (float)getHeight() / h_;
        if(wAux < hAux)
            s_ = wAux;
        else
            s_ = hAux;
    }

    /**
     * Tranforma una posicion X
     */
    public float transformXToCenter(float x){
        return (x - getWidth()/2) * (1.0f/s_);
    }

    /**
     * Tranforma una posicion Y
     */
    public float transformYToCenter(float y){
        return (getHeight()/2 - y) * (1.0f/s_);
    }

    /**
     * Transfomra y escala para podisiconar todo en su sitio
     */
    protected void preRender(){
        translate(getWidth()/2.0f, getHeight()/2.0f);
        scale(s_);
    }

}
