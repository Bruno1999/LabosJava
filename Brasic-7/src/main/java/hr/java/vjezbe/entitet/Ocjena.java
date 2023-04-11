package hr.java.vjezbe.entitet;

import java.math.BigDecimal;

/**
 * Enumeracija svih dozvoljenih vrijednosti ocjena
 */

public enum Ocjena {
    PRAZNO(0,"prazno"),
    NEDOVOLJAN(1, "nedovoljan"),
    DOVOLJAN(2, "dovoljan"),
    DOBAR(3, "dobar"),
    VRLO_DOBAR(4, "vrlo dobar"),
    IZVRSTAN(5, "izvrstan");

    private final Integer numerickaVrijednost;
    private final String opis;

    Ocjena(Integer numerickaVrijednost, String opis) {
        this.numerickaVrijednost = numerickaVrijednost;
        this.opis = opis;
    }

    public Integer getNumerickaVrijednost() {
        return numerickaVrijednost;
    }

    public String getOpis() {
        return opis;
    }

    /**
     * Pretvara ocjenu u {@link BigDecimal}
     * @return numericka vrijednost ocjene kao {@link BigDecimal}
     */
    public BigDecimal toBigDecimal(){
        return new BigDecimal(numerickaVrijednost);
    }
}
