package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;
//import java.util.logging.LoggerFactory;
//import java.util.logging.Logger;

/**
 * Sluzi za pokretanje programa
 */
public class Glavna {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    private static final int BROJ_STUDENT = 2;
    private static final int BROJ_PROFESOR = 2;
    private static final int BROJ_PREDMET = 2;
    private static final int BROJ_ISPIT = 2;

    /**
     * Sluzi za pokretanje programa
     * @param args argumenti koji su poslani u program
     */
    public static void main(String[] args) {
        Scanner unos = new Scanner(System.in);

        logger.info("Example log from{}",Glavna.class.getSimpleName());

        Profesor[] profesori = new Profesor[BROJ_PROFESOR];
        Predmet[] predmeti = new Predmet[BROJ_PREDMET];
        Student[] studenti = new Student[BROJ_STUDENT];
        Ispit[] ispiti = new Ispit[BROJ_ISPIT];

        Integer brojObrazovnihUstanova = Integer.valueOf(0);
        boolean nastaviPetlju = false;
        do{
            try{
                System.out.print("Unesite broj obrazovnih ustanova: ");
                brojObrazovnihUstanova = unos.nextInt();
                nastaviPetlju = false;
            }catch (RuntimeException ex){
                System.out.println("Morate unjeti brojčane vrijednosti");
                unos.nextLine();
                nastaviPetlju = true;
                logger.error("Broj obrazovnih ustanova -> InputMismatchException " + ex);
            }
        }while(nastaviPetlju);


        //unos.nextLine();

        for (int j = 0;j < brojObrazovnihUstanova;j++){
            System.out.println("Unesite podatke za "+ (j+1) +". obrazovnu ustanovu:");

            //unos profesora
            for (int i = 0;i < BROJ_PROFESOR;i++){
                Profesor profesor = unosProfesora(unos,i);
                profesori[i] = profesor;
            }

            //unos predmeta
            for (int i = 0;i < BROJ_PREDMET;i++){
                Predmet predmet = unosPredmeta(unos,profesori,i);
                predmeti[i] = predmet;
            }

            //unos studenta
            for (int i = 0;i < BROJ_STUDENT;i++){
                Student student = unosStudenta(unos,i);
                studenti[i] = student;
            }

            //unos ispitnog roka
            for (int i = 0;i < BROJ_ISPIT;i++){
                Ispit ispit = unosIspitnogRoka(unos,predmeti,studenti,i);
                ispiti[i] = ispit;
            }
            //ispis na kraju
            for (int i = 0;i < BROJ_ISPIT;i++){
                String ocjenaTekst;
                if(ispiti[i].getOcjena() == 1){
                    ocjenaTekst = "nedovoljan";
                }else if(ispiti[i].getOcjena() == 2){
                    ocjenaTekst = "dovoljan";
                }else if(ispiti[i].getOcjena() == 3){
                    ocjenaTekst = "dobar";
                }else if(ispiti[i].getOcjena() == 4){
                    ocjenaTekst = "vrlo dobar";
                }else {
                    ocjenaTekst = "izvrstan";
                }

                if(ispiti[i].getOcjena() == 5) {
                    System.out.println("Student " + ispiti[i].getIme().getIme() + " " + ispiti[i].getIme().getPrezime() + " je ostvario ocjenu '" +
                            ocjenaTekst + "' na predmetu '" + ispiti[i].getNaziv().getNaziv() + "'");
                }
            }

            nastaviPetlju = false;
            Integer odabirUstanove = Integer.valueOf(0);
            do{
                try{
                    System.out.println("Odaberite obrazovnu ustanovu za navedene podatke koju želite unijeti (1 - Veleučilište Jave, " +
                            "2 - Fakultet računarstva):");
                    odabirUstanove = unos.nextInt();
                    nastaviPetlju = false;
                }catch (InputMismatchException ex){
                    System.out.println("Morate unjeti brojčane vrijednosti");
                    unos.nextLine();
                    nastaviPetlju = true;
                    logger.error("Odabir ustanove -> InputMismatchException " + ex);
                }
            }while(nastaviPetlju);


            //unos.nextLine();

            System.out.println("Unesite naziv obrazovne ustanove: ");
            String nazivUstanove = unos.nextLine();

            if (odabirUstanove == 1){
                VeleucilisteJave veleucilisteJave = new VeleucilisteJave(nazivUstanove,predmeti,profesori,studenti,ispiti);
                for (int k = 0;k < BROJ_STUDENT;k++){
                    Integer ocjenaZavrsnogRada = Integer.valueOf(0);
                    Integer ocjenaObraneZavrsnogRada = Integer.valueOf(0);

                    nastaviPetlju = false;
                    if (ispiti[k].getOcjena()>1){
                        do{
                            try{
                                System.out.print("Unesite ocjenu zavrsnog rada za studenta: " + studenti[k].getIme() + " " + studenti[k].getPrezime()+":");
                                ocjenaZavrsnogRada = unos.nextInt();
                                nastaviPetlju = false;
                            }catch (InputMismatchException ex){
                                System.out.println("Morate unjeti brojčane vrijednosti");
                                unos.nextLine();
                                nastaviPetlju = true;
                                logger.error("Ocjena zavrsnog rada -> InputMismatchException " + ex);
                            }
                        }while(nastaviPetlju);

                        //unos.nextLine();

                        nastaviPetlju = false;
                        do{
                            try{
                                System.out.println("Unesite ocjenu obrane zavrsnog rada za studenta: " + studenti[k].getIme() + " " + studenti[k].getPrezime()+":");
                                ocjenaObraneZavrsnogRada = unos.nextInt();
                                nastaviPetlju = false;
                            }catch (InputMismatchException ex){
                                System.out.println("Morate unjeti brojčane vrijednosti");
                                unos.nextLine();
                                nastaviPetlju = true;
                                logger.error("Ocjena obrane zavrsnog rada -> InputMismatchException " + ex);
                            }
                        }while(nastaviPetlju);
                    }

                    //unos.nextLine();
                    System.out.println("Konačna ocjena studija studenta "+  studenti[k].getIme() + " " + studenti[k].getPrezime() + "je " + veleucilisteJave.izracunajKonacnuOcjenuStudijaZaStudente(ispiti,ocjenaZavrsnogRada,ocjenaObraneZavrsnogRada));
                    System.out.println("Najbolji student 2022. godine " + veleucilisteJave.odrediNajuspjesnijegStudentaNaGodini(2022) +" "+ veleucilisteJave.odrediNajuspjesnijegStudentaNaGodini(2022).getPrezime() + " JMBAG: " + veleucilisteJave.odrediNajuspjesnijegStudentaNaGodini(2022).getJmbag());
                }
            }else{
                FakultetRacunarstva fakultetRacunarstva = new FakultetRacunarstva(nazivUstanove,predmeti,profesori,studenti,ispiti);
                for (int k = 0;k < BROJ_STUDENT;k++){
                    nastaviPetlju = false;
                    Integer ocjenaDiplomskogRada = Integer.valueOf(0);
                    Integer ocjenaObraneDiplomskogRada = Integer.valueOf(0);

                    if (ispiti[k].getOcjena()>1){
                        do{
                            try{
                                System.out.println("Unesite ocjenu diplomskog rada za studenta: " + studenti[k].getIme() + " " + studenti[k].getPrezime()+":");
                                ocjenaDiplomskogRada = unos.nextInt();
                                nastaviPetlju = false;
                            }catch (InputMismatchException ex){
                                System.out.println("Morate unjeti brojčane vrijednosti");
                                unos.nextLine();
                                nastaviPetlju = true;
                                logger.error("Ocjena diplomskog rada -> InputMismatchException " + ex);
                            }
                        }while(nastaviPetlju);

                        //unos.nextLine();

                        nastaviPetlju = false;

                        do{
                            try{
                                System.out.println("Unesite ocjenu obrane diplomskog rada za studenta: " + studenti[k].getIme() + " " + studenti[k].getPrezime()+":");
                                ocjenaObraneDiplomskogRada = unos.nextInt();
                                nastaviPetlju = false;
                            }catch (InputMismatchException ex){
                                System.out.println("Morate unjeti brojčane vrijednosti");
                                unos.nextLine();
                                nastaviPetlju = true;
                                logger.error("Ocjena diplomskog rada -> InputMismatchException " + ex);
                            }
                        }while(nastaviPetlju);
                    }



                    //unos.nextLine();
                    System.out.println("Konačna ocjena studija studenta "+  studenti[k].getIme() + " " + studenti[k].getPrezime() + "je "+ fakultetRacunarstva.izracunajKonacnuOcjenuStudijaZaStudente(ispiti,ocjenaDiplomskogRada,ocjenaObraneDiplomskogRada));
                    System.out.println("Najbolji student 2022. godine " + fakultetRacunarstva.odrediNajuspjesnijegStudentaNaGodini(2022)+" "+fakultetRacunarstva.odrediNajuspjesnijegStudentaNaGodini(2022).getPrezime()+" JMBAG: " + fakultetRacunarstva.odrediNajuspjesnijegStudentaNaGodini(2022).getJmbag());
                    System.out.println("Student koji je osvojio rektorovu nagradu je: " + fakultetRacunarstva.odrediStudentaZaRektorovuNagradu());

                }

            }
        }
    }

