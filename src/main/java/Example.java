
import ru.textanalysis.tfwwt.gama.main.Gama;

public class Example {

    public static void main(String[] args) {

        Gama gama = new Gama();
        gama.init();

        gama.getMorphWord("мама").getOmoForm().forEach((omoForm) -> {
            System.out.println(omoForm);
        });

        System.out.println();
        gama.getMorphWord("село").getOmoForm().forEach((omoForm) -> {
            System.out.println(omoForm);
        });

        gama.getMorphBearingPhrase("Я шагаю").forEach((word) -> {
            word.getOmoForm().forEach((omoForm) -> {
                System.out.println(omoForm);
            });
        });

        gama.getMorphBearingPhrase("Мама мыла раму").forEach((word) -> {
            System.out.println();
            word.getOmoForm().forEach((omoForm) -> {
                System.out.println(omoForm);
            });
        });

        gama.getMorphBearingPhrase("Мама и папа мыла раму ").forEach((word) -> {
            System.out.println();
            word.getOmoForm().forEach((omoForm) -> {
                System.out.println(omoForm);
            });
        });

        gama.getMorphBearingPhrase("Мама мыла раму, а папа нет ").forEach((word) -> {
            System.out.println();
            word.getOmoForm().forEach((omoForm) -> {
                System.out.println(omoForm);
            });
        });

        gama.getMorphBearingPhrase("Мама мыла раму. А папа нет").forEach((word) -> {
            System.out.println();
            word.getOmoForm().forEach((omoForm) -> {
                System.out.println(omoForm);
            });
        });

        gama.getMorphSentence("Мама мыла раму. А папа нет").forEach(bearingPhrase -> {
            System.out.println();
            bearingPhrase.forEach(word -> {
                word.getOmoForm().forEach((omoForm) -> {
                    System.out.println(omoForm);
                });
            });
        });

        gama.getMorphParagraph("Мама мыла раму. А папа нет").forEach((morphSentence) -> {
            morphSentence.forEach(bearingPhrase -> {
                System.out.println();
                bearingPhrase.forEach(word -> {
                    word.getOmoForm().forEach((omoForm) -> {
                        System.out.println(omoForm);
                    });
                });
            });
        });

        gama.getMorphText("Мама мыла раму. А папа нет").forEach((morphParagraph) -> {
            System.out.println();
            morphParagraph.forEach((morphSentence) -> {
                morphSentence.forEach(bearingPhrase -> {
                    System.out.println();
                    bearingPhrase.forEach(word -> {
                        word.getOmoForm().forEach((omoForm) -> {
                            System.out.println(omoForm);
                        });
                    });
                });
            });
        });
    }
}
