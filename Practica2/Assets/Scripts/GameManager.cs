using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using System.Security.Cryptography;
using System;
using System.Text;

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

        private PackageSave sFile; //Guardado de partida
        private int package; //Pack actual
        private int level; //Nivel actial
        private int[] maxLevels; //Nivel Maximo (para guardar)
        private bool pause = false; 
        private int hints = 0;


        private void Awake()
        {
            
        }
        void Start()
        {

            Load();
#if UNITY_EDITOR
            hints = 9999;
            package = packageToPlay;
            level = levelToPlay;
            //ResetSaveData(); //Reinicia los valores guardados
#endif
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

        //Reinicia el nivel
        public void ResetLevel()
        {
            levelManager.ClearScene();
            StartNewScene();
        }

        // Actualiza el nivel y muestra la pantalla de final de nivel
        public void FinishLevel()
        {
            level++;
            level = level % levelPackages[package].levels.Length;
            Save();
            levelManager.FinishLevel();
            levelManager.ClearScene();
        }

        //Pasa de nivel y muestra un anuncio
        public void NextLevel()
        {
            AdsManager._ADInstance.DispayAD();       
            StartNewScene();
        }

        //Enseña una nueva pista
        public void ShowHint()
        {
            levelManager.ShowNewHint();
            Save();
        }

        // pausa el juego
        public void Pause()
        {
            pause = true;
        }

        // despausa el juego
        public void Resume()
        {
            pause = false;
        }


        public bool isPaused()
        {
            return pause;
        }

        //Añade pistas y guarda partida
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
            BasicInit();
            if (json != "null")
            {
                PackageSave pack = JsonUtility.FromJson<PackageSave>(json);

                string hash = ComputeSha256Hash(json);
                if (hash == PlayerPrefs.GetString("hash", "null")) //Comprobacion del hash
                {                    
                    hints = pack.h;
                    int l = maxLevels.Length;
                    if (maxLevels.Length > pack.p.Length)
                        l = pack.p.Length;

                    for (int i = 0; i < l; i++)
                    {
                        maxLevels[i] = pack.p[i];
                    }
                }
            }
        }

        //Inicio basico de las pistas y el nivel maximo de cada pack
        void BasicInit()
        {
            hints = 0;
            for (int i = 0; i < maxLevels.Length; i++)
                maxLevels[i] = 0;
        }

        // Guarda la partida en PlayerPrefs
        void Save()
        {
            if (maxLevels[package] < level)
                maxLevels[package] = level;
            sFile = new PackageSave();
            sFile.h = hints;
            sFile.p = maxLevels;
            string json = JsonUtility.ToJson(sFile);
            string hash = ComputeSha256Hash(json);

            PlayerPrefs.SetString("memory", json);
            PlayerPrefs.SetString("hash", hash);
        }

        // Devuelve el hash de un json
        string ComputeSha256Hash(string rawData)
        {
            // Create a SHA256   
            using (SHA256 sha256Hash = SHA256.Create())
            {
                // ComputeHash - returns byte array  
                byte[] bytes = sha256Hash.ComputeHash(Encoding.UTF8.GetBytes(rawData));

                // Convert byte array to a string   
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < bytes.Length; i++)
                {
                    builder.Append(bytes[i].ToString("x2"));
                }
                return builder.ToString();
            }
        }

        //Carga PlayScene
        public void StartGame()
        {
            SceneManager.LoadScene("PlayScene");
        }

        //Carga MenuScene
        public void GoToMenu()
        {
            SceneManager.LoadScene("MenuScene");
        }
       
        public int GetMaxLevel()
        {
            return maxLevels[package];
        }

        public int GetMaxLevel(int p)
        {
            return maxLevels[p];
        }

        public void SetLevel(int l)
        {
            level = l;
        }

        public void SetPackage(int p)
        {
            package = p;
        }

        // Elimina la partida guardada
        public void ResetSaveData()
        {
            PlayerPrefs.DeleteKey("memory");
        }

    }
}
