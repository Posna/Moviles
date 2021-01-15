using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore {

    public enum Dirs{ Up, Down, Left, Right, Neutral }
    //public enum Dirs { Up, Down, Left, Right }

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

        public void EnableIce()
        {
            iceFloor.gameObject.SetActive(true);
        }

        public void EnableFin()
        {
            finFloor.gameObject.SetActive(true);
        }

        //Con un enum o algo de eso, o una estructura
        public void EnableWall(Dirs w)
        {
            walls[(int)w].gameObject.SetActive(true);   
        }

        public void SetActivePath(Dirs d, Color c, bool a)
        {
            paths[(int)d].gameObject.SetActive(a);
            paths[(int)d].color = c;
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
