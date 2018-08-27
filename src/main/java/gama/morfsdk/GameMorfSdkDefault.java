
package gama.morfsdk;

import jmorfsdk.JMorfSdk;
import jmorfsdk.load.JMorfSdkLoad;
import morphological.structures.storage.OmoFormList;

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
