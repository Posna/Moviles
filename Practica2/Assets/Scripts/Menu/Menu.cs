using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


namespace MazesAndMore
{
    public class Menu : MonoBehaviour
    {
        public GameObject button;
        public RectTransform zonaButtons;

        //Instancia de los botones de los packs
        void Start()
        {
            int r = GameManager._instance.levelPackages.Length;
            float h = button.GetComponent<RectTransform>().rect.height;
            int tam = (r - 8);
            if (tam < 0)
                tam = 0;
            zonaButtons.offsetMin = new Vector2(zonaButtons.offsetMin.x, -tam * (r + h + GetComponent<VerticalLayoutGroup>().spacing));

            for (int i = 0; i < GameManager._instance.levelPackages.Length; i++)
            {
                GameObject newButton = Instantiate(button, transform);
                PackagesButtons logica = newButton.GetComponent<PackagesButtons>();
                logica.Init(GameManager._instance.levelPackages[i].image, GameManager._instance.levelPackages[i].packName, i);
            }
        }
    }
}