using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore {
    public class Tile : MonoBehaviour
    {
        [Tooltip ("Indica que la celda noseque")]
        public SpriteRenderer iceFloor;
        
        public void EnableIce()
        {

        }

        public void DisableIce()
        {

        }

        public void EnableStart()
        {

        }

        //Con un enum o algo de eso, o una estructura
        public void EnableWestWall()
        {

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
