using UnityEngine.SceneManagement;
using UnityEngine;

public class MainMenu : MonoBehaviour
{
    public string levelToLoad = "Level01";
    public static MainMenu instance;
    public GameObject settings;

    private void Awake()
    {
        if (instance != null)
        {
            Debug.LogWarning("Attention il y'a plus d'une instance de MainMenu dans la scéne!");
        }
        instance = this;
    }

    public void startGame()
    {
        SceneManager.LoadScene(levelToLoad);
    }

    public void settingsButton()
    {
        settings.SetActive(true);
    }

    public void creditsButton()
    {
        SceneManager.LoadScene("Credits");
    }

    public void quitGame()
    {
        Application.Quit();
    }
}
