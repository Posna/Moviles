using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    [System.Serializable]
    public class SaveFile //Serializado de nivel y hash para la comprobacion
    {
        public int hash;
        public string pack;
    }

    [System.Serializable]
    public class PackageSave //Serializacion del estado
    {
        public int[] p;
        public int h;        
    }
}
