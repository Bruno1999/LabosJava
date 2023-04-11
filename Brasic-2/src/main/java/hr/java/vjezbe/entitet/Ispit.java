package hr.java.vjezbe.entitet;

import java.time.LocalDateTime;

public non-sealed class Ispit implements Online {

    private Predmet naziv;
    private Dvorana nazivDvorane;
    private Student ime;
    private Integer ocjena;
    private LocalDateTime datumIVrijeme;

    public Ispit(Predmet naziv, Dvorana nazivDvorane, Student ime, Integer ocjena, LocalDateTime datumIVrijeme) {
        this.naziv = naziv;
        this.nazivDvorane = nazivDvorane;
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

    public Dvorana getNazivDvorane() {
        return nazivDvorane;
    }

    public void setNazivDvorane(Dvorana nazivDvorane) {
        this.nazivDvorane = nazivDvorane;
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

    /*
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
    }*/
}
