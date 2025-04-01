using UnityEngine;

public class InventoryDataBase : MonoBehaviour
{
    public ItemData[] allItems; 
    public static InventoryDataBase instance;

    public void Awake()
    {
        if(instance != null)
        {
            Debug.Log("Attention il existe plus d'une instance de InventoryDataBase dans la scene!");
        }

        instance = this;
    }
}
