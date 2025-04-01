using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class PauseMenu : MonoBehaviour
{
    private bool isPaused = false;
    public GameObject pausePannel;
    public GameObject settingsPannel;
    public AudioClip clip;

    private void Awake()
    {
        pausePannel.SetActive(false);
    }

    void Update()
    {
        if(Input.GetKeyDown(KeyCode.Escape))
        {
            if(isPaused)
            {
                resume();
            }
            else
            {
                pause();
            }
        }
    }

    void pause()
    {
        PlayerMovement.instance.enabled = false;
        pausePannel.SetActive(true);
        Time.timeScale = 0;
        isPaused = true;
    }

    public void resume()
    {
        playSound();
        PlayerMovement.instance.enabled = true;
        pausePannel.SetActive(false);
        Time.timeScale = 1;
        isPaused = false;
    }

    public void settings()
    {
        playSound();
        settingsPannel.SetActive(true);
    }

    public void mainMenu()
    {
        playSound();
        resume();
        SceneManager.LoadScene("MainMenu");
    }

    private void playSound()
    {
        AudioManager.instance.playAudioAt(clip, transform.position, 0.3f);
    }
}
