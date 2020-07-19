package preprocessing.stemmer;

import opennlp.tools.stemmer.PorterStemmer;

import java.util.List;

public class Stemmer {
    private PorterStemmer _stemmer;

    public Stemmer(){
        _stemmer = new PorterStemmer();
    }

    public String stem(String text) {
        return _stemmer.stem(text);
    }

    public List<String> stemLine(List<String> line) {
        line.replaceAll(word -> stem(word));
        return  line;
    }
}
