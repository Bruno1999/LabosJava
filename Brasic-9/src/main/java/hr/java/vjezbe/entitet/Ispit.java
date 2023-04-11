package hr.java.vjezbe.entitet;

import java.time.LocalDateTime;


/**
 * Klasa Ispit koja implementira sucelje Online
 * Klasa je tipa non-sealed kj znaci da je final u nasljedivanju
 */
public non-sealed class Ispit extends Entitet implements Online {

    private Predmet naziv;
    private Dvorana nazivDvorane;
    private Student student;
    private Ocjena ocjena;
    private LocalDateTime datumIVrijeme;

    public Ispit(long id,Predmet naziv, Dvorana nazivDvorane, Student student, Ocjena ocjena, LocalDateTime datumIVrijeme) {
        super(id);
        this.naziv = naziv;
        this.nazivDvorane = nazivDvorane;
        this.student = student;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Ocjena getOcjena() {
        return ocjena;
    }

    public void setOcjena(Ocjena ocjena) {
        this.ocjena = ocjena;
    }

    public LocalDateTime getDatumIVrijeme() {
        return datumIVrijeme;
    }

    public void setDatumIVrijeme(LocalDateTime datumIVrijeme) {
        this.datumIVrijeme = datumIVrijeme;
    }


}
