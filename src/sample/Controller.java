package sample;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Button btn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void btn(ActionEvent actionEvent) throws IOException {
        IndexBuilder indexBuilder = new IndexBuilder();
        indexBuilder.build();
    }
}
