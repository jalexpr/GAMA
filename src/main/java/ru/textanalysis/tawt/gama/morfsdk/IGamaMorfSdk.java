package ru.textanalysis.tawt.gama.morfsdk;

import ru.textanalysis.tawt.ms.internal.ref.RefOmoFormList;

public interface IGamaMorfSdk {
    void init();
    RefOmoFormList getMorphWord(String word);
}
