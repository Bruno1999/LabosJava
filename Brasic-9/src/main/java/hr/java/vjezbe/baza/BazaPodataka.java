package hr.java.vjezbe.baza;

import hr.java.vjezbe.entitet.Profesor;
import hr.java.vjezbe.entitet.Student;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.sql.*;


public class BazaPodataka {

    public static Connection connectToDatabase() throws SQLException, IOException {

        Properties configuration = new Properties();
        configuration.load(new FileReader("dat/bazaPodataka.properties"));

        String databaseURL = configuration.getProperty("databaseURL");
        String databaseUsername = configuration.getProperty("databaseUsername");
        String databasePassword = configuration.getProperty("databasePassword");

        Connection connection = DriverManager.getConnection(databaseURL, databaseUsername, databasePassword);
        return connection;
    }

    public static List<Student> getAllStudentsFromDatabase(Connection connection) throws SQLException, IOException {

        //Connection connection = connectToDatabase();

        List<Student> studentList = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        ResultSet studentsResultSet = sqlStatement.executeQuery("SELECT * FROM student");

        while(studentsResultSet.next()) {
            Student newStudent = getStudentFromResultSet(studentsResultSet);
            studentList.add(newStudent);
        }

        connection.close();

        return studentList;
    }

    public static List<Profesor> getAllProfesorsFromDatabase(Connection connection) throws SQLException, IOException {

        //Connection connection = connectToDatabase();

        List<Profesor> profesorList = new ArrayList<>();

        Statement sqlStatement = connection.createStatement();

        ResultSet profesorResultSet = sqlStatement.executeQuery("SELECT * FROM profesor");

        while(profesorResultSet.next()) {
            Profesor newProfesor = getProfesorFromResultSet(profesorResultSet);
            profesorList.add(newProfesor);
        }

        connection.close();

        return profesorList;
    }



    public static void insertNewStudentToDatabase(Student student) throws SQLException, IOException {
        Connection connection = connectToDatabase();

        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO STUDENT (JMBAG, IME, PREZIME, DATUM_RODJENJA) VALUES(?, ?, ?, ?)");

        stmt.setString(1, student.getJmbag());
        stmt.setString(2, student.getIme());
        stmt.setString(3, student.getPrezime());
        stmt.setDate(4, Date.valueOf(student.getDatumRodenja()));

        stmt.executeUpdate();

        connection.close();
    }

    public static void insertNewProfesorToDatabase(Profesor profesor) throws SQLException, IOException {
        Connection connection = connectToDatabase();

        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO PROFESOR (SIFRA, IME, PREZIME, TITULA) VALUES(?, ?, ?, ?)");

        stmt.setString(1, profesor.getSifra());
        stmt.setString(2, profesor.getIme());
        stmt.setString(3, profesor.getPrezime());
        stmt.setString(4, profesor.getTitula());

        stmt.executeUpdate();

        connection.close();
    }


    public static void updateStudentFirstName(Student studentToUpdate) throws SQLException, IOException {
        Connection connection = connectToDatabase();

        PreparedStatement updateStudenti =
                connection.prepareStatement(
                        "UPDATE STUDENTI SET IME = ? WHERE JMBAG = ?");

        updateStudenti.setString(1, studentToUpdate.getIme());
        updateStudenti.setString(2, studentToUpdate.getJmbag());

        updateStudenti.executeUpdate();

        connection.close();
    }

    public static void deleteStudent(Student studentToDelete) throws SQLException, IOException {
        Connection connection = connectToDatabase();

        PreparedStatement deleteStudent =
                connection.prepareStatement(
                        "DELETE FROM STUDENTI WHERE JMBAG = ?");

        deleteStudent.setString(1, studentToDelete.getJmbag());

        deleteStudent.executeUpdate();

        connection.close();
    }

    public static List<Student> filterStudentsByCriteria(Student criteria) throws SQLException, IOException  {

        Connection connection = connectToDatabase();

        List<Student> filteredStudentList = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM studenti WHERE 1=1");

        if(criteria.getJmbag().matches("[0-9]{10,20}") && criteria.getJmbag().isEmpty() == false) {
            sql.append(" AND JMBAG = " + criteria.getJmbag());
        }

        PreparedStatement statement = connection.prepareStatement(sql.toString());

        ResultSet studentsResultSet = statement.executeQuery();

        while(studentsResultSet.next()) {
            Student newStudent = getStudentFromResultSet(studentsResultSet);
            filteredStudentList.add(newStudent);
        }

        connection.close();

        return filteredStudentList;
    }

    private static Student getStudentFromResultSet(ResultSet studentsResultSet) throws SQLException {
        Long studentId = studentsResultSet.getLong("ID");
        String jmbag = studentsResultSet.getString("JMBAG");
        String firstName = studentsResultSet.getString("IME");
        String lastName = studentsResultSet.getString("PREZIME");
        LocalDate dateOfBirth = studentsResultSet.getDate("DATUM_RODJENJA").toLocalDate();

        return new Student(studentId, jmbag, firstName, lastName, dateOfBirth);
    }

    private static Profesor getProfesorFromResultSet(ResultSet profesorResultSet) throws SQLException {
        Long profesorId = profesorResultSet.getLong("ID");
        String sifra = profesorResultSet.getString("SIFRA");
        String firstName = profesorResultSet.getString("IME");
        String lastName = profesorResultSet.getString("PREZIME");
        String titula = profesorResultSet.getString("TITULA");

        return new Profesor(profesorId, sifra, firstName, lastName, titula);
    }

}
