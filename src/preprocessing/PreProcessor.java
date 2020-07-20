package preprocessing;

import preprocessing.irregularVerb.IrregularVerbs;
import preprocessing.stemmer.Stemmer;

import java.io.IOException;
import java.util.List;

public class PreProcessor {

    Stemmer stemmer;
    IrregularVerbs irregularVerbs;
    StopWordsRemoval stopWordsRemoval;
    public PreProcessor() throws IOException {
        stemmer = new Stemmer();
        irregularVerbs = new IrregularVerbs();
        stopWordsRemoval = new StopWordsRemoval();
    }

    public List<String> linePreProcess(List<String> line){
        line = stopWordsRemoval.removeStopWords(line);
        line = irregularVerbs.getInfinitive(line);
        line = stemmer.stemLine(line);
        return line;
    }
}
