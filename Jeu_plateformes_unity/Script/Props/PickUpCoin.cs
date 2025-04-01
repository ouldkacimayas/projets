using UnityEngine;

public class PickUpCoin : MonoBehaviour
{
    public GameObject coin;
    public AudioClip sound;
    private void OnTriggerEnter2D(Collider2D collision)
    {
        if(collision.CompareTag("Player"))
        {
            AudioManager.instance.playAudioAt(sound, transform.position, 0.3f);
            Inventory.instance.addCoins(1);
            CurrentSceneManager.instance.coinsPickedUp ++;
            Destroy(coin);
        }
    }
}
