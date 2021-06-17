using UnityEngine;

namespace MazesAndMore
{
    //Clase publica a forma de callBack
    public class ButtonManager : MonoBehaviour
    {
        public void ShowHint()
        {
            GameManager._instance.ShowHint();
        }

        /// <summary>
        /// Agrega n pistas
        /// </summary>
        /// <param name="n"> Número de pistas que debe agregar </param>
        public void AddHint(int n)
        {
            GameManager._instance.AddHints(n);
        }

        /// <summary>
        /// Vuleve al menú principal
        /// </summary>
        public void GoToMenu()
        {
            GameManager._instance.GoToMenu();
        }

        /// <summary>
        /// Reanuda la partida
        /// </summary>
        public void Resume()
        {
            GameManager._instance.Resume();
        }

        /// <summary>
        /// Pausa el juego
        /// </summary>
        public void Pause()
        {
            GameManager._instance.Pause();
        }

        /// <summary>
        /// Reinicia el nivel
        /// </summary>
        public void ResetLevel()
        {
            GameManager._instance.ResetLevel();
        }

        /// <summary>
        /// Siguiente nivel
        /// </summary>
        public void NextLevel()
        {
            GameManager._instance.NextLevel();
        }
    }
}