package lab3;

public class Main {
    public static void main(String[] args) {
        Person p1 = new Person("Zaharia Vasile");
        Person p2 = new Person("Ana Popescu");
        Person p3 = new Person("Mihai Ionescu");

        Company c1 = new Company("Apple Inc.");
        Company c2 = new Company("Google SRL");

        // persoana - persoana
        p1.addRelationship(p2, "best-friend");
        // pers - comp
        p1.addRelationship(c1, "angajat");
        // comp - pers
        c2.addRelationship(p3, "CEO");

        // fac reteaua
        SocialNetwork socialNetwork = new SocialNetwork();
        socialNetwork.addProfile(p1);
        socialNetwork.addProfile(c1); // Le adăugăm intenționat amestecate
        socialNetwork.addProfile(p2);
        socialNetwork.addProfile(c2);
        socialNetwork.addProfile(p3);

        // reteaua inainte de sortare
        System.out.println("--- Reteaua Inainte de Sortare ---");
        System.out.println(socialNetwork);

        // Sortez lista folosind un comparator
        socialNetwork.sort();
        System.out.println("--- Reteaua Dupa Sortarea Alfabetica ---");
        System.out.println(socialNetwork);
    }
}