public class test {
    @Annotation
    public void metodaFaraArgumente() {
        System.out.println("s a executat metodaFaraArgumente()");
    }

    @Annotation
    public void metodaCuUnInt(int numar) {
        System.out.println("s a executat metodaCuUnInt(). Numarul primit este: " + numar);
    }

    public void metodaFaraAdnotare(String text) {
        System.out.println("Aceasta nu trebuie sa fie apelata.");
    }
}
