using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore
{
    public class PackagesButtons : MonoBehaviour
    {
        [Header("Variables cambiantes")]        
        public Text percentage;
        public Text levelName;
        public Image spriteRenderer;

        private MenuManager menuManager;
        private int package;

        void Start()
        {
            Button bt = GetComponent<Button>();
            bt.onClick.AddListener(Click);
        }

        public void Init(Sprite sprite, string name, int n, MenuManager menuManager)
        {
            spriteRenderer.sprite = sprite;
            levelName.text = name;
            package = n;
            this.menuManager = menuManager;
        }

        void Click()
        {
            GameManager._instance.SetPackage(package);
            menuManager.ShowButtonsLevel(GameManager._instance.levelPackages[package].color, 
            GameManager._instance.levelPackages[package].levels.Length, GameManager._instance.levelPackages[package].packName);
        }

        private void Update()
        {
            percentage.text = ((int)((GameManager._instance.GetMaxLevel(package) / (float)GameManager._instance.levelPackages[package].levels.Length) * 100.0f)).ToString() + "%"; 
        }


    }
}
