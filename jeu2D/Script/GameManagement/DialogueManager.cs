using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class DialogueManager : MonoBehaviour
{
    public Text nameNPC;
    public Text dialogueNPC;
    public static DialogueManager instance;
    public bool endDialogue;
    private Queue<string> sentences;

    private void Awake()
    {
        if(instance != null)
        {
            Debug.Log("Attention il existe plus d'une instance de DialogueManager dans la scéne!");
        }

        instance = this;

        sentences = new Queue<string>();
        endDialogue = false;
    }

    public void startDialogue(Dialogue dialogue)
    {
        nameNPC.text = dialogue.name;

        sentences.Clear();

        foreach (string sentence in dialogue.sentences)
        {
            sentences.Enqueue(sentence);
        }

        loadDialogue();
    }

    public void loadDialogue()
    {
        if(sentences.Count == 0)
        {
            endDialogue = true;
            return;
        }

        string sentence = sentences.Dequeue();
        StopAllCoroutines();
        StartCoroutine(typeText(sentence));
    }

    public IEnumerator typeText(string sentence)
    {
        dialogueNPC.text = "";

        foreach (char letter in sentence.ToCharArray())
        {
            dialogueNPC.text += letter;
            yield return new WaitForSeconds(0.01f);
        }
    }
}
