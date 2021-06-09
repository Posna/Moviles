using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore
{
    [RequireComponent(typeof(Button))]
    public class ButtonsLogic : MonoBehaviour
    {
        [Header("Componentes")]
        public Image blockImg;

        [Header("Hijos")]
        public GameObject lockImg;
        public Text buttonText;

        private int level;
        private bool block; // Variable de control para saber que niveles estan bloqueados


        //Si estan bloqueados no se peude ir al nivel y se muestran con candado
        void Start()
        {
            block = (GameManager._instance.GetMaxLevel() < level);
            if (GameManager._instance.GetMaxLevel() <= level)
            {
                blockImg.color = Color.white;
                buttonText.color = Color.black;
            }
            if (block)
            {
                lockImg.SetActive(true);
                buttonText.gameObject.SetActive(false);
            }


            GetComponent<Button>().onClick.AddListener(Click);
        }


        public void Init(Color c, int lvl, string text)
        {
            blockImg.color = c;
            level = lvl;
            buttonText.text = text;
        }

        public void Click()
        {
            if(!block)
            {
                GameManager._instance.SetLevel(level);
                GameManager._instance.StartGame();
            }
        }
    }
}