package sample;

import indexing.Indexer;
import org.apache.commons.configuration.PropertiesConfiguration;
import preprocessing.TextProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class IndexBuilder {
    PropertiesConfiguration config;
    String corpusPath;
    Indexer indexer;
    FileReader fileReader;
    BufferedReader bufferedReader;
    List<String> splitedFile;
    TextProcess textProcess;

    public IndexBuilder() throws  IOException {
//        config = new PropertiesConfiguration();
//        config.load("Application.properties");
//        corpusPath = config.getString("corpus.path");
        corpusPath  = "D:/IR/corpus";
        indexer = new Indexer();
        textProcess = new TextProcess();
    }


    public void build() throws IOException {

        final File folder = new File(corpusPath);
        for (final File fileEntry : folder.listFiles()) {
            processFile(fileEntry);
//            if (sicel == 0) {
//                break;
//            }
//            sicel --;
            }
        indexer.build();

        }

    private void processFile(File fileEntry) throws IOException {
        FileReader fr = new FileReader(fileEntry);
        BufferedReader br = new BufferedReader(fr);
        String line;
        StringBuilder doc = new StringBuilder();
        while((line = br.readLine()) != null)
            doc.append(line);

        splitedFile = textProcess.lemmatize(Objects.requireNonNull(doc).toString());
        indexer.addLineToDoc(fileEntry.getName(), splitedFile);
        System.out.println(fileEntry.getName());
    }

    public Indexer getIxndexer() {
        return this.indexer;
    }
}
