using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore {
    public class GameManager : MonoBehaviour
    {

        public LevelPackage[] levelPackages;

        public LevelManager levelManager;
        private void Start()
        {
            
        }

        static GameManager _instance;


    }
}
