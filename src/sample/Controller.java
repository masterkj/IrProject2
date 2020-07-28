package sample;

import imsspell.SpellCheck;
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
    public TextArea Suggestions;
    public TextArea Quary;
    private QueryProcessing queryProcessing;
    private IndexBuilder indexBuilder;
    private QueryMatching queryMatching;
    private SpellCheck sc;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            sc = new SpellCheck();
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
        String textCorrection = sc.getMisspelled(Quary.getText()).toString().toLowerCase();
        Suggestions.setText(textCorrection);
        SearchResults.setText(res.toString());
    }
}
