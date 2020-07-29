package evaluation;

import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.FileCollectingFunctions.getFileLines;

public class Evaluation {
    private QueriesResultFile queriesResultFile;
    private final String systemOutFile = "D:/IR/Evaluation/out.txt";
    private final String goldenRulesFile = "D:/IR/Evaluation/cleanRelevance.txt";


    public Evaluation() throws IOException {
        queriesResultFile = new QueriesResultFile();
    }

    public void calculateQueriesResultFile() throws IOException {
        queriesResultFile.calculateQueriesResultFile();
    }

    public void evaluateSystem() throws IOException {
        List<String> systemOut = getFileLines(systemOutFile);
        List<String> goldenRules = getFileLines(goldenRulesFile);
        Double totalValue = 0.0;
        List<Double> res;
        Double avgPrecision = 0.0;
        Double avgRecall = 0.0;
        Double avgF = 0.0;
        Integer ignored = 0;

        for (int i = 0; i < systemOut.size(); i++) {
            res = evaluateQuery(systemOut.get(i), goldenRules.get(i), goldenRules.size());
            avgPrecision += res.get(0);
            avgRecall += res.get(1);
            avgF += res.get(2);
        }
        System.out.println("Precision: " + avgPrecision / (goldenRules.size()));
        System.out.println("Recall: " + (avgRecall / (goldenRules.size())));
        System.out.println("F: " + (avgF / (goldenRules.size())));
    }

    private List<Double> evaluateQuery(String querySystemRes, String goldenQueryRole, int filesCount) {
        List<String> querySystemResList = Arrays.asList(querySystemRes.split("\\s"));
        List<String> goldenQueryRoleList = Arrays.asList(goldenQueryRole.split("\\s"));

        List<Double> res = new ArrayList<>();
        Integer numOfReleventThatRetrived = calculateNumOfReleventThatRetrived(querySystemResList, goldenQueryRoleList);
        Double Precision = numOfReleventThatRetrived * 1.0 / querySystemResList.size();// (10 || 20 in our case)
        Double Recall = numOfReleventThatRetrived * 1.0 / goldenQueryRoleList.size();
        System.out.println("Precision: "+ Precision+ " Recall: "+ Recall + " numOfReleventThatRetrived: "+ numOfReleventThatRetrived+ " goldenQueryRoleList.size(): "+  goldenQueryRoleList.size() );
        Double f = 1 / (0.5 * ((1 / Precision) + (1 / Recall)));
        res.add(Precision);
        res.add(Recall);
        res.add(f);

        return res;
    }

    private Integer calculateNumOfReleventThatRetrived(List<String> querySystemResList, List<String> goldenQueryRoleList) {
        int res = 0;
        for (String goldenRoleFileName : goldenQueryRoleList) {
            if (querySystemResList.contains(goldenRoleFileName))
                res++;
        }
        return res;
    }


}
