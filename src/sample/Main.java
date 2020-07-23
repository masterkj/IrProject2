package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import preprocessing.TextProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 700, 450));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException {

        launch(args);

//        TextProcess textProcess = new TextProcess();
//        List<String> splitedFile;
//        splitedFile = textProcess.lemmatize(Objects.requireNonNull(" NUMBER OF TROOPS THE UNITED STATES HAS STATIONED IN SOUTH\n" +
//                "\n" +
//                "VIET NAM AS COMPARED WITH THE NUMBER OF TROOPS IT HAS STATIONED\n" +
//                "\n" +
//                "IN WEST GERMANY ."));
//        System.out.println(splitedFile);

    }
}
