package com.example.brasic7;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class IzbornikController {

    public void showStudentSearchScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pretragaStudenata.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        HelloApplication.getMainStage().setTitle("Pretraga studenata");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();

    }

    public void showAddStudent() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unosStudenta.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        HelloApplication.getMainStage().setTitle("Unos studenata");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();

    }

    public void showAddProfesor() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unosProfesor.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        HelloApplication.getMainStage().setTitle("Unos Profesora");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();

    }

    public void showProfesorSearchScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pretragaProfesor.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        HelloApplication.getMainStage().setTitle("Pretraga profesora");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();

    }
    public void showPredmetSearchScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        HelloApplication.getMainStage().setTitle("Pretraga predmet");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();

    }
    public void showIspitSearchScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        HelloApplication.getMainStage().setTitle("Pretraga ispit");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();

    }

    public void showPregledSearchScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pregled.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        HelloApplication.getMainStage().setTitle("Pretraga profesora");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();

    }

}
