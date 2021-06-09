using UnityEngine;
using UnityEngine.UI;


namespace MazesAndMore
{
    public class Menu : MonoBehaviour
    {
        const int MAX_BUTTONS = 8;

        [Header("Instancia")]
        [Tooltip("Boton a instanciar para elegir el pack")]
        public GameObject button;

        [Header("Menu Manager")]
        public MenuManager menuManager;

        [Header("Modificables en ejecucion")]
        public RectTransform zonaButtons;
        public VerticalLayoutGroup verticalLayout;

        //Instancia de los botones de los packs
        void Start()
        {
            int r = GameManager._instance.levelPackages.Length;
            float h = button.GetComponent<RectTransform>().rect.height;
            int tam = (r - MAX_BUTTONS);
            if (tam < 0)
                tam = 0;
            zonaButtons.offsetMin = new Vector2(zonaButtons.offsetMin.x, -tam * (r + h + verticalLayout.spacing));

            for (int i = 0; i < GameManager._instance.levelPackages.Length; i++)
            {
                GameObject newButton = Instantiate(button, transform);
                PackagesButtons logica = newButton.GetComponent<PackagesButtons>();
                logica.Init(GameManager._instance.levelPackages[i].image, GameManager._instance.levelPackages[i].packName, i, menuManager);
            }
        }
    }
}