using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class Move : MonoBehaviour
    {
        [SerializeField]
        private SpriteRenderer playerSprite;

        public float timeMoving = 0.5f;

        private Vector2 firstTouch;
        private Vector2 finalPos;
        private Vector2 initPos;
        private float timeLapsed = 0;
        private bool isMoving = false;
        private Vector2 actualDir;
        private List<Vector2> moves;
        private Stack<Vector2> allMoves;
        private bool backwards;
        private Vector2Int fin_;

        private Color c_;

        private BoardManager bm;

        void Start()
        {
            moves = new List<Vector2>();
            finalPos = transform.localPosition;
            allMoves = new Stack<Vector2>();
            allMoves.Push(new Vector2(0, 0));
        }

        void Update()
        {
            //Para que no se mueva si esta pausado el juego
            if (GameManager._instance.isPaused())
                return;

            if (isMoving)
            {
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
                if (Input.anyKey)
                    HandleVector(AnyArrowPressed());

                //Handle screen input
                if (Input.touchCount > 0)
                {
                    //Gets the first touch
                    Touch touch = Input.GetTouch(0);

                    //When the touch begins
                    if (touch.phase == TouchPhase.Began)
                    {
                        firstTouch = touch.position;
                    }
                    //When the touch ends
                    else if (touch.phase == TouchPhase.Ended)
                        HandleVector(touch.position - firstTouch);
                }
            }

            //El Player esta al final del nivel
            Vector2Int p = new Vector2Int((int)transform.localPosition.x, (int)transform.localPosition.y);
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
                Debug.Log(bm.GetMap().GetNDirs(initPos));
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
        }

    }
}
