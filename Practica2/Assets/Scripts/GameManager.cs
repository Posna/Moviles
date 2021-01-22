using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

namespace MazesAndMore {
    public class GameManager : MonoBehaviour
    {
#if UNITY_EDITOR
        public int levelToPlay;
        public int packageToPlay;
#endif
        //Serializar objetos para el guardado
        public LevelManager levelManager;
        public LevelPackage[] levelPackages;

        private PackageSave sFile;
        private int package;
        private int level;
        private int[] maxLevels;
        private bool pause = false;
        private int hints = 0;


        private void Awake()
        {
            
        }
        void Start()
        {

            Load();
#if UNITY_EDITOR
            //hints = 9999;
            //package = packageToPlay;
            //level = levelToPlay;
            //ResetSaveData();
#endif
            Debug.Log("Start llamado");
            if (_instance != null)
            {
                _instance.levelManager = levelManager;
                _instance.StartNewScene();
                DestroyImmediate(gameObject);
                return;
            }
            else
            {
                _instance = this;
            }
            StartNewScene();
            DontDestroyOnLoad(gameObject);
        }

        public static GameManager _instance { get; private set; }

        //Si existe lvlmanager es que estoy en la escena de juego
        //si esta el entero lanzo el nivel que quiero, si no el que dio el jugador
        private void StartNewScene()
        {
            if (levelManager)
            {
                levelManager.level = levelPackages[package].levels[level];
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
            AdsManager._ADInstance.DispayAD();
            level++;
            Save();
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

        public bool isPaused()
        {
            return pause;
        }

        public void AddHints(int sum)
        {
            hints +=sum;
            Save();
        }

        public int GetHints()
        {
            return hints;
        }

        public string GetName()
        {
            return levelPackages[package].name + " - " + (level+1);
        }

        // Load the levels availables and hints
        void Load()
        {
            maxLevels = new int[levelPackages.Length];
            string json = PlayerPrefs.GetString("memory", "null");
            if (json != "null")
            {
                SaveFile s = JsonUtility.FromJson<SaveFile>(json);
                
                int has = s.pack.GetHashCode();
                if (s.hash == has)
                {
                    PackageSave pack = JsonUtility.FromJson<PackageSave>(s.pack);
                    hints = pack.h;
                    maxLevels = pack.p;
                }
                else
                    BasicInit();
            }else
                BasicInit();
        }

        void BasicInit()
        {
            hints = 0;
            for (int i = 0; i < maxLevels.Length; i++)
                maxLevels[i] = 0;
        }

        void Save()
        {
            if (maxLevels[package] < level)
                maxLevels[package] = level;
            sFile = new PackageSave();
            sFile.h = hints;
            sFile.p = maxLevels;
            string j = JsonUtility.ToJson(sFile);
            SaveFile s = new SaveFile();
            s.hash = j.GetHashCode();
            s.pack = j;
            string json = JsonUtility.ToJson(s);
            PlayerPrefs.SetString("memory", json);
        }

        public void StartGame()
        {
            SceneManager.LoadScene("PlayScene");
        }

        public void GoToMenu()
        {
            SceneManager.LoadScene("MenuScene");
        }

        public int GetMaxLevel()
        {
            return maxLevels[package];
        }

        public void SetLevel(int l)
        {
            level = l;
        }

        public void SetPackage(int p)
        {
            package = p;
        }

        public void ResetSaveData()
        {
            PlayerPrefs.DeleteKey("memory");
        }

    }
}
