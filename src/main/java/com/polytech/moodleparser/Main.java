package com.polytech.moodleparser;

import com.polytech.moodleparser.parser.XMLParser;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        BorderPane layout = new BorderPane();

        Button button = new Button("Открыть файл");
        Label label = new Label();

        button.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();

            fileChooser.setInitialDirectory(new File("C:\\"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {

                label.setText(selectedFile.getName());
                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }
                    // Здесь парсер
                    XMLParser.collectXMLData(stringBuilder.toString());

                    //Здесь должен создавать документ

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("No file selected");
            }
        });
        layout.setTop(button);
        layout.setBottom(label);

        BorderPane.setAlignment(button, Pos.CENTER);
        BorderPane.setAlignment(label, Pos.CENTER);

        Scene scene = new Scene(layout, 320, 240);
        stage.setTitle("Конвертация файла");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}