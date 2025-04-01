using UnityEngine;

public class CurrentSceneManager : MonoBehaviour
{
    public int coinsPickedUp;
    public int levelToUnlock;
    public static CurrentSceneManager instance;
    public Vector3 spawnPos;

    private void Awake()
    {
        if (instance != null)
        {
            Debug.LogWarning("Attention il y'a plus d'une instance de CurrentSceneManager dans la scéne!");
        }
        instance = this;
        coinsPickedUp = 0;

        spawnPos = GameObject.FindGameObjectWithTag("Player").transform.position;
    }
}
