using UnityEngine;
using UnityEngine.UI;

public class ClimbLadder : MonoBehaviour
{
    private bool isInRange;
    private PlayerMovement playerMovement;
    public Collider2D platforme;
    private Rigidbody2D gravity;
    private Text interaction;

    private void Awake()
    {
        playerMovement = GameObject.FindGameObjectWithTag("Player").GetComponent<PlayerMovement>();
        gravity = playerMovement.GetComponentInParent<Rigidbody2D>();
        interaction = GameObject.FindGameObjectWithTag("Interaction").GetComponent<Text>();
        interaction.enabled = false;
    }

    private void Update()
    {
        if(isInRange && playerMovement.isClimbing && Input.GetKeyDown(KeyCode.E))
        {
            playerMovement.isClimbing = false;
            gravity.gravityScale = 1;
            platforme.isTrigger = false;
            return;
        }

        if (isInRange && Input.GetKeyDown(KeyCode.E))
        {
            platforme.isTrigger = true;
            playerMovement.isClimbing = true;
            gravity.gravityScale = 0;
            interaction.enabled = false;
        }
     }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if(collision.CompareTag("Player"))
        {
            isInRange = true;
            interaction.enabled = true;
        }
    }

    private void OnTriggerExit2D(Collider2D collision)
    {
        if (collision.CompareTag("Player"))
        {
            isInRange = false;
            platforme.isTrigger = false;
            playerMovement.isClimbing = false;
            gravity.gravityScale = 1;
            interaction.enabled = false;
        }
    }
}
