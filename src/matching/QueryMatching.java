package matching;

import indexing.Indexer;
import sample.IndexBuilder;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static utils.HelpingFunctions.sortByValue;

public class QueryMatching {
    private Indexer index;
    private Map<String, Double> docsSimilarity;
    private final int BEST_RESULTS = 5;

    public QueryMatching(IndexBuilder indexBuilder) {
        index = indexBuilder.getIxndexer();
        docsSimilarity = new HashMap<>();
    }
    public List<String> match(Map<String, Double> queryVector) {
        List<String> queryResult = new ArrayList<>();
        index.getIndex().forEach((doc, vector) -> {
            docsSimilarity.put(doc, cosineSimilarity(queryVector, vector));
        });
        docsSimilarity = sortByValue((HashMap<String, Double>) docsSimilarity);
        int x = BEST_RESULTS;
        for (Map.Entry<String, Double> el : docsSimilarity.entrySet()){
            if(x == 0)
                break;
            queryResult.add(el.getKey());
            x--;
        }
        return queryResult;
    }

    private Double cosineSimilarity(Map<String, Double> queryVector, HashMap<String, Double> docVector) {
        AtomicReference<Double> dotProd = new AtomicReference<>(0.0);
        queryVector.forEach((word, weight) -> {
            dotProd.updateAndGet(v -> v + weight * docVector.getOrDefault(word, 0.0));
        });
        return dotProd.get() / (vectorTall(queryVector) * vectorTall(docVector));
    }

    private Double vectorTall(Map<String, Double> vector) {
        Collection<Double> vecComponents;
        vecComponents = vector.values();
        AtomicReference<Double> unSquartedResulted = new AtomicReference<>(0.0);
        vecComponents.forEach(e -> {
            unSquartedResulted.updateAndGet(v -> v + e * e);
        });
        return Math.sqrt(unSquartedResulted.get());
    }

}
