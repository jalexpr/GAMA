
package gama;

import jmorfsdk.JMorfSdk;
import jmorfsdk.load.JMorfSdkLoad;
import morphological.structures.storage.OmoFormList;

public class GameJMorfSdk {

    private final static JMorfSdk JMORFSDK = JMorfSdkLoad.loadInAnalysisMode();
    
    static OmoFormList getMorfWord(String word) {
        return JMORFSDK.getAllCharacteristicsOfForm(word);
    }

}
