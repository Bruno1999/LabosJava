package com.example.brasic7;

import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PregledController {
    private static final int BROJ_ZAPISA_DAT_STUDENTI = 5;
    @FXML
    private TextField imeStudentaTextField;
    @FXML
    private TextField prezimeStudentaTextField;
    @FXML
    private TextField jmbagStudentaTextField;
    @FXML
    private DatePicker datumRodenjaDatePicker;
    @FXML
    private TableView<Student> studentTableView;
    @FXML
    private TableColumn<Student,String> imeStudentaTableColumn;
    @FXML
    private TableColumn<Student,String> prezimeStudentaTableColumn;
    @FXML
    private TableColumn<Student,String> jmbagStudentaTableColumn;
    @FXML
    private TableColumn<Student,String> datumRodenjaStudentaTableColumn;

    private static final int BROJ_ZAPISA_DAT_PROFESORI = 5;

    @FXML
    private TextField imeProfesoraTextField;
    @FXML
    private TextField prezimeProfesoraTextField;

    @FXML
    private TextField sifraProfesoraTextField;

    @FXML
    private TextField titulaProfesoraTextField;

    @FXML
    private TableView<Profesor> profesorTableView = new TableView<>();

    @FXML
    private TableColumn<Profesor,String> imeProfesoraTableColumn;
    @FXML
    private TableColumn<Profesor,String> prezimeProfesoraTableColumn;

    @FXML
    private TableColumn<Profesor,String> sifraProfesoraTableColumn;
    @FXML
    private TableColumn<Profesor,String> titulaProfesoraTableColumn;


    private List<Profesor> profesorList = new ArrayList<>();

    private List<Student> studentList = new ArrayList<>();
    public void initialize(){

        //ucitavanje profesora
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

        sifraProfesoraTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getSifra()));
        imeProfesoraTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getIme()));
        prezimeProfesoraTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getPrezime()));
        titulaProfesoraTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getTitula()));

        profesorTableView.setItems(FXCollections.observableList(profesorList));

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

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        imeStudentaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getIme()));
        prezimeStudentaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getPrezime()));
        jmbagStudentaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getJmbag()));
        datumRodenjaStudentaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getDatumRodenja().toString()));

        studentTableView.setItems(FXCollections.observableList(studentList));
    }

    public void dohvatiStudente(){
        String ime = imeStudentaTextField.getText();

        List<Student> filStudenti = studentList.stream()
                .filter(s->s.getIme().toLowerCase().contains(ime))
                .toList();

        studentTableView.setItems(FXCollections.observableList(filStudenti));
    }
    public void dohvatiProfesore(){
        String ime = imeProfesoraTextField.getText();

        List<Profesor> filProfesori = profesorList.stream()
                .filter(s->s.getIme().toLowerCase().contains(ime))
                .toList();

        profesorTableView.setItems(FXCollections.observableList(filProfesori));
    }


}
