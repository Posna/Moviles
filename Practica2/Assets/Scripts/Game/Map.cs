using UnityEngine;
using System;

namespace MazesAndMore
{
    public class Map
    {   
        //Auxiliar para desserializar el mapa
        [Serializable]
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
        [Serializable]
        public class WallsOD
        {
            public Vector2Int o;
            public Vector2Int d;
        }

        //Auxiliar para deserializar las pistas
        [Serializable]
        public class MyVector2
        {
            public int x = 0;
            public int y = 0;
        }

        /// <summary>
        /// Lectura del JSON
        /// </summary>
        /// <param name="json"> Nombre del JSON</param>
        public void FromJson(string json)
        {
            // Desserializacion del JSON
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

        /// <summary>
        /// Contruccion de los muros
        /// </summary>
        /// <param name="mapaAux"> Mapa del que se extraen los datos </param>
        private void BuildMap(MapaSerializable mapaAux)
        {
            // Recorre todos los muros
            foreach (WallsOD item in mapaAux.w)
            {
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

        /// <summary>
        /// Inicializacion por defecto del mapa
        /// </summary>
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

        /// <summary>
        /// Pone los muros a true de las casillas contiguas
        /// </summary>
        /// <param name="x"> Posicion X del muro </param>
        /// <param name="y"> Posicion Y del muro </param>
        /// <param name="lado"> Lado del tile en el que se encuentra el muro </param>
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
                
        /// <returns> Devuelve el ancho del mapa </returns>
        public int GetWidth()
        {
            return _width;
        }

        /// <returns> Devuelve el alto del mapa </returns>
        public int GetHeight()
        {
            return _height;
        }

        /// <summary>
        /// Obtenemos si hay muro en una posicion y direccion concreta
        /// </summary>
        /// <param name="p"> Posicion del tile </param>
        /// <param name="w"> Lado del muro </param>
        /// <returns> true si hay muro, false en el caso contrario</returns>
        public bool GetWall(Vector2 p, Dirs w)
        {
            if (w == Dirs.Neutral)
                return true;
            return _walls[(int)p.x, (int)p.y][(int)w];
        }

        /// <returns> Devuelve la posicion inicial </returns>
        public Vector2Int GetIni()
        {
            return _ini;
        }

        /// <returns> Devuelve la posicion final </returns>
        public Vector2Int GetFin()
        {
            return _fin;
        }

        /// <summary>
        /// Comprueba cuantas direcciones hay disponibles en una casilla concreta
        /// </summary>
        /// <param name="p"> Posicion del tile </param>
        /// <returns> Numero de direcciones posibles </returns>
        public int GetNDirs(Vector2 p)
        {
            int i = 0;

            for (int w = 0; w < 4;w++)
            {
                i += Convert.ToInt32(_walls[(int)p.x, (int)p.y][w]);
            }

            return 4 - i ;
        }

        
        /// <returns> Devuelve un vector con la posición de todos los tiles con hielo </returns>
        public Vector2[] GetIce()
        {
            return _ice;
        }

        /// <summary>
        /// Devuelve la primera direccion que no sea la dada (d)
        /// </summary>
        /// <param name="p"> Posicion del tile </param>
        /// <param name="d"> Direccion a evitar </param>
        /// <returns> Dirección posible distinta de d </returns>
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

        /// <returns> Devuelve la posición de todas las pistas </returns>
        public Vector2[] GetHints()
        {
            return _hints;
        }
        
        private bool[,][] _walls; // Muros de todo el mapa 

        private Vector2Int _ini; // Posicion inicial
        private Vector2Int _fin; // Posicion final

        private Vector2[] _hints; // Posicion de las pistas
        private Vector2[] _ice; // Posiciones de los tiles con hielo

        private int _width; // Ancho del mapa
        private int _height; // Alto del mapa

    }
}
