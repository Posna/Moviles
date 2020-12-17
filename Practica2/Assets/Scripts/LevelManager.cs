using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{

    public class LevelManager : MonoBehaviour
    {
#if UNITY_EDITOR
        public int levelToPlay;
#endif
        public BoardManager boardManager;

        public TextAsset level;

        private void Start()
        {
           /* if (_instance != null)
            {
                _instance.levelManager = levelManager;
                DestroyImmediate(gameObject);
                return;
            }*/
            boardManager.Init(this);
        }

    }
}
