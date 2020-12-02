package es.ucm.gdv.offtheline;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import es.ucm.gdv.engine.Engine;
import es.ucm.gdv.engine.Graphics;
import es.ucm.gdv.engine.Input;
import es.ucm.gdv.engine.Logic;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.*;


public class OffTheLineLogic implements Logic {
    Engine engine_;
    boolean menu = true;
    int w = 640; int h = 480; //Tamaño de la logica
    float s_ = 1; //Escala
    int actuallLevel_ = 0; //Nivel actual
    float eatDistance_ = 20; //Distancia a la que se consiguen coins
    String nameLevel_;
    JSONArray levels; //Niveles guardado en un jason
    Player player_;
    Vector<Path> paths_ = new Vector(10, 10);
    Vector<Coin> coins_ = new Vector(10, 10);
    Vector<Line> enemies_ = new Vector(10, 10);
    Vector<CrossCube> lifes_ = new Vector(10, 10); //Array con el numero de vidas

    boolean hardMode_ = false;
    int life_ = 10;
    int maxLife_;
    float timeToNextLevel = 1;
    boolean playerDead = false;
    float timeToReset = 2; //Tiempo que transcurre hasta que se reinicia el nivel

    StateMachine machine_;


    public OffTheLineLogic(Engine e, StateMachine machine, boolean hardMode){
        engine_ = e;
        machine_ = machine;


        if(hardMode)
            life_ = 5;

        maxLife_ = life_;
        actuallLevel_ = 0;
        int x = w/2 - 20;
        for (int i = 0; i < life_; i++){
            lifes_.add(new CrossCube(new Vector2D(x - i*20, h/2 - 20), 12));
        }

        JSONParser jsonParser = new JSONParser();
        InputStream in = engine_.openInputFile("levels.json");
        if(in == null)
            System.out.println("No se encuentra el archivo que se intenta abrir");
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
        paths_.clear();
        coins_.clear();
        enemies_.clear();


        JSONObject obj = (JSONObject) levels.get(actuallLevel_);
        nameLevel_ = (String)obj.get("name");

        /*************************** Paths ********************************/
        loadPaths(obj);

        /*************************** Items ********************************/
        loadItems(obj);

        /*************************** Enemies ********************************/
        loadEnemies(obj);

        int speed;
        if(hardMode_)
            speed = 400;
        else
            speed = 250;

        player_ = new Player(paths_.elementAt(0).getPunta1(), paths_.elementAt(0), speed);

    }

