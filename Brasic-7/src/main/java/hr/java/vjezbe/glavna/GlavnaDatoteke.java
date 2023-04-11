package hr.java.vjezbe.glavna;

import hr.java.vjezbe.entitet.*;
import hr.java.vjezbe.sortiranje.StudentSorter;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class GlavnaDatoteke {

    //private static final Logger logger = LoggerFactory.getLogger(Glavna.class);

    private static final String STUDENTI_FILE_NAME = "dat/studenti.txt";

    private static final String PROFESORI_FILE_NAME = "dat/profesori.txt";

    private static final String PREDMETI_FILE_NAME = "dat/predmeti.txt";

    private static final String ISPITI_FILE_NAME = "dat/ispiti.txt";

    private static final String OBRAZOVNAUSTANOVA_FILE_NAME = "dat/obrazovneUstanove.txt";

    private static final int BROJ_STUDENT = 2;
    private static final int BROJ_PROFESOR = 3;
    private static final int BROJ_PREDMET = 3;
    private static final int BROJ_ISPIT = 2;

    private static final int BROJ_ZAPISA_DAT_STUDENTI = 5;

    private static final int BROJ_ZAPISA_DAT_PROSJEK = 2;

    private static final int BROJ_ZAPISA_DAT_PREDMETI = 6;



    public static void main(String[] args) {
        Scanner unos = new Scanner(System.in);

        //logger.info("Example log from{}",Glavna.class.getSimpleName());

        List<Profesor> listaProfesora = new ArrayList<>();
        List<Predmet> listaPredmeta = new ArrayList<>();
        List<Student> listaStudenta = new ArrayList<>();
        List<Ispit> listaIspiti = new ArrayList<>();

        List<Prosjek> listaProsjeka = new ArrayList<>();
        Map<Profesor, List<Predmet>> nositelji = new HashMap<>();
        Sveuciliste<ObrazovnaUstanova> sveuciliste = new Sveuciliste<>();



        //ucitavanje studenata
        try(BufferedReader bufReaderStudent = new BufferedReader(new FileReader(new File("dat/studenti.txt")));
        ){

            List<String> datotekaStudenti = bufReaderStudent.lines().collect(Collectors.toList());

            for (int i = 0;i < datotekaStudenti.size()/BROJ_ZAPISA_DAT_STUDENTI;i++){
                String idStudent = datotekaStudenti.get(i * BROJ_ZAPISA_DAT_STUDENTI);
                String imeStudent = datotekaStudenti.get(i * BROJ_ZAPISA_DAT_STUDENTI+1);
                String prezimeStudent = datotekaStudenti.get(i * BROJ_ZAPISA_DAT_STUDENTI+2);
                String jmbagStudent = datotekaStudenti.get(i * BROJ_ZAPISA_DAT_STUDENTI+3);
                String datumRodenjaStudent = datotekaStudenti.get(i * BROJ_ZAPISA_DAT_STUDENTI+4);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

                LocalDate datumRodenja = LocalDate.parse(datumRodenjaStudent,formatter);
                listaStudenta.add(new Student(Long.parseLong(idStudent),
                        imeStudent,
                        prezimeStudent,
                        jmbagStudent,
                        datumRodenja));
            }

            datotekaStudenti.forEach(System.out::println);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //ucitavanje profesora
        try(BufferedReader bufReaderProfesor = new BufferedReader(new FileReader(new File("dat/profesori.txt")));
        ){

            List<String> datotekaProfesori = bufReaderProfesor.lines().collect(Collectors.toList());

            for (int i = 0;i < datotekaProfesori.size()/BROJ_ZAPISA_DAT_STUDENTI;i++){
                String idProfesora = datotekaProfesori.get(i * BROJ_ZAPISA_DAT_STUDENTI);
                String sifraProfesora = datotekaProfesori.get(i * BROJ_ZAPISA_DAT_STUDENTI+1);
                String imeProfesora = datotekaProfesori.get(i * BROJ_ZAPISA_DAT_STUDENTI+2);
                String prezimeProfesora = datotekaProfesori.get(i * BROJ_ZAPISA_DAT_STUDENTI+3);
                String titula = datotekaProfesori.get(i * BROJ_ZAPISA_DAT_STUDENTI+4);
                listaProfesora.add(new Profesor(Long.parseLong(idProfesora),
                        sifraProfesora,
                        imeProfesora,
                        prezimeProfesora,
                        titula));
            }

            datotekaProfesori.forEach(System.out::println);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //ucitavanje predmeta
        try(BufferedReader bufReaderPredmet = new BufferedReader(new FileReader(new File("dat/predmeti.txt")));
        ){

            List<String> datotekaPredmet = bufReaderPredmet.lines().collect(Collectors.toList());

            for (int i = 0;i < datotekaPredmet.size()/BROJ_ZAPISA_DAT_PREDMETI;i++){
                String idPredmet = datotekaPredmet.get(i * BROJ_ZAPISA_DAT_PREDMETI);
                String sifraPredmet = datotekaPredmet.get(i * BROJ_ZAPISA_DAT_PREDMETI+1);
                String nazivPredmet = datotekaPredmet.get(i * BROJ_ZAPISA_DAT_PREDMETI+2);
                String brojEcts = datotekaPredmet.get(i * BROJ_ZAPISA_DAT_PREDMETI+3);
                String nositeljString = datotekaPredmet.get(i * BROJ_ZAPISA_DAT_PREDMETI+4);
                String studentiString = datotekaPredmet.get(i * BROJ_ZAPISA_DAT_PREDMETI+5);

                Set<Student> listaStudenataSet = new HashSet<>();
                listaStudenataSet.add(listaStudenta.get(i));

                Integer broj = Integer.valueOf(brojEcts);
                listaPredmeta.add(new Predmet(Long.parseLong(idPredmet),
                        sifraPredmet,
                        nazivPredmet,
                        broj,
                        listaProfesora.get(i),
                        listaStudenataSet
                ));
            }

            datotekaPredmet.forEach(System.out::println);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //unos ispita
        try(BufferedReader bufReaderIspit = new BufferedReader(new FileReader(new File("dat/ispiti.txt")));
        ){

            List<String> datotekaIspiti = bufReaderIspit.lines().collect(Collectors.toList());

            for (int i = 0;i < datotekaIspiti.size()/BROJ_ZAPISA_DAT_STUDENTI;i++){
                String idIspit = datotekaIspiti.get(i * BROJ_ZAPISA_DAT_STUDENTI);
                String nazivIspit = datotekaIspiti.get(i * BROJ_ZAPISA_DAT_STUDENTI+1);
                String zgradaString = datotekaIspiti.get(i * BROJ_ZAPISA_DAT_STUDENTI+2);
                String dvoranaString = datotekaIspiti.get(i * BROJ_ZAPISA_DAT_STUDENTI+3);
                String student = datotekaIspiti.get(i * BROJ_ZAPISA_DAT_STUDENTI+4);
                String ocjenaString = datotekaIspiti.get(i * BROJ_ZAPISA_DAT_STUDENTI+5);
                String datumIVrijeme = datotekaIspiti.get(i * BROJ_ZAPISA_DAT_STUDENTI+6);
                Dvorana dvorana = new Dvorana(zgradaString,dvoranaString);
                Integer ocjenaInt = Integer.parseInt(ocjenaString);
                Ocjena ocjenaTipaOcjena = Ocjena.PRAZNO;
                switch(ocjenaInt){
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

                listaIspiti.add(new Ispit(Long.parseLong(idIspit),
                        listaPredmeta.get(i),
                        dvorana,
                        listaStudenta.get(i),
                        ocjenaTipaOcjena,
                        LocalDateTime.parse(datumIVrijeme)));
            }

            datotekaIspiti.forEach(System.out::println);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        HashMap<Long, BigDecimal> hash_map = new HashMap<Long, BigDecimal>();

        //ucitavanje prosjeka
        System.out.println("Unos path-a do tekstualne datoteke:");

        String path = unos.nextLine();

        try(BufferedReader bufReaderProsjek = new BufferedReader(new FileReader(new File("dat/prosjek.txt")));
        ){
            List<String> datotekaProsjek = bufReaderProsjek.lines().collect(Collectors.toList());

            for (int i = 0;i < datotekaProsjek.size()/BROJ_ZAPISA_DAT_PROSJEK;i++){
                String sifraPredmeta = datotekaProsjek.get(i * BROJ_ZAPISA_DAT_PROSJEK);
                String prosjekOcjena = datotekaProsjek.get(i * BROJ_ZAPISA_DAT_PROSJEK+1);

                BigDecimal prosjekOcjenaBigDecimal = BigDecimal.valueOf(Double.valueOf(prosjekOcjena));

                //spremanje u zbirku
                hash_map.put(Long.parseLong(sifraPredmeta),prosjekOcjenaBigDecimal);

                String sifraPredmetaZaUsporedbu = listaIspiti.get(i).getNaziv().getSifra();

                Integer ocjenaStudenta = listaIspiti.get(i).getOcjena().getNumerickaVrijednost();
                BigDecimal ocjenaStudentaBigDecimal = BigDecimal.valueOf(ocjenaStudenta);

                //ispis studenata koji us ostvarili visu ocjenu od prosjeka
                System.out.println("Studenti koji su ostvarili visu ocjenu od prosjeka");
                if(sifraPredmeta.equals(sifraPredmetaZaUsporedbu)){
                    if (prosjekOcjenaBigDecimal.compareTo(ocjenaStudentaBigDecimal)==1){
                        System.out.println(listaIspiti.get(i).getStudent());
                    } else if (ocjenaStudentaBigDecimal.compareTo(prosjekOcjenaBigDecimal)==-1){
                        System.out.println(listaIspiti.get(i).getStudent());
                    }
                }
            }
            datotekaProsjek.forEach(System.out::println);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }





        /*Integer brojObrazovnihUstanova = Integer.valueOf(0);
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
*/

        //unos.nextLine();
        listaIspiti.clear();
        listaProfesora.clear();
        listaPredmeta.clear();
        listaStudenta.clear();

        /*for (int j = 0;j < brojObrazovnihUstanova;j++){
            System.out.println("Unesite podatke za "+ (j+1) +". obrazovnu ustanovu:");

            //ispis profesora

            listaPredmeta.stream().forEach(a -> nositelji.put(a.getNositelj(),listaPredmeta.stream().filter(b -> b.getNositelj().equals(a)).toList()));
            nositelji.toString();

            for (int i = 0;i < BROJ_PROFESOR;i++){
                System.out.println("Profesor " + listaPredmeta.get(i).getNositelj().getIme() + " " +listaPredmeta.get(i).getNositelj().getPrezime() + " predaje sljedece predmete:");
                System.out.println( i +") " + listaPredmeta.get(i).getNaziv());
            }
*/
            /*ispis studenata na odredenom predmetu*/
           /* for (int i = 0;i<BROJ_PREDMET;i++){
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
*/
            /*if (odabirUstanove == 1){
                VeleucilisteJave veleucilisteJave = new VeleucilisteJave(5,nazivUstanove,listaPredmeta,listaProfesora,listaStudenta,listaIspiti/*predmeti,profesori,studenti,ispiti*//*);
                /*for (int k = 0;k < BROJ_STUDENT;k++){
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
                    System.out.println("Konačna ocjena studija studenta "+  listaStudenta.get(k).getIme() + " " + listaStudenta.get(k).getPrezime() + "je " + veleucilisteJave.izracunajKonacnuOcjenuStudijaZaStudente(listaIspiti/*ispiti*//*,ocjenaZavrsnogRadaTipaOcjena,ocjenaObraneZavrsnogRadaTipaOcjena));
                    System.out.println("Najbolji student 2022. godine " + veleucilisteJave.odrediNajuspjesnijegStudentaNaGodini(2022) +" "+ veleucilisteJave.odrediNajuspjesnijegStudentaNaGodini(2022).getPrezime() + " JMBAG: " + veleucilisteJave.odrediNajuspjesnijegStudentaNaGodini(2022).getJmbag());
                }
            }else{
                FakultetRacunarstva fakultetRacunarstva = new FakultetRacunarstva(4,nazivUstanove,listaPredmeta,listaProfesora,listaStudenta,listaIspiti/*predmeti,profesori,studenti,ispiti*//*);
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
                    System.out.println("Konačna ocjena studija studenta "+  listaStudenta.get(k).getIme() + " " + listaStudenta.get(k).getPrezime() + "je "+ fakultetRacunarstva.izracunajKonacnuOcjenuStudijaZaStudente(listaIspiti/*ispiti*//*,ocjenaDiplomskogRadaTipaOcjena,ocjenaObraneDiplomskogRadaTipaOcjena));
                    /*System.out.println("Najbolji student 2022. godine " + fakultetRacunarstva.odrediNajuspjesnijegStudentaNaGodini(2022)+" "+fakultetRacunarstva.odrediNajuspjesnijegStudentaNaGodini(2022).getPrezime()+" JMBAG: " + fakultetRacunarstva.odrediNajuspjesnijegStudentaNaGodini(2022).getJmbag());
                    System.out.println("Student koji je osvojio rektorovu nagradu je: " + fakultetRacunarstva.odrediStudentaZaRektorovuNagradu());

                }
*/
           /* }*/
            /*for (int i = 0;i < brojObrazovnihUstanova;i++){
                Student sveucilista = unosStudenta(unos,i);
                sveuciliste.add(i,student);
            }*/
            //sveuciliste.dodajObrazovnuUstanovu();


        }
    //}




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




    //public static void ucitavanjeProsjeka(){

   // }


}
