using UnityEngine;
using System.Linq;

public class SaveAndLoadGameData : MonoBehaviour
{
    public static SaveAndLoadGameData instance;

    private void Awake()
    {
        if (instance != null)
        {
            Debug.LogWarning("Attention il y'a plus d'une instance de SaveAndLoadGameData dans la scéne!");
        }
        instance = this;
    }

    void Start()
    {
        Inventory.instance.coinsCount = PlayerPrefs.GetInt("coinsCount", 0);
        Inventory.instance.setCoinsUI();

        string[] itemsId = PlayerPrefs.GetString("inventoryItems", "").Split(',');

        for (int i = 0; i < itemsId.Length; i++)
        {
            if (itemsId[i] != "")
            {
                int id = int.Parse(itemsId[i]);
                ItemData currentItem = InventoryDataBase.instance.allItems.Single(x => x.id == id);
                Inventory.instance.content.Add(currentItem);
                Inventory.instance.currentItemImage();
            }
        }
    }

    public void saveData()
    {
        PlayerPrefs.SetInt("coinsCount", Inventory.instance.coinsCount);
        if(CurrentSceneManager.instance.levelToUnlock > PlayerPrefs.GetInt("levelReached", 1))
            PlayerPrefs.SetInt("levelReached", CurrentSceneManager.instance.levelToUnlock);

        string inventoryItemId = string.Join(',', Inventory.instance.content.Select(x => x.id));
        PlayerPrefs.SetString("inventoryItems", inventoryItemId);
    }

}
