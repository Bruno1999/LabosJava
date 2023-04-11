package com.example.brasic7;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class HelloController implements Initializable {

    //private static final int BROJ_ZAPISA_DAT_STUDENTI = 5;


    private static Connection connection;
    private static List<Student> studentList = new ArrayList<>();
    static {
        try {
            connection = BazaPodataka.connectToDatabase();
            studentList = BazaPodataka.getAllStudentsFromDatabase(connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private static ObservableList<Student> observableListStudent;


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

    //private List<Student> studentList = new ArrayList<>();

    public void initialize(URL url, ResourceBundle resourceBundle){

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

        if (observableListStudent == null){
            observableListStudent = FXCollections.observableArrayList();
        }

        observableListStudent.addAll(studentList);
        studentTableView.setItems(observableListStudent);
        studentTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //studentTableView.setItems(FXCollections.observableList(studentList));

    }
/*
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

    }*/

    public void dohvatiStudente(){

        HelloApplication.getMainStage().getScene().setOnKeyPressed(e-> {if (e.getCode()!= KeyCode.ENTER) return;});

        String ime = imeStudentaTextField.getText();

        String prezime = prezimeStudentaTextField.getText();

        String jmbag = jmbagStudentaTextField.getText();

        Predicate <Student> predIme = student -> student.getIme().toLowerCase().contains(ime.toLowerCase());

        List<Student> filStudenti = studentList.stream().filter(predIme).collect(Collectors.toList());


        /*List<Student> filStudenti = studentList.stream()
                .filter(s->s.getJmbag().toLowerCase().contains(jmbag))
                .filter(s->s.getPrezime().toLowerCase().contains(prezime))
                .filter(s->s.getIme().toLowerCase().contains(ime))
                .toList();

        studentTableView.setItems(FXCollections.observableList(filStudenti));*/

        observableListStudent.clear();
        observableListStudent.addAll(filStudenti);
    }

    /*public void dohvatiStudente(){
        String ime = imeStudentaTextField.getText();

        String prezime = prezimeStudentaTextField.getText();

        String jmbag = jmbagStudentaTextField.getText();

        List<Student> filStudenti = studentList.stream()
                .filter(s->s.getJmbag().toLowerCase().contains(jmbag))
                .filter(s->s.getPrezime().toLowerCase().contains(prezime))
                .filter(s->s.getIme().toLowerCase().contains(ime))
                .toList();

        studentTableView.setItems(FXCollections.observableList(filStudenti));
    }*/

}