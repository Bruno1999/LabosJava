package hr.java.vjezbe.glavna;

import com.sun.tools.javac.Main;
import hr.java.vjezbe.entitet.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import hr.java.vjezbe.sortiranje.StudentSorter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
//import java.util.logging.LoggerFactory;
//import java.util.logging.Logger;

/**
 * Sluzi za pokretanje programa
 */
public class Glavna {

    private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    private static final String STUDENTI_FILE_NAME = "dat/studenti.txt";

    private static final String PROFESORI_FILE_NAME = "dat/profesori.txt";

    private static final String PREDMETI_FILE_NAME = "dat/predmeti.txt";

    private static final String ISPITI_FILE_NAME = "dat/ispiti.txt";

    private static final String OBRAZOVNAUSTANOVA_FILE_NAME = "dat/obrazovneUstanove.txt";

    private static final int BROJ_STUDENT = 2;
    private static final int BROJ_PROFESOR = 3;
    private static final int BROJ_PREDMET = 3;
    private static final int BROJ_ISPIT = 2;

    private static final int BROJ_ZAPISA_DAT_STUDENTI = 4;

    /**
     * Sluzi za pokretanje programa
     * @param args argumenti koji su poslani u program
     */
    public static void main(String[] args) {
        Scanner unos = new Scanner(System.in);

        logger.info("Example log from{}",Glavna.class.getSimpleName());

        List<Profesor> listaProfesora = new ArrayList<>();
        List<Predmet> listaPredmeta = new ArrayList<>();
        List<Student> listaStudenta = new ArrayList<>();
        List<Ispit> listaIspiti = new ArrayList<>();
        Map<Profesor, List<Predmet>> nositelji = new HashMap<>();
        Sveuciliste<ObrazovnaUstanova> sveuciliste = new Sveuciliste<>();



        try(BufferedReader bufReaderStudent = new BufferedReader(new FileReader(new File("dat/studenti.txt")));
        ){

            List<String> datotekaStudenti = bufReaderStudent.lines().collect(Collectors.toList());

            for (int i = 0;i < datotekaStudenti.size()/BROJ_ZAPISA_DAT_STUDENTI;i++){
                String idStudent = datotekaStudenti.get(i * BROJ_ZAPISA_DAT_STUDENTI);
                String imeStudent = datotekaStudenti.get(i * BROJ_ZAPISA_DAT_STUDENTI+1);
                String prezimeStudent = datotekaStudenti.get(i * BROJ_ZAPISA_DAT_STUDENTI+2);
                String jmbagStudent = datotekaStudenti.get(i * BROJ_ZAPISA_DAT_STUDENTI+3);
                String datumRodenjaStudent = datotekaStudenti.get(i * BROJ_ZAPISA_DAT_STUDENTI+4);
                listaStudenta.add(new Student(Long.parseLong(idStudent),
                                            imeStudent,
                                            prezimeStudent,
                                            jmbagStudent,
                                            LocalDate.parse(datumRodenjaStudent)));
            }

            datotekaStudenti.forEach(System.out::println);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        listaIspiti.clear();
        listaProfesora.clear();
        listaPredmeta.clear();
        listaStudenta.clear();

        for (int j = 0;j < brojObrazovnihUstanova;j++){
            System.out.println("Unesite podatke za "+ (j+1) +". obrazovnu ustanovu:");

            //unos profesora
            for (int i = 0;i < BROJ_PROFESOR;i++){
                Profesor profesor = unosProfesora(unos,i);
                listaProfesora.add(i,profesor);
            }


            //unos predmeta
            for (int i = 0;i < BROJ_PREDMET;i++){
                Predmet predmet = unosPredmeta(unos,listaProfesora,i);
                listaPredmeta.add(i,predmet);
            }

            //ispis profesora

            listaPredmeta.stream().forEach(a -> nositelji.put(a.getNositelj(),listaPredmeta.stream().filter(b -> b.getNositelj().equals(a)).toList()));
            nositelji.toString();

            for (int i = 0;i < BROJ_PROFESOR;i++){
                System.out.println("Profesor " + listaPredmeta.get(i).getNositelj().getIme() + " " +listaPredmeta.get(i).getNositelj().getPrezime() + " predaje sljedece predmete:");
                System.out.println( i +") " + listaPredmeta.get(i).getNaziv());
            }

            //unos studenta
            for (int i = 0;i < BROJ_STUDENT;i++){
                Student student = unosStudenta(unos,i);
                listaStudenta.add(i,student);
            }

            //unos ispitnog roka
            for (int i = 0;i < BROJ_ISPIT;i++){
                Ispit ispit = unosIspitnogRoka(unos,listaPredmeta,listaStudenta,i);
                listaIspiti.add(i,ispit);
            }

            /*ispis studenata na odredenom predmetu*/
            for (int i = 0;i<BROJ_PREDMET;i++){
                if (listaPredmeta.get(i).getStudenti().isEmpty()){
                    System.out.println("Nema studenta upisanih na predmet '" + listaPredmeta.get(i).getNaziv()+"'.");
                }else {
                    System.out.println("Studenti upisani na predmet '" + listaPredmeta.get(i).getNaziv() + "' su:");
                    for (int b = 0;b < listaPredmeta.get(i).getStudenti().size();b++){
                        ispisiStudentaNaPredmetu(listaPredmeta.get(b));
                    }
                }
            }


            //ispis na kraju
            for (int i = 0;i < BROJ_ISPIT;i++){
                String ocjenaTekst;
                if(listaIspiti.get(i).getOcjena().getNumerickaVrijednost() == 1){
                    ocjenaTekst = "nedovoljan";
                }else if(listaIspiti.get(i).getOcjena().getNumerickaVrijednost() == 2){
                    ocjenaTekst = "dovoljan";
                }else if(listaIspiti.get(i).getOcjena().getNumerickaVrijednost() == 3){
                    ocjenaTekst = "dobar";
                }else if(listaIspiti.get(i).getOcjena().getNumerickaVrijednost() == 4){
                    ocjenaTekst = "vrlo dobar";
                }else {
                    ocjenaTekst = "izvrstan";
                }

                if(listaIspiti.get(i).getOcjena().getNumerickaVrijednost() == 5) {
                    System.out.println("Student " + listaIspiti.get(i).getStudent().getIme() + " " + listaIspiti.get(i).getStudent().getPrezime() + " je ostvario ocjenu '" +
                            ocjenaTekst + "' na predmetu '" + listaIspiti.get(i).getNaziv().getNaziv() + "'");
                }
            }

            //ispisiIspiteSOcjenomIzvrstan(listaIspiti);

            listaPredmeta.stream().forEach(a -> nositelji.put(a.getNositelj(),listaPredmeta.stream().filter(b -> b.getNositelj().equals(a)).toList()));
            nositelji.toString();

            //listaPredmeta.stream().forEach();

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
                VeleucilisteJave veleucilisteJave = new VeleucilisteJave(nazivUstanove,listaPredmeta,listaProfesora,listaStudenta,listaIspiti/*predmeti,profesori,studenti,ispiti*/);
                for (int k = 0;k < BROJ_STUDENT;k++){
                    Integer ocjenaZavrsnogRada = Integer.valueOf(0);
                    Integer ocjenaObraneZavrsnogRada = Integer.valueOf(0);

                    Ocjena ocjenaZavrsnogRadaTipaOcjena = Ocjena.PRAZNO;
                    Ocjena ocjenaObraneZavrsnogRadaTipaOcjena = Ocjena.PRAZNO;

                    nastaviPetlju = false;
                    if (listaIspiti.get(k).getOcjena().getNumerickaVrijednost()>1){
                        do{
                            try{
                                System.out.print("Unesite ocjenu zavrsnog rada za studenta: " + listaStudenta.get(k).getIme() + " " + listaStudenta.get(k).getPrezime()+":");
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
                                System.out.println("Unesite ocjenu obrane zavrsnog rada za studenta: " + listaStudenta.get(k).getIme() + " " + listaStudenta.get(k).getPrezime()+":");
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

                    switch(ocjenaZavrsnogRada){
                        case 1:
                            ocjenaZavrsnogRadaTipaOcjena = Ocjena.NEDOVOLJAN;
                            break;
                        case 2:
                            ocjenaZavrsnogRadaTipaOcjena = Ocjena.DOVOLJAN;
                            break;
                        case 3:
                            ocjenaZavrsnogRadaTipaOcjena = Ocjena.DOBAR;
                            break;
                        case 4:
                            ocjenaZavrsnogRadaTipaOcjena = Ocjena.VRLO_DOBAR;
                            break;
                        case 5:
                            ocjenaZavrsnogRadaTipaOcjena = Ocjena.IZVRSTAN;
                            break;
                    }

                    switch(ocjenaObraneZavrsnogRada){
                        case 1:
                            ocjenaObraneZavrsnogRadaTipaOcjena = Ocjena.NEDOVOLJAN;
                            break;
                        case 2:
                            ocjenaObraneZavrsnogRadaTipaOcjena = Ocjena.DOVOLJAN;
                            break;
                        case 3:
                            ocjenaObraneZavrsnogRadaTipaOcjena = Ocjena.DOBAR;
                            break;
                        case 4:
                            ocjenaObraneZavrsnogRadaTipaOcjena = Ocjena.VRLO_DOBAR;
                            break;
                        case 5:
                            ocjenaObraneZavrsnogRadaTipaOcjena = Ocjena.IZVRSTAN;
                            break;
                    }

                    //unos.nextLine();
                    System.out.println("Konačna ocjena studija studenta "+  listaStudenta.get(k).getIme() + " " + listaStudenta.get(k).getPrezime() + "je " + veleucilisteJave.izracunajKonacnuOcjenuStudijaZaStudente(listaIspiti/*ispiti*/,ocjenaZavrsnogRadaTipaOcjena,ocjenaObraneZavrsnogRadaTipaOcjena));
                    System.out.println("Najbolji student 2022. godine " + veleucilisteJave.odrediNajuspjesnijegStudentaNaGodini(2022) +" "+ veleucilisteJave.odrediNajuspjesnijegStudentaNaGodini(2022).getPrezime() + " JMBAG: " + veleucilisteJave.odrediNajuspjesnijegStudentaNaGodini(2022).getJmbag());
                }
            }else{
                FakultetRacunarstva fakultetRacunarstva = new FakultetRacunarstva(nazivUstanove,listaPredmeta,listaProfesora,listaStudenta,listaIspiti/*predmeti,profesori,studenti,ispiti*/);
                for (int k = 0;k < BROJ_STUDENT;k++){
                    nastaviPetlju = false;
                    Integer ocjenaDiplomskogRada = Integer.valueOf(0);
                    Integer ocjenaObraneDiplomskogRada = Integer.valueOf(0);

                    Ocjena ocjenaDiplomskogRadaTipaOcjena = Ocjena.PRAZNO;
                    Ocjena ocjenaObraneDiplomskogRadaTipaOcjena = Ocjena.PRAZNO;

                    if (listaIspiti.get(k).getOcjena().getNumerickaVrijednost()>1){
                        do{
                            try{
                                System.out.println("Unesite ocjenu diplomskog rada za studenta: " + listaStudenta.get(k).getIme() + " " + listaStudenta.get(k).getPrezime()+":");
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
                                System.out.println("Unesite ocjenu obrane diplomskog rada za studenta: " + listaStudenta.get(k).getIme() + " " + listaStudenta.get(k).getPrezime()+":");
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


                    switch(ocjenaDiplomskogRada){
                        case 1:
                            ocjenaDiplomskogRadaTipaOcjena = Ocjena.NEDOVOLJAN;
                            break;
                        case 2:
                            ocjenaDiplomskogRadaTipaOcjena = Ocjena.DOVOLJAN;
                            break;
                        case 3:
                            ocjenaDiplomskogRadaTipaOcjena = Ocjena.DOBAR;
                            break;
                        case 4:
                            ocjenaDiplomskogRadaTipaOcjena = Ocjena.VRLO_DOBAR;
                            break;
                        case 5:
                            ocjenaDiplomskogRadaTipaOcjena = Ocjena.IZVRSTAN;
                            break;
                    }

                    switch(ocjenaObraneDiplomskogRada){
                        case 1:
                            ocjenaObraneDiplomskogRadaTipaOcjena = Ocjena.NEDOVOLJAN;
                            break;
                        case 2:
                            ocjenaObraneDiplomskogRadaTipaOcjena = Ocjena.DOVOLJAN;
                            break;
                        case 3:
                            ocjenaObraneDiplomskogRadaTipaOcjena = Ocjena.DOBAR;
                            break;
                        case 4:
                            ocjenaObraneDiplomskogRadaTipaOcjena = Ocjena.VRLO_DOBAR;
                            break;
                        case 5:
                            ocjenaObraneDiplomskogRadaTipaOcjena = Ocjena.IZVRSTAN;
                            break;
                    }

                    //unos.nextLine();
                    System.out.println("Konačna ocjena studija studenta "+  listaStudenta.get(k).getIme() + " " + listaStudenta.get(k).getPrezime() + "je "+ fakultetRacunarstva.izracunajKonacnuOcjenuStudijaZaStudente(listaIspiti/*ispiti*/,ocjenaDiplomskogRadaTipaOcjena,ocjenaObraneDiplomskogRadaTipaOcjena));
                    System.out.println("Najbolji student 2022. godine " + fakultetRacunarstva.odrediNajuspjesnijegStudentaNaGodini(2022)+" "+fakultetRacunarstva.odrediNajuspjesnijegStudentaNaGodini(2022).getPrezime()+" JMBAG: " + fakultetRacunarstva.odrediNajuspjesnijegStudentaNaGodini(2022).getJmbag());
                    System.out.println("Student koji je osvojio rektorovu nagradu je: " + fakultetRacunarstva.odrediStudentaZaRektorovuNagradu());

                }

            }
            /*for (int i = 0;i < brojObrazovnihUstanova;i++){
                Student sveucilista = unosStudenta(unos,i);
                sveuciliste.add(i,student);
            }*/
            //sveuciliste.dodajObrazovnuUstanovu();


        }
    }

    private static void readStudentsFromFile(List<Student> studenti) {

        try (FileReader fileReader = new FileReader("dat/studenti.txt"/*Main.STUDENTI_FILE_NAME*/);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String line;

            while ((line = reader.readLine()) != null) {
                String ime = reader.readLine();
                String prezime = reader.readLine();
                String jmbag = reader.readLine();
                String datumRodenja = reader.readLine();
                /*Long id = Long.parseLong(line);
                String name = reader.readLine();
                String description = reader.readLine();*/

                Student student = new Student(ime,prezime,jmbag,datumRodenja);
                studenti.add(student);
            }
        } catch (IOException e) {
            System.out.println("File " + "dat/studenti.txt"/*Main.STUDENTI_FILE_NAME*/ + " not found.");
            logger.error(e.getMessage(), e);
        }
    }

    private static void readProfesorFromFile(List<Profesor> profesori) {

        try (FileReader fileReader = new FileReader("dat/profesori.txt"/*Main.PROFESORI_FILE_NAME*/);
             BufferedReader reader = new BufferedReader(fileReader)) {

            String line;

            while ((line = reader.readLine()) != null) {

                String sifra = reader.readLine();
                String ime = reader.readLine();
                String prezime = reader.readLine();
                String titula = reader.readLine();

                Profesor profesor = new Profesor(ime,prezime,sifra,titula);
                profesori.add(profesor);
            }
        } catch (IOException e) {
            System.out.println("File " + "dat/profesori.txt"/*Main.PROFESOR_FILE_NAME*/ + " not found.");
            logger.error(e.getMessage(), e);
        }
    }


    /**
     * Metoda za kreiranje objekta Profesor
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
                .id(1)
                .setSifra(sifra)
                .setIme(ime)
                .setPrezime(prezime)
                .setTitula(titula)
                .build();
        return vrati;

        //unos.nextLine();
    }

    /**
     * Metoda za ispisivanje objekata tipa Profesora iz liste profesori
     * @param profesori je polje objekata koje se predaje u funkciju
     */
    public static  void ispisProfesora(List<Profesor> profesori){
        for (int j = 0;j < BROJ_PROFESOR;j++){
            System.out.println((j+1) + ". " + profesori.get(j).getIme() +
                    " " + profesori.get(j).getPrezime());
        }
    }

    /**
     * Metoda za ispisivanje objekata tipa Student iz polja studenti
     * @param studenti je polje objekata tipa Student koje se predaje u funkciju
     */
    public static  void ispisStudenata(List<Student> studenti){
        for (int j = 0;j < BROJ_STUDENT;j++){
            System.out.println((j+1) + ". " + studenti.get(j).getIme() +
                    " " + studenti.get(j).getPrezime());
        }
    }

    /**
     * Metoda za ispisivanje objekata tipa Predmet iz polja predmeti
     * @param predmeti je polje objekata tipa Predmet koje se predaje u funkciju
     */
    public static  void ispisPredmeta(List<Predmet> predmeti){
        for (int j = 0;j < BROJ_PREDMET;j++){
            System.out.println((j+1) + ". " + predmeti.get(j).getNaziv());
        }
    }

    /**
     * Metoda za ispis nositelja predmeta
     * @param nositelji predaje se map sa key Profesor,value lista<Predmet>
     */

    private static void ispisiPredmeteNositelja(Map<Profesor, List<Predmet>> nositelji) {
        nositelji.forEach((nositelj, predmeti) -> {
            System.out.printf("%s %s je nositelj predmeta:%n", nositelj.getIme(), nositelj.getPrezime());
            for (var p : predmeti) {
                System.out.printf("  %s %s%n", p.getSifra(), p.getNaziv());
            }
        });
    }

    /**
     * Metoda za ispisivanje objekata tipa Ispit iz polja ispiti
     * @param ispiti je polje objekata tipa Ispit koje se predaje u funkciju
     */

    public static  void ispisIspitnogRoka(List<Ispit> ispiti){
        for (int j = 0;j < BROJ_ISPIT;j++){
            System.out.println((j+1) + ". " + ispiti.get(j).getNaziv() + " " + ispiti.get(j).getStudent() + " "
                    + ispiti.get(j).getOcjena() + " " + ispiti.get(j).getDatumIVrijeme());
        }
    }

    /**
     * Metoda za ispis studenata na predmetu
     * @param p predaje se lista predmeta
     */

    private static void ispisiStudentaNaPredmetu(Predmet p) {
        System.out.printf("Studenti upisani na predmet %s (%s):%n", p.getNaziv(), p.getSifra());

        /*for (var s : p.getStudenti() .stream().sorted(new StudentSorter()).toList()) {
            System.out.printf("%s %s %s%n", s.getJmbag(), s.getPrezime(), s.getIme());
        }*/

        p.getStudenti().stream()
                .sorted(new StudentSorter())
                .toList()
                .forEach(s -> System.out.printf("%s %s %s%n", s.getJmbag(), s.getPrezime(), s.getIme()));

    }

    /**
     * Ispisuje ispite koji su ocjenjeni s ocjenom 5.
     * @param ustanova Ustanova na kojoj su pisani ispiti.
     */
    private static void ispisiIspiteSOcjenomIzvrstan(ObrazovnaUstanova ustanova) {
        System.out.println("Ispiti s ocjenom 5:");

        var ispiti = ustanova.ispitiSOcjenomIzvrstan();

        if (ispiti.size() == 0) {
            System.out.println("Nitko nije dobio ocjenu 5!");
        } else {
            ispiti.forEach(i -> System.out.printf("%s %s, %s%n", i.getStudent().getIme(), i.getStudent().getPrezime(), i.getNaziv()));
        }
    }


    /**
     * Metoda za kreiranje objekta Predmet
     * @param unos Scanner objekt sluzi za ucitavanje podataka
     * @param profesori predaje se polje objekata koje se koristi za odabir odredenog profesora za predmet
     * @param i sluzi kao brojac da znamo koji predmet po redu unosim i pomoc pri programiranju
     * @return vraca objekt Predmet
     */
    public static Predmet unosPredmeta(Scanner unos,List<Profesor> profesori,int i){

        Set<Student> studenti= new HashSet<>();

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


        nastaviPetlju = false;
        Integer indexProfesor = -1;
        for (int a = 0;a<1;a++) {

            List<Profesor> profesorUPetlji = new ArrayList<>();
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

            profesorUPetlji.set(a,profesori.get(indexProfesor-1));

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

        for (int j = 0;j<brojStudenata;j++){
            studenti.add(unosStudenta(unos,j));
        }

        //unos.nextLine();

        Predmet predmet = new Predmet(sifra,naziv,brojEctsBodova,profesori.get(indexProfesor-1),studenti);
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
    public static Ispit unosIspitnogRoka(Scanner unos,List<Predmet> predmeti,List<Student> studenti,int i){
        System.out.println("Unesite " + (i+1) + ". ispitni rok:");

   boolean nastaviPetlju = false;
        Integer indexPredmet = -1;
        for (int a = 0;a<1;a++) {
            int k = 0;
            List<Predmet>  predmetUPetlji = new ArrayList<>();
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
            predmetUPetlji.set(a, predmeti.get(indexPredmet-1));

        }

        System.out.print("Unesite naziv dvorane:");
        String nazivDvorane = unos.nextLine();
        System.out.println("Unesite zgradu dvorane: ");
        String nazivZgrade = unos.nextLine();

        nastaviPetlju = false;
        Integer indexStudent = -1;
        for (int a = 0;a<1;a++) {
            int k = 0;
            List<Student>  studentUPetlji = new ArrayList<>();
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
            studentUPetlji.set(a,studenti.get(indexStudent-1));

        }

        nastaviPetlju = false;
        Integer ocjena = Integer.valueOf(0);
        Ocjena ocjenaTipaOcjena = Ocjena.PRAZNO;
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

        switch(ocjena){
            case 1:
                ocjenaTipaOcjena = Ocjena.NEDOVOLJAN;
                break;
            case 2:
                ocjenaTipaOcjena = Ocjena.DOVOLJAN;
                break;
            case 3:
                ocjenaTipaOcjena = Ocjena.DOBAR;
                break;
            case 4:
                ocjenaTipaOcjena = Ocjena.VRLO_DOBAR;
                break;
            case 5:
                ocjenaTipaOcjena = Ocjena.IZVRSTAN;
                break;
        }

        Dvorana nD = new Dvorana(nazivDvorane,nazivZgrade);
        Ispit ispit = new Ispit(predmeti.get(indexPredmet-1),nD,studenti.get(indexStudent-1),ocjenaTipaOcjena,datumIVrijeme);
        return ispit;
    }
}


