package gama.parser;

import java.util.List;

/**
 * Created by User on 03.04.2018.
 */
public interface GamaParserInterface {

    public List<String> getParserBearingPhrase(String bearingPhrase);

    public List<List<String>> getParserSentence(String sentence);

    public List<List<List<String>>> getParserParagraph(String sentence);

    public List<List<List<List<String>>>> getParserText(String text);

}
