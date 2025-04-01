using UnityEngine;

public class EnemyPatrol : MonoBehaviour
{
    public float moveSpeed;
    public int damage = 20;

    public SpriteRenderer srEnnemy;
    public Transform[] wayPoints;
    private Transform target;
    private int dest = 0;

    private void Start()
    {
        target = wayPoints[dest];
        srEnnemy.flipX = true;
    }

    void FixedUpdate()
    {
        //La distance entre la position actuelle de l'ennemi et le point d'arrivé
        Vector3 dir = target.position - transform.position;

        //Deplacement de l'ennemi vers le points voulu tel que (dir.normalized = 0|1) est la direction
        transform.Translate(moveSpeed * Time.deltaTime * dir.normalized, Space.World);

        //Si l'ennemie atteint le point d'arrivé ce point change et l'ennemie change de direction
        //vers ce point
        if (Vector3.Distance(transform.position, target.position) < 0.3f)
        {
            dest = (dest + 1) % wayPoints.Length;
            target = wayPoints[dest];
            srEnnemy.flipX = !srEnnemy.flipX;
        }
    }

    public void OnCollisionEnter2D(Collision2D collision)
    {
        if(collision.transform.CompareTag("Player"))
        {
            PlayerHealth.instance.takeDammage(damage);
        }
    }
}
