package gama;

import java.util.List;
import parserstring.Parser;

public class GamaParser {

    public static List<String> getParserBearingPhrase(String bearingPhrase) {
        return Parser.parserBasicsPhase(bearingPhrase.toLowerCase());
    }

    public static List<List<String>> getParserSentence(String sentence) {
        return Parser.parserSentence(sentence.toLowerCase());
    }

    public static List<List<List<String>>> getParserParagraph(String sentence) {
        return Parser.parserParagraph(sentence.toLowerCase());
    }

    public static List<List<List<List<String>>>> getParserText(String text) {
        return Parser.parserText(text.toLowerCase());
    }
}
