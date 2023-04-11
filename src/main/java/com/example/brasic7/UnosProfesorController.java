package com.example.brasic7;

import hr.java.vjezbe.entitet.Profesor;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalLong;

public class UnosProfesorController {

    @FXML
    private TextField imeProfesorTextField;
    @FXML
    private TextField prezimeProfesorTextField;
    @FXML
    private TextField sifraProfesorTextField;
    @FXML
    private TextField titulaProfesorTextField;
    /*@FXML
    private TextField idProfesorTextField;*/



    private static final String PROFESOR_FILE_NAME = "dat/profesori.txt";

    public static List<Profesor> getProfesorList() throws IOException {
        FileReader reader = new FileReader(PROFESOR_FILE_NAME);

        List<Profesor> profesorList = new ArrayList<>();

        BufferedReader br = new BufferedReader(reader);

        String readLine;

        while((readLine = br.readLine()) != null) {
            String id = readLine;
            long idLong = Long.parseLong(id);
            String sifra = br.readLine();
            String ime = br.readLine();
            String prezime = br.readLine();
            String titula = br.readLine();

            Profesor newProfesor = new Profesor(idLong,sifra,ime, prezime, titula);

            profesorList.add(newProfesor);
        }

        return profesorList;
    }
    public void saveProfesor() {

        StringBuilder errorMessages = new StringBuilder();

        /*String id = idProfesorTextField.getText();

        if(id.isEmpty()) {
            errorMessages.append("ID should not be empty!\n");
        }

        long idLong = Long.parseLong(id);*/



        String sifra = sifraProfesorTextField.getText();

        if(sifra.isEmpty()) {
            errorMessages.append("First name should not be empty!\n");
        }


        String ime = imeProfesorTextField.getText();

        if(ime.isEmpty()) {
            errorMessages.append("First name should not be empty!\n");
        }

        String prezime = prezimeProfesorTextField.getText();

        if(prezime.isEmpty()) {
            errorMessages.append("Last name should not be empty!\n");
        }

        String titula = titulaProfesorTextField.getText();

        if(titula.isEmpty()) {
            errorMessages.append("JMBAG should not be empty!\n");
        }

        List<Profesor> tempProfesorList = new ArrayList<>();
        try {
            tempProfesorList = getProfesorList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OptionalLong maksimalniId = tempProfesorList.stream()
                .mapToLong(profesor -> profesor.getId()).max();


        if(errorMessages.isEmpty()) {
            Profesor newProfesor = new Profesor(maksimalniId.getAsLong() + 1,sifra,ime,prezime,titula);

            List<Profesor> profesorList = null;
            try {
                profesorList = getProfesorList();

                profesorList.add(newProfesor);

                saveProfesors(profesorList);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save action succeded!");
            alert.setHeaderText("Profesor data saved!");
            alert.setContentText("Profesor " + ime + " " + prezime + " saved to the database!");

            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save action failed!");
            alert.setHeaderText("Profesor data not saved!");
            alert.setContentText(errorMessages.toString());

            alert.showAndWait();
        }

    }
    public static void saveProfesors(List<Profesor> profesorList) throws IOException {
        FileWriter writer = new FileWriter("dat/profesori.txt");
        PrintWriter printer = new PrintWriter(writer);

        for(Profesor profesor : profesorList) {
            printer.println(profesor.getId());
            printer.println(profesor.getSifra());
            printer.println(profesor.getIme());
            printer.println(profesor.getPrezime());
            printer.println(profesor.getTitula());
        }

        writer.flush();
    }


}
