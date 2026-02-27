package compulsory;

public class Road {

    private String type;
    private int length;
    private int speed;
    private Location loc1, loc2;

    public Road(String type, int length, int speed, Location L1, Location L2) {
        this.type = type;
        this.length = length;
        this.speed = speed;
        this.loc1 = L1;
        this.loc2 = L2;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }

    public int getSpeed() {
        return speed;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public String toString()
    {
        //Afisarea datelor
        StringBuilder data = new StringBuilder("");
        data.append("Type " + this.type + '\n');
        data.append("Length: " + this.length + '\n');
        data.append("Speed limit: " + this.speed + '\n');
        data.append("Location 1: " + this.loc1.getName() + '\n');
        data.append("Location 2: " + this.loc2.getName() + '\n');

        return data.toString();
    }
}
