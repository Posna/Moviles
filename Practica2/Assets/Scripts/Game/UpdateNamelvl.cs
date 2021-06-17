using UnityEngine;
using UnityEngine.UI;

namespace MazesAndMore
{
    [RequireComponent(typeof(Text))]
    public class UpdateNamelvl : MonoBehaviour
    {
        private Text text;

        void Start()
        {
            text = GetComponent<Text>();
        }

        void Update()
        {
            text.text = GameManager._instance.GetName(); // Actualiza continuamente el nombre del nivel
        }
    }
}