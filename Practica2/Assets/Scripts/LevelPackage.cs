using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


[CreateAssetMenu(fileName = "Data", menuName = "ScriptableObjects/LevelGroup", order = 1)]
public class LevelPackage: ScriptableObject
{
    public TextAsset[] levels;

    public Color color;

    public Button intro;
}
