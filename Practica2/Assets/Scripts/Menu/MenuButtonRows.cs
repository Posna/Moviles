using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore {
    public class MenuButtonRows : MonoBehaviour
    {
        public GameObject rows;
        public GameObject buttons;
        public RectTransform zonaButtons;


        //Creacion de los botones niveles. Se crean filas y dentro los botones
        public void CreateButtons(Color c, int levels)
        {
            int r = (int)Mathf.Ceil((levels / 5.0f));
            int tam = (r - 7);
            if (tam < 0)
                tam = 0;
            zonaButtons.offsetMax = new Vector2(zonaButtons.offsetMax.x, 0);
            zonaButtons.offsetMin = new Vector2(zonaButtons.offsetMin.x, -tam * (rows.GetComponent<RectTransform>().rect.height + GetComponent<VerticalLayoutGroup>().spacing));
            for (int i = 0; i < r; i++)
            {
                GameObject f = Instantiate(rows, transform);
                int nButtons = 5;
                if (i == r - 1 && levels % 5 != 0)
                    nButtons = levels % 5;
                for (int j = 0; j < nButtons; j++)
                {
                    GameObject b = Instantiate(buttons, f.transform);
                    b.GetComponent<Image>().color = c;
                    b.GetComponent<ButtonsLogic>().level = 5 * i + j;
                    b.GetComponentInChildren<Text>().text = (5 * i + j + 1).ToString();
                }
            }
        }

    }
}