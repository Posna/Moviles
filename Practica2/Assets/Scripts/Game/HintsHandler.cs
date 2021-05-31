using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore {
    public class HintsHandler : MonoBehaviour
    {
        public GameObject hintsMenu;
        void Start()
        {
            Button btn = GetComponent<Button>(); //Grabs the button component
            btn.onClick.AddListener(TaskOnClick); //Adds a listner on the button
        }

        void TaskOnClick()
        {
            if (GameManager._instance.GetHints() > 0)
                hintsMenu.SetActive(true);
        }
    }
}
