package ru.textanalysis.tawt.gama.morfsdk;

import ru.textanalysis.tawt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tawt.jmorfsdk.JMorfSdkFactory;
import ru.textanalysis.tawt.ms.model.jmorfsdk.Form;

import java.util.List;

public class GameMorphSdkDefault implements GamaMorfSdk {

	private JMorfSdk jMorfSdk;

	@Override
	public void init() {
		jMorfSdk = JMorfSdkFactory.loadFullLibrary();
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
