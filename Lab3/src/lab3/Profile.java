package lab3;

import java.util.HashMap;

public interface Profile extends Comparable<Profile> {
    public String getID();
    public String getName();
    public void setName(String name);
    public void addRelationship(Profile p, String rel);
    public int getImportance();
    public int compareTo(Profile other);
    public int compareToImp(Profile other);
    public String toString();
}
