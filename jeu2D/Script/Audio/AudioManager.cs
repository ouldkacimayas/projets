using UnityEngine;
using UnityEngine.Audio;

public class AudioManager : MonoBehaviour
{
    private int musicIndex = 0;
    public bool isOver = true;
    public AudioClip[] playlist;
    public AudioSource audioSource;
    public AudioMixerGroup mixerGroup;
    public static AudioManager instance;

    private void Awake()
    {
        if (instance != null)
        {
            Debug.LogWarning("Attention il y'a plus d'une instance de AudioManager dans la scéne!");
        }
        instance = this;
    }

    void Start()
    {
        audioSource.clip = playlist[0];
        audioSource.Play();
    }

    void Update()
    {
        if ((PlayerHealth.instance.currentHealth <= 0) && isOver)
        {
            musicIndex = (musicIndex + 1) % playlist.Length;
            playNextMusic(musicIndex);
            isOver = false;
        }

        if (!audioSource.isPlaying && (PlayerHealth.instance.currentHealth > 0))
        {
            playNextMusic(musicIndex);
        }
    }

    void playNextMusic(int musicIndex)
    {
        audioSource.clip = playlist[musicIndex];
        audioSource.Play();
    }

    //Creation d'un objet temporaire pour jouer le son 
    public void playAudioAt(AudioClip clip, Vector3 pos, float time)
    {
        //Creation de l'objet et initialisation de sa position
        GameObject tempGo = new GameObject("TempAudio");
        tempGo.transform.position = pos;

        //Ajout d'un composant son a l'objet crée et la modification de ses parametre
        AudioSource audioSource = tempGo.AddComponent<AudioSource>();
        //Son a jouer
        audioSource.clip = clip;
        //Mixeur de son
        audioSource.outputAudioMixerGroup = mixerGroup;
        audioSource.Play();

        //Destruction de l'objet apres un laps de temp
        Destroy(tempGo, time);
    }
}
