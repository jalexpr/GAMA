package ru.textanalysis.tawt.gama.morfsdk;

import ru.textanalysis.tawt.ms.model.jmorfsdk.Form;

import java.util.List;

public interface GamaMorfSdk {

	void init();

	List<Form> getMorphWord(String word);

	List<String> getMorphWord(String word, long morfCharacteristics) throws Exception;
}
