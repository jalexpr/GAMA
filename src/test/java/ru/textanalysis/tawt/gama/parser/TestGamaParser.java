package ru.textanalysis.tawt.gama.parser;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGamaParser {

	private static GamaParserDefault gamaParserDefault = new GamaParserDefault();
	private static final HashMap<String, String[]> getParserBearingPhraseData = new HashMap<>();
	private static final HashMap<String, String[][]> getParserSentenceData = new HashMap<>();

	@BeforeAll
	public static void setUpGetParserBearingPhrase() {
		gamaParserDefault.init();
		getParserBearingPhraseData.put("Я иду один по городу!", new String[] {"я", "иду", "один", "по", "городу"});
		getParserBearingPhraseData.put("Я иду, шагаю по Москве!", new String[] {"я", "иду", "шагаю", "по", "москве"});
		getParserSentenceData.put("Я иду один по городу!", new String[][] {{"я", "иду", "один", "по", "городу"}});
		getParserSentenceData.put("Я иду, шагаю по Москве!", new String[][] {{"я", "иду"}, {"шагаю", "по", "москве"}});
		getParserSentenceData.put("Я иду, шагаю по Москве! И мне хорошо.", new String[][] {{"я", "иду"}, {"шагаю", "по", "москве"}, {"и", "мне", "хорошо"}});
	}

	@AfterAll
	public static void tearDownGetParserBearingPhrase() {
		getParserBearingPhraseData.clear();
	}

	@Test
	public void testGetParserBearingPhrase() {
		getParserBearingPhraseData.forEach((testData, expected) -> {
			final List<String> actual = gamaParserDefault.getParserBearingPhrase(testData);
			assertEquals(expected.length, actual.size());
			for (int i = 0; i < actual.size(); i++) {
				assertEquals(expected[i], actual.get(i));
			}
		});
	}

	@Test
	public void testGetParserSentence() {
		getParserSentenceData.forEach((testData, expected) -> {
			final List<List<String>> actual = gamaParserDefault.getParserSentence(testData);
			assertEquals(expected.length, actual.size());
			for (int i = 0; i < actual.size(); i++) {
				List<String> actualGetIndexI = actual.get(i);
				for (int j = 0; j < actualGetIndexI.size(); j++) {
					assertEquals(expected[i][j], actualGetIndexI.get(j));
				}
			}
		});
	}
}
