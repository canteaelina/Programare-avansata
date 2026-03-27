package lab3;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Algorithm {
    private SocialNetwork network;
    private Map<Profile, Set<Profile>> list_adj; //Graful scris cu liste de adiacenta profil = nod rel = muchii

    private int time;
    private Map<Profile, Integer> disc;
    private Map<Profile, Integer> low;
    private Map<Profile, Profile> parent;
    private Set<Profile> articulationPoints;
    private List<Set<Profile>> biconnectedComponents;
    private Stack<int[]> stack;
}
