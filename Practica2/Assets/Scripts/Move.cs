using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class Move : MonoBehaviour
    {
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

        void Start()
        {
            moves = new List<Vector2>();
            finalPos = transform.localPosition;
            allMoves = new Stack<Vector2>();
            allMoves.Push(new Vector2(0, 0));
        }

        void Update()
        {
            if (GameManager._instance.isPaused())
                return;

            if (isMoving)
            {

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
                        MoveCube(actualDir);
                        timeLapsed = 0;
                    }
                }

                transform.localPosition = Vector3.Lerp(initPos, finalPos, timeLapsed / timeMoving);
            }
            else
            {
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
                    {
                        if (touch.position == firstTouch)
                            return;
                        collectMoves(touch.position - firstTouch);
                        isMoving = true;
                        timeLapsed = 0;
                    }
                }
            }
            Vector2Int p = new Vector2Int((int)transform.localPosition.x, (int)transform.localPosition.y);
            if (transform.localPosition.x - fin_.x == 0 && transform.localPosition.y - fin_.y == 0)
                GameManager._instance.nextLevel();
        }

        void MoveCube(Vector2 dir)
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

            if(Map.GetMap().GetWall(initPos, w))
            {
                return;
            }
            

            backwards = allMoves.Peek().x == -dir.x && allMoves.Peek().y == -dir.y;

            if (!backwards)
                allMoves.Push(dir);
            else
                allMoves.Pop();

            finalPos = new Vector3(transform.localPosition.x + dir.x, transform.localPosition.y + dir.y);
            Invoke("PathAppearF", timeMoving / 3f);
            Invoke("PathAppearS", timeMoving / 1.2f);
        }

        void PathAppearF()
        {
            BoardManager.EnablePath(initPos, Map.GetWallByDir(actualDir), c_, !backwards);
        }

        void PathAppearS()
        {
            BoardManager.EnablePath(finalPos, Map.GetWallByDir(-actualDir), c_, !backwards);
            
        }

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
            if (Map.GetMap().GetWall(initPos, w))
            {
                return;
            }

            do
            {
                moves.Add(dir);
                initPos += dir;
                if(!BoardManager.IsIce(initPos))
                    dir = Map.GetMap().GetOneDir(initPos, dir);
                Debug.Log(Map.GetMap().GetNDirs(initPos));
            } while ((!BoardManager.IsIce(initPos) && Map.GetMap().GetNDirs(initPos) == 2) || 
                (BoardManager.IsIce(initPos) && !Map.GetMap().GetWall(initPos, Map.GetWallByDir(dir))));

            actualDir = moves[0];
            moves.RemoveAt(0);
            MoveCube(actualDir);
            
        }

        public void Init(Color c, Vector2Int fin)
        {
            fin_ = fin;
            c_ = c;
            GetComponent<SpriteRenderer>().color = c_;
        }

    }
}
