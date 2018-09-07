
import org.tfwwt.gama.main.Gama;

public class Example {
    
    public static void main(String[] args) {

        Gama gama = new Gama();

        gama.getMorfWord("мама").forEach((omoForm) -> {
            System.out.println(omoForm);
        });

        System.out.println();
        gama.getMorfWord("село").forEach((omoForm) -> {
            System.out.println(omoForm);
        });

        gama.getMorfBearingPhrase("Я шагаю").forEach((word) -> {
            word.forEach((omoForm) -> {
                System.out.println(omoForm);
            });
        });

        gama.getMorfBearingPhrase("Мама мыла раму").forEach((word) -> {
            System.out.println();
            word.forEach((omoForm) -> {
                System.out.println(omoForm);
            });
        });

        gama.getMorfBearingPhrase("Мама и папа мыла раму ").forEach((word) -> {
            System.out.println();
            word.forEach((omoForm) -> {
                System.out.println(omoForm);
            });
        });

        gama.getMorfBearingPhrase("Мама мыла раму, а папа нет ").forEach((word) -> {
            System.out.println();
            word.forEach((omoForm) -> {
                System.out.println(omoForm);
            });
        });

        gama.getMorfBearingPhrase("Мама мыла раму. А папа нет").forEach((word) -> {
            System.out.println();
            word.forEach((omoForm) -> {
                System.out.println(omoForm);
            });
        });

        gama.getMorfSentence("Мама мыла раму. А папа нет").forEach((word) -> {
            System.out.println();
            word.forEach((omoForm) -> {
                System.out.println(omoForm);
            });
        });

        gama.getMorfParagraph("Мама мыла раму. А папа нет").forEach((word) -> {
            System.out.println();
            word.forEach((omoForm) -> {
                System.out.println(omoForm);
            });
        });

        gama.getMorfText("Мама мыла раму. А папа нет").forEach((word) -> {
            System.out.println();
            word.forEach((omoForm) -> {
                System.out.println(omoForm);
            });
        });

    }

}
