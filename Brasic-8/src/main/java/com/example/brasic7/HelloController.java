package com.example.brasic7;

import hr.java.vjezbe.entitet.Student;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HelloController {

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

    private List<Student> studentList = new ArrayList<>();
    public void initialize(){
        /*String datumRodenjaStudent = "20.10.2000.";
        String datumRodenjaStudent2 = "14.04.1999.";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");

        LocalDate datumRodenja = LocalDate.parse(datumRodenjaStudent,formatter);
        LocalDate datumRodenja2 = LocalDate.parse(datumRodenjaStudent2,formatter);
        studentList = new ArrayList<>();

        studentList.add(new Student(1,"pero","peric","5423452345",datumRodenja));
        studentList.add(new Student(2,"ivo","ivic","5423452345",datumRodenja2));
*/
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

        String prezime = prezimeStudentaTextField.getText();

        String jmbag = jmbagStudentaTextField.getText();

       /* List<Student> filtriranistudeneti = studentList.stream().filter(s -> s.getIme().contains(ime))
                .toList();
        List<Student> filtriraniStudentiPoPrezimenu = studentList.stream().filter(s -> s.getPrezime().contains(prezime))
                .toList();
        List<Student> filtriraniStudentiPoJmbag = studentList.stream().filter(s -> s.getJmbag().contains(jmbag))
                .toList();
        */

        List<Student> filStudenti = studentList.stream()
                .filter(s->s.getJmbag().toLowerCase().contains(jmbag))
                .filter(s->s.getPrezime().toLowerCase().contains(prezime))
                .filter(s->s.getIme().toLowerCase().contains(ime))
                .toList();

        studentTableView.setItems(FXCollections.observableList(filStudenti));
    }

}