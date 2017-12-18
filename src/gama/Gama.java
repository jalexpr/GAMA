package gama;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import storagestructures.OmoFormList;
import storagestructures.WordList;
import storagestructures.BearingPhraseList;
import storagestructures.SentenceList;
import storagestructures.ParagraphList;

public class Gama implements GamaAccessInterface {

    @Override
    public OmoFormList getMorfWord(String word) throws Exception {
        return GameJMorfSdk.getMorfWord(word);
    }

    @Override
    public WordList getMorfBearingPhrase(String bearingPhrase) {
        return getMorfBearingPhrase(GamaParser.getParserBearingPhrase(bearingPhrase));
    }

    @Override
    public BearingPhraseList getMorfSentence(String sentence) {
        return getMorfSentence(GamaParser.getParserSentence(sentence));
    }

    @Override
    public SentenceList getMorfParagraph(String paragraph) {
        return getMorfParagraph(GamaParser.getParserParagraph(paragraph));
    }

    @Override
    public ParagraphList getMorfText(String text) {
        ParagraphList morfText = new ParagraphList();
        GamaParser.getParserText(text).forEach((paragraph) -> {
            try {
                morfText.add(getMorfParagraph(paragraph));
            } catch (Exception ex) {
                Logger.getLogger(Gama.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return morfText;
    }

    private WordList getMorfBearingPhrase(List<String> bearingPhrase){
        WordList morfBearingPhrase = new WordList();
        bearingPhrase.forEach((word) -> {
            try {
                morfBearingPhrase.add(getMorfWord(word));
            } catch (Exception ex) {
                Logger.getLogger(Gama.class.getName()).log(Level.SEVERE, String.format("В опорном обороте %s\n", bearingPhrase), ex);
            }
        });
        return morfBearingPhrase;
    }

    public BearingPhraseList getMorfSentence(List<List<String>> sentence) {
        BearingPhraseList morfSentence = new BearingPhraseList();
        sentence.forEach((bearingPhrase) -> {
            morfSentence.add(getMorfBearingPhrase(bearingPhrase));
        });
        return morfSentence;
    }

    public SentenceList getMorfParagraph(List<List<List<String>>> paragraph) {
        SentenceList morfSentence = new SentenceList();
        paragraph.forEach((sentence) -> {
            try {
                morfSentence.add(getMorfSentence(sentence));
            } catch (Exception ex) {
                Logger.getLogger(Gama.class.getName()).log(Level.SEVERE, String.format("Парсер не верно распарсил предложение \"%s\"\n", sentence), ex);
            }
        });
        return morfSentence;
    }
}
