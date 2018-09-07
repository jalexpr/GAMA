
package org.tfwwt.gama.morfsdk;

import org.tfwwt.jmorfsdk.JMorfSdk;
import org.tfwwt.jmorfsdk.load.JMorfSdkLoad;
import org.tfwwt.morphological.structures.storage.OmoFormList;

public class GameMorfSdkDefault implements IGamaMorfSdk {
    private JMorfSdk jMorfSdk;

    @Override
    public void init() {
        jMorfSdk = JMorfSdkLoad.loadInAnalysisMode();
    }

    @Override
    public OmoFormList getMorfWord(String word) {
        return jMorfSdk.getAllCharacteristicsOfForm(word);
    }
}
