
package ru.textanalysis.tawt.gama.morfsdk;

import ru.textanalysis.tawt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tawt.jmorfsdk.loader.JMorfSdkFactory;
import ru.textanalysis.tawt.ms.internal.ref.RefOmoFormList;

public class GameMorphSdkDefault implements IGamaMorfSdk {
    private JMorfSdk jMorfSdk;

    @Override
    public void init() {
        jMorfSdk = JMorfSdkFactory.loadFullLibrary();
    }

    @Override
    public RefOmoFormList getMorphWord(String word) {
        return jMorfSdk.getRefOmoFormList(word);
    }
}
