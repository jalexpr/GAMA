
import gama.Gama;

public class Example {
    
    public static void main(String[] args) {
        Gama gama = new Gama();
        
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
    }
}
