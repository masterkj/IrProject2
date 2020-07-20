package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import preprocessing.TextProcess;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Button SearchButton;
    public TextArea SearchResults;
    public TextField Quary;
    private TextProcess tp ;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            tp = new TextProcess();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void SearchButton(ActionEvent actionEvent) throws IOException {
       SearchResults.setText(String.join(" ", tp.lemmatize(Quary.getText())));



    }

}
