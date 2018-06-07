package gama.parser;

import parserstring.Parser;

import java.util.List;

class GamaParserDefault implements GamaParserInterface {

    @Override
    public List<String> getParserBearingPhrase(String bearingPhrase) {
        return Parser.parserBasicsPhase(bearingPhrase.toLowerCase());
    }

    @Override
    public List<List<String>> getParserSentence(String sentence) {
        return Parser.parserSentence(sentence.toLowerCase());
    }

    @Override
    public List<List<List<String>>> getParserParagraph(String sentence) {
        return Parser.parserParagraph(sentence.toLowerCase());
    }

    @Override
    public List<List<List<List<String>>>> getParserText(String text) {
        return Parser.parserText(text.toLowerCase());
    }

}
