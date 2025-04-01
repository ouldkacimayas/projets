using UnityEngine;
using System.Collections;

public class DeathZone : MonoBehaviour
{
    private Animator fadeSystem;

    private void Awake()
    {
        fadeSystem = GameObject.FindGameObjectWithTag("FadeSystem").GetComponent<Animator>();
    }
    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.CompareTag("Player"))
        {
            StartCoroutine(reSpawn(collision));
        }
    }

    private IEnumerator reSpawn(Collider2D collision)
    {
        fadeSystem.SetTrigger("FadeIn");
        yield return new WaitForSeconds(1f);
        PlayerHealth.instance.takeDammage(25);
        collision.transform.position = CurrentSceneManager.instance.spawnPos;
    }

}
