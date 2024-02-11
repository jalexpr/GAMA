package ru.textanalysis.tawt.gama.parser;

import java.util.List;

public interface GamaParser {

	void init();

	List<String> parserPhraseWithPunctuation(String bearingPhrase);

	List<List<String>> parserSentenceWithPunctuation(String sentence);

	List<String> getParserBearingPhrase(String bearingPhrase);

	List<List<String>> getParserSentence(String sentence);

	List<List<List<String>>> getParserParagraph(String sentence);

	List<List<List<List<String>>>> getParserText(String text);
}
