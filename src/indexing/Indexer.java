package indexing;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Indexer {
     private Map<String, HashMap<String, Integer>> wordFrequency = new HashMap<>();
     private Map<String, HashMap<String, Double>> tf = new HashMap<>();
     private Map<String, Double> idf = new HashMap<>();
     private Map<String, HashMap<String, Double>> index = new HashMap<>();
     private Long corpusSize = null;
     private Map<String, Integer> docSizes = new HashMap<>();

    public void addLineToDoc(String docName, List<String> line) {
        if(!wordFrequency.containsKey(docName))
            wordFrequency.put(docName, new HashMap<String, Integer>());

        for (String word : line) {
            incrementWordFreq(word, docName);
        }
    }

    private void incrementWordFreq(String word, String docName) {
        Integer currentWordFreq = wordFrequency.get(docName).getOrDefault(word, 0);
        ++currentWordFreq;
        wordFrequency.get(docName).put(word, currentWordFreq);
    }

    public void printIndex() {
        System.out.println(wordFrequency);
    }


    public void build() {
        wordFrequency.forEach((docName,freqMap) -> {
            setTf(docName);
            freqMap.forEach((word, freq) -> {
                setIdf(word);
            });
        });
        tf.forEach((doc, tfMap) -> {
            setTfIdf(doc, tfMap);
        });
        System.out.println(index);

    }

    private void setTfIdf(String doc, HashMap<String, Double> tfMap) {
        index.put(doc, new HashMap<>());
        tf.get(doc).forEach((word, tfValue) -> {
            Double idfValue = this.idf.get(word);
            index.get(doc).put(word,calculateTfIdf(idfValue, tfValue));
        });
    }


    private void setTf(String doc) {
        tf.put(doc, new HashMap<>());
        wordFrequency.get(doc).forEach((word,freq) ->
            tf.get(doc).put(word, calculateTf(freq, getDocSize(doc)))
        );
    }

    private Integer getDocSize(String doc) {
        if(docSizes.getOrDefault(doc, null) != null)
            return docSizes.get(doc);
        AtomicReference<Integer> count = new AtomicReference<>(0);
        wordFrequency.get(doc).forEach((word,freq) -> {
            count.updateAndGet(v -> v + freq);
        });
        docSizes.put(doc, count.get());
        return count.get();
    }


    private void setIdf(String word) {
        if(idf.containsKey(word))
            return;
        AtomicReference<Long> WordGloballyFreq = new AtomicReference<Long>(0L) ;
        wordFrequency.forEach((doc, freqMap) -> {
            WordGloballyFreq.updateAndGet(v -> v + freqMap.getOrDefault(word, 0));
        });
        Double idf = calculateIdf(WordGloballyFreq.get(), corpusSize());
        this.idf.put(word, idf);
    }

    private Double calculateTf(Integer freq, Integer docSize) {
        DecimalFormat df = new DecimalFormat("#.#########");
        Double d =  (freq *1.0) / docSize;
        d = Double.parseDouble(df.format(d));
        System.out.println("freq: " + freq + " doc size: " + docSize + " res: " + df.format(d));
        return d;
    }

    private Double calculateIdf(Long wordGlobalFreq, Long corpusSize) {
        return Math.log(corpusSize * 1.0 / wordGlobalFreq);
    }

    private Double calculateTfIdf(Double idfValue, Double tfValue) {
        return idfValue * tfValue;
    }

    private Long corpusSize() {
        if(this.corpusSize != null)
            return this.corpusSize;
        AtomicReference<Long> corpusSize = new AtomicReference<>(0L);
        wordFrequency.forEach((doc, freqMap) -> {
            corpusSize.updateAndGet(v -> v + getDocSize(doc));
        });
        return corpusSize.get();
    }
}
