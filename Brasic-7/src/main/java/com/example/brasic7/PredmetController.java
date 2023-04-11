package com.example.brasic7;

import hr.java.vjezbe.entitet.Predmet;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PredmetController {
/*
    private static final int BROJ_ZAPISA_DAT_PREDMETI = 6;

    @FXML
    private TextField imeProfesoraTextField;
    @FXML
    private TextField imeStudentTextField;

    @FXML
    private TextField nazivPredmetaTextField;

    @FXML
    private TextField sifraPredmetaTextField;

    @FXML
    private TextField brojECTSBodovaTextField;

    @FXML
    private TableView<Predmet> predmetTableView;

    @FXML
    private TableColumn<Predmet,String> imeProfesoraTableColumn;
    @FXML
    private TableColumn<Predmet,String> imeStudentaTableColumn;

    @FXML
    private TableColumn<Predmet,String> nazivPredmetaTextColumn;

    @FXML
    private TableColumn<Predmet,String> sifraPredmetaTableColumn;
    @FXML
    private TableColumn<Predmet,String> brojECTSTableColumn;


    private List<Predmet> predmetList = new ArrayList<>();
    private List<Profesor> profesorList = new ArrayList<>();
    private static final int BROJ_ZAPISA_DAT_PROFESORI = 5;
    private List<Student> studentList = new ArrayList<>();
    private static final int BROJ_ZAPISA_DAT_STUDENTI = 5;


    public void initialize(){
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
                studentList.add(new Student(Long.parseLong(idStudent),
                        imeStudent,
                        prezimeStudent,
                        jmbagStudent,
                        datumRodenja));
            }

            //datotekaStudenti.forEach(System.out::println);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try(BufferedReader bufReaderProfesor = new BufferedReader(new FileReader(new File("dat/profesori.txt")));
        ){

            List<String> datotekaProfesori = bufReaderProfesor.lines().collect(Collectors.toList());

            for (int i = 0;i < datotekaProfesori.size()/BROJ_ZAPISA_DAT_PROFESORI;i++){
                String idProfesor = datotekaProfesori.get(i * BROJ_ZAPISA_DAT_PROFESORI);
                String sifraProfesora = datotekaProfesori.get(i * BROJ_ZAPISA_DAT_PROFESORI+1);
                String imeProfesora = datotekaProfesori.get(i * BROJ_ZAPISA_DAT_PROFESORI+2);
                String prezimeProfesora = datotekaProfesori.get(i * BROJ_ZAPISA_DAT_PROFESORI+3);
                String titulaProfesora = datotekaProfesori.get(i * BROJ_ZAPISA_DAT_PROFESORI+4);

                profesorList.add(new Profesor(Long.parseLong(idProfesor),
                        sifraProfesora,
                        imeProfesora,
                        prezimeProfesora,
                        titulaProfesora));
            }

            //datotekaStudenti.forEach(System.out::println);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //ucitavanje studenata
        try(BufferedReader bufReaderPredmet = new BufferedReader(new FileReader(new File("dat/predmeti.txt")));
        ){

            List<String> datotekaPredmeti = bufReaderPredmet.lines().collect(Collectors.toList());

            for (int i = 0;i < datotekaPredmeti.size()/BROJ_ZAPISA_DAT_PREDMETI;i++){
                String idPredmeta = datotekaPredmeti.get(i * BROJ_ZAPISA_DAT_PREDMETI);
                String sifraPredmeta = datotekaPredmeti.get(i * BROJ_ZAPISA_DAT_PREDMETI+1);
                String nazivPredmeta = datotekaPredmeti.get(i * BROJ_ZAPISA_DAT_PREDMETI+2);
                String brojEcts = datotekaPredmeti.get(i * BROJ_ZAPISA_DAT_PREDMETI+3);
                String nositelj = datotekaPredmeti.get(i * BROJ_ZAPISA_DAT_PREDMETI+4);
                String studenti = datotekaPredmeti.get(i * BROJ_ZAPISA_DAT_PREDMETI+5);

                Integer brojECTSInt = Integer.valueOf(brojEcts);
                Profesor profesor = new Profesor(profesorList.get(i).getId(),profesorList.get(i).getSifra(),
                        profesorList.get(i).getIme(),profesorList.get(i).getPrezime(),profesorList.get(i).getTitula());

                Student student = new Student(studentList.get(i).getId(),studentList.get(i).getIme(),
                        studentList.get(i).getPrezime(),studentList.get(i).getJmbag(),studentList.get(i).getDatumRodenja());

                predmetList.add(new Predmet(Long.parseLong(idPredmeta),
                        sifraPredmeta,
                        nazivPredmeta,
                        brojECTSInt,
                        profesor,
                        student));
            }

            //datotekaStudenti.forEach(System.out::println);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sifraPredmetaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getSifra()));
        imeProfesoraTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getNositelj().getIme()));
        imeStudentaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getStudenti().));
        nazivPredmetaTextColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getNaziv()));



        brojECTSTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleIntegerProperty(cellData.getValue().getBrojEctsBodova().toString()));

        predmetTableView.setItems(FXCollections.observableList(predmetList));

    }

    public void dohvatiPredmete(){
        String ime = imeProfesoraTextField.getText();

        String imeStudenta = imeStudentTextField.getText();

        String sifra = sifraPredmetaTextField.getText();

        String brojECTS = brojECTSBodovaTextField.getText();

        String naziv = nazivPredmetaTextField.getText();

        List<Predmet> filPredmeti = predmetList.stream()
                .filter(s->s.getSifra().toLowerCase().contains(sifra))
                .filter(s->s.getNositelj().getIme().contains(ime))
                .filter(s->s.getStudenti().getIme().contains(imeStudenta))
                .filter(s->s.getNaziv().toLowerCase().contains(naziv))
                .filter(s->s.getBrojEctsBodova().contains(brojECTS))
                .toList();

        predmetTableView.setItems(FXCollections.observableList(filPredmeti));

    }
*/
}
