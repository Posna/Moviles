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

        //Crea el nivel
        public void CreateLevel(Color c)
        {
            color_ = c;
            boardManager.Init(this);
            boardManager.SetMap(level.text);
        }

        //Limpia la escena
        public void ClearScene()
        {
            boardManager.ClearMap();
        }

        public Color GetLevelColor()
        {
            return color_;
        }

        public void ShowNewHint()
        {
            boardManager.NewHint();
        }

        public void FinishLevel()
        {
            finLevel.SetActive(true);
            levelUI.SetActive(false);
        }

    }
}
