using UnityEngine;

namespace MazesAndMore
{
    public class BoardManager : MonoBehaviour
    {

        public Tile tilePrefab; // Prefab del tile 
        public Camera cam; // Camara
        public Move player; // Prefab del jugador
        public int hintsNumber = 3; // Numero maximo de pistas

        private int hints = 0; // Pistas pedidas

        private Move _p; // Instancia del juegador

        private Map _map; // Mapa del juego

        int width;
        int height;

        private Vector2 resolution; //Guardado de la resolución de la pantalla

        private void Awake()
        {
            resolution = new Vector2(Screen.width, Screen.height);
            _map = new Map();
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

        public Map GetMap()
        {
            return _map;
        }

        /// <summary>
        /// Creacion de tiles, player y hielos (si toca)
        /// </summary>
        /// <param name="lvltext"> numero del nivel </param>
        public void SetMap(string lvltext)
        {
            _map.FromJson(lvltext);

            _tiles = new Tile[_map.GetWidth(), _map.GetHeight()];
            for (int i = 0; i < _map.GetHeight(); i++)
            {
                for (int j = 0; j < _map.GetWidth(); j++)
                {
                    _tiles[j, i] = Instantiate(tilePrefab, new Vector2(j, i), Quaternion.identity, gameObject.transform);
                    for (int k = 0; k < 4; k++)
                    {
                        if (_map.GetWall(new Vector2Int(j, i), (Dirs)k))
                            _tiles[j, i].EnableWall((Dirs)k);
                    }
                }
            }

            //Activa el final de casilla
            _tiles[_map.GetFin().x, _map.GetFin().y].EnableFin(_levelManager.GetLevelColor());

            //Instancia del player e inicializacion
            _p = Instantiate(player, new Vector2(_map.GetIni().x, _map.GetIni().y), Quaternion.identity, gameObject.transform);
            _p.Init(_levelManager.GetLevelColor(), _map.GetFin(), this);

            //Guardado de alto y ancho de la pantalla
            height = _map.GetHeight();
            width = _map.GetWidth();

            // Activamos los tiles con ice
            foreach (Vector2 item in _map.GetIce())
            {
                _tiles[(int)item.x, (int)item.y].EnableIce();
            }

            AdjustResolution();
        }

        /// <summary>
        /// Ajuste de la resolucion
        /// </summary>
        private void AdjustResolution()
        {
            ResetScale();
            float r, scale;
            r = cam.pixelWidth / (float)cam.pixelHeight; // Relacion de ancho y alto


            scale = ((cam.orthographicSize - 0.01f) * 2 * r) / width;

            if (r > 3 / 5.0f)
                scale = ((cam.orthographicSize - 1.01f) * 2) / height;

            gameObject.transform.localScale = new Vector3(scale, scale, 1);
            gameObject.transform.position = new Vector3(-scale * ((width / 2.0f) - 0.5f), -scale * ((height / 2.0f) - 0.5f), 0);
        }

        /// <summary>
        /// Reinicio de la escala
        /// </summary>
        private void ResetScale()
        {
            gameObject.transform.localScale = new Vector3(1, 1, 1);
            gameObject.transform.position = new Vector3(0, 0, 0);
        }

        /// <summary>
        /// Inicialización del BoardManager
        /// </summary>
        /// <param name="lvl"> Instancia del LevelManager </param>
        public void Init(LevelManager lvl)
        {
            _levelManager = lvl;
        }

        /// <summary>
        /// Limpieza del mapa
        /// </summary>
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
        /// <param name="c"> Color del camino </param>
        /// <param name="active"> Boleano para activarlo o desactivarlo </param>
        public void EnablePath(Vector2 p, Dirs d, Color c, bool active)
        {
            if (d != Dirs.Neutral)
            {
                if (active)
                    _tiles[(int)p.x, (int)p.y].EnablePath(d, c);
                else
                    _tiles[(int)p.x, (int)p.y].DisablePath(d);
            }
        }

        /// <summary>
        /// Pregunta si un tile es de hielo
        /// </summary>
        /// <param name="p"> Posición del tile </param>
        /// <returns> Respuesta de si el tile es de hielo </returns>
        public bool IsIce(Vector2 p)
        {
            return _tiles[(int)p.x, (int)p.y].IsIce();
        }

        /// <summary>
        /// Enseña el camino al canjear una nueva pista
        /// </summary>
        public void NewHint()
        {
            if (hints < hintsNumber)
            {
                Vector2[] h = _map.GetHints();

                int ini = hints * h.Length / hintsNumber;
                int fin = (hints + 1) * h.Length / hintsNumber;

                //La primera pista necesita empezar en el inicio
                if (hints == 0)
                {
                    _tiles[_map.GetIni().x, _map.GetIni().y].EnableHint(-Vector2.one, h[0]);
                    _tiles[(int)h[0].x, (int)h[0].y].EnableHint(_map.GetIni(), h[1]);
                    ini++;
                }

                //La ultima pista tiene que terminar correctamente en el final
                if (hints == hintsNumber - 1)
                {
                    _tiles[(int)h[h.Length - 1].x, (int)h[h.Length - 1].y].EnableHint(h[h.Length - 2], _map.GetFin());
                    _tiles[_map.GetFin().x, _map.GetFin().y].EnableHint(h[h.Length - 1], -Vector2.one);
                    fin--;
                }

                //Activación de las pistas entre medias
                for (int j = ini; j < fin; j++)
                {
                    _tiles[(int)h[j].x, (int)h[j].y].EnableHint(h[j - 1], h[j + 1]);
                }

                hints++;
            }
        }

        private Tile[,] _tiles; // Mapa de todos los tiles

        private LevelManager _levelManager; // Instancia del LevelManager
    }
}
