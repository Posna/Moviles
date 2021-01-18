using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

namespace MazesAndMore {
    public class GameManager : MonoBehaviour
    {
#if UNITY_EDITOR
        public int levelToPlay;
        public int package;
#endif

        public LevelManager levelManager;
        public LevelPackage[] levelPackages;

        static private bool pause = false;

        void Start()
        {
            Debug.Log("Start llamado");
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
                levelManager.level = levelPackages[package].levels[levelToPlay];
#endif
                levelManager.CreateLevel(levelPackages[package].color);
            }
        }

        public void ResetLevel()
        {
            //SceneManager.LoadScene(SceneManager.GetActiveScene().name);
            levelManager.ClearScene();
            StartNewScene();
        }

        public void nextLevel()
        {
            levelToPlay++;
            levelManager.ClearScene();
            StartNewScene();
        }

        public void ShowHint()
        {
            levelManager.ShowNewHint();
        }

        public void Pause()
        {
            pause = true;
        }

        public void Resume()
        {
            pause = false;
        }

        static public bool isPaused()
        {
            return pause;
        }

    }
}
