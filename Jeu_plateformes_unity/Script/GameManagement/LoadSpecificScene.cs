using UnityEngine;
using UnityEngine.SceneManagement;
using System.Collections;

public class LoadSpecificScene : MonoBehaviour
{
    public string SceneName;
    public bool theEnd;
    public AudioClip sound;
    private Animator FadeSystem;
    private GameObject player;
    public static LoadSpecificScene instance;

    private void Awake()
    {
        if (instance != null)
        {
            Debug.LogWarning("Attention il y'a plus d'une instance de LoadSpecificScene dans la scéne!");
        }
        instance = this;

        FadeSystem = GameObject.FindGameObjectWithTag("FadeSystem").GetComponent<Animator>();
        player = GameObject.FindGameObjectWithTag("Player");
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if(collision.CompareTag("Player"))
        {
            StartCoroutine(changeScene());  
        }
    }

    private IEnumerator changeScene()
    {
        PlayerMovement.instance.enabled = false;
        PlayerMovement.instance.player1.velocity = Vector3.zero;
        PlayerMovement.instance.animator.SetFloat("Speed", -10f);
        SaveAndLoadGameData.instance.saveData();
        AudioManager.instance.playAudioAt(sound, player.transform.position, 4f);
        if(theEnd)
            yield return new WaitForSeconds(1.2f);
        FadeSystem.SetTrigger("FadeIn");
        yield return new WaitForSeconds(1.2f);
        PlayerMovement.instance.enabled = true;
        SceneManager.LoadScene(SceneName);
    }
}
