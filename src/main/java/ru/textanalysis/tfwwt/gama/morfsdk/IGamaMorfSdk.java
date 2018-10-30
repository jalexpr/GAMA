package ru.textanalysis.tfwwt.gama.morfsdk;

import ru.textanalysis.tfwwt.morphological.structures.storage.OmoFormList;

public interface IGamaMorfSdk {
    public void init();
    public OmoFormList getMorfWord(String word);
}
