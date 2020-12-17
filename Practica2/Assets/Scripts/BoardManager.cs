using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class BoardManager : MonoBehaviour
    {

        public Tile tilePrefab;


        public void SetMap(Map map)
        {

        }

        public void Init(LevelManager lvl)
        {
            _levelManager = lvl;
        }

        private Tile[,] _tiles;

        private LevelManager _levelManager;
    }
}
