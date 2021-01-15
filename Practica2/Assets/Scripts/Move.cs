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

        private Color c_;
        private Map map_;

        void Start()
        {
            moves = new List<Vector2>();
            finalPos = transform.localPosition;
        }

        void Update()
        {
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

            if(map_.GetWall(initPos, w))
            {
                return;
            }


            finalPos = new Vector3(transform.localPosition.x + dir.x, transform.localPosition.y + dir.y);
            Invoke("PathAppearF", timeMoving / 2f);
            Invoke("PathAppearS", timeMoving / 1.2f);
        }

        void PathAppearF()
        {
            BoardManager.EnablePath(initPos, map_.GetWallByDir(actualDir), c_);
        }

        void PathAppearS()
        {
            BoardManager.EnablePath(finalPos, map_.GetWallByDir(-actualDir), c_);
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
            if (map_.GetWall(initPos, w))
            {
                return;
            }

            do
            {
                moves.Add(dir);
                initPos += dir;
                dir = map_.GetOneDir(initPos, dir);
                Debug.Log(map_.GetNDirs(initPos));
            } while (map_.GetNDirs(initPos) == 2);

            actualDir = moves[0];
            moves.RemoveAt(0);
            MoveCube(actualDir);
        }

        public void Init(Map map, Color c)
        {
            map_ = map;
            c_ = c;
            GetComponent<SpriteRenderer>().color = c_;
        }

    }
}
