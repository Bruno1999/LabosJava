package com.example.brasic7;

import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public class UnosStudentController {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
    @FXML
    private TextField imeStudentaTextField;
    @FXML
    private TextField prezimeStudentaTextField;
    @FXML
    private TextField jmbagStudentaTextField;
    /*@FXML
    private TextField idStudentTextField;*/
    @FXML
    private DatePicker datumRodenjaDatePicker;


    private static final String STUDENT_FILE_NAME = "dat/studenti.txt";

    public static List<Student> getStudentList() throws IOException {
        FileReader reader = new FileReader(STUDENT_FILE_NAME);

        List<Student> studentList = new ArrayList<>();

        BufferedReader br = new BufferedReader(reader);

        String readLine;

        while((readLine = br.readLine()) != null) {
            String id = readLine;
            long idLong = Long.parseLong(id);
            String ime = br.readLine();
            String prezime = br.readLine();
            String jmbag = br.readLine();
            String dateOfBirthString = br.readLine();
            LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString, DATE_FORMAT);

            Student newStudent = new Student(idLong,ime, prezime, jmbag, dateOfBirth);

            studentList.add(newStudent);
        }

        return studentList;
    }
    public void saveStudent() {

        StringBuilder errorMessages = new StringBuilder();

        /*String id = idStudentTextField.getText();

        if(id.isEmpty()) {
            errorMessages.append("ID should not be empty!\n");
        }

        long idLong = Long.parseLong(id);*/


        String firstName = imeStudentaTextField.getText();

        if(firstName.isEmpty()) {
            errorMessages.append("First name should not be empty!\n");
        }

        String lastName = prezimeStudentaTextField.getText();

        if(lastName.isEmpty()) {
            errorMessages.append("Last name should not be empty!\n");
        }

        String jmbag = jmbagStudentaTextField.getText();

        if(jmbag.isEmpty()) {
            errorMessages.append("JMBAG should not be empty!\n");
        }


        LocalDate dateOfBirth = datumRodenjaDatePicker.getValue();

        if(Optional.ofNullable(dateOfBirth).isPresent() == false) {
            errorMessages.append("Date of birth should not be empty!\n");
        }

        List<Student> tempStudentList = new ArrayList<>();
        try {
            tempStudentList = getStudentList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        OptionalLong maksimalniId = tempStudentList.stream()
                .mapToLong(profesor -> profesor.getId()).max();

        if(errorMessages.isEmpty()) {
            Student newStudent = new Student(maksimalniId.getAsLong() + 1, firstName, lastName, jmbag, dateOfBirth);

            List<Student> studentList = null;
            try {
                studentList = getStudentList();

                studentList.add(newStudent);

                saveStudents(studentList);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Save action succeded!");
            alert.setHeaderText("Student data saved!");
            alert.setContentText("Student " + firstName + " " + lastName + " saved to the database!");

            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Save action failed!");
            alert.setHeaderText("Student data not saved!");
            alert.setContentText(errorMessages.toString());

            alert.showAndWait();
        }

    }
    public static void saveStudents(List<Student> studentList) throws IOException {
        FileWriter writer = new FileWriter("dat/studenti.txt");
        PrintWriter printer = new PrintWriter(writer);

        for(Student student : studentList) {
            printer.println(student.getId());
            printer.println(student.getIme());
            printer.println(student.getPrezime());
            printer.println(student.getJmbag());
            printer.println(DATE_FORMAT.format(student.getDatumRodenja()));
        }

        writer.flush();
    }

}
