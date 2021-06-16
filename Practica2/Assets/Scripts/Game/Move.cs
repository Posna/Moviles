using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class Move : MonoBehaviour
    {
        [SerializeField]
        private SpriteRenderer playerSprite = null;

        [Tooltip("Tiempo que tarda el jugador en desplazarse una casilla")]
        public float timeMoving = 0.5f;

        [Tooltip("Flechas mostradas para indicar las direcciones posibles. Arriba, Abajo, Izquierda, Derecha")]
        public SpriteRenderer[] flechas = new SpriteRenderer[4];

        private Vector2 firstTouch = new Vector2();
        private Vector2 finalPos = new Vector2();
        private Vector2 initPos = new Vector2();
        private float timeLapsed = 0;
        private bool isMoving = false;
        private Vector2 actualDir = new Vector2();
        private List<Vector2> moves = new List<Vector2>();
        private Stack<Vector2> allMoves = new Stack<Vector2>();
        private bool backwards = false;
        private Vector2Int fin_ = new Vector2Int();        

        private Color c_;

        private BoardManager bm;

        void Start()
        {
            moves = new List<Vector2>();
            finalPos = transform.localPosition;
            allMoves = new Stack<Vector2>();
            allMoves.Push(new Vector2(0, 0));

            Input.simulateMouseWithTouches = true;
        }

        void Update()
        {
            //Para que no se mueva si esta pausado el juego
            if (GameManager._instance.isPaused())
                return;

            if (isMoving)
            {
                ResetDirs();
                //Realiza movimientos hasta que no quedan mas en moves
                timeLapsed += Time.deltaTime;
                if (timeLapsed >= timeMoving)
                {
                    transform.localPosition = finalPos;
                    if (moves.Count == 0)
                    {
                        isMoving = false;
                    }
                    else
                    {
                        actualDir = moves[0];
                        moves.RemoveAt(0);
                        MovePlayer(actualDir);
                        timeLapsed = 0;
                    }
                }

                transform.localPosition = Vector3.Lerp(initPos, finalPos, timeLapsed / timeMoving);
            }
            else
            {
                EnablePosibleDirs();
                //Maneja el input del teclado
                if (Input.anyKey)
                    HandleVector(AnyArrowPressed());

                //Maneja el input de la pantalla
                if (Input.touchCount > 0)
                    HandleVector(AnyTouch());

                //Maneja el input del raton
                HandleVector(AnyMouseClick());
            }

            //El Player esta al final del nivel
            if (transform.localPosition.x - fin_.x == 0 && transform.localPosition.y - fin_.y == 0)
                GameManager._instance.FinishLevel();
        }
        
        // Maneja el vector de entrada ya sea de teclado o de pantalla
        void HandleVector(Vector2 m)
        {
            // Comprobacion de que el vector que recibimos no es nulo
            if (m == Vector2.zero)
                return;

            collectMoves(m);
            isMoving = true;
            timeLapsed = 0;
        }

        // Comprueba si se ha apretado alguna flecha y devuelve el vector2 correspondiente
        Vector2 AnyArrowPressed()
        {
            if (Input.GetKeyDown(KeyCode.DownArrow)) return new Vector2(0, -1);
            if (Input.GetKeyDown(KeyCode.UpArrow)) return new Vector2(0, 1);
            if (Input.GetKeyDown(KeyCode.LeftArrow)) return new Vector2(-1, 0);
            if (Input.GetKeyDown(KeyCode.RightArrow)) return new Vector2(1, 0);

            return new Vector2(0, 0);
        }

        Vector2 AnyMouseClick()
        {
            //Gets the first touch
            if (Input.GetMouseButtonDown(0))
                firstTouch = Input.mousePosition;

            //Gets the first touch
            if (Input.GetMouseButtonUp(0))
                return (Vector2)Input.mousePosition - firstTouch;

            return Vector2.zero;
        }

        Vector2 AnyTouch()
        {
            //Gets the first touch
            Touch touch = Input.GetTouch(0);

            //When the touch begins
            if (touch.phase == TouchPhase.Began)
                firstTouch = touch.position;            
            //When the touch ends
            else if (touch.phase == TouchPhase.Ended)
                return touch.position - firstTouch;

            return Vector2.zero;
        }

        void EnablePosibleDirs()
        {
            Dirs[] dirs = { Dirs.Up, Dirs.Down, Dirs.Left, Dirs.Right };

            for (int i = 0; i < dirs.Length; i++)
            {
                flechas[i].enabled = !bm.GetMap().GetWall(transform.localPosition, dirs[i]);
            }
        }

        void ResetDirs()
        {
            for (int i = 0; i < flechas.Length; i++)
            {
                flechas[i].enabled = false;
            }
        }

        //Hace un movimiento dada una direccion
        void MovePlayer(Vector2 dir)
        {
            dir.Normalize();
            float X = Mathf.Abs(dir.x);
            float Y = Mathf.Abs(dir.y);
            Dirs w;

            //Change the dir to unit vectors [(0,1), (-1,0), (1,0), (0,-1)]
            if (X > Y)
            {
                dir.Set((dir.x / X), 0);
                if (dir.x < 0) w = Dirs.Left;
                else w = Dirs.Right;
            }
            else
            {
                dir.Set(0, (dir.y / Y));
                if (dir.y < 0) w = Dirs.Down;
                else w = Dirs.Up;
            }

            //Calculate the final position and rotation
            initPos = transform.localPosition;

            if(bm.GetMap().GetWall(initPos, w))
            {
                return;
            }
            

            backwards = allMoves.Peek().x == -dir.x && allMoves.Peek().y == -dir.y;

            if (!backwards)
                allMoves.Push(dir);
            else
                allMoves.Pop();

            finalPos = new Vector3(transform.localPosition.x + dir.x, transform.localPosition.y + dir.y);

            //Hace aparecer el camino segun va pasando
            Invoke("PathAppearF", timeMoving / 3f);
            Invoke("PathAppearS", timeMoving / 1.2f);
        }

        void PathAppearF()
        {
            bm.EnablePath(initPos, Utility.GetWallByDir(actualDir), c_, !backwards);
        }

        void PathAppearS()
        {
            bm.EnablePath(finalPos, Utility.GetWallByDir(-actualDir), c_, !backwards);

        }

        //hace todos los movimientos hasta que encuentra una interseccion y los va añadiendo a la lista moves
        void collectMoves(Vector2 dir)
        {
            dir.Normalize();
            float X = Mathf.Abs(dir.x);
            float Y = Mathf.Abs(dir.y);
            Dirs w;

            //Change the dir to unit vectors [(0,1), (-1,0), (1,0), (0,-1)]
            if (X > Y)
            {
                dir.Set((dir.x / X), 0);
                if (dir.x < 0) w = Dirs.Left;
                else w = Dirs.Right;
            }
            else
            {
                dir.Set(0, (dir.y / Y));
                if (dir.y < 0) w = Dirs.Down;
                else w = Dirs.Up;
            }

            initPos = transform.localPosition;
            if (bm.GetMap().GetWall(initPos, w))
            {
                return;
            }

            do
            {
                moves.Add(dir);
                initPos += dir;
                if(!bm.IsIce(initPos))
                    dir = bm.GetMap().GetOneDir(initPos, dir);
            } while ((!bm.IsIce(initPos) && bm.GetMap().GetNDirs(initPos) == 2) || 
                (bm.IsIce(initPos) && !bm.GetMap().GetWall(initPos, Utility.GetWallByDir(dir))));

            actualDir = moves[0];
            moves.RemoveAt(0);
            MovePlayer(actualDir);
            
        }

        //Cambia el color del personaje y asigna el fin
        public void Init(Color c, Vector2Int fin, BoardManager bm)
        {
            fin_ = fin;
            c_ = c;
            playerSprite.color = c_;
            this.bm = bm;

            //Cambia el color de las flechas de direccion
            foreach (SpriteRenderer f in flechas)
            {
                f.color = c;
            }
        }

    }
}
