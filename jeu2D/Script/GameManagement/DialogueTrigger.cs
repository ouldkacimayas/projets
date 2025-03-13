using UnityEngine;
using UnityEngine.UI;

public class DialogueTrigger : MonoBehaviour
{
    public Dialogue dialogue;
    public Text interaction;
    public Animator dialoguePannel;
    private bool inRange;
    private bool isTalking;

    private void Awake()
    {
        inRange = false;
        isTalking = false;
        interaction.enabled = false;
        dialoguePannel.SetBool("isTalking", false);
    }

    private void OnTriggerEnter2D(Collider2D collision)
    {
        if(collision.CompareTag("Player"))
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
            dialoguePannel.SetBool("isTalking",false);
            interaction.enabled = false;
            isTalking = false;
        }

    }

    void Update()
    {
        if (inRange && Input.GetKeyDown(KeyCode.E) && !isTalking)
        {
            isTalking = true;
            showDialogue();
        }

        if(DialogueManager.instance.endDialogue)
        {
            dialoguePannel.SetBool("isTalking", false);
            isTalking = false;
            interaction.enabled = true;
            DialogueManager.instance.endDialogue = false;
        }
    }

    public void showDialogue()
    {
        interaction.enabled = false;
        DialogueManager.instance.startDialogue(dialogue);
        dialoguePannel.SetBool("isTalking",true);
    }
}
