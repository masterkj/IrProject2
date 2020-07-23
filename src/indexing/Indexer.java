package indexing;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static utils.HelpingFunctions.sortByIntegerValue;
import static utils.HelpingFunctions.sortByValue;

public class Indexer {
    private Map<String, HashMap<String, Integer>> wordFrequency = new HashMap<>();
    private Map<String, Double> wordIdf = new HashMap<>();
    private Map<String, HashMap<String, Double>> index = new HashMap<>();
    private Map<String, Integer> docMaxTf = new HashMap<>();
    private final static double K = 0.4;


    private Map<String, Integer> docSizes = new HashMap<>();
    private Integer docsCount;

    public void addLineToDoc(String docName, List<String> line) {
        if (!wordFrequency.containsKey(docName))
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

    public void printIndex(String docName) {

        System.out.println(index.get(docName));
    }


    public void build() {
        this.docsCount = this.wordFrequency.size();
        wordFrequency.forEach((docName, freqMap) -> {
            Integer docSize = getDocSize(docName);
            index.put(docName, new HashMap<>());
            freqMap.forEach((word, freq) -> index.get(docName).put(word, calculateTf(freq, docSize, getMaxTf(docName)) * calculateIdf(word, docsCount)));
        });
//        printIndex("326.txt");
    }

    private Integer getMaxTf(String docName) {
        Integer res = null;
        if ((res = docMaxTf.get(docName)) != null)
            return res;
        for (Map.Entry<String, Integer> el : sortByIntegerValue(wordFrequency.get(docName)).entrySet()) {
            res = el.getValue();
        }
        docMaxTf.put(docName,res);

        return res;
    }

    private Integer getDocSize(String doc) {
        if (docSizes.getOrDefault(doc, null) != null)
            return docSizes.get(doc);
        AtomicReference<Integer> count = new AtomicReference<>(0);
        wordFrequency.get(doc).forEach((word, freq) -> {
            count.updateAndGet(v -> v + freq);
        });
        docSizes.put(doc, count.get());
        return count.get();
    }


    public static Double calculateTf(Integer freq, Integer docSize, Integer maxTf) {
        DecimalFormat df = new DecimalFormat("#.#########");
        Double d =  (freq *1.0) / docSize;
//        Double d = K + (((1 - K) * freq  / maxTf + 1.0));
        d = Double.parseDouble(df.format(d));
//        System.out.println("non-normalized: "+freq+" docsSize: "+docSize+" , normalized: "+d+ " doc size: "+ docSize);
        return d;
    }

    private Double calculateIdf(String word, Integer docsCount) {
        Double res;
        if ((res = wordIdf.getOrDefault(word, null)) != null)
            return res;
        Integer df = this.getDf(word);
//        System.out.println("word: "+ word + " docsCount: "+docsCount+" df: "+df+" idf: "+ Math.log(docsCount * 1.0 / (df + 1)));
        Double idf = Math.log((docsCount * 1.0 / (df + 1)));
//        Double idf = Math.log((docsCount + 0.5) / df) / (Math.log(docsCount + 1.0));
        wordIdf.put(word, idf);
        return idf;
    }

    public Double getIdf(String word) {
        return wordIdf.getOrDefault(word, 0.0);
    }

    private Integer getDf(String word) {
        AtomicReference<Integer> WordGloballyFreq = new AtomicReference<Integer>(0);
        wordFrequency.forEach((doc, freqMap) -> {
            WordGloballyFreq.updateAndGet(v -> freqMap.getOrDefault(word, -1) != -1 ? v + 1 : v);
        });
        return WordGloballyFreq.get();
    }

    public Map<String, HashMap<String, Double>> getIndex() {
        return index;
    }

}
