using UnityEngine;

public class RegenerateLife : MonoBehaviour
{
    public int life = 20;
    public AudioClip sound;
    private void OnTriggerEnter2D(Collider2D collision)
    {
        if(collision.CompareTag("Player"))
        {
            if(PlayerHealth.instance.currentHealth < PlayerHealth.instance.maxHealth)
            {
                AudioManager.instance.playAudioAt(sound, transform.position, 0.6f);
                PlayerHealth.instance.regenerateLife(life);
                Destroy(gameObject);
            }
        }
    }
}
