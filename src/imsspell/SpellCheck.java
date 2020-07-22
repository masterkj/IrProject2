package imsspell;

import com.swabunga.spell.engine.SpellDictionary;
import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellCheckEvent;
import com.swabunga.spell.event.SpellCheckListener;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
public class SpellCheck implements SpellCheckListener {
    private List<String> misspelled;
    private SpellChecker checker = null;
    public SpellCheck() throws IOException {
        String dictFile = "D:/IR/Resources/english.0/english.0";
        misspelled = new ArrayList<>();
        SpellDictionary dictionary = new SpellDictionaryHashMap(new File(dictFile));
        checker = new SpellChecker(dictionary);
        checker.addSpellCheckListener(this);
    }
    public List<String> getMisspelled(String txt) {
        misspelled.clear();
        checker.checkSpelling(new StringWordTokenizer(txt));
       /* for (:) {

        }*/
        Iterator it = misspelled.iterator();
        while (it.hasNext()) {
            System.out.println("misspelled: " + it.next());
        }
        return misspelled;
    }
    public void spellingError(SpellCheckEvent event) {
//        event.ignoreWord(true);
        misspelled.add(event.getInvalidWord() + "   "+ checker.getSuggestions(event.getInvalidWord(), 2));
    }
}
