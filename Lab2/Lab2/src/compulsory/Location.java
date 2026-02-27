package compulsory;

public class Location {
    private String name;
    private String type;
    private int x;
    private int y;

    //Constructor
    public Location(String name, String type, int x, int y) {
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    //Getters
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    //Metoda noua toString()
    @Override
    public String toString()
    {
        //Afisarea datelor
        StringBuilder data = new StringBuilder("");
        data.append("Name " + this.name + '\n');
        data.append("Type: " + this.type + '\n');
        data.append("Coordinate x: " + this.x + '\n');
        data.append("Coordinate y: " + this.y + '\n');

        return data.toString();
    }
}
