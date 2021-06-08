using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    public class Utility : MonoBehaviour
    {
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
            else if (v.x == -1)
                dir = Dirs.Left;
            else if (v.y == 1)
                dir = Dirs.Up;

            return dir;
        }
    }
}