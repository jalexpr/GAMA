
import gama.GamaParser;
import java.util.HashMap;
import java.util.List;
import org.junit.*;

public class TestGamaParser {

    private final HashMap<String, String[]> getParserBearingPhraseData = new HashMap<>();

    @Before
    public void setUpGetParserBearingPhrase() {
        getParserBearingPhraseData.put("Я иду один по городу!", new String[]{"я", "иду", "один", "по", "городу"});
        getParserBearingPhraseData.put("Я иду, шагаю по Москве!", new String[]{"я", "иду", "шагаю", "по", "москве"});
    }

    @After
    public void tearDownGetParserBearingPhrase() {
        getParserBearingPhraseData.clear();
    }

    @Test
    public void testGetParserBearingPhrase() {
        getParserBearingPhraseData.entrySet().forEach((entry) -> {
            final String[] expected = entry.getValue();
            final String testData = entry.getKey();
            final List<String> actual = GamaParser.getParserBearingPhrase(testData);
            assertEquals(expected.length, actual.size());
            for(int i = 0; i < actual.size(); i++){
                assertEquals(expected[i], actual.get(i));
            }
        });
    }
    
    
    private final HashMap<String, String[][]> getParserSentenceData = new HashMap<>();
    
    @Before
    public void setUpGetParserSentence() {
        getParserSentenceData.put("Я иду один по городу!", new String[][]{{"я", "иду", "один", "по", "городу"}});
        getParserSentenceData.put("Я иду, шагаю по Москве!", new String[][]{{"я", "иду"},{"шагаю", "по", "москве"}});
        getParserSentenceData.put("Я иду, шагаю по Москве! И мне хорошо.", new String[][]{{"я", "иду"},{"шагаю", "по", "москве"},{"и", "мне", "хорошо"}});
    }

    @After
    public void tearDownGetParserSentence() {
        getParserSentenceData.clear();
    }

    @Test
    public void testGetParserSentence() {
        getParserSentenceData.entrySet().forEach((entry) -> {
            final String[][] expected = entry.getValue();
            final String testData = entry.getKey();
            final List<List<String>> actual = GamaParser.getParserSentence(testData);
            assertEquals(expected.length, actual.size());
            for(int i = 0; i < actual.size(); i++){
                List<String> actualGetIndexI = actual.get(i);
                for(int j = 0; j < actualGetIndexI.size(); j++){
                    assertEquals(expected[i][j], actualGetIndexI.get(j));
                }
            }
        });
    }
}
