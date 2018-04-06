package gama.main;

import morphological.structures.storage.*;

public interface GamaAccessInterface {

    public OmoFormList getMorfWord(String word) throws Exception;

    public WordList getMorfBearingPhrase(String bearingPhrase);

    public BearingPhraseList getMorfSentence(String sentence);

    public SentenceList getMorfParagraph(String paragraph);

    public ParagraphList getMorfText(String text);

}
