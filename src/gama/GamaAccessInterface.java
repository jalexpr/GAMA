package gama;

import storagestructures.OmoFormList;
import storagestructures.WordList;
import storagestructures.BearingPhraseList;
import storagestructures.SentenceList;
import storagestructures.ParagraphList;

public interface GamaAccessInterface {

    public OmoFormList getMorfWord(String word) throws Exception;

    public WordList getMorfBearingPhrase(String bearingPhrase);

    public BearingPhraseList getMorfSentence(String sentence);

    public SentenceList getMorfParagraph(String paragraph);

    public ParagraphList getMorfText(String text);
}
