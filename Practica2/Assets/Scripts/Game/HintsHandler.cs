using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore {

    [RequireComponent(typeof(Button))]
    public class HintsHandler : MonoBehaviour
    {
        public GameObject hintsMenu;
        void Start()
        {
            Button btn = GetComponent<Button>(); //Grabs the button component
            btn.onClick.AddListener(TaskOnClick); //Adds a listner on the button
        }

        /// <summary>
        /// Activa el panel de pistas si quedan disponibles
        /// </summary>
        void TaskOnClick()
        {
            if (GameManager._instance.GetHints() > 0)
                hintsMenu.SetActive(true);
        }
    }
}
