package org.tfwwt.gama.main;

import org.tfwwt.gama.morfsdk.GameMorfSdkDefault;
import org.tfwwt.gama.morfsdk.IGamaMorfSdk;
import org.tfwwt.gama.parser.GamaParserDefault;
import org.tfwwt.gama.parser.IGamaParser;
import org.tfwwt.morphological.structures.storage.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Gama implements GamaAccessInterface {
    public IGamaParser gamaParser = new GamaParserDefault();
    public IGamaMorfSdk gamaMorfSdk= new GameMorfSdkDefault();

    public void init() {
        gamaParser.init();

    }

    public void setGamaParser(IGamaParser gamaParser) {
        this.gamaParser = gamaParser;
    }

    public void setGamaMorfSdk(IGamaMorfSdk gamaMorfSdk) {
        this.gamaMorfSdk = gamaMorfSdk;
    }

    @Override
    public OmoFormList getMorfWord(String word) {
        return gamaMorfSdk.getMorfWord(word);
    }

    @Override
    public WordList getMorfBearingPhrase(String bearingPhrase) {
        return getMorfBearingPhrase(gamaParser.getParserBearingPhrase(bearingPhrase));
    }

    @Override
    public BearingPhraseList getMorfSentence(String sentence) {
        return getMorfSentence(gamaParser.getParserSentence(sentence));
    }

    @Override
    public SentenceList getMorfParagraph(String paragraph) {
        return getMorfParagraph(gamaParser.getParserParagraph(paragraph));
    }

    @Override
    public ParagraphList getMorfText(String text) {
        ParagraphList morfText = new ParagraphList();
        gamaParser.getParserText(text).forEach((paragraph) -> {
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
