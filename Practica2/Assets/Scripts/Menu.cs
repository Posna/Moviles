using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


namespace MazesAndMore
{
    public class Menu : MonoBehaviour
    {
        void Start()
        {
            for (int i = 0; i < GameManager._instance.levelPackages.Length; i++)
            {
                GameObject newButton = Instantiate(GameManager._instance.levelPackages[i].intro, transform);
            }
        }
    }
}
