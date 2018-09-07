
package gama.morfsdk;

import org.tffwt.jmorfsdk.JMorfSdk;
import org.tffwt.jmorfsdk.load.JMorfSdkLoad;
import org.tffwt.morphological.structures.storage.OmoFormList;

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
