package hr.java.vjezbe.entitet;

/**
 * Sucelje Online koje je sealed i moze ga nasljediti samo klasa Ispit
 */
public sealed interface Online permits Ispit {
    default String getNazivSoftware(){
        return "imeSoftware";
    };
}
