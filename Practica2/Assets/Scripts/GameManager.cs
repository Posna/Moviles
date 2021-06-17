using UnityEngine;
using UnityEngine.SceneManagement;
using System.Security.Cryptography;
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

        /// <summary>
        /// Reinicia el nivel
        /// </summary>
        public void ResetLevel()
        {
            levelManager.ClearScene();
            StartNewScene();
        }

        /// <summary>
        /// Actualiza el nivel y muestra la pantalla de final de nivel
        /// </summary>
        public void FinishLevel()
        {
            level++;
            level = level % levelPackages[package].levels.Length;
            Save();
            levelManager.FinishLevel();
            levelManager.ClearScene();
        }

        /// <summary>
        /// Pasa de nivel y muestra un anuncio
        /// </summary>
        public void NextLevel()
        {
            AdsManager._ADInstance.DispayAD();       
            StartNewScene();
            Input.ResetInputAxes();
        }

        /// <summary>
        /// Enseña una nueva pista
        /// </summary>
        public void ShowHint()
        {
            levelManager.ShowNewHint();
            Save();
        }

        /// <summary>
        /// Pausa el juego
        /// </summary>
        public void Pause()
        {
            pause = true;
        }

        /// <summary>
        /// Despausa el juego. Limpia el buffer de input para evitar 
        /// movimientos del personaje
        /// </summary>
        public void Resume()
        {
            pause = false;
            Input.ResetInputAxes();
        }


        /// <returns> Devuelve si el juego esta pausado </returns>
        public bool isPaused()
        {
            return pause;
        }

        /// <summary>
        /// Añade pistas y guarda partida
        /// </summary>
        /// <param name="sum"> numero de pistas a añadir (o restar)</param>
        public void AddHints(int sum)
        {
            hints +=sum;
            Save();
        }


        /// <returns> Devuelve el numero de pistas </returns>
        public int GetHints()
        {
            return hints;
        }

        /// <returns> Devuelve el nombre del nivel </returns>
        public string GetName()
        {
            return levelPackages[package].name + " - " + (level+1);
        }

        /// <summary>
        /// Load the levels availables and hints
        /// </summary>
        void Load()
        {
            maxLevels = new int[levelPackages.Length];
            string json = PlayerPrefs.GetString("memory", "null"); // JSON guardado de la partida anterior
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

        /// <summary>
        /// Inicio basico de las pistas y el nivel maximo de cada pack
        /// </summary>
        void BasicInit()
        {
            hints = 0;
            for (int i = 0; i < maxLevels.Length; i++)
                maxLevels[i] = 0;
        }

        /// <summary>
        /// Guarda la partida en PlayerPrefs
        /// </summary>
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
            PlayerPrefs.SetString("hash", hash); // Guardado del HASH
        }

        /// <summary>
        /// Devuelve el hash de un string
        /// </summary>
        /// <param name="rawData"> String de datos </param>
        /// <returns> string con el hash de rawData </returns>
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

        /// <summary>
        /// Carga PlayScene
        /// </summary>
        public void StartGame()
        {
            SceneManager.LoadScene("PlayScene");
        }

        /// <summary>
        /// Carga MenuScene
        /// </summary>
        public void GoToMenu()
        {
            SceneManager.LoadScene("MenuScene");
        }
       
        /// <returns> Numero del nivel mas avanzado dentro del pack actual </returns>
        public int GetMaxLevel()
        {
            return maxLevels[package];
        }

        /// <param name="p"> Numero de pack </param>
        /// <returns> Numero del nivel mas avanzado dentro del pack p </returns>
        public int GetMaxLevel(int p)
        {
            return maxLevels[p];
        }

        /// <summary>
        /// Seleccionar nivel
        /// </summary>
        /// <param name="l"> Nivel nuevo </param>
        public void SetLevel(int l)
        {
            level = l;
        }

        /// <summary>
        /// Selecciona un pack
        /// </summary>
        /// <param name="p"> Pack seleccionado </param>
        public void SetPackage(int p)
        {
            package = p;
        }

        /// <summary>
        /// Elimina la partida guardada
        /// </summary>
        public void ResetSaveData()
        {
            PlayerPrefs.DeleteKey("memory");
        }

    }
}
