using UnityEngine;

public class PlayerMovement : MonoBehaviour
{
    private bool isJumping;
    private bool isGrounded;
    [HideInInspector]
    public bool isClimbing;

    public float jumpForce;
    public float moveSpeed;
    public float climbSpeed;
    public float radius;
    float horizontalMovement;
    float verticalMovement;

    public AudioClip clip;
    public Gizmos cercle;
    public Transform checkGround;
    public LayerMask collisionLayer;
    public SpriteRenderer sr;
    public Rigidbody2D player1;
    public Animator animator;
    private Vector3 velocity = Vector3.zero;
    public static PlayerMovement instance;

    private void Awake()
    {
        if (instance != null)
        {
            Debug.LogWarning("Attention il y'a plus d'une instance de PlayerMovement dans la scéne!");
        }
        instance = this;
    }

    void Update()
    {
        //Calculer la distance vertical a parcourir
        horizontalMovement = Input.GetAxis("Horizontal") * moveSpeed * Time.fixedDeltaTime;

        verticalMovement = Input.GetAxis("Vertical") * climbSpeed * Time.fixedDeltaTime;

        //Si on clique sur 'Espace' et le personnage n'est pas dans le airs il saute
        if (Input.GetButtonDown("Jump") && isGrounded)
        {
            isJumping = true;
        }

        //Changement de direction
        flip();

        float characterSpeed = Mathf.Abs(player1.velocity.x+player1.velocity.y);
        animator.SetFloat("Speed", characterSpeed);
        animator.SetBool("IsClimbing", isClimbing);
    }

    void FixedUpdate()
    {
        /*Creation d'une zone entre les 2 points droit et gauche qui detecte lorsque une entité
        la traverse(lorsque le personnage est sur une plateforme)*/
        isGrounded = Physics2D.OverlapCircle(checkGround.position, radius, collisionLayer);
        
        movePlayer(horizontalMovement, verticalMovement);    
    }

    void movePlayer(float _horizontalMovement, float _verticalMovement)
    {
        if (!isClimbing)
        {
            Vector3 targetVelocity = new Vector2(_horizontalMovement, player1.velocity.y);

            //Deplacement fluide du personnage 
            player1.velocity = Vector3.SmoothDamp(player1.velocity, targetVelocity, ref velocity, .05f);

            //Ajout d'une force phisique vers le haut en cliquant sur 'Espace'
            if (isJumping)
            {
                player1.AddForce(new Vector2(0f, jumpForce));
                isJumping = false;
                AudioManager.instance.playAudioAt(clip, transform.position, 0.1f);
            }
        }
        else
        {
            Vector3 targetVelocity = new Vector2(0, _verticalMovement);
            player1.velocity = Vector3.SmoothDamp(player1.velocity, targetVelocity, ref velocity, .05f);
        }        
    }

    void flip()
    {
        //La direction du personnage est en fonction du signe de sa velocité horizontale
        //Si + droite si - gauche  
        if (player1.velocity.x > 0.1f)
        {
            sr.flipX = false;
        }
        else if(player1.velocity.x < -0.1f)
        {
            sr.flipX = true;
        }
    }

    private void OnDrawGizmos()
    {
        Gizmos.color = Color.red;
        Gizmos.DrawWireSphere(checkGround.position, radius);
    }
}

