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
    int actuallLevel_ = 15;
    String nameLevel_;
    JSONArray levels;
    Cube prueba3 = new Cube(new Vector2D(0, 0), 12);
    Vector<GameObject> paths_ = new Vector(10, 10);
    Vector<GameObject> coins_ = new Vector(10, 10);
    Vector<GameObject> enemies_ = new Vector(10, 10);

    float i = 0;

    public OffTheLineLogic(Engine e){
        engine_ = e;
        //prueba3.setAngularVel(100);
        //paths_.addElement(prueba);
        //paths_.addElement(prueba3);

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

        loadLevel();

    }

    public void loadLevel(){
        JSONObject obj = (JSONObject) levels.get(actuallLevel_);
        nameLevel_ = (String)obj.get("name");


        JSONArray paths = (JSONArray) obj.get("paths");
        int n = paths.size();
        for (int j = 0;j < n; j++) {
            JSONArray vertices = (JSONArray) ((JSONObject)paths.get(j)).get("vertices");
            JSONArray directions = (JSONArray) ((JSONObject)paths.get(j)).get("directions");
            boolean b = ((JSONObject) paths.get(j)).containsKey("directions");
            int m = vertices.size();
            System.out.println(m);
            for (int i = 0; i < m; i++) {
                /*********************** Vertices ***************************/
                Vector2D p1;
                Vector2D p2;
                JSONObject v1 = (JSONObject) vertices.get(i);
                JSONObject v2 = (JSONObject) vertices.get((i + 1) % m);
                float x1 = ((Number) v1.get("x")).floatValue();
                float y1 = ((Number) v1.get("y")).floatValue();
                p1 = new Vector2D(x1, y1);
                float x2 = ((Number) v2.get("x")).floatValue();
                float y2 = ((Number) v2.get("y")).floatValue();
                p2 = new Vector2D(x2, y2);
                Path p = new Path(p1, p2);

                /*********************** Directions **********************/
                if(b){
                    v1 = (JSONObject) directions.get(i);

                    x1 = ((Number) v1.get("x")).floatValue();
                    y1 = ((Number) v1.get("y")).floatValue();
                    p1 = new Vector2D(x1, y1);
                    p.setNormal(p1);
                }
                paths_.add(p);
            }
        }

        /*************************** Items ********************************/
        JSONArray items = (JSONArray) obj.get("items");
        n = items.size();
        for (int i = 0; i < n; i++){
            Vector2D p1;
            JSONObject v1 = (JSONObject) items.get(i);
            p1 = new Vector2D(((Number)v1.get("x")).floatValue(), ((Number)v1.get("y")).floatValue());
            float rad = 0; float speedEx = 0; float extAng = 0;
            /*** Radius ***/
            if(v1.containsKey("radius")) {
                System.out.println("radio si");
                rad = ((Number) v1.get("radius")).floatValue();
            }
            /*** Speed ***/
            if(v1.containsKey("speed"))
                speedEx = ((Number) v1.get("speed")).floatValue();
            /*** Angle ***/
            if(v1.containsKey("angle"))
                extAng = ((Number) v1.get("angle")).floatValue();
            coins_.add(new Coin(p1, rad, speedEx, extAng));
        }

        if(obj.containsKey("enemies")){
            JSONArray enemies = (JSONArray) obj.get("enemies");
            n = enemies.size();
            for (int i = 0; i < n; i++){
                Vector2D p1;
                JSONObject v1 = (JSONObject) enemies.get(i);
                p1 = new Vector2D(((Number)v1.get("x")).floatValue(), ((Number)v1.get("y")).floatValue());
                float l = ((Number)v1.get("length")).floatValue();
                float ang = ((Number) v1.get("angle")).floatValue();
                float speed = 0;
                if(v1.containsKey("speed")) {
                    speed = ((Number) v1.get("speed")).floatValue();
                }

                Line line = new Line(p1, ang, l, speed);
                if(v1.containsKey("offset")) {
                    JSONObject offset = (JSONObject) v1.get("offset");
                    Vector2D ofs = new Vector2D(((Number)offset.get("x")).floatValue(), ((Number)offset.get("y")).floatValue());
                    line.setOffSet(ofs, ((Number) v1.get("time1")).floatValue(), ((Number) v1.get("time2")).floatValue());
                }
                enemies_.add(line);
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
        g.scale(s_);
        //g.save();

        g.clear(0, 0, 0);

        g.setColor(255, 255, 255, 255);
        for (int i = 0; i < paths_.size(); i++) {
            paths_.elementAt(i).render(g);
        }
        g.setColor(255, 255, 0, 255);
        //g.setColor(0, 136, 255, 255); Player
        for (int i = 0; i < coins_.size(); i++) {
            coins_.elementAt(i).render(g);
        }
        g.setColor(255, 0, 0, 255);
        for (int i = 0; i < enemies_.size(); i++) {
            enemies_.elementAt(i).render(g);
        }

    }
}