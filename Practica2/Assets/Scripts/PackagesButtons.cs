using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore
{
    public class PackagesButtons : MonoBehaviour
    {
        public int package;
        void Start()
        {
            Button bt = GetComponent<Button>();
            bt.onClick.AddListener(Click);
        }

        void Click()
        {
            GameManager._instance.SetPackage(package);
            MenuManager._menuInstance.ShowButtonsLevel(GameManager._instance.levelPackages[package].color, 
                GameManager._instance.levelPackages[package].levels.Length, GameManager._instance.levelPackages[package].name);
        }


    }
}
