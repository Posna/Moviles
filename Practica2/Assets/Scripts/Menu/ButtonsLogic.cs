using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Events;
using UnityEngine.UI;
using UnityEngine.EventSystems;

namespace MazesAndMore
{
    public class ButtonsLogic : MonoBehaviour
    {
        public int level;
        public GameObject blockImg;
        public Text text;

        private bool block;

        //Si estan bloqueados no se peude ir al nivel y se muestran con candado
        void Start()
        {
            block = (GameManager._instance.GetMaxLevel() < level);
            if (GameManager._instance.GetMaxLevel() <= level)
            {
                GetComponent<Image>().color = Color.white;
                text.color = Color.black;
            }
            if (block)
            {
                blockImg.SetActive(true);
                text.gameObject.SetActive(false);
            }


            GetComponent<Button>().onClick.AddListener(Click);
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