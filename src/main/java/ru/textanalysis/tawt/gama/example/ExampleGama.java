package ru.textanalysis.tawt.gama.example;

import ru.textanalysis.tawt.gama.GamaImpl;
import ru.textanalysis.tawt.graphematic.parser.text.GParserImpl;
import ru.textanalysis.tawt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tawt.jmorfsdk.JMorfSdkFactory;
import ru.textanalysis.tawt.ms.model.gama.BearingPhrase;
import ru.textanalysis.tawt.ms.model.gama.Sentence;

public class ExampleGama {

	public static void main(String[] args) {

		GamaImpl gama = new GamaImpl();
		gama.init();

		BearingPhrase bearingPhraseExample = gama.getMorphBearingPhrase("Мама мыла раму, папа нет.");
		System.out.println(bearingPhraseExample);

//		BearingPhrase: [
//			Word:	- мама:
//				{TF=INITIAL,isInit=true,hash=33207555,str='мама',ToS=17,morf=106},
//			Word: 	- мыло:
//				{TF=DERIVATIVE,isInit=false,hash=408617219,str='мыла',ToS=17,morf=175}
//				{TF=DERIVATIVE,isInit=false,hash=408617219,str='мыла',ToS=17,morf=127}
//				{TF=DERIVATIVE,isInit=false,hash=408617219,str='мыла',ToS=17,morf=575}
//				{TF=DERIVATIVE,isInit=false,hash=408617219,str='мыла',ToS=20,morf=666664},
//			Word:	- рам:
//				{TF=DERIVATIVE,isInit=false,hash=615364099,str='раму',ToS=17,morf=549755814119}
//				{TF=DERIVATIVE,isInit=false,hash=615364099,str='раму',ToS=17,morf=555},
//			Word:	- папа:
//				{TF=INITIAL,isInit=true,hash=47220483,str='папа',ToS=17,morf=102},
//			Word:	- нет:
//				{TF=INITIAL,isInit=true,hash=40636160,str='нет',ToS=14,morf=0}
//				{TF=INITIAL,isInit=true,hash=40636160,str='нет',ToS=15,morf=0}
//				{TF=INITIAL,isInit=true,hash=40636160,str='нет',ToS=11,morf=65536}
//		]


		GParserImpl gParser = new GParserImpl();
		JMorfSdk jMorfSdk = JMorfSdkFactory.loadFullLibrary();
		Sentence sentenceList = gama.getMorphSentence("Осенний марафон -"
			+ " стало ясно, что будет с российской валютой. Справедливый курс,"
			+ " по мнению аналитиков, — на уровне 65-66.");
		System.out.println(sentenceList);

		String str = "мама и папа мыли стол, стоящий у длинной стены дома";
		String str1 = "мама";
		String str2 = "и";
		String str3 = "папа";
		String str4 = "мыли";
		String str5 = "стол";
		String str6 = "стоящий";
		String str7 = "у";
		String str8 = "длинной";
		String str9 = "стены";
		String str10 = "дома";
		long start;
		long end;

		System.out.println("gParser");
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10_000 * (i + 1); j++) {
				gParser.parserSentence(str);
			}
		}

		for (int i = 0; i < 10; i++) {
			start = System.currentTimeMillis();
			for (int j = 0; j < 10_000 * (i + 1); j++) {
				gParser.parserSentence(str);
			}
			end = System.currentTimeMillis();
			System.out.println(end - start);
		}

		System.out.println("jmorfsdk outside");
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 100_000 * (i + 1); j++) {
				jMorfSdk.getOmoForms(str1);
			}
		}

		for (int i = 0; i < 10; i++) {
			start = System.currentTimeMillis();
			for (int j = 0; j < 10_000 * (i + 1); j++) {
				jMorfSdk.getOmoForms(str1);
				jMorfSdk.getOmoForms(str2);
				jMorfSdk.getOmoForms(str3);
				jMorfSdk.getOmoForms(str4);
				jMorfSdk.getOmoForms(str5);
				jMorfSdk.getOmoForms(str6);
				jMorfSdk.getOmoForms(str7);
				jMorfSdk.getOmoForms(str8);
				jMorfSdk.getOmoForms(str9);
				jMorfSdk.getOmoForms(str10);
			}
			end = System.currentTimeMillis();
			System.out.println(end - start);
		}

		System.out.println("jmorfsdk inside");
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 100_000 * (i + 1); j++) {
				gama.getMorphWord(str1);
			}
		}

		for (int i = 0; i < 10; i++) {
			start = System.currentTimeMillis();
			for (int j = 0; j < 10_000 * (i + 1); j++) {
				gama.getMorphWord(str1);
				gama.getMorphWord(str2);
				gama.getMorphWord(str3);
				gama.getMorphWord(str4);
				gama.getMorphWord(str5);
				gama.getMorphWord(str6);
				gama.getMorphWord(str7);
				gama.getMorphWord(str8);
				gama.getMorphWord(str9);
				gama.getMorphWord(str10);
			}
			end = System.currentTimeMillis();
			System.out.println(end - start);
		}

		System.out.println("gama");
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10_000 * (i + 1); j++) {
				gama.getMorphSentence(str);
			}
		}

		for (int i = 0; i < 10; i++) {
			start = System.currentTimeMillis();
			for (int j = 0; j < 10_000 * (i + 1); j++) {
				gama.getMorphSentence(str);
			}
			end = System.currentTimeMillis();
			System.out.println(end - start);
		}

		System.out.println();
		gama.getMorphWord("село").getOmoForms().forEach(System.out::println);

		gama.getMorphBearingPhrase("Я шагаю").getWords().forEach((word) ->
			word.getOmoForms().forEach(System.out::println)
		);

		gama.getMorphBearingPhrase("Мама мыла раму").getWords().forEach((word) -> {
			System.out.println();
			word.getOmoForms().forEach(System.out::println);
		});

		gama.getMorphBearingPhrase("Мама и папа мыла раму ").getWords().forEach((word) -> {
			System.out.println();
			word.getOmoForms().forEach(System.out::println);
		});

		gama.getMorphBearingPhrase("Мама мыла раму, а папа нет ").getWords().forEach((word) -> {
			System.out.println();
			word.getOmoForms().forEach(System.out::println);
		});

		gama.getMorphBearingPhrase("Мама мыла раму. А папа нет").getWords().forEach((word) -> {
			System.out.println();
			word.getOmoForms().forEach(System.out::println);
		});

		gama.getMorphSentence("Мама мыла раму. А папа нет").getBearingPhrases().forEach(bearingPhrase -> {
			System.out.println();
			bearingPhrase.getWords().forEach(word -> word.getOmoForms().forEach(System.out::println));
		});

//		gama.getMorphParagraph("Мама мыла раму. А папа нет").forEach((morphSentence) ->
//			morphSentence.forEach(bearingPhrase -> {
//				System.out.println();
//				bearingPhrase.forEach(word -> word.getOmoForm().forEach(System.out::println));
//			})
//		); todo

//		gama.getMorphText("Мама мыла раму. А папа нет").forEach((morphParagraph) -> {
//			System.out.println();
//			morphParagraph.forEach((morphSentence) ->
//				morphSentence.forEach(bearingPhrase -> {
//					System.out.println();
//					bearingPhrase.forEach(word -> word.getOmoForm().forEach(System.out::println));
//				})
//			);
//		}); todo
	}
}
