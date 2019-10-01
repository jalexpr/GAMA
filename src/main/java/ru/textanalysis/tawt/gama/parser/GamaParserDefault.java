package ru.textanalysis.tawt.gama.parser;

import ru.textanalysis.tawt.graphematic.parser.text.ParserImpl;

import java.util.List;

public class GamaParserDefault implements IGamaParser {
    private ParserImpl parser = new ParserImpl();

    @Override
    public void init() {
    }

    @Override
    public List<String> getParserBearingPhrase(String bearingPhrase) {
        return parser.parserBasicsPhase(bearingPhrase.toLowerCase());
    }

    @Override
    public List<List<String>> getParserSentence(String sentence) {
        return parser.parserSentence(sentence.toLowerCase());
    }

    @Override
    public List<List<List<String>>> getParserParagraph(String sentence) {
        return parser.parserParagraph(sentence.toLowerCase());
    }

    @Override
    public List<List<List<List<String>>>> getParserText(String text) {
        return parser.parserText(text.toLowerCase());
    }
}
