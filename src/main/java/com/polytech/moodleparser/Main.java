package com.polytech.moodleparser;

import com.polytech.moodleparser.docxConfig.Question;
import com.polytech.moodleparser.docxConfig.Word;
import com.polytech.moodleparser.parser.XMLParser;
import javafx.application.Application;
import javafx.application.Platform;
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
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class Main extends Application {
    static String fileName = "";
    @Override
    public void start(Stage stage) {
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: rgba(30,29,29,0.95)");

        Label label = new Label();
        label.setText("Конвертация из MoodleXML в DOCX");
        label.setId("label-1");
        label.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/LabelStyle.css")).toExternalForm());

        Button button = new Button("Загрузить XML файл");
        button.setMaxSize(150, 50);
        button.setId("my-button");
        button.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/ButtonStyle.css")).toExternalForm());

        BorderPane.setAlignment(button, Pos.TOP_CENTER);
        BorderPane.setAlignment(label, Pos.TOP_CENTER);
        layout.setCenter(button);
        layout.setTop(label);
        Scene scene = new Scene(layout, 420, 420);
        stage.setTitle("Загрузка файла");
        stage.setScene(scene);
        stage.show();

        button.setOnAction(actionEvent -> {
            button.getStyleClass().removeAll("addBobOk, focus");
            button.getStyleClass().add("addBobOk");
            FileChooser fileChooser = new FileChooser();

            fileChooser.setInitialDirectory(new File("C:\\"));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML", "*.xml"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                fileName = selectedFile.getName();

                try {
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile, StandardCharsets.UTF_8));
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line).append("\n");
                    }

                    List<Question> parsed = XMLParser.collectXMLData(stringBuilder.toString());
                    Word.generateWord(parsed);

                    stage.close();
                    Platform.runLater( () -> {
                        try {
                            new SecondStage().start( new Stage() );
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("No file selected");
            }
        });
    }

    public static String getFileName() {
        return fileName;
    }

    public static void main(String[] args) {
        launch();
    }
}