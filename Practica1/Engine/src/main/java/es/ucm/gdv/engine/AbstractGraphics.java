package es.ucm.gdv.engine;

public abstract class AbstractGraphics implements Graphics {

    float s_, w_, h_;

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

    protected void preRender(){
        translate(getWidth()/2.0f, getHeight()/2.0f);
        scale(s_);
    }

}
