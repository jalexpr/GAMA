package ru.textanalysis.tawt.gama;

import ru.textanalysis.tawt.ms.interfaces.InitializationModule;
import ru.textanalysis.tawt.ms.model.gama.BearingPhrase;
import ru.textanalysis.tawt.ms.model.gama.Paragraph;
import ru.textanalysis.tawt.ms.model.gama.Sentence;
import ru.textanalysis.tawt.ms.model.gama.Word;

import java.util.List;

public interface Gama extends InitializationModule {

	/**
	 * Получить список омоформа слова к входному литералу
	 *
	 * @param word литерал слова
	 *
	 * @return омоформы
	 */
	Word getMorphWord(String word);

	BearingPhrase getMorphBearingPhrase(String bearingPhrase);

	/**
	 * Частичное снятие омонимии в предложении
	 *
	 * @param bearingPhrase предложение
	 * @return предложение с частично снятой омонимии на уровне слов
	 */
	BearingPhrase disambiguation(BearingPhrase bearingPhrase);

	Sentence getMorphSentence(String sentence);

	Paragraph getMorphParagraph(String paragraph);

	List<Paragraph> getMorphText(String text);
}
