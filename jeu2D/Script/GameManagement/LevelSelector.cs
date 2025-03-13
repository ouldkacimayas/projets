using UnityEngine;
using UnityEngine.SceneManagement;
using UnityEngine.UI;

public class LevelSelector : MonoBehaviour
{
    public Button[] buttons;
    private int levelReached;

    private void Awake()
    {
        levelReached = PlayerPrefs.GetInt("levelReached", 1);
    }

    private void Start()
    {
        for (int i = 0; i < buttons.Length; i++)
        {
            if(i+1 > levelReached)
                buttons[i].interactable = false;
        }
    }
    public void loadLevel(string levelName)
    {
        SceneManager.LoadScene(levelName);
    }
}
