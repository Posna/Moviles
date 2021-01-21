﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class ButtonManager : MonoBehaviour
    {
        public void ShowHint()
        {
            GameManager._instance.ShowHint();
        }

        public void AddHint(int n)
        {
            GameManager._instance.AddHints(n);
        }

        public void GoToMenu()
        {
            GameManager._instance.GoToMenu();
        }

        public void Resume()
        {
            GameManager._instance.Resume();
        }

        public void Pause()
        {
            GameManager._instance.Pause();
        }

        public void ResetLevel()
        {
            GameManager._instance.ResetLevel();
        }
    }
}