using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore {
    public class UpdateNHints : MonoBehaviour
    {
        private Text text;

        void Start()
        {
            text = GetComponent<Text>();
        }

        // Update is called once per frame
        void Update()
        {
            text.text = GameManager._instance.GetHints().ToString();
        }
    }
}