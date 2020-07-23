package preprocessing;

import java.util.regex.Pattern;

import static preprocessing.SimilarWords.*;

public class AbbreviationReplace {
    public String process(String documentText) {
//        documentText = processCurrency(documentText);
    //    documentText = processCurrency1(documentText);
        documentText = processCommons(documentText);
        documentText = processCountry(documentText);
        return documentText;
    }

    private String processCountry(String documentText) {
        for (int i = 0; i < country.size(); i++) {
            documentText = documentText.replaceAll(country.get(i).toLowerCase(), countryShort.get(i));
        }
        return documentText;
    }

    private String processCommons(String documentText) {

        for (int i = 0; i < Commons.size(); i++) {
            documentText = documentText.replaceAll(Commons.get(i).toLowerCase(), ShortCommon.get(i));
        }
        return documentText;
    }

    private String processCurrency1(String documentText) {
        for (int i = 0; i < currencyCountry.size(); i++) {
            documentText = documentText.replaceAll(currencyCountry.get(i).toLowerCase(), currencyNames.get(i));
        }
        return documentText;
    }

    private String processCurrency(String documentText) {
        for (int i = 0; i < shortCurrency.size(); i++) {
            documentText = documentText.replaceAll(shortCurrency.get(i).toLowerCase(), currencySymbol.get(i));
            documentText = documentText.replaceAll(Currency.get(i).toLowerCase(), currencySymbol.get(i));
        }
        return documentText;
    }
}
