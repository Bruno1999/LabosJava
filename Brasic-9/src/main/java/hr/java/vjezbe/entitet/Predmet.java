package hr.java.vjezbe.entitet;

import java.util.Set;

/**
 * Klasa Predmet
 */
public class Predmet extends Entitet {
    private String sifra;
    private String naziv;
    private int brojEctsBodova;
    private hr.java.vjezbe.entitet.Profesor nositelj;
    private Set<hr.java.vjezbe.entitet.Student> studenti;

    public Predmet(long id, String sifra, String naziv, int brojEctsBodova, hr.java.vjezbe.entitet.Profesor nositelj, Set<hr.java.vjezbe.entitet.Student> studenti) {
        super(id);
        this.sifra = sifra;
        this.naziv = naziv;
        this.brojEctsBodova = brojEctsBodova;
        this.nositelj = nositelj;
        this.studenti = studenti;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getBrojEctsBodova() {
        return brojEctsBodova;
    }

    public void setBrojEctsBodova(int brojEctsBodova) {
        this.brojEctsBodova = brojEctsBodova;
    }

    public hr.java.vjezbe.entitet.Profesor getNositelj() {
        return nositelj;
    }

    public void setNositelj(Profesor nositelj) {
        this.nositelj = nositelj;
    }

    public Set<hr.java.vjezbe.entitet.Student> getStudenti() {
        return studenti;
    }

    public void setStudenti(Set<Student> studenti) {
        this.studenti = studenti;
    }


}