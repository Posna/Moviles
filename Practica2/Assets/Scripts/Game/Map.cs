using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System;

namespace MazesAndMore
{
    public class Map
    {   
        //Auxiliar para desserializar el mapa
        [System.Serializable]
        class MapaSerializable
        {
            public int r = 0;
            public int c = 0;

            public Vector2Int s = new Vector2Int();
            public Vector2Int f = new Vector2Int();

            public MyVector2[] h = null;
            public MyVector2[] i = null;
            public WallsOD[] w = null;
        }
        //Auxiliar para desserializar los muros
        [System.Serializable]
        public class WallsOD
        {
            public Vector2Int o;
            public Vector2Int d;
        }

        //Auxiliar para desserializar las pistas
        [System.Serializable]
        public class MyVector2
        {
            public int x = 0;
            public int y = 0;
        }

        //Lectura del json
        public void FromJson(string json)
        {
            MapaSerializable mapaAux = JsonUtility.FromJson<MapaSerializable>(json);

            _height = mapaAux.r; // Filas
            _width = mapaAux.c; // Columnas
            
            _fin = mapaAux.f; // Casilla final
            _ini = mapaAux.s; // Casilla inicial

            // hints
            _hints = new Vector2[mapaAux.h.Length];
            for (int i = 0; i < mapaAux.h.Length; i++)
            {
                _hints[i].x = mapaAux.h[i].x;
                _hints[i].y = mapaAux.h[i].y;
                if (_hints[i].x < 0 || _hints[i].x >= GetWidth())
                    _hints[i].x = 0;

                if (_hints[i].y < 0 || _hints[i].y >= GetHeight())
                    _hints[i].y = 0;
            }

            //Lectura de Hielo
            _ice = new Vector2[mapaAux.i.Length];
            for (int i = 0; i < mapaAux.i.Length; i++)
            {
                _ice[i].x = mapaAux.i[i].x;
                _ice[i].y = mapaAux.i[i].y;
                if (_ice[i].x < 0 || _ice[i].x >= GetWidth())
                    _ice[i].x = 0;

                if (_ice[i].y < 0 || _ice[i].y >= GetHeight())
                    _ice[i].y = 0;
            }

            /*** init mapa ***/
            _walls = new bool[_width, _height][];
            initMap();

            /**** mapa ***/
            BuildMap(mapaAux);
        }

        //Contruccion de los muros
        private void BuildMap(MapaSerializable mapaAux)
        {
            foreach (WallsOD item in mapaAux.w)
            {
                Debug.Log(item.o.x + " " + (item.d.y) + "\n");
                Dirs lado = Dirs.Down;
                if (item.o.x < item.d.x)
                    lado = Dirs.Down;
                else if (item.o.x != item.d.x)
                {
                    int a = item.o.x;
                    item.o.x = item.d.x;
                    item.d.x = a;
                    lado = Dirs.Down;
                }
                if (item.o.y > item.d.y)
                    lado = Dirs.Left;
                else if (item.o.y != item.d.y)
                {
                    int a = item.o.y;
                    item.o.y = item.d.y;
                    item.d.y = a;
                    lado = Dirs.Left;
                }

                if (item.o.x == _width && item.o.y == _height)
                {
                    _walls[item.o.x - 1, item.o.y - 1][(int)Dirs.Right] = true;
                    continue;
                }
                if (item.o.x == _width && lado != Dirs.Down)
                {
                    _walls[item.o.x - 1, item.d.y][(int)Dirs.Right] = true;
                    continue;
                }
                if (item.o.y == _height && lado != Dirs.Left)
                {
                    _walls[item.o.x, item.o.y - 1][(int)Dirs.Up] = true;
                    continue;
                }

                _walls[item.o.x, item.d.y][(int)lado] = true;
                normalizeWall(item.o.x, item.d.y, lado);
            } //foreach

        }

        // Inicializacion por defecto del mapa
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
        private void normalizeWall(int x, int y, Dirs lado)
        {
            int k = (int)lado;
            if (_walls[x, y][k])
            {
                
                if (k == 1 && y > 0)
                {
                    _walls[x, y-1][(int)Dirs.Up] = true;
                }
                else if (k == 2 && x > 0)
                {
                    _walls[x - 1, y][(int)Dirs.Right] = true;
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

        //Obtenemos si hay muro en una posicion y direccion concreta
        public bool GetWall(Vector2 p, Dirs w)
        {
            if (w == Dirs.Neutral)
                return true;
            return _walls[(int)p.x, (int)p.y][(int)w];
        }

        public Vector2Int GetIni()
        {
            return _ini;
        }

        public Vector2Int GetFin()
        {
            return _fin;
        }

        //Comprueba cuantass direcciones hay disponibles en una casilla concreta
        public int GetNDirs(Vector2 p)
        {
            int i = 0;

            for (int w = 0; w < 4;w++)
            {
                i += Convert.ToInt32(_walls[(int)p.x, (int)p.y][w]);
            }

            return 4 - i ;
        }

        public Vector2[] GetIce()
        {
            return _ice;
        }

        //Devuelve la primera direccion que no sea la dada (d)
        public Vector2 GetOneDir(Vector2 p, Vector2 d)
        {
            bool found = false;
            int i = 0;
            while (!found && i < 4)
            {
                if(Utility.GetDirByWall((Dirs)i) != -1*d)
                    found = !_walls[(int)p.x, (int)p.y][i];
                if(!found)
                    i++;
            }

            return Utility.GetDirByWall((Dirs)i);
        }

        

        public Vector2[] GetHints()
        {
            return _hints;
        }
        
        private bool[,][] _walls;

        private Vector2Int _ini;
        private Vector2Int _fin;

        private Vector2[] _hints;
        private Vector2[] _ice;

        private int _width;
        private int _height;

    }
}
