package sample;

import indexing.Indexer;
import preprocessing.TextProcess;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryProcessing {
    private TextProcess textProcess;
    private Indexer indexer;

    public QueryProcessing(IndexBuilder indexBuilder) throws IOException {
        textProcess = new TextProcess();
        this.indexer = indexBuilder.getIxndexer() ;
    }

    public Map<String, Double> process(String query) {
        List<String> processedText = textProcess.lemmatize(query);
        System.out.println(processedText);
        return calculateQueryWeight(processedText);
    }

    private Map<String, Double> calculateQueryWeight(List<String> processedQuery) {
        Map<String, Double> wordTF;
        wordTF = queryWordTf(processedQuery);
        Map<String, Double> queryWeight = new HashMap<>();
        wordTF.forEach((word, tf) -> queryWeight.put(word, tf* this.indexer.getIdf(word)));
        return queryWeight;
    }

    private Map<String, Double> queryWordTf(List<String> processedQuery) {
        Map<String, Double> wordFreq = new HashMap<>();
        processedQuery.forEach(word -> {
            if(wordFreq.getOrDefault(word, null) == null)
                wordFreq.put(word, 1.0);
            else
                wordFreq.put(word, wordFreq.get(word) + 1);
        });

        // calculate TF
        wordFreq.forEach((word, freq) -> {
            wordFreq.put(word, freq / wordFreq.size());
        });
        return wordFreq;
    }
}
