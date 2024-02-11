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

	public List<List<String>> parserSentenceWithPunctuation(String sentence) throws NotParserTextException {
		List<List<String>> sentenceList = new LinkedList<>();

		for (String basicsPhase : sentence.split("((?=(\n))|((?<=[,.!?–;:])(?!(\\p{N})))|((?=[,.!?–;:\n])(?<!(\\p{N}))))")) {
			System.out.println(basicsPhase);
			sentenceList.add(parserPhraseWithPunctuation(basicsPhase));
		}

		return sentenceList;
	}

	public List<String> parserPhraseWithPunctuation(String basicsPhase) throws NotParserTextException {
		return new LinkedList<>(Arrays.asList(basicsPhase.split("(?<=(\n))|((?<![\\p{L}\\p{N}_-])|(?![\\p{L}\\p{N}_-]))((?<![,./])|(?!(\\p{N})))")));
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
