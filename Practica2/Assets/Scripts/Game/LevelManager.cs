using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class LevelManager : MonoBehaviour
    {
        public BoardManager boardManager;

        public TextAsset level;
        public GameObject finLevel;
        public GameObject levelUI;

        private Color color_;

        /// <summary>
        /// Crea el nivel con un color concreto
        /// </summary>
        /// <param name="c"> Color del nivel </param>
        public void CreateLevel(Color c)
        {
            color_ = c;
            boardManager.Init(this);
            boardManager.SetMap(level.text);
        }

        /// <summary>
        /// Limpia la escena
        /// </summary>
        public void ClearScene()
        {
            boardManager.ClearMap();
        }

        /// <returns> Devuelve el color del nivel </returns>
        public Color GetLevelColor()
        {
            return color_;
        }

        /// <summary>
        /// Enseña una nueva pista
        /// </summary>
        public void ShowNewHint()
        {
            boardManager.NewHint();
        }

        /// <summary>
        /// Termina el nivel
        /// </summary>
        public void FinishLevel()
        {
            finLevel.SetActive(true);
            levelUI.SetActive(false);
        }

    }
}