    void loadPaths(JSONObject obj){
        JSONArray paths = (JSONArray) obj.get("paths");
        int n = paths.size();
        int id = 0;
        for (int j = 0;j < n; j++) {
            Vector<Path> aux = new Vector(10, 10);

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
                Path p = new Path(p1, p2, id);
                id++;

                /*********************** Directions **********************/
                if(b){
                    v1 = (JSONObject) directions.get(i);

                    x1 = ((Number) v1.get("x")).floatValue();
                    y1 = ((Number) v1.get("y")).floatValue();
                    p1 = new Vector2D(x1, y1);
                    p.setNormal(p1);
                }

                if(i >= 1){
                    aux.elementAt(i-1).setNextPath(p);
                    p.setLastPath(aux.elementAt(i-1));
                }
                if(i == m - 1){
                    aux.elementAt(0).setLastPath(p);
                    p.setNextPath(aux.elementAt(0));
                }
                aux.add(p);
            }
            for(int k = 0; k < aux.size(); k++){
                paths_.add(aux.elementAt(k));
            }
            aux.clear();

        }
    }

    void loadItems(JSONObject obj){
        JSONArray items = (JSONArray) obj.get("items");
        int n = items.size();
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
    }

    void loadEnemies(JSONObject obj){
        if(obj.containsKey("enemies")){
            JSONArray enemies = (JSONArray) obj.get("enemies");
            int n = enemies.size();
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
        if(wAux < hAux)
            s_ = wAux;
        else
            s_ = hAux;
    }

    public void handleInput(){
        List<Input.TouchEvent> l = engine_.getInput().getTouchEvents();
        if(l.size()!=0){
            System.out.println("HandleInput");
            for (Input.TouchEvent e: l) {
                switch (e.type){
                    case 0:
                        player_.jump();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void update(float deltaTime){

        player_.update(deltaTime);

        /** Colision del jugador con paths si esta saltando **/
        if(player_.saltando_){
            pathCollision();
        }

        /** Update y colision de los coins **/
        for (int i = 0; i < coins_.size(); i++) {
            coins_.elementAt(i).update(deltaTime);
            if (player_.saltando_)
                coinCollision(i);
            if(coinKilled(i))
                i--;
        }

        if(coins_.size() <= 0 && !playerDead)
            timeToNextLevel -= deltaTime;


        for (int i = 0; i < enemies_.size(); i++) {
            Line e = enemies_.elementAt(i);
            e.update(deltaTime);
            Vector2D aux = Utils.segmentCollition(player_.getPos(), player_.getLastPos_(), e.p1_, e.p2_);
            if(aux  != null && !playerDead) {
                player_.kill();
                playerDead = true;
            }
        }

        if(playerDead){
            timeToReset -= deltaTime;
            if(timeToReset <= 0){
                timeToReset = 2;
                playerDied();
            }
        }

        if(timeToNextLevel <= 0){
            actuallLevel_ = (actuallLevel_ + 1) % 20;
            loadLevel();
            timeToNextLevel = 1;
        }

        if(!insideBounds()){
            playerDied();
        }
    }

    void pathCollision(){
        int j = 0;
        boolean found = false;
        Vector2D c;
        float distance = Float.MAX_VALUE;
        while (j < paths_.size() /*&& !found*/) {
            Path p = paths_.elementAt(j);
            if(p.getId() == player_.getActualPath().getId()) {
                j++;
                if(j != paths_.size()-1)
                    continue;
                p = paths_.elementAt(j);
            }
            Vector2D corte = Utils.segmentCollition(p.getPunta1(), p.getPunta2(), player_.getPos(), player_.getLastPos_());
            if(corte != null){
                player_.land(corte, p);
                float d = Utils.pointDistance(corte, player_.getPos());
                if(d < distance) {
                    distance = d;
                    c = new Vector2D(corte);
                }
                //found = true;
            }
            j++;

        }
    }

    void coinCollision(int i){
        float d = Utils.pointDistance(coins_.elementAt(i).getRealPos(), player_.getPos());
        if(d < eatDistance_){
            coins_.elementAt(i).kill(0.5f, 80f);
        }
    }

    boolean coinKilled(int i){
        if(coins_.elementAt(i).timeDying_ < 0) {
            coins_.remove(i);
            return true;
        }
        return false;
    }

    public void render(){
        Graphics g = engine_.getGraphics();
        /*g.translate(g.getWidth()/2.0f, g.getHeight()/2.0f);
        g.scale(s_);*/

        g.setColor(255, 255, 0, 255);
        for (int i = 0; i < coins_.size(); i++) {
            coins_.elementAt(i).render(g);
        }
        g.setColor(255, 255, 255, 255);
        for (int i = 0; i < paths_.size(); i++) {
            paths_.elementAt(i).render(g);
        }
        g.setColor(255, 0, 0, 255);
        for (int i = 0; i < enemies_.size(); i++) {
            enemies_.elementAt(i).render(g);
        }
        for (int i = 0; i < lifes_.size(); i++) {
            lifes_.elementAt(i).render(g);
        }
        g.setColor(0, 136, 255, 255); //Player
        player_.render(g);

    }

    boolean insideBounds(){
        return player_.getPosX() <= w && player_.getPosX() >= -w &&
                player_.getPosY() <= h && player_.getPosY() >= -w;
    }

    void playerDied(){
        lifes_.elementAt(maxLife_ - life_).startRenderCross();
        life_--;
        if(life_ <= 0){
            menu = true;
            //loading = false;
        }
        playerDead = false;
        loadLevel();
    }

    void updateMenu(float deltaTime){

    }

    void renderMenu(){

    }

    void handleInputMenu(){

    }

    Vector2D transformCoord(float x, float y){
        Vector2D aux =  new Vector2D((x - engine_.getGraphics().getWidth()/2.0f)*s_, -(y - engine_.getGraphics().getHeight()/2.0f)*s_);
        /*if(aux.x_ < -w/2.0f)
            aux.x_ = -w/2.0f;
        if(aux.x_ > w/2.0f)
            aux.x_ = w/2.0f;
        if(aux.y_ < -h/2.0f)
            aux.y_ = -h/2.0f;
        if(aux.x_ > h/2.0f)
            aux.y_ = h/2.0f;*/
        return aux;
    }

    void loadNewGame(){
        lifes_.clear();
        maxLife_ = life_;
        actuallLevel_ = 0;
        int x = w/2 - 20;
        for (int i = 0; i < life_; i++){
            lifes_.add(new CrossCube(new Vector2D(x - i*20, h/2 - 20), 12));
        }
        loadLevel();
    }
}