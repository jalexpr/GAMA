package gama;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import parserstring.SentenceParser;

public class GamaParser {

    public static List<String> getParserBearingPhrase(String bearingPhrase) {

        return SentenceParser.getCharacteristicsOfOneSentence(bearingPhrase).get(0);
    }

    public static List<List<String>> getParserSentence(String sentence) {
        return SentenceParser.getCharacteristicsOfOneSentence(sentence);
    }

    public static List<List<List<String>>> getParserParagraph(String sentence) {
        throw new UnsupportedOperationException("Not supported yet."); //throw new Exception(String.format("String \"%s\" не является одним предложением", sentence));
    }

    public static List<List<List<List<String>>>> getParserText(String text) {
        throw new UnsupportedOperationException("Not supported yet."); //throw new Exception(String.format("String \"%s\" не является одним абзацем", paragraph));
    }

    public static boolean isOneWord(String word) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
