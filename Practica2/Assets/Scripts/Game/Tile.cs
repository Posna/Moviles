using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{

    public enum Dirs { Up, Down, Left, Right, Neutral }

    public class Tile : MonoBehaviour
    {
        [Tooltip("Indica que la celda tiene hielo")]
        public SpriteRenderer iceFloor;

        [Tooltip("Indica que la celda es la meta")]
        public SpriteRenderer finFloor;

        [Tooltip("Muros")]
        public SpriteRenderer[] walls;

        [Tooltip("Caminos que puede tomar el jugador")]
        public SpriteRenderer[] paths;

        private int[] path = { 0, 0, 0, 0 }; //Contador de pasadar del player
        private bool[] hints = { false, false, false, false }; //Direccion de las pistas si es que tiene
        private bool isIce = false; //Si el Tile tiene hielo

        public void EnableIce()
        {
            iceFloor.gameObject.SetActive(true);
            isIce = true;
        }

        public bool IsIce()
        {
            return isIce;
        }

        public void EnableFin(Color c_)
        {
            finFloor.gameObject.SetActive(true);
            finFloor.color = c_;
        }


        public void EnableWall(Dirs w)
        {
            walls[(int)w].gameObject.SetActive(true);
        }

        public void EnablePath(Dirs d, Color c)
        {
            path[(int)d]++;
            if (path[(int)d] == 1)
            {
                paths[(int)d].gameObject.SetActive(true);
                paths[(int)d].color = c;
                paths[(int)d].sortingOrder = 3;
            }
        }

        //Habilita la pista desde el boardManager
        public void EnableHint(Vector2 from, Vector2 to)
        {
            Dirs fromH = Dirs.Neutral, toH = Dirs.Neutral; // Direcciones de las pistas

            // Valores que no se aceptan
            if (from != -Vector2.one)
                fromH = Utility.GetWallByDir(from - (Vector2)transform.localPosition);
            if (to != -Vector2.one)
                toH = Utility.GetWallByDir(to - (Vector2)transform.localPosition);

            if (fromH != Dirs.Neutral)
            {

                hints[(int)fromH] = true;
                if (!paths[(int)fromH].gameObject.activeSelf)
                {
                    paths[(int)fromH].gameObject.SetActive(true);
                    paths[(int)fromH].color = Color.yellow;
                    paths[(int)fromH].sortingOrder = 2;
                }
            }

            if (toH != Dirs.Neutral)
            {
                hints[(int)toH] = true;
                if (!paths[(int)toH].gameObject.activeSelf)
                {
                    paths[(int)toH].gameObject.SetActive(true);
                    paths[(int)toH].color = Color.yellow;
                    paths[(int)toH].sortingOrder = 2;
                }
            }
        }

        public void DisablePath(Dirs d, Color c)
        {
            path[(int)d]--;
            if (path[(int)d] == 0)
            {
                paths[(int)d].gameObject.SetActive(false);
            }
            if (hints[(int)d])
            {
                paths[(int)d].gameObject.SetActive(true);
                paths[(int)d].color = Color.yellow;
            }

        }

        private void Start()
        {
#if UNITY_EDITOR
            if (iceFloor = null)
            {
                Debug.LogError("No hay sprite");
                gameObject.SetActive(false);
                return;
            }
#endif
        }

    }
}
