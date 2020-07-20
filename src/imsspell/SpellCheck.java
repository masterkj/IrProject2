package imsspell;

import com.swabunga.spell.engine.SpellDictionary;
import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.*;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SpellCheck implements SpellCheckListener {
    private List<String> misspelled;
    private SpellChecker checker = null;

    public SpellCheck() throws IOException {
//        PropertiesConfiguration configuration = new PropertiesConfiguration();
//        configuration.load("Application.properties");
//        String dictFile = configuration.getString("dict.path");
        String dictFile = "F:/IR/Resources/english.0/english.0";

        misspelled = new ArrayList<>();
        SpellDictionary dictionary = new SpellDictionaryHashMap(new File(dictFile));
        checker = new SpellChecker(dictionary);
        checker.addSpellCheckListener(this);
    }

    public void getMisspelled(String txt) {

        checker.checkSpelling(new StringWordTokenizer(txt));

        Iterator it = misspelled.iterator();
        while (it.hasNext()) {
            System.out.println("misspelled: " + it.next());
        }
        System.out.println();

    }

    public void spellingError(SpellCheckEvent event) {
//        event.ignoreWord(true);
        misspelled.add(event.getInvalidWord() + "   "+ checker.getSuggestions(event.getInvalidWord(), 2));
    }


}
