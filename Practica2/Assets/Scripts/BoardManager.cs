using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class BoardManager : MonoBehaviour
    {

        public Tile tilePrefab;
        public Camera cam;
        public Move player;

        private int hints = 0;

        private Move _p;

        int width;
        int height;

        private Vector2 resolution;

        private void Awake()
        {
            resolution = new Vector2(Screen.width, Screen.height);
        }

        private void Update()
        {
            if (resolution.x != Screen.width || resolution.y != Screen.height)
            {
                AdjustResolution();
                resolution.x = Screen.width;
                resolution.y = Screen.height;
            }
        }


        public void SetMap(Map map)
        {
            //ClearMap();
            _tiles = new Tile[map.GetWidth(), map.GetHeight()];
            for (int i = 0; i < map.GetHeight(); i++)
            {
                for (int j = 0; j < map.GetWidth(); j++)
                {
                    _tiles[j, i] = Instantiate(tilePrefab, new Vector2(j, i), Quaternion.identity, gameObject.transform);
                    for (int k = 0; k < 4; k++)
                    {
                        if (map.GetWall(new Vector2Int(j, i), (Dirs)k))
                            _tiles[j, i].EnableWall((Dirs)k);
                    }
                }
            }

            //Activa el final de casilla
            _tiles[map.GetFin().x, map.GetFin().y].EnableFin(_levelManager.GetLevelColor());

            _p = Instantiate(player, new Vector2(map.GetIni().x, map.GetIni().y), Quaternion.identity, gameObject.transform);
            _p.Init(_levelManager.GetLevelColor(), map.GetFin());

            height = map.GetHeight();
            width = map.GetWidth();

            // Activamos los tiles con ice
            foreach (Vector2 item in map.GetIce())
            {
                _tiles[(int)item.x, (int)item.y].EnableIce();
            }


            //Añadimos las pistas para dejarlas listas
            int size = map.GetHints().Length;
            Vector2[] hints = map.GetHints();
            _tiles[(int)hints[0].x, (int)hints[0].y].SetIsHint(0, map.GetIni(), hints[1]);
            for (int i = 1; i < size - 1; i++)
            {
                int hintN = Mathf.FloorToInt(i / (size / 3.0f));
                _tiles[(int)hints[i].x, (int)hints[i].y].SetIsHint(hintN, hints[i - 1], hints[i + 1]);
            }
            _tiles[(int)hints[size - 1].x, (int)hints[size - 1].y].SetIsHint(2, hints[size - 2], map.GetFin());

            AdjustResolution();
        }

        private void AdjustResolution()
        {
            ResetScale();
            float r, scale;
            r = cam.pixelWidth / (float)cam.pixelHeight;


            scale = ((cam.orthographicSize - 0.01f) * 2 * r) / width;

            if(r > 3/5.0f)
                scale = ((cam.orthographicSize - 1.01f) * 2) / height;

            gameObject.transform.localScale = new Vector3(scale, scale, 1);
            gameObject.transform.position = new Vector3(-scale * ((width / 2.0f) - 0.5f), -scale * ((height / 2.0f) - 0.5f), 0);
        }

        private void ResetScale()
        {
            gameObject.transform.localScale = new Vector3(1, 1, 1);
            gameObject.transform.position = new Vector3(0, 0, 0);
        }

        public void Init(LevelManager lvl)
        {
            _levelManager = lvl;
        }

        public void ClearMap()
        {
            ResetScale();
            if (_tiles != null && _tiles.Length > 0)
            {
                foreach (Tile item in _tiles)
                {
                    Destroy(item.gameObject);
                }
            }
            hints = 0;
            Destroy(_p.gameObject);
        }

        /// <summary>
        /// Hace visible el camino por el que pasa el jugador o muestra la pista
        /// </summary>
        /// <param name="p"> Posicion del Tile </param>
        /// <param name="d"> Direccion del camino </param>
        /// <param name=""> Color del camino </param>
        static public void EnablePath(Vector2 p, Dirs d, Color c, bool active)
        {
            if(d != Dirs.Neutral)
            {
                if(active)
                    _tiles[(int)p.x, (int)p.y].EnablePath(d, c);
                else
                    _tiles[(int)p.x, (int)p.y].DisablePath(d, c);
            }
        }

        static public bool IsIce(Vector2 p)
        {
            return _tiles[(int)p.x, (int)p.y].IsIce();
        }

        public void NewHint()
        {
            if (hints < 3)
            {
                int i = 0;
                Vector2[] h = Map.GetMap().GetHints();
                bool isHint = true;
                while (i < h.Length && isHint)
                {
                    isHint = _tiles[(int)h[i].x, (int)h[i].y].EnableHint(hints);
                    i++;
                }
                hints++;
            }
        }

        static private Tile[,] _tiles;

        private LevelManager _levelManager;
    }
}
