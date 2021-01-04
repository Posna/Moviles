using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class Map
    {
        [System.Serializable]
        class Aux
        {
            public int r;
            public int c;

            public Vector2Int s;
            public Vector2Int f;

            public Vector2Int[] h;
            public WallsOD[] w;
        }
        [System.Serializable]
        public class WallsOD
        {
            public Vector2Int o;
            public Vector2Int d;
        }


        public static Map FromJson(string json)
        {
            _m = new Map();
            Aux mapaAux = JsonUtility.FromJson<Aux>(json);

            _m._height = mapaAux.r; // Filas
            _m._width = mapaAux.c; // Columnas

            _m._fin = mapaAux.f; // Casilla final
            _m._ini = mapaAux.s; // Casilla inicial

            /*** init mapa ***/
            _m._walls = new bool[_m._width, _m._height][];
            _m.initMap();

            /**** mapa ***/
            foreach (WallsOD item in mapaAux.w)
            {
                Debug.Log(item.o.x + " " + (item.d.y) + "\n");
                Walls lado;
                if (item.o.x < item.d.x)
                    lado = Walls.Down;
                else
                    lado = Walls.Left;
                if (item.o.x == _m._width && item.o.y == _m._height)
                {
                    _m._walls[item.o.x - 1, item.o.y - 1][(int)Walls.Right] = true;
                    continue;
                }
                if (item.o.x == _m._width && lado != Walls.Down)
                {
                    _m._walls[item.o.x - 1, item.d.y][(int)Walls.Right] = true;
                    continue;
                }
                if (item.o.y == _m._height && lado != Walls.Left)
                {
                    _m._walls[item.o.x, item.o.y - 1][(int)Walls.Up] = true;
                    continue;
                }
                
                _m._walls[item.o.x, item.d.y][(int)lado] = true;
                _m.normalizeWall(item.o.x, item.d.y, lado);
            } //foreach

            return _m;
        }

        private void initMap()
        {
            for (int i = 0; i < _height; i++)
            {
                for (int j = 0; j < _width; j++)
                {
                    _walls[j, i] = new bool[] { false, false, false, false };
                }
            }
        }

        // Pone los muros a true de las casillas contiguas
        private void normalizeWall(int x, int y, Walls lado)
        {
            int k = (int)lado;
            if (_walls[x, y][k])
            {
                if(k == 0 && y > 0)
                {
                    _walls[x, y-1][(int)Walls.Down] = true;
                }
                else if (k == 1 && y < _height-1)
                {
                    _walls[x, y+1][(int)Walls.Up] = true;
                }
                else if (k == 2 && x > 0)
                {
                    _walls[x - 1, y][(int)Walls.Right] = true;
                }
                else if (k == 3 && x < _width-1)
                {
                    _walls[x + 1, y][(int)Walls.Left] = true;
                }
            } //if
        }

        public int GetWidth()
        {
            return _width;
        }

        public int GetHeight()
        {
            return _height;
        }

        public bool GetWall(Vector2Int p, Walls w)
        {
            return _walls[p.x, p.y][(int)w];
        }

        public Vector2Int GetIni()
        {
            return _ini;
        }

        public Vector2Int GetFin()
        {
            return _fin;
        }

        private int[,] _grid;

        class WallPerTile
        {
            //Arriba abjo izquiera derecha
            public bool[] w = new bool[] { false, false, false, false };
        }
        private bool[,][] _walls;

        private Vector2Int _ini;
        private Vector2Int _fin;

        private int _width;
        private int _height;

        private static Map _m;

    }
}
