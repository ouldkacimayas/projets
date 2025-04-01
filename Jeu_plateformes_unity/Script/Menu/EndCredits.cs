using UnityEngine;
using UnityEngine.SceneManagement;

public class EndCredits : MonoBehaviour
{
    public void loadMainMenu()
    {
        SceneManager.LoadScene("MainMenu");
    }

    private void Update()
    {
        if(Input.GetKeyDown(KeyCode.Escape))
        {
            loadMainMenu();
        }
    }
}
