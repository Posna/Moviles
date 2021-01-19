using System.Collections;
using System.Collections.Generic;
using UnityEngine;

namespace MazesAndMore
{
    [System.Serializable]
    public class SaveFile
    {
        public int hash;
        public string pack;
    }

    [System.Serializable]
    public class PackageSave
    {
        public int[] p;
        public int h;

        //public override int GetHashCode()
        //{
        //    unchecked
        //    {
        //        int result = 0;
        //        int i = 1;
        //        foreach (int item in p)
        //        {
        //            result += 397^item * i;
        //            i++;
        //        }
        //        //result = (result * 397) ^ (Street != null ? Street.GetHashCode() : 0);
        //        result = (result * 397) ^ h;
        //        return result;
        //    }
        //}
        
    }
}
