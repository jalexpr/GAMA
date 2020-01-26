package ru.textanalysis.tawt.gama;

import ru.textanalysis.tawt.gama.main.Gama;
import ru.textanalysis.tawt.graphematic.parser.text.GParserImpl;
import ru.textanalysis.tawt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tawt.jmorfsdk.loader.JMorfSdkFactory;
import ru.textanalysis.tawt.ms.storage.ref.RefSentenceList;

public class ExampleGama {

    public static void main(String[] args) {

        Gama gama = new Gama();
        gama.init();
        GParserImpl gParser = new GParserImpl();
        JMorfSdk jMorfSdk = JMorfSdkFactory.loadFullLibrary();
        RefSentenceList sentenceList = gama.getMorphParagraph("Осенний марафон -"
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
        Long start;
        Long end;

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
                jMorfSdk.getAllCharacteristicsOfForm(str1);
            }
        }

        for (int i = 0; i < 10; i++) {
            start = System.currentTimeMillis();
            for (int j = 0; j < 10_000 * (i + 1); j++) {
                jMorfSdk.getAllCharacteristicsOfForm(str1);
                jMorfSdk.getAllCharacteristicsOfForm(str2);
                jMorfSdk.getAllCharacteristicsOfForm(str3);
                jMorfSdk.getAllCharacteristicsOfForm(str4);
                jMorfSdk.getAllCharacteristicsOfForm(str5);
                jMorfSdk.getAllCharacteristicsOfForm(str6);
                jMorfSdk.getAllCharacteristicsOfForm(str7);
                jMorfSdk.getAllCharacteristicsOfForm(str8);
                jMorfSdk.getAllCharacteristicsOfForm(str9);
                jMorfSdk.getAllCharacteristicsOfForm(str10);
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
        gama.getMorphWord("село").getOmoForm().forEach(System.out::println);

        gama.getMorphBearingPhrase("Я шагаю").forEach((word) ->
                word.getOmoForm().forEach(System.out::println)
        );

        gama.getMorphBearingPhrase("Мама мыла раму").forEach((word) -> {
            System.out.println();
            word.getOmoForm().forEach(System.out::println);
        });

        gama.getMorphBearingPhrase("Мама и папа мыла раму ").forEach((word) -> {
            System.out.println();
            word.getOmoForm().forEach(System.out::println);
        });

        gama.getMorphBearingPhrase("Мама мыла раму, а папа нет ").forEach((word) -> {
            System.out.println();
            word.getOmoForm().forEach(System.out::println);
        });

        gama.getMorphBearingPhrase("Мама мыла раму. А папа нет").forEach((word) -> {
            System.out.println();
            word.getOmoForm().forEach(System.out::println);
        });

        gama.getMorphSentence("Мама мыла раму. А папа нет").forEach(bearingPhrase -> {
            System.out.println();
            bearingPhrase.forEach(word -> word.getOmoForm().forEach(System.out::println));
        });

        gama.getMorphParagraph("Мама мыла раму. А папа нет").forEach((morphSentence) ->
                morphSentence.forEach(bearingPhrase -> {
                    System.out.println();
                    bearingPhrase.forEach(word -> word.getOmoForm().forEach(System.out::println));
                })
        );

        gama.getMorphText("Мама мыла раму. А папа нет").forEach((morphParagraph) -> {
            System.out.println();
            morphParagraph.forEach((morphSentence) ->
                    morphSentence.forEach(bearingPhrase -> {
                        System.out.println();
                        bearingPhrase.forEach(word -> word.getOmoForm().forEach(System.out::println));
                    })
            );
        });
    }
}
