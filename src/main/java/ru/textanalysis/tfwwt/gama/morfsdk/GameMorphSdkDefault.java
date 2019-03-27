
package ru.textanalysis.tfwwt.gama.morfsdk;

import ru.textanalysis.tfwwt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tfwwt.jmorfsdk.load.JMorfSdkLoad;
import ru.textanalysis.tfwwt.morphological.structures.internal.ref.RefOmoFormList;

public class GameMorphSdkDefault implements IGamaMorfSdk {
    private JMorfSdk jMorfSdk;

    @Override
    public void init() {
        jMorfSdk = JMorfSdkLoad.loadFullLibrary();
    }

    @Override
    public RefOmoFormList getMorphWord(String word) {
        return jMorfSdk.getRefOmoFormList(word);
    }
}