    /**
     * Funkcija za kreiranje objekta Profesor
     * @param unos Scanner objekta sluzi za ucitavanje podataka
     * @param i sluzi kao brojac da znamo koji profesor po redu unosim i pomoc pri programiranju
     * @return vraca objekt tipa Profesor nazad u main program
     */
    public static Profesor unosProfesora(Scanner unos,int i){

        System.out.println("Unesite " + (i+1) + ". profesora:");
        System.out.print("Unesite sifru profesora:");
        String sifra = unos.nextLine();

        System.out.print("Unesite ime profesora:");
        String ime = unos.nextLine();

        System.out.print("Unesite prezime profesora:");
        String prezime = unos.nextLine();

        System.out.print("Unesite titulu profesora:");
        String titula = unos.nextLine();

        Profesor vrati = new Profesor.ProfesorBuilder()
                .sifra(sifra)
                .ime(ime)
                .prezime(prezime)
                .titula(titula)
                .build();
        return vrati;

        //unos.nextLine();
        /*Profesor profesor = new Profesor(sifra,ime,prezime,titula);
        return profesor;*/
    }

    /**
     * Funkcija za ispisivanje objekata tipa Profesora iz polja profesori
     * @param profesori je polje objekata koje se predaje u funkciju
     */
    public static  void ispisProfesora(Profesor[] profesori){
        for (int j = 0;j < BROJ_PROFESOR;j++){
            System.out.println((j+1) + ". " + profesori[j].getIme() +
                    " " + profesori[j].getPrezime());
        }
    }

