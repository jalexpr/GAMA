package ru.textanalysis.tfwwt.gama.main;

import ru.textanalysis.tfwwt.morphological.structures.internal.ref.RefOmoFormList;
import ru.textanalysis.tfwwt.morphological.structures.storage.ref.RefBearingPhraseList;
import ru.textanalysis.tfwwt.morphological.structures.storage.ref.RefParagraphList;
import ru.textanalysis.tfwwt.morphological.structures.storage.ref.RefSentenceList;
import ru.textanalysis.tfwwt.morphological.structures.storage.ref.RefWordList;

public interface GamaAccessInterface {

    RefOmoFormList getMorphWord(String word) throws Exception;

    RefWordList getMorphBearingPhrase(String bearingPhrase);

    RefBearingPhraseList getMorphSentence(String sentence);

    RefSentenceList getMorphParagraph(String paragraph);

    RefParagraphList getMorphText(String text);

}
