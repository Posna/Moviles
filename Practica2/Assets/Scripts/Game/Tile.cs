using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore {

    public enum Dirs{ Up, Down, Left, Right, Neutral }

    public class Tile : MonoBehaviour
    {
        [Tooltip ("Indica que la celda tiene hielo")]
        public SpriteRenderer iceFloor;

        [Tooltip("Indica que la celda es la meta")]
        public SpriteRenderer finFloor;

        [Tooltip("Muros")]
        public SpriteRenderer[] walls;

        [Tooltip("Caminos que puede tomar el jugador")]
        public SpriteRenderer[] paths;

        private int[] path = { 0, 0, 0, 0 }; //Contador de pasadar del player
        private bool[] hints = { false, false, false, false }; //Direccion de las pistas si es que tiene
        private int whatHint = 0; //Numero de pista (0, 1, o 2)
        private Dirs fromH = Dirs.Neutral, toH = Dirs.Neutral; // Direcciones de las pistas
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
            if (path[(int)d] == 1) {
                paths[(int)d].gameObject.SetActive(true);
                paths[(int)d].color = c;
            }
        }

        //Habilita la pista si es que toca
        public bool EnableHint(int h)
        {
            if(whatHint <= h)
            {
                if (fromH != Dirs.Neutral)
                {

                    hints[(int)fromH] = true;
                    if (!paths[(int)fromH].gameObject.activeSelf)
                    {
                        paths[(int)fromH].gameObject.SetActive(true);
                        paths[(int)fromH].color = Color.yellow;
                    }
                }

                if (toH != Dirs.Neutral)
                {
                    hints[(int)toH] = true;
                    if (!paths[(int)toH].gameObject.activeSelf)
                    {
                        paths[(int)toH].gameObject.SetActive(true);
                        paths[(int)toH].color = Color.yellow;
                    }
                }

                return true;
            }
            return false;
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

        // Si es pista se asigna de donde viene y a donde va
        public void SetIsHint(int number, Vector2 from, Vector2 to)
        {
            whatHint = number;
            if(from != Vector2.zero)
                fromH = Utility.GetWallByDir(from - (Vector2)transform.localPosition);
            if(to != Vector2.zero)
                toH = Utility.GetWallByDir(to - (Vector2)transform.localPosition);
        }

        public int GetWhatHint()
        {
            return whatHint;
        }

        private void Start()
        {
#if UNITY_EDITOR
            if(iceFloor = null)
            {
                Debug.LogError("No hay sprite");
                gameObject.SetActive(false);
                return;
            }
#endif
        }

    }
}
