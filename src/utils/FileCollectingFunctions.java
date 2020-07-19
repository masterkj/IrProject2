package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
}
