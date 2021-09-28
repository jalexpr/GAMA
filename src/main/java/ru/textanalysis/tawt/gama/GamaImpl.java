package ru.textanalysis.tawt.gama;

import lombok.extern.slf4j.Slf4j;
import ru.textanalysis.tawt.gama.morfsdk.GamaMorfSdk;
import ru.textanalysis.tawt.gama.morfsdk.GameMorphSdkDefault;
import ru.textanalysis.tawt.gama.parser.GamaParser;
import ru.textanalysis.tawt.gama.parser.GamaParserDefault;
import ru.textanalysis.tawt.ms.model.gama.BearingPhrase;
import ru.textanalysis.tawt.ms.model.gama.Paragraph;
import ru.textanalysis.tawt.ms.model.gama.Sentence;
import ru.textanalysis.tawt.ms.model.gama.Word;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class GamaImpl implements Gama {

	private GamaParser gamaParser = new GamaParserDefault();
	private GamaMorfSdk gamaMorphSdk = new GameMorphSdkDefault();

	@Override
	public void init() {
		gamaParser.init();
		gamaMorphSdk.init();
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
