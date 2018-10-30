
package ru.textanalysis.tfwwt.gama.morfsdk;

import ru.textanalysis.tfwwt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tfwwt.jmorfsdk.load.JMorfSdkLoad;
import ru.textanalysis.tfwwt.morphological.structures.storage.OmoFormList;

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
