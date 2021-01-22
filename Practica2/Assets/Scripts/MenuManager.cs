using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore
{
    public class MenuManager : MonoBehaviour
    {
        public GameObject buttons;
        public GameObject packages;
        public GameObject mainMenu;

        public GameObject verticalZone;
        public Text packName;

        private int state = 0;

        private void Awake()
        {
            _menuInstance = this;
        }

        // Start is called before the first frame update
        public void ShowButtonsLevel(Color c, int i, string name)
        {
            buttons.SetActive(true);
            packages.SetActive(false);
            mainMenu.SetActive(false);
            verticalZone.GetComponent<MenuButtonRows>().CreateButtons(c, i);
            packName.text = name;
            state++;
        }

        public void ShowPackagesLevels()
        {
            buttons.SetActive(false);
            packages.SetActive(true);
            mainMenu.SetActive(false);
            state++;
        }

        public void ShowMainMenu()
        {
            buttons.SetActive(false);
            packages.SetActive(false);
            mainMenu.SetActive(true);
            state++;
        }

        private void ClearChildren()
        {
            foreach (Transform child in verticalZone.transform)
            {
                Destroy(child.gameObject);
            }
        }

        private void Update()
        {
            if (Input.GetKeyDown(KeyCode.Escape))
            {
                switch (state)
                {
                    case 0:
                        Application.Quit();
                        break;
                    case 1:
                        ShowMainMenu();
                        state -= 2;
                        break;
                    case 2:
                        ClearChildren();
                        ShowPackagesLevels();
                        state -= 2;
                        break;
                    default:
                        break;
                }

            }
        }

        public static MenuManager _menuInstance;

    }
}