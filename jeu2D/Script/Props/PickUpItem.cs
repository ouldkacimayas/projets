using UnityEngine;
using UnityEngine.UI;

public class PickUpItem : MonoBehaviour
{
    public AudioClip clip;
    private Text interaction;
    public ItemData item;
    private bool inRange;

    private void Awake()
    {
        inRange = false;

        interaction = GameObject.FindGameObjectWithTag("Interaction").GetComponent<Text>();
        interaction.enabled = false;
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.CompareTag("Player"))
        {
            inRange = true;
            interaction.enabled = true;
        }
    }

    private void OnTriggerExit2D(Collider2D collision)
    {
        if (collision.CompareTag("Player"))
        {
            inRange = false;
            interaction.enabled = false;
        }
    }

    private void Update()
    {
        if (inRange && Input.GetKeyDown(KeyCode.E))
        {
            takeItem();
        }
    }

    private void takeItem()
    {
        Inventory.instance.content.Add(item);
        Inventory.instance.currentItemImage();
        AudioManager.instance.playAudioAt(clip, transform.position, 0.3f);
        interaction.enabled = false;
        Destroy(gameObject);
    }
}
