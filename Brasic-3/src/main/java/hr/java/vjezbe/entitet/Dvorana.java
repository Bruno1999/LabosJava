package hr.java.vjezbe.entitet;


/**
 * Klasa tipa record zvana Dvorana, Ova klasa sluzi samo za jednom spremiti podatak koji kasnije sluzi samo za citanje
 * Ova klasa ne mora imati ni konstruktor ni getter (jer inace ima defaultni)
 * @param nazivDvorane predaje se nazivDvorane tipa String
 * @param nazivZgrade predaje se nazivZgrade tipa String
 */
public record Dvorana(String nazivDvorane,String nazivZgrade) {
    /**
     * Konstruktor za klasu Dvorana
     * @param nazivDvorane predaje se nazivDvorane tipa String
     * @param nazivZgrade predaje se nazivZgrade tipa String
     */
    public Dvorana(String nazivDvorane, String nazivZgrade) {
        this.nazivDvorane = nazivDvorane;
        this.nazivZgrade = nazivZgrade;
    }
}
