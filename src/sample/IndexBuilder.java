package sample;

import indexing.Indexer;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import preprocessing.PreProcessor;
import preprocessing.TextProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class IndexBuilder {
    PropertiesConfiguration config;
    String corpusPath;
    PreProcessor preProcessor;
    Indexer indexer;
    FileReader fileReader;
    BufferedReader bufferedReader;
    List<String> splitedLine;
    TextProcess textProcess;

    IndexBuilder() throws  IOException {
//        config = new PropertiesConfiguration();
//        config.load("Application.properties");
//        corpusPath = config.getString("corpus.path");
        corpusPath  = "D:/IR/corpus";
        preProcessor = new PreProcessor();
        indexer = new Indexer();
        textProcess = new TextProcess();
    }


    public void build() throws IOException {
        int sicel = 2;
        final File folder = new File(corpusPath);
        for (final File fileEntry : folder.listFiles()) {
            processFile(fileEntry);
            if (sicel == 0) {
                break;
            }
            sicel --;
            }
        indexer.build();
        }

    private void processFile(File fileEntry) throws IOException {
        FileReader fr = new FileReader(fileEntry);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null)
            processLine(line, fileEntry.getName());
        System.out.println(fileEntry.getName());
    }

    private void processLine(String line, String fileName) {
//        line = HelpingFunctions.removeDecimeters(line);
//        List<String> splitedLine = (new ArrayList<String>(Arrays.asList(line.toLowerCase().split("\\s"))));

//        splitedLine = preProcessor.linePreProcess(splitedLine);
        splitedLine = textProcess.lemmatize(line.toLowerCase());
        indexer.addLineToDoc(fileName, splitedLine);
    }
}
