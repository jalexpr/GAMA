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
		return new LinkedList<>(Arrays.asList(bearingPhrase.split("(?<=(\n))|((?<![\\p{L}\\p{N}_-])|(?![\\p{L}\\p{N}_-]))((?<![,./])|(?!(\\p{N})))")));
	}

	public List<List<String>> getParserSentenceWithPunctuation(String sentence) {
		List<List<String>> phraseList = new LinkedList<>();

		for (String basicsPhase : sentence.split("((?=(\n))|((?<=[,.!?–;:])(?!(\\p{N})))|((?=[,.!?–;:\n])(?<!(\\p{N}))))")) {
			phraseList.add(getParserBearingPhraseWithPunctuation(basicsPhase));
		}

		return phraseList;
	}

	public List<List<List<String>>> getParserParagraphWithPunctuation(String paragraph) {
		List<List<List<String>>> sentenceList = new LinkedList<>();

		for (String sentence : paragraph.split("((?=(\n))|((?<=[.!?])(?!(\\p{N})))|((?=[.!?\n])(?<!(\\p{N}))))")) {
			sentenceList.add(getParserSentenceWithPunctuation(sentence));
		}

		return sentenceList;
	}

	public List<List<List<List<String>>>> getParserTextWithPunctuation(String text) {
		List<List<List<List<String>>>> paragraphList = new LinkedList<>();

		for (String paragraph : text.split("(?=(\n))")) {
			paragraphList.add(getParserParagraphWithPunctuation(paragraph));
		}

		return paragraphList;
	}
}
