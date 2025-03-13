using UnityEngine;
using UnityEngine.UI;
using System.Collections.Generic;
using System.Collections;

public class Inventory : MonoBehaviour
{
    public int coinsCount;
    public Sprite emptyItemImage;
    public Text coinsCountText;
    public List<ItemData> content = new List<ItemData>();
    private int selectedItem = 0;
    public Image itemImage;
    public Text itemName;
    public static Inventory instance;

    private void Awake()
    {
        if(instance!=null)
        {
            Debug.LogWarning("Attention il y'a plus d'une instance de Inventory dans la scéne!");
        }
        instance = this;
    }

    private void Start()
    {
        currentItemImage();
    }

    public void addCoins(int count = 1)
    {
        coinsCount += count;
        setCoinsUI();
    }

    public void subCoins(int amount)
    {
        coinsCount -= amount;
        setCoinsUI();
    }

    public void setCoinsUI()
    {
        coinsCountText.text = coinsCount.ToString();
    }

    public void consumeItem()
    {
        if (content.Count == 0)
            return;

        ItemData item = content[selectedItem];

        PlayerHealth.instance.regenerateLife(item.hpGiven);
        StartCoroutine(raiseSpeedTemporary(item.speedGiven, item.speedDuration));
        StartCoroutine(raiseJumpForceTemporary(item.jumpPower, item.jumpDuration));

        content.Remove(item);
        nextItem();
        currentItemImage();
    }

    public void nextItem()
    {
        if (content.Count < 1)
            return;

        if (selectedItem > content.Count - 2)
            selectedItem = 0;
        else
            selectedItem++;

        currentItemImage();
    }

    public void previousItem()
    {
        if (content.Count < 1)
            return;

        if (selectedItem < 1)
            selectedItem = content.Count -1;
        else
            selectedItem--;

        currentItemImage();
    }

    public void currentItemImage()
    {
        if (content.Count > 0)
        {
            itemImage.sprite = content[selectedItem].image;
            itemName.text = content[selectedItem].name.ToUpper();
        }
        else
        {
            itemImage.sprite = emptyItemImage;
            itemName.text = "EMPTY";
        }
    }

    private IEnumerator raiseSpeedTemporary(float speedGiven, float duration)
    {
        PlayerMovement.instance.moveSpeed += speedGiven;
        yield return new WaitForSeconds(duration);
        PlayerMovement.instance.moveSpeed -= speedGiven;
    }

    private IEnumerator raiseJumpForceTemporary(float jumpForce, float duration)
    {
        PlayerMovement.instance.jumpForce += jumpForce;
        yield return new WaitForSeconds(duration);
        PlayerMovement.instance.jumpForce -= jumpForce;
    }
}
