package ru.textanalysis.tawt.gama.parser;

import ru.textanalysis.tawt.graphematic.parser.text.GParserImpl;
import ru.textanalysis.tawt.graphematic.parser.text.GraphematicParser;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GamaParserDefault implements GamaParser {

	private GraphematicParser parser;

	@Override
	public void init() {
		parser = new GParserImpl();
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

	public List<String> getParserBearingPhraseWithPunctuation(String bearingPhrase) {
		return parser.parserBasicsPhaseWithPunctuation(bearingPhrase);
	}

	public List<List<String>> getParserSentenceWithPunctuation(String sentence) {
		return parser.parserSentenceWithPunctuation(sentence);
	}

	public List<List<List<String>>> getParserParagraphWithPunctuation(String paragraph) {
		return parser.parserParagraphWithPunctuation(paragraph);
	}

	public List<List<List<List<String>>>> getParserTextWithPunctuation(String text) {
		return parser.parserTextWithPunctuation(text);
	}
}