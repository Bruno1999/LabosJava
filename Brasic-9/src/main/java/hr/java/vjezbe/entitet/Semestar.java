package hr.java.vjezbe.entitet;

public enum Semestar {
    PRAZNO(0,"prazno"),
    ZIMSKI(1, "zimski"),
    LJETNI(2, "ljetni"),
    ;

    private final Integer brojSemestra;
    private final String nazivSemestra;

    Semestar(Integer brojSemestra, String nazivSemestra) {
        this.brojSemestra = brojSemestra;
        this.nazivSemestra = nazivSemestra;
    }

    public Integer getBrojSemestra() {
        return brojSemestra;
    }

    public String getNazivSemestra() {
        return nazivSemestra;
    }
}
