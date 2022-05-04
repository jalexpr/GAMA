package ru.textanalysis.tawt.gama.morfsdk;

import ru.textanalysis.tawt.gama.stat.LoadStatFromFile;
import ru.textanalysis.tawt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tawt.jmorfsdk.JMorfSdkFactory;
import ru.textanalysis.tawt.ms.model.jmorfsdk.Form;

import java.util.HashMap;
import java.util.List;

public class GameMorphSdkDefault implements GamaMorfSdk {

	private JMorfSdk jMorfSdk;
	private HashMap<String, String> tagSequence;

	@Override
	public void init() {
		jMorfSdk = JMorfSdkFactory.loadFullLibrary();
		LoadStatFromFile loadStatFromFile = new LoadStatFromFile();
		tagSequence = loadStatFromFile.load(jMorfSdk);
	}

	@Override
	public boolean tagSequenceContains(String tag) {
		return tagSequence.containsKey(tag);
	}

	@Override
	public String getTagOccurrence(String tag) {
		try {
			return tagSequence.getOrDefault(tag, "0");
		} catch (Exception ex) {
			return "0";
		}
	}

	@Override
	public List<Form> getMorphWord(String literal) {
		return jMorfSdk.getOmoForms(literal);
	}

	@Override
	public List<String> getMorphWord(String literal, long morfCharacteristics) throws Exception {
		return jMorfSdk.getDerivativeFormLiterals(literal, morfCharacteristics);
	}
}
