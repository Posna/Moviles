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
    int w = 640; int h = 480; //Tamaño de la logica
    float s_ = 1; //Escala
    int actuallLevel_ = 0; //Nivel actual
    float eatDistance_ = 20; //Distancia a la que se consiguen coins
    JSONArray levels; //Niveles guardado en un jason

    /** Objetos **/
    Player player_;
    Vector<Path> paths_ = new Vector(10, 10);
    Vector<Coin> coins_ = new Vector(10, 10);
    Vector<Line> enemies_ = new Vector(10, 10);
    Vector<CrossCube> lifes_ = new Vector(10, 10); //Array con el numero de vidas
    Texto level;
    EndGame endGame_; //Pantalla final de partida (tanto perder como ganar)

    boolean hardMode_;
    int life_ = 10;
    int maxLife_;
    float timeToNextLevel = 1; //Tiempo hasta el siguiente nivel
    boolean playerDead = false;
    float timeToReset = 2; //Tiempo que transcurre hasta que se reinicia el nivel
    boolean gameOver = false;


    StateMachine machine_;


    public OffTheLineLogic(Engine e, StateMachine machine, boolean hardMode){
        engine_ = e;
        machine_ = machine;
        hardMode_ = hardMode;

        if(hardMode)
            life_ = 5;

        maxLife_ = life_;
        actuallLevel_ = 0;

        /** Posicionamiento de vidas **/
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

        /** Creacion de Nivel **/
        loadLevel();
    }

    public void loadLevel(){
        /** Se ha llegado al nivel maximo **/
        if(actuallLevel_ >= 20){
            endGame_ = new EndGame(true, actuallLevel_ +1, hardMode_);
            gameOver = true;
            return;
        }

        paths_.clear();
        coins_.clear();
        enemies_.clear();


        JSONObject obj = (JSONObject) levels.get(actuallLevel_);
        String nameLevel = (String)obj.get("name");
        level = new Texto(new Vector2D(-300, 210), "LEVEL " + (actuallLevel_ + 1) + " - " + nameLevel, "BungeeHairline-Regular.ttf", 20, true);
        level.setColor(255, 255, 255);

        /*************************** Paths ********************************/
        loadPaths(obj);

        /*************************** Items ********************************/
        loadItems(obj);

        /*************************** Enemies ********************************/
        loadEnemies(obj);

        /*************************** Player ********************************/
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

    public void handleInput(){
        List<Input.TouchEvent> l = engine_.getInput().getTouchEvents();
        if(l.size()!=0){
            for (Input.TouchEvent e: l) {
                switch (e.type){
                    case 0: //Click del raton o pulsacion con el dedo
                        if(!gameOver)
                            player_.jump();
                        else
                            machine_.popState();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void update(float deltaTime){
        //Si se ha perdido o ganado se para de actualizar el juego
        if(gameOver)
            return;

        /** Update del jugador **/
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

        /** Cuenta atras para siguiente nivel **/
        if(coins_.size() <= 0 && !playerDead)
            timeToNextLevel -= deltaTime;

        /** Update y colision con los enemigos **/
        for (int i = 0; i < enemies_.size(); i++) {
            Line e = enemies_.elementAt(i);
            e.update(deltaTime);
            Vector2D aux = Utils.segmentCollition(player_.getPos(), player_.getLastPos_(), e.p1_, e.p2_);
            if(aux  != null && !playerDead) {
                player_.kill();
                playerDead = true;
            }
        }

        /** Cuenta atras para reset del nivel **/
        if(playerDead && !gameOver){
            timeToReset -= deltaTime;
            if(timeToReset <= 0){
                timeToReset = 2;
                playerDied();
            }
        }

        /** Muerte por salir de la pantalla **/
        if(!insideBounds() && !gameOver){
            playerDied();
        }

        /** Cuenta atras para pasar de nivel **/
        if(timeToNextLevel <= 0){
            actuallLevel_ = actuallLevel_ + 1;
            timeToNextLevel = 1;
            loadLevel();
        }

    }

    void pathCollision(){
        int j = 0;
        while (j < paths_.size()) {
            Path p = paths_.elementAt(j);

            //Se comprueba la colision con todos los paths menos desde el que se salta
            if(p.getId() == player_.getActualPath().getId()) {
                j++;
                if(j != paths_.size()-1)
                    continue;
                p = paths_.elementAt(j);
            }
            Vector2D corte = Utils.segmentCollition(p.getPunta1(), p.getPunta2(), player_.getPos(), player_.getLastPos_());
            if(corte != null){
                player_.land(corte, p);
            }
            j++;
        }
    }

    /**
     * Collision con un coin concreto
     * @param i Coin numero i dentro del vector de coins
     */
    void coinCollision(int i){
        float d = Utils.pointDistance(coins_.elementAt(i).getRealPos(), player_.getPos());
        if(d < eatDistance_){
            coins_.elementAt(i).kill(0.5f, 80f);
        }
    }

    /**
     * Mata a un coin concreto
     * @param i Coin numero i dentro del vector de coins
     * @return si el tiempo de muerte es mayor que 0 se devuelve false, si no true
     */
    boolean coinKilled(int i){
        if(coins_.elementAt(i).timeDying_ < 0) {
            coins_.remove(i);
            return true;
        }
        return false;
    }

    public void render(){
        Graphics g = engine_.getGraphics();

        /** Render de los coins **/
        g.setColor(255, 255, 0, 255);
        for (int i = 0; i < coins_.size(); i++) {
            coins_.elementAt(i).render(g);
        }

        /** Render de los paths **/
        g.setColor(255, 255, 255, 255);
        for (int i = 0; i < paths_.size(); i++) {
            paths_.elementAt(i).render(g);
        }

        /** Render de los enemigos **/
        g.setColor(255, 0, 0, 255);
        for (int i = 0; i < enemies_.size(); i++) {
            enemies_.elementAt(i).render(g);
        }

        /** Render de las vidas **/
        for (int i = 0; i < lifes_.size(); i++) {
            lifes_.elementAt(i).render(g);
        }

        /** Render del jugador **/
        g.setColor(0, 136, 255, 255); //Player
        player_.render(g);

        /** Render del nombre y numero del nivel **/
        level.render(g);

        /** Render de la pantalla final de partida **/
        if(gameOver)
            endGame_.render(g);

    }

    /**
     * Comprueba que el jugador este dentro de los bordes logicos
     * @return true si esta dentro
     */
    boolean insideBounds(){
        return player_.getPosX() <= w && player_.getPosX() >= -w &&
                player_.getPosY() <= h && player_.getPosY() >= -w;
    }

    /**
     * Se llama cuando el jugador muere. Disminuye la vidas y vuelve a cargar el
     * nivel si las vidas son mayores a 0
     */
    void playerDied(){
        lifes_.elementAt(maxLife_ - life_).startRenderCross();
        life_--;
        if(life_ <= 0){
            gameOver = true;
            endGame_ = new EndGame(false, actuallLevel_ +1, hardMode_);
            return;
        }
        playerDead = false;
        loadLevel();
    }

}