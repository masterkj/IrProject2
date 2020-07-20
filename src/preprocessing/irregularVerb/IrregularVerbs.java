package preprocessing.irregularVerb;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IrregularVerbs {

    /** The infinitive forms of the irregular verbs.*/
    private static ArrayList<String> inf = new ArrayList<String>();
    /** The simple past forms of the irregular verbs.*/
    private static ArrayList<String> sp = new ArrayList<String>();
    /** The past participle forms of the irregular verbs.*/
    private static ArrayList<String> pp = new ArrayList<String>();

    public IrregularVerbs() {
//        PropertiesConfiguration config = new PropertiesConfiguration();
//        config.load("Application.properties");


//        loadVerbs(config.getString("irregularVerb.path"));
        loadVerbs("F:/IR/Resources/irregular-verbs.txt");

    }


    public boolean loadVerbs(String filename) {
        File file = new File(filename);

        try {
            BufferedReader in = new BufferedReader(new FileReader(file));

            // read (infinitive, simple past, past participle) triples
            String[] verb;
            while (in.ready()) {
                verb = in.readLine().split(" ");

                inf.add(verb[0]);
                sp.add(verb[1]);
                pp.add(verb[2]);
            }

            in.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    private static boolean matches(String word, String words) {
        String[] list = words.split("/");

        for (String elem : list)
            if (elem.toLowerCase().equals(word.toLowerCase())) return true;
        return false;
    }

    private static int getIndex(String verb) {
        for (int i = 0; i < inf.size(); i++)
            if (matches(verb, inf.get(i)) ||
                    matches(verb, sp.get(i)) ||
                    matches(verb, pp.get(i)))
                return i;

        return -1;
    }

    public String getInfinitive(String verb) {
        int index = getIndex(verb);

        if (index == -1) return verb;
        else return inf.get(index);
    }

    public List<String> getInfinitive(List<String> line) {
        line.replaceAll(word -> getInfinitive(word));
        return line;
    }
}
