using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore {

    public enum Walls{ Up, Down, Left, Right }

    public class Tile : MonoBehaviour
    {
        [Tooltip ("Indica que la celda tiene hielo")]
        public SpriteRenderer iceFloor;

        [Tooltip("Indica que la celda es la meta")]
        public SpriteRenderer finFloor;

        [Tooltip("Muros")]
        public SpriteRenderer[] walls;

        public void EnableIce()
        {
            iceFloor.gameObject.SetActive(true);
        }

        public void EnableFin()
        {
            finFloor.gameObject.SetActive(true);
        }

        //Con un enum o algo de eso, o una estructura
        public void EnableWall(Walls w)
        {
            walls[(int)w].gameObject.SetActive(true); 
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
