package ru.textanalysis.tawt.gama;

import lombok.extern.slf4j.Slf4j;
import ru.textanalysis.tawt.gama.disambiguation.DisambiguationResolver;
import ru.textanalysis.tawt.gama.morfsdk.GamaMorfSdk;
import ru.textanalysis.tawt.gama.morfsdk.GameMorphSdkDefault;
import ru.textanalysis.tawt.gama.parser.GamaParser;
import ru.textanalysis.tawt.gama.parser.GamaParserDefault;
import ru.textanalysis.tawt.ms.model.gama.BearingPhrase;
import ru.textanalysis.tawt.ms.model.gama.Paragraph;
import ru.textanalysis.tawt.ms.model.gama.Sentence;
import ru.textanalysis.tawt.ms.model.gama.Word;
import ru.textanalysis.tawt.ms.model.jmorfsdk.Form;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class GamaImpl implements Gama {

	private GamaParser gamaParser = new GamaParserDefault();
	private GamaMorfSdk gamaMorphSdk = new GameMorphSdkDefault();
	private DisambiguationResolver disambiguationResolver;

	@Override
	public void init() {
		gamaParser.init();
		gamaMorphSdk.init();
		disambiguationResolver = new DisambiguationResolver(Boolean.FALSE);
		log.debug("Gama is initialized!");
	}

	public void init(boolean initDisambiguationResolver) {
		gamaParser.init();
		gamaMorphSdk.init();
		disambiguationResolver = new DisambiguationResolver(initDisambiguationResolver);
		log.debug("Gama is initialized!");
	}

	public void setGamaParser(GamaParser gamaParser) {
		this.gamaParser = gamaParser;
	}

	public void setGamaMorphSdk(GamaMorfSdk gamaMorphSdk) {
		this.gamaMorphSdk = gamaMorphSdk;
	}

	@Override
	public Word getMorphWord(String literal) {
		return new Word(gamaMorphSdk.getMorphWord(literal));
	}

	@Override
	public BearingPhrase getMorphBearingPhrase(String bearingPhrase) {
		return new BearingPhrase(
			gamaParser.getParserBearingPhrase(bearingPhrase).stream()
				.map(this::getMorphWord)
				.collect(Collectors.toList())
		);
	}

	@Override
	public BearingPhrase disambiguation(BearingPhrase bearingPhrase) {
		List<Word> words = bearingPhrase.getWords();
		List<String> wordTags = new ArrayList<>();
		final String[] str = {""};
		words.forEach(word -> {
			List<Form> forms = word.getOmoForms();
			if (forms.size() == 1) {
				wordTags.add(Byte.toString(forms.get(0).getTypeOfSpeech()));
			} else if (forms.size() > 1) {
				forms.forEach(form -> {
					if (!str[0].contains(Byte.toString(form.getTypeOfSpeech()))) {
						str[0] += form.getTypeOfSpeech();
						str[0] += "|";
					}
				});
				str[0] = str[0].substring(0, str[0].length() - 1);
				wordTags.add(str[0]);
				str[0] = "";
			} else {
				wordTags.add("empty");
			}
		});
		disambiguationResolver.setPoSStopWords(words, wordTags);
		for (int k = 0; k < 2; k++) {
			for (int i = 0; i < wordTags.size(); i++) {
				if (k == 0) {
					if (wordTags.get(i).contains("|")) {
						if (words.get(i).getOmoForms().size() > 0 && disambiguationResolver.tagSequenceContains(words.get(i).getOmoForms().get(0).hashCode())) {
							disambiguationResolver.tagProbabilityCalculation(words, wordTags, i, true);
						} else if (words.get(i).getOmoForms().size() > 0) {
							disambiguationResolver.tagProbabilityCalculation(words, wordTags, i, false);
						}
					}
				} else {
					disambiguationResolver.caseProbabilityCalculation(words, wordTags, i);
				}
			}
		}
		return new BearingPhrase(disambiguationResolver.setFinalCharacteristics(words, wordTags));
	}


	@Override
	public Sentence getMorphSentence(String sentence) {
		return new Sentence(
			gamaParser.getParserSentence(sentence).stream()
				.map(
					bearingPhrase ->
						new BearingPhrase(
							bearingPhrase.stream()
								.map(this::getMorphWord)
								.collect(Collectors.toList())
						)
				)
				.collect(Collectors.toList())
		);
	}

	@Override
	public Paragraph getMorphParagraph(String paragraph) {
		return null;//todo
	}

	@Override
	public List<Paragraph> getMorphText(String text) {
//		return gamaParser.getParserText(text).stream()
//			.map(paragraph -> {
//				try {
//					return getMorphParagraph(paragraph);
//				} catch (Exception ex) {
//					log.debug("Cannot parse paragraph" + paragraph, ex);
//					return null;
//				}
//			})
//			.filter(Objects::nonNull)
//			.collect(Collectors.toList());
		return null;//todo
	}
}
