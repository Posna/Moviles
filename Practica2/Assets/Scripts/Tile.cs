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

        private int[] path = { 0, 0, 0, 0 }; 

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

        public void EnablePath(Dirs d, Color c)
        {
            path[(int)d]++;
            if (path[(int)d] == 1) {
                paths[(int)d].gameObject.SetActive(true);
                paths[(int)d].color = c;
            }
        }

        public void DisablePath(Dirs d, Color c)
        {
            path[(int)d]--;
            if (path[(int)d] <= 0)
            {
                paths[(int)d].gameObject.SetActive(false);
            }

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
