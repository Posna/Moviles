using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore {
    public class GameManager : MonoBehaviour
    {
#if UNITY_EDITOR
        public int levelToPlay;
#endif

        public LevelManager levelManager;
        public LevelPackage[] levelPackages;


        private void Start()
        {
            if (_instance != null)
            {
                _instance.levelManager = levelManager;
                DestroyImmediate(gameObject);
                return;
            }
            StartNewScene();
            DontDestroyOnLoad(gameObject);
        }

        static GameManager _instance;

        //Si existe lvlmanager es que estoy en la escena de juego
        //si esta el entero lanzo el nivel que quiero, si no el que dio el jugador
        private void StartNewScene()
        {
            if (levelManager)
            {

#if UNITY_EDITOR
                levelManager.level = levelPackages[0].levels[levelToPlay];
#endif
                //levelManager.CreateLevel();
            }
        }

    }
}
