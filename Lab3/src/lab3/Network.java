package lab3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Network {
    private List<Profile> profiles;

    public Network() {
        this.profiles = new ArrayList<>();
    }

    //adaug un profil
    public void addProfile(Profile profile) {
        if (profile != null) {
            profiles.add(profile);
        }
    }

    public List<Profile> getProfiles()
    {
        return profiles;
    }

    public void sort() {
        Collections.sort(profiles, Profile::compareTo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Social Network: \n");
        for (Profile p : profiles) {
            sb.append(p.toString()).append("\n");
        }
        return sb.toString();
    }
}
