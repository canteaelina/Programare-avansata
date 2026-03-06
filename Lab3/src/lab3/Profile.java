package lab3;

import java.util.HashMap;

public interface Profile extends Comparable<Profile> {
    String getID();
    String getName();

    void setName(String name);
    void addRelationship(Profile p, String rel);
    public int compareTo(Profile other);

}

class Person implements Profile {
    private String id;
    private String name;
    private static int count = 0;
    private HashMap<Profile, String> relationships;

    public Person(String name)
    {
        relationships = new HashMap<>();
        this.name = name;
        id = "Person" + Integer.toString(count++);
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

    @Override
    public void addRelationship(Profile p, String rel)
    {
        relationships.put(p, rel);
    }
    @Override
    public int compareTo(Profile other) {
        return this.name.compareTo(other.getName());
    }
    @Override
    public String toString() {
        return this.id + " - " + this.name;
    }
}

class Company implements Profile {

    String id;
    String name;
    private static int count = 0;
    private HashMap<Profile, String> relationships;

    public Company(String name)
    {
        relationships = new HashMap<>();
        this.name = name;
        id = "Company" + Integer.toString(count++);
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

    //adaug o relatie
    @Override
    public void addRelationship(Profile p, String rel)
    {
        if(p != null)
            relationships.put(p, rel);
    }

    @Override
    public int compareTo(Profile other) {
        return this.name.compareTo(other.getName());
    }

    @Override
    public String toString() {
        return this.id + " - " + this.name;
    }

}