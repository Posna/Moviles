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
        class Aux
        {
            public int r;
            public int c;

            public Vector2Int s;
            public Vector2Int f;

            public MyVector2[] h;
            public MyVector2[] i;
            public WallsOD[] w;
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
        public static Map FromJson(string json)
        {
            _m = new Map();
            Aux mapaAux = JsonUtility.FromJson<Aux>(json);

            _m._height = mapaAux.r; // Filas
            _m._width = mapaAux.c; // Columnas

            Debug.Log("Alto: " + _m._height + " Ancho: " + _m._width);

            _m._fin = mapaAux.f; // Casilla final
            _m._ini = mapaAux.s; // Casilla inicial

            //_m._hints = mapaAux.h; // hints
            _m._hints = new Vector2[mapaAux.h.Length];
            for (int i = 0; i < mapaAux.h.Length; i++)
            {
                _m._hints[i].x = mapaAux.h[i].x;
                _m._hints[i].y = mapaAux.h[i].y;
                if (_m._hints[i].x < 0 || _m._hints[i].x >= _m.GetWidth())
                    _m._hints[i].x = 0;

                if (_m._hints[i].y < 0 || _m._hints[i].y >= _m.GetHeight())
                    _m._hints[i].y = 0;
            }

            //Lectura de Hielo
            _m._ice = new Vector2[mapaAux.i.Length];
            for (int i = 0; i < mapaAux.i.Length; i++)
            {
                _m._ice[i].x = mapaAux.i[i].x;
                _m._ice[i].y = mapaAux.i[i].y;
                if (_m._ice[i].x < 0 || _m._ice[i].x >= _m.GetWidth())
                    _m._ice[i].x = 0;

                if (_m._ice[i].y < 0 || _m._ice[i].y >= _m.GetHeight())
                    _m._ice[i].y = 0;
            }

            /*** init mapa ***/
            _m._walls = new bool[_m._width, _m._height][];
            _m.initMap();

            /**** mapa ***/
            BuildMap(mapaAux);

            return _m;
        }

        //Contruccion de los muros
        static private void BuildMap(Aux mapaAux)
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

                if (item.o.x == _m._width && item.o.y == _m._height)
                {
                    _m._walls[item.o.x - 1, item.o.y - 1][(int)Dirs.Right] = true;
                    continue;
                }
                if (item.o.x == _m._width && lado != Dirs.Down)
                {
                    _m._walls[item.o.x - 1, item.d.y][(int)Dirs.Right] = true;
                    continue;
                }
                if (item.o.y == _m._height && lado != Dirs.Left)
                {
                    _m._walls[item.o.x, item.o.y - 1][(int)Dirs.Up] = true;
                    continue;
                }

                Debug.Log("X: " + item.o.x + "Y: " + item.d.y);
                _m._walls[item.o.x, item.d.y][(int)lado] = true;
                _m.normalizeWall(item.o.x, item.d.y, lado);
            } //foreach

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
                if(GetDirByWall((Dirs)i) != -1*d)
                    found = !_walls[(int)p.x, (int)p.y][i];
                if(!found)
                    i++;
            }

            return GetDirByWall((Dirs)i);
        }

        //Devuelve la Dir opuesta
        static public Dirs GetOppositeWall(Dirs w)
        {
            if ((int)w % 2 == 0)
            {
                return (Dirs)((int)w + 1);
            }

            return (Dirs)((int)w - 1);
        }

        //Dada una Dir se devuelve el vector unitario
        static public Vector2 GetDirByWall(Dirs w)
        {
            int p = 1;
            if (w == Dirs.Left || w == Dirs.Down)
                p = -1;
            int x = 0;
            int y = 0;
            if ((int)w <= 1)
                y = 1;
            else
                x = 1;

            return new Vector2(p * x, p * y);
        }

        static public Map GetMap()
        {
            return _m;
        }

        /// <summary>
        /// Dada una direccion unitaria [0, 1], [0, -1], [-1, 0], [1, 0]
        /// devuelve la direccion en el enum
        /// </summary>
        /// <param name="v"> Vector de direccion </param>
        /// <returns> Direccion a la que pertenece en el enum </returns>
        static public Dirs GetWallByDir(Vector2 v)
        {
            if (v.x == 0 && v.y == 0)
                return Dirs.Neutral;

            Dirs dir = Dirs.Down;
            if (v.x == 1)
                dir = Dirs.Right;
            else if(v.x == -1)
                dir = Dirs.Left;
            else if(v.y == 1)
                dir = Dirs.Up;       

            return dir;
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

        private static Map _m;

    }
}
