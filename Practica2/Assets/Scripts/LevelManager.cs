using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{

    public class LevelManager : MonoBehaviour
    {
        public BoardManager boardManager;

        public TextAsset level;

        private Color color_;

        private void Start()
        {
            //CreateLevel();
        }

        public void CreateLevel(Color c)
        {
            color_ = c;
            boardManager.Init(this);
            boardManager.SetMap(Map.FromJson(level.text));
        }
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

    }
}
