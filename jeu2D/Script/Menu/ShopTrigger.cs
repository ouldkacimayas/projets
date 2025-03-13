using UnityEngine;
using UnityEngine.UI;

public class ShopTrigger : MonoBehaviour
{
    public Text interaction;
    public string npcName;
    public ItemData[] itemsToSell;
    private bool inRange;
    private bool isOpen;

    private void Awake()
    {
        inRange = false;
        isOpen = false;
        interaction.enabled = false;
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.CompareTag("Player"))
        {
            interaction.enabled = true;
            inRange = true;
        }
    }

    private void OnTriggerExit2D(Collider2D collision)
    {
        if (collision.CompareTag("Player"))
        {
            inRange = false;
            interaction.enabled = false;
            isOpen = false;
        }

    }

    void Update()
    {
        if (inRange && Input.GetKeyDown(KeyCode.E) && !isOpen)
        {
            isOpen = true;
            ShopManager.instance.openShop(itemsToSell, npcName);
        }

        if (inRange && Input.GetKeyDown(KeyCode.E) && isOpen)
        {
            isOpen = false;
            
        }
    }
}
