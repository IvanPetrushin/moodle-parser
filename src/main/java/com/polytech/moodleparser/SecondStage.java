package com.polytech.moodleparser;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class SecondStage extends Application {
    Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) {
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: rgba(30,29,29,0.95)");

        Button button = new Button("Конвертировать");
        button.setMaxSize(150, 50);
        button.setId("my-button");
        button.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/ButtonStyle.css")).toExternalForm());

        Button buttonNew = new Button("Выбрать новый");
        buttonNew.setMaxSize(150, 50);
        buttonNew.setId("my-button-1");
        buttonNew.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/ButtonStyle.css")).toExternalForm());


        Label label = new Label();
        if (!Main.getFileName().equals("")) {
            label.setText("Загружен файл:\n" + Main.getFileName());
            label.setId("label-2");
            label.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/CSS/LabelStyle.css")).toExternalForm());
        }
        layout.setTop(label);
        layout.setCenter(button);
        layout.setBottom(buttonNew);

        BorderPane.setAlignment(label, Pos.TOP_CENTER);
        BorderPane.setAlignment(button, Pos.TOP_CENTER);
        BorderPane.setAlignment(buttonNew, Pos.CENTER);
        BorderPane.setMargin(buttonNew, new Insets(0, 0, 150, 0));

        Scene scene = new Scene(layout, 420, 420);
        stage.setTitle("Конвертация файла");
        stage.setScene(scene);
        stage.show();

        button.setOnAction(actionEvent -> {
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
                    label.setText("Сохранено");
                } catch (IOException e) {
                    label.setText("Ошибка");
                }
            }
        });

        buttonNew.setOnAction(actionEvent -> {
            stage.close();
            try {
                new Main().start( new Stage() );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}
