package gama.morfsdk;

import morphological.structures.storage.OmoFormList;

public interface IGamaMorfSdk {
    public void init();
    public OmoFormList getMorfWord(String word);
}
