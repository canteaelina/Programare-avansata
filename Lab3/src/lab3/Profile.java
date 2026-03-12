package lab3;

import java.util.HashMap;

public interface Profile extends Comparable<Profile> {
    String getID();
    String getName();

    void setName(String name);
    void addRelationship(Profile p, String rel);
    public int compareTo(Profile other);

}
