package gama.morfsdk;

import org.tffwt.morphological.structures.storage.OmoFormList;

public interface IGamaMorfSdk {
    public void init();
    public OmoFormList getMorfWord(String word);
}
