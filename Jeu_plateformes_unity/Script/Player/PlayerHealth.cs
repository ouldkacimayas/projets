using UnityEngine;
using System.Collections;

public class PlayerHealth : MonoBehaviour
{
    public float invincibilityTimeAfterHit = 3f;
    public float invincibilityFlashDelay = 0.1f;
    private bool isInvincible = false;
    public int maxHealth = 100;
    public int currentHealth;

    public AudioClip clip;
    public HealthBar healthBar;
    public SpriteRenderer graphics;
    public AudioClip sound;
    public GameObject player;
    public Collider2D playerCollider;
    public static PlayerHealth instance;

    private void Awake()
    {
        if (instance != null)
        {
            Debug.LogWarning("Attention il y'a plus d'une instance de PlayerHealth dans la scéne!");
        }
        instance = this;
    }

    private void Start()
    {
        currentHealth = maxHealth;
        healthBar.setMaxHealth(maxHealth);
    }

    private void Update()
    {
        if (Input.GetKeyDown(KeyCode.H))
            takeDammage(20);

        if(GameOver.instance.respawn)
        {
            playersRespawn();
            GameOver.instance.respawn = false;
        }
    }

    public void takeDammage(int dammage)
    {
        if (!isInvincible && currentHealth > 0)
        {
            AudioManager.instance.playAudioAt(sound, transform.position, 0.3f);
            currentHealth -= dammage;
            healthBar.setHealth(currentHealth);

            if (currentHealth <= 0)
            {
                StartCoroutine(playersDeath());
                return;
            }

            isInvincible = true;
            StartCoroutine(invincibilityFlash());
            StartCoroutine(invincibilityDelay());
        }
    }

    public void regenerateLife(int health)
    {
        if(currentHealth + health > maxHealth)
        {
            currentHealth = maxHealth;
        }
        else
        {
            currentHealth += health;
        }

        healthBar.setHealth(currentHealth);
    }

    public IEnumerator invincibilityFlash()
    {
        while(isInvincible)
        {
            graphics.color = new Color(1f, 1f, 1f, 0f);
            yield return new WaitForSeconds(invincibilityFlashDelay);
            graphics.color = new Color(1f, 1f, 1f, 1f);
            yield return new WaitForSeconds(invincibilityFlashDelay);
        }
    }

    public IEnumerator invincibilityDelay()
    {
        yield return new WaitForSeconds(invincibilityTimeAfterHit);
        isInvincible = false;
    }

    public IEnumerator playersDeath()
    {
        PlayerMovement.instance.enabled = false;
        PlayerMovement.instance.animator.SetTrigger("IsDead");
        PlayerMovement.instance.player1.bodyType = RigidbodyType2D.Kinematic;
        playerCollider.enabled = false;
        PlayerMovement.instance.player1.velocity = Vector3.zero;
        yield return new WaitForSeconds(0.2f);
        AudioManager.instance.playAudioAt(clip, transform.position, 3f);
        yield return new WaitForSeconds(0.4f);
        GameOver.instance.onPlayerDeath();
    }

    public void playersRespawn()
    {
        PlayerMovement.instance.enabled = true;
        PlayerMovement.instance.animator.SetTrigger("Respawn");
        PlayerMovement.instance.player1.bodyType = RigidbodyType2D.Dynamic;
        playerCollider.enabled = true;
        currentHealth = maxHealth;
        healthBar.setHealth(currentHealth);
        AudioManager.instance.isOver = true;
    }
}
