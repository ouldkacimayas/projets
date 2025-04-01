using UnityEngine;

public class WeakSpot : MonoBehaviour
{
    public GameObject snake;
    public AudioClip sound;
    private void OnTriggerEnter2D(Collider2D collision)
    {
        //Si le personnage saute sur la tete de l'ennemie, ce dernier meure
        if(collision.CompareTag("Player"))
        {
            AudioManager.instance.playAudioAt(sound, transform.position, 0.5f);
            Destroy(snake);
        }
    }
}
