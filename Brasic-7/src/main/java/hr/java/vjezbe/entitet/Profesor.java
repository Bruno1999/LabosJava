package hr.java.vjezbe.entitet;

/**
 * Klasa Profesor koja nasljeduje klasu Osoba
 * Klasa sadrzi builder pattern tj.ako neki podatak fali prilikom unosa ostali podaci ce se unjeti
 */
/*
public class Profesor extends Osoba {
    private String sifra;
    private String titula;

    public Profesor(ProfesorBuilder builder) {
        super(builder.ime, builder.prezime);
        this.sifra = builder.sifra;
        this.titula = builder.titula;
    }

    public static class ProfesorBuilder {
        private String sifra;
        private String titula;
        private String ime;
        private String prezime;

        public ProfesorBuilder ime(String ime) {
            this.ime= ime;
            return this;
        }

        public ProfesorBuilder prezime(String prezime) {
            this.prezime = prezime;
            return this;
        }

        public ProfesorBuilder sifra(String sifra) {
            this.sifra = sifra;
            return this;
        }

        public ProfesorBuilder titula(String titula) {
            this.titula = titula;
            return this;
        }

        public Profesor build() {
            return new Profesor(this);
        }
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getTitula() {
        return titula;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }

}*/
public class Profesor extends Osoba {
    private String sifra;
    private String ime;
    private String prezime;
    private String titula;

    public Profesor(long id, String sifra, String ime, String prezime, String titula) {
        super(id, ime, prezime);
        this.sifra = sifra;
        this.titula = titula;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getTitula() {
        return titula;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }

    /**
     * Klasa za generiranje objekata tipa {@link Profesor}
     */
    public static class ProfesorBuilder {
        private long id;
        private String sifra;
        private String ime;
        private String prezime;
        private String titula;

        public ProfesorBuilder(long id) {
            this.id = id;
        }

        public ProfesorBuilder setSifra(String sifra) {
            this.sifra = sifra;
            return this;
        }

        public ProfesorBuilder setIme(String ime) {
            this.ime = ime;
            return this;
        }

        public ProfesorBuilder setPrezime(String prezime) {
            this.prezime = prezime;
            return this;
        }

        public ProfesorBuilder setTitula(String titula) {
            this.titula = titula;
            return this;
        }

        /**
         * Generira objekt tipa {@link Profesor}.
         * @return Generiran objekt tipa {@link Profesor}.
         */
        public Profesor build() {
            return new Profesor(id, sifra, ime, prezime, titula);
        }
    }
}
