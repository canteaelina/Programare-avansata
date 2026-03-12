package lab3;

import java.util.HashMap;

public class Company implements Profile {

    private String id;
    private String name;
    private static int count = 0;
    private HashMap<Profile, String> relationships;
    private int nrOfEmployees = 0;
    private String hq;


    public Company(String name, String hq)
    {
        relationships = new HashMap<>();
        this.name = name;
        id = "Company" + Integer.toString(count++);
        this.hq = hq;
    }

    public int getNrOfEmployees() {
        return nrOfEmployees;
    }

    public String getHq() {
        return hq;
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

    @Override
    public void setName(String name)
    {
        this.name = name;
    }

    public void setHq(String hq) {
        this.hq = hq;
    }

    //adaug o relatie si cresc numarul de angajati
    @Override
    public void addRelationship(Profile p, String rel)
    {
        if(p != null)
        {
            relationships.put(p, rel);
            nrOfEmployees++;
        }
    }

    @Override
    public int compareTo(Profile other) {
        return this.name.compareTo(other.getName());
    }

    @Override
    //Metoda care obtine importanta (nr de relatii)
    public int getImportance()
    {
        int cnt = 0;
        for(var a : relationships.entrySet()) //parcurgem lista intrarilor din map ul cu relatii
            cnt++;
        return cnt;
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

    @Override
    public String toString() {
        return this.id + " - " + this.name + "\nLocation: " + this.hq;
    }

}
