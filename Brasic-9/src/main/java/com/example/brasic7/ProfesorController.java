package com.example.brasic7;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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

public class ProfesorController implements Initializable {

    private static final int BROJ_ZAPISA_DAT_PROFESORI = 5;


    private static Connection connection;
    private static List<Profesor> profesorList = new ArrayList<>();
    static {
        try {
            connection = BazaPodataka.connectToDatabase();
            profesorList = BazaPodataka.getAllProfesorsFromDatabase(connection);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private static ObservableList<Profesor> observableListProfesor;

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


    //private List<Profesor> profesorList = new ArrayList<>();
    public void initialize(URL url, ResourceBundle resourceBundle){

        imeProfesoraTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getIme()));
        prezimeProfesoraTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getPrezime()));
        sifraProfesoraTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getSifra()));
        titulaProfesoraTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getTitula()));

        if (observableListProfesor == null){
            observableListProfesor = FXCollections.observableArrayList();
        }

        observableListProfesor.addAll(profesorList);
        profesorTableView.setItems(observableListProfesor);
        profesorTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //studentTableView.setItems(FXCollections.observableList(studentList));

    }

    /*
    public void initialize(){

        //ucitavanje studenata
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

    }*/

    public void dohvatiProfesore(){
        String ime = imeProfesoraTextField.getText();

        String prezime = prezimeProfesoraTextField.getText();

        String sifra = sifraProfesoraTextField.getText();

        String titula = titulaProfesoraTextField.getText();

        /*List<Profesor> filProfesori = profesorList.stream()
                .filter(s->s.getSifra().toLowerCase().contains(sifra))
                .filter(s->s.getPrezime().toLowerCase().contains(prezime))
                .filter(s->s.getIme().toLowerCase().contains(ime))
                .filter(s->s.getTitula().toLowerCase().contains(titula))
                .toList();

        profesorTableView.setItems(FXCollections.observableList(filProfesori));*/

        HelloApplication.getMainStage().getScene().setOnKeyPressed(e-> {if (e.getCode()!= KeyCode.ENTER) return;});



        Predicate<Profesor> predIme = profesor -> profesor.getIme().toLowerCase().contains(ime.toLowerCase());

        List<Profesor> filProfesor = profesorList.stream().filter(predIme).collect(Collectors.toList());


        /*List<Student> filStudenti = studentList.stream()
                .filter(s->s.getJmbag().toLowerCase().contains(jmbag))
                .filter(s->s.getPrezime().toLowerCase().contains(prezime))
                .filter(s->s.getIme().toLowerCase().contains(ime))
                .toList();

        studentTableView.setItems(FXCollections.observableList(filStudenti));*/

        observableListProfesor.clear();
        observableListProfesor.addAll(filProfesor);

    }

}
