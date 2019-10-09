package ru.textanalysis.tawt.gama;

import ru.textanalysis.tawt.gama.main.Gama;
import ru.textanalysis.tawt.ms.storage.ref.RefSentenceList;

public class ExampleGama {

    public static void main(String[] args) {

        Gama gama = new Gama();
        gama.init();
        RefSentenceList sentenceList = gama.getMorphParagraph("Осенний марафон -"
                + " стало ясно, что будет с российской валютой. Справедливый курс,"
                + " по мнению аналитиков, — на уровне 65-66.");
        System.out.println(sentenceList);


        gama.getMorphWord("мама").getOmoForm().forEach(System.out::println);

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
                    bearingPhrase.forEach(word ->  word.getOmoForm().forEach(System.out::println));
                })
            );
        });
    }
}
