package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileCollectingFunctions {
    public static List<String> getAllWordsInFile(String filePath) throws IOException {
        List<String> words = new ArrayList<>();
        FileReader fr = new FileReader(filePath);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            int positionNumber = 0;
            for (String word : line.split("\\s")) {
                if (!word.isEmpty())
                    words.add(word);
            }
        }
        return words;
    }

    public static List<String> getFileLines(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while((line = bufferedReader.readLine()) != null){
            lines.add(line);
        }
        return lines;
    }
}
