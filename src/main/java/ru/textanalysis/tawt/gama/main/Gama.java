package ru.textanalysis.tawt.gama.main;

import org.slf4j.LoggerFactory;
import ru.textanalysis.tawt.gama.morfsdk.GameMorphSdkDefault;
import ru.textanalysis.tawt.gama.parser.GamaParserDefault;
import ru.textanalysis.tawt.ms.interfaces.gama.GamaAccessInterface;
import ru.textanalysis.tawt.ms.interfaces.gama.IGamaMorfSdk;
import ru.textanalysis.tawt.ms.interfaces.gama.IGamaParser;
import ru.textanalysis.tawt.ms.internal.ref.RefOmoFormList;
import ru.textanalysis.tawt.ms.storage.ref.RefBearingPhraseList;
import ru.textanalysis.tawt.ms.storage.ref.RefParagraphList;
import ru.textanalysis.tawt.ms.storage.ref.RefSentenceList;
import ru.textanalysis.tawt.ms.storage.ref.RefWordList;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Gama implements GamaAccessInterface {
    private final org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

    private IGamaParser gamaParser = new GamaParserDefault();
    private IGamaMorfSdk gamaMorphSdk = new GameMorphSdkDefault();

    @Override
    public void init() {
        gamaParser.init();
        gamaMorphSdk.init();
        log.debug("Gama is initialized!");
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
