using UnityEngine;

public class CameraFollow : MonoBehaviour
{
    public Transform player1;
    public float timeOffset;
    public Vector3 posOffset;

    private Vector3 velocity = Vector3.zero;
    private Transform playerPos;

    void Update()
    {
        //Deplacement fluide de la camera en fonction du personnage avec un decalage du temps
        transform.position = Vector3.SmoothDamp(transform.position, player1.position + posOffset,
            ref velocity, timeOffset);
    }
}
