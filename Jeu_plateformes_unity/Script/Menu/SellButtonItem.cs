using UnityEngine;
using UnityEngine.UI;

public class SellButtonItem : MonoBehaviour
{
    public Image itemImage;
    public Text itemName;
    public Text itemCost;
    public ItemData item;

    public void buyItem()
    {
        Inventory inventory = Inventory.instance;

        if(inventory.coinsCount >= item.Price)
        {
            inventory.content.Add(item);
            inventory.currentItemImage();
            inventory.subCoins(item.Price);
        }

    }
}
