package ru.textanalysis.tfwwt.gama.morfsdk;

import ru.textanalysis.tfwwt.morphological.structures.internal.ref.RefOmoFormList;

public interface IGamaMorfSdk {
    void init();
    RefOmoFormList getMorphWord(String word);
}
