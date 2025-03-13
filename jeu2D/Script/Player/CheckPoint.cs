using UnityEngine;

public class CheckPoint : MonoBehaviour
{
    public AudioClip clip;
    private void OnTriggerEnter2D(Collider2D collision)
    {
        if(collision.CompareTag("Player"))
        {
            CurrentSceneManager.instance.spawnPos = transform.position;
            transform.GetComponent<BoxCollider2D>().enabled = false;

            AudioManager.instance.playAudioAt(clip, transform.position, 1.5f);
        }
    }
}
