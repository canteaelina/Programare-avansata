package lab3;

import java.time.LocalDate;
import java.util.HashMap;

public class Person implements Profile {
    private String id;
    private String name;
    private static int count = 0;
    private LocalDate birthDate;
    private String nationality;
    private int yrsOfExp;
    private HashMap<Profile, String> relationships;

    //CONSTRUCTOR
    public Person(String name, LocalDate birth, String nationality)
    {
        relationships = new HashMap<>();
        this.name = name;
        this.id = "Person" + Integer.toString(count++);
        this.birthDate = birth;
        this.nationality = nationality;
    }

    //GETTERS
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public String getID()
    {
        return id;
    }

    @Override
    public String getName()
    {
        return name;
    }


    //SETTERS
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    //Adaugarea unei relatii dintre o persoana si o alta persoana/companie
    @Override
    public void addRelationship(Profile p, String rel)
    {
        relationships.put(p, rel);
    }

    //functia noua de comparare
    @Override
    public int compareTo(Profile other) {
        return this.name.compareTo(other.getName());
    }

    @Override
    public int compareToImp(Profile other)
    {
        if(this.getImportance() < other.getImportance())
            return -1;
        if(this.getImportance() == other.getImportance())
            return 0;
        return 1;
    }

    //Metoda care obtine importanta (nr de relatii)
    @Override
    public int getImportance()
    {
        int cnt = 0;
        for(var a : relationships.entrySet()) //parcurgem lista intrarilor din map ul cu relatii
           cnt++;
        return cnt;
    }

    @Override
    public String toString() {
        return this.id + " - " + this.name + "\nSpecialization: none";
    }
}
