package es.ucm.gdv.engine;

public abstract class AbstractGraphics implements Graphics {

    public float s_, w_, h_;

    protected void initLogicSizes(float w, float h){
        w_ = w;
        h_ = h;
    }

    protected void calculateScale(){
        float wAux = (float)getWidth() / w_;
        float hAux = (float)getHeight() / h_;
        if(wAux < hAux)
            s_ = wAux;
        else
            s_ = hAux;
    }

    public float transformXToCenter(float x){
        return (x - getWidth()/2) * (1.0f/s_);
    }

    public float transformYToCenter(float y){
        return (getHeight()/2 - y) * (1.0f/s_);
    }

    protected void preRender(){
        translate(getWidth()/2.0f, getHeight()/2.0f);
        scale(s_);
    }

}
