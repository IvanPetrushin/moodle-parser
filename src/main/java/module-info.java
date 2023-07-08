module com.polytech.moodleparser {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.ooxml;
    requires java.xml;


    opens com.polytech.moodleparser to javafx.fxml;
    exports com.polytech.moodleparser;
    exports com.polytech.moodleparser.docxConfig;
    opens com.polytech.moodleparser.docxConfig to javafx.fxml;
    exports com.polytech.moodleparser.parser;
    opens com.polytech.moodleparser.parser to javafx.fxml;
}