    /**
     * Funkcija za ispisivanje objekata tipa Student iz polja studenti
     * @param studenti je polje objekata tipa Student koje se predaje u funkciju
     */
    public static  void ispisStudenata(Student[] studenti){
        for (int j = 0;j < BROJ_STUDENT;j++){
            System.out.println((j+1) + ". " + studenti[j].getIme() +
                    " " + studenti[j].getPrezime());
        }
    }

    /**
     * Funkcija za ispisivanje objekata tipa Predmet iz polja predmeti
     * @param predmeti je polje objekata tipa Predmet koje se predaje u funkciju
     */
    public static  void ispisPredmeta(Predmet[] predmeti){
        for (int j = 0;j < BROJ_PREDMET;j++){
            System.out.println((j+1) + ". " + predmeti[j].getNaziv());
        }
    }

    /**
     * Funkcija za ispisivanje objekata tipa Ispit iz polja ispiti
     * @param ispiti je polje objekata tipa Ispit koje se predaje u funkciju
     */
    public static  void ispisIspitnogRoka(Ispit[] ispiti){
        for (int j = 0;j < BROJ_ISPIT;j++){
            System.out.println((j+1) + ". " + ispiti[j].getNaziv() + " " + ispiti[j].getIme() + " "
                    + ispiti[j].getOcjena() + " " + ispiti[j].getDatumIVrijeme());
        }
    }

