package hr.java.vjezbe.entitet;

import java.time.LocalDate;
import java.util.Objects;


/**
 * Klasa Student koja nasljeduje klasu Osoba
 */
public class Student extends Osoba {

    private String jmbag;
    private LocalDate datumRodenja;

    public Student(long id,String ime, String prezime, String jmbag, LocalDate datumRodenja) {
        super(id,ime,prezime);
        this.jmbag = jmbag;
        this.datumRodenja = datumRodenja;
    }

    public String getJmbag() {
        return jmbag;
    }

    public void setJmbag(String jmbag) {
        this.jmbag = jmbag;
    }

    public LocalDate getDatumRodenja() {
        return datumRodenja;
    }

    public void setDatumRodenja(LocalDate datumRodenja) {
        this.datumRodenja = datumRodenja;
    }

    /**
     * Metoda za provjeru jednakosti
     * @param o objekt
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return getIme().equals(student.getIme()) && getPrezime().equals(student.getPrezime()) && jmbag.equals(student.jmbag) && datumRodenja.equals(student.datumRodenja);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag, datumRodenja);
    }

    @Override
    public String toString() {
        return "Student{" +
                "jmbag='" + jmbag + '\'' +
                ", datumRodenja=" + datumRodenja +
                '}';
    }
}
