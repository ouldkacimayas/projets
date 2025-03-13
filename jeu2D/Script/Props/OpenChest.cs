using UnityEngine;
using UnityEngine.UI;

public class OpenChest : MonoBehaviour
{
    public int amount = 3;
    public Animator animator;
    public AudioClip clip;

    private Text interaction;
    private bool inRange;
    private bool isOpen;

    private void Awake()
    {
        isOpen = false;
        inRange = false;

        interaction = GameObject.FindGameObjectWithTag("Interaction").GetComponent<Text>();
        interaction.enabled = false;
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if(collision.CompareTag("Player"))
        {
            inRange = true;

            if (isOpen)
            {
                interaction.enabled = false;
            }
            else
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
        if(inRange && Input.GetKeyDown(KeyCode.E) && !isOpen)
        {
            openChest();
        }        
    }

    private void openChest()
    {
        animator.SetTrigger("IsOpen");
        Inventory.instance.addCoins(amount);
        AudioManager.instance.playAudioAt(clip, transform.position, 0.4f);
        interaction.enabled = false;
        isOpen = true;
    }
}
