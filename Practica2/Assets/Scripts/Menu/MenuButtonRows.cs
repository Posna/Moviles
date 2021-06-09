using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore {
    public class MenuButtonRows : MonoBehaviour
    {
        public RectTransform rows;
        public GameObject buttons;
        public RectTransform zonaButtons;
        public VerticalLayoutGroup verticalLayout;


        //Creacion de los botones niveles. Se crean filas y dentro los botones
        public void CreateButtons(Color c, int levels)
        {
            int r = (int)Mathf.Ceil((levels / 5.0f));
            int tam = (r - 7);
            if (tam < 0)
                tam = 0;

            zonaButtons.offsetMax = new Vector2(zonaButtons.offsetMax.x, 0);
            zonaButtons.offsetMin = new Vector2(zonaButtons.offsetMin.x, -tam * (rows.rect.height + verticalLayout.spacing));

            for (int i = 0; i < r; i++)
            {
                GameObject f = Instantiate(rows.gameObject, transform);
                int nButtons = 5;
                if (i == r - 1 && levels % 5 != 0)
                    nButtons = levels % 5;
                for (int j = 0; j < nButtons; j++)
                {
                    GameObject b = Instantiate(buttons, f.transform);
                    ButtonsLogic logic = b.GetComponent<ButtonsLogic>();
                    int level = 5 * i + j;
                    string text = (5 * i + j + 1).ToString();
                    logic.Init(c, level, text);
                }
            }
        }

    }
}