    /**
     * Funkcija za kreiranje objekta Predmet
     * @param unos Scanner objekt sluzi za ucitavanje podataka
     * @param profesori predaje se polje objekata koje se koristi za odabir odredenog profesora za predmet
     * @param i sluzi kao brojac da znamo koji predmet po redu unosim i pomoc pri programiranju
     * @return vraca objekt Predmet
     */
    public static Predmet unosPredmeta(Scanner unos,Profesor[] profesori,int i){
        System.out.println("Unesite " + (i+1) + ". predmet:");
        System.out.print("Unesite sifru predmeta:");
        String sifra = unos.nextLine();

        System.out.print("Unesite naziv predmeta:");
        String naziv = unos.nextLine();


        boolean nastaviPetlju = false;
        Integer brojEctsBodova = Integer.valueOf(0);
        do{
            try{
                System.out.print("Unesite broj ECTS bodova za predmet '" + naziv +"':" );
                brojEctsBodova = unos.nextInt();
                nastaviPetlju = false;
            }catch (InputMismatchException ex){
                System.out.println("Morate unjeti brojčane vrijednosti");
                unos.nextLine();
                nastaviPetlju = true;
                logger.error("Unesen broj ECTS bodova -> InputMismatchException " + ex);
            }
        }while(nastaviPetlju);


        // napraviti try catch za odabir profesora

       /* System.out.println("Odaberite profesora: ");
        Integer indexProfesor = -1;
        for (int a = 0;a<1;a++){
            int k = 0;
            Profesor[]  profesorUPetlji = new Profesor[BROJ_PROFESOR];
            do {
                k++;
                if (k>BROJ_PROFESOR){
                    System.out.println("Neispravan unos, pokušajte ponovo!");
                }
                ispisProfesora(profesori);
                System.out.print("Odabir >> ");
                indexProfesor = unos.nextInt();
                unos.nextLine();

            }while (indexProfesor < 1 || indexProfesor > BROJ_PROFESOR);
            profesorUPetlji[a] = profesori[indexProfesor-1];
        }*/
        nastaviPetlju = false;
        Integer indexProfesor = -1;
        for (int a = 0;a<1;a++) {
            int k = 0;
            Profesor[] profesorUPetlji = new Profesor[BROJ_PROFESOR];
            do {
                try {
                    ispisProfesora(profesori);
                    System.out.print("Odabir >> ");
                    indexProfesor = unos.nextInt();
                    //unos.nextLine();
                    nastaviPetlju = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Morate unjeti brojčane vrijednosti");
                    unos.nextLine();
                    nastaviPetlju = true;
                    logger.error("Odabrani broj profesora -> InputMismatchException " + ex);
                }


            } while (nastaviPetlju);
            profesorUPetlji[a] = profesori[indexProfesor - 1];

        }


        nastaviPetlju = false;
        Integer brojStudenata = Integer.valueOf(0);
        do{
            try{
                System.out.print("Unesite broj studenata za predmet '" + naziv + "':");
                brojStudenata = unos.nextInt();
                nastaviPetlju = false;
            }catch (InputMismatchException ex){
                System.out.println("Morate unjeti brojčane vrijednosti");
                unos.nextLine();
                nastaviPetlju = true;
                logger.error("Unesen broj studenata za predmet -> InputMismatchException " + ex);
            }
        }while(nastaviPetlju);

        //unos.nextLine();

        Predmet predmet = new Predmet(sifra,naziv,brojEctsBodova,profesori[indexProfesor-1],brojStudenata);
        return  predmet;
    }

    /**
     * Funkcija za kreiranje objekta Student
     * @param unos Scanner objekt sluzi za ucitavanje podataka
     * @param i sluzi kao brojac da znamo koji predmet po redu unosim i pomoc pri programiranju
     * @return vraca objekt Student
     */
    public static Student unosStudenta(Scanner unos,int i){
        System.out.println("Unesite " + (i+1) + ". studenta:");
        System.out.print("Unesite ime studenta:");
        String ime = unos.nextLine();

        System.out.print("Unesite prezime studenta:");
        String prezime = unos.nextLine();

        System.out.print("Unesite datum rođenja studenta " + prezime + " " + ime + " u formatu (dd.MM.yyyy.):");
        String privremeniDatumRodenja = unos.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

        LocalDate datumRodenja = LocalDate.parse(privremeniDatumRodenja,formatter);

        System.out.print("Unesite jmbag studenta:" + prezime +" "+ ime + ":");
        String jmbag = unos.nextLine();

        //unos.nextLine();

        Student student = new Student(ime,prezime,jmbag,datumRodenja);
        return student;
    }

