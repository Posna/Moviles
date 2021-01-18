using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Advertisements;

namespace MazesAndMore
{
    public class AdsManager : MonoBehaviour, IUnityAdsListener
    {

        string Android_ID = "3978709";
        bool TestMode = true;

        string myPlacementId = "rewardedVideo";

        // Start is called before the first frame update
        void Start()
        {
            Advertisement.AddListener(this);
            Advertisement.Initialize(Android_ID, TestMode);
        }

        public void DispayAD()
        {
            // Check if UnityAds ready before calling Show method:
            if (Advertisement.IsReady())
            {
                Advertisement.Show();
            }
            else
            {
                Debug.Log("Interstitial ad not ready at the moment! Please try again later!");
            }
        }

        public void ShowRewardedVideo()
        {
            // Check if UnityAds ready before calling Show method:
            if (Advertisement.IsReady(myPlacementId))
            {
                Advertisement.Show(myPlacementId);
            }
            else
            {
                Debug.Log("Rewarded video is not ready at the moment! Please try again later!");
            }
        }

        // Implement IUnityAdsListener interface methods:
        public void OnUnityAdsDidFinish(string placementId, ShowResult showResult)
        {
            // Define conditional logic for each ad completion status:
            if (showResult == ShowResult.Finished)
            {
                GameManager.AddHints(1);
            }
            else if (showResult == ShowResult.Skipped)
            {
                // Do not reward the user for skipping the ad.
            }
            else if (showResult == ShowResult.Failed)
            {
                Debug.LogWarning("The ad did not finish due to an error.");
            }
        }

        public void OnUnityAdsReady(string placementId)
        {
            // If the ready Placement is rewarded, show the ad:
            if (placementId == myPlacementId)
            {
                // Optional actions to take when the placement becomes ready(For example, enable the rewarded ads button)
            }
        }

        public void OnUnityAdsDidError(string message)
        {
            // Log the error.
        }

        public void OnUnityAdsDidStart(string placementId)
        {
            // Optional actions to take when the end-users triggers an ad.
        }

        // When the object that subscribes to ad events is destroyed, remove the listener:
        public void OnDestroy()
        {
            Advertisement.RemoveListener(this);
        }

        void Awake()
        {
            if (!_ADInstance)
            {
                _ADInstance = this;
                DontDestroyOnLoad(gameObject);
            }
            else
            {
                Destroy(gameObject);
            }
        }



        public static AdsManager _ADInstance { get; private set; }
    }
}