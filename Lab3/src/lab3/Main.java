package lab3;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Person p1 = new Programmer("Zaharia Vasile", LocalDate.of(1998, 5, 15), "Romanian", 5, "Java");
        Person p2 = new Designer("Anna Perez", LocalDate.of(2002, 8, 10), "Spanish", "Adobe", "Maximalist");
        Person p3 = new Person("Mary Schmidt", LocalDate.of(2000, 11, 25), "German");

        Company c1 = new Company("Apple Inc.", "loc c1");
        Company c2 = new Company("Google SRL", "loc c2");

        // persoana - persoana
        p1.addRelationship(p2, "best-friend");
        // pers - comp
        p1.addRelationship(c1, "angajat");
        // comp - pers
        c2.addRelationship(p3, "CEO");

        p2.addRelationship(c1, "angajat");

        p1.addRelationship(p3, "friend");

        p3.addRelationship(p1, "boss");

        // fac reteaua
        SocialNetwork socialNetwork = new SocialNetwork();
        socialNetwork.addProfile(p1);
        socialNetwork.addProfile(c1); // Le adăugăm intenționat amestecate
        socialNetwork.addProfile(p2);
        socialNetwork.addProfile(c2);
        socialNetwork.addProfile(p3);

        // reteaua inainte de sortare
        System.out.println("--- Reteaua Inainte de Sortare Aflabetica ---");
        System.out.println(socialNetwork);

        // Sortez lista folosind un comparator
        socialNetwork.sort();
        System.out.println("--- Reteaua Dupa Sortarea Alfabetica ---");
        System.out.println(socialNetwork);

        // reteaua inainte de sortare dupa importanta
        System.out.println("--- Reteaua Inainte de Sortare Bazata pe Importanta ---");
        System.out.println(socialNetwork);

        // Sortez lista folosind un comparator
        socialNetwork.sortImp();
        System.out.println("--- Reteaua Dupa Sortarea Bazata pe Importanta ---");
        System.out.println(socialNetwork);
    }
}