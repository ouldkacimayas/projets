using UnityEngine;
using UnityEngine.UI;

public class ShopManager : MonoBehaviour
{
    public GameObject shop;
    public Text npcName;
    public GameObject itemButton;
    public Transform itemsPannel;
    public static ShopManager instance;

    private void Awake()
    {
        if (instance != null)
        {
            Debug.Log("Attention il existe plus d'une instance de ShopManager dans la scéne!");
        }

        instance = this;
        shop.SetActive(false);
    }
    
    public void openShop(ItemData[] items, string name)
    {
        shop.SetActive(true);
        npcName.text = name;
        createButtons(items);
    }

    void createButtons(ItemData[] items)
    {
        //Supprimer les potentiels bouton présent dans le shop
        for (int i = 0; i < itemsPannel.childCount; i++)
        {
            Destroy(itemsPannel.GetChild(i).gameObject);
        }

        //Instancier un bouton pour chaque items à vendre et le configurer
        for (int i = 0; i < items.Length; i++)
        {
            GameObject button = Instantiate(itemButton, itemsPannel);
            SellButtonItem buttonScript = button.GetComponent<SellButtonItem>();
            buttonScript.itemName.text = items[i].name;
            buttonScript.itemImage.sprite = items[i].image;
            buttonScript.itemCost.text = items[i].Price.ToString();
            buttonScript.item = items[i];
        }
    }

    public void closeShop()
    {
        shop.SetActive(false);
    }
}
