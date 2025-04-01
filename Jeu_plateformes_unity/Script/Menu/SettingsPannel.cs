using UnityEngine;
using UnityEngine.Audio;
using UnityEngine.UI;
using System.Collections.Generic;
using System.Linq;

public class SettingsPannel : MonoBehaviour
{
    public AudioMixer musicMixer;
    public AudioMixer soundsMixer;
    public Dropdown resolutionDropDown;
    public Slider musicSlider;
    public Slider soundSlider;
    Resolution[] resolutions;

    private void Awake()
    {
        gameObject.SetActive(false);
    }

    private void Start()
    {
        musicMixer.GetFloat("Music", out float musicValueForSlider);
        musicSlider.value = musicValueForSlider;

        soundsMixer.GetFloat("SoundsEffects", out float soundValueForSlider);
        soundSlider.value = soundValueForSlider;

        resolutions = Screen.resolutions.Select(resolution => new Resolution { width = resolution.width, height = resolution.height }).Distinct().ToArray();

        resolutionDropDown.ClearOptions();

        int currentResolutionIndex = 0;

        List<string> options = new List<string>();

        for (int i = 0; i < resolutions.Length; i++)
        {
            string option = resolutions[i].width + "x" + resolutions[i].height;
            options.Add(option);

            if(resolutions[i].width == Screen.width && resolutions[i].height == Screen.height)
            {
                currentResolutionIndex = i;
            }
        }

        resolutionDropDown.AddOptions(options);
        resolutionDropDown.value = currentResolutionIndex;
        resolutionDropDown.RefreshShownValue();

        Screen.fullScreen = true;
    }

    public void setMusic(float music)
    {
        musicMixer.SetFloat("Music", music);
    }

    public void setSoundsEffects(float sound)
    {
        soundsMixer.SetFloat("SoundsEffects", sound);
    }

    public void setFullScreen(bool isFullScreen)
    {
        Screen.fullScreen = isFullScreen;
    }

    public void setResolution(int resolutionIndex)
    {
        Resolution resolution = resolutions[resolutionIndex];

        Screen.SetResolution(resolution.width, resolution.height, Screen.fullScreen);
    }

    public void quitSettingsPannel()
    {
        gameObject.SetActive(false);
    }

    public void clearAll()
    {
        PlayerPrefs.DeleteAll();
    }
}
