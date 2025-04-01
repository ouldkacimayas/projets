using UnityEngine;
using UnityEngine.SceneManagement;
using System.Collections;

public class GameOver : MonoBehaviour
{
    public bool respawn;
    public GameObject gameOver;
    public AudioClip clip;
    public static GameOver instance;
    private Animator fadeSystem;

    private void Awake()
    {
        if (instance != null)
        {
            Debug.LogWarning("Attention il y'a plus d'une instance de GameOver dans la scéne!");
        }
        instance = this;
        gameOver.SetActive(false);
        respawn = false;
        fadeSystem = GameObject.FindGameObjectWithTag("FadeSystem").GetComponent<Animator>();
    }

    public void onPlayerDeath()
    {
        gameOver.SetActive(true);
    }

    public void retry()
    {
        playSound();
        StartCoroutine(retryTransition());        
    }

    public void mainMenu()
    {
        playSound();
        SceneManager.LoadScene("MainMenu");
    }

    public void quit()
    {
        playSound();
        Application.Quit();
    }

    public IEnumerator retryTransition()
    {
        fadeSystem.SetTrigger("FadeIn");
        yield return new WaitForSeconds(0.2f);
        gameOver.SetActive(false);
        yield return new WaitForSeconds(1f);
        SceneManager.LoadScene(SceneManager.GetActiveScene().buildIndex);
        respawn = true;
        Inventory.instance.subCoins(CurrentSceneManager.instance.coinsPickedUp); 
    }

    private void playSound()
    {
        AudioManager.instance.playAudioAt(clip, transform.position, 0.3f);
    }
}
