package hr.java.vjezbe.entitet;

import java.time.LocalDateTime;

public class Ispit {

    private Predmet naziv;
    private Student ime;
    private Integer ocjena;
    private LocalDateTime datumIVrijeme;

    public Ispit(Predmet naziv, Student ime, Integer ocjena, LocalDateTime datumIVrijeme) {
        this.naziv = naziv;
        this.ime = ime;
        this.ocjena = ocjena;
        this.datumIVrijeme = datumIVrijeme;
    }

    public Predmet getNaziv() {
        return naziv;
    }

    public void setNaziv(Predmet naziv) {
        this.naziv = naziv;
    }

    public Student getIme() {
        return ime;
    }

    public void setIme(Student ime) {
        this.ime = ime;
    }

    public Integer getOcjena() {
        return ocjena;
    }

    public void setOcjena(Integer ocjena) {
        this.ocjena = ocjena;
    }

    public LocalDateTime getDatumIVrijeme() {
        return datumIVrijeme;
    }

    public void setDatumIVrijeme(LocalDateTime datumIVrijeme) {
        this.datumIVrijeme = datumIVrijeme;
    }
}
