package compulsory;

/**
 * Reprezinta o locatie abstracta pe harta.
 * Clasa este sealed si permite doar mostenirea de catre clasele specifice: {@link City}, {@link Airport} si {@link GasStation}.
 */
public sealed abstract class Location permits City, Airport, GasStation {
    private String name;
    private String type;
    private int x;
    private int y;

    /**
     * Construieste un obiect de tip Location.
     *
     * @param name Numele locatiei.
     * @param type Tipul locatiei (ex. "City", "Airport").
     * @param x    Coordonata pe axa X.
     * @param y    Coordonata pe axa Y.
     */
    public Location(String name, String type, int x, int y) {
        this.name = name;
        this.type = type;
        this.x = x;
        this.y = y;
    }

    /**
     * Obtine numele locatiei.
     *
     * @return Numele locatiei.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtine tipul locatiei.
     *
     * @return Tipul locatiei.
     */
    public String getType() {
        return type;
    }

    /**
     * Obtine coordonata X a locatiei.
     *
     * @return Coordonata X.
     */
    public int getX() {
        return x;
    }

    /**
     * Obtine coordonata Y a locatiei.
     *
     * @return Coordonata Y.
     */
    public int getY() {
        return y;
    }

    /**
     * Seteaza numele locatiei.
     *
     * @param name Noul nume al locatiei.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metoda abstracta pentru setarea tipului.
     * Trebuie implementata in subclase.
     *
     * @param type Tipul de setat.
     */
    public abstract void setType(String type);

    /**
     * Seteaza coordonata X a locatiei.
     *
     * @param x Noua coordonata X.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Seteaza coordonata Y a locatiei.
     *
     * @param y Noua coordonata Y.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Fac metoda toString() abstracta pentru a putea afisa proprietatile specifice fiecarei subclase.
     *
     * @return Un string cu detaliile locatiei.
     */
    @Override
    public abstract String toString();

    /**
     * Verifica egalitatea dintre aceasta locatie si un alt obiect.
     * Doua locatii sunt considerate egale daca au acelasi nume.
     *
     * @param obj Obiectul cu care se face comparatia.
     * @return {@code true} daca obiectele sunt egale, {@code false} in caz contrar.
     */
    @Override
    public boolean equals(Object obj)
    {
        //verific daca parametrul primit este exact acelasi obiect
        if(this == obj) return true;

        //verific daca parametrul obj e valid (ambele obiecte trebuie sa apartina aceleiasi clase si obj sa nu fie null)
        if(this.getClass() != obj.getClass() || obj == null)
            return false;

        Location other = (Location) obj;

        if(this.name.equals(other.name))
            return true;

        return false;
    }
}
/**
 * Reprezinta o locatie de tip City.
 */
final class City extends Location {

    private int population;

    /**
     * Construieste o noua instanta City.
     *
     * @param name       Numele orasului.
     * @param x          Coordonata pe axa X.
     * @param y          Coordonata pe axa Y.
     * @param population Numarul de locuitori ai orasului.
     */
    public City(String name, int x, int y, int population) {
        super(name, "City", x, y); //apelez constructorul din clasa parinte
        this.population = population;
    }

    /**
     * Seteaza populatia orasului.
     *
     * @param p Noul numar de locuitori.
     */
    public void setPopulation(int p)
    {
        this.population = p;
    }

    /**
     * Suprascrie metoda setType. In aceasta implementare nu face nimic.
     *
     * @param type Tipul locatiei.
     */
    public void setType(String type) {}

    /**
     * Obtine populatia orasului.
     *
     * @return Numarul de locuitori.
     */
    public int getPopulation() {
        return population;
    }

    /**
     * Returneaza o reprezentare textuala a orasului.
     *
     * @return String cu detaliile orasului.
     */
    public String toString()
    {
        //Afisarea datelor
        StringBuilder data = new StringBuilder("");
        data.append("Name " + this.getName() + '\n');
        data.append("Type: " + this.getType() + '\n');
        data.append("Coordinate x: " + this.getX() + '\n');
        data.append("Coordinate y: " + this.getY() + '\n');
        data.append("Population: " + this.population + '\n');

        return data.toString();
    }

}
/**
 * Reprezinta o locatie de tip Airport.
 */
final class Airport extends Location {

    private int nrOfTerminals;

    /**
     * Construieste o noua instanta Airport.
     *
     * @param name Numele aeroportului.
     * @param x    Coordonata pe axa X.
     * @param y    Coordonata pe axa Y.
     * @param nr   Numarul de terminale ale aeroportului.
     */
    public Airport(String name, int x, int y, int nr) {
        super(name, "Airport", x, y); //apelez constructorul din clasa parinte
        this.nrOfTerminals = nr;
    }

    /**
     * Seteaza numarul de terminale.
     *
     * @param n Noul numar de terminale.
     */
    public void nrOfTerminals(int n)
    {
        this.nrOfTerminals = n;
    }

    /**
     * Suprascrie metoda setType. In aceasta implementare nu face nimic.
     *
     * @param type Tipul locatiei.
     */
    public void setType(String type) {}

    /**
     * Obtine numarul de terminale.
     *
     * @return Numarul de terminale.
     */
    public int nrOfTerminals() {
        return nrOfTerminals;
    }

    /**
     * Returneaza o reprezentare textuala a aeroportului.
     *
     * @return String cu detaliile aeroportului.
     */
    public String toString()
    {
        //Afisarea datelor
        StringBuilder data = new StringBuilder("");
        data.append("Name " + this.getName() + '\n');
        data.append("Type: " + this.getType() + '\n');
        data.append("Coordinate x: " + this.getX() + '\n');
        data.append("Coordinate y: " + this.getY() + '\n');
        data.append("Number of terminals: " + this.nrOfTerminals + '\n');

        return data.toString();
    }

}
/**
 * Reprezinta o locatie de tip Benzinarie (Gas Station).
 */
final class GasStation extends Location {

    private int gasPrice;

    /**
     * Construieste o noua instanta GasStation.
     *
     * @param name  Numele benzinariei.
     * @param x     Coordonata pe axa X.
     * @param y     Coordonata pe axa Y.
     * @param price Pretul combustibilului.
     */
    public GasStation(String name, int x, int y, int price) {
        super(name, "Gas Station", x, y); //apelez constructorul din clasa parinte
        this.gasPrice = price;
    }

    /**
     * Seteaza pretul combustibilului.
     *
     * @param price Noul pret.
     */
    public void gasPrice(int price)
    {
        this.gasPrice = price;
    }

    /**
     * Obtine pretul combustibilului.
     *
     * @return Pretul combustibilului.
     */
    public void setType(String type) {}

    public int gasPrice() {
        return gasPrice;
    }

    /**
     * Returneaza o reprezentare textuala a benzinariei.
     *
     * @return String cu detaliile benzinariei.
     */
    public String toString()
    {
        //Afisarea datelor
        StringBuilder data = new StringBuilder("");
        data.append("Name " + this.getName() + '\n');
        data.append("Type: " + this.getType() + '\n');
        data.append("Coordinate x: " + this.getX() + '\n');
        data.append("Coordinate y: " + this.getY() + '\n');
        data.append("Gas price: " + this.gasPrice + '\n');

        return data.toString();
    }

}