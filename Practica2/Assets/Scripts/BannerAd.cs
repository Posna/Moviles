using System.Collections;
using UnityEngine;
using UnityEngine.Advertisements;

public class BannerAd : MonoBehaviour
{
    public string gameId = "3978709";
    public string placementId = "bannerPlacement";
    public bool testMode = true;

    void Start()
    {
        Advertisement.Initialize(gameId, testMode);
        StartCoroutine(ShowBannerWhenInitialized());
    }

    /// <summary>
    /// Muestra el banner
    /// </summary>
    IEnumerator ShowBannerWhenInitialized()
    {
        while (!Advertisement.isInitialized)
        {
            yield return new WaitForSeconds(0.5f);
        }
        Advertisement.Banner.SetPosition(BannerPosition.BOTTOM_CENTER);
        Advertisement.Banner.Show(placementId);
    }
}
