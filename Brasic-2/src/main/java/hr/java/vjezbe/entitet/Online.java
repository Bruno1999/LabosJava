package hr.java.vjezbe.entitet;

public sealed interface Online permits Ispit {
    default String getNazivSoftware(){
        return "imeSoftware";
    };
}
