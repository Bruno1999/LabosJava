package hr.java.vjezbe.entitet;

public record Dvorana(String nazivDvorane,String nazivZgrade) {

    public Dvorana(String nazivDvorane, String nazivZgrade) {
        this.nazivDvorane = nazivDvorane;
        this.nazivZgrade = nazivZgrade;
    }
}
