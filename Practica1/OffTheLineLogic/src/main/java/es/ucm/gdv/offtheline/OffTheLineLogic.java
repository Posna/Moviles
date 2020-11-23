package es.ucm.gdv.offtheline;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Graphics;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;


public class OffTheLineLogic {
    Engine engine_;

    int w = 640; int h = 480;
    float s_ = 1;
    int actuallLevel_ = 0;
    String nameLevel_;
    JSONArray levels;
    Coin prueba = new Coin(new Vector2D(-100, 100), 70, 100, 45);
    Cube prueba3 = new Cube(new Vector2D(-100, 100), 100);
    Line prueba2 = new Line(new Vector2D(0, 140), 90.0f, 280);
    Vector<GameObject> paths_ = new Vector(10, 10);
    Vector<GameObject> coins_ = new Vector(10, 10);
    Vector<GameObject> enemies_ = new Vector(10, 10);

    float i = 0;

    public OffTheLineLogic(Engine e){
        engine_ = e;
        prueba3.setAngularVel(100);
        paths_.addElement(prueba);
        paths_.addElement(prueba3);

        JSONParser jsonParser = new JSONParser();
        InputStream in = engine_.openInputFile("levels.json");
        if(in == null)
            System.out.println("Va a petar ^^");
        try{
            levels = (JSONArray)jsonParser.parse(new InputStreamReader(in));
            in.close();
        //
        }catch (UnsupportedEncodingException er){
            er.printStackTrace();
        }catch(IOException er){
            er.printStackTrace();
        }catch(ParseException er){
            er.printStackTrace();
        }

        //loadLevel();

    }

    public void loadLevel(){
        JSONObject obj = (JSONObject) levels.get(actuallLevel_);
        nameLevel_ = (String)obj.get("name");


        JSONArray paths = (JSONArray) obj.get("paths");
        int n = paths.size();
        for (int j = 0;j < n; j++) {
            JSONArray vertices = (JSONArray) ((JSONObject)paths.get(j)).get("vertices");
            int m = vertices.size();
            System.out.println(m);
            for (int i = 0; i < m; i++) {
                Vector2D p1;
                Vector2D p2;
                JSONObject v1 = (JSONObject) vertices.get(i);
                JSONObject v2 = (JSONObject) vertices.get((i + 1) % n);
                float x = (Float) v1.get("x");
                p1 = new Vector2D(x, (Float) v1.get("y"));
                p2 = new Vector2D((Float) v2.get("x"), (Float) v2.get("y"));
                paths_.add(new Line(p1, p2));
            }
        }

        JSONArray items = (JSONArray) obj.get("items");
        n = items.size();
        for (int i = 0; i < n; i++){
            Vector2D p1;
            JSONObject v1 = (JSONObject) items.get(i);
            p1 = new Vector2D((Integer)v1.get("x"), (Integer)v1.get("y"));
            float rad = 0; float speedEx = 0; float extAng = 0;
            if(v1.containsValue("radius"))
                rad = (Float) v1.get("radius");
            if(v1.containsKey("speed"))
                speedEx = (Float) v1.get("speed");
            if(v1.containsKey("angle"))
                extAng = (Float) v1.get("angle");
            coins_.add(new Coin(p1, rad, speedEx, extAng));
        }

        if(obj.containsValue("enemies")){
            JSONArray enemies = (JSONArray) obj.get("enemies");
            n = enemies.size();
            for (int i = 0; i < n; i++){
                Vector2D p1;
                JSONObject v1 = (JSONObject) enemies.get(i);
                p1 = new Vector2D((Integer)v1.get("x"), (Integer)v1.get("y"));
                float l = (Float)v1.get("lenght");
                float ang = (Float) v1.get("angle");
                enemies_.add(new Line(p1, l, ang));
            }
        }

    }

    public void setLogicalScale(int width, int height){
        float wAux = (float)width / (float)w;
        float hAux = (float)height / (float)h;
        System.out.println(wAux);
        if(wAux < hAux)
            s_ = wAux;
        else
            s_ = hAux;
    }

    public void update(float deltaTime){
        for (int i = 0; i < paths_.size(); i++) {
            paths_.elementAt(i).update(deltaTime);
        }

        for (int i = 0; i < coins_.size(); i++) {
            coins_.elementAt(i).update(deltaTime);
        }

        for (int i = 0; i < enemies_.size(); i++) {
            enemies_.elementAt(i).update(deltaTime);
        }
        //prueba.update(deltaTime);
    }

    public void render(){
        Graphics g = engine_.getGraphics();
        g.translate(g.getWidth()/2.0f, g.getHeight()/2.0f);
        g.rotate(180);
        g.scale(s_);
        //g.save();

        g.clear(0, 0, 0);

        g.setColor(255, 0, 0, 255);
        for (int i = 0; i < paths_.size(); i++) {
            paths_.elementAt(i).render(g);
        }
        for (int i = 0; i < coins_.size(); i++) {
            coins_.elementAt(i).render(g);
        }
        for (int i = 0; i < enemies_.size(); i++) {
            enemies_.elementAt(i).render(g);
        }

    }
}