package indexing;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Indexer {
     private Map<String, HashMap<String, Integer>> wordFrequency = new HashMap<>();
     private Map<String, Double> wordIdf = new HashMap<>();
     private Map<String, HashMap<String, Double>> index = new HashMap<>();
     private Map<String, Integer> docSizes = new HashMap<>();
     private Integer docsCount;

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
        System.out.println(index);
    }


    public void build() {
        this.docsCount = this.wordFrequency.size();
        wordFrequency.forEach((docName,freqMap) -> {
            Integer docSize = getDocSize(docName);
            index.put(docName, new HashMap<>());
            freqMap.forEach((word, freq) -> index.get(docName).put(word,calculateTf(freq, docSize) * calculateIdf(word, docsCount)));
        });
        printIndex();
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


    private Double calculateTf(Integer freq, Integer docSize) {
        DecimalFormat df = new DecimalFormat("#.#########");
        Double d =  (freq *1.0) / docSize;
        d = Double.parseDouble(df.format(d));
        System.out.println("non-normalized: "+freq+" docsSize: "+docSize+" , normalized: "+d+ " doc size: "+ docSize);
        return d;
    }

    private Double calculateIdf(String word, Integer docsCount) {
        Double res;
        if((res = wordIdf.getOrDefault(word, null))!= null)
            return res;
        Integer df = this.getDf(word);
        System.out.println("word: "+ word + " docsCount: "+docsCount+" df: "+df+" idf: "+ Math.log(docsCount * 1.0 / (df + 1)));
        return Math.log(docsCount * 1.0 / (df + 1));
    }

    public Double getIdf(String word) {
        return wordIdf.getOrDefault(word, 0.0);
    }

    private Integer getDf(String word) {
        AtomicReference<Integer> WordGloballyFreq = new AtomicReference<Integer>(0) ;
        wordFrequency.forEach((doc, freqMap) -> {
            WordGloballyFreq.updateAndGet(v -> freqMap.getOrDefault(word, -1) != -1 ? v + 1 : v);
        });
        return WordGloballyFreq.get();
    }

}
