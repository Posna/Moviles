using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


[CreateAssetMenu(fileName = "Data", menuName = "ScriptableObjects/LevelGroup", order = 1)]
public class LevelPackage: ScriptableObject
{
    public TextAsset[] levels; //Niveles

    public Color color; //Color del pack

    public Sprite image; // Imagen del boton que se debe instanciar en el menu

    public string packName; // Nombre del pack
}
