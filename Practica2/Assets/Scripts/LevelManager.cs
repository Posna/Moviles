using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{

    public class LevelManager : MonoBehaviour
    {
        public BoardManager boardManager;

        public TextAsset level;

        private void Start()
        {
            boardManager.Init(this);
            boardManager.SetMap(Map.FromJson(level.text));
        }

        public void CreateLevel()
        {
        }

    }
}
