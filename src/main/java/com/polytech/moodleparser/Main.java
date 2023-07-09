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
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        BorderPane layout = new BorderPane();

        Button button = new Button("Открыть файл");
        Button button1 = new Button("Конвертировать");
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
                    List<Question> parsed = XMLParser.collectXMLData(stringBuilder.toString());

                    //Здесь должен создавать документ
                    Word.generateWord(parsed);
                    stage.close();
                    Platform.runLater( () -> new Main().start( new Stage() ) );
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("No file selected");
            }
        });
        button1.setOnAction(actionEvent -> {
            File file = new File("src/main/resources/DOCX/Вопросы.docx");
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file");
            fileChooser.setInitialDirectory(new File("C:\\Users\\Public\\Documents"));
            fileChooser.setInitialFileName("Вопросы");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("DOCX", "*.docx"));
            File dest = fileChooser.showSaveDialog(stage);

            if (dest != null) {
                try {
                    Files.copy(file.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.out.println(file.toPath());
                }
            }

        });


        layout.setTop(button);
        layout.setCenter(label);
        layout.setBottom(button1);

        BorderPane.setAlignment(button, Pos.CENTER);
        BorderPane.setAlignment(label, Pos.CENTER);
        BorderPane.setAlignment(button1, Pos.CENTER);

        Scene scene = new Scene(layout, 320, 240);
        stage.setTitle("Конвертация файла");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}