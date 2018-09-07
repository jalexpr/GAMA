package org.tfwwt.gama.morfsdk;

import org.tfwwt.morphological.structures.storage.OmoFormList;

public interface IGamaMorfSdk {
    public void init();
    public OmoFormList getMorfWord(String word);
}
