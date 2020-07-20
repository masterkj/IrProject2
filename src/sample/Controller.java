package sample;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import matching.QueryMatching;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public Button SearchButton;
    public TextArea SearchResults;
    public TextField Quary;
    private QueryProcessing queryProcessing;
    private IndexBuilder indexBuilder;
    private QueryMatching queryMatching;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            indexBuilder = new IndexBuilder();
            indexBuilder.build();
            queryProcessing = new QueryProcessing(indexBuilder);
            queryMatching = new QueryMatching(indexBuilder);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void SearchButton(ActionEvent actionEvent) throws IOException {
        List<String> res = queryMatching.match(queryProcessing.process(Quary.getText()));
        SearchResults.setText(res.toString());

    }

}
