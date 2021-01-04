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
            _tiles = new Tile[map.GetWidth(), map.GetHeight()];
            for (int i = 0; i < map.GetHeight(); i++)
            {
                for (int j = 0; j < map.GetWidth(); j++)
                {
                    _tiles[j, i] = Instantiate(tilePrefab, new Vector2(j, i), Quaternion.identity);
                    for (int k = 0; k < 4; k++)
                    {
                        if (map.GetWall(new Vector2Int(j, i), (Walls)k))
                            _tiles[j, i].EnableWall((Walls)k);
                    }
                }
            }

            //Activa el final de casilla
            _tiles[map.GetFin().x, map.GetFin().y].EnableFin();
        }

        public void Init(LevelManager lvl)
        {
            _levelManager = lvl;
        }

        private Tile[,] _tiles;

        private LevelManager _levelManager;
    }
}
