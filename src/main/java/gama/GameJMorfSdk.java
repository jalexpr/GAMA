
package gama;

import jmorfsdk.JMorfSdk;
import jmorfsdk.load.LoadJMorfSdk;
import storagestructures.OmoFormList;

public class GameJMorfSdk {

    private final static JMorfSdk JMORFSDK = LoadJMorfSdk.loadInAnalysisMode();
    
    static OmoFormList getMorfWord(String word) {
        return JMORFSDK.getAllCharacteristicsOfForm(word);
    }    
}