    /**
     * Funkcija za kreiranje objekt Ispit
     * @param unos Scanner objekt sluzi za ucitavanje podataka
     * @param predmeti  predaje se polje objekata koje se koristi za odabir odredenog predmeta za ispitni rok
     * @param studenti  predaje se polje objekata koje se koristi za odabir odredenog studenta za ispitni rok
     * @param i sluzi kao brojac da znamo koji predmet po redu unosim i pomoc pri programiranju
     * @return vraca objekt Ispit
     */
    public static Ispit unosIspitnogRoka(Scanner unos,Predmet[] predmeti,Student[] studenti,int i){
        System.out.println("Unesite " + (i+1) + ". ispitni rok:");

/*
        //napraviti try catch
        System.out.println("Odaberite predmet:");
        // ODABIR PREDMET
        Integer indexPredmet = -1;
        for (int a = 0;a<1;a++){
            int k = 0;
            Predmet[]  predmetUPetlji = new Predmet[BROJ_PREDMET];
            do {
                k++;
                if (k>BROJ_PREDMET){
                    System.out.println("Neispravan unos, pokušajte ponovo!");
                }
                ispisPredmeta(predmeti);
                System.out.print("Odabir >> ");
                indexPredmet = unos.nextInt();
                unos.nextLine();

            }while (indexPredmet < 1 || indexPredmet > BROJ_PREDMET);
            predmetUPetlji[a] = predmeti[indexPredmet-1];
        }
*/      boolean nastaviPetlju = false;
        Integer indexPredmet = -1;
        for (int a = 0;a<1;a++) {
            int k = 0;
            Predmet[]  predmetUPetlji = new Predmet[BROJ_PREDMET];
            do {
                try {
                    ispisPredmeta(predmeti);
                    System.out.print("Odabir >> ");
                    indexPredmet = unos.nextInt();
                    //unos.nextLine();
                    nastaviPetlju = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Morate unjeti brojčane vrijednosti");
                    unos.nextLine();
                    nastaviPetlju = true;
                    logger.error("Odabrani broj predmeta -> InputMismatchException " + ex);
                }


            } while (nastaviPetlju);
            predmetUPetlji[a] = predmeti[indexPredmet - 1];

        }

        System.out.print("Unesite naziv dvorane:");
        String nazivDvorane = unos.nextLine();
        System.out.println("Unesite zgradu dvorane: ");
        String nazivZgrade = unos.nextLine();

        /*System.out.println("Odaberite studenta:");
        // ODABIR STUDENTA
        Integer indexStudent = -1;
        for (int a = 0;a<1;a++){
            int k = 0;
            Student[]  studentUPetlji = new Student[BROJ_STUDENT];
            do {
                k++;
                if (k>BROJ_STUDENT){
                    System.out.println("Neispravan unos, pokušajte ponovo!");
                }
                ispisStudenata(studenti);
                System.out.print("Odabir >> ");
                indexStudent = unos.nextInt();
                unos.nextLine();

            }while (indexStudent < 1 || indexStudent > BROJ_STUDENT);
            studentUPetlji[a] = studenti[indexStudent-1];
        }
*/
/*
        Integer indexStudent = -1;
        for (int a = 0;a<1;a++){
            int k = 0;
            Student[]  studentUPetlji = new Student[BROJ_STUDENT];
            do {
                k++;
                if (k>BROJ_STUDENT){
                    System.out.println("Neispravan unos, pokušajte ponovo!");
                }
                ispisStudenata(studenti);
                System.out.print("Odabir >> ");
                indexStudent = unos.nextInt();
                unos.nextLine();

            }while (indexStudent < 1 || indexStudent > BROJ_STUDENT);
            studentUPetlji[a] = studenti[indexStudent-1];
        }
*/
        nastaviPetlju = false;
        Integer indexStudent = -1;
        for (int a = 0;a<1;a++) {
            int k = 0;
            Student[]  studentUPetlji = new Student[BROJ_STUDENT];
            do {
                try {
                    ispisStudenata(studenti);
                    System.out.print("Odabir >> ");
                    indexStudent = unos.nextInt();
                    //unos.nextLine();
                    nastaviPetlju = false;
                } catch (InputMismatchException ex) {
                    System.out.println("Morate unjeti brojčane vrijednosti");
                    unos.nextLine();
                    nastaviPetlju = true;
                    logger.error("Odabrani broj studenta -> InputMismatchException " + ex);
                }


            } while (nastaviPetlju);
            studentUPetlji[a] = studenti[indexStudent - 1];

        }



        nastaviPetlju = false;
        Integer ocjena = Integer.valueOf(0);
        do{
            try{
                System.out.print("Unesite ocjenu na ispitu(1-5): ");
                ocjena = unos.nextInt();
                nastaviPetlju = false;
            }catch (InputMismatchException ex){
                System.out.println("Morate unjeti brojčane vrijednosti");
                unos.nextLine();
                nastaviPetlju = true;
                logger.error("Unesena ocjena na ispitu -> InputMismatchException " + ex);
            }
        }while(nastaviPetlju);


        unos.nextLine();
        System.out.print("Unesite datum i vrijeme ispita u formatu (dd.MM.yyyy.THH:mm):");
        String privremenoVrijeme = unos.nextLine();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.'T'HH:mm");

        LocalDateTime datumIVrijeme = LocalDateTime.parse(privremenoVrijeme,formatter);

        unos.nextLine();

        Dvorana nD = new Dvorana(nazivDvorane,nazivZgrade);
        Ispit ispit = new Ispit(predmeti[indexPredmet-1],nD,studenti[indexStudent-1],ocjena,datumIVrijeme);
        return ispit;
    }
}


