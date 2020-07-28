package preprocessing;/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ITE_Syria
 */

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class TextProcess {

    protected StanfordCoreNLP pipeline;
    List<String> stopwords = new ArrayList<String>();
    PropertiesConfiguration config;
    AbbreviationReplace abbreviationReplace;

    public TextProcess() throws IOException {
        // Create StanfordCoreNLP object properties, with POS tagging
        // (required for lemmatization), and lemmatization
//        config = new PropertiesConfiguration();
//        config.load("Application.properties");

        stopwords = Files.readAllLines(Paths.get("D:/IR/Resources/stop-words.txt"));

        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");

        this.pipeline = new StanfordCoreNLP(props);
        abbreviationReplace = new AbbreviationReplace();
    }

    public List<String> lemmatize(String documentText) {
        documentText = documentText.toLowerCase();
        documentText = abbreviationReplace.process(documentText);
        documentText = documentText.toLowerCase();
        List<String> lemmas = new ArrayList<>();
        // Create an empty Annotation just with the given text
        Annotation document = new Annotation(documentText);
        // run all Annotators on this text
        this.pipeline.annotate(document);
        // Iterate over all of the sentences found
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            // Iterate over all tokens in a sentence

            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
                // Retrieve and add the lemma for each word into the
                // list of lemmas

                if (!stopwords.contains(token.originalText().toUpperCase())) {
                    //    if (token.originalText().length() > 0 && token.originalText().length() < 20) {

                    String word = token.originalText();
                    boolean IncorrectFormula = false;

                    for (int j = 0; j < word.length(); j++) {
                        char x = word.charAt(j);
                        if (Character.isLetter(x)) {

                        } else {
                            if (Character.isDigit(x)) {
                                IncorrectFormula = true;
                            }

                            IncorrectFormula = true;
                        }

                    }
                    if (!IncorrectFormula || Validatore.DateValidator(word) || Validatore.emailValidator(word) || Validatore.UrlValidator(word)) {
                        lemmas.add(token.get(LemmaAnnotation.class));

                    }
                    //  lemmas.add(token.get(LemmaAnnotation.class));

                }///////////////////

                //  System.out.println(token.originalText());
                // }
            }
        }
        return lemmas;

    }

    public List<String> lemmatize2(String documentText) {
        List<String> lemmas = new LinkedList<String>();
        // Create an empty Annotation just with the given text
        Annotation document = new Annotation(documentText);
        // run all Annotators on this text
        this.pipeline.annotate(document);
        // Iterate over all of the sentences found
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            // Iterate over all tokens in a sentence

            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
                // Retrieve and add the lemma for each word into the
                // list of lemmas
                lemmas.add(token.get(LemmaAnnotation.class));

                //  System.out.println(token.originalText());
            }
        }
        return lemmas;
    }

}
;