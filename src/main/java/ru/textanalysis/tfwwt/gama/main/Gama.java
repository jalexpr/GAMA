package ru.textanalysis.tfwwt.gama.main;

import ru.textanalysis.tfwwt.gama.morfsdk.GameMorphSdkDefault;
import ru.textanalysis.tfwwt.gama.morfsdk.IGamaMorfSdk;
import ru.textanalysis.tfwwt.gama.parser.GamaParserDefault;
import ru.textanalysis.tfwwt.gama.parser.IGamaParser;
import ru.textanalysis.tfwwt.morphological.structures.internal.ref.RefOmoFormList;
import ru.textanalysis.tfwwt.morphological.structures.storage.ref.RefBearingPhraseList;
import ru.textanalysis.tfwwt.morphological.structures.storage.ref.RefParagraphList;
import ru.textanalysis.tfwwt.morphological.structures.storage.ref.RefSentenceList;
import ru.textanalysis.tfwwt.morphological.structures.storage.ref.RefWordList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Gama implements GamaAccessInterface {
    private IGamaParser gamaParser = new GamaParserDefault();
    private IGamaMorfSdk gamaMorphSdk = new GameMorphSdkDefault();

    public void init() {
        gamaParser.init();
        gamaMorphSdk.init();
    }

    public void setGamaParser(IGamaParser gamaParser) {
        this.gamaParser = gamaParser;
    }

    public void setGamaMorphSdk(IGamaMorfSdk gamaMorphSdk) {
        this.gamaMorphSdk = gamaMorphSdk;
    }

    @Override
    public RefOmoFormList getMorphWord(String word) {
        return gamaMorphSdk.getMorphWord(word);
    }

    @Override
    public RefWordList getMorphBearingPhrase(String bearingPhrase) {
        return getMorphBearingPhrase(gamaParser.getParserBearingPhrase(bearingPhrase));
    }

    @Override
    public RefBearingPhraseList getMorphSentence(String sentence) {
        return getMorphSentence(gamaParser.getParserSentence(sentence));
    }

    @Override
    public RefSentenceList getMorphParagraph(String paragraph) {
        return getMorphParagraph(gamaParser.getParserParagraph(paragraph));
    }

    @Override
    public RefParagraphList getMorphText(String text) {
        RefParagraphList morphText = new RefParagraphList();
        gamaParser.getParserText(text).forEach((paragraph) -> {
            try {
                morphText.add(getMorphParagraph(paragraph));
            } catch (Exception ex) {
                Logger.getLogger(Gama.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return morphText;
    }

    private RefWordList getMorphBearingPhrase(List<String> bearingPhrase) {
        RefWordList morphBearingPhrase = new RefWordList();
        bearingPhrase.forEach((word) -> {
            try {
                morphBearingPhrase.add(getMorphWord(word));
            } catch (Exception ex) {
                Logger.getLogger(Gama.class.getName()).log(Level.SEVERE, String.format("В опорном обороте %s\n", bearingPhrase), ex);
            }
        });
        return morphBearingPhrase;
    }

    private RefBearingPhraseList getMorphSentence(List<List<String>> sentence) {
        RefBearingPhraseList morphSentence = new RefBearingPhraseList();
        sentence.forEach((bearingPhrase) -> {
            morphSentence.add(getMorphBearingPhrase(bearingPhrase));
        });
        return morphSentence;
    }

    private RefSentenceList getMorphParagraph(List<List<List<String>>> paragraph) {
        RefSentenceList morphSentence = new RefSentenceList();
        paragraph.forEach((sentence) -> {
            try {
                morphSentence.add(getMorphSentence(sentence));
            } catch (Exception ex) {
                Logger.getLogger(Gama.class.getName()).log(Level.SEVERE, String.format("Парсер не верно распарсил предложение \"%s\"\n", sentence), ex);
            }
        });
        return morphSentence;
    }
}
