package evaluation;

import matching.QueryMatching;
import sample.IndexBuilder;
import sample.QueryProcessing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QueriesResultFile {

    private final QueryProcessing queryProcessing;
    private final QueryMatching queryMatching;
    private final String cleanQueriesFilePath = "D:/IR/Resources/cleanQueries.txt";
    private final String systemOutFile = "D:/IR/Evaluation/out.txt";
    private final IndexBuilder indexBuilder;

    public QueriesResultFile() throws IOException {
        indexBuilder = new IndexBuilder();
        queryProcessing = new QueryProcessing(indexBuilder);
        queryMatching = new QueryMatching(indexBuilder);
    }

    public void calculateQueriesResultFile() throws IOException {
        indexBuilder.build();
        List<String> queries = readAllQueries();
        List<String> res = new ArrayList<>();
        for (String query : queries) {
            res.add(getQueryResult(query));
        }
        writeResult(res);
    }

    private void writeResult(List<String> res) throws IOException {
        FileWriter fr =  new FileWriter(systemOutFile);
        BufferedWriter br = new BufferedWriter(fr);
        for (String queryResult : res) {
            br.write(queryResult);
            br.newLine();
        }
        br.flush();
    }

    private String getQueryResult(String query) {
        List<String> res = queryMatching.match(queryProcessing.process(query));
        return formatQueryResultString(res);
    }

    private String formatQueryResultString(List<String> res) {
        res = res.stream()
                .map(queryRes -> queryRes.replace(".txt", ""))
                .collect(Collectors.toList());
        String resString = res.toString().replace("[", "");
        resString = resString.replace("]", "");
        resString = resString.replaceAll(",", "");
        return resString;
    }

    private List<String> readAllQueries() throws IOException {
        List<String> queries = new ArrayList<>();
        FileReader fr = new FileReader(cleanQueriesFilePath);
        BufferedReader br = new BufferedReader(fr);
        String line;
        StringBuilder query = new StringBuilder();
        while((line = br.readLine()) != null){
            if(line.equals("----")){
                queries.add(query.toString());
                query = new StringBuilder();
                line = "";
            }
            query.append(line);
        }
        return queries;
    }
}
