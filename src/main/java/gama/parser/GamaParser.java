package gama.parser;

import java.util.List;

public class GamaParser {

    private static GamaParserInterface parser;

    static {
        init();
    }

    private GamaParser() {}

    private static void init() {
        GamaParser.init(new GamaParserDefault());
    }

    private static void init(GamaParserInterface parser) {
        GamaParser.parser = parser;
    }

    public static List<String> getParserBearingPhrase(String bearingPhrase) {
        return parser.getParserBearingPhrase(bearingPhrase.toLowerCase());
    }

    public static List<List<String>> getParserSentence(String sentence) {
        return parser.getParserSentence(sentence.toLowerCase());
    }

    public static List<List<List<String>>> getParserParagraph(String sentence) {
        return parser.getParserParagraph(sentence.toLowerCase());
    }

    public static List<List<List<List<String>>>> getParserText(String text) {
        return parser.getParserText(text.toLowerCase());
    }

